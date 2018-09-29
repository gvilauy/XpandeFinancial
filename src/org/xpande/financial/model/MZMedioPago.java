package org.xpande.financial.model;

import org.compiere.model.Query;

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

}
