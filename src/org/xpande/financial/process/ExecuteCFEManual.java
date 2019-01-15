package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.acct.Doc_DDOrder;
import org.compiere.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.cfe.utils.ProcesadorCFE;
import org.xpande.financial.model.I_Z_ResguardoSocio;
import org.xpande.financial.model.MZResguardoSocio;
import org.xpande.financial.model.X_Z_ResguardoSocio;

import java.math.BigDecimal;

/**
 * Proceso para enviar manualmente un CFE para un determinado documento financiero.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/15/19.
 */
public class ExecuteCFEManual extends SvrProcess {

    private int adOrgID = 0;
    private int cDocTypeID = 0;
    private String documentNoRef = "";

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_DocType_ID")){
                        this.cDocTypeID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DocumentNoRef")){
                        this.documentNoRef = para[i].getParameter().toString().trim();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        MZResguardoSocio resguardo = null;

        String message = null;

        try{

            MDocType docType = new MDocType(getCtx(), this.cDocTypeID, get_TrxName());
            if ((docType.getDocBaseType().equalsIgnoreCase("RGU")) || (docType.getDocBaseType().equalsIgnoreCase("RGC"))){

                String whereClause = X_Z_ResguardoSocio.COLUMNNAME_AD_Org_ID + " =" + this.adOrgID +
                        " AND " + X_Z_ResguardoSocio.COLUMNNAME_C_DocType_ID + " =" + this.cDocTypeID +
                        " AND " + X_Z_ResguardoSocio.COLUMNNAME_DocumentNo + " ='" + this.documentNoRef + "' " +
                        " AND " + X_Z_ResguardoSocio.COLUMNNAME_DocStatus + " ='CO' ";

                int [] modelIDs = MZResguardoSocio.getAllIDs(I_Z_ResguardoSocio.Table_Name, whereClause, get_TrxName());
                if (modelIDs.length <= 0){
                    return "@Error@ " + "No se obtuvo documento con ese número para procesar.";
                }
                resguardo = new MZResguardoSocio(getCtx(), modelIDs[0], get_TrxName());

                ProcesadorCFE procesadorCFE = new ProcesadorCFE(getCtx(), get_TrxName());
                message = procesadorCFE.executeCFE(resguardo, resguardo.getAD_Org_ID(), resguardo.getC_DocType_ID());
                if (message != null){
                    return "@Error@ " + message;
                }

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK.";
    }

}
