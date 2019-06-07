package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.xpande.financial.model.MZFinancialConfig;
import org.xpande.financial.model.MZOrdenPago;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.X_Z_MedioPagoFolio;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Proceso que permite la carga rápida de Recibos de Proveedores con Ordenes de Pago asociadas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/7/19.
 */
public class ReciboProveedorAgil extends SvrProcess {

    private String nroReciboProveedor = null;
    private String nroOrdenPago1 = null;
    private String nroOrdenPago2 = null;
    private String nroOrdenPago3 = null;
    private String nroOrdenPago4 = null;
    private String nroOrdenPago5 = null;
    private Timestamp fechaRecibo = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("NroRecibo")){
                        this.nroReciboProveedor = (String) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.fechaRecibo = (Timestamp)para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("NroAux1")){
                        this.nroOrdenPago1 = (String) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("NroAux2")){
                        this.nroOrdenPago2 = (String) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("NroAux3")){
                        this.nroOrdenPago3 = (String) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("NroAux4")){
                        this.nroOrdenPago4 = (String) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("NroAux5")){
                        this.nroOrdenPago5 = (String) para[i].getParameter();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        String message = "OK";

        try{

            MZFinancialConfig financialConfig = MZFinancialConfig.getDefault(getCtx(), null);
            if ((financialConfig == null) || (financialConfig.get_ID() <= 0)){
                return "@Error@ " + "Falta parametrización Financiera general en el Sistema";
            }
            if (financialConfig.getDefaultDocPPD_ID() <= 0){
                return "@Error@ " + "Falta parametrizar de Documento por Defecto para Pago a Proveedores en Configuración Financiera";
            }

            MZOrdenPago ordenPagoMain = null;

            // Armo array con ordenes de pago indicadas por el usuario.
            List<MZOrdenPago> ordenPagoList = new ArrayList<MZOrdenPago>();

            // Si tengo numero de orden de pago 1
            if ((this.nroOrdenPago1 != null) && (!this.nroOrdenPago1.trim().equalsIgnoreCase(""))){
                // Veo si tengo orden de pago con ese numero y si es asi la asocio a la orden de pago principal, para luego verificar
                // que las demas sean del mismo socio de negocio y moneda.
                ordenPagoMain = MZOrdenPago.getByDocumentNo(getCtx(), this.nroOrdenPago1.trim(), get_TrxName());
                if ((ordenPagoMain == null) || (ordenPagoMain.get_ID() <= 0)){
                    return "@Error@ " + "No existe en el sistema una Orden de Pago con el Numero de Orden de Pago 1 ingresado.";
                }
                if (!ordenPagoMain.getDocStatus().equalsIgnoreCase(DocAction.STATUS_Completed)){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago1 + " no esta en estado COMPLETO, por lo tanto no puede procesarse.";
                }
                if (ordenPagoMain.getZ_Pago_ID() > 0){
                    MZPago pago = (MZPago) ordenPagoMain.getZ_Pago();
                    String nroRecibo = pago.getDocumentNo();
                    if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                        nroRecibo = pago.getNroRecibo();
                    }
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago1 + " ya esta asociada al Recibo de Proveedor número " + nroRecibo;
                }

                ordenPagoList.add(ordenPagoMain);
            }
            else{
                return "@Error@ " + "Debe indicar número de Orden de Pago 1 para continuar.";
            }

            // Segunda orden
            if ((this.nroOrdenPago2 != null) && (!this.nroOrdenPago2.trim().equalsIgnoreCase(""))){
                MZOrdenPago ordenPagoAux = MZOrdenPago.getByDocumentNo(getCtx(), this.nroOrdenPago2.trim(), get_TrxName());
                if ((ordenPagoAux == null) || (ordenPagoAux.get_ID() <= 0)){
                    return "@Error@ " + "No existe en el sistema una Orden de Pago con el Numero de Orden de Pago 2 ingresado.";
                }
                if (!ordenPagoAux.getDocStatus().equalsIgnoreCase(DocAction.STATUS_Completed)){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago2 + " no esta en estado COMPLETO, por lo tanto no puede procesarse.";
                }
                if (ordenPagoAux.getAD_Org_ID() != ordenPagoMain.getAD_Org_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago2 + " no pertenece a la misma Organización que la orden de pago 1";
                }
                if (ordenPagoAux.getC_BPartner_ID() != ordenPagoMain.getC_BPartner_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago2 + " no pertenece al mismo Socio de Negocio que la orden de pago 1";
                }
                if (ordenPagoAux.getC_Currency_ID() != ordenPagoMain.getC_Currency_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago2 + " no tiene la misma Moneda que la orden de pago 1";
                }
                if (ordenPagoAux.getZ_Pago_ID() > 0){
                    MZPago pago = (MZPago) ordenPagoAux.getZ_Pago();
                    String nroRecibo = pago.getDocumentNo();
                    if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                        nroRecibo = pago.getNroRecibo();
                    }
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago2 + " ya esta asociada al Recibo de Proveedor número " + nroRecibo;
                }

                ordenPagoList.add(ordenPagoAux);
            }

            // Tercer orden
            if ((this.nroOrdenPago3 != null) && (!this.nroOrdenPago3.trim().equalsIgnoreCase(""))){
                MZOrdenPago ordenPagoAux = MZOrdenPago.getByDocumentNo(getCtx(), this.nroOrdenPago3.trim(), get_TrxName());
                if ((ordenPagoAux == null) || (ordenPagoAux.get_ID() <= 0)){
                    return "@Error@ " + "No existe en el sistema una Orden de Pago con el Numero de Orden de Pago 3 ingresado.";
                }
                if (!ordenPagoAux.getDocStatus().equalsIgnoreCase(DocAction.STATUS_Completed)){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago3 + " no esta en estado COMPLETO, por lo tanto no puede procesarse.";
                }
                if (ordenPagoAux.getAD_Org_ID() != ordenPagoMain.getAD_Org_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago3 + " no pertenece a la misma Organización que la orden de pago 1";
                }
                if (ordenPagoAux.getC_BPartner_ID() != ordenPagoMain.getC_BPartner_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago3 + " no pertenece al mismo Socio de Negocio que la orden de pago 1";
                }
                if (ordenPagoAux.getC_Currency_ID() != ordenPagoMain.getC_Currency_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago3 + " no tiene la misma Moneda que la orden de pago 1";
                }
                if (ordenPagoAux.getZ_Pago_ID() > 0){
                    MZPago pago = (MZPago) ordenPagoAux.getZ_Pago();
                    String nroRecibo = pago.getDocumentNo();
                    if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                        nroRecibo = pago.getNroRecibo();
                    }
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago3 + " ya esta asociada al Recibo de Proveedor número " + nroRecibo;
                }

                ordenPagoList.add(ordenPagoAux);
            }

            // Cuarta orden
            if ((this.nroOrdenPago4 != null) && (!this.nroOrdenPago4.trim().equalsIgnoreCase(""))){
                MZOrdenPago ordenPagoAux = MZOrdenPago.getByDocumentNo(getCtx(), this.nroOrdenPago4.trim(), get_TrxName());
                if ((ordenPagoAux == null) || (ordenPagoAux.get_ID() <= 0)){
                    return "@Error@ " + "No existe en el sistema una Orden de Pago con el Numero de Orden de Pago 4 ingresado.";
                }
                if (!ordenPagoAux.getDocStatus().equalsIgnoreCase(DocAction.STATUS_Completed)){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago4 + " no esta en estado COMPLETO, por lo tanto no puede procesarse.";
                }
                if (ordenPagoAux.getAD_Org_ID() != ordenPagoMain.getAD_Org_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago4 + " no pertenece a la misma Organización que la orden de pago 1";
                }
                if (ordenPagoAux.getC_BPartner_ID() != ordenPagoMain.getC_BPartner_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago4 + " no pertenece al mismo Socio de Negocio que la orden de pago 1";
                }
                if (ordenPagoAux.getC_Currency_ID() != ordenPagoMain.getC_Currency_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago4 + " no tiene la misma Moneda que la orden de pago 1";
                }
                if (ordenPagoAux.getZ_Pago_ID() > 0){
                    MZPago pago = (MZPago) ordenPagoAux.getZ_Pago();
                    String nroRecibo = pago.getDocumentNo();
                    if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                        nroRecibo = pago.getNroRecibo();
                    }
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago4 + " ya esta asociada al Recibo de Proveedor número " + nroRecibo;
                }

                ordenPagoList.add(ordenPagoAux);
            }


            // Quinta orden
            if ((this.nroOrdenPago5 != null) && (!this.nroOrdenPago5.trim().equalsIgnoreCase(""))){
                MZOrdenPago ordenPagoAux = MZOrdenPago.getByDocumentNo(getCtx(), this.nroOrdenPago5.trim(), get_TrxName());
                if ((ordenPagoAux == null) || (ordenPagoAux.get_ID() <= 0)){
                    return "@Error@ " + "No existe en el sistema una Orden de Pago con el Numero de Orden de Pago 5 ingresado.";
                }
                if (!ordenPagoAux.getDocStatus().equalsIgnoreCase(DocAction.STATUS_Completed)){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago5 + " no esta en estado COMPLETO, por lo tanto no puede procesarse.";
                }
                if (ordenPagoAux.getAD_Org_ID() != ordenPagoMain.getAD_Org_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago5 + " no pertenece a la misma Organización que la orden de pago 1";
                }
                if (ordenPagoAux.getC_BPartner_ID() != ordenPagoMain.getC_BPartner_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago5 + " no pertenece al mismo Socio de Negocio que la orden de pago 1";
                }
                if (ordenPagoAux.getC_Currency_ID() != ordenPagoMain.getC_Currency_ID()){
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago5 + " no tiene la misma Moneda que la orden de pago 1";
                }
                if (ordenPagoAux.getZ_Pago_ID() > 0){
                    MZPago pago = (MZPago) ordenPagoAux.getZ_Pago();
                    String nroRecibo = pago.getDocumentNo();
                    if ((pago.getNroRecibo() != null) && (!pago.getNroRecibo().trim().equalsIgnoreCase(""))){
                        nroRecibo = pago.getNroRecibo();
                    }
                    return "@Error@ " + "La Orden de Pago número " + this.nroOrdenPago5 + " ya esta asociada al Recibo de Proveedor número " + nroRecibo;
                }

                ordenPagoList.add(ordenPagoAux);
            }

            // Si tengo array de ordenes a procesar
            if (ordenPagoList.size() > 0){
                // Genero recibo a partir de este array de ordenes de pago
                MZPago pago = new MZPago(getCtx(), 0, get_TrxName());
                pago.setAD_Org_ID(ordenPagoMain.getAD_Org_ID());
                pago.setTieneOrdenPago(true);
                pago.setC_BPartner_ID(ordenPagoMain.getC_BPartner_ID());
                pago.setC_Currency_ID(ordenPagoMain.getC_Currency_ID());
                pago.setDateDoc(this.fechaRecibo);
                pago.setDateAcct(this.fechaRecibo);
                pago.setTieneRecibo(true);
                pago.setNroRecibo(this.nroReciboProveedor);
                pago.setC_DocType_ID(financialConfig.getDefaultDocPPD_ID());
                pago.setPayAmt(Env.ZERO);
                pago.setTotalMediosPago(Env.ZERO);
                pago.setDescription("Generada Automáticamente desde Ingreso Ágil de Recibos.");
                pago.saveEx();

                // Cargo ordenes de pago en el recibo
                for (MZOrdenPago ordenPago: ordenPagoList){
                    pago.setFromOrdenPago(ordenPago);
                }

                // Completo recibo
                if (!pago.processIt(DocAction.ACTION_Complete)){
                    message = pago.getProcessMsg();
                    if (message == null){
                        message = "Problemas al completar el Recibo";
                    }
                    return "@Error@ " + "No se pudo completar y generar el Recibo : " + message;
                }
                pago.saveEx();

                message = "Recibo generado con EXITO para el Socio de Negocio : " + ((MBPartner) pago.getC_BPartner()).getName();
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

}
