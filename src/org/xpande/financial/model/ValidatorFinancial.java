package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
     * Validaciones para documentos de la tabla C_Invoice en gestión financiera.
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param timing
     * @return
     */
    private String docValidate(MInvoice model, int timing) {

        String message = null;

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

}
