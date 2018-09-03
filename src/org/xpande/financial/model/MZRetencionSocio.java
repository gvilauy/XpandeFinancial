package org.xpande.financial.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MInvoice;
import org.compiere.model.Query;
import org.compiere.util.DB;

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
                // Si la retencion no es aplicable en impuestos, se asume que esta invoice es aplicable a retenciones. Salgo en true.
                if (!retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_IMPUESTOS)){
                    return true;
                }
                else{
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
