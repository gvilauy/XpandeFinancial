package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.utils.NumberToString;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para item de un determinado medio de pago trazable.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZMedioPagoItem extends X_Z_MedioPagoItem {

    public MZMedioPagoItem(Properties ctx, int Z_MedioPagoItem_ID, String trxName) {
        super(ctx, Z_MedioPagoItem_ID, trxName);
    }

    public MZMedioPagoItem(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Setea leyendas de impresión para monto del medio de pago.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     */
    public void setLeyendasImpresion(){

        int topCharacters = 70;

        try{

            this.setLeyendaImpresion1(null);
            this.setLeyendaImpresion2(null);

            NumberToString conv = new NumberToString();

            String cadena = conv.getStringOfBigDecimal(this.getTotalAmt());

            if (cadena.length() <= topCharacters){
                this.setLeyendaImpresion1(cadena);
                return;
            }

            int posHasta1 = cadena.indexOf(" ", topCharacters);
            if (posHasta1 < 0) posHasta1 = topCharacters;
            int posDesde1 = cadena.lastIndexOf(" ", topCharacters);
            if (posDesde1 < 0) posDesde1 = topCharacters;

            int posSep1 = ((posHasta1 - topCharacters) < ((posDesde1 - topCharacters) * -1)) ? posHasta1 : posDesde1;

            this.setLeyendaImpresion1(cadena.substring(0, posSep1));
            this.setLeyendaImpresion2(cadena.substring(posSep1 + 1));

        }
        catch (Exception e){
            throw new AdempiereException();
        }
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Valido importe siempre mayor a cero
        if ((this.getTotalAmt() == null) || (this.getTotalAmt().compareTo(Env.ZERO) <= 0)){
            log.saveError("ATENCIÓN", "Debe indicar importe mayor a cero en medio de pago.");
            return false;
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        // Si este item esta asociado a un folio, y este item ademas esta emitido, refresco disponibilidad del folio si es necesario.
        if (this.getZ_MedioPagoFolio_ID() > 0){
            if (this.isEmitido()){
                MZMedioPagoFolio medioPagoFolio = (MZMedioPagoFolio) this.getZ_MedioPagoFolio();
                medioPagoFolio.updateDisponibilidad();
            }
        }

        return true;
    }

    /***
     * Imprime medio de pago.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     */
    public void imprimir() {
        /*

        try {

            MPInstance instance = new MPInstance(Env.getCtx(), adProcessID, 0);
            instance.saveEx();

            ProcessInfo pi = new ProcessInfo ("PrintCheck", adProcessID);
            pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());

            MPInstancePara para = new MPInstancePara(instance, 10);
            para.setParameter("UY_MediosPago_ID", new BigDecimal(uyMediosPagoID));
            para.saveEx();

            ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
            worker.start();

            Thread.sleep(2000);

            para.deleteEx(true, null);
            instance.deleteEx(true, null);

            java.lang.Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Exception Controlada para Tiempo de Impresion de Cheque");
        }

        */

    }

    /***
     * Informa si este medio de pago esta emitido y nada mas, o si por el contraro tiene otro estado posterior.
     * Xpande. Created by Gabriel Vila on 11/14/18.
     * @return
     */
    public boolean IsOnlyEmitido(){

        try{

            if (!this.isEmitido()){
                return false;
            }

            if (this.isEntregado() || this.isAnulado() || this.isDepositado() || this.isConciliado() || this.isReemplazado()){
                return false;
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }


    /***
     * Dejo este medio de pago sin ninguna actividad y desafectado de socio de negocio.
     * Xpande. Created by Gabriel Vila on 11/14/18
     * @return
     */
    public String desafectar() {

        String message = null, action = "";

        try{

            // Desafecto medio de pago
            action = " update z_mediopagoitem set emitido ='N', c_bpartner_id = null, dateemitted = null, duedate = null, " +
            " leyendaimpresion1 = null, leyendaimpresion2 = null, totalamt = 0, isprinted='N', z_emisionmediopago_id = null " +
            " where z_mediopagoitem_id =" + this.get_ID();

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }
}
