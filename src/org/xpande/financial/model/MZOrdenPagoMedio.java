package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Medio de pago utilizado en una orden de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZOrdenPagoMedio extends X_Z_OrdenPagoMedio {

    public MZOrdenPagoMedio(Properties ctx, int Z_OrdenPagoMedio_ID, String trxName) {
        super(ctx, Z_OrdenPagoMedio_ID, trxName);
    }

    public MZOrdenPagoMedio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
