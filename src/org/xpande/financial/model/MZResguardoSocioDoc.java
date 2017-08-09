package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para comprobante considerado en una Emisión de Resguardo a Socio de Negocio. *
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/2/17.
 */
public class MZResguardoSocioDoc extends X_Z_ResguardoSocioDoc {

    public MZResguardoSocioDoc(Properties ctx, int Z_ResguardoSocioDoc_ID, String trxName) {
        super(ctx, Z_ResguardoSocioDoc_ID, trxName);
    }

    public MZResguardoSocioDoc(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeDelete() {

        // No permito eliminar comprobantes en un contra-resguardo
        MZResguardoSocio resguardoSocio = (MZResguardoSocio) this.getZ_ResguardoSocio();
        if (resguardoSocio.isEsContraResguardo()){
            log.saveError("ATENCIÓN", "No es posible eliminar comprobantes en un Contra-Resguardo.");
            return false;
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return success;

        // Al eliminar un documento a considerar en el resguardo, debo recaulcular retenciones.
        MZResguardoSocio resguardoSocio = (MZResguardoSocio) this.getZ_ResguardoSocio();
        if (!resguardoSocio.isEsContraResguardo()){
            resguardoSocio.calcularRetenciones();
        }

        return true;
    }


    /***
     * Obtiene y retorna lista de impuestos asociados a este comprobante
     * Xpande. Created by Gabriel Vila on 8/2/17.
     * @return
     */
    public List<MZResguardoSocioDocTax> getDocTaxes(){

        String whereClause = X_Z_ResguardoSocioDocTax.COLUMNNAME_Z_ResguardoSocioDoc_ID + " =" + this.get_ID();

        List<MZResguardoSocioDocTax> lines = new Query(getCtx(), I_Z_ResguardoSocioDocTax.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }

}
