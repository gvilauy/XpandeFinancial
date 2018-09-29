package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para gestionar estados de cuenta de socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/20/18.
 */
public class MZEstadoCuenta extends X_Z_EstadoCuenta {

    public MZEstadoCuenta(Properties ctx, int Z_EstadoCuenta_ID, String trxName) {
        super(ctx, Z_EstadoCuenta_ID, trxName);
    }

    public MZEstadoCuenta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
