package org.xpande.financial.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.X_Z_OrdenPagoMedio;
import org.xpande.financial.model.X_Z_PagoMedioPago;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Callouts relacionados a Pagos y Cobros.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/29/18.
 */
public class CalloutPago extends CalloutEngine {

    /***
     * Al indicar cuenta bancaria en un pago/cobro, se calcula el monto diferencia entre total de medios
     * de pago y total documento. Este monto se setea en el campo de Importe de esta linea de medio de pago.
     * Xpande. Created by Gabriel Vila on 1/29/18.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setMedioPagoMonto(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        BigDecimal amtMedioPago = (BigDecimal) mTab.getValue(X_Z_PagoMedioPago.COLUMNNAME_TotalAmtMT);
        if ((amtMedioPago == null) || (amtMedioPago.compareTo(Env.ZERO) <= 0)){
            int zPagoID = Env.getContextAsInt(ctx, WindowNo, "Z_Pago_ID");

            MZPago pago = new MZPago(ctx, zPagoID, null);
            if ((pago != null) && (pago.get_ID() > 0)){

                BigDecimal totalMedioPago = pago.getTotalMediosPago();
                BigDecimal totalDocs = pago.getPayAmt();

                if (totalMedioPago == null) totalMedioPago = Env.ZERO;
                if (totalDocs == null) totalDocs = Env.ZERO;

                amtMedioPago = totalDocs.subtract(totalMedioPago);
                if (amtMedioPago.compareTo(Env.ZERO) < 0) amtMedioPago = Env.ZERO;

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TotalAmtMT, amtMedioPago);
            }
        }

        return "";
    }

}
