package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea del proceso de generación de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZGeneraOrdenPagoLin extends X_Z_GeneraOrdenPagoLin {

    public MZGeneraOrdenPagoLin(Properties ctx, int Z_GeneraOrdenPagoLin_ID, String trxName) {
        super(ctx, Z_GeneraOrdenPagoLin_ID, trxName);
    }

    public MZGeneraOrdenPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if ((!is_ValueChanged(X_Z_GeneraOrdenPagoLin.COLUMNNAME_IsSelected))
            && (!is_ValueChanged(X_Z_GeneraOrdenPagoLin.COLUMNNAME_AmtAllocation))){
            return true;
        }

        String action = "";

        try{

            action = " update z_generaordenpagosocio set totalamt = " +
                        " (select sum(coalesce(amtallocation,0)) from z_generaordenpagolin " +
                        " where z_generaordenpagosocio_id =" + this.getZ_GeneraOrdenPagoSocio_ID() +
                        " and isselected ='Y') " +
                        " where z_generaordenpagosocio_id =" + this.getZ_GeneraOrdenPagoSocio_ID();

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }
}
