package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de retención aplicada en una Emisión de Resguardo a Socio de Negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/2/17.
 */
public class MZResguardoSocioRet extends X_Z_ResguardoSocioRet {

    public MZResguardoSocioRet(Properties ctx, int Z_ResguardoSocioRet_ID, String trxName) {
        super(ctx, Z_ResguardoSocioRet_ID, trxName);
    }

    public MZResguardoSocioRet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
