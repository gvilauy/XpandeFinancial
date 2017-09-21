package org.xpande.financial.callout;

import org.compiere.model.*;
import org.xpande.financial.model.MZFinancialConfig;
import org.xpande.financial.model.MZMedioPagoMotivoRep;
import org.xpande.financial.model.X_Z_MedioPagoMotivoRep;

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


}
