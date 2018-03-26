package org.xpande.financial.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Clase que contiene la Lógica de Ejecución para el Reporte de Estado de Cuenta de Socios de Negocio (RP y RV)
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/24/18.
 */
public class EstadoCuenta {

    private final String TABLA_REPORTE = "Z_RP_EstadoCuenta";

    public int adClientID = 0;
    public int adOrgID = 0;
    public int adUserID = 0;
    public int cBPartnerID = 0;
    public int cCurrencyID = 0;
    public String tipoFecha = "";
    public String tipoSocioNegocio = "";
    public Timestamp startDate = null;
    public Timestamp endDate = null;


    /***
     * Constructor
     */
    public EstadoCuenta() {
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

        String sql = "", action = "";

        try{
            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, amtsourcecr, amtsourcedr, " +
                    " c_bpartner_id, c_currency_id, c_doctype_id, c_invoice_id, c_invoicepayschedule_id, " +
                    " datedoc, docbasetype, documentnoref, duedate, estadoaprobacion, " +
                    " issotrx, referenciapago, z_afectacion_id, z_estadocuenta_id, " +
                    " z_ordenpago_id, z_pago_id, z_resguardosocio_id, z_resguardosocio_to_id, ad_user_id, tipofiltrofecha, tiposocionegocio) ";

            // Armo condicion where dinámica del reporte
            String whereClause = this.getWhereClause();

            sql = " select ad_client_id, " + this.adOrgID + ", amtsourcecr, amtsourcedr, c_bpartner_id, c_currency_id, c_doctype_id, c_invoice_id, " +
                    " c_invoicepayschedule_id, datedoc, docbasetype, documentnoref, duedate, estadoaprobacion, issotrx, referenciapago, " +
                    " z_afectacion_id, z_estadocuenta_id, z_ordenpago_id, z_pago_id, z_resguardosocio_id, z_resguardosocio_to_id, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "' " +
                    " from z_estadocuenta " +
                    " where " + whereClause +
                    " order by c_currency_id, c_bpartner_id, datedoc, c_doctype_id, z_estadocuenta_id ";

            DB.executeUpdateEx(action + sql, null);

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
            whereClause += " and datedoc between '" + this.startDate + "' AND '" + this.endDate + "'";
        }
        else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
            whereClause += " and duedate between '" + this.startDate + "' AND '" + this.endDate + "'";
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
            sql = " select a.c_currency_id, a.c_bpartner_id, a.amtsourcedr, a.amtsourcecr, a.z_estadocuenta_id " +
                    " from " + TABLA_REPORTE + " a " +
                    " inner join c_bpartner bp on a.c_bpartner_id = bp.c_bpartner_id " +
                    " where a.ad_user_id =" + this.adUserID +
                    " order by a.c_currency_id, bp.name, a.datedoc, a.c_doctype_id, a.z_estadocuenta_id ";

        	pstmt = DB.prepareStatement(sql, null);
        	rs = pstmt.executeQuery();

        	int cCurrencyIDAux = 0, cBpartnerIDAux = 0;

            BigDecimal amtAcumulado = Env.ZERO;

        	while(rs.next()){

        	    // Corte por moneda y socio de negocio
                if ((rs.getInt("c_currency_id") != cCurrencyIDAux) || (rs.getInt("c_bpartner_id") != cBpartnerIDAux)){

                    cCurrencyIDAux = rs.getInt("c_currency_id");
                    cBpartnerIDAux = rs.getInt("c_bpartner_id");

                    // Obtengo y seteo saldo inicial del socio de negocio-moneda en tabla del reporte
                    BigDecimal saldoInicial = this.getSaldoInicial(cBpartnerIDAux, cCurrencyIDAux);
                    if (saldoInicial == null) saldoInicial = Env.ZERO;

                    action = " update " + TABLA_REPORTE +
                            " set AmtStart =" + saldoInicial +
                            " where ad_user_id =" + this.adUserID +
                            " and c_bpartner_id = " + cBpartnerIDAux +
                            " and c_currency_id = " + cCurrencyIDAux;
                    DB.executeUpdateEx(action, null);

                    amtAcumulado = saldoInicial;
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

    /***
     * Obtiene y retorna saldo inicial de un determinado socio de negocio en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 3/26/18.
     * @param cBpartnerID
     * @param cCurrencyID
     * @return
     */
    private BigDecimal getSaldoInicial(int cBpartnerID, int cCurrencyID) {

        BigDecimal amt = Env.ZERO;
        String sql = "";

        try{

            String whereClause = "";

            whereClause = " and ad_client_id =" + this.adClientID;

            if (this.adOrgID > 0){
                whereClause += " and ad_org_id =" + this.adOrgID;
            }

            if (this.tipoFecha.equalsIgnoreCase("VALOR")){
                whereClause += " and datedoc < '" + this.startDate + "'";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " and duedate < '" + this.startDate + "'";
            }

            if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                whereClause += " and issotrx ='Y'";
            }
            else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                whereClause += " and issotrx ='N'";
            }

            sql = " select (sum(amtsourcedr) - sum(amtsourcecr)) as saldo " +
                    " from z_estadocuenta " +
                    " where c_bpartner_id =" + cBpartnerID +
                    " and c_currency_id =" + cCurrencyID + whereClause;

            amt = DB.getSQLValueBDEx(null, sql);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return amt;
    }

}