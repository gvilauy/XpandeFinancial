package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas de movimientos de caja.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/22/20.
 */
public class MZMovCajaLin extends X_Z_MovCajaLin{

    public MZMovCajaLin(Properties ctx, int Z_MovCajaLin_ID, String trxName) {
        super(ctx, Z_MovCajaLin_ID, trxName);
    }

    public MZMovCajaLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        MZMovCaja movCaja = (MZMovCaja) this.getZ_MovCaja();

        // Nuevos registros o cambio en valor de importe en moneda de transacción
        if ((newRecord) || (is_ValueChanged(X_Z_MovBancoLin.COLUMNNAME_TotalAmtMT))){

            // Actualizo total del cabezal
            movCaja.updateTotals();
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return false;

        // Actualizo totales del documento
        MZMovCaja movCaja = (MZMovCaja) this.getZ_MovCaja();
        movCaja.updateTotals();

        return true;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro de tener la organización correcta
        MZMovCaja movCaja = (MZMovCaja) this.getZ_MovCaja();
        this.setAD_Org_ID(movCaja.getAD_Org_ID());

        return true;
    }
}
