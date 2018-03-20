package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para gestionar información de saldos de comprobantes de socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZInvoiceAfectacion extends X_Z_InvoiceAfectacion {

    public MZInvoiceAfectacion(Properties ctx, int Z_InvoiceAfectacion_ID, String trxName) {
        super(ctx, Z_InvoiceAfectacion_ID, trxName);
    }

    public MZInvoiceAfectacion(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo segun ID de invoice y de orden de pago recibidos.
     * Xpande. Created by Gabriel Vila on 3/15/18.
     * @param ctx
     * @param cInvoiceID
     * @param zOrdenPagoID
     * @param trxName
     * @return
     */
    public static MZInvoiceAfectacion getByInvoiceOrdenPago(Properties ctx, int cInvoiceID, int zOrdenPagoID, String trxName) {

        String whereClause = X_Z_InvoiceAfectacion.COLUMNNAME_C_Invoice_ID + " =" + cInvoiceID +
                " AND " + X_Z_InvoiceAfectacion.COLUMNNAME_Z_OrdenPago_ID + " =" + zOrdenPagoID;

        MZInvoiceAfectacion model = new Query(ctx, I_Z_InvoiceAfectacion.Table_Name, whereClause, trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna modelo segun ID de vencimeinto de invoice y de orden de pago recibidos.
     * @param ctx
     * @param cInvoicePayScheduleID
     * @param zOrdenPagoID
     * @param trxName
     * @return
     */
    public static MZInvoiceAfectacion getByInvoiceSchOrdenPago(Properties ctx, int cInvoicePayScheduleID, int zOrdenPagoID, String trxName) {

        String whereClause = X_Z_InvoiceAfectacion.COLUMNNAME_C_InvoicePaySchedule_ID + " =" + cInvoicePayScheduleID +
                " AND " + X_Z_InvoiceAfectacion.COLUMNNAME_Z_OrdenPago_ID + " =" + zOrdenPagoID;

        MZInvoiceAfectacion model = new Query(ctx, I_Z_InvoiceAfectacion.Table_Name, whereClause, trxName).first();

        return model;
    }

}
