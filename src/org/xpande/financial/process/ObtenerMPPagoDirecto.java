package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZPago;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/14/18.
 */
public class ObtenerMPPagoDirecto extends SvrProcess {

    private MZPago pago = null;
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

        this.pago = new MZPago(getCtx(), this.getRecord_ID(), get_TrxName());

    }


    @Override
    protected String doIt() throws Exception {

        String message = this.pago.getMediosPagoEmitidos(tipoAccion);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
