package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para socio de negocio a considerarse en generacion de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZGeneraOrdenPagoSocio extends X_Z_GeneraOrdenPagoSocio {

    public MZGeneraOrdenPagoSocio(Properties ctx, int Z_GeneraOrdenPagoSocio_ID, String trxName) {
        super(ctx, Z_GeneraOrdenPagoSocio_ID, trxName);
    }

    public MZGeneraOrdenPagoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lineas de documentos seleccionados para este socio de negocio.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    public List<MZGeneraOrdenPagoLin> getSelectedDocuments() {

        String whereClause = X_Z_GeneraOrdenPagoLin.COLUMNNAME_Z_GeneraOrdenPagoSocio_ID + " =" + this.get_ID() +
                " AND " + X_Z_GeneraOrdenPagoLin.COLUMNNAME_IsSelected + " ='Y'";

        List<MZGeneraOrdenPagoLin> lines = new Query(getCtx(), I_Z_GeneraOrdenPagoLin.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }


    /***
     * Marca / desmarca todos los documentos de este socio de negocio segun parametro de acción recibido, en el proceso de generación
     * de ordenes de pago.
     * Xpande. Created by Gabriel Vila on 10/13/17.
     * @param marcarRegistros
     * @return
     */
    public String marcarDocumentos(boolean marcarRegistros) {

        String message = null;

        try{

            String isSelected = "Y";

            if (!marcarRegistros){
                isSelected = "N";
            }

            String action = " update " + I_Z_GeneraOrdenPagoLin.Table_Name +
                            " set " + I_Z_GeneraOrdenPagoLin.COLUMNNAME_IsSelected  + " ='" + isSelected + "' " +
                            " where " + I_Z_GeneraOrdenPagoLin.COLUMNNAME_Z_GeneraOrdenPagoSocio_ID + " =" + this.get_ID();

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

}
