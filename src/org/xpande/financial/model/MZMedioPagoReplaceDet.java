package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para detalle de nuevos medios de pago asociados a un reemplazo de medio de pago en modulo financiero.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/28/17.
 */
public class MZMedioPagoReplaceDet extends X_Z_MedioPagoReplaceDet {

    public MZMedioPagoReplaceDet(Properties ctx, int Z_MedioPagoReplaceDet_ID, String trxName) {
        super(ctx, Z_MedioPagoReplaceDet_ID, trxName);
    }

    public MZMedioPagoReplaceDet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
