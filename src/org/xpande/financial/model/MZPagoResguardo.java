package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Resaguardos asociados a un pago a proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoResguardo extends X_Z_PagoResguardo {

    public MZPagoResguardo(Properties ctx, int Z_PagoResguardo_ID, String trxName) {
        super(ctx, Z_PagoResguardo_ID, trxName);
    }

    public MZPagoResguardo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
