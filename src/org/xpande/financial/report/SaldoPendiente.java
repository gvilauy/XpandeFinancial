package org.xpande.financial.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Clase que contiene la Lógica de Ejecución para el Reporte de Saldos Pendientes de Socios de Negocio (RP y RV)
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/15/19.
 */
public class SaldoPendiente {

    private final String TABLA_REPORTE = "Z_RP_SaldoPendiente";

    public int adClientID = 0;
    public int adOrgID = 0;
    public int adUserID = 0;
    public int cBPartnerID = 0;
    public int cCurrencyID = 0;
    public String tipoFecha = "";
    public boolean tieneAcct = false;
    public String tipoSocioNegocio = "";
    public String tipoConceptoDoc = "";
    public Timestamp endDate = null;

    /***
     * Constructor
     */
    public SaldoPendiente() {
    }


    /***
     * Método que ejecuta la lógica del Reporte.
     * Xpande. Created by Gabriel Vila on 3/26/18.
     * @return
     */
    public String executeReport(){

        String message = null;

        try{
            this.deleteData();
            this.getData();
            this.updateData();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Elimina información anterior para este usuario en tablas de reporte
     * Xpande. Created by Gabriel Vila on 3/26/18.
     */
    private void deleteData() {

        try{
            String action = " delete from " + TABLA_REPORTE + " where ad_user_id =" + this.adUserID;
            DB.executeUpdateEx(action, null);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene información inicial para el reporte considerando filtros del reporte.
     * Impacta esta información en tabla del reporte.
     * Xpande. Created by Gabriel Vila on 3/26/18.
     */
    private void getData(){

        String sql = "", sql2 = "", sql3 = "", action = "";

        try{
            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, c_invoice_id, " +
                    " c_doctype_id, documentnoref, c_currency_id, datedoc, duedate, dateacct, issotrx, docbasetype, " +
                    " ad_user_id, tipofiltrofecha, tiposocionegocio, tieneacct, tipoconceptodoc) ";

            // Armo condicion where dinámica del reporte
            String whereClause = this.getWhereClause();

            // Invoices
            sql = " select ad_client_id, ad_org_id, c_bpartner_id, c_invoice_id, c_doctypetarget_id, " +
                    " (coalesce(documentserie, '') || documentno) as documentnoref, c_currency_id, datedoc, " +
                    " paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate, dateacct, issotrx, " +
                    " docbasetype, " + this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "' " +
                    " from c_invoice " +
                    " where " + whereClause +
                    " order by datedoc, c_bpartner_id ";

            DB.executeUpdateEx(action + sql, null);


            // Transferencias de Saldos
            sql = " select ad_client_id, ad_org_id, c_bpartner_id, c_invoice_id, c_doctypetarget_id, " +
                    " (coalesce(documentserie, '') || documentno) as documentnoref, c_currency_id, datedoc, " +
                    " paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate, dateacct, issotrx, " +
                    " docbasetype, " + this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "' " +
                    " from z_transfersaldo " +
                    " where " + whereClause +
                    " order by datedoc, c_bpartner_id ";

            // Anticipos de cobros / pagos




        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene string con condiciones a aplicar a datos del estado de cuenta.
     * Xpande. Created by Gabriel Vila on 3/26/18.
     * @return
     */
    private String getWhereClause() {

        String whereClause = "";

        whereClause = " ad_client_id =" + this.adClientID;

        if (this.adOrgID > 0){
            whereClause += " and ad_org_id =" + this.adOrgID;
        }

        if (this.cBPartnerID > 0){
            whereClause += " and c_bpartner_id =" + this.cBPartnerID;
        }

        if (this.cCurrencyID > 0){
            whereClause += " and c_currency_id =" + this.cCurrencyID;
        }

        if (this.tipoFecha.equalsIgnoreCase("VALOR")){
            whereClause += " and datedoc <='" + this.endDate + "'";
        }
        else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
            whereClause += " and duedate <='" + this.endDate + "'";
        }
        else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
            whereClause += " and dateacct <='" + this.endDate + "'";
        }

        if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
            whereClause += " and issotrx ='Y'";
        }
        else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
            whereClause += " and issotrx ='N'";
        }

        return whereClause;
    }


    /***
     * Actualiza saldo inicial y monto acumulado en tabla del reporte.
     * Xpande. Created by Gabriel Vila on 3/26/18.
     */
    private void updateData(){

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.issotrx, a.ad_org_id, a.c_currency_id, a.c_bpartner_id, a.amtsourcedr, a.amtsourcecr, a.z_estadocuenta_id " +
                    " from " + TABLA_REPORTE + " a " +
                    " inner join c_bpartner bp on a.c_bpartner_id = bp.c_bpartner_id " +
                    " where a.ad_user_id =" + this.adUserID +
                    " order by a.issotrx, a.ad_org_id, a.c_currency_id, bp.name, a.datedoc, a.c_doctype_id, a.z_estadocuenta_id ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            int cCurrencyIDAux = 0, cBpartnerIDAux = 0, adOrgIDAux= 0;
            String isSOTrxAux = "-";

            BigDecimal amtAcumulado = Env.ZERO;

            while(rs.next()){

                // Corte por issotrx, moneda y socio de negocio
                if ((!rs.getString("issotrx").equalsIgnoreCase(isSOTrxAux))
                        || (rs.getInt("ad_org_id") != adOrgIDAux)
                        || (rs.getInt("c_currency_id") != cCurrencyIDAux)
                        || (rs.getInt("c_bpartner_id") != cBpartnerIDAux)){


                    cCurrencyIDAux = rs.getInt("c_currency_id");
                    cBpartnerIDAux = rs.getInt("c_bpartner_id");
                    adOrgIDAux = rs.getInt("ad_org_id");
                    isSOTrxAux = rs.getString("issotrx");

                    amtAcumulado = Env.ZERO;
                }

                // Calculo y seto acumulado de esta linea en Tabla del Reporte para este ID
                BigDecimal amtSourceDr = rs.getBigDecimal("amtsourcedr");
                if (amtSourceDr == null) amtSourceDr = Env.ZERO;

                BigDecimal amtSourceCr = rs.getBigDecimal("amtsourcecr");
                if (amtSourceCr == null) amtSourceCr = Env.ZERO;

                amtAcumulado = amtAcumulado.add(amtSourceDr.subtract(amtSourceCr));

                action = " update " + TABLA_REPORTE +
                        " set AmtAcumulado =" + amtAcumulado +
                        " where ad_user_id =" + this.adUserID +
                        " and z_estadocuenta_id =" + rs.getInt("z_estadocuenta_id");
                DB.executeUpdateEx(action, null);
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

    }



}
