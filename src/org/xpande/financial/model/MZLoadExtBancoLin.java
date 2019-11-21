package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para líneas de documento de carga de extractos bancarios.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/15/19.
 */
public class MZLoadExtBancoLin extends X_Z_LoadExtBancoLin {

    public MZLoadExtBancoLin(Properties ctx, int Z_LoadExtBancoLin_ID, String trxName) {
        super(ctx, Z_LoadExtBancoLin_ID, trxName);
    }

    public MZLoadExtBancoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeDelete() {
        log.saveError("ATENCIÓN", "No es posible Eliminar una linea de carga de extracto. Si no quiere considerarla debe desmarcar el check de Procesar.");
        return false;
    }
}
