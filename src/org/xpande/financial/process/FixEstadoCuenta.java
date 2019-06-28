package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.financial.model.MZTransferSaldo;
import org.xpande.financial.utils.FinancialUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/28/19.
 */
public class FixEstadoCuenta extends SvrProcess {

    private int adClientID = 0;
    private int adOrgID = 0;
    private Timestamp startDate = null;
    private Timestamp endDate = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (name.trim().equalsIgnoreCase("AD_Client_ID")){
                    this.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                    this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("DateDoc")){
                    this.startDate = (Timestamp)para[i].getParameter();
                    this.endDate = (Timestamp)para[i].getParameter_To();
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        String action = "";

        try{

            String whereClause = "";

            if (this.adOrgID > 0){
                whereClause += " and a.ad_org_id =" + this.adOrgID;
            }

            // Elimino información actual en estado de cuenta según filtros indicados
            action = " delete from z_estadocuenta a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' ";
            DB.executeUpdateEx(action, get_TrxName());

            // Proceso Invoices
            this.setInvoices(whereClause);

            // Proceso Transferencias de Saldo
            this.setTransferSaldos(whereClause);

            // Proceso Resguardos

            // Proceso anticipos de pago / cobro

            // Proceso Ordenes de Pago

            // Proceso recibos de pago / cobro



        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";

    }

    private void setInvoices(String whereClause) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.c_invoice_id " +
                    " from c_invoice a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    " and a.dateinvoiced between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.dateinvoiced ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), get_TrxName());

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
    }

    private void setTransferSaldos(String whereClause) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_transfersaldo_id " +
                    " from z_transfersaldo a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.datedoc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZTransferSaldo transferSaldo = new MZTransferSaldo(getCtx(), rs.getInt("z_transfersaldo_id"), get_TrxName());

                //FinancialUtils.setEstadoCtaInvoice(getCtx(), invoice, null, true, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }
    }

}
