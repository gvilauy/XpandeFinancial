package org.xpande.financial.model;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.Env;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Resaguardos asociados a un pago a proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoResguardo extends X_Z_PagoResguardo {

    public MZPagoResguardo(Properties ctx, int Z_PagoResguardo_ID, String trxName) {
        super(ctx, Z_PagoResguardo_ID, trxName);
    }

    public MZPagoResguardo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (!newRecord){

            if (this.getZ_OrdenPago_ID() > 0){
                log.saveError("ATENCIÓN", "No es posible modificar esta linea ya que esta asociada a una Orden de Pago.");
                return false;
            }

            // Si se modifica el monto a pagar o la tasa de cambio, debo recalcular el monto a pagar en moneda de la transacción.
            if ((is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_AmtAllocation))){
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


    @Override
    protected boolean beforeDelete() {

        if (this.getZ_OrdenPago_ID() > 0){
            log.saveError("ATENCIÓN", "No es posible eliminar esta linea ya que esta asociada a una Orden de Pago.\n" +
                    "Elimine la Orden de Pago de la grilla de Ordenes de Pago asociadas a este Recibo.");
            return false;
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if ((is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_AmtAllocation))
                || (is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_IsSelected))){

            // Actualizo totales del documento
            MZPago pago = (MZPago) this.getZ_Pago();
            pago.updateTotals();

        }

        if (newRecord){

            // Actualizo totales del documento
            MZPago pago = (MZPago) this.getZ_Pago();
            pago.updateTotals();

        }
        else if ((is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_AmtAllocation))
                || (is_ValueChanged(X_Z_PagoResguardo.COLUMNNAME_IsSelected))){

            // Actualizo totales del documento
            MZPago pago = (MZPago) this.getZ_Pago();
            pago.updateTotals();

        }

        return true;
    }

}
