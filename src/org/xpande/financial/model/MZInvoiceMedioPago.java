package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.MiniBrowser;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.CurrencyUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para ingreso de medios de pago en una invoice para afectación directa.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/21/20.
 */
public class MZInvoiceMedioPago extends X_Z_InvoiceMedioPago {

    public MZInvoiceMedioPago(Properties ctx, int Z_InvoiceMedioPago_ID, String trxName) {
        super(ctx, Z_InvoiceMedioPago_ID, trxName);
    }

    public MZInvoiceMedioPago(Properties ctx, ResultSet rs, String trxName) {
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
        MInvoice invoice = (MInvoice) this.getC_Invoice();
        this.setAD_Org_ID(invoice.getAD_Org_ID());

        MAcctSchema schema = MClient.get(getCtx(), this.getAD_Client_ID()).getAcctSchema();

        // Si tengo item de medio de pago y el mismo ya esta emitido, no puedo modificar datos de ese item
        if (this.getZ_MedioPagoItem_ID() > 0){
            MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
            if (medioPagoItem.isEmitido()){

                if (this.getDateEmitted() == null) this.setDateEmitted(medioPagoItem.getDateEmitted());
                if (this.getDueDate() == null) this.setDueDate(medioPagoItem.getDueDate());
                if ((this.getTotalAmt() == null) || (this.getTotalAmt().compareTo(Env.ZERO) <= 0)) this.setTotalAmt(medioPagoItem.getTotalAmt());

                if ((this.getDateEmitted() != null) && (this.getDueDate() != null) && (this.getTotalAmt() != null)){
                    if ((!this.getDateEmitted().equals(medioPagoItem.getDateEmitted())) || (!this.getDueDate().equals(medioPagoItem.getDueDate()))
                            || (this.getTotalAmt().compareTo(medioPagoItem.getTotalAmt()) != 0)){
                        log.saveError("ATENCIÓN", "No es posible modificar datos de este medio de pago ya que el mismo esta emitido.");
                        return false;
                    }
                }
            }
        }

        // Cuando es nuevo registro, por callout obtuve el monto MT y tengo entonces que calcular alreves el monto en moneda de medio de pago
        if (newRecord){

            // Si no indico tasa de cambio al ingresar medio de pago por primera vez
            if ((this.getMultiplyRate() == null) || (this.getMultiplyRate().compareTo(Env.ZERO) <= 0)){

                this.setMultiplyRate(Env.ONE);

                // Si moneda de la linea es diferente a la moneda de la invoice
                if (this.getC_Currency_ID() != invoice.getC_Currency_ID()){

                    BigDecimal multiplyRate = CurrencyUtils.getCurrencyRate(getCtx(), invoice.getAD_Client_ID(), 0,
                            this.getC_Currency_ID(), invoice.getC_Currency_ID(), 114, invoice.getDateInvoiced(), null);
                    if (multiplyRate == null){
                        log.saveError("ATENCIÓN", "No se pudo obtener Tasa de Cambio para esta moneda y Fecha del Documento.");
                        return false;
                    }

                    this.setMultiplyRate(multiplyRate);
                }
            }

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
    protected boolean beforeDelete() {

        String action  = "";

        try{
            MInvoice invoice = (MInvoice) this.getC_Invoice();

            // Desasocio emision de medio de pago e item de medio de pago que se quitó de esta orden de pago.
            if (this.getZ_MedioPagoItem_ID() > 0){
                MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
                if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){

                    // Si estoy en un documento de venta
                    if (invoice.isSOTrx()){
                        // Elimino directamente este medio de pago
                        medioPagoItem.deleteEx(true);
                    }
                    else{
                        if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
                            action = " update z_emisionmediopago set z_pago_id = null where z_emisionmediopago_id =" + medioPagoItem.getZ_EmisionMedioPago_ID();
                            DB.executeUpdateEx(action, get_TrxName());
                        }

                        action = " update z_mediopagoitem set z_pago_id = null, entregado='N' where z_mediopagoitem_id =" + medioPagoItem.get_ID();
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

        if (!success) return false;

        String action;

        try{
            MInvoice invoice = (MInvoice) this.getC_Invoice();

            if ((newRecord) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_MultiplyRate)) || (is_ValueChanged(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt))){

                // Actualizo total de medios de pago en el cabezal
                String sql = " select sum(coalesce(totalamtmt,0)) as total from z_invoicemediopago " +
                             " where c_invoice_id =" + invoice.get_ID();
                BigDecimal sumMedios = DB.getSQLValueBDEx(get_TrxName(), sql);
                if (sumMedios == null) sumMedios = Env.ZERO;

                action = " update c_invoice set totalmediospago =" + sumMedios + " where c_invoice_id =" + invoice.get_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }

            // Control de integridad de campos según comportamiento del medio de pago
            if (!invoice.isSOTrx()){
                if (this.getZ_MedioPago_ID() > 0){
                    MZMedioPago medioPago = (MZMedioPago) this.getZ_MedioPago();

                    if ((!medioPago.isTieneCaja()) && (this.getC_CashBook_ID() > 0)){
                        action = " update z_invoicemediopago set c_cashbook_id = null where z_invoicemediopago_id =" + this.get_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }

                    if ((!medioPago.isTieneCtaBco()) && (this.getC_BankAccount_ID() > 0)){
                        action = " update z_invoicemediopago set c_bankaccount_id = null where z_invoicemediopago_id =" + this.get_ID();
                        DB.executeUpdateEx(action, get_TrxName());
                    }

                    if ((!medioPago.isTieneFolio()) && (this.getZ_MedioPagoFolio_ID() > 0)){
                        action = " update z_invoicemediopago set z_mediopagofolio_id = null where z_invoicemediopago_id =" + this.get_ID();
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

        if (!success) return false;

        try{

            MInvoice invoice = (MInvoice) this.getC_Invoice();

            // Actualizo total de medios de pago en el cabezal
            String sql = " select sum(coalesce(totalamtmt,0)) as total from z_invoicemediopago " +
                    " where c_invoice_id =" + invoice.get_ID();
            BigDecimal sumMedios = DB.getSQLValueBDEx(get_TrxName(), sql);
            if (sumMedios == null) sumMedios = Env.ZERO;

            String action = " update c_invoice set totalmediospago =" + sumMedios + " where c_invoice_id =" + invoice.get_ID();
            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }

    /***
     * Valida campos de este modelo.
     * Xpande. Created by Gabriel Vila on 11/21/20.
     * @return
     */
    private String validacionesCampos(){

        try{
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
            }

            if (this.isTieneFecVenc()){
                if (this.getDueDate() == null){
                    return "Debe indicar Fecha de Vencimiento del Medio de Pago";
                }
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

            if (this.getTotalAmt() == null){
                return "Debe indicar importe para este medio de pago.";
            }

            if (this.getTotalAmt().compareTo(Env.ZERO) <= 0){

                // Si el medio de pago no acepta monto negativo, aviso.
                boolean isSOTrx = ((MInvoice) this.getC_Invoice()).isSOTrx();
                MZMedioPago medioPago = (MZMedioPago) this.getZ_MedioPago();
                if (!isSOTrx && !medioPago.isAceptaNegativo()){
                    return "Debe indicar importe mayor a cero para este medio de pago.";
                }
                else{
                    if (isSOTrx && !medioPago.isAceptaNegCobro()){
                        return "Debe indicar importe mayor a cero para este medio de pago.";
                    }
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return null;
    }

}
