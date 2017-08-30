package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para importe de retencion asociado a un comprobante-retencion en proceso de resguardos a socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/30/17.
 */
public class MZResguardoSocioDocRet extends X_Z_ResguardoSocioDocRet {

    public MZResguardoSocioDocRet(Properties ctx, int Z_ResguardoSocioDocRet_ID, String trxName) {
        super(ctx, Z_ResguardoSocioDocRet_ID, trxName);
    }

    public MZResguardoSocioDocRet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
