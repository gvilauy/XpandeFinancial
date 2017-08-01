package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de retencion para socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/1/17.
 */
public class MZRetencionSocio extends X_Z_RetencionSocio {

    public MZRetencionSocio(Properties ctx, int Z_RetencionSocio_ID, String trxName) {
        super(ctx, Z_RetencionSocio_ID, trxName);
    }

    public MZRetencionSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
