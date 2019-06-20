package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Medio de pago utilizado en una orden de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZOrdenPagoMedio extends X_Z_OrdenPagoMedio {

    public MZOrdenPagoMedio(Properties ctx, int Z_OrdenPagoMedio_ID, String trxName) {
        super(ctx, Z_OrdenPagoMedio_ID, trxName);
    }

    public MZOrdenPagoMedio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro de tener ID de generador cuando se ingresa un registro, de manera manual o por sistema.
        if (newRecord){

            // Me aseguro de tener la organización correcta
            MZOrdenPago ordenPago = (MZOrdenPago) this.getZ_OrdenPago();
            this.setAD_Org_ID(ordenPago.getAD_Org_ID());

            if (this.getZ_GeneraOrdenPago_ID() <= 0){
                this.setZ_GeneraOrdenPago_ID(((MZOrdenPago) this.getZ_OrdenPago()).getZ_GeneraOrdenPago_ID());
            }
            if (this.getC_BPartner_ID() <= 0){
                this.setC_BPartner_ID(((MZOrdenPago) this.getZ_OrdenPago()).getC_BPartner_ID());
            }
        }

        // Si selecciono un item de medio de pago ya emitido, cargo datos del mismo en esta linea.
        if ((newRecord) || (is_ValueChanged(X_Z_OrdenPagoMedio.COLUMNNAME_Z_MedioPagoItem_ID))){
            if (this.getZ_MedioPagoItem_ID() > 0){
                MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
                if (medioPagoItem.isEmitido()){
                    this.setDateEmitted(medioPagoItem.getDateEmitted());
                    this.setDueDate(medioPagoItem.getDueDate());
                }
            }
        }

        // Valido fecha de emision debe ser siempre menor a fecha de vencimiento.
        if ((this.getDateEmitted() != null) && (this.getDueDate() != null)){
            if (this.getDueDate().before(this.getDateEmitted())){
                log.saveError("ATENCIÓN", "La fecha de Vencimiento debe ser mayor o igual a la fecha de Emisión.");
                return false;
            }
        }

        return true;
    }


    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        String action = "";

        try{

            action = " update z_ordenpago set AmtPaymentRule = " +
                    " (select sum(coalesce(totalamt,0)) from z_ordenpagomedio " +
                    " where z_ordenpago_id =" + this.getZ_OrdenPago_ID() + ") " +
                    " where z_ordenpago_id =" + this.getZ_OrdenPago_ID();

            DB.executeUpdateEx(action, get_TrxName());

            // Control de integridad de campos según comportamiento del medio de pago
            if (this.getZ_MedioPago_ID() > 0){
                MZMedioPago medioPago = (MZMedioPago) this.getZ_MedioPago();

                if ((!medioPago.isTieneCaja()) && (this.getC_CashBook_ID() > 0)){
                    action = " update z_ordenpagomedio set c_cashbook_id = null where z_ordenpagomedio_id =" + this.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());
                }

                if ((!medioPago.isTieneCtaBco()) && (this.getC_BankAccount_ID() > 0)){
                    action = " update z_ordenpagomedio set c_bankaccount_id = null where z_ordenpagomedio_id =" + this.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());
                }

                if ((!medioPago.isTieneFolio()) && (this.getZ_MedioPagoFolio_ID() > 0)){
                    action = " update z_ordenpagomedio set z_mediopagofolio_id = null where z_ordenpagomedio_id =" + this.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }

    @Override
    protected boolean beforeDelete() {

        String action = "";

        try{

            // Desasocio emision de medio de pago e item de medio de pago que se quitó de esta orden de pago.
            if (this.getZ_MedioPagoItem_ID() > 0){
                MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
                if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){

                    action = " update z_mediopagoitem set z_ordenpago_id = null where z_mediopagoitem_id =" + medioPagoItem.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());

                    if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
                        action = " update z_emisionmediopago set z_ordenpago_id = null where z_emisionmediopago_id =" + medioPagoItem.getZ_EmisionMedioPago_ID();
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

        String action = "";

        try{

            action = " update z_ordenpago set AmtPaymentRule = " +
                    " (select sum(coalesce(totalamt,0)) from z_ordenpagomedio " +
                    " where z_ordenpago_id =" + this.getZ_OrdenPago_ID() + ") " +
                    " where z_ordenpago_id =" + this.getZ_OrdenPago_ID();

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }

}
