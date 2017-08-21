package org.xpande.financial.callout;

import org.compiere.model.*;

import java.util.Properties;

/**
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

}
