package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.pdf.Document;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.comercial.model.MZInvoiceRef;
import org.xpande.financial.utils.FinancialUtils;

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
        String action, sql;

        if ((timing == TIMING_BEFORE_REACTIVATE) || (timing == TIMING_BEFORE_VOID)){

            MZFinancialConfig financialConfig = MZFinancialConfig.getDefault(model.getCtx(), null);
            if (financialConfig == null){
                return "Falta información de Configuración Financiera.";
            }

            // Guardo flag para saber si esta invoice fue imputada directamente con medios de pago asociados
            sql = " select count(*) from z_invoicemediopago where c_invoice_id =" + model.get_ID();
            int contador = DB.getSQLValueEx(model.get_TrxName(), sql);
            boolean afectacionDirecta = (contador > 0);

            // Antes de reactivar o anular valido que esta invoice no tengo movimientos posteriores
            if (!model.isSOTrx()) {

                // Para comprobantes de compra, valido que no este asociado a un resguardo.
                if (financialConfig.isControlaResguardos()){
                    message = this.validateInvoiceResguardo(model);
                    if (message != null) {
                        return message;
                    }
                }

                // Para comprobantes de compra, valido que no este asociado a una orden de pago.
                if (financialConfig.isControlaPagos()){
                    message = this.validateInvoiceOrdenPago(model);
                    if (message != null) {
                        return message;
                    }
                }

                // Para comprobantes de compra o venta, valido que no este asociado a un pago / cobro.
                if (financialConfig.isControlaPagos()){
                    // El control no aplica cuando se esta reactivando un comprobante de compra y el mismo tiene medio de pago efectivo o cuando tiene
                    // medios de pago asociados de manera directa.
                    if ((!X_C_Invoice.PAYMENTRULE_Cash.equalsIgnoreCase(model.getPaymentRule()) && (!afectacionDirecta))){
                        message = this.validateInvoicePago(model);
                        if (message != null){
                            return message;
                        }
                    }
                }

            }
            else{
                // Para comprobantes de compra o venta, valido que no este asociado a un pago / cobro.
                if (financialConfig.isControlaCobros()){
                    // El control no aplica cuando se esta reactivando un comprobante de venta y el mismo tiene medio de pago efectivo o cuando tiene
                    // medios de pago asociados de manera directa.
                    if ((!X_C_Invoice.PAYMENTRULE_Cash.equalsIgnoreCase(model.getPaymentRule()) && (!afectacionDirecta))){
                        message = this.validateInvoicePago(model);
                        if (message != null){
                            return message;
                        }
                    }
                }
            }

            // Para comprobantes de compra
            if (!model.isSOTrx()){
                // Para comprobantes de proveedores con medio de pago EFECTIVO o que tienen afectación directa,
                // tengo que anular el pago que se generó de manera automática.
                if ((X_C_Invoice.PAYMENTRULE_Cash.equals(model.getPaymentRule())) || (afectacionDirecta)){
                    message = this.deletePagosInvoice(model);
                    if (message != null){
                        return message;
                    }
                }
            }

        }

        else if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){

            // Elimino datos en el estado de cuenta
            FinancialUtils.setEstadoCtaInvoice(model.getCtx(), model, null, false, model.get_TrxName());

            // Para comprobantes de compra
            if (!model.isSOTrx()){

                // Si existe, anulo y elimino documento de Transferencia de Saldos asociado.
                if (model.get_ValueAsBoolean("TransferSaldo")){
                    sql = " select z_transfersaldo_id from z_transfersaldo where c_invoice_id =" + model.get_ID();
                    int transferSaldoID = DB.getSQLValueEx(model.get_TrxName(), sql);
                    if (transferSaldoID > 0){
                        MZTransferSaldo transferSaldo = new MZTransferSaldo(model.getCtx(), transferSaldoID, model.get_TrxName());
                        if (!transferSaldo.processIt(DocAction.ACTION_Void)){
                            message = transferSaldo.getProcessMsg();
                            return message;
                        }
                        transferSaldo.saveEx();
                        transferSaldo.deleteEx(true);
                    }
                }
            }
        }

        else if (timing == TIMING_BEFORE_COMPLETE){

            // Valido información cuando se indica Transferir Saldo a otro Socio de Negocio para pago / cobro
            if (model.get_ValueAsBoolean("TransferSaldo")){
                if (model.get_ValueAsInt("C_BPartnerRelation_ID") <= 0){
                    message = "Debe indicar Socio de Negocio Destino para Transferencia de Saldo.";
                    return message;
                }

                // No permito transferir saldo con medio de pago efectivo
                if (X_C_Invoice.PAYMENTRULE_Cash.equals(model.getPaymentRule())){
                    message = "No se permite Transferencia de Saldos con el Medio de Pago seleccionado.";
                    return message;
                }
            }

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
            else{
                // Si tengo monto de medios de pago
                BigDecimal amtMediosPago = (BigDecimal) model.get_Value("TotalMediosPago");
                if (amtMediosPago == null) amtMediosPago = Env.ZERO;
                if (amtMediosPago.compareTo(Env.ZERO) > 0){
                    // Valido que el monto de medios de pago sea igual al monto del comprobante
                    if (amtMediosPago.compareTo(model.getGrandTotal()) != 0){
                        message = "Monto total de medios de pago debe ser igual al Total del comprobante.";
                        return message;
                    }

                    // Genero pago/cobro para medios de pago asociados a este comprobante
                    MZPago pago = new MZPago(model.getCtx(), 0, model.get_TrxName());
                    message = pago.generateFromInvoice(model);
                    if (message != null){
                        return message;
                    }
                }
            }

        }

        else if (timing == TIMING_AFTER_COMPLETE){


            // Si tengo flag de Transferir Saldo a otro socio de negocio, genero este documento y lo completo.
            MZTransferSaldo transferSaldo = null;
            if (model.get_ValueAsBoolean("TransferSaldo")){
                // Genero documento de transferencia de saldo
                transferSaldo = new MZTransferSaldo(model.getCtx(), 0, model.get_TrxName());
                message = transferSaldo.generateFromInvoice(model);
                if (message != null){
                    return message;
                }
                // Completo documento de transferencia de saldo
                if (!transferSaldo.processIt(DocAction.ACTION_Complete)){
                    message = transferSaldo.getProcessMsg();
                    return message;
                }
                transferSaldo.saveEx();
            }

            // Al completar impacto en estado de cuenta
            FinancialUtils.setEstadoCtaInvoice(model.getCtx(), model, transferSaldo, true, model.get_TrxName());

        }

        return null;
    }


    /***
     * Elimina pagos que esten afectando a una determinada invoice.
     * Xpande. Created by Gabriel Vila on 5/9/19.
     * @param model
     * @return
     */
    private String deletePagosInvoice(MInvoice model) {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = "select z_pago_id from z_invoiceafectacion where C_Invoice_ID =" + model.get_ID();

        	pstmt = DB.prepareStatement(sql, model.get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){
                MZPago pago = new MZPago(model.getCtx(), rs.getInt("z_pago_id"), model.get_TrxName());
                if ((pago != null) && (pago.get_ID() > 0)){
                    if (pago.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
                        if (!pago.processIt(DocAction.ACTION_ReActivate)){
                            return pago.getProcessMsg();
                        }
                        pago.saveEx();

                        // Elimino el pago
                        pago.deleteEx(true);
                    }
                    else if (pago.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_InProgress)){

                        // Elimino el pago
                        pago.deleteEx(true);
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
