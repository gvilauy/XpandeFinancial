package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZLoadMedioPago;

/**
 * Proceso para carga masiva de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/2/19.
 */
public class InterfaceCargaMPago extends SvrProcess {

    MZLoadMedioPago loadMedioPago = null;

    @Override
    protected void prepare() {

        this.loadMedioPago = new MZLoadMedioPago(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{

            if ((this.loadMedioPago.getFileName() == null) || (this.loadMedioPago.getFileName().trim().equalsIgnoreCase(""))){
                return "@Error@ Debe indicar archivo a procesar ";
            }

            this.loadMedioPago.executeInterface();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }

}
