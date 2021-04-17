package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/17/21.
 */
public class MZConciliaMPagoLin extends X_Z_ConciliaMPagoLin{

    public MZConciliaMPagoLin(Properties ctx, int Z_ConciliaMPagoLin_ID, String trxName) {
        super(ctx, Z_ConciliaMPagoLin_ID, trxName);
    }

    public MZConciliaMPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
