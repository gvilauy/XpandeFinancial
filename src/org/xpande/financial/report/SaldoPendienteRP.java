package org.xpande.financial.report;

import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MUser;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Reporte de Saldos Pendientes con salida para JasperReports.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/15/19.
 */
public class SaldoPendienteRP extends SvrProcess {

    private SaldoPendiente saldoPendProcessor = new SaldoPendiente();

    private ProcessInfoParameter paramTituloReporte = null;
    private ProcessInfoParameter paramCompania = null;
    private ProcessInfoParameter paramOrganizacion = null;
    private ProcessInfoParameter paramMonedaReporte = null;
    private ProcessInfoParameter paramFechaFin = null;
    private ProcessInfoParameter paramUsuario = null;

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
                else if (name.trim().equalsIgnoreCase("EndDate")){
                    this.saldoPendProcessor.endDate = (Timestamp)para[i].getParameter();
                }
                else if (name.trim().equalsIgnoreCase("TipoConceptoDoc")) {
                    this.saldoPendProcessor.tipoConceptoDoc = (String)para[i].getParameter();
                }
                else if (name.trim().equalsIgnoreCase("RP_Titulo")){
                    paramTituloReporte = para[i];
                }
                else if (name.trim().equalsIgnoreCase("RP_Compania")){
                    paramCompania = para[i];
                }
                else if (name.trim().equalsIgnoreCase("RP_Organizacion")){
                    paramOrganizacion = para[i];
                }
                else if (name.trim().equalsIgnoreCase("RP_Usuario")){
                    paramUsuario = para[i];
                }
                else if (name.trim().equalsIgnoreCase("RP_EndDate")){
                    paramFechaFin = para[i];
                }
            }
        }

        this.saldoPendProcessor.adUserID = this.getAD_User_ID();

        // Seteo parametros fijos del reporte Jasper
        this.setParametrosRP();

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.saldoPendProcessor.executeReport();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }


    /***
     * Setea parametros del reporte.
     * Xpande. Created by Gabriel Vila on 3/27/18.
     */
    private void setParametrosRP(){

        if (paramTituloReporte != null){
            if (this.saldoPendProcessor.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                paramTituloReporte.setParameter("Estado De Cuenta de Proveedores");
            }
            else if (this.saldoPendProcessor.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                paramTituloReporte.setParameter("Estado De Cuenta de Clientes");
            }
            else if (this.saldoPendProcessor.tipoSocioNegocio.equalsIgnoreCase("TODOS")){
                paramTituloReporte.setParameter("Estado De Cuenta de Proveedores/Clientes");
            }
        }

        if (paramCompania != null){
            MClient client = new MClient(getCtx(), this.saldoPendProcessor.adClientID, null);
            paramCompania.setParameter(client.getDescription());
        }

        if (paramOrganizacion != null){
            MOrg org = new MOrg(getCtx(), this.saldoPendProcessor.adOrgID, null);
            paramOrganizacion.setParameter(org.getName());
        }

        if (paramUsuario != null){
            MUser user = new MUser(getCtx(), this.saldoPendProcessor.adUserID, null);
            paramUsuario.setParameter(user.getName());
        }

        if (paramFechaFin != null){
            paramFechaFin.setParameter(this.saldoPendProcessor.endDate);
        }
    }

}
