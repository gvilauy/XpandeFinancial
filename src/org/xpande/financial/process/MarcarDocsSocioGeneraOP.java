package org.xpande.financial.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.financial.model.MZGeneraOrdenPagoSocio;
import org.xpande.financial.model.X_Z_MedioPagoFolio;

import java.math.BigDecimal;

/**
 * Proceso para marcar / desmarcar documentos de un socio de negocio en el proceso de generación de ordenes de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/13/17.
 */
public class MarcarDocsSocioGeneraOP extends SvrProcess {

    private MZGeneraOrdenPagoSocio generaOrdenPagoSocio = null;
    private String accion = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("AccionMarcaDoc")){
                        this.accion = (String)para[i].getParameter();
                    }
                }
            }
        }

        this.generaOrdenPagoSocio = new MZGeneraOrdenPagoSocio(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        boolean marcarRegistros = this.accion.equalsIgnoreCase("MARCAR");

        String message = this.generaOrdenPagoSocio.marcarDocumentos(marcarRegistros);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";

    }
}
