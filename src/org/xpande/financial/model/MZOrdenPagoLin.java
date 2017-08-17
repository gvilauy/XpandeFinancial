package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZOrdenPagoLin extends X_Z_OrdenPagoLin {

    public MZOrdenPagoLin(Properties ctx, int Z_OrdenPagoLin_ID, String trxName) {
        super(ctx, Z_OrdenPagoLin_ID, trxName);
    }

    public MZOrdenPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
