package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para afectaciones hechas a Anticipos de pago / cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/24/19.
 */
public class MZPagoAfectacion extends X_Z_PagoAfectacion {

    public MZPagoAfectacion(Properties ctx, int Z_PagoAfectacion_ID, String trxName) {
        super(ctx, Z_PagoAfectacion_ID, trxName);
    }

    public MZPagoAfectacion(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
