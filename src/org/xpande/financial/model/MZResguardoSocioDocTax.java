package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para importe de impuesto de comprobante a considerar en Emisión de Resguardos.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/2/17.
 */
public class MZResguardoSocioDocTax extends X_Z_ResguardoSocioDocTax {

    public MZResguardoSocioDocTax(Properties ctx, int Z_ResguardoSocioDocTax_ID, String trxName) {
        super(ctx, Z_ResguardoSocioDocTax_ID, trxName);
    }

    public MZResguardoSocioDocTax(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
