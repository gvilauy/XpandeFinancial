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
    public int cCurrencyID = -1;
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



        try{

            String whereClause = "";

            // Si el usuario indica incluír todos los documentos o solamente cuenta abierta
            if ((this.tipoConceptoDoc.equalsIgnoreCase("TODOS")) || (this.tipoConceptoDoc.equalsIgnoreCase("CTA_ABIERTA"))){

                // Incluyo Invoices
                this.getDataInvoices();

                // Incluyo transferencias de saldo


                // Incluyo anticipos a socios de negocio

            }

            // Si el usuario indica incluír todos los documentos o solamente cuenta documentada
            if ((this.tipoConceptoDoc.equalsIgnoreCase("TODOS")) || (this.tipoConceptoDoc.equalsIgnoreCase("CTA_DOC"))){

                // Incluyo medios de pago emitidos pero no entregados
                this.getDataMediosPago();
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene información de invoices para el reporte.
     * Xpande. Created by Gabriel Vila on 3/19/19.
     */
    private void getDataInvoices(){

        String sql = "", action = "";

        try{

            // Incluyo invoices
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, c_invoice_id, c_invoicepayschedule_id, " +
                    " c_doctype_id, documentnoref, c_currency_id, datedoc, duedate, dateacct, issotrx, docbasetype, " +
                    " amtdocument, amtopen, amtallocated,  " +
                    " ad_user_id, tipofiltrofecha, tiposocionegocio, tieneacct, tipoconceptodoc, enddate, " +
                    " c_currency_id_to, ad_orgtrx_id, seqno, reference) ";

            String whereClauseAfecta = "";

            String whereClause = " and a.ad_client_id =" + this.adClientID;

            if (this.adOrgID > 0){
                whereClause += " and a.ad_org_id =" + this.adOrgID;
            }

            if (this.cBPartnerID > 0){
                whereClause += " and a.c_bpartner_id =" + this.cBPartnerID;
            }

            if (this.cCurrencyID > 0){
                whereClause += " and a.c_currency_id =" + this.cCurrencyID;
            }

            if (this.tipoFecha.equalsIgnoreCase("VALOR")){
                whereClause += " and a.dateinvoiced <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(a.C_PaymentTerm_ID, a.DateInvoiced)), a.dateinvoiced) <='" + this.endDate + "' ";
                whereClauseAfecta += " duedate <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.dateacct <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }

            if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                whereClause += " and a.issotrx ='Y'";
            }
            else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                whereClause += " and a.issotrx ='N'";
            }

            sql = " select a.ad_client_id, a.ad_org_id, a.c_bpartner_id, a.c_invoice_id, ips.c_invoicepayschedule_id, a.c_doctypetarget_id, " +
                    " (coalesce(a.documentserie, '') || a.documentno) as documentnoref, a.c_currency_id, a.dateinvoiced, " +
                    " coalesce(coalesce(ips.duedate, paymentTermDueDate(a.C_PaymentTerm_ID, a.DateInvoiced)), a.dateinvoiced)::timestamp without time zone as duedate, " +
                    " a.dateacct, a.issotrx, doc.docbasetype, " +
                    " coalesce(ips.dueamt, a.grandtotal) as amtdocument, coalesce(ips.dueamt, a.grandtotal) as amtopen, " +
                    " case when ips.c_invoicepayschedule_id > 0 then " +
                    " (select round(coalesce(sum(amtallocation),0),4) as amtallocated from z_invoiceafectacion " +
                    " where " + whereClauseAfecta + " and c_invoicepayschedule_id = ips.c_invoicepayschedule_id) " +
                    " else (select round(coalesce(sum(amtallocation),0),4) as amtallocated from z_invoiceafectacion " +
                    " where " + whereClauseAfecta + " and c_invoice_id = a.c_invoice_id) end as amtallocated, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "', '" + this.endDate + "', " + this.cCurrencyID + ", " +
                    this.adOrgID + ", 0, 'ABIERTA' " +
                    " from c_invoice a " +
                    " inner join c_doctype doc on a.c_doctypetarget_id = doc.c_doctype_id " +
                    " left outer join c_invoicepayschedule ips on a.c_invoice_id = ips.c_invoice_id " +
                    " where a.docstatus ='CO' " + whereClause +
                    " order by a.dateinvoiced, a.c_bpartner_id ";

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene información de medios de pago emitidos pero no entregados para el reporte.
     * Xpande. Created by Gabriel Vila on 3/19/19.
     */
    private void getDataMediosPago(){

        String sql = "", action = "";

        try{

            // Incluyo invoices
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, " +
                    " z_mediopago_id, nromediopago, z_mediopagoitem_id, z_emisionmediopago_id, " +
                    " c_bankaccount_id, c_currency_id, datedoc, duedate, dateacct, issotrx, " +
                    " amtdocument, amtopen, amtallocated,  " +
                    " ad_user_id, tipofiltrofecha, tiposocionegocio, tieneacct, tipoconceptodoc, enddate, " +
                    " c_currency_id_to, ad_orgtrx_id, seqno, reference) ";

            String whereClause = " and a.ad_client_id =" + this.adClientID;

            if (this.adOrgID > 0){
                whereClause += " and a.ad_org_id =" + this.adOrgID;
            }

            if (this.cBPartnerID > 0){
                whereClause += " and a.c_bpartner_id =" + this.cBPartnerID;
            }

            if (this.cCurrencyID > 0){
                whereClause += " and a.c_currency_id =" + this.cCurrencyID;
            }

            if (this.tipoFecha.equalsIgnoreCase("VALOR")){
                whereClause += " and a.dateemitted <='" + this.endDate + "'";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " and a.duedate <='" + this.endDate + "'";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.dateemitted <='" + this.endDate + "'";
            }

            if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                whereClause += " and a.isreceipt ='Y'";
            }
            else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                whereClause += " and a.isreceipt ='N'";
            }

            sql = " select a.ad_client_id, a.ad_org_id, a.c_bpartner_id, a.z_mediopago_id, a.nromediopago, " +
                    " a.z_mediopagoitem_id, a.z_emisionmediopago_id, a.c_bankaccount_id, a.c_currency_id, a.dateemitted, " +
                    " a.duedate, a.dateemitted, a.isreceipt, " +
                    " a.totalamt as amtdocument, a.totalamt as amtopen, 0, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "',' " + this.endDate + "', " + this.cCurrencyID + ", "
                    + this.adOrgID + ", 1, 'DOC' " +
                    " from z_mediopagoitem a " +
                    " where a.anulado ='N' and a.conciliado ='N' and a.depositado ='N' and a.emitido ='Y' and a.entregado ='N' and a.reemplazado ='N' " +
                    whereClause +
                    " order by a.dateemitted, a.c_bpartner_id ";

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene string para filtro de información de medios de pago emitidos pero no entregados.
     * Xpande. Created by Gabriel Vila on 3/19/19.
     * @return
     */
    private String getWhereClauseMediosPago() {

        String whereClause = "";

        whereClause = " a.ad_client_id =" + this.adClientID;

        if (this.adOrgID > 0){
            whereClause += " a.and ad_org_id =" + this.adOrgID;
        }

        if (this.cBPartnerID > 0){
            whereClause += " and a.c_bpartner_id =" + this.cBPartnerID;
        }

        if (this.cCurrencyID > 0){
            whereClause += " and a.c_currency_id =" + this.cCurrencyID;
        }

        if (this.tipoFecha.equalsIgnoreCase("VALOR")){
            whereClause += " and a.dateemmited <='" + this.endDate + "'";
        }
        else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
            whereClause += " and a.duedate <='" + this.endDate + "'";
        }
        else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
            whereClause += " and a.dateemmited <='" + this.endDate + "'";
        }

        if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
            whereClause += " and a.isreceipt ='Y'";
        }
        else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
            whereClause += " and a.isreceipt ='N'";
        }

        return whereClause;
    }


    /***
     * Actualizo información de la tabla del reporte.
     * Xpande. Created by Gabriel Vila on 3/19/19.
     */
    private void updateData(){

        String action = "";

        try{

            // Actualizo saldos pendientes
            action = " update " + TABLA_REPORTE +
                    " set amtopen = amtdocument - amtallocated " +
                    " where ad_user_id =" + this.adUserID;
            DB.executeUpdateEx(action, null);

            // Elimino comprobantes sin saldo pendiente
            action = " delete from " + TABLA_REPORTE +
                     " where ad_user_id =" + this.adUserID +
                     " and amtopen = 0";
            DB.executeUpdateEx(action, null);

            // Signos segun tipo de documento
            action = " update " + TABLA_REPORTE +
                    " set amtopen = (amtopen * -1), amtdocument = (amtdocument * -1), amtallocated = (amtallocated * -1) " +
                    " where ad_user_id =" + this.adUserID +
                    " and docbasetype in ('APC', 'ARC') ";
            DB.executeUpdateEx(action, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    private void updateData2(){

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.* " +
                    " from " + TABLA_REPORTE + " a " +
                    " inner join c_bpartner bp on a.c_bpartner_id = bp.c_bpartner_id " +
                    " where a.ad_user_id =" + this.adUserID +
                    " order by a.issotrx, a.ad_org_id, a.c_currency_id, bp.name, a.tipoconceptodoc, a.datedoc, a.c_doctype_id ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            int cCurrencyIDAux = 0, cBpartnerIDAux = 0, adOrgIDAux= 0;
            String isSOTrxAux = "-", tipoConceptoDoc = "-";

            BigDecimal amtAcumulado = Env.ZERO;

            while(rs.next()){

                // Corte por issotrx, moneda y socio de negocio
                if ((!rs.getString("issotrx").equalsIgnoreCase(isSOTrxAux))
                        || (rs.getInt("ad_org_id") != adOrgIDAux)
                        || (rs.getInt("c_currency_id") != cCurrencyIDAux)
                        || (rs.getInt("c_bpartner_id") != cBpartnerIDAux)
                        || (!rs.getString("tipoConceptoDoc").equalsIgnoreCase(tipoConceptoDoc))){

                    cCurrencyIDAux = rs.getInt("c_currency_id");
                    cBpartnerIDAux = rs.getInt("c_bpartner_id");
                    adOrgIDAux = rs.getInt("ad_org_id");
                    isSOTrxAux = rs.getString("issotrx");
                    tipoConceptoDoc = rs.getString("tipoconceptodoc");

                    amtAcumulado = Env.ZERO;
                }

                // Calculo y seto acumulado de esta linea en Tabla del Reporte para este ID
                BigDecimal amtOpen = rs.getBigDecimal("amtOpen");
                if (amtOpen == null) amtOpen = rs.getBigDecimal("amtDocument");

                BigDecimal amtAllocated = rs.getBigDecimal("amtAllocated");
                if (amtAllocated == null) amtAllocated = Env.ZERO;

                BigDecimal amtSaldo = amtOpen.subtract(amtAllocated);

                amtAcumulado = amtAcumulado.add(amtSaldo);

                action = " update " + TABLA_REPORTE +
                        " set amtOpen =" + amtSaldo + ", " +
                        " amtacumulado =" + amtAcumulado +
                        " where ad_user_id =" + this.adUserID;

                if (rs.getInt("c_invoice_id") > 0){
                    action = action + " and c_invoice_id =" + rs.getInt("c_invoice_id");
                }
                else if (rs.getInt("z_transfersaldo_id") > 0){
                    action = action + " and z_transfersaldo_id =" + rs.getInt("z_transfersaldo_id");
                }
                else if (rs.getInt("z_pago_id") > 0){
                    action = action + " and z_pago_id =" + rs.getInt("z_pago_id");
                }
                else if (rs.getInt("z_mediopagoitem_id") > 0){
                    action = action + " and z_mediopagoitem_id =" + rs.getInt("z_mediopagoitem_id");
                }

                DB.executeUpdateEx(action, null);
            }

            // Elimino registros sin saldo pendiente
            action = " delete from " + TABLA_REPORTE + " where ad_user_id =" + this.adUserID +
                     " and amtopen = 0 ";
            DB.executeUpdateEx(action, null);

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
