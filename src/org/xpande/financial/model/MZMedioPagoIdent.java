package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para identificadores asociados a medios de pago en modulo financiero.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/10/19.
 */
public class MZMedioPagoIdent extends X_Z_MedioPagoIdent {

    public MZMedioPagoIdent(Properties ctx, int Z_MedioPagoIdent_ID, String trxName) {
        super(ctx, Z_MedioPagoIdent_ID, trxName);
    }

    public MZMedioPagoIdent(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene ID del ultimo producto asociado a este identificador.
     * Xpande. Created by Gabriel Vila on 1/20/20.
     * @return
     */
    public int getLastProductID() {

        int value = -1;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select m_product_id from Z_MPagoIdentProd where z_mediopagoident_id =" + this.get_ID() +
                    " and isactive='Y' " +
                    " order by created ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){
                value = rs.getInt("m_product_id");
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return value;
    }
}
