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
            if (this.getZ_GeneraOrdenPago_ID() <= 0){
                this.setZ_GeneraOrdenPago_ID(((MZOrdenPago) this.getZ_OrdenPago()).getZ_GeneraOrdenPago_ID());
            }
            if (this.getC_BPartner_ID() <= 0){
                this.setC_BPartner_ID(((MZOrdenPago) this.getZ_OrdenPago()).getC_BPartner_ID());
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

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;

    }
}
