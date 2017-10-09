package org.xpande.financial.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MDocType;
import org.compiere.util.Env;
import org.xpande.financial.model.MZOrdenPago;
import org.xpande.financial.model.X_Z_OrdenPagoMedio;
import org.xpande.financial.model.X_Z_ResguardoSocio;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Callouts relacionados a Ordenes de Pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/9/17.
 */
public class CalloutOrdenPago extends CalloutEngine {


    /***
     * Al indicar cuenta bancaria en medio de pago de una orden de pago, se calcula el monto diferencia entre total de medios
     * de pago y total documentos. Este monto se setea en el campo de Importe de esta linea de medio de pago.
     * Xpande. Created by Gabriel Vila on 8/9/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String docType(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        BigDecimal amtMedioPago = (BigDecimal) mTab.getValue(X_Z_OrdenPagoMedio.COLUMNNAME_TotalAmt);
        if ((amtMedioPago == null) || (amtMedioPago.compareTo(Env.ZERO) <= 0)){
            int zOrdenPagoID = Env.getContextAsInt(ctx, WindowNo, "Z_OrdenPago_ID");

            MZOrdenPago ordenPago = new MZOrdenPago(ctx, zOrdenPagoID, null);
            if ((ordenPago != null) && (ordenPago.get_ID() > 0)){

                BigDecimal totalMedioPago = ordenPago.getAmtPaymentRule();
                BigDecimal totalDocs = ordenPago.getTotalAmt();

                if (totalMedioPago == null) totalMedioPago = Env.ZERO;
                if (totalDocs == null) totalDocs = Env.ZERO;

                amtMedioPago = totalDocs.subtract(totalMedioPago);
                if (amtMedioPago.compareTo(Env.ZERO) < 0) amtMedioPago = Env.ZERO;

                mTab.setValue(X_Z_OrdenPagoMedio.COLUMNNAME_TotalAmt, amtMedioPago);
            }
        }

        return "";
    }

}
