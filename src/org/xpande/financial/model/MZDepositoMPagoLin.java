package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea de documento de deposito de medio de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/5/19.
 */
public class MZDepositoMPagoLin extends X_Z_DepositoMPagoLin {

    public MZDepositoMPagoLin(Properties ctx, int Z_DepositoMPagoLin_ID, String trxName) {
        super(ctx, Z_DepositoMPagoLin_ID, trxName);
    }

    public MZDepositoMPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro tener la organización seteada
        if (newRecord){
            MZDepositoMedioPago depositoMedioPago = (MZDepositoMedioPago) this.getZ_DepositoMedioPago();
            this.setAD_Org_ID(depositoMedioPago.getAD_Org_ID());
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        try{

            MZDepositoMedioPago depositoMedioPago = (MZDepositoMedioPago) this.getZ_DepositoMedioPago();

            if ((newRecord) || (is_ValueChanged(X_Z_DepositoMPagoLin.COLUMNNAME_TotalAmt))){

                // Actualizo totales del documento
                depositoMedioPago.updateTotals();
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
        MZDepositoMedioPago depositoMedioPago = (MZDepositoMedioPago) this.getZ_DepositoMedioPago();
        depositoMedioPago.updateTotals();

        return true;
    }
}
