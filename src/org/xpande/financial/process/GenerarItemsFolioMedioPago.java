package org.xpande.financial.process;

import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZMedioPagoFolio;

/**
 * Proceso para generar items de un folio de medio de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class GenerarItemsFolioMedioPago extends SvrProcess{

    private MZMedioPagoFolio medioPagoFolio = null;

    @Override
    protected void prepare() {

        this.medioPagoFolio = new MZMedioPagoFolio(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.medioPagoFolio.generateItems();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }
}
