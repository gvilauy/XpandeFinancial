package org.xpande.financial.process;

import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZGeneraOrdenPago;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class GenerarOrdenesPago extends SvrProcess {

    MZGeneraOrdenPago generaOrdenPago = null;

    @Override
    protected void prepare() {

        this.generaOrdenPago = new MZGeneraOrdenPago(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.generaOrdenPago.generateOrdenes();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";

    }
}
