package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.comercial.model.MZInvoiceRef;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

/**
 * ModelValidator para funcionalidades relacionadas a gestión financiera del Core.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/8/17.
 */
public class ValidatorFinancial implements ModelValidator {

    private int adClientID = 0;

    @Override
    public void initialize(ModelValidationEngine engine, MClient client) {

        // Guardo compañia
        if (client != null){
            this.adClientID = client.get_ID();
        }

        // Document Validations
        engine.addDocValidate(I_C_Invoice.Table_Name, this);

        // DB Validations
        engine.addModelChange(I_C_BankAccount.Table_Name, this);

    }

    @Override
    public int getAD_Client_ID() {
        return this.adClientID;
    }

    @Override
    public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
        return null;
    }

    @Override
    public String modelChange(PO po, int type) throws Exception {

        return null;
    }

    @Override
    public String docValidate(PO po, int timing) {

        if (po.get_TableName().equalsIgnoreCase(I_C_Invoice.Table_Name)){
            return docValidate((MInvoice) po, timing);
        }

        return null;
    }


    /***
     * Validaciones y procesos para documentos de la tabla C_Invoice en gestión financiera.
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param timing
     * @return
     */
    private String docValidate(MInvoice model, int timing) {

        String message = null;
        String action = "";

        if ((timing == TIMING_BEFORE_REACTIVATE) || (timing == TIMING_BEFORE_VOID)){

            // Antes de reactivar o anular valido que esta invoice no tengo movimientos posteriores
            if (!model.isSOTrx()){

                // Para comprobantes de compra, valido que no este asociado a un resguardo.
                message = this.validateInvoiceResguardo(model);
                if (message != null){
                    return message;
                }


                // Para comprobantes de compra, valido que no este asociado a una orden de pago.
                message = this.validateInvoiceOrdenPago(model);
                if (message != null){
                    return message;
                }


                // Para comprobantes de compra, valido que no este asociado a un pago.
                message = this.validateInvoicePago(model);
                if (message != null){
                    return message;
                }

            }

        }

        else if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){

            // Al momento de reactivar o anular, debo eliminar invoice del estado de cuenta
            if (!model.isSOTrx()){
                action = " delete from z_estadocuenta where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }
        }

        else if (timing == TIMING_BEFORE_COMPLETE){

            // Manejo de CAJA y FONDO FIJO para medio de pago = Efectivo al completar una Invoice.
            if (X_C_Invoice.PAYMENTRULE_Cash.equals(model.getPaymentRule())){
                if (model.get_ValueAsInt("C_CashBook_ID") <= 0){
                    message = "Debe indicar Caja Destino del Efectivo seleccionado como medio de pago.";
                    return message;
                }

                // Genero pago o cobro que refleje el medio de pago efectivo seleccionado, en la caja indicada.
                MZPago pago = new MZPago(model.getCtx(), 0, model.get_TrxName());
                message = pago.generateFromCashInvoice(model);
                if (message != null){
                    return message;
                }
            }
        }

        else if (timing == TIMING_AFTER_COMPLETE){

            MDocType docType = (MDocType) model.getC_DocTypeTarget();
            String documentNoRef = model.getDocumentNo();
            if (model.get_ValueAsString("DocumentSerie") != null){
                if (!model.get_ValueAsString("DocumentSerie").trim().equalsIgnoreCase("")){
                    documentNoRef = model.get_ValueAsString("DocumentSerie").trim() + documentNoRef;
                }
            }

            // Al completar impacto en estado de cuenta
            // Impacto según vencimientos o no de esta invoice
            MInvoicePaySchedule[] paySchedules = MInvoicePaySchedule.getInvoicePaySchedule(model.getCtx(), model.get_ID(), 0, model.get_TrxName());
            if (paySchedules.length > 0){
                for (int i = 0; i < paySchedules.length; i++){
                    MInvoicePaySchedule ips = paySchedules[i];
                    MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(model.getCtx(), 0, model.get_TrxName());
                    estadoCuenta.setC_Invoice_ID(model.get_ID());
                    estadoCuenta.setC_InvoicePaySchedule_ID(ips.get_ID());
                    estadoCuenta.setAD_Table_ID(model.get_Table_ID());

                    if (!model.isSOTrx()){
                        if (docType.getDocBaseType().equalsIgnoreCase("API")){
                            estadoCuenta.setAmtSourceCr(ips.getDueAmt());
                            estadoCuenta.setAmtSourceDr(Env.ZERO);
                        }
                        else if (docType.getDocBaseType().equalsIgnoreCase("APC")){
                            estadoCuenta.setAmtSourceCr(Env.ZERO);
                            estadoCuenta.setAmtSourceDr(ips.getDueAmt());
                        }
                        else{
                            estadoCuenta.setAmtSourceCr(ips.getDueAmt());
                            estadoCuenta.setAmtSourceDr(Env.ZERO);
                        }
                    }
                    else{
                        if (docType.getDocBaseType().equalsIgnoreCase("ARC")){
                            estadoCuenta.setAmtSourceCr(ips.getDueAmt());
                            estadoCuenta.setAmtSourceDr(Env.ZERO);
                        }
                        else if (docType.getDocBaseType().equalsIgnoreCase("ARI")){
                            estadoCuenta.setAmtSourceCr(Env.ZERO);
                            estadoCuenta.setAmtSourceDr(ips.getDueAmt());
                        }
                        else{
                            estadoCuenta.setAmtSourceCr(Env.ZERO);
                            estadoCuenta.setAmtSourceDr(ips.getDueAmt());
                        }
                    }

                    estadoCuenta.setC_BPartner_ID(model.getC_BPartner_ID());
                    estadoCuenta.setC_Currency_ID(model.getC_Currency_ID());
                    estadoCuenta.setC_DocType_ID(model.getC_DocTypeTarget_ID());
                    estadoCuenta.setDateDoc(model.getDateInvoiced());
                    estadoCuenta.setDocBaseType(docType.getDocBaseType());
                    estadoCuenta.setDocumentNoRef(documentNoRef);
                    estadoCuenta.setDueDate(ips.getDueDate());
                    estadoCuenta.setEstadoAprobacion(model.get_ValueAsString("EstadoAprobacion"));
                    estadoCuenta.setIsSOTrx(model.isSOTrx());
                    estadoCuenta.setRecord_ID(model.get_ID());
                    estadoCuenta.setAD_Org_ID(model.getAD_Org_ID());
                    estadoCuenta.saveEx();
                }
            }
            else{
                // Fecha de Vencimiento de esta invoice, directamente de termino de pago
                String sql = " select paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate " +
                                " from c_invoice " +
                                " where c_invoice_id =" + model.get_ID();
                Timestamp dueDate = DB.getSQLValueTSEx(model.get_TrxName(), sql);
                if (dueDate == null){
                    dueDate = model.getDateInvoiced();
                }

                MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(model.getCtx(), 0, model.get_TrxName());
                estadoCuenta.setC_Invoice_ID(model.get_ID());
                estadoCuenta.setAD_Table_ID(model.get_Table_ID());

                // No se porque razón el grandtotal en esta etapa me lo guarda sin el redondeo.
                // Fuerzo el redondeo aca para el estado de cuenta
                BigDecimal amtRounding = (BigDecimal) model.get_Value("AmtRounding");
                if (amtRounding == null) amtRounding = Env.ZERO;
                BigDecimal amtTotal = model.getGrandTotal().add(amtRounding);

                if (!model.isSOTrx()){

                    if (docType.getDocBaseType().equalsIgnoreCase("API")){
                        estadoCuenta.setAmtSourceCr(amtTotal);
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                    else if (docType.getDocBaseType().equalsIgnoreCase("APC")){
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(amtTotal);
                    }
                    else{
                        estadoCuenta.setAmtSourceCr(amtTotal);
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                }
                else{
                    if (docType.getDocBaseType().equalsIgnoreCase("ARC")){
                        estadoCuenta.setAmtSourceCr(amtTotal);
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                    else if (docType.getDocBaseType().equalsIgnoreCase("ARI")){
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(amtTotal);
                    }
                    else{
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(amtTotal);
                    }
                }
                estadoCuenta.setC_BPartner_ID(model.getC_BPartner_ID());
                estadoCuenta.setC_Currency_ID(model.getC_Currency_ID());
                estadoCuenta.setC_DocType_ID(model.getC_DocTypeTarget_ID());
                estadoCuenta.setDateDoc(model.getDateInvoiced());
                estadoCuenta.setDocBaseType(docType.getDocBaseType());
                estadoCuenta.setDocumentNoRef(documentNoRef);
                estadoCuenta.setDueDate(dueDate);
                estadoCuenta.setEstadoAprobacion(model.get_ValueAsString("EstadoAprobacion"));
                estadoCuenta.setIsSOTrx(model.isSOTrx());
                estadoCuenta.setRecord_ID(model.get_ID());
                estadoCuenta.setAD_Org_ID(model.getAD_Org_ID());
                estadoCuenta.saveEx();
            }

            // Si estoy en una nota de crédito de clientes y tengo invoices referenciadas, guardo afectación
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_ARCredit)){
                List<MZInvoiceRef> invoiceRefList = MZInvoiceRef.getByInvoice(model.getCtx(), model.get_ID(), model.get_TrxName());
                for (MZInvoiceRef invoiceRef: invoiceRefList){
                    MZInvoiceAfectacion invoiceAfecta = new MZInvoiceAfectacion(model.getCtx(), 0, model.get_TrxName());
                    invoiceAfecta.setRef_Invoice_ID(model.get_ID());
                    invoiceAfecta.setAD_Table_ID(model.get_Table_ID());
                    invoiceAfecta.setAmtAllocation(invoiceRef.getAmtAllocation());
                    invoiceAfecta.setC_DocType_ID(model.getC_DocTypeTarget_ID());
                    invoiceAfecta.setC_Invoice_ID(invoiceRef.getC_Invoice_To_ID());
                    invoiceAfecta.setDateDoc(model.getDateInvoiced());
                    invoiceAfecta.setDocumentNoRef(model.getDocumentNo());
                    invoiceAfecta.setDueDate(model.getDateInvoiced());
                    invoiceAfecta.setRecord_ID(model.get_ID());
                    invoiceAfecta.setC_Currency_ID(model.getC_Currency_ID());
                    invoiceAfecta.setAD_Org_ID(model.getAD_Org_ID());
                    invoiceAfecta.saveEx();
                }
            }

        }

        return null;
    }


    /***
     * Valida si una invoice esta asociada a un documento de Resguardo Financiero.
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @return
     */
    private String validateInvoiceResguardo(MInvoice model) {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select rg.documentno, rg.docstatus, rgd.c_invoice_id " +
                    " from z_resguardosociodoc rgd " +
                    " inner join z_resguardosocio rg on rgd.z_resguardosocio_id = rg.z_resguardosocio_id " +
                    " inner join c_doctype doc on rg.c_doctype_id = doc.c_doctype_id " +
                    " where rgd.c_invoice_id =" + model.get_ID() +
                    " and rg.docstatus != 'VO'" +
                    " and doc.docbasetype='RGU' " +
                    " and rg.z_resguardosocio_ref_id is null ";

        	pstmt = DB.prepareStatement(sql, model.get_TrxName());
        	rs = pstmt.executeQuery();

        	if (rs.next()){
                message = " Este comprobante esta asociado al Resguardo : " + rs.getString("documentno") +
                        " (Estado Documento = " + rs.getString("docstatus") + ")";
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return message;
    }


    /***
     * Valida si una invoice esta asociada a un documento de Pago / Cobro
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param invoice
     * @return
     */
    private String validateInvoicePago(MInvoice invoice) {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            sql = " select pl.c_invoice_id, p.documentno, p.docstatus " +
                    " from z_pagolin pl " +
                    " inner join z_pago p on pl.z_pago_id = p.z_pago_id " +
                    " where pl.c_invoice_id =" + invoice.get_ID() +
                    " and pl.IsSelected ='Y' " +
                    " and p.docstatus != 'VO' ";

            pstmt = DB.prepareStatement(sql, invoice.get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){
                if (invoice.isSOTrx()){
                    message = " Este comprobante esta asociado al Cobro : " + rs.getString("documentno") +
                            " (Estado Documento = " + rs.getString("docstatus") + ")";
                }
                else{
                    message = " Este comprobante esta asociado al Pago : " + rs.getString("documentno") +
                            " (Estado Documento = " + rs.getString("docstatus") + ")";
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

        return message;
    }


    /***
     * Valida si una invoice esta asociada a un documento de Orden de Pago
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param invoice
     * @return
     */
    private String validateInvoiceOrdenPago(MInvoice invoice) {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            sql = " select pl.c_invoice_id, p.documentno, p.docstatus " +
                    " from z_ordenpagolin pl " +
                    " inner join z_ordenpago p on pl.z_ordenpago_id = p.z_ordenpago_id " +
                    " where pl.c_invoice_id =" + invoice.get_ID() +
                    " and p.docstatus != 'VO' ";

            pstmt = DB.prepareStatement(sql, invoice.get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){
                message = " Este comprobante esta asociado a la Orden de Pago : " + rs.getString("documentno") +
                        " (Estado Documento = " + rs.getString("docstatus") + ")";
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return message;
    }


    /***
     * Validaciones para el modelo de Cuenta Bancaria
     * Xpande. Created by Gabriel Vila on 9/29/18.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MBankAccount model, int type) throws Exception {

        String mensaje = null, action = "";

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Me aseguro de que se indique Organización distinta de *.
            if (model.getAD_Org_ID() <= 0){
                return "Debe indicar Organización para esta Cuenta Bancaria.";
            }

        }

        return mensaje;
    }


}
