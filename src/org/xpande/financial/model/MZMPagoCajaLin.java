package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea de documento de ingreso de medios de pago por linea de caja.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/29/20.
 */
public class MZMPagoCajaLin extends X_Z_MPagoCajaLin{

    public MZMPagoCajaLin(Properties ctx, int Z_MPagoCajaLin_ID, String trxName) {
        super(ctx, Z_MPagoCajaLin_ID, trxName);
    }

    public MZMPagoCajaLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
