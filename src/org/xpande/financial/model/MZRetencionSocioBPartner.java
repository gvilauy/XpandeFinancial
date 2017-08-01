package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para asociación de retención financiera con un socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/1/17.
 */
public class MZRetencionSocioBPartner extends X_Z_RetencionSocioBPartner {

    public MZRetencionSocioBPartner(Properties ctx, int Z_RetencionSocioBPartner_ID, String trxName) {
        super(ctx, Z_RetencionSocioBPartner_ID, trxName);
    }

    public MZRetencionSocioBPartner(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
