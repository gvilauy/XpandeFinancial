package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZGeneraOrdenPago;
import org.xpande.financial.model.X_Z_MedioPagoFolio;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class ImprimirChequesGenPago extends SvrProcess {

    private MZGeneraOrdenPago generaOrdenPago = null;
    private int zMedioPagoFolioID = 0;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase(X_Z_MedioPagoFolio.COLUMNNAME_Z_MedioPagoFolio_ID)){
                        this.zMedioPagoFolioID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }

        this.generaOrdenPago = new MZGeneraOrdenPago(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.generaOrdenPago.imprimirCheques(this.zMedioPagoFolioID);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
