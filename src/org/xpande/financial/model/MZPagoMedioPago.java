package org.xpande.financial.model;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.Env;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Medios de pago asociados a un pago/cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoMedioPago extends X_Z_PagoMedioPago {

    public MZPagoMedioPago(Properties ctx, int Z_PagoMedioPago_ID, String trxName) {
        super(ctx, Z_PagoMedioPago_ID, trxName);
    }

    public MZPagoMedioPago(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    @Override
    protected boolean beforeSave(boolean newRecord) {

        MAcctSchema schema = MClient.get(getCtx(), this.getAD_Client_ID()).getAcctSchema();

        // Cuando es nuevo registro, por callout obtuve el monto MT y tengo entonces que calcular alreves el monto en moneda de medio de pago
        if (newRecord){

            // Obtengo moneda de pago a considerarse para esta moneda de medio de pago
            MZPagoMoneda pagoMoneda = MZPagoMoneda.setByCurrency(getCtx(), this.getZ_Pago_ID(), this.getC_Currency_ID(), get_TrxName());
            if ((pagoMoneda != null) && (pagoMoneda.get_ID() > 0)){
                this.setMultiplyRate(pagoMoneda.getMultiplyRate());
            }

            if (this.getMultiplyRate().compareTo(Env.ONE) == 0){
                this.setTotalAmt(this.getTotalAmtMT());
            }
            else{
                if (this.getC_Currency_ID() != schema.getC_Currency_ID()){
                    this.setTotalAmt(this.getTotalAmtMT().divide(this.getMultiplyRate(), 2, RoundingMode.HALF_UP));
                }
                else{
                    this.setTotalAmt(this.getTotalAmtMT().multiply(this.getMultiplyRate()).setScale(2, RoundingMode.HALF_UP));
                }
            }

        }

        // Cuando no es nuevo registro, y modifican determinado campos, debo recalcular el monto MT de este medio de pago.
        if (!newRecord){

            // Si se modifica el monto a pagar o la tasa de cambio, debo recalcular el monto a pagar en moneda de la transacción.
            if ((is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt))){
                if (this.getMultiplyRate().compareTo(Env.ONE) == 0){
                    this.setTotalAmtMT(this.getTotalAmt());
                }
                else{
                    if (this.getC_Currency_ID() != schema.getC_Currency_ID()){
                        this.setTotalAmtMT(this.getTotalAmt().multiply(this.getMultiplyRate()).setScale(2, RoundingMode.HALF_UP));
                    }
                    else{
                        this.setTotalAmtMT(this.getTotalAmt().divide(this.getMultiplyRate(), 2, RoundingMode.HALF_UP));
                    }
                }
            }
        }

        return true;
    }


    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if ((newRecord) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt))){

            // Actualizo totales del documento
            MZPago pago = (MZPago) this.getZ_Pago();
            pago.updateTotals();
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return success;

        // Actualizo totales del documento
        MZPago pago = (MZPago) this.getZ_Pago();
        pago.updateTotals();

        return true;
    }
}
