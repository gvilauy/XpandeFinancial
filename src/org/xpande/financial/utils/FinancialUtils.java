package org.xpande.financial.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_C_TaxGroup;
import org.xpande.financial.model.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

            // Si el socio de negocio tiene tipo de identificación OTROS, no impacto nada.
            MBPartner partner = (MBPartner) model.getC_BPartner();
            if ((partner != null) && (partner.get_ID() > 0)){
                if (partner.getC_TaxGroup_ID() > 0){
                    X_C_TaxGroup taxGroup = (X_C_TaxGroup) partner.getC_TaxGroup();
                    if ((taxGroup.getValue() != null) && (taxGroup.getValue().equalsIgnoreCase("OTRO"))){
                        return;
                    }
                }
            }

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
                        estadoCuenta.setDateAcct(model.getDateAcct());
                        estadoCuenta.setDocBaseType(docType.getDocBaseType());
                        estadoCuenta.setDocumentNoRef(documentNoRef);
                        estadoCuenta.setDueDate(ips.getDueDate());
                        estadoCuenta.setEstadoAprobacion(model.get_ValueAsString("EstadoAprobacion"));
                        estadoCuenta.setIsSOTrx(model.isSOTrx());
                        estadoCuenta.setRecord_ID(model.get_ID());
                        estadoCuenta.setAD_Org_ID(model.getAD_Org_ID());
                        estadoCuenta.set_ValueOfColumn("AD_Client_ID", model.getAD_Client_ID());
                        estadoCuenta.setDescription(model.getDescription());

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
                    estadoCuenta.setDateAcct(model.getDateAcct());
                    estadoCuenta.setDocBaseType(docType.getDocBaseType());
                    estadoCuenta.setDocumentNoRef(documentNoRef);
                    estadoCuenta.setDueDate(dueDate);
                    estadoCuenta.setEstadoAprobacion(model.get_ValueAsString("EstadoAprobacion"));
                    estadoCuenta.setIsSOTrx(model.isSOTrx());
                    estadoCuenta.setRecord_ID(model.get_ID());
                    estadoCuenta.setAD_Org_ID(model.getAD_Org_ID());
                    estadoCuenta.set_ValueOfColumn("AD_Client_ID", model.getAD_Client_ID());
                    estadoCuenta.setDescription(model.getDescription());

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


    /***
     * Método que impacta información de documento de Transferencia de Saldo en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param transferSaldo
     * @param dueDate
     * @param onComplete
     * @param trxName
     */
    public static void setEstadoCtaTransferSaldo(Properties ctx, MZTransferSaldo transferSaldo, Timestamp dueDate,
                                                 boolean onComplete, String trxName) {

        String action = "";

        try{

            MDocType docTypeTarget = (MDocType) transferSaldo.getC_DocTypeTarget();
            MDocType docType = (MDocType) transferSaldo.getC_DocType();
            MBPartner partnerRef = new MBPartner(ctx, transferSaldo.getC_BPartnerRelation_ID(), null);
            MBPartner partner = (MBPartner) transferSaldo.getC_BPartner();

            // Si se esta completando este documento
            if (onComplete){

                // Impacto documento en estado de cuenta  (se comporta como una invoice)
                MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(ctx, 0, trxName);
                estadoCuenta.setZ_TransferSaldo_ID(transferSaldo.get_ID());

                if (!transferSaldo.isSOTrx()){

                    estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);

                    if (docTypeTarget.getDocBaseType().equalsIgnoreCase("API")){
                        estadoCuenta.setAmtSourceCr(transferSaldo.getGrandTotal());
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                    else if (docTypeTarget.getDocBaseType().equalsIgnoreCase("APC")){
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(transferSaldo.getGrandTotal());
                    }
                    else{
                        estadoCuenta.setAmtSourceCr(transferSaldo.getGrandTotal());
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                }
                else{

                    estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);

                    if (docTypeTarget.getDocBaseType().equalsIgnoreCase("ARC")){
                        estadoCuenta.setAmtSourceCr(transferSaldo.getGrandTotal());
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                    else if (docTypeTarget.getDocBaseType().equalsIgnoreCase("ARI")){
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(transferSaldo.getGrandTotal());
                    }
                    else{
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(transferSaldo.getGrandTotal());
                    }
                }
                estadoCuenta.setC_BPartner_ID(transferSaldo.getC_BPartner_ID());
                estadoCuenta.setC_Currency_ID(transferSaldo.getC_Currency_ID());
                estadoCuenta.setC_DocType_ID(transferSaldo.getC_DocType_ID());
                estadoCuenta.setDateDoc(transferSaldo.getDateDoc());
                estadoCuenta.setDateAcct(transferSaldo.getDateDoc());
                estadoCuenta.setDocBaseType(docType.getDocBaseType());
                estadoCuenta.setDocumentNoRef(transferSaldo.getDocumentNo());
                estadoCuenta.setDueDate(dueDate);
                if ((partnerRef != null) && (partnerRef.get_ID() > 0)){
                    estadoCuenta.setReferenciaPago(partnerRef.getName());
                }
                estadoCuenta.setIsSOTrx(transferSaldo.isSOTrx());
                estadoCuenta.setAD_Table_ID(transferSaldo.get_Table_ID());
                estadoCuenta.setRecord_ID(transferSaldo.get_ID());
                estadoCuenta.setAD_Org_ID(transferSaldo.getAD_Org_ID());
                estadoCuenta.setDescription(transferSaldo.getDescription());

                estadoCuenta.saveEx();

                // Impacto linea en estado de cuenta para bajar la deuda del socio de negocio de la invoice
                MZEstadoCuenta estadoCuentaInv = new MZEstadoCuenta(ctx, 0, trxName);
                estadoCuentaInv.setZ_TransferSaldo_ID(transferSaldo.get_ID());

                if (!transferSaldo.isSOTrx()){

                    estadoCuentaInv.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);

                    if (docTypeTarget.getDocBaseType().equalsIgnoreCase("API")){
                        estadoCuentaInv.setAmtSourceDr(transferSaldo.getGrandTotal());
                        estadoCuentaInv.setAmtSourceCr(Env.ZERO);
                    }
                    else if (docTypeTarget.getDocBaseType().equalsIgnoreCase("APC")){
                        estadoCuentaInv.setAmtSourceDr(Env.ZERO);
                        estadoCuentaInv.setAmtSourceCr(transferSaldo.getGrandTotal());
                    }
                    else{
                        estadoCuentaInv.setAmtSourceDr(transferSaldo.getGrandTotal());
                        estadoCuentaInv.setAmtSourceCr(Env.ZERO);
                    }
                }
                else{

                    estadoCuentaInv.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);

                    if (docTypeTarget.getDocBaseType().equalsIgnoreCase("ARC")){
                        estadoCuentaInv.setAmtSourceDr(transferSaldo.getGrandTotal());
                        estadoCuentaInv.setAmtSourceCr(Env.ZERO);
                    }
                    else if (docTypeTarget.getDocBaseType().equalsIgnoreCase("ARI")){
                        estadoCuentaInv.setAmtSourceCr(Env.ZERO);
                        estadoCuentaInv.setAmtSourceDr(transferSaldo.getGrandTotal());
                    }
                    else{
                        estadoCuentaInv.setAmtSourceCr(Env.ZERO);
                        estadoCuentaInv.setAmtSourceDr(transferSaldo.getGrandTotal());
                    }
                }
                estadoCuentaInv.setC_BPartner_ID(transferSaldo.getC_BPartnerRelation_ID());
                estadoCuentaInv.setC_Currency_ID(transferSaldo.getC_Currency_ID());
                estadoCuentaInv.setC_DocType_ID(transferSaldo.getC_DocType_ID());
                estadoCuentaInv.setDateDoc(transferSaldo.getDateDoc());
                estadoCuentaInv.setDateAcct(transferSaldo.getDateDoc());
                estadoCuentaInv.setDocBaseType(docType.getDocBaseType());
                estadoCuentaInv.setDocumentNoRef(transferSaldo.getDocumentNo());
                estadoCuentaInv.setDueDate(dueDate);
                estadoCuentaInv.setIsSOTrx(transferSaldo.isSOTrx());
                estadoCuentaInv.setAD_Table_ID(transferSaldo.get_Table_ID());
                estadoCuentaInv.setRecord_ID(transferSaldo.get_ID());
                estadoCuentaInv.setAD_Org_ID(transferSaldo.getAD_Org_ID());
                estadoCuentaInv.setReferenciaPago(partner.getName());
                estadoCuenta.setDescription(transferSaldo.getDescription());

                estadoCuentaInv.saveEx();
            }
            else {  // Reactivate o Void del documento

                action = " update z_estadocuenta set z_transfersaldo_to_id = null, datereftransfsaldo = null " +
                        " where c_invoice_id =" + transferSaldo.getC_Invoice_ID();
                DB.executeUpdateEx(action, trxName);

                // Desasocio info en estado de cuenta para este documento
                action = " delete from z_estadocuenta where z_transfersaldo_id =" + transferSaldo.get_ID();
                DB.executeUpdateEx(action, trxName);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Método que impacta información de Resguardo en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param resguardoSocio
     * @param onComplete
     * @param trxName
     */
    public static void setEstadoCtaResguardo(Properties ctx, MZResguardoSocio resguardoSocio, boolean onComplete, String trxName){

        String action = "";

        try{
            // Si es contra-resguardo, seteo ADENDA para impresión
            MDocType docType = (MDocType) resguardoSocio.getC_DocType();

            // Si esta completando este documento
            if (onComplete){

                // Impacto asociación de resguardo a invoices en estado de cuenta
                action = " update z_estadocuenta set z_resguardosocio_to_id =" + resguardoSocio.get_ID() + ", " +
                        " daterefresguardo ='" + resguardoSocio.getDateDoc() + "' " +
                        " where c_invoice_id is not null " +
                        " and c_invoice_id in (select c_invoice_id from z_resguardosociodoc " +
                        " where z_resguardosocio_id =" + resguardoSocio.get_ID() + ")";
                DB.executeUpdateEx(action, trxName);

                // Hago tantos impactos de este resguardo como monedas tengan sus lineas
                HashMap<Integer, InfoMultiCurrency> hashResgMoneda = new HashMap<Integer, InfoMultiCurrency>();
                List<MZResguardoSocioDoc> resguardoSocioDocList = resguardoSocio.getResguardoDocs();
                for (MZResguardoSocioDoc resguardoSocioDoc: resguardoSocioDocList){
                    // Sumarizo por moneda
                    if (!hashResgMoneda.containsKey(resguardoSocioDoc.getC_Currency_ID())){
                        hashResgMoneda.put(resguardoSocioDoc.getC_Currency_ID(), new InfoMultiCurrency());
                        hashResgMoneda.get(resguardoSocioDoc.getC_Currency_ID()).cuurencyID = resguardoSocioDoc.getC_Currency_ID();
                    }
                    hashResgMoneda.get(resguardoSocioDoc.getC_Currency_ID()).amtSource = hashResgMoneda.get(resguardoSocioDoc.getC_Currency_ID()).amtSource.add(resguardoSocioDoc.getAmtRetencionMO());
                    hashResgMoneda.get(resguardoSocioDoc.getC_Currency_ID()).amtAcct = hashResgMoneda.get(resguardoSocioDoc.getC_Currency_ID()).amtAcct.add(resguardoSocioDoc.getAmtRetencion());
                }

                for (HashMap.Entry<Integer, InfoMultiCurrency> entry : hashResgMoneda.entrySet()){

                    // Impacto documento en estado de cuenta en este moneda
                    MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(ctx, 0, trxName);
                    estadoCuenta.setZ_ResguardoSocio_ID(resguardoSocio.get_ID());
                    estadoCuenta.setAD_Table_ID(resguardoSocio.get_Table_ID());

                    estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);

                    // Monto al debe o al haber segun sea resguardo o contra-resguardo
                    if (docType.getDocBaseType().equalsIgnoreCase("RGC")){
                        estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                    }
                    else{
                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                        estadoCuenta.setAmtSourceDr(entry.getValue().amtSource);
                    }
                    estadoCuenta.setC_BPartner_ID(resguardoSocio.getC_BPartner_ID());
                    estadoCuenta.setC_Currency_ID(entry.getValue().cuurencyID);
                    estadoCuenta.setC_DocType_ID(resguardoSocio.getC_DocType_ID());
                    estadoCuenta.setDateDoc(resguardoSocio.getDateDoc());
                    estadoCuenta.setDateAcct(resguardoSocio.getDateDoc());
                    estadoCuenta.setDocBaseType(docType.getDocBaseType());
                    estadoCuenta.setDocumentNoRef(resguardoSocio.getDocumentNo());
                    estadoCuenta.setIsSOTrx(false);
                    estadoCuenta.setRecord_ID(resguardoSocio.get_ID());
                    estadoCuenta.setAD_Org_ID(resguardoSocio.getAD_Org_ID());
                    estadoCuenta.setDescription(resguardoSocio.getDescription());

                    estadoCuenta.saveEx();

                }

            }
            else {  // Reactivate o Void del documento

                // Desafecto documentos asociadas con este resguardo en el estado de cuenta
                action = " update z_estadocuenta set z_resguardosocio_to_id = null, daterefresguardo = null " +
                        " where z_resguardosocio_to_id =" + resguardoSocio.get_ID();
                DB.executeUpdateEx(action, trxName);

                // Desafecto info en estado de cuenta para este documento de pago/cobro
                action = " delete from z_estadocuenta where z_resguardosocio_id =" + resguardoSocio.get_ID();
                DB.executeUpdateEx(action, trxName);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Método que impacta información de Pago / Cobro / Anticipo en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param pago
     * @param onComplete
     * @param trxName
     */
    public static void setEstadoCtaPago(Properties ctx, MZPago pago, boolean onComplete, String trxName){

        String action = "";

        try{

            // Si el socio de negocio tiene tipo de identificación OTROS, no impacto nada.
            MBPartner partner = (MBPartner) pago.getC_BPartner();
            if ((partner != null) && (partner.get_ID() > 0)){
                if (partner.getC_TaxGroup_ID() > 0){
                    X_C_TaxGroup taxGroup = (X_C_TaxGroup) partner.getC_TaxGroup();
                    if ((taxGroup.getValue() != null) && (taxGroup.getValue().equalsIgnoreCase("OTRO"))){
                        return;
                    }
                }
            }

            // Si esta completando este documento
            if (onComplete){

                // Impactos en estado de cuenta del socio de negocio. Parte Acreedora y Deudora.
                boolean isVendor = true;
                if (pago.isSOTrx()) isVendor = false;

                // Si es un anticipo, impacto en estado de cuenta para parte acreedora y deudora
                if ((pago.isAnticipo()) && (!pago.isAnticipoDirecto())){

                    if (!isVendor){
                        FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), isVendor, trxName);
                        FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), !isVendor, trxName);
                    }
                    else {

                        // Si es anticipo de cobros a empleados
                        if (partner.isEmployee()){
                            FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), isVendor, trxName);
                        }
                        else{
                            FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), isVendor, trxName);
                            FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), !isVendor, trxName);
                        }
                    }
                }
                else{
                    // Si es un recibo para pago de anticipo
                    if (pago.isReciboAnticipo()){
                        // Impacto solamente parte acreedora para pagos y parte deudora para cobros
                        FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), isVendor, trxName);
                    }
                    else{

                        // Si tengo medios de pago, entonces este documento debe salir en el estado de cuento, sino no tiene sentido
                        if ((pago.getTotalMediosPago() != null) && (pago.getTotalMediosPago().compareTo(Env.ZERO) != 0)){

                            BigDecimal amtAnticipo = pago.getAmtAnticipo();
                            if (amtAnticipo == null) amtAnticipo = Env.ZERO;

                            // Impacto parte acreedora para pagos y deudora para cobros, por monto total menos anticipos
                            if (amtAnticipo.compareTo(Env.ZERO) <= 0){
                                FinancialUtils.setEstadoCtaPago(ctx, pago, false, pago.getPayAmt(), isVendor, trxName);
                            }
                            else{
                                FinancialUtils.setEstadoCtaPago(ctx, pago, true, pago.getPayAmt().add(amtAnticipo), isVendor, trxName);
                            }

                            // Impacto parte deudora para pagos y acreedora para cobros por monto anticipos (si es mayor a cero)
                            if (amtAnticipo.compareTo(Env.ZERO) > 0){
                                FinancialUtils.setEstadoCtaPago(ctx, pago, true, amtAnticipo, !isVendor, trxName);
                            }
                        }
                    }
                }

                // Si no es un anticipo
                if (!pago.isAnticipo()){

                    // Obtengo lineas del documento, impacto en estado de cuenta
                    List<MZPagoLin> pagoLinList = pago.getSelectedLines();
                    for (MZPagoLin pagoLin: pagoLinList){

                        // Afecto estado de cuenta para invoice
                        if (pagoLin.getC_Invoice_ID() > 0){
                            if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
                                action = " update z_estadocuenta set z_pago_to_id =" + pago.get_ID() + ", " +
                                        " daterefpago ='" + pago.getDateDoc() + "' " +
                                        " where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
                            }
                            else{
                                action = " update z_estadocuenta set z_pago_to_id =" + pago.get_ID() + ", " +
                                        " daterefpago ='" + pago.getDateDoc() + "' " +
                                        " where c_invoice_id =" + pagoLin.getC_Invoice_ID();
                            }
                            DB.executeUpdateEx(action, trxName);
                        }
                        // Afecto estado de cuenta para transferencia de saldo
                        else if (pagoLin.getZ_TransferSaldo_ID() > 0){

                            action = " update z_estadocuenta set z_pago_to_id =" + pago.get_ID() + ", " +
                                    " daterefpago ='" + pago.getDateDoc() + "' " +
                                    " where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
                            DB.executeUpdateEx(action, trxName);
                        }
                        // Afecto estado de cuenta de esta anticipo
                        else if (pagoLin.getRef_Pago_ID() > 0){

                            action = " update z_estadocuenta set z_pago_to_id =" + pago.get_ID() + ", " +
                                    " daterefpago ='" + pago.getDateDoc() + "' " +
                                    " where z_pago_id =" + pagoLin.getRef_Pago_ID();
                            DB.executeUpdateEx(action, trxName);
                        }
                    }

                    if (!pago.isSOTrx()){

                        // Impacto en ordenes de pago asociadas
                        List<MZPagoOrdenPago> ordenPagoList = pago.getOrdenesPagoReferenciadas();
                        for (MZPagoOrdenPago pagoOrdenPago: ordenPagoList){
                            // Elimino la orden de pago del estado de cuenta, ya que ahora tengo un recibo.
                            action = " delete from z_estadocuenta where z_ordenpago_id =" + pagoOrdenPago.getZ_OrdenPago_ID();
                            DB.executeUpdateEx(action, trxName);
                        }

                        /*
                        // Impacto en anticipos directos asociados
                        List<MZPago> anticipoDirList = pago.getAnticiposDirReferenciados();
                        for (MZPago anticipoDir: anticipoDirList){
                            // Elimino este anticipo directo del estado de cuenta, ya que ahora tengo un recibo.
                            action = " delete from z_estadocuenta where z_pago_id =" + anticipoDir.get_ID();
                            DB.executeUpdateEx(action, trxName);
                        }
                        */

                        // Impacto en resguardos asociados
                        List<MZPagoResguardo> pagoResguardoList = pago.getResguardos();
                        for (MZPagoResguardo pagoResguardo: pagoResguardoList){
                            if (pagoResguardo.isSelected()){
                                action = " update z_estadocuenta set z_pago_to_id =" + pago.get_ID() + ", " +
                                        " daterefpago ='" + pago.getDateDoc() + "' " +
                                        " where z_resguardosocio_id =" + pagoResguardo.getZ_ResguardoSocio_ID();
                                DB.executeUpdateEx(action, trxName);
                            }
                        }
                    }
                }
            }
            else {  // Reactivate o Void del documento

                // Desasocio info en estado de cuenta para cualquier documento que haga referencia a esta pago/cobro
                action = " update z_estadocuenta set z_pago_to_id = null, daterefpago = null " +
                        " where z_pago_to_id =" + pago.get_ID();
                DB.executeUpdateEx(action, trxName);

                // Elimino info en estado de cuenta para este documento de pago/cobro
                action = " delete from z_estadocuenta where z_pago_id =" + pago.get_ID();
                DB.executeUpdateEx(action, trxName);

                if (!pago.isAnticipo()){
                    if (!pago.isSOTrx()){
                        // Impactos en ordenes de pago
                        List<MZPagoOrdenPago> ordenPagoList = pago.getOrdenesPagoReferenciadas();
                        for (MZPagoOrdenPago pagoOrdenPago: ordenPagoList){
                            MZOrdenPago ordenPago = (MZOrdenPago) pagoOrdenPago.getZ_OrdenPago();
                            FinancialUtils.setEstadoCtaOrdenPago(ctx, ordenPago, true, trxName);
                        }

                        /*
                        // Impacto en anticipos directos asociados
                        List<MZPago> anticipoDirList = pago.getAnticiposDirReferenciados();
                        for (MZPago anticipoDir: anticipoDirList){
                            FinancialUtils.setEstadoCtaPago(ctx, anticipoDir, true, trxName);
                        }
                        */
                    }
                }

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Método que impacta información de Pago / Cobro / Anticipo en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param pago
     * @param amt
     * @param isVendor
     * @param trxName
     */
    private static void setEstadoCtaPago(Properties ctx, MZPago pago, Boolean considerarAmt, BigDecimal amt, boolean isVendor, String trxName){

        try{

            MDocType docType = (MDocType) pago.getC_DocType();
            MBPartner partner = (MBPartner) pago.getC_BPartner();

            // Hago tantos impactos de este pago como monedas tengan sus lineas
            HashMap<Integer, InfoMultiCurrency> hashPagosMoneda = new HashMap<Integer, InfoMultiCurrency>();
            List<MZPagoLin> pagoLinList = pago.getSelectedLines();
            for (MZPagoLin pagoLin: pagoLinList){
                // Sumarizo por moneda
                if (!hashPagosMoneda.containsKey(pagoLin.getC_Currency_ID())){
                    hashPagosMoneda.put(pagoLin.getC_Currency_ID(), new InfoMultiCurrency());
                    hashPagosMoneda.get(pagoLin.getC_Currency_ID()).cuurencyID = pagoLin.getC_Currency_ID();
                }
                hashPagosMoneda.get(pagoLin.getC_Currency_ID()).amtSource = hashPagosMoneda.get(pagoLin.getC_Currency_ID()).amtSource.add(pagoLin.getAmtAllocation());
                hashPagosMoneda.get(pagoLin.getC_Currency_ID()).amtAcct = hashPagosMoneda.get(pagoLin.getC_Currency_ID()).amtAcct.add(pagoLin.getAmtAllocationMT());
            }

            // Si no tengo lineas de pago porque estoy en un anticipo, obtengo las monedas de los medios de pago
            if (hashPagosMoneda.size() <= 0){
                List<MZPagoMedioPago> pagoMedioPagoList = pago.getMediosPago();
                for (MZPagoMedioPago pagoMedioPago: pagoMedioPagoList){
                    // Sumarizo por moneda
                    if (!hashPagosMoneda.containsKey(pagoMedioPago.getC_Currency_ID())){
                        hashPagosMoneda.put(pagoMedioPago.getC_Currency_ID(), new InfoMultiCurrency());
                        hashPagosMoneda.get(pagoMedioPago.getC_Currency_ID()).cuurencyID = pagoMedioPago.getC_Currency_ID();
                    }
                    hashPagosMoneda.get(pagoMedioPago.getC_Currency_ID()).amtSource = hashPagosMoneda.get(pagoMedioPago.getC_Currency_ID()).amtSource.add(pagoMedioPago.getTotalAmt());
                    hashPagosMoneda.get(pagoMedioPago.getC_Currency_ID()).amtAcct = hashPagosMoneda.get(pagoMedioPago.getC_Currency_ID()).amtAcct.add(pagoMedioPago.getTotalAmtMT());
                }
            }

            for (HashMap.Entry<Integer, InfoMultiCurrency> entry : hashPagosMoneda.entrySet()){

                // Impacto documento en estado de cuenta para esta moneda
                MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(ctx, 0, trxName);
                estadoCuenta.setZ_Pago_ID(pago.get_ID());
                estadoCuenta.setAD_Table_ID(pago.get_Table_ID());
                estadoCuenta.setC_BPartner_ID(pago.getC_BPartner_ID());

                if (considerarAmt){
                    estadoCuenta.setC_Currency_ID(pago.getC_Currency_ID());
                }
                else{
                    //estadoCuenta.setC_Currency_ID(pago.getC_Currency_ID());
                    estadoCuenta.setC_Currency_ID(entry.getValue().cuurencyID);
                }

                estadoCuenta.setC_DocType_ID(pago.getC_DocType_ID());
                estadoCuenta.setDateDoc(pago.getDateDoc());
                estadoCuenta.setDateAcct(pago.getDateDoc());
                estadoCuenta.setDocBaseType(docType.getDocBaseType());
                if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                    estadoCuenta.setDocumentNoRef(pago.getNroRecibo());
                }
                else{
                    estadoCuenta.setDocumentNoRef(pago.getDocumentNo());
                }

                estadoCuenta.setIsSOTrx(pago.isSOTrx());

                // Si es Recibo de Proveedor
                if (!pago.isSOTrx()){
                    // Parte Acreedora
                    if (isVendor){
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);
                        if ((!pago.isAnticipo()) || (pago.isAnticipoDirecto())){
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt);
                                }
                                else{
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt.negate());
                                }
                            }
                            else{
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource);
                                }
                                else {
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                        else{
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt);
                                }
                                else {
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt.negate());
                                }
                            }
                            else {
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                                }
                                else {
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                    }
                    else{  // Parte Deudora
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);
                        if ((!pago.isAnticipo()) || (pago.isAnticipoDirecto())){
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt);
                                }
                                else{
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt.negate());
                                }
                            }
                            else{
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                                }
                                else {
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                        else{
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt);
                                }
                                else {
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt.negate());
                                }
                            }
                            else{
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource);
                                }
                                else {
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                    }
                }
                else{  // Es Recibo de Cobro

                    // Parte Deudora
                    if (!isVendor){
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);
                        if (!pago.isAnticipo()){
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt);
                                }
                                else {
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt.negate());
                                }
                            }
                            else{
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                                }
                                else{
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                        else{
                            // Si es recibo de cobro marcado como anticipo, y es de un empleado
                            if (partner.isEmployee()){
                                if (considerarAmt){
                                    if (amt.compareTo(Env.ZERO) >= 0){
                                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                                        estadoCuenta.setAmtSourceCr(amt);
                                    }
                                    else{
                                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                                        estadoCuenta.setAmtSourceDr(amt.negate());
                                    }
                                }
                                else{
                                    if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                                        estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                                    }
                                    else{
                                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                                        estadoCuenta.setAmtSourceDr(entry.getValue().amtSource.negate());
                                    }
                                }
                            }
                            else{
                                if (considerarAmt){
                                    if (amt.compareTo(Env.ZERO) >= 0){
                                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                                        estadoCuenta.setAmtSourceDr(amt);
                                    }
                                    else{
                                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                                        estadoCuenta.setAmtSourceCr(amt.negate());
                                    }
                                }
                                else{
                                    if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                        estadoCuenta.setAmtSourceCr(Env.ZERO);
                                        estadoCuenta.setAmtSourceDr(entry.getValue().amtSource);
                                    }
                                    else{
                                        estadoCuenta.setAmtSourceDr(Env.ZERO);
                                        estadoCuenta.setAmtSourceCr(entry.getValue().amtSource.negate());
                                    }
                                }

                            }
                        }
                    }
                    else{  // Parte Acreedora
                        estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);
                        if (!pago.isAnticipo()){
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt);
                                }
                                else {
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt.negate());
                                }
                            }
                            else{
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource);
                                }
                                else {
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                        else{
                            if (considerarAmt){
                                if (amt.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(amt);
                                }
                                else {
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(amt.negate());
                                }
                            }
                            else {
                                if (entry.getValue().amtSource.compareTo(Env.ZERO) >= 0){
                                    estadoCuenta.setAmtSourceDr(Env.ZERO);
                                    estadoCuenta.setAmtSourceCr(entry.getValue().amtSource);
                                }
                                else{
                                    estadoCuenta.setAmtSourceCr(Env.ZERO);
                                    estadoCuenta.setAmtSourceDr(entry.getValue().amtSource.negate());
                                }
                            }
                        }
                    }
                }

                estadoCuenta.setRecord_ID(pago.get_ID());
                estadoCuenta.setAD_Org_ID(pago.getAD_Org_ID());
                estadoCuenta.setDescription(pago.getDescription());

                if (!pago.isSOTrx()){
                    List<MZPagoOrdenPago> ordenPagoList = pago.getOrdenesPagoReferenciadas();
                    if (ordenPagoList.size() > 0){
                        estadoCuenta.setZ_OrdenPago_To_ID(ordenPagoList.get(0).getZ_OrdenPago_ID());
                        estadoCuenta.setDateRefOrdenPago(ordenPagoList.get(0).getDateTrx());
                    }
                }

                estadoCuenta.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    /***
     * Método que impacta información de Orden de Pago en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param ordenPago
     * @param onComplete
     * @param trxName
     */
    public static void setEstadoCtaOrdenPago(Properties ctx, MZOrdenPago ordenPago, boolean onComplete, String trxName){

        String action = "";

        try{

            if (onComplete){

                // Si es una orden para pago de anticipo
                if (ordenPago.isOrdPagoAnticipo()){
                    // Impacto solamente parte acreedora
                    FinancialUtils.setEstadoCtaOrdenPago(ctx, ordenPago, ordenPago.getTotalAmt(), true, trxName);
                }
                else{  // Es una orden de pago normal de afectacion de multiples documentos

                    BigDecimal amtAnticipo = ordenPago.getAmtAnticipo();
                    if (amtAnticipo == null) amtAnticipo = Env.ZERO;

                    // Impacto parte acreedora por monto total menos anticipos
                    FinancialUtils.setEstadoCtaOrdenPago(ctx, ordenPago, ordenPago.getTotalAmt().add(amtAnticipo), true, trxName);

                    // Impacto parte deudora por monto anticipos (si es mayor a cero)
                    if (amtAnticipo.compareTo(Env.ZERO) > 0){
                        FinancialUtils.setEstadoCtaOrdenPago(ctx, ordenPago, amtAnticipo, false , trxName);
                    }
                }

                // Si es una orden normal
                if (!ordenPago.isOrdPagoAnticipo()){

                    // Impacto invoices
                    List<MZOrdenPagoLin> pagoLinInvList = ordenPago.getInvoices();
                    for (MZOrdenPagoLin pagoLin: pagoLinInvList){
                        if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
                            action = " update z_estadocuenta set z_ordenpago_to_id =" + ordenPago.get_ID() + ", " +
                                    " daterefordenpago ='" + ordenPago.getDateDoc() + "' " +
                                    " where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
                        }
                        else{
                            action = " update z_estadocuenta set z_ordenpago_to_id =" + ordenPago.get_ID() + ", " +
                                    " daterefordenpago ='" + ordenPago.getDateDoc() + "' " +
                                    " where c_invoice_id =" + pagoLin.getC_Invoice_ID();
                        }
                        DB.executeUpdateEx(action, trxName);
                    }

                    // Impacto transferencias de saldo
                    List<MZOrdenPagoLin> pagoLinTransferList = ordenPago.getTransferSaldos();
                    for (MZOrdenPagoLin pagoLin: pagoLinTransferList){
                        action = " update z_estadocuenta set z_ordenpago_to_id =" + ordenPago.get_ID() + ", " +
                                " daterefordenpago ='" + ordenPago.getDateDoc() + "' " +
                                " where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
                        DB.executeUpdateEx(action, trxName);
                    }

                    // Impacto resguardos
                    List<MZOrdenPagoLin> pagoLinResgList = ordenPago.getResguardos();
                    for (MZOrdenPagoLin pagoLin: pagoLinResgList){
                        action = " update z_estadocuenta set z_ordenpago_to_id =" + ordenPago.get_ID() + ", " +
                                " daterefordenpago ='" + ordenPago.getDateDoc() + "' " +
                                " where z_resguardosocio_id =" + pagoLin.getZ_ResguardoSocio_ID();
                        DB.executeUpdateEx(action, trxName);
                    }

                }

                // Impacto anticipos
                List<MZOrdenPagoLin> pagoLinAnticipoList = ordenPago.getAnticipos();
                for (MZOrdenPagoLin pagoLin: pagoLinAnticipoList){
                    action = " update z_estadocuenta set z_ordenpago_to_id =" + ordenPago.get_ID() + ", " +
                            " daterefordenpago ='" + ordenPago.getDateDoc() + "' " +
                            " where z_pago_id =" + pagoLin.getZ_Pago_ID();
                    DB.executeUpdateEx(action, trxName);
                }
            }
            else {

                // Anulo orden de pago del estado de cuenta del socio de negocio
                action = " delete from z_estadocuenta where z_ordenpago_id =" + ordenPago.get_ID();
                DB.executeUpdateEx(action, trxName);

                // Impacto invoices
                List<MZOrdenPagoLin> pagoLinInvList = ordenPago.getInvoices();
                for (MZOrdenPagoLin pagoLin: pagoLinInvList){
                    if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
                        action = " update z_estadocuenta set z_ordenpago_to_id = null, daterefordenpago = null " +
                                " where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
                    }
                    else{
                        action = " update z_estadocuenta set z_ordenpago_to_id = null, daterefordenpago = null " +
                                " where c_invoice_id =" + pagoLin.getC_Invoice_ID();
                    }
                    DB.executeUpdateEx(action, trxName);
                }

                // Impacto transferencias de saldo
                List<MZOrdenPagoLin> pagoLinTransferList = ordenPago.getTransferSaldos();
                for (MZOrdenPagoLin pagoLin: pagoLinTransferList){
                    action = " update z_estadocuenta set z_ordenpago_to_id = null, daterefordenpago = null " +
                            " where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
                    DB.executeUpdateEx(action, trxName);
                }

                // Impacto resguardos
                List<MZOrdenPagoLin> pagoLinResgList = ordenPago.getResguardos();
                for (MZOrdenPagoLin pagoLin: pagoLinResgList){
                    action = " update z_estadocuenta set z_ordenpago_to_id = null, daterefordenpago = null " +
                            " where z_resguardosocio_id =" + pagoLin.getZ_ResguardoSocio_ID();
                    DB.executeUpdateEx(action, trxName);
                }

                // Impacto anticipos
                List<MZOrdenPagoLin> pagoLinAnticipoList = ordenPago.getAnticipos();
                for (MZOrdenPagoLin pagoLin: pagoLinAnticipoList){
                    action = " update z_estadocuenta set z_ordenpago_to_id = null, daterefordenpago = null " +
                            " where z_pago_id =" + pagoLin.getZ_Pago_ID();
                    DB.executeUpdateEx(action, trxName);
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Método que impacta información de Orden de Pago en estado de cuenta.
     * Xpande. Created by Gabriel Vila on 6/29/19.
     * @param ctx
     * @param ordenPago
     * @param amt
     * @param isVendor
     * @param trxName
     */
    private static void setEstadoCtaOrdenPago(Properties ctx, MZOrdenPago ordenPago, BigDecimal amt, boolean isVendor, String trxName){

        try{

            MDocType docType = (MDocType) ordenPago.getC_DocType();

            // Impacto documento en estado de cuenta
            MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(ctx, 0, trxName);
            estadoCuenta.setZ_OrdenPago_ID(ordenPago.get_ID());
            estadoCuenta.setAD_Table_ID(ordenPago.get_Table_ID());

            if (isVendor){
                estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_PROVEEDORES);
                estadoCuenta.setAmtSourceCr(Env.ZERO);
                estadoCuenta.setAmtSourceDr(amt);
            }
            else{
                estadoCuenta.setTipoSocioNegocio(X_Z_EstadoCuenta.TIPOSOCIONEGOCIO_CLIENTES);
                estadoCuenta.setAmtSourceDr(Env.ZERO);
                estadoCuenta.setAmtSourceCr(amt);
            }

            estadoCuenta.setC_BPartner_ID(ordenPago.getC_BPartner_ID());
            estadoCuenta.setC_Currency_ID(ordenPago.getC_Currency_ID());
            estadoCuenta.setC_DocType_ID(ordenPago.getC_DocType_ID());
            estadoCuenta.setDateDoc(ordenPago.getDateDoc());
            estadoCuenta.setDateAcct(ordenPago.getDateDoc());
            estadoCuenta.setDocBaseType(docType.getDocBaseType());
            estadoCuenta.setDocumentNoRef(ordenPago.getDocumentNo());
            estadoCuenta.setIsSOTrx(false);
            estadoCuenta.setRecord_ID(ordenPago.get_ID());
            estadoCuenta.setAD_Org_ID(ordenPago.getAD_Org_ID());
            estadoCuenta.setDescription(ordenPago.getDescription());

            estadoCuenta.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene término de pago por defecto sin importar la compañia.
     * Xpande. Created by Gabriel Vila on 10/10/19.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MPaymentTerm getPaymentTermByDefault(Properties ctx , String trxName) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(I_C_PaymentTerm.COLUMNNAME_IsDefault).append("=?");

        return new Query(ctx, I_C_PaymentTerm.Table_Name, whereClause.toString(), trxName)
                .setOnlyActiveRecords(true)
                .setParameters(true)
                .first();
    }

}
