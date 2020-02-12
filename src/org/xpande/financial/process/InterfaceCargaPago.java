package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZLoadMedioPago;
import org.xpande.financial.model.MZLoadPago;

/**
 * Proceso para cargar pagos / cobros desde interface.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/10/20.
 */
public class InterfaceCargaPago extends SvrProcess {

    MZLoadPago loadPago = null;

    @Override
    protected void prepare() {

        this.loadPago = new MZLoadPago(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{

            if ((this.loadPago.getFileName() == null) || (this.loadPago.getFileName().trim().equalsIgnoreCase(""))){
                return "@Error@ Debe indicar archivo a procesar ";
            }

            this.loadPago.executeInterface();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }

}
