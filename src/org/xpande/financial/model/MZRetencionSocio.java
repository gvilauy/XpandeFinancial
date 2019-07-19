package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.utils.CurrencyUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de retencion para socios de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/1/17.
 */
public class MZRetencionSocio extends X_Z_RetencionSocio {

    public MZRetencionSocio(Properties ctx, int Z_RetencionSocio_ID, String trxName) {
        super(ctx, Z_RetencionSocio_ID, trxName);
    }

    public MZRetencionSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de retenciones asociadas a un determinado socio de negocio recibido.
     * Xpande. Created by Gabriel Vila on 8/2/17.
     * @param ctx
     * @param cBPartnerID
     * @param trxName
     * @return
     */
    public static List<MZRetencionSocioBPartner> getRetencionesBPartner(Properties ctx, int cBPartnerID, String trxName){

        String whereClause = X_Z_RetencionSocioBPartner.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID;

        List<MZRetencionSocioBPartner> lines = new Query(ctx, I_Z_RetencionSocioBPartner.Table_Name, whereClause, trxName)
                .setOnlyActiveRecords(true).list();

        return lines;
    }


    /***
     * Verifica si una determinada invoice recibida es aplicable a retenciones o no.
     * Xpande. Created by Gabriel Vila on 9/18/17.
     * @param ctx
     * @param cInvoiceID
     * @param trxName
     * @return
     */
    public static boolean invoiceAplicanRetenciones(Properties ctx, int cInvoiceID, String trxName) {

        boolean value = false;
        String sql = "";

        try{

            // Instancio invoice recibida
            MInvoice invoice = new MInvoice(ctx, cInvoiceID, trxName);
            if ((invoice == null) || (invoice.get_ID() <= 0)){
                return false;
            }



            // Obtengo retenciones asociadas al socio de negocio de la invoice recibida
            List<MZRetencionSocioBPartner> retencionesBP = MZRetencionSocio.getRetencionesBPartner(ctx, invoice.getC_BPartner_ID(), null);
            for (MZRetencionSocioBPartner retencionBP: retencionesBP){
                MZRetencionSocio retencionSocio = (MZRetencionSocio) retencionBP.getZ_RetencionSocio();

                // Si la retencion no es aplicable en impuestos
                if (retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_IMPUESTOS)){
                    // Verifico que este invoice tenga uno de los impuestos contra los cuales aplica esta retencion
                    // En cuyo caso retorno true.
                    sql = " select count(invt.*) as contador " +
                            "from c_invoicetax invt " +
                            "inner join c_tax tax on invt.c_tax_id = tax.c_tax_id " +
                            "where invt.c_invoice_id =" + invoice.get_ID() +
                            "and tax.c_taxcategory_id in " +
                            "(select c_taxcategory_id from z_retencionsociotax " +
                            "where z_retencionsocio_id =" + retencionSocio.get_ID() + ")";
                    int contador = DB.getSQLValueEx(null, sql);
                    if (contador > 0){
                        return true;
                    }
                }
                // Si la retención es aplicable al subtotal
                else if (retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_SUBTOTAL)){
                    // Si la retención tiene topo en unidades indexadas
                    if ((retencionSocio.getAmtUnidadIndexada() != null) && (retencionSocio.getAmtUnidadIndexada().compareTo(Env.ZERO) > 0)){
                        // Obtengo monto en moneda nacional equivalente al monto tope en unidades indexadas
                        MClient client = new MClient(ctx, invoice.getAD_Client_ID(), null);
                        MAcctSchema acctSchema = (MAcctSchema) client.getAcctSchema();
                        BigDecimal multiplyRate = CurrencyUtils.getMultiplyRateToUnidadIndexada(ctx, invoice.getAD_Client_ID(), 0,
                                acctSchema.getC_Currency_ID(), 114, invoice.getDateInvoiced(), null);
                        if (multiplyRate != null){
                            BigDecimal amtUnidIndex = retencionSocio.getAmtUnidadIndexada().multiply(multiplyRate).setScale(2, RoundingMode.HALF_UP);
                            BigDecimal subTotalInv = (BigDecimal) invoice.get_Value("AmtSubtotal");
                            if (amtUnidIndex.compareTo(subTotalInv) > 0){
                                return false;
                            }
                        }
                        else{
                            return true;
                        }
                    }
                    else {
                        return true;
                    }
                }
                else {
                    return true;
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return value;
    }


    /***
     * Obtiene y retorna modelo de parametrización contable para esta Retención.
     * Xpande. Created by Gabriel Vila on 8/28/18.
     * @return
     */
    public MZRetencionSocioAcct getAcct(){

        String whereClause = X_Z_RetencionSocio_Acct.COLUMNNAME_Z_RetencionSocio_ID + " = " + this.get_ID();

        MZRetencionSocioAcct model = new Query(getCtx(), I_Z_RetencionSocio_Acct.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

}
