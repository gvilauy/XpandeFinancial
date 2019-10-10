package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/10/19.
 */
public class MZMPagoIdentProd extends X_Z_MPagoIdentProd {

    public MZMPagoIdentProd(Properties ctx, int Z_MPagoIdentProd_ID, String trxName) {
        super(ctx, Z_MPagoIdentProd_ID, trxName);
    }

    public MZMPagoIdentProd(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
