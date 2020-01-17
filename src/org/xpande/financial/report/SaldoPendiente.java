package org.xpande.financial.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.financial.model.X_Z_EmisionMedioPago;
import org.xpande.financial.model.X_Z_Pago;
import org.xpande.financial.model.X_Z_TransferSaldo;

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
                this.getDataTransfSaldos();

                // Incluyo anticipos a socios de negocio
                this.getDataAnticipos();

                // Si el reporte es para proveedores, incluyo resguardos que no esten asociados a ordenes de pago o recibos.
                if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                    this.getDataResguardos();
                }
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
                    " c_currency_id_to, ad_orgtrx_id, seqno, reference, isgasto) ";

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
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(a.C_PaymentTerm_ID, a.DateInvoiced)), a.dateinvoiced) <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.dateacct <='" + this.endDate + "'";
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
                    " coalesce(ips.dueamt, a.grandtotal) as amtdocument, coalesce(ips.dueamt, a.grandtotal) as amtopen, 0, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "', '" + this.endDate + "', " + this.cCurrencyID + ", " +
                    this.adOrgID + ", 0, 'ABIERTA', " +
                    " case when (a.issotrx = 'N' and a.subdocbasetype is null) then 'Y' else 'N' end as isgasto " +
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
     * Obtiene información de documentos de Transferencia de Saldo para el reporte.
     * Xpande. Created by Gabriel Vila on 3/19/19.
     */
    private void getDataTransfSaldos(){

        String sql = "", action = "";

        try{

            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, z_transfersaldo_id, c_invoicepayschedule_id, " +
                    " c_doctype_id, documentnoref, c_currency_id, datedoc, duedate, dateacct, issotrx, docbasetype, " +
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
                whereClause += " and a.datedoc <='" + this.endDate + "'";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(inv.C_PaymentTerm_ID, inv.DateInvoiced)), a.datedoc) <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.datedoc <='" + this.endDate + "'";
            }

            if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                whereClause += " and a.issotrx ='Y'";
            }
            else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                whereClause += " and a.issotrx ='N'";
            }

            sql = " select a.ad_client_id, a.ad_org_id, a.c_bpartner_id, a.z_transfersaldo_id, ips.c_invoicepayschedule_id, a.c_doctype_id, " +
                    " a.documentno, a.c_currency_id, a.datedoc, " +
                    " coalesce(coalesce(ips.duedate, paymentTermDueDate(inv.C_PaymentTerm_ID, inv.DateInvoiced)), a.datedoc)::timestamp without time zone as duedate, " +
                    " a.datedoc, a.issotrx, doc.docbasetype, " +
                    " coalesce(ips.dueamt, a.grandtotal) as amtdocument, coalesce(ips.dueamt, a.grandtotal) as amtopen, 0, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "', '" + this.endDate + "', " + this.cCurrencyID + ", " +
                    this.adOrgID + ", 0, 'ABIERTA' " +
                    " from z_transfersaldo a " +
                    " inner join c_doctype doc on a.c_doctypetarget_id = doc.c_doctype_id " +
                    " inner join c_invoice inv on a.c_invoice_id = inv.c_invoice_id " +
                    " left outer join c_invoicepayschedule ips on inv.c_invoice_id = ips.c_invoice_id " +
                    " where a.docstatus ='CO' " + whereClause +
                    " order by a.datedoc, a.c_bpartner_id ";

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene información de Anticipos para el reporte.
     * Xpande. Created by Gabriel Vila on 3/25/19.
     */
    private void getDataAnticipos(){

        String sql = "", action = "";

        try{

            // Incluyo invoices
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, z_pago_id, " +
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
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }

            if (this.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                whereClause += " and a.issotrx ='Y'";
            }
            else if (this.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                whereClause += " and a.issotrx ='N'";
            }

            sql = " select a.ad_client_id, a.ad_org_id, a.c_bpartner_id, a.z_pago_id, a.c_doctype_id, " +
                    " a.documentno, a.c_currency_id, a.datedoc, a.datedoc, a.datedoc, a.issotrx, doc.docbasetype, " +
                    " a.payamt as amtdocument, a.payamt as amtopen, " +
                    " (select round(coalesce(sum(amtallocation),0),4) as amtallocated from z_pagoafectacion " +
                    " where " + whereClauseAfecta + " and z_pago_id = a.z_pago_id) as amtallocated, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "', '" + this.endDate + "', " + this.cCurrencyID + ", " +
                    this.adOrgID + ", 0, 'ABIERTA' " +
                    " from z_pago a " +
                    " inner join c_doctype doc on a.c_doctype_id = doc.c_doctype_id " +
                    " where a.docstatus ='CO' " +
                    " and anticipo ='Y' " +
                    " and z_pago_to_id is null " + whereClause +
                    " order by a.datedoc, a.c_bpartner_id ";

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    /***
     * Obtiene información de resguardos para el reporte.
     * Xpande. Created by Gabriel Vila on 1/9/20.
     */
    private void getDataResguardos(){

        String sql = "", action = "";

        try{
            // Incluyo Resguardos
            action = " insert into " + TABLA_REPORTE + "(ad_client_id, ad_org_id, c_bpartner_id, z_resguardosocio_id, " +
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
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClause += " and a.datedoc <='" + this.endDate + "'";
                whereClauseAfecta += " datedoc <='" + this.endDate + "' ";
            }

            sql = " select a.ad_client_id, a.ad_org_id, a.c_bpartner_id, a.z_resguardosocio_id, a.c_doctype_id, " +
                    " a.documentno, a.c_currency_id, a.datedoc, a.datedoc, a.datedoc, 'N', doc.docbasetype, " +
                    " a.totalamt as amtdocument, a.totalamt as amtopen, 0, " +
                    this.adUserID + ", '" + this.tipoFecha + "', '" + this.tipoSocioNegocio + "', '" +
                    ((this.tieneAcct) ? "Y" : "N") + "', '" + this.tipoConceptoDoc + "', '" + this.endDate + "', " + this.cCurrencyID + ", " +
                    this.adOrgID + ", 0, 'ABIERTA' " +
                    " from z_resguardosocio a " +
                    " inner join c_doctype doc on a.c_doctype_id = doc.c_doctype_id " +
                    " where a.docstatus ='CO' " + whereClause +
                    " and z_resguardosocio_id not in " +
                    " (select b.z_resguardosocio_id " +
                    " from z_pagoresguardo b " +
                    " inner join z_pago c on b.z_pago_id = c.z_pago_id " +
                    " where b.z_resguardosocio_id is not null and c.docstatus='CO' and c.datedoc <='" + this.endDate + "') " +
                    " and z_resguardosocio_id not in " +
                    " (select b.z_resguardosocio_id " +
                    " from Z_OrdenPagoLin b " +
                    " inner join z_ordenpago c on b.z_ordenpago_id = c.z_ordenpago_id " +
                    " where b.z_resguardosocio_id is not null and c.docstatus='CO' and c.datedoc <='" + this.endDate + "') " +
                    " order by a.datedoc, a.c_bpartner_id ";

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

            // Incluyo medios de pago emitidos
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
                    " where a.anulado ='N' and a.conciliado ='N' and a.depositado ='N' and a.emitido ='Y' and a.reemplazado ='N' " +
                    " and ((a.entregado ='N') or ((a.entregado ='Y') and (a.daterefpago is not null and a.daterefpago > '" + this.endDate + "'))) " +
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

            // Actualizo saldo afectacion
            this.updateSaldoAfectacion();

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
                    //" and docbasetype in ('APC', 'ARC', 'PPA', 'CCA') ";
                    " and docbasetype in ('APC', 'ARC', 'RGU') ";
            DB.executeUpdateEx(action, null);

            // Actualizo información de ultimo recibo y/o orden de pago
            this.updateInfoPago();

            // Actualizo ordenes de pago y pagos con id 0
            action = " update " + TABLA_REPORTE +
                    " set z_ordenpago_id = null " +
                    " where ad_user_id =" + this.adUserID +
                    " and z_ordenpago_id = 0";
            DB.executeUpdateEx(action, null);

            action = " update " + TABLA_REPORTE +
                    " set z_pago_id = null " +
                    " where ad_user_id =" + this.adUserID +
                    " and z_pago_id = 0";
            DB.executeUpdateEx(action, null);

            if (this.tieneAcct){

                // Cuenta contable proveedores API
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_C_Invoice.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctcr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".c_invoice_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and c_invoice_id > 0 " +
                        " and docbasetype ='API' ";
                DB.executeUpdateEx(action, null);

                // Cuenta contable proveedores APC
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_C_Invoice.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctdr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".c_invoice_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and c_invoice_id > 0 " +
                        " and docbasetype = 'APC' ";
                DB.executeUpdateEx(action, null);

                // Cuenta contable deudores ARI
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_C_Invoice.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctdr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".c_invoice_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and c_invoice_id > 0 " +
                        " and docbasetype ='ARI' ";
                DB.executeUpdateEx(action, null);

                // Cuenta contable deudores ARC
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_C_Invoice.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctcr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".c_invoice_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and c_invoice_id > 0 " +
                        " and docbasetype ='ARC' ";
                DB.executeUpdateEx(action, null);


                // Cuenta contable transferencias de saldos
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_Z_TransferSaldo.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctcr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".z_transfersaldo_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and z_transfersaldo_id > 0 ";
                DB.executeUpdateEx(action, null);

                // Cuenta contable anticipos proveedores
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_Z_Pago.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctcr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".z_pago_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and z_pago_id > 0 " +
                        " and docbasetype in ('PPA') ";
                DB.executeUpdateEx(action, null);

                // Cuenta contable Medios de Pago EMITIDOS
                action = " update " + TABLA_REPORTE +
                        " set c_elementvalue_id = (" +
                        " select account_id from fact_acct " +
                        " where ad_table_id =" + X_Z_EmisionMedioPago.Table_ID +
                        " and m_product_id is null and c_tax_id is null " +
                        " and amtacctcr != 0 " +
                        " and record_id = " + TABLA_REPORTE + ".z_emisionmediopago_id) " +
                        " where ad_user_id =" + this.adUserID +
                        " and z_emisionmediopago_id > 0 ";
                DB.executeUpdateEx(action, null);

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    private void updateSaldoAfectacion() {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String whereClauseAfecta = "";

            if (this.tipoFecha.equalsIgnoreCase("VALOR")){
                whereClauseAfecta = " and case when b.z_pago_id is null then a.datedoc <='" + this.endDate + "' else b.datedoc <='" + this.endDate + "' end ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("VENCIMIENTO")){
                whereClauseAfecta = " and a.duedate <='" + this.endDate + "' ";
            }
            else if (this.tipoFecha.equalsIgnoreCase("ACCT")){
                whereClauseAfecta = " and case when b.z_pago_id is null then a.datedoc <='" + this.endDate + "' else b.datedoc <='" + this.endDate + "' end ";
            }

            // Descarto actualizar saldo de afectacion de anticipos ya que lo calculo de una cuando se cargan los anticipos.
            sql = " select * from " + TABLA_REPORTE + " where ad_user_id =" + this.adUserID +
                    " and z_pago_id is null ";

        	pstmt = DB.prepareStatement(sql, null);
        	rs = pstmt.executeQuery();

        	BigDecimal amtAllocation = Env.ZERO;

        	while(rs.next()){

                amtAllocation = Env.ZERO;

        	    if (rs.getInt("c_invoice_id") > 0){

                    if (rs.getInt("c_invoicepayschedule_id") > 0){

                        sql = " select round(coalesce(sum(a.amtallocation),0),4) as amtallocated " +
                                " from z_invoiceafectacion a " +
                                " left outer join z_pago b on a.z_pago_id = b.z_pago_id " +
                                " where a.c_invoicepayschedule_id =" + rs.getInt("c_invoicepayschedule_id")  +
                                " and (a.z_pago_id is not null or a.z_transfersaldo_id is not null) " + whereClauseAfecta;

                        amtAllocation = DB.getSQLValueBDEx(null, sql);
                        if (amtAllocation == null) amtAllocation = Env.ZERO;

                        action = " update " + TABLA_REPORTE +
                                " set amtallocated =" + amtAllocation +
                                " where ad_user_id =" + this.adUserID +
                                " and c_invoice_id =" + rs.getInt("c_invoice_id") +
                                " and c_invoicepayschedule_id =" + rs.getInt("c_invoicepayschedule_id");
                        DB.executeUpdateEx(action, null);
                    }
                    else{
                        sql = " select round(coalesce(sum(a.amtallocation),0),4) as amtallocated " +
                                " from z_invoiceafectacion a " +
                                " left outer join z_pago b on a.z_pago_id = b.z_pago_id " +
                                " where a.c_invoice_id =" + rs.getInt("c_invoice_id")  +
                                " and (a.z_pago_id is not null or a.z_transfersaldo_id is not null) " + whereClauseAfecta;

                        amtAllocation = DB.getSQLValueBDEx(null, sql);
                        if (amtAllocation == null) amtAllocation = Env.ZERO;

                        action = " update " + TABLA_REPORTE +
                                " set amtallocated =" + amtAllocation +
                                " where ad_user_id =" + this.adUserID +
                                " and c_invoice_id =" + rs.getInt("c_invoice_id") +
                                " and c_invoicepayschedule_id is null ";
                        DB.executeUpdateEx(action, null);

                    }
                }
                else if (rs.getInt("z_transfersaldo_id") > 0){

                    if (rs.getInt("c_invoicepayschedule_id") > 0){

                        sql = " select round(coalesce(sum(a.amtallocation),0),4) as amtallocated " +
                                " from z_transferafectacion a " +
                                " left outer join z_pago b on a.z_pago_id = b.z_pago_id " +
                                " where a.c_invoicepayschedule_id =" + rs.getInt("c_invoicepayschedule_id")  +
                                " and a.z_pago_id is not null " + whereClauseAfecta;

                        amtAllocation = DB.getSQLValueBDEx(null, sql);
                        if (amtAllocation == null) amtAllocation = Env.ZERO;

                        action = " update " + TABLA_REPORTE +
                                " set amtallocated =" + amtAllocation +
                                " where ad_user_id =" + this.adUserID +
                                " and z_transfersaldo_id =" + rs.getInt("z_transfersaldo_id") +
                                " and c_invoicepayschedule_id =" + rs.getInt("c_invoicepayschedule_id");
                        DB.executeUpdateEx(action, null);

                    }
                    else{
                        sql = " select round(coalesce(sum(a.amtallocation),0),4) as amtallocated " +
                                " from z_transferafectacion a " +
                                " left outer join z_pago b on a.z_pago_id = b.z_pago_id " +
                                " where a.z_transfersaldo_id =" + rs.getInt("z_transfersaldo_id")  +
                                " and a.z_pago_id is not null " + whereClauseAfecta;

                        amtAllocation = DB.getSQLValueBDEx(null, sql);
                        if (amtAllocation == null) amtAllocation = Env.ZERO;

                        action = " update " + TABLA_REPORTE +
                                " set amtallocated =" + amtAllocation +
                                " where ad_user_id =" + this.adUserID +
                                " and z_transfersaldo_id =" + rs.getInt("z_transfersaldo_id") +
                                " and c_invoicepayschedule_id is null ";
                        DB.executeUpdateEx(action, null);

                    }

                }
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

    private void updateInfoPago(){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select * from " + TABLA_REPORTE + " where ad_user_id =" + this.adUserID;

        	pstmt = DB.prepareStatement(sql, null);
        	rs = pstmt.executeQuery();

        	while(rs.next()){

        	    // Actualizo invoices
        	    if (rs.getInt("c_invoice_id") > 0){
                    this.updateInfoPagoInvoice(rs.getInt("c_invoice_id"));
                }
                // Actualizo transferencias de saldo
                else if (rs.getInt("z_transfersaldo_id") > 0){
                    this.updateInfoPagoTransferSaldo(rs.getInt("z_transfersaldo_id"));
                }

                // Actualizo anticipos

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

    private void updateInfoPagoInvoice(int cInvoiceID){

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_ordenpago_id, a.z_pago_id, op.datedoc as dateordered, pago.datedoc as daterefpago " +
                    " from z_invoiceafectacion a " +
                    " left outer join z_ordenpago op on (a.z_ordenpago_id = op.z_ordenpago_id and op.datedoc >'" + this.endDate + "')  " +
                    " left outer join z_pago pago on (a.z_pago_id = pago.z_pago_id and pago.datedoc >'" + this.endDate + "')  " +
                    " where a.c_invoice_id =" + cInvoiceID +
                    " order by a.datedoc desc ";

        	pstmt = DB.prepareStatement(sql, null);
        	rs = pstmt.executeQuery();

        	if(rs.next()){

        	    if ((rs.getTimestamp("dateordered") != null) && (rs.getTimestamp("daterefpago") != null)){
                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " dateordered ='" + rs.getTimestamp("dateordered") + "', " +
                            " daterefpago ='" + rs.getTimestamp("daterefpago") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and c_invoice_id =" + cInvoiceID;
                    DB.executeUpdateEx(action, null);
                }
        	    else if ((rs.getTimestamp("dateordered") == null) && (rs.getTimestamp("daterefpago") != null)){
                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " daterefpago ='" + rs.getTimestamp("daterefpago") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and c_invoice_id =" + cInvoiceID;
                    DB.executeUpdateEx(action, null);
                }
                else if ((rs.getTimestamp("dateordered") != null) && (rs.getTimestamp("daterefpago") == null)){
                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " dateordered ='" + rs.getTimestamp("dateordered") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and c_invoice_id =" + cInvoiceID;
                    DB.executeUpdateEx(action, null);
                }
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

    private void updateInfoPagoTransferSaldo(int zTransferSaldoID){

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_ordenpago_id, a.z_pago_id, op.datedoc as dateordered, pago.datedoc as daterefpago " +
                    " from z_transferafectacion a " +
                    " left outer join z_ordenpago op on (a.z_ordenpago_id = op.z_ordenpago_id and op.datedoc >'" + this.endDate + "')  " +
                    " left outer join z_pago pago on (a.z_pago_id = pago.z_pago_id and pago.datedoc >'" + this.endDate + "')  " +
                    " where a.z_transfersaldo_id =" + zTransferSaldoID +
                    " order by a.datedoc desc ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            if(rs.next()){

                if ((rs.getTimestamp("dateordered") != null) && (rs.getTimestamp("daterefpago") != null)){

                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " dateordered ='" + rs.getTimestamp("dateordered") + "', " +
                            " daterefpago ='" + rs.getTimestamp("daterefpago") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and z_transfersaldo_id =" + zTransferSaldoID;
                    DB.executeUpdateEx(action, null);
                }
                else if ((rs.getTimestamp("dateordered") == null) && (rs.getTimestamp("daterefpago") != null)){

                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " daterefpago ='" + rs.getTimestamp("daterefpago") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and z_transfersaldo_id =" + zTransferSaldoID;
                    DB.executeUpdateEx(action, null);

                }
                else if ((rs.getTimestamp("dateordered") != null) && (rs.getTimestamp("daterefpago") == null)){

                    action = " update " + TABLA_REPORTE +
                            " set z_ordenpago_id =" + rs.getInt("z_ordenpago_id") + ", " +
                            " z_pago_id =" + rs.getInt("z_pago_id") + ", " +
                            " dateordered ='" + rs.getTimestamp("dateordered") + "' " +
                            " where ad_user_id =" + this.adUserID +
                            " and z_transfersaldo_id =" + zTransferSaldoID;
                    DB.executeUpdateEx(action, null);

                }

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
