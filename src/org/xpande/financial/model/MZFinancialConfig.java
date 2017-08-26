package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para configuración de modulo Financiero del Core.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/25/17.
 */
public class MZFinancialConfig extends X_Z_FinancialConfig{

    public MZFinancialConfig(Properties ctx, int Z_FinancialConfig_ID, String trxName) {
        super(ctx, Z_FinancialConfig_ID, trxName);
    }

    public MZFinancialConfig(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene modelo único de configuración de modulo financiero.
     * Xpande. Created by Gabriel Vila on 8/22/17.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MZFinancialConfig getDefault(Properties ctx, String trxName){

        MZFinancialConfig model = new Query(ctx, I_Z_FinancialConfig.Table_Name, "", trxName).first();

        return model;
    }

}
