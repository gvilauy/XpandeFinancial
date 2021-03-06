package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para folios de medios de pago.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/16/17.
 */
public class MZMedioPagoFolio extends X_Z_MedioPagoFolio {

    public MZMedioPagoFolio(Properties ctx, int Z_MedioPagoFolio_ID, String trxName) {
        super(ctx, Z_MedioPagoFolio_ID, trxName);
    }

    public MZMedioPagoFolio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Genera items para este folio de medio de pago.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    public String generateItems() {

        String message = null;

        try{

            for (long i = this.getNumeroDesde(); i <= this.getNumeroHasta(); i++){
                MZMedioPagoItem item = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
                item.setZ_MedioPago_ID(this.getZ_MedioPago_ID());
                item.setAD_Org_ID(this.getAD_Org_ID());
                item.setZ_MedioPagoFolio_ID(this.get_ID());
                item.setC_BankAccount_ID(this.getC_BankAccount_ID());
                item.setC_Currency_ID(this.getC_Currency_ID());
                item.setNroMedioPago(String.valueOf(i));
                item.setIsReceipt(false);
                item.setDocumentSerie(this.getDocumentSerie());
                item.setEmitido(false);
                item.setTotalAmt(Env.ZERO);
                item.setIsOwn(true);
                item.saveEx();
            }

            this.setIsExecuted(true);
            this.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Obtiene y retorna siguiente item de medio de pago disponible para este folio.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    public MZMedioPagoItem getCurrentNext(){

        String whereClause = X_Z_MedioPagoItem.COLUMNNAME_Z_MedioPagoFolio_ID + " =" + this.get_ID() +
                " AND " + X_Z_MedioPagoItem.COLUMNNAME_Emitido + " ='N'";

        if (this.getNroMedioPagoDesde() != null){
            whereClause += " AND " + X_Z_MedioPagoItem.COLUMNNAME_NroMedioPago + " >='" + this.getNroMedioPagoDesde() + "' ";
        }

        MZMedioPagoItem model = new Query(getCtx(), I_Z_MedioPagoItem.Table_Name, whereClause, get_TrxName()).setOrderBy(" NroMedioPago ").first();

        return model;

    }

    /***
     * Actualiza estado de disponibilidad segun haya algun item de este folio como no emitido.
     * Xpande. Created by Gabriel Vila on 2/1/18.
     */
    public void updateDisponibilidad(){

        String sql = "";

        try{

            // Consulto si tengo algun item de este folio que no este emitido
            sql = " select count(*) as contador " +
                    " from z_mediopagoitem " +
                    " where z_mediopagofolio_id =" + this.get_ID() +
                    " and emitido ='N'";

            int contador = DB.getSQLValueEx(get_TrxName(), sql);

            // Si todos estan emitidos, este folio no esta mas disponible.
            if (contador <= 0){
                this.setDisponible(false);
                this.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }


    /***
     * Obtiene y retorna lista de itemas de medios de pago emitidos y asociados a este folio.
     * Xpande. Created by Gabriel Vila on 1/31/19.
     * @return
     */
    public List<MZMedioPagoItem> getItemsEmitidos(){

        String whereClause = X_Z_MedioPagoItem.COLUMNNAME_Z_MedioPagoFolio_ID + " =" + this.get_ID() +
                " AND " + X_Z_MedioPagoItem.COLUMNNAME_Emitido + " ='Y' ";

        List<MZMedioPagoItem> lines = new Query(getCtx(), I_Z_MedioPagoItem.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }

    @Override
    protected boolean beforeDelete() {

        try{

            // Obtengo lista de items emitidos de este folio.
            // Si tengo al menos uno, aviso y no hago nada.
            List<MZMedioPagoItem> itemList = this.getItemsEmitidos();
            if (itemList.size() > 0){

                log.saveError("ATENCIÓN", "No es posible eliminar Folio ya que tiene Items Emitidos.");
                return false;
            }

            // ELimino primero items, para que pueda eliminarse este folio.
            String action = " delete from z_mediopagoitem where z_mediopagofolio_id =" + this.get_ID();
            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return true;
    }
}
