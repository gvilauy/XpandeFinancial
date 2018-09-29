package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para motivos de reemplazo de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/21/17.
 */
public class MZMedioPagoMotivoRep extends X_Z_MedioPagoMotivoRep {

    public MZMedioPagoMotivoRep(Properties ctx, int Z_MedioPagoMotivoRep_ID, String trxName) {
        super(ctx, Z_MedioPagoMotivoRep_ID, trxName);
    }

    public MZMedioPagoMotivoRep(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
