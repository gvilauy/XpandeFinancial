package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de configuracion de monedas para carga automatica de tasas de cambio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/3/17.
 */
public class MZFinancialConfigTC extends X_Z_FinancialConfigTC {

    public MZFinancialConfigTC(Properties ctx, int Z_FinancialConfigTC_ID, String trxName) {
        super(ctx, Z_FinancialConfigTC_ID, trxName);
    }

    public MZFinancialConfigTC(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
