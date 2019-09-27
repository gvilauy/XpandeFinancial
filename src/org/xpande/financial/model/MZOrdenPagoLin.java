package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZOrdenPagoLin extends X_Z_OrdenPagoLin {

    public MZOrdenPagoLin(Properties ctx, int Z_OrdenPagoLin_ID, String trxName) {
        super(ctx, Z_OrdenPagoLin_ID, trxName);
    }

    public MZOrdenPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return success;

        try{

            MZOrdenPago ordenPago = (MZOrdenPago) this.getZ_OrdenPago();
            ordenPago.updateTotals();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (!newRecord){
            // Si se modifica el monto a pagar o la tasa de cambio, debo recalcular el monto a pagar en moneda de la transacción.
            if ((is_ValueChanged(X_Z_PagoLin.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoLin.COLUMNNAME_AmtAllocation))){
                if (this.getMultiplyRate().compareTo(Env.ONE) == 0){
                    this.setAmtAllocationMT(this.getAmtAllocation());
                }
                else{
                    MAcctSchema schema = MClient.get(getCtx(), this.getAD_Client_ID()).getAcctSchema();
                    if (this.getC_Currency_ID() != schema.getC_Currency_ID()){
                        this.setAmtAllocationMT(this.getAmtAllocation().multiply(this.getMultiplyRate()).setScale(2, RoundingMode.HALF_UP));
                    }
                    else{
                        this.setAmtAllocationMT(this.getAmtAllocation().divide(this.getMultiplyRate(), 2, RoundingMode.HALF_UP));
                    }
                }
            }
        }

        return true;
    }
}
