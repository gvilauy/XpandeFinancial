package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para afectaciones hechas a Anticipos de pago / cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/24/19.
 */
public class MZPagoAfectacion extends X_Z_PagoAfectacion {

    public MZPagoAfectacion(Properties ctx, int Z_PagoAfectacion_ID, String trxName) {
        super(ctx, Z_PagoAfectacion_ID, trxName);
    }

    public MZPagoAfectacion(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene y retorna modelo segun ID y de orden de pago recibidos.
     * Xpande. Created by Gabriel Vila on 3/25/19.
     * @param ctx
     * @param zPagoID
     * @param zOrdenPagoID
     * @param trxName
     * @return
     */
    public static MZPagoAfectacion getByPagoOrdenPago(Properties ctx, int zPagoID, int zOrdenPagoID, String trxName) {

        String whereClause = X_Z_PagoAfectacion.COLUMNNAME_Z_Pago_ID + " =" + zPagoID +
                " AND " + X_Z_PagoAfectacion.COLUMNNAME_Z_OrdenPago_ID + " =" + zOrdenPagoID;

        MZPagoAfectacion model = new Query(ctx, I_Z_PagoAfectacion.Table_Name, whereClause, trxName).first();

        return model;
    }

}
