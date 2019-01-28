package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.financial.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/28/19.
 */
public class FixInvoicesCash extends SvrProcess {

    @Override
    protected void prepare() {

    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String message = null;

        try{

            sql = " select c_invoice_id from aux_cc order by c_invoice_id ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), get_TrxName());

                // Genero pago o cobro que refleje el medio de pago efectivo seleccionado, en la caja indicada.
                MZPago pago = new MZPago(getCtx(), 0, get_TrxName());
                message = pago.generateFromCashInvoice(invoice);
                if (message != null){
                    throw new AdempiereException(message);
                }

        	}

            /*
            String action = "";
            sql = " select * from aux_ff order by z_pago_id ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZPago pago = new MZPago(getCtx(), rs.getInt("z_pago_id"), get_TrxName());
                MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), rs.getInt("z_pagomediopago_id"), get_TrxName());
                // Por las dudas me aseguro que no venga un medio de pago que ya fue emitido en una orden de pago
                if (pagoMedioPago.getZ_OrdenPago_ID() > 0){
                    continue;
                }

                if ((pagoMedioPago.getTotalAmt() == null) || (pagoMedioPago.getTotalAmt().compareTo(Env.ZERO) == 0)){
                    continue;
                }

                // Instancio modelo de medio de pago, si es que tengo.
                MZMedioPago medioPago = (MZMedioPago) pagoMedioPago.getZ_MedioPago();
                if ((medioPago == null) || (medioPago.get_ID() <= 0)){
                    continue;
                }

                // Si este medio de pago no se emite por definición
                if (!medioPago.isTieneEmision()){
                    continue;
                }

                if (pagoMedioPago.getDateEmitted() == null){
                    pagoMedioPago.setDateEmitted(pago.getDateDoc());
                }

                if (pagoMedioPago.getDueDate() == null){
                    pagoMedioPago.setDueDate(pagoMedioPago.getDateEmitted());
                }

                // Si no tengo item de medio de pago, y en caso de que este medio de pago requiera folio,
                // entonces obtengo el siguiente disponible del folio
                MZMedioPagoItem medioPagoItem = null;
                if (pagoMedioPago.getZ_MedioPagoItem_ID() <= 0){

                    if (medioPago.isTieneFolio()){
                        if (pagoMedioPago.getZ_MedioPagoFolio_ID() <= 0){
                            return "Medio de pago no tiene Libreta asociada.";
                        }
                        medioPagoItem = ((MZMedioPagoFolio) pagoMedioPago.getZ_MedioPagoFolio()).getCurrentNext();
                        if ((medioPagoItem == null) || (medioPagoItem.get_ID() <= 0)){
                            return "Libreta no tiene medios de pago disponibles para utilizar.";
                        }
                        pagoMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
                    }
                    else{
                        // Creo el item de medio de pago sin folio asociado
                        medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
                        medioPagoItem.setZ_MedioPago_ID(pagoMedioPago.getZ_MedioPago_ID());
                        medioPagoItem.setAD_Org_ID(pago.getAD_Org_ID());
                        if (pagoMedioPago.getC_BankAccount_ID() > 0){
                            medioPagoItem.setC_BankAccount_ID(pagoMedioPago.getC_BankAccount_ID());
                        }
                        if (pagoMedioPago.getC_CashBook_ID() > 0){
                            medioPagoItem.setC_CashBook_ID(pagoMedioPago.getC_CashBook_ID());
                        }

                        medioPagoItem.setC_Currency_ID(pagoMedioPago.getC_Currency_ID());

                        if ((pagoMedioPago.getDocumentNoRef() == null) || (pagoMedioPago.getDocumentNoRef().trim().equalsIgnoreCase(""))){

                            medioPagoItem.setNroMedioPago(String.valueOf(pagoMedioPago.get_ID()));

                            // Seteo numero de medio de pago en la linea de medio de pago
                            action = " update z_pagomediopago set documentnoref ='" + medioPagoItem.getNroMedioPago() + "' " +
                                    " where z_pagomediopago_id =" + pagoMedioPago.get_ID();
                            DB.executeUpdateEx(action, get_TrxName());

                        }
                        else{
                            medioPagoItem.setNroMedioPago(pagoMedioPago.getDocumentNoRef());
                        }

                        medioPagoItem.setDateEmitted(pagoMedioPago.getDateEmitted());
                        medioPagoItem.setDueDate(pagoMedioPago.getDueDate());

                        medioPagoItem.setIsReceipt(false);
                        medioPagoItem.setEmitido(false);
                        medioPagoItem.setTotalAmt(pagoMedioPago.getTotalAmt());
                        medioPagoItem.setIsOwn(true);
                        medioPagoItem.setC_BPartner_ID(pago.getC_BPartner_ID());
                    }
                    medioPagoItem.saveEx();
                }
                else{
                    medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
                }

                pagoMedioPago.saveEx();


                // Realizo emisión para este medio de pago a considerar
                if (!medioPagoItem.isEmitido()){
                    MZEmisionMedioPago emisionMedioPago = new MZEmisionMedioPago(getCtx(), 0, get_TrxName());
                    emisionMedioPago.setZ_MedioPago_ID(pagoMedioPago.getZ_MedioPago_ID());
                    emisionMedioPago.setAD_Org_ID(pagoMedioPago.getAD_Org_ID());

                    if (pagoMedioPago.getZ_MedioPagoFolio_ID() > 0){
                        emisionMedioPago.setZ_MedioPagoFolio_ID(pagoMedioPago.getZ_MedioPagoFolio_ID());
                    }

                    if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){
                        emisionMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
                        emisionMedioPago.setC_Currency_ID(medioPagoItem.getC_Currency_ID());

                        if (medioPagoItem.getC_BankAccount_ID() > 0){
                            emisionMedioPago.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
                        }
                        if (medioPagoItem.getC_CashBook_ID() > 0){
                            emisionMedioPago.setC_CashBook_ID(medioPagoItem.getC_CashBook_ID());
                        }

                    }
                    else{
                        emisionMedioPago.setReferenceNo(pagoMedioPago.getDocumentNoRef());
                        emisionMedioPago.setC_Currency_ID(pagoMedioPago.getC_Currency_ID());

                        if (pagoMedioPago.getC_BankAccount_ID() > 0){
                            emisionMedioPago.setC_BankAccount_ID(pagoMedioPago.getC_BankAccount_ID());
                        }
                        if (pagoMedioPago.getC_CashBook_ID() > 0){
                            emisionMedioPago.setC_CashBook_ID(pagoMedioPago.getC_CashBook_ID());
                        }
                    }

                    emisionMedioPago.setZ_Pago_ID(pago.get_ID());
                    emisionMedioPago.setC_BPartner_ID(pago.getC_BPartner_ID());
                    emisionMedioPago.setDateDoc(pago.getDateDoc());
                    emisionMedioPago.setDateEmitted(pagoMedioPago.getDateEmitted());
                    emisionMedioPago.setDueDate(pagoMedioPago.getDueDate());
                    emisionMedioPago.setTotalAmt(pagoMedioPago.getTotalAmt());
                    emisionMedioPago.saveEx();

                    // Completo documento de emisión de medio de pago
                    if (!emisionMedioPago.processIt(DocAction.ACTION_Complete)){
                        message = emisionMedioPago.getProcessMsg();
                        return message;
                    }
                    emisionMedioPago.saveEx();
                }

                // Marco medio de pago como entregado en este documento de pago
                medioPagoItem.setZ_Pago_ID(pago.get_ID());
                medioPagoItem.setEntregado(true);
                medioPagoItem.saveEx();
            }
            */

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return "OK.";
    }
}
