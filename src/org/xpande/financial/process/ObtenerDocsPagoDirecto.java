package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZPago;

/**
 * Proceso que dispara la obtención de documentos con saldo para realizar un pago/cobro.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/24/18.
 */
public class ObtenerDocsPagoDirecto extends SvrProcess {

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

        String message = this.pago.getDocumentos(tipoAccion);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
