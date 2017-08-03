package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de retencion para socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/1/17.
 */
public class MZRetencionSocio extends X_Z_RetencionSocio {

    public MZRetencionSocio(Properties ctx, int Z_RetencionSocio_ID, String trxName) {
        super(ctx, Z_RetencionSocio_ID, trxName);
    }

    public MZRetencionSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de retenciones asociadas a un determinado socio de negocio recibido.
     * Xpande. Created by Gabriel Vila on 8/2/17.
     * @param ctx
     * @param cBPartnerID
     * @param trxName
     * @return
     */
    public static List<MZRetencionSocioBPartner> getRetencionesBPartner(Properties ctx, int cBPartnerID, String trxName){

        String whereClause = X_Z_RetencionSocioBPartner.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID;

        List<MZRetencionSocioBPartner> lines = new Query(ctx, I_Z_RetencionSocioBPartner.Table_Name, whereClause, trxName)
                .setOnlyActiveRecords(true).list();

        return lines;
    }
}
