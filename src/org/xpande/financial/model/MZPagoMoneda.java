package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Monedas de documentos afectados en un pago/cobro. Gestión multimoneda.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoMoneda extends X_Z_PagoMoneda {

    public MZPagoMoneda(Properties ctx, int Z_PagoMoneda_ID, String trxName) {
        super(ctx, Z_PagoMoneda_ID, trxName);
    }

    public MZPagoMoneda(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
