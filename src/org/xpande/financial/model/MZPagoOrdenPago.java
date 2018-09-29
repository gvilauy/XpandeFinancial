package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de orden de pago asociada a un documento de pago a proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/12/18.
 */
public class MZPagoOrdenPago extends X_Z_PagoOrdenPago {

    public MZPagoOrdenPago(Properties ctx, int Z_PagoOrdenPago_ID, String trxName) {
        super(ctx, Z_PagoOrdenPago_ID, trxName);
    }

    public MZPagoOrdenPago(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeDelete() {

        String action = "";

        try{

            // Al eliminar una orden de pago, debo eliminar los documentos referenciados para que no se consideren en este documento de pago
            action = " delete from " + X_Z_PagoResguardo.Table_Name +
                     " where z_pago_id =" + this.getZ_Pago_ID() +
                     " and z_ordenpago_id =" + this.getZ_OrdenPago_ID();
            DB.executeUpdateEx(action, get_TrxName());

            action = " delete from " + X_Z_PagoMedioPago.Table_Name +
                    " where z_pago_id =" + this.getZ_Pago_ID() +
                    " and z_ordenpago_id =" + this.getZ_OrdenPago_ID();
            DB.executeUpdateEx(action, get_TrxName());

            action = " delete from " + X_Z_PagoLin.Table_Name +
                    " where z_pago_id =" + this.getZ_Pago_ID() +
                    " and z_ordenpago_id =" + this.getZ_OrdenPago_ID();
            DB.executeUpdateEx(action, get_TrxName());

            // Actualizo totales del cabezal del documento de pago
            MZPago pago = (MZPago) this.getZ_Pago();
            pago.updateTotals();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }
}
