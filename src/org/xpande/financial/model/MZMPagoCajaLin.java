package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para linea de documento de ingreso de medios de pago por linea de caja.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/29/20.
 */
public class MZMPagoCajaLin extends X_Z_MPagoCajaLin{

    public MZMPagoCajaLin(Properties ctx, int Z_MPagoCajaLin_ID, String trxName) {
        super(ctx, Z_MPagoCajaLin_ID, trxName);
    }

    public MZMPagoCajaLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        String message = this.validacionesCampos();
        if (message != null){
            log.saveError("ATENCIÓN", message);
            return false;
        }

        // Me aseguro de tener la organización correcta
        MZMPagoCaja pagoCaja = (MZMPagoCaja) this.getZ_MPagoCaja();
        this.setAD_Org_ID(pagoCaja.getAD_Org_ID());

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        if ((newRecord) || (is_ValueChanged(X_Z_MPagoCajaLin.COLUMNNAME_TotalAmt))){
            // Actualizo totales del documento
            MZMPagoCaja pagoCaja = (MZMPagoCaja) this.getZ_MPagoCaja();
            pagoCaja.updateTotals();
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return false;

        // Actualizo totales del documento
        MZMPagoCaja pagoCaja = (MZMPagoCaja) this.getZ_MPagoCaja();
        pagoCaja.updateTotals();

        return true;
    }

    /***
     * Valida campos de este modelo.
     * Xpande. Created by Gabriel Vila on 7/30/20.
     * @return
     */
    private String validacionesCampos(){

        try{

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Validaciones de campos obligatorios segun atributos obtenidos del medio de pago
            if (this.getC_Bank_ID() <= 0){
                return "Debe indicar Banco del Medio de Pago";
            }

            if (this.getDateEmitted() == null){
                return "Debe indicar Fecha de Emisión del Medio de Pago";
            }

            if (this.getDueDate() == null){
                return "Debe indicar Fecha de Vencimiento del Medio de Pago";
            }

            if (this.getDateEmitted() != null){
                if (this.getDueDate().before(this.getDateEmitted())){
                    return "La Fecha de Vencimiento del Medio de Pago no puede ser menor a la Fecha de Emisión del mismo";
                }
            }

            if (this.getTotalAmt() == null){
                return "Debe indicar importe para este medio de pago.";
            }

            if (this.getTotalAmt().compareTo(Env.ZERO) <= 0){
                return "Debe indicar importe mayor a cero para este medio de pago.";
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return null;
    }

}
