package org.xpande.financial.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;
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

            BigDecimal totalMedioPago = Env.ZERO;
            BigDecimal totalDocs = Env.ZERO;

            int zPagoID = Env.getContextAsInt(ctx, WindowNo, "Z_Pago_ID");
            if (zPagoID > 0){
                MZPago pago = new MZPago(ctx, zPagoID, null);
                if ((pago != null) && (pago.get_ID() > 0)) {
                    totalMedioPago = pago.getTotalMediosPago();
                    totalDocs = pago.getPayAmt();
                }
            }
            else{
                int cInvoiceID = Env.getContextAsInt(ctx, WindowNo, "C_Invoice_ID");
                if (cInvoiceID > 0){
                    MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
                    if ((invoice != null) && (invoice.get_ID() > 0)) {
                        totalMedioPago = (BigDecimal) invoice.get_Value("TotalMediosPago");
                        totalDocs = invoice.getGrandTotal();
                    }
                }
            }

            if (totalMedioPago == null) totalMedioPago = Env.ZERO;
            if (totalDocs == null) totalDocs = Env.ZERO;

            amtMedioPago = totalDocs.subtract(totalMedioPago);
            if (amtMedioPago.compareTo(Env.ZERO) < 0) amtMedioPago = Env.ZERO;

            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TotalAmtMT, amtMedioPago);
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

            boolean isSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));

            // Seteo comportamiento del medio de pago según sea un documento de pago o cobro
            if (!isSOTrx) {

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecEmi, medioPago.isTieneFecEmi());
                if (!medioPago.isTieneFecEmi()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DateEmitted, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecVenc, medioPago.isTieneFecVenc());
                if (!medioPago.isTieneFecVenc()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DueDate, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCtaBco, medioPago.isTieneCtaBco());
                if (!medioPago.isTieneCtaBco()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_C_BankAccount_ID, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCaja, medioPago.isTieneCaja());
                if (!medioPago.isTieneCaja()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_C_CashBook_ID, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFolio, medioPago.isTieneFolio());
                if (!medioPago.isTieneFolio()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_Z_MedioPagoFolio_ID, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneBanco, medioPago.isTieneBanco());
                if (!medioPago.isTieneBanco()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_C_Bank_ID, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneNroRef, medioPago.isTieneNroRef());
                if (!medioPago.isTieneNroRef()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DocumentNoRef, null);
                }

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneIdent, medioPago.isTieneIdent());
                if (!medioPago.isTieneIdent()){
                    mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_Z_MedioPagoIdent_ID, null);
                }


            } else {

                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecEmi, medioPago.isTieneFecEmiCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFecVenc, medioPago.isTieneFecVencCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCtaBco, medioPago.isTieneCtaBcoCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneCaja, medioPago.isTieneCajaCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneFolio, medioPago.isTieneFolioCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneBanco, medioPago.isTieneBancoCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneNroRef, medioPago.isTieneNroRefCobro());
                mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TieneIdent, medioPago.isTieneIdentCobro());
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


    /***
     * Setea atributos asociados al item de medios de pago
     * Xpande. Created by Gabriel Vila on 1/31/18.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setItemMedioPagoInfo(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zMedioPagoItemID = (Integer) value;

        if (zMedioPagoItemID <= 0) return "";

        MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(ctx, zMedioPagoItemID, null);

        if (medioPagoItem.isEmitido()){
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DateEmitted, medioPagoItem.getDateEmitted());
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_DueDate, medioPagoItem.getDueDate());
            mTab.setValue(X_Z_PagoMedioPago.COLUMNNAME_TotalAmt, medioPagoItem.getTotalAmt());
        }

        return "";
    }

}