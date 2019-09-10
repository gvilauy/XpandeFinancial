package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para identificadores asociados a medios de pago en modulo financiero.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/10/19.
 */
public class MZMedioPagoIdent extends X_Z_MedioPagoIdent {

    public MZMedioPagoIdent(Properties ctx, int Z_MedioPagoIdent_ID, String trxName) {
        super(ctx, Z_MedioPagoIdent_ID, trxName);
    }

    public MZMedioPagoIdent(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
