package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea cargada desde archivo de interface para carga de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/2/19.
 */
public class MZLoadMedioPagoFile extends X_Z_LoadMedioPagoFile {

    public MZLoadMedioPagoFile(Properties ctx, int Z_LoadMedioPagoFile_ID, String trxName) {
        super(ctx, Z_LoadMedioPagoFile_ID, trxName);
    }

    public MZLoadMedioPagoFile(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
