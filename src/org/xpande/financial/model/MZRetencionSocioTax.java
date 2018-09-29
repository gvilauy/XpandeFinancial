package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de impuesto sobre el cual aplica una determinada Retención Financiera.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/1/17.
 */
public class MZRetencionSocioTax extends X_Z_RetencionSocioTax {

    public MZRetencionSocioTax(Properties ctx, int Z_RetencionSocioTax_ID, String trxName) {
        super(ctx, Z_RetencionSocioTax_ID, trxName);
    }

    public MZRetencionSocioTax(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
