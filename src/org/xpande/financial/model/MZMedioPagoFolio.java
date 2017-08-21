package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Env;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para folios de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZMedioPagoFolio extends X_Z_MedioPagoFolio {

    public MZMedioPagoFolio(Properties ctx, int Z_MedioPagoFolio_ID, String trxName) {
        super(ctx, Z_MedioPagoFolio_ID, trxName);
    }

    public MZMedioPagoFolio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Genera items para este folio de medio de pago.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    public String generateItems() {

        String message = null;

        try{

            for (long i = this.getNumeroDesde(); i <= this.getNumeroHasta(); i++){
                MZMedioPagoItem item = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
                item.setZ_MedioPago_ID(this.getZ_MedioPago_ID());
                item.setZ_MedioPagoFolio_ID(this.get_ID());
                item.setC_BankAccount_ID(this.getC_BankAccount_ID());
                item.setC_Currency_ID(this.getC_Currency_ID());
                item.setNroMedioPago(String.valueOf(i));
                item.setIsReceipt(false);
                item.setDocumentSerie(this.getDocumentSerie());
                item.setEmitido(false);
                item.setTotalAmt(Env.ZERO);
                item.saveEx();
            }

            this.setIsExecuted(true);
            this.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Obtiene y retorna siguiente item de medio de pago disponible para este folio.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    public MZMedioPagoItem getCurrentNext(){

        String whereClause = X_Z_MedioPagoItem.COLUMNNAME_Z_MedioPagoFolio_ID + " =" + this.get_ID() +
                " AND " + X_Z_MedioPagoItem.COLUMNNAME_Emitido + " ='N'";

        MZMedioPagoItem model = new Query(getCtx(), I_Z_MedioPagoItem.Table_Name, whereClause, get_TrxName()).setOrderBy(" NroMedioPago ").first();

        return model;

    }

}
