package org.xpande.financial.callout;

import org.compiere.model.*;
import org.xpande.financial.model.X_Z_ResguardoSocio;

import java.util.Properties;

/**
 * Callouts para resguardos en módulo financiero.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/9/17.
 */
public class CalloutResguardo extends CalloutEngine {


    /***
     * Al indicar documento en una emisión de resguardos, seteo si el mismo es un Contra Resguardo o no.
     * Xpande. Created by Gabriel Vila on 8/9/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String docType(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int cDocTypeID = ((Integer)value).intValue();

        MDocType docType = new MDocType(ctx, cDocTypeID, null);
        if (docType.getDocBaseType().equalsIgnoreCase("RGC")){
            mTab.setValue(X_Z_ResguardoSocio.COLUMNNAME_EsContraResguardo, true);
        }
        else{
            mTab.setValue(X_Z_ResguardoSocio.COLUMNNAME_EsContraResguardo, false);
        }

        return "";
    }


}
