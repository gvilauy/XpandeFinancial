package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

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
            }
        }

        if ((timing == TIMING_AFTER_REACTIVATE) || (timing == TIMING_AFTER_VOID)){

            // Al momento de reactivar o anular, debo eliminar invoice del estado de cuenta
            if (!model.isSOTrx()){
                action = " delete from z_estadocuenta where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }
        }

        if (timing == TIMING_AFTER_COMPLETE){

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
