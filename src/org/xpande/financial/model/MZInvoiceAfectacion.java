package org.xpande.financial.model;

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

}
