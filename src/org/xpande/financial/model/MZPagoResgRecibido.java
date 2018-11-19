package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para información de resguardos recibidos de Clientes.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/19/18.
 */
public class MZPagoResgRecibido extends X_Z_PagoResgRecibido {

    public MZPagoResgRecibido(Properties ctx, int Z_PagoResgRecibido_ID, String trxName) {
        super(ctx, Z_PagoResgRecibido_ID, trxName);
    }

    public MZPagoResgRecibido(Properties ctx, ResultSet rs, String trxName) {
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
                this.setAmtAllocationMT(this.getAmtAllocation());
            }
            else{
                if (this.getC_Currency_ID() != schema.getC_Currency_ID()){
                    this.setAmtAllocationMT(this.getAmtAllocation().multiply(this.getMultiplyRate()).setScale(2, RoundingMode.HALF_UP));
                }
                else{
                    this.setAmtAllocationMT(this.getAmtAllocation().divide(this.getMultiplyRate(), 2, RoundingMode.HALF_UP));
                }
            }
        }

        // Cuando no es nuevo registro, y modifican determinado campos, debo recalcular el monto MT de este medio de pago.
        if (!newRecord){

            // Si se modifica el monto a pagar o la tasa de cambio, debo recalcular el monto a pagar en moneda de la transacción.
            if ((is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt))){
                if (this.getMultiplyRate().compareTo(Env.ONE) == 0){
                    this.setAmtAllocationMT(this.getAmtAllocation());
                }
                else{
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
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if ((newRecord) || (is_ValueChanged(X_Z_PagoResgRecibido.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoResgRecibido.COLUMNNAME_AmtAllocation))){

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
