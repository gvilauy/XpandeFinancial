package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Lineas asociadas a un pago/cobro. Documentos que se afectan.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoLin extends X_Z_PagoLin {

    public MZPagoLin(Properties ctx, int Z_PagoLin_ID, String trxName) {
        super(ctx, Z_PagoLin_ID, trxName);
    }

    public MZPagoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
