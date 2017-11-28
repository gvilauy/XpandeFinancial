package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para lineas del proceso de Reemplazo de Medios de Pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/21/17.
 */
public class MZMedioPagoReplaceLin extends X_Z_MedioPagoReplaceLin {

    public MZMedioPagoReplaceLin(Properties ctx, int Z_MedioPagoReplaceLin_ID, String trxName) {
        super(ctx, Z_MedioPagoReplaceLin_ID, trxName);
    }

    public MZMedioPagoReplaceLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna detalle de esta linea de medio de pago a reemplazar.
     * Xpande. Created by Gabriel Vila on 11/28/17.
     * @return
     */
    public List<MZMedioPagoReplaceDet> getDetail() {

        String whereClause = X_Z_MedioPagoReplaceDet.COLUMNNAME_Z_MedioPagoReplaceLin_ID + " =" + this.get_ID();

        List<MZMedioPagoReplaceDet> lines = new Query(getCtx(), I_Z_MedioPagoReplaceDet.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }

}
