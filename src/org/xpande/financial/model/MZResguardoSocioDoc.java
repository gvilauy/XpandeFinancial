package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para comprobante considerado en una Emisión de Resguardo a Socio de Negocio. *
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/2/17.
 */
public class MZResguardoSocioDoc extends X_Z_ResguardoSocioDoc {

    public MZResguardoSocioDoc(Properties ctx, int Z_ResguardoSocioDoc_ID, String trxName) {
        super(ctx, Z_ResguardoSocioDoc_ID, trxName);
    }

    public MZResguardoSocioDoc(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
