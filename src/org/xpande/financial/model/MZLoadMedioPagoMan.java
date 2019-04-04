package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea cargada manualmente en el proceso de carga masiva de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/2/19.
 */
public class MZLoadMedioPagoMan extends X_Z_LoadMedioPagoMan {

    public MZLoadMedioPagoMan(Properties ctx, int Z_LoadMedioPagoMan_ID, String trxName) {
        super(ctx, Z_LoadMedioPagoMan_ID, trxName);
    }

    public MZLoadMedioPagoMan(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
