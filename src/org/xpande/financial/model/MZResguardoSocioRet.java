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

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro de tener la organización correcta
        if ((newRecord) || (is_ValueChanged(X_Z_ResguardoSocioRet.COLUMNNAME_AD_Org_ID))){
            MZResguardoSocio resguardoSocio = (MZResguardoSocio) this.getZ_ResguardoSocio();
            this.setAD_Org_ID(resguardoSocio.getAD_Org_ID());
        }

        return true;
    }

}
