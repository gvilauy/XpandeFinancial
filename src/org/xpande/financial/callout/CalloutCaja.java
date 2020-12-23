package org.xpande.financial.callout;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.financial.model.MZMovCaja;
import org.xpande.financial.model.X_Z_MovCajaLin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

/**
 * Callouts relacionados con movimientos de caja.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/22/20.
 */
public class CalloutCaja extends CalloutEngine {

    /***
     * Al digitar moneda en lineas de documento de movimientos de caja, se obtiene tasa de cambio y se calcula
     * el monto en moneda de la transacción.
     * Si se digita tasa de cambio, entonces solo tengo que calcular el monto en moneda de transacción.
     * Xpande. Created by Gabriel Vila on 12/22/20.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setMovCajaAmtMT(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if (value == null) return "";

        // Modelo del cabezal
        int zMovCajaID = Env.getContextAsInt(ctx, WindowNo, "Z_MovCaja_ID");
        MZMovCaja movCaja = new MZMovCaja(ctx, zMovCajaID, null);

        MAcctSchema schema = MClient.get(ctx, movCaja.getAD_Client_ID()).getAcctSchema();

        String column = mField.getColumnName();

        BigDecimal amt = null;
        BigDecimal rate = null;
        int cCurrencyID = -1;

        // Si se modifica moneda
        if (column.equalsIgnoreCase("C_Currency_ID")){

            cCurrencyID = ((Integer) value).intValue();
            amt = (BigDecimal) mTab.getValue(X_Z_MovCajaLin.COLUMNNAME_TotalAmt);

            if (cCurrencyID <= 0){
                mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_CurrencyRate, Env.ZERO);
                mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_TotalAmtMT, Env.ZERO);
                return "";
            }

            // Si la moneda de la linea es igual a la moneda del cabezal, entonces monto en moneda transaccion es igual
            // al monto en la moneda de la linea.
            if (movCaja.getC_Currency_ID() == cCurrencyID){
                mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_TieneTasaCambio, false);
                mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_CurrencyRate, Env.ONE);
                mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_TotalAmtMT, amt);
                return "";
            }

            mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_TieneTasaCambio, true);

            // Moneda de la linea es distinta a la moneda del cabezal, debo obtener tasa de cambio.
            rate = CurrencyUtils.getCurrencyRate(ctx, movCaja.getAD_Client_ID(), 0,
                    cCurrencyID, movCaja.getC_Currency_ID(), 114, movCaja.getDateDoc(), null);

            if (rate == null){
                mTab.fireDataStatusEEvent ("Error", "No se pudo obtener Tasa de Cambio para Moneda ingresada y fecha : " + movCaja.getDateDoc().toString(), true);
                return "";
            }
        }
        else if (column.equalsIgnoreCase("CurrencyRate")){

            amt = (BigDecimal) mTab.getValue(X_Z_MovCajaLin.COLUMNNAME_TotalAmt);
            cCurrencyID = (Integer) mTab.getValue(X_Z_MovCajaLin.COLUMNNAME_C_Currency_ID);

            rate = (BigDecimal) value;

            if (rate == null) rate = Env.ZERO;
            if (rate.compareTo(Env.ZERO) <= 0){
                mTab.fireDataStatusEEvent ("Error", "Debe indicar valor para Tasa de Cambio", true);
                return "";
            }
        }
        else if (column.equalsIgnoreCase("TotalAmt")){

            rate = (BigDecimal) mTab.getValue(X_Z_MovCajaLin.COLUMNNAME_CurrencyRate);
            cCurrencyID = (Integer) mTab.getValue(X_Z_MovCajaLin.COLUMNNAME_C_Currency_ID);

            amt = (BigDecimal) value;

            if (amt == null) amt = Env.ZERO;
        }

        BigDecimal amtMT = amt;
        if (cCurrencyID != schema.getC_Currency_ID()){
            amtMT = amt.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        }
        else{
            amtMT = amt.divide(rate, 2, RoundingMode.HALF_UP);
        }

        mTab.setValue(X_Z_MovCajaLin.COLUMNNAME_TotalAmtMT, amtMT);

        return "";
    }
}
