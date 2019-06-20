package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

        String message = this.validacionesCampos();
        if (message != null){
            log.saveError("ATENCIÓN", message);
            return false;
        }

        // Me aseguro de tener la organización correcta
        MZPago pago = (MZPago) this.getZ_Pago();
        this.setAD_Org_ID(pago.getAD_Org_ID());

        MAcctSchema schema = MClient.get(getCtx(), this.getAD_Client_ID()).getAcctSchema();

        // Cuando es nuevo registro, por callout obtuve el monto MT y tengo entonces que calcular alreves el monto en moneda de medio de pago
        if (newRecord){

            // Si no indico tasa de cambio al ingresar medio de pago por primera vez
            if ((this.getMultiplyRate() == null) || (this.getMultiplyRate().compareTo(Env.ZERO) <= 0)){
                // Obtengo moneda de pago a considerarse para esta moneda de medio de pago
                MZPagoMoneda pagoMoneda = MZPagoMoneda.setByCurrency(getCtx(), this.getZ_Pago_ID(), this.getC_Currency_ID(), get_TrxName());
                if ((pagoMoneda != null) && (pagoMoneda.get_ID() > 0)){
                    this.setMultiplyRate(pagoMoneda.getMultiplyRate());
                }
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

            if (!is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_Z_MedioPagoItem_ID)){
                if (this.getZ_OrdenPago_ID() > 0){
                    log.saveError("ATENCIÓN", "No es posible modificar esta linea ya que esta asociada a una Orden de Pago.");
                    return false;
                }
            }

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
    protected boolean beforeDelete() {

        String action  = "";

        try{

            if (this.getZ_OrdenPago_ID() > 0){
                log.saveError("ATENCIÓN", "No es posible eliminar esta linea ya que esta asociada a una Orden de Pago.\n" +
                        "Elimine la Orden de Pago de la grilla de Ordenes de Pago asociadas a este Recibo.");
                return false;
            }

            // Desasocio emision de medio de pago e item de medio de pago que se quitó de esta orden de pago.
            if (this.getZ_MedioPagoItem_ID() > 0){
                MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
                if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){

                    action = " update z_mediopagoitem set z_pago_id = null, entregado='N' where z_mediopagoitem_id =" + medioPagoItem.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());

                    if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
                        action = " update z_emisionmediopago set z_pago_id = null where z_emisionmediopago_id =" + medioPagoItem.getZ_EmisionMedioPago_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }


    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        String action = "";

        try{

            MZPago pago = (MZPago) this.getZ_Pago();

            if ((newRecord) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt))){

                // Actualizo totales del documento
                pago.updateTotals();
            }

            // Control de integridad de campos según comportamiento del medio de pago
            if (!pago.isSOTrx()){
                if (this.getZ_MedioPago_ID() > 0){
                    MZMedioPago medioPago = (MZMedioPago) this.getZ_MedioPago();

                    if ((!medioPago.isTieneCaja()) && (this.getC_CashBook_ID() > 0)){
                        action = " update z_pagomediopago set c_cashbook_id = null where z_pagomediopago_id =" + this.get_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }

                    if ((!medioPago.isTieneCtaBco()) && (this.getC_BankAccount_ID() > 0)){
                        action = " update z_pagomediopago set c_bankaccount_id = null where z_pagomediopago_id =" + this.get_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }

                    if ((!medioPago.isTieneFolio()) && (this.getZ_MedioPagoFolio_ID() > 0)){
                        action = " update z_pagomediopago set z_mediopagofolio_id = null where z_pagomediopago_id =" + this.get_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
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

    /***
     * Valida campos de este modelo.
     * Xpande. Created by Gabriel Vila on 3/8/18.
     * @return
     */
    private String validacionesCampos(){

        String message = null;

        try{

            // Si este medio de pago viene de una orden de pago, no necesito validaciones.
            if (this.getZ_OrdenPago_ID() > 0){
                return null;
            }

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Validaciones de campos obligatorios segun atributos obtenidos del medio de pago
            if (this.isTieneCtaBco()){
                if (this.getC_BankAccount_ID() <= 0){
                    return "Debe indicar Cuenta Bancaria del Medio de Pago";
                }
            }

            if (this.isTieneFecEmi()){
                if (this.getDateEmitted() == null){
                    return "Debe indicar Fecha de Emisión del Medio de Pago";
                }
                /*
                if (this.getDateEmitted().before(fechaHoy)){
                    if (this.isEmisionManual()){
                        return "La Fecha de Emisión del Medio de Pago no puede ser menor a la fecha actual";
                    }
                }
                */
            }

            if (this.isTieneFecVenc()){
                if (this.getDueDate() == null){
                    return "Debe indicar Fecha de Vencimiento del Medio de Pago";
                }
                /*
                if (this.getDueDate().before(fechaHoy)){
                    if (this.isEmisionManual()){
                        return "La Fecha de Vencimiento del Medio de Pago no puede ser menor a la fecha actual";
                    }
                }
                */
                if (this.getDateEmitted() != null){
                    if (this.getDueDate().before(this.getDateEmitted())){
                        return "La Fecha de Vencimiento del Medio de Pago no puede ser menor a la Fecha de Emisión del mismo";
                    }
                }
            }

            if (this.isTieneFolio()){
                if (this.getZ_MedioPagoFolio_ID() <= 0){
                    return "Debe indicar Libreta de Medios de Pago";
                }
                if (this.isEmisionManual()){
                    if (this.getZ_MedioPagoItem_ID() <= 0){
                        return "Debe indicar Número de Medio de Pago";
                    }
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

}
