package org.xpande.financial.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para configuración de modulo Financiero del Core.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/25/17.
 */
public class MZFinancialConfig extends X_Z_FinancialConfig{

    public MZFinancialConfig(Properties ctx, int Z_FinancialConfig_ID, String trxName) {
        super(ctx, Z_FinancialConfig_ID, trxName);
    }

    public MZFinancialConfig(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene modelo único de configuración de modulo financiero.
     * Xpande. Created by Gabriel Vila on 8/22/17.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MZFinancialConfig getDefault(Properties ctx, String trxName){

        MZFinancialConfig model = new Query(ctx, I_Z_FinancialConfig.Table_Name, "", trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna modelo de configuracion para carga de tasa de cambio de una moneda recibida.
     * Xpande. Created by Gabriel Vila on 10/3/17.
     * @param cCurrencyID
     * @return
     */
    public MZFinancialConfigTC getConfigTCByCurrency(int cCurrencyID){

        String whereClause = X_Z_FinancialConfigTC.COLUMNNAME_Z_FinancialConfig_ID + " =" + this.get_ID() +
                " AND " + X_Z_FinancialConfigTC.COLUMNNAME_C_Currency_ID + " =" + cCurrencyID;

        MZFinancialConfigTC model = new Query(getCtx(), I_Z_FinancialConfigTC.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).first();

        return model;

    }

    /***
     * Obtiene y retorna modelo de configuracion para carga de tasa de cambio dado un codigo ISO de moneda recibido.
     * Xpande. Created by Gabriel Vila on 10/3/17.
     * @param currencyTCCode
     * @return
     */
    public MZFinancialConfigTC getConfigTCByCurrencyTCCode(String currencyTCCode){

        String whereClause = X_Z_FinancialConfigTC.COLUMNNAME_Z_FinancialConfig_ID + " =" + this.get_ID() +
                " AND " + X_Z_FinancialConfigTC.COLUMNNAME_CodigoTC + " ='" + currencyTCCode + "'";

        MZFinancialConfigTC model = new Query(getCtx(), I_Z_FinancialConfigTC.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).first();

        return model;

    }


    /***
     * Obtiene y retorna lista de configuraciones para carga de tasa de cambio en distintas monedas.
     * Xpande. Created by Gabriel Vila on 10/3/17.
     * @return
     */
    public List<MZFinancialConfigTC> getConfigTCs(){

        String whereClause = X_Z_FinancialConfigTC.COLUMNNAME_Z_FinancialConfig_ID + " =" + this.get_ID();

        List<MZFinancialConfigTC> lines = new Query(getCtx(), I_Z_FinancialConfigTC.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;

    }


}
