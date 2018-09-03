package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para parametrización contable de Retenciones de Socios de Negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/28/18.
 */
public class MZRetencionSocioAcct extends X_Z_RetencionSocio_Acct {

    public MZRetencionSocioAcct(Properties ctx, int Z_RetencionSocio_Acct_ID, String trxName) {
        super(ctx, Z_RetencionSocio_Acct_ID, trxName);
    }

    public MZRetencionSocioAcct(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
