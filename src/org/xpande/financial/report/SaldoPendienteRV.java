package org.xpande.financial.report;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Reporte de Saldos Pendientes con salida para ReportView.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/19/19.
 */
public class SaldoPendienteRV extends SvrProcess {

    private SaldoPendiente saldoPendProcessor = new SaldoPendiente();

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (name.trim().equalsIgnoreCase("AD_Client_ID")){
                    this.saldoPendProcessor.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("AD_OrgTrx_ID")){
                    this.saldoPendProcessor.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                    if (para[i].getParameter() != null){
                        this.saldoPendProcessor.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
                else if (name.trim().equalsIgnoreCase("C_Currency_ID_To")){
                    if (para[i].getParameter() != null){
                        this.saldoPendProcessor.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
                else if (name.trim().equalsIgnoreCase("TieneAcct")) {
                    this.saldoPendProcessor.tieneAcct = (((String) para[i].getParameter()).trim().equalsIgnoreCase("Y")) ? true : false;
                }
                else if (name.trim().equalsIgnoreCase("TipoFiltroFecha")){
                    this.saldoPendProcessor.tipoFecha = (String)para[i].getParameter();
                }
                else if (name.trim().equalsIgnoreCase("TipoSocioNegocio")){
                    this.saldoPendProcessor.tipoSocioNegocio = (String)para[i].getParameter();
                }
                else if (name.trim().equalsIgnoreCase("StartDate")){
                    if (para[i].getParameter() != null){
                        this.saldoPendProcessor.startDate = (Timestamp)para[i].getParameter();
                    }
                }
                else if (name.trim().equalsIgnoreCase("EndDate")){
                    this.saldoPendProcessor.endDate = (Timestamp)para[i].getParameter();
                }
                else if (name.trim().equalsIgnoreCase("TipoConceptoDoc")) {
                    this.saldoPendProcessor.tipoConceptoDoc = (String)para[i].getParameter();
                }
            }
        }

        this.saldoPendProcessor.adUserID = this.getAD_User_ID();
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.saldoPendProcessor.executeReport();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
