package org.xpande.financial.report;

import java.sql.Timestamp;

/**
 * Clase que contiene la Lógica de Ejecución para el Reporte de Saldos Pendientes de Socios de Negocio (RP y RV)
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/15/19.
 */
public class SaldoPendiente {

    private final String TABLA_REPORTE = "Z_RP_SaldoPendiente";

    public int adClientID = 0;
    public int adOrgID = 0;
    public int adUserID = 0;
    public int cBPartnerID = 0;
    public int cCurrencyID = 0;
    public String tipoFecha = "";
    public String tipoSocioNegocio = "";
    public Timestamp endDate = null;
    public boolean sinMovConSaldoIni = true;


    /***
     * Constructor
     */
    public SaldoPendiente() {

    }




}
