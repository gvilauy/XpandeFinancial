package org.xpande.financial.callout;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.xpande.financial.model.*;

import java.util.Properties;

/**
 * Callout Financieros.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/21/17.
 */
public class CalloutFinancial extends CalloutEngine {

    /***
     * Setea moneda segun cuenta bancaria recibida.
     * Xpande. Created by Gabriel Vila on 7/31/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String currencyByBankAccount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int cBankAccountID = ((Integer) value).intValue();

        MBankAccount bankAccount = new MBankAccount(ctx, cBankAccountID, null);
        mTab.setValue("C_Currency_ID", bankAccount.getC_Currency_ID());

        return "";
    }

    /***
     * Setea moneda dewstino segun cuenta bancaria destino recibida.
     * Xpande. Created by Gabriel Vila on 11/25/20.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String currencyToByBankAccount(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int cBankAccountID = ((Integer) value).intValue();

        MBankAccount bankAccount = new MBankAccount(ctx, cBankAccountID, null);
        mTab.setValue("C_Currency_ID_To", bankAccount.getC_Currency_ID());

        return "";
    }


    /***
     * Setea DocBaseType según ID de documento recibido.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String docBaseTypeByDocType(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int cDocTypeID = ((Integer) value).intValue();

        MDocType docType = new MDocType(ctx, cDocTypeID, null);
        mTab.setValue("DocBaseType", docType.getDocBaseType());

        return "";
    }

    /***
     * Setea vencimiento manual en un documento, cuando el termino de pago recibido esta marcado como manual en la configuracion financiera.
     * Xpande. Created by Gabriel Vila on 8/25/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String vencManualByPaymentTerm(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int cPaymentTermID = ((Integer) value).intValue();

        // Marco vencimiento manual si este termino de pago esta configurado como termino de pago manual en configuración financiera
        MZFinancialConfig financialConfig = MZFinancialConfig.getDefault(ctx, null);
        if (financialConfig.getC_PaymentTerm_ID() == cPaymentTermID){
            mTab.setValue("VencimientoManual", true);
        }
        else{
            mTab.setValue("VencimientoManual", false);
        }

        return "";
    }


    /***
     * Dado un motivo de reemplazo de medio de pago, se setea campo de solicitar o no nuevas fechas de vencimiento.
     * Xpande. Created by Gabriel Vila on 9/21/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String solicitarVencByMotivo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int zMedioPagoMotivoRepID = ((Integer) value).intValue();

        MZMedioPagoMotivoRep motivoRep = new MZMedioPagoMotivoRep(ctx, zMedioPagoMotivoRepID, null);
        mTab.setValue(X_Z_MedioPagoMotivoRep.COLUMNNAME_SolicitaFecVenc, motivoRep.isSolicitaFecVenc());

        return "";
    }


    /***
     * Setea atributos asociados al medio de pago seleccionado en documento de Reemplazo de Medios de Pago.
     * Xpande. Created by Gabriel Vila on 6/12/19.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setMedioPagoInfoReemplazo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zMedioPagoID = (Integer) value;

        if (zMedioPagoID <= 0) return "";

        MZMedioPago medioPago = new MZMedioPago(ctx, zMedioPagoID, null);

        if ((medioPago != null) && (medioPago.get_ID() > 0)) {

            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneFecEmi, medioPago.isTieneFecEmi());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneFecVenc, medioPago.isTieneFecVenc());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneCtaBco, medioPago.isTieneCtaBco());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneCaja, medioPago.isTieneCaja());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneFolio, medioPago.isTieneFolio());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneBanco, medioPago.isTieneBanco());
            mTab.setValue(X_Z_MedioPagoReplaceDet.COLUMNNAME_TieneNroRef, medioPago.isTieneNroRef());
        }

        return "";
    }

    /***
     * Setea atributos asociados al item de un medio de pago seleccionado en documentos de Pago / Orden de Pago.
     * Xpande. Created by Gabriel Vila on 10/8/20.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setMedioPagoItemInfoPago(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zMedioPagoItemID = (Integer) value;

        if (zMedioPagoItemID <= 0) return "";

        MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(ctx, zMedioPagoItemID, null);

        if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)) {

            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DateEmitted, medioPagoItem.getDateEmitted());
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DueDate, medioPagoItem.getDueDate());
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt, medioPagoItem.getTotalAmt());

        }

        return "";
    }

}
