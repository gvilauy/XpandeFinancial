package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.financial.model.MZOrdenPago;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.MZResguardoSocio;
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

        //this.fixDocumentosTodos();

        this.fixResguardos();

        return "OK";

    }

    private void fixDocumentosTodos(){

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
            this.setResguardos(whereClause);

            // Proceso anticipos de pago / cobro
            this.setAnticipos(whereClause);

            // Proceso Ordenes de Pago para anticipos
            this.setOrdenesPago(whereClause, true);

            // Proceso recibos de pago / cobro para anticipos
            this.setPagos(whereClause, true);

            // Proceso ordenes de pago normales
            this.setOrdenesPago(whereClause, false);

            // Proceso recibos de pago / cobro normales
            this.setPagos(whereClause, false);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    private void fixResguardos(){

        String action = "";

        try{

            String whereClause = "";

            if (this.adOrgID > 0){
                whereClause += " and a.ad_org_id =" + this.adOrgID;
            }

            // Elimino información actual en estado de cuenta según filtros indicados
            action = " delete from z_estadocuenta  " +
                    " where z_resguardosocio_id is not null " +
                    " and z_resguardosocio_id in (select z_resguardosocio_id from aux_resg) ";
            DB.executeUpdateEx(action, get_TrxName());

            // Proceso Resguardos
            this.setResguardos2(whereClause);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
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

                // Obtengo fecha de vencimiento de la invoice referenciada
                // Fecha de Vencimiento de esta invoice, directamente de termino de pago
                sql = " select paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate " +
                        " from c_invoice " +
                        " where c_invoice_id =" + transferSaldo.getC_Invoice_ID();
                Timestamp dueDate = DB.getSQLValueTSEx(get_TrxName(), sql);
                if (dueDate == null){
                    dueDate = transferSaldo.getDateInvoiced();
                }

                FinancialUtils.setEstadoCtaTransferSaldo(getCtx(), transferSaldo, dueDate,true, get_TrxName());
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

    private void setResguardos(String whereClause){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_resguardosocio_id " +
                    " from z_resguardosocio a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.datedoc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZResguardoSocio resguardoSocio = new MZResguardoSocio(getCtx(), rs.getInt("z_resguardosocio_id"), get_TrxName());

                FinancialUtils.setEstadoCtaResguardo(getCtx(), resguardoSocio, true, get_TrxName());
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


    private void setResguardos2(String whereClause){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select z_resguardosocio_id " +
                    " from aux_resg ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZResguardoSocio resguardoSocio = new MZResguardoSocio(getCtx(), rs.getInt("z_resguardosocio_id"), get_TrxName());

                FinancialUtils.setEstadoCtaResguardo(getCtx(), resguardoSocio, true, get_TrxName());
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


    private void setOrdenesPago(String whereClause, boolean esOrdenAnticipo){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_ordenpago_id " +
                    " from z_ordenpago a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    ((esOrdenAnticipo) ? " and OrdPagoAnticipo ='Y' " :  " and OrdPagoAnticipo ='N' ") +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.datedoc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZOrdenPago ordenPago = new MZOrdenPago(getCtx(), rs.getInt("z_ordenpago_id"), get_TrxName());

                FinancialUtils.setEstadoCtaOrdenPago(getCtx(), ordenPago, true, get_TrxName());
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

    private void setPagos(String whereClause, boolean esReciboAnticipo){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_pago_id " +
                    " from z_pago a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    ((esReciboAnticipo) ? " and ReciboAnticipo ='Y' " :  " and ReciboAnticipo ='N' ") +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.datedoc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZPago pago = new MZPago(getCtx(), rs.getInt("z_pago_id"), get_TrxName());

                FinancialUtils.setEstadoCtaPago(getCtx(), pago, true, get_TrxName());
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


    private void setAnticipos(String whereClause){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_pago_id " +
                    " from z_pago a " +
                    " where a.ad_client_id =" + this.adClientID + whereClause +
                    " and a.docstatus ='CO' " +
                    " and anticipo ='Y' " +
                    " and a.datedoc between '" + this.startDate + "' and '" + this.endDate + "' " +
                    " order by a.datedoc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZPago pago = new MZPago(getCtx(), rs.getInt("z_pago_id"), get_TrxName());

                FinancialUtils.setEstadoCtaPago(getCtx(), pago, true, get_TrxName());
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
