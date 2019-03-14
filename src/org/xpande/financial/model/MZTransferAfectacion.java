package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para afectaciones de documentos de transferencias de saldos para pago / cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/14/19.
 */
public class MZTransferAfectacion extends X_Z_TransferAfectacion {

    public MZTransferAfectacion(Properties ctx, int Z_TransferAfectacion_ID, String trxName) {
        super(ctx, Z_TransferAfectacion_ID, trxName);
    }

    public MZTransferAfectacion(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo segun ID y de orden de pago recibidos.
     * Xpande. Created by Gabriel Vila on 3/14/19.
     * @param ctx
     * @param zTransferSaldoID
     * @param zOrdenPagoID
     * @param trxName
     * @return
     */
    public static MZTransferAfectacion getByTransferOrdenPago(Properties ctx, int zTransferSaldoID, int zOrdenPagoID, String trxName) {

        String whereClause = X_Z_TransferAfectacion.COLUMNNAME_Z_TransferSaldo_ID + " =" + zTransferSaldoID +
                " AND " + X_Z_TransferAfectacion.COLUMNNAME_Z_OrdenPago_ID + " =" + zOrdenPagoID;

        MZTransferAfectacion model = new Query(ctx, I_Z_TransferAfectacion.Table_Name, whereClause, trxName).first();

        return model;
    }

}
