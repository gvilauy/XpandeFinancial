package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZLoadExtBanco;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/20/19.
 */
public class InterfaceCargaExtBanco extends SvrProcess {

    MZLoadExtBanco extBanco = null;

    @Override
    protected void prepare() {

        this.extBanco = new MZLoadExtBanco(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{
            if ((this.extBanco.getFileName() == null) || (this.extBanco.getFileName().trim().equalsIgnoreCase(""))){
                return "@Error@ Debe indicar archivo a procesar ";
            }

            String message = this.extBanco.executeInterface();
            if (message != null){
                return "@Error@ " + message;
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }

}
