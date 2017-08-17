package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para filtros de socios de negocio en generacion de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZGenOPagoFiltroSocio extends X_Z_GenOPagoFiltroSocio {

    public MZGenOPagoFiltroSocio(Properties ctx, int Z_GenOPagoFiltroSocio_ID, String trxName) {
        super(ctx, Z_GenOPagoFiltroSocio_ID, trxName);
    }

    public MZGenOPagoFiltroSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
