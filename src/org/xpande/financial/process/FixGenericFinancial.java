package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.financial.model.MZPago;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/6/19.
 */
public class FixGenericFinancial extends SvrProcess {

    @Override
    protected void prepare() {

    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String message = null;

        try{

            sql = " select distinct z_pago_id from aux_fix order by z_pago_id ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZPago pago = new MZPago(getCtx(), rs.getInt("z_pago_id"), get_TrxName());

                if (!pago.processIt(DocAction.ACTION_Void)){
                    message = pago.getProcessMsg();
                    if (message == null){
                        message = "Error al procesar pago con ID : " + rs.getInt("z_pago_id");
                    }
                    throw new AdempiereException(message);
                }
                pago.saveEx();
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return "OK.";
    }
}
