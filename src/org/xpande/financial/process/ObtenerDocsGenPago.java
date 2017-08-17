package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZGeneraOrdenPago;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class ObtenerDocsGenPago extends SvrProcess {

    private MZGeneraOrdenPago generaOrdenPago = null;
    private String tipoAccion = "NUEVOS";

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){

                if (para[i].getParameter() != null){

                    if (name.trim().equalsIgnoreCase("SelDocGenPagoAction")){
                        this.tipoAccion = (String)para[i].getParameter();
                    }
                }
            }
        }


        this.generaOrdenPago = new MZGeneraOrdenPago(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.generaOrdenPago.getDocumentos(tipoAccion);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";

    }
}
