package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para auditoría del proceso de reemplazo de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/27/17.
 */
public class MZMedioPagoReplaceAud extends X_Z_MedioPagoReplaceAud {

    public MZMedioPagoReplaceAud(Properties ctx, int Z_MedioPagoReplaceAud_ID, String trxName) {
        super(ctx, Z_MedioPagoReplaceAud_ID, trxName);
    }

    public MZMedioPagoReplaceAud(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
