package org.xpande.financial.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Clase de métodos staticos referidos a funcionalidades financieras.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/17/19.
 */
public final class FinancialUtils {


    /***
     * Obtiene y retorna monto abierto para invoice recibida.
     * Xpande. Created by Gabriel Vila on 6/17/19.
     * @param ctx
     * @param cInvoiceID
     * @param trxName
     * @return
     */
    public static BigDecimal getInvoiceAmtOpen(Properties ctx, int cInvoiceID, String trxName){

        BigDecimal value = Env.ZERO;

        try{
            String sql = " select coalesce(amtopen, amtdocument) as monto from zv_financial_invopen where c_invoice_id =" + cInvoiceID;

            value = DB.getSQLValueBDEx(trxName, sql);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return value;
    }

}
