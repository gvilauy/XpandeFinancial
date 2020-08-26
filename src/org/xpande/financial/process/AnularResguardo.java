package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZResguardoSocio;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/26/20.
 */
public class AnularResguardo extends SvrProcess {

    private MZResguardoSocio resguardoSocio = null;
    private int adOrgID = -1;
    private int cDocTypeID = -1;
    private int cBPartnerID = -1;
    private String documentNo = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("DocumentNoRef")){
                        this.documentNo = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                        this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValue();
                    }
                    else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValue();
                    }
                    else if (name.trim().equalsIgnoreCase("C_DocType_ID")){
                        this.cDocTypeID = ((BigDecimal)para[i].getParameter()).intValue();
                    }
                }
            }
        }
        this.resguardoSocio = MZResguardoSocio.getByOrgDocPartner(getCtx(), this.adOrgID, this.cDocTypeID,
                this.documentNo, this.cBPartnerID, get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = null;

        try{
            if (this.resguardoSocio == null){
                return "@Error@ " + "No existe Comprobante para ese Socio de Negocio con el Número :" + this.documentNo;
            }
            if (!this.resguardoSocio.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
                return "@Error@ " + "El comprobante existe pero no esta en estado COMPLETO.";
            }

            // Anulo el comprobante
            if (!this.resguardoSocio.voidIt()){
                message = "No se pudo Anular el comprobante. ";
                if (this.resguardoSocio.getProcessMsg() != null){
                    message += this.resguardoSocio.getProcessMsg();
                }
                return "@Error@ " + message;
            }
            this.resguardoSocio.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }
}
