package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.comercial.model.MZLoadInvoice;
import org.xpande.financial.model.MZLoadPago;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.utils.FinancialUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/25/21.
 */
public class SetEstadoCtaLoadPago extends SvrProcess {

    private MZLoadPago loadPago = null;

    @Override
    protected void prepare() {
        this.loadPago = new MZLoadPago(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select z_pago_id from z_pago where z_loadpago_id =" + this.loadPago.get_ID();

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZPago pago = new MZPago(getCtx(), rs.getInt("z_pago_id"), get_TrxName());

                // Desasocio info en estado de cuenta para cualquier documento que haga referencia a esta pago/cobro
                action = " update z_estadocuenta set z_pago_to_id = null, daterefpago = null " +
                        " where z_pago_to_id =" + pago.get_ID();
                DB.executeUpdateEx(action, get_TrxName());

                // Elimino info en estado de cuenta para este documento de pago/cobro
                action = " delete from z_estadocuenta where z_pago_id =" + pago.get_ID();
                DB.executeUpdateEx(action, get_TrxName());

                // Impacto en estado de cuenta
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

        return "OK";
    }
}
