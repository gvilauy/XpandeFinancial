package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/17/21.
 */
public class MZConciliaMPagoLin extends X_Z_ConciliaMPagoLin{

    public MZConciliaMPagoLin(Properties ctx, int Z_ConciliaMPagoLin_ID, String trxName) {
        super(ctx, Z_ConciliaMPagoLin_ID, trxName);
    }

    public MZConciliaMPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro tener la organización seteada
        if (newRecord){
            MZConciliaMedioPago conciliaMedioPago = (MZConciliaMedioPago) this.getZ_ConciliaMedioPago();
            this.setAD_Org_ID(conciliaMedioPago.getAD_Org_ID());
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        try{
            MZConciliaMedioPago conciliaMedioPago = (MZConciliaMedioPago) this.getZ_ConciliaMedioPago();

            if ((newRecord) || (is_ValueChanged(X_Z_ConciliaMPagoLin.COLUMNNAME_TotalAmt))){

                // Actualizo totales del documento
                conciliaMedioPago.updateTotals();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return false;

        // Actualizo totales del documento
        MZConciliaMedioPago conciliaMedioPago = (MZConciliaMedioPago) this.getZ_ConciliaMedioPago();
        conciliaMedioPago.updateTotals();

        return true;
    }
}
