package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.xpande.core.utils.CurrencyUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 * Monedas de documentos afectados en un pago/cobro. Gestión multimoneda.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class MZPagoMoneda extends X_Z_PagoMoneda {

    public MZPagoMoneda(Properties ctx, int Z_PagoMoneda_ID, String trxName) {
        super(ctx, Z_PagoMoneda_ID, trxName);
    }

    public MZPagoMoneda(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Dado un hash con monedas a procesar, se cargan valores para las mismas en la tabla de este modelo.
     * Xpande. Created by Gabriel Vila on 1/26/18.
     * @param ctx
     * @param zPagoID
     * @param hashCurrency
     * @param trxName
     * @return
     */
    public static String setMonedas(Properties ctx, int zPagoID, HashMap<Integer, Integer> hashCurrency, String trxName){

        String message = null;

        try{

            for (Integer cCurrencyID: hashCurrency.values()){
                MZPagoMoneda pagoMoneda = MZPagoMoneda.getByCurrency(ctx, zPagoID, cCurrencyID, trxName);
                if (pagoMoneda == null){

                    // Tasa de Cambio para moneda recibida en moneda del documento de pago/cobro.
                    MZPago pago = new MZPago(ctx, zPagoID, trxName);
                    BigDecimal multiplyRate = CurrencyUtils.getCurrencyRate(ctx, pago.getAD_Client_ID(), pago.getAD_Org_ID(),
                            cCurrencyID, pago.getC_Currency_ID(), 114, pago.getDateDoc(), null);

                    if (multiplyRate == null){
                        return "No se pudo obtener Tasa de Cambio para Moneda : " + cCurrencyID + ", Fecha : " + pago.getDateDoc().toString();
                    }

                    pagoMoneda = new MZPagoMoneda(ctx, 0, trxName);
                    pagoMoneda.setZ_Pago_ID(zPagoID);
                    pagoMoneda.setC_Currency_ID(cCurrencyID);
                    pagoMoneda.setMultiplyRate(multiplyRate);
                    pagoMoneda.saveEx();
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Setea y retorna modelo para id de pago y moneda recibido.
     * Xpande. Created by Gabriel Vila on 1/29/18.
     * @param ctx
     * @param zPagoID
     * @param cCurrencyID
     * @param trxName
     * @return
     */
    public static MZPagoMoneda setByCurrency(Properties ctx, int zPagoID, int cCurrencyID, String trxName){

        MZPagoMoneda pagoMoneda = null;

        try{
            pagoMoneda = MZPagoMoneda.getByCurrency(ctx, zPagoID, cCurrencyID, trxName);
            if (pagoMoneda == null){

                // Tasa de Cambio para moneda recibida en moneda del documento de pago/cobro.
                MZPago pago = new MZPago(ctx, zPagoID, trxName);
                BigDecimal multiplyRate = CurrencyUtils.getCurrencyRate(ctx, pago.getAD_Client_ID(), pago.getAD_Org_ID(),
                        cCurrencyID, pago.getC_Currency_ID(), 114, pago.getDateDoc(), null);

                if (multiplyRate == null){
                    throw new AdempiereException("No se pudo obtener Tasa de Cambio para Moneda : " + cCurrencyID + ", Fecha : " + pago.getDateDoc().toString());
                }
                pagoMoneda = new MZPagoMoneda(ctx, 0, trxName);
                pagoMoneda.setZ_Pago_ID(zPagoID);
                pagoMoneda.setC_Currency_ID(cCurrencyID);
                pagoMoneda.setMultiplyRate(multiplyRate);
                pagoMoneda.saveEx();
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return pagoMoneda;
    }


    /***
     * Obtiene y retorna modelo según pago/cobro y moneda recibida.
     * @param ctx
     * @param zPagoID
     * @param cCurrencyID
     * @param trxName
     * @return
     */
    public static MZPagoMoneda getByCurrency(Properties ctx, int zPagoID, int cCurrencyID, String trxName){

        String whereClause = X_Z_PagoMoneda.COLUMNNAME_Z_Pago_ID + " =" + zPagoID +
                " AND " + X_Z_PagoMoneda.COLUMNNAME_C_Currency_ID + " =" + cCurrencyID;

        MZPagoMoneda model = new Query(ctx, I_Z_PagoMoneda.Table_Name, whereClause, trxName).first();

        return model;
    }

}
