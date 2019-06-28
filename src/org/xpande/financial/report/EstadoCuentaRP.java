package org.xpande.financial.report;

import org.compiere.model.MClient;
import org.compiere.model.MOrg;
import org.compiere.model.MUser;
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

    private ProcessInfoParameter paramTituloReporte = null;
    private ProcessInfoParameter paramCompania = null;
    private ProcessInfoParameter paramOrganizacion = null;
    private ProcessInfoParameter paramMonedaReporte = null;
    private ProcessInfoParameter paramFechaInicio = null;
    private ProcessInfoParameter paramFechaFin = null;
    private ProcessInfoParameter paramUsuario = null;


    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (name.trim().equalsIgnoreCase("AD_Client_ID")){
                    this.estadoCuentaProcessor.adClientID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                    this.estadoCuentaProcessor.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                }
                else if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                    if (para[i].getParameter() != null){
                        this.estadoCuentaProcessor.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
                else if (name.trim().equalsIgnoreCase("C_Currency_ID")){
                    if (para[i].getParameter() != null){
                        this.estadoCuentaProcessor.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
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
                else if (name.trim().equalsIgnoreCase("SinMovConSaldo")) {
                    this.estadoCuentaProcessor.sinMovConSaldoIni = (((String) para[i].getParameter()).trim().equalsIgnoreCase("Y")) ? true : false;
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
                else if (name.trim().equalsIgnoreCase("RP_StartDate")){
                    paramFechaInicio = para[i];
                }
                else if (name.trim().equalsIgnoreCase("RP_EndDate")){
                    paramFechaFin = para[i];
                }
            }
        }

        this.estadoCuentaProcessor.adUserID = this.getAD_User_ID();

        // Seteo parametros fijos del reporte Jasper
        this.setParametrosRP();


    }

    @Override
    protected String doIt() throws Exception {

        String message = this.estadoCuentaProcessor.executeReport();

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
            if (this.estadoCuentaProcessor.tipoSocioNegocio.equalsIgnoreCase("PROVEEDORES")){
                paramTituloReporte.setParameter("Estado De Cuenta de Proveedores");
            }
            else if (this.estadoCuentaProcessor.tipoSocioNegocio.equalsIgnoreCase("CLIENTES")){
                paramTituloReporte.setParameter("Estado De Cuenta de Clientes");
            }
            else if (this.estadoCuentaProcessor.tipoSocioNegocio.equalsIgnoreCase("TODOS")){
                paramTituloReporte.setParameter("Estado De Cuenta de Proveedores/Clientes");
            }
        }

        if (paramCompania != null){
            MClient client = new MClient(getCtx(), this.estadoCuentaProcessor.adClientID, null);
            paramCompania.setParameter(client.getDescription());
        }

        if (paramOrganizacion != null){
            MOrg org = new MOrg(getCtx(), this.estadoCuentaProcessor.adOrgID, null);
            paramOrganizacion.setParameter(org.getName());
        }

        if (paramUsuario != null){
            MUser user = new MUser(getCtx(), this.estadoCuentaProcessor.adUserID, null);
            paramUsuario.setParameter(user.getName());
        }

        if (paramFechaInicio != null){
            paramFechaInicio.setParameter(this.estadoCuentaProcessor.startDate);
        }

        if (paramFechaFin != null){
            paramFechaFin.setParameter(this.estadoCuentaProcessor.endDate);
        }
    }

}
