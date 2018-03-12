package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de orden de pago asociada a un documento de pago a proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/12/18.
 */
public class MZPagoOrdenPago extends X_Z_PagoOrdenPago {

    public MZPagoOrdenPago(Properties ctx, int Z_PagoOrdenPago_ID, String trxName) {
        super(ctx, Z_PagoOrdenPago_ID, trxName);
    }

    public MZPagoOrdenPago(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
