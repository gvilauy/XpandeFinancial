package org.xpande.financial.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.xpande.financial.model.*;

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
        if ((amtMedioPago == null) || (amtMedioPago.compareTo(Env.ZERO) <= 0)) {
            int zPagoID = Env.getContextAsInt(ctx, WindowNo, "Z_Pago_ID");

            MZPago pago = new MZPago(ctx, zPagoID, null);
            if ((pago != null) && (pago.get_ID() > 0)) {

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


    /***
     * Setea atributos asociados al medio de pago seleccionado.
     * Xpande. Created by Gabriel Vila on 1/31/18.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setMedioPagoInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zMedioPagoID = (Integer) value;

        if (zMedioPagoID <= 0) return "";

        MZMedioPago medioPago = new MZMedioPago(ctx, zMedioPagoID, null);

        if ((medioPago != null) && (medioPago.get_ID() > 0)) {

            int zPagoID = Env.getContextAsInt(ctx, WindowNo, "Z_Pago_ID");
            MZPago pago = new MZPago(ctx, zPagoID, null);
            if ((pago != null) && (pago.get_ID() > 0)) {

                // Seteo comportamiento del medio de pago según sea un documento de pago o cobro
                if (!pago.isSOTrx()) {

                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecEmi, medioPago.isTieneFecEmi());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecVenc, medioPago.isTieneFecVenc());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCtaBco, medioPago.isTieneCtaBco());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCaja, medioPago.isTieneCaja());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFolio, medioPago.isTieneFolio());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneBanco, medioPago.isTieneBanco());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneNroRef, medioPago.isTieneNroRef());

                } else {

                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecEmi, medioPago.isTieneFecEmiCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecVenc, medioPago.isTieneFecVencCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCtaBco, medioPago.isTieneCtaBcoCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCaja, medioPago.isTieneCajaCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFolio, medioPago.isTieneFolioCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneBanco, medioPago.isTieneBancoCobro());
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneNroRef, medioPago.isTieneNroRefCobro());
                }
            }

        }

        return "";
    }


    /***
     * Setea atributos asociados al folio de medios de pago
     * Xpande. Created by Gabriel Vila on 1/31/18.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setFolioInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zMedioPagoFolioID = (Integer) value;

        if (zMedioPagoFolioID <= 0) return "";

        MZMedioPagoFolio medioPagoFolio = new MZMedioPagoFolio(ctx, zMedioPagoFolioID, null);

        mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_EmisionManual, medioPagoFolio.isEmisionManual());

        if (medioPagoFolio.isEmisionManual()){
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_Z_MedioPagoItem_ID, null);
        }

        return "";
    }

}