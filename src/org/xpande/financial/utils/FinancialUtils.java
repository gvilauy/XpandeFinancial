package org.xpande.financial.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoicePaySchedule;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.financial.model.MZEstadoCuenta;
import org.xpande.financial.model.MZTransferSaldo;
import org.xpande.financial.model.X_Z_EstadoCuenta;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Clase de métodos staticos referidos a funcionalidades financieras.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/17/19.
 */
public final class FinancialUtils {


    /***
     * Obtiene y retorna monto abierto para invoice recibida.
     * Xpande. Created by Gabriel Vila on 6/17/19.
     * @param ctx
     * @param cInvoiceID
     * @param trxName
     * @return
     */
    public static BigDecimal getInvoiceAmtOpen(Properties ctx, int cInvoiceID, String trxName){

        BigDecimal value = Env.ZERO;

        try{
            String sql = " select coalesce(amtopen, amtdocument) as monto from zv_financial_invopen where c_invoice_id =" + cInvoiceID;

            value = DB.getSQLValueBDEx(trxName, sql);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return value;
    }


    /***
     * Método que impacta información de Invoices en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/28/19.
     * @param ctx
     * @param model
     * @param transferSaldo
     * @param onComplete
     * @param trxName
     */
    public static void setEstadoCtaInvoice(Properties ctx, MInvoice model, MZTransferSaldo transferSaldo, boolean onComplete, String trxName){

        String action = "";

        try{

            MDocType docType = (MDocType) model.getC_DocTypeTarget();
            String documentNoRef = model.getDocumentNo();
            if (model.get_ValueAsString("DocumentSerie") != null){
                if (!model.get_ValueAsString("DocumentSerie").trim().equalsIgnoreCase("")){
                    documentNoRef = model.get_ValueAsString("DocumentSerie").trim() + documentNoRef;
                }
            }

            if (onComplete){

                // Impacto según vencimientos o no de esta invoice
                MInvoicePaySchedule[] paySchedules = MInvoicePaySchedule.getInvoicePaySchedule(model.getCtx(), model.get_ID(), 0, trxName);
                if (paySchedules.length > 0){
                    for (int i = 0; i < paySchedules.length; i++){
                        MInvoicePaySchedule ips = paySchedules[i];
                        MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(model.getCtx(), 0, trxName);
                        estadoCuenta.setC_Invoice_ID(model.get_ID());
                        estadoCuenta.setC_InvoicePaySchedule_ID(ips.get_ID());
                        estadoCuenta.setAD_Table_ID(model.get_Table_ID());

                        if (!model.isSOTrx()){
                            estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);
                        }
                        else{
                            estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);
                        }


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

                        if ((transferSaldo != null) && (transferSaldo.get_ID() > 0)){
                            estadoCuenta.setZ_TransferSaldo_To_ID(transferSaldo.get_ID());
                            estadoCuenta.setDateRefTransfSaldo(transferSaldo.getDateDoc());
                        }

                        estadoCuenta.saveEx();
                    }
                }
                else{
                    // Fecha de Vencimiento de esta invoice, directamente de termino de pago
                    String sql = " select paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate " +
                                    " from c_invoice " +
                                    " where c_invoice_id =" + model.get_ID();
                    Timestamp dueDate = DB.getSQLValueTSEx(trxName, sql);
                    if (dueDate == null){
                        dueDate = model.getDateInvoiced();
                    }

                    MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(model.getCtx(), 0, trxName);
                    estadoCuenta.setC_Invoice_ID(model.get_ID());
                    estadoCuenta.setAD_Table_ID(model.get_Table_ID());

                    if (!model.isSOTrx()){
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);
                    }
                    else{
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);
                    }

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

                    if ((transferSaldo != null) && (transferSaldo.get_ID() > 0)){
                        estadoCuenta.setZ_TransferSaldo_To_ID(transferSaldo.get_ID());
                        estadoCuenta.setDateRefTransfSaldo(transferSaldo.getDateDoc());
                    }

                    estadoCuenta.saveEx();
                }


            }
            else {

                // Elimino datos en el estado de cuenta
                action = " delete from z_estadocuenta where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, trxName);

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

}
