package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas del proceso de Reemplazo de Medios de Pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/21/17.
 */
public class MZMedioPagoReplaceLin extends X_Z_MedioPagoReplaceLin {

    public MZMedioPagoReplaceLin(Properties ctx, int Z_MedioPagoReplaceLin_ID, String trxName) {
        super(ctx, Z_MedioPagoReplaceLin_ID, trxName);
    }

    public MZMedioPagoReplaceLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
