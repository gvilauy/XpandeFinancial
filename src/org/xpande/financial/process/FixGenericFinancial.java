package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.MZResguardoSocio;
import org.xpande.financial.model.MZResguardoSocioDoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
        String action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String message = null;

        try{

            sql = " select z_resguardosocio_id, Z_ResguardoSocio_Ref_ID " +
                    "from Z_ResguardoSocio " +
                    "where c_doctype_id =1000053 " +
                    "and docstatus='CO' ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            int i = 0;

            while(rs.next()){

                i++;
                System.out.println("Linea : " + i);

                MZResguardoSocio contraResguardo = new MZResguardoSocio(getCtx(), rs.getInt("z_resguardosocio_id"), get_TrxName());
                MZResguardoSocio resguardo = new MZResguardoSocio(getCtx(), rs.getInt("Z_ResguardoSocio_Ref_ID"), get_TrxName());

                List<MZResguardoSocioDoc> resguardoSocioDocList = resguardo.getResguardoDocs();
                for (MZResguardoSocioDoc resguardoSocioDoc: resguardoSocioDocList){
                    action = " update z_resguardosociodoc set amtretencionmo =" + resguardoSocioDoc.getAmtRetencionMO() +
                            " where z_resguardosocio_id =" + contraResguardo.get_ID() +
                            " and c_invoice_id =" + resguardoSocioDoc.getC_Invoice_ID();
                    DB.executeUpdateEx(action, get_TrxName());
                }

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
