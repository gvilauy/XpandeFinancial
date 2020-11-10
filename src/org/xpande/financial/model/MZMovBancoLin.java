package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas de movimientos bancarios.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/10/20.
 */
public class MZMovBancoLin extends X_Z_MovBancoLin {

    public MZMovBancoLin(Properties ctx, int Z_MovBancoLin_ID, String trxName) {
        super(ctx, Z_MovBancoLin_ID, trxName);
    }

    public MZMovBancoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        MZMovBanco movBanco = (MZMovBanco) this.getZ_MovBanco();

        // Nuevos registros o cambio en valor de importe en moneda de transacción
        if ((newRecord) || (is_ValueChanged(X_Z_MovBancoLin.COLUMNNAME_TotalAmtMT))){

            // Actualizo total del cabezal
            movBanco.updateTotals();
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return false;

        // Actualizo totales del documento
        MZMovBanco movBanco = (MZMovBanco) this.getZ_MovBanco();
        movBanco.updateTotals();

        return true;
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro de tener la organización correcta
        MZMovBanco movBanco = (MZMovBanco) this.getZ_MovBanco();
        this.setAD_Org_ID(movBanco.getAD_Org_ID());

        return true;
    }
}
