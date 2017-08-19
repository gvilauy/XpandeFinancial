package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
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
}