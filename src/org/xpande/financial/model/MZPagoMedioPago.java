package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Medios de pago asociados a un pago/cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoMedioPago extends X_Z_PagoMedioPago {

    public MZPagoMedioPago(Properties ctx, int Z_PagoMedioPago_ID, String trxName) {
        super(ctx, Z_PagoMedioPago_ID, trxName);
    }

    public MZPagoMedioPago(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
