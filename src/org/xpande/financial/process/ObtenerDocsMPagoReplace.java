package org.xpande.financial.process;

import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZMedioPagoReplace;

/**
 * Proceso para obtener documentos a considerar en el proceso de Reemplazo de Medios de Pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/21/17.
 */
public class ObtenerDocsMPagoReplace extends SvrProcess {

    MZMedioPagoReplace medioPagoReplace = null;

    @Override
    protected void prepare() {
        this.medioPagoReplace = new MZMedioPagoReplace(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.medioPagoReplace.getDocuments();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
