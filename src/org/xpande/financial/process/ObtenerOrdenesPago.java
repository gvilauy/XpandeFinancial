package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZPago;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/12/18.
 */
public class ObtenerOrdenesPago extends SvrProcess {

    private MZPago pago = null;

    @Override
    protected void prepare() {

        this.pago = new MZPago(getCtx(), this.getRecord_ID(), get_TrxName());
    }


    @Override
    protected String doIt() throws Exception {

        String message = this.pago.getOrdenesPago();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
