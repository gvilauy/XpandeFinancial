package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZMedioPago extends X_Z_MedioPago {

    public MZMedioPago(Properties ctx, int Z_MedioPago_ID, String trxName) {
        super(ctx, Z_MedioPago_ID, trxName);
    }

    public MZMedioPago(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo según código recibido.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @param ctx
     * @param value
     * @param trxName
     * @return
     */
    public static MZMedioPago getByValue(Properties ctx, String value, String trxName){

        String whereClause = X_Z_MedioPago.COLUMNNAME_Value + " ='" + value + "'";

        MZMedioPago model = new Query(ctx, I_Z_MedioPago.Table_Name, whereClause, trxName).setOnlyActiveRecords(true).first();

        return model;
    }

    public static MZMedioPago getByPosValue(Properties ctx, String codMedioPagoPOS, String trxName) {

        try{
            String sql = " select z_mediopago_id from z_mediopagopos where codmediopagopos ='" + codMedioPagoPOS + "'";
            int zMedioPagoID = DB.getSQLValueEx(trxName, sql);
            if (zMedioPagoID <= 0){
                return null;
            }
            MZMedioPago medioPago = new MZMedioPago(ctx, zMedioPagoID, trxName);
            return medioPago;
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    public MZMedioPagoIdent getMedioPagoIdentPOS(String codTarjetaPOS) {
        try{
            String sql = " select a.z_mediopagoident_id " +
                    " from z_mediopagoident a " +
                    " inner join z_mpagoidentpos b on (a.z_mediopagoident_id = b.z_mediopagoident_id " +
                    " and b.codmediopagopos ='" + codTarjetaPOS + "') " +
                    " where a.z_mediopago_id =" + this.get_ID();
            int id = DB.getSQLValueEx(get_TrxName(), sql);
            if (id <= 0){
                return null;
            }
            MZMedioPagoIdent pagoIdent = new MZMedioPagoIdent(getCtx(), id, get_TrxName());
            return pagoIdent;
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }
}
