package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
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

            String sql = " select sum(coalesce(amtallocation,0)) from z_generaordenpagolin " +
                            " where z_generaordenpagosocio_id =" + this.getZ_GeneraOrdenPagoSocio_ID() +
                            " and isselected ='Y' ";
            BigDecimal amt = DB.getSQLValueBDEx(get_TrxName(), sql);
            if (amt == null) amt = Env.ZERO;

            action = " update z_generaordenpagosocio set totalamt =" + amt +
                        " where z_generaordenpagosocio_id =" + this.getZ_GeneraOrdenPagoSocio_ID();

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }
}
