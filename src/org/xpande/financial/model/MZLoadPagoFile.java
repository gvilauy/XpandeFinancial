package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/10/20.
 */
public class MZLoadPagoFile extends X_Z_LoadPagoFile {

    public MZLoadPagoFile(Properties ctx, int Z_LoadPagoFile_ID, String trxName) {
        super(ctx, Z_LoadPagoFile_ID, trxName);
    }

    public MZLoadPagoFile(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
