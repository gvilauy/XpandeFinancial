package org.xpande.financial.report;

import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/26/18.
 */
public class EstadoCuentaRP extends SvrProcess {

    private EstadoCuenta estadoCuentaProcessor = new EstadoCuenta();

    @Override
    protected void prepare() {

        ProcessInfoParameter paramTituloReporte = null;
        ProcessInfoParameter paramOrganizacion = null;
        ProcessInfoParameter paramMonedaReporte = null;
        ProcessInfoParameter paramFechaInicio = null;
        ProcessInfoParameter paramFechaFin = null;
        ProcessInfoParameter paramUsuario = null;

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("AD_Client_ID")){
                        this.estadoCuentaProcessor.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.estadoCuentaProcessor.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                        this.estadoCuentaProcessor.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_Currency_ID")){
                        this.estadoCuentaProcessor.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("TipoFiltroFecha")){
                        this.estadoCuentaProcessor.tipoFecha = (String)para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("TipoSocioNegocio")){
                        this.estadoCuentaProcessor.tipoSocioNegocio = (String)para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("DateDoc")){
                        this.estadoCuentaProcessor.startDate = (Timestamp)para[i].getParameter();
                        this.estadoCuentaProcessor.endDate = (Timestamp)para[i].getParameter_To();
                    }
                    else if (name.trim().equalsIgnoreCase("RP_Titulo")){
                        paramTituloReporte = para[i];
                    }

                }
            }
        }

        this.estadoCuentaProcessor.adUserID = this.getAD_User_ID();

        // Seteo parametros fijos del reporte Jasper
        if (paramTituloReporte != null){
            paramTituloReporte.setParameter("ESTADO DE CUENTA PROVEEDORES");
        }


    }

    @Override
    protected String doIt() throws Exception {

        String message = this.estadoCuentaProcessor.executeReport();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
