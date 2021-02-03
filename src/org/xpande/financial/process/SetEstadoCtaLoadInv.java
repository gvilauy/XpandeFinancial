package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.comercial.model.MZLoadInvoice;
import org.xpande.financial.utils.FinancialUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Proceso que setea información para estado de cuenta, en invoices generadas en una detarminada carga de comprobantes comerciales. *
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/2/21.
 */
public class SetEstadoCtaLoadInv extends SvrProcess {

    private MZLoadInvoice loadInvoice = null;

    @Override
    protected void prepare() {
        this.loadInvoice = new MZLoadInvoice(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select c_invoice_id from c_invoice where z_loadinvoice_id =" + this.loadInvoice.get_ID();

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), get_TrxName());

                // Elimino por las dudas entradas anteriores al estado de cuenta.
                action = " delete from z_estadocuenta where c_invoice_id ="+ invoice.get_ID();
                DB.executeUpdateEx(action, get_TrxName());

                // Impacto en estado de cuenta
                FinancialUtils.setEstadoCtaInvoice(getCtx(), invoice, null, true, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return "OK";
    }
}
