/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.xpande.financial.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_EstadoCuenta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_EstadoCuenta extends PO implements I_Z_EstadoCuenta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190626L;

    /** Standard Constructor */
    public X_Z_EstadoCuenta (Properties ctx, int Z_EstadoCuenta_ID, String trxName)
    {
      super (ctx, Z_EstadoCuenta_ID, trxName);
      /** if (Z_EstadoCuenta_ID == 0)
        {
			setAD_Table_ID (0);
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocBaseType (null);
			setDocumentNoRef (null);
			setIsSOTrx (false);
// N
			setRecord_ID (0);
			setZ_EstadoCuenta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_EstadoCuenta (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_EstadoCuenta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Source Credit.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	public void setAmtSourceCr (BigDecimal AmtSourceCr)
	{
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

	/** Get Source Credit.
		@return Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Source Debit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	public void setAmtSourceDr (BigDecimal AmtSourceDr)
	{
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

	/** Get Source Debit.
		@return Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException
    {
		return (I_C_InvoicePaySchedule)MTable.get(getCtx(), I_C_InvoicePaySchedule.Table_Name)
			.getPO(getC_InvoicePaySchedule_ID(), get_TrxName());	}

	/** Set Invoice Payment Schedule.
		@param C_InvoicePaySchedule_ID 
		Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, Integer.valueOf(C_InvoicePaySchedule_ID));
	}

	/** Get Invoice Payment Schedule.
		@return Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set DateRefOrdenPago.
		@param DateRefOrdenPago 
		Fecha referencia de Orden de Pago
	  */
	public void setDateRefOrdenPago (Timestamp DateRefOrdenPago)
	{
		set_Value (COLUMNNAME_DateRefOrdenPago, DateRefOrdenPago);
	}

	/** Get DateRefOrdenPago.
		@return Fecha referencia de Orden de Pago
	  */
	public Timestamp getDateRefOrdenPago () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefOrdenPago);
	}

	/** Set DateRefPago.
		@param DateRefPago 
		Fecha referencia de un documento de pago / cobro
	  */
	public void setDateRefPago (Timestamp DateRefPago)
	{
		set_Value (COLUMNNAME_DateRefPago, DateRefPago);
	}

	/** Get DateRefPago.
		@return Fecha referencia de un documento de pago / cobro
	  */
	public Timestamp getDateRefPago () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefPago);
	}

	/** Set DateRefResguardo.
		@param DateRefResguardo 
		Fecha referencia de un Resguardo a Socio de Negocio
	  */
	public void setDateRefResguardo (Timestamp DateRefResguardo)
	{
		set_Value (COLUMNNAME_DateRefResguardo, DateRefResguardo);
	}

	/** Get DateRefResguardo.
		@return Fecha referencia de un Resguardo a Socio de Negocio
	  */
	public Timestamp getDateRefResguardo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefResguardo);
	}

	/** Set DateRefTransfSaldo.
		@param DateRefTransfSaldo 
		Fecha referencia de documento de Transferencia de Saldo
	  */
	public void setDateRefTransfSaldo (Timestamp DateRefTransfSaldo)
	{
		set_Value (COLUMNNAME_DateRefTransfSaldo, DateRefTransfSaldo);
	}

	/** Get DateRefTransfSaldo.
		@return Fecha referencia de documento de Transferencia de Saldo
	  */
	public Timestamp getDateRefTransfSaldo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefTransfSaldo);
	}

	/** DocBaseType AD_Reference_ID=183 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** GL Journal = GLJ */
	public static final String DOCBASETYPE_GLJournal = "GLJ";
	/** GL Document = GLD */
	public static final String DOCBASETYPE_GLDocument = "GLD";
	/** AP Invoice = API */
	public static final String DOCBASETYPE_APInvoice = "API";
	/** AP Payment = APP */
	public static final String DOCBASETYPE_APPayment = "APP";
	/** AR Invoice = ARI */
	public static final String DOCBASETYPE_ARInvoice = "ARI";
	/** AR Receipt = ARR */
	public static final String DOCBASETYPE_ARReceipt = "ARR";
	/** Sales Order = SOO */
	public static final String DOCBASETYPE_SalesOrder = "SOO";
	/** AR Pro Forma Invoice = ARF */
	public static final String DOCBASETYPE_ARProFormaInvoice = "ARF";
	/** Material Delivery = MMS */
	public static final String DOCBASETYPE_MaterialDelivery = "MMS";
	/** Material Receipt = MMR */
	public static final String DOCBASETYPE_MaterialReceipt = "MMR";
	/** Material Movement = MMM */
	public static final String DOCBASETYPE_MaterialMovement = "MMM";
	/** Purchase Order = POO */
	public static final String DOCBASETYPE_PurchaseOrder = "POO";
	/** Purchase Requisition = POR */
	public static final String DOCBASETYPE_PurchaseRequisition = "POR";
	/** Material Physical Inventory = MMI */
	public static final String DOCBASETYPE_MaterialPhysicalInventory = "MMI";
	/** AP Credit Memo = APC */
	public static final String DOCBASETYPE_APCreditMemo = "APC";
	/** AR Credit Memo = ARC */
	public static final String DOCBASETYPE_ARCreditMemo = "ARC";
	/** Bank Statement = CMB */
	public static final String DOCBASETYPE_BankStatement = "CMB";
	/** Cash Journal = CMC */
	public static final String DOCBASETYPE_CashJournal = "CMC";
	/** Payment Allocation = CMA */
	public static final String DOCBASETYPE_PaymentAllocation = "CMA";
	/** Material Production = MMP */
	public static final String DOCBASETYPE_MaterialProduction = "MMP";
	/** Match Invoice = MXI */
	public static final String DOCBASETYPE_MatchInvoice = "MXI";
	/** Match PO = MXP */
	public static final String DOCBASETYPE_MatchPO = "MXP";
	/** Project Issue = PJI */
	public static final String DOCBASETYPE_ProjectIssue = "PJI";
	/** Maintenance Order = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** Manufacturing Order = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** Quality Order = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** Distribution Order = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** Manufacturing Cost Collector = MCC */
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MCC";
	/** Warehouse Management Order = WMO */
	public static final String DOCBASETYPE_WarehouseManagementOrder = "WMO";
	/** Manufacturing Planned Order = MPO */
	public static final String DOCBASETYPE_ManufacturingPlannedOrder = "MPO";
	/** AP Payment Selection = APS */
	public static final String DOCBASETYPE_APPaymentSelection = "APS";
	/** Sales Commission = SOC */
	public static final String DOCBASETYPE_SalesCommission = "SOC";
	/** Fixed Assets Addition = FAA */
	public static final String DOCBASETYPE_FixedAssetsAddition = "FAA";
	/** Fixed Assets Disposal = FAD */
	public static final String DOCBASETYPE_FixedAssetsDisposal = "FAD";
	/** Fixed Assets Depreciation = FDP */
	public static final String DOCBASETYPE_FixedAssetsDepreciation = "FDP";
	/** PLV Precios de Proveedor = PLV */
	public static final String DOCBASETYPE_PLVPreciosDeProveedor = "PLV";
	/** Retail Confirmacion Etiquetas = RCE */
	public static final String DOCBASETYPE_RetailConfirmacionEtiquetas = "RCE";
	/** PVP Actualizacion Precios de Venta = PVP */
	public static final String DOCBASETYPE_PVPActualizacionPreciosDeVenta = "PVP";
	/** RCP Retail Comunicacion POS = RCP */
	public static final String DOCBASETYPE_RCPRetailComunicacionPOS = "RCP";
	/** RGU Emision de Resguardos = RGU */
	public static final String DOCBASETYPE_RGUEmisionDeResguardos = "RGU";
	/** RGC Emision de Contra-Resguardos = RGC */
	public static final String DOCBASETYPE_RGCEmisionDeContra_Resguardos = "RGC";
	/** OPG Generacion de Ordenes de Pago = OPG */
	public static final String DOCBASETYPE_OPGGeneracionDeOrdenesDePago = "OPG";
	/** OOP Ordenes de Pago = OOP */
	public static final String DOCBASETYPE_OOPOrdenesDePago = "OOP";
	/** EMP Emision Medio Pago = EMP */
	public static final String DOCBASETYPE_EMPEmisionMedioPago = "EMP";
	/** RMP Reemplazo Medio de Pago = RMP */
	public static final String DOCBASETYPE_RMPReemplazoMedioDePago = "RMP";
	/** NCG Generacion de Notas de Cŕedito = NCG */
	public static final String DOCBASETYPE_NCGGeneracionDeNotasDeCŕedito = "NCG";
	/** ODV Orden de Devolución a Proveedor = ODV */
	public static final String DOCBASETYPE_ODVOrdenDeDevoluciónAProveedor = "ODV";
	/** OFP Oferta Periódica Retail = OFP */
	public static final String DOCBASETYPE_OFPOfertaPeriódicaRetail = "OFP";
	/** PPD Pago Proveedor = PPD */
	public static final String DOCBASETYPE_PPDPagoProveedor = "PPD";
	/** PPR Recibo de Pagos Emitidos = PPR */
	public static final String DOCBASETYPE_PPRReciboDePagosEmitidos = "PPR";
	/** PPA Anticipo a Proveedor = PPA */
	public static final String DOCBASETYPE_PPAAnticipoAProveedor = "PPA";
	/** RDI Remito Diferencia Factura = RDI */
	public static final String DOCBASETYPE_RDIRemitoDiferenciaFactura = "RDI";
	/** RDC Remito Diferencia Cantidad = RDC */
	public static final String DOCBASETYPE_RDCRemitoDiferenciaCantidad = "RDC";
	/** CCD Cobranza a Cliente = CCD */
	public static final String DOCBASETYPE_CCDCobranzaACliente = "CCD";
	/** GEN General = GEN */
	public static final String DOCBASETYPE_GENGeneral = "GEN";
	/** CII Carga Inicial Invoices = CII */
	public static final String DOCBASETYPE_CIICargaInicialInvoices = "CII";
	/** TSP Transferencia Saldo a Pagar = TSP */
	public static final String DOCBASETYPE_TSPTransferenciaSaldoAPagar = "TSP";
	/** CIM Carga Inicial Medios de Pago = CIM */
	public static final String DOCBASETYPE_CIMCargaInicialMediosDePago = "CIM";
	/** CIJ Carga Inicial Asientos Contables = CIJ */
	public static final String DOCBASETYPE_CIJCargaInicialAsientosContables = "CIJ";
	/** AVG Generacion de Asientos de Venta POS = AVG */
	public static final String DOCBASETYPE_AVGGeneracionDeAsientosDeVentaPOS = "AVG";
	/** Set Document BaseType.
		@param DocBaseType 
		Logical type of document
	  */
	public void setDocBaseType (String DocBaseType)
	{

		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Document BaseType.
		@return Logical type of document
	  */
	public String getDocBaseType () 
	{
		return (String)get_Value(COLUMNNAME_DocBaseType);
	}

	/** Set DocumentNoRef.
		@param DocumentNoRef 
		Numero de documento referenciado
	  */
	public void setDocumentNoRef (String DocumentNoRef)
	{
		set_Value (COLUMNNAME_DocumentNoRef, DocumentNoRef);
	}

	/** Get DocumentNoRef.
		@return Numero de documento referenciado
	  */
	public String getDocumentNoRef () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoRef);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** EstadoAprobacion AD_Reference_ID=1000018 */
	public static final int ESTADOAPROBACION_AD_Reference_ID=1000018;
	/** APROBADO = APROBADO */
	public static final String ESTADOAPROBACION_APROBADO = "APROBADO";
	/** BLOQUEADO = BLOQUEADO */
	public static final String ESTADOAPROBACION_BLOQUEADO = "BLOQUEADO";
	/** Set EstadoAprobacion.
		@param EstadoAprobacion 
		Estadode aprobacion de un comprobante
	  */
	public void setEstadoAprobacion (String EstadoAprobacion)
	{

		set_Value (COLUMNNAME_EstadoAprobacion, EstadoAprobacion);
	}

	/** Get EstadoAprobacion.
		@return Estadode aprobacion de un comprobante
	  */
	public String getEstadoAprobacion () 
	{
		return (String)get_Value(COLUMNNAME_EstadoAprobacion);
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ReferenciaPago.
		@param ReferenciaPago 
		Referencia descriptiva para pagos
	  */
	public void setReferenciaPago (String ReferenciaPago)
	{
		set_Value (COLUMNNAME_ReferenciaPago, ReferenciaPago);
	}

	/** Get ReferenciaPago.
		@return Referencia descriptiva para pagos
	  */
	public String getReferenciaPago () 
	{
		return (String)get_Value(COLUMNNAME_ReferenciaPago);
	}

	/** Set Ref_Pago_ID.
		@param Ref_Pago_ID 
		ID de Pago Referenciado
	  */
	public void setRef_Pago_ID (int Ref_Pago_ID)
	{
		if (Ref_Pago_ID < 1) 
			set_Value (COLUMNNAME_Ref_Pago_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Pago_ID, Integer.valueOf(Ref_Pago_ID));
	}

	/** Get Ref_Pago_ID.
		@return ID de Pago Referenciado
	  */
	public int getRef_Pago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Pago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** TipoSocioNegocio AD_Reference_ID=1000029 */
	public static final int TIPOSOCIONEGOCIO_AD_Reference_ID=1000029;
	/** CLIENTES = CLIENTES */
	public static final String TIPOSOCIONEGOCIO_CLIENTES = "CLIENTES";
	/** PROVEEDORES = PROVEEDORES */
	public static final String TIPOSOCIONEGOCIO_PROVEEDORES = "PROVEEDORES";
	/** TODOS = TODOS */
	public static final String TIPOSOCIONEGOCIO_TODOS = "TODOS";
	/** Set TipoSocioNegocio.
		@param TipoSocioNegocio 
		Tipo de Socio de Negocio
	  */
	public void setTipoSocioNegocio (String TipoSocioNegocio)
	{

		set_Value (COLUMNNAME_TipoSocioNegocio, TipoSocioNegocio);
	}

	/** Get TipoSocioNegocio.
		@return Tipo de Socio de Negocio
	  */
	public String getTipoSocioNegocio () 
	{
		return (String)get_Value(COLUMNNAME_TipoSocioNegocio);
	}

	public I_Z_Afectacion getZ_Afectacion() throws RuntimeException
    {
		return (I_Z_Afectacion)MTable.get(getCtx(), I_Z_Afectacion.Table_Name)
			.getPO(getZ_Afectacion_ID(), get_TrxName());	}

	/** Set Z_Afectacion ID.
		@param Z_Afectacion_ID Z_Afectacion ID	  */
	public void setZ_Afectacion_ID (int Z_Afectacion_ID)
	{
		if (Z_Afectacion_ID < 1) 
			set_Value (COLUMNNAME_Z_Afectacion_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Afectacion_ID, Integer.valueOf(Z_Afectacion_ID));
	}

	/** Get Z_Afectacion ID.
		@return Z_Afectacion ID	  */
	public int getZ_Afectacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Afectacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_EstadoCuenta ID.
		@param Z_EstadoCuenta_ID Z_EstadoCuenta ID	  */
	public void setZ_EstadoCuenta_ID (int Z_EstadoCuenta_ID)
	{
		if (Z_EstadoCuenta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_EstadoCuenta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_EstadoCuenta_ID, Integer.valueOf(Z_EstadoCuenta_ID));
	}

	/** Get Z_EstadoCuenta ID.
		@return Z_EstadoCuenta ID	  */
	public int getZ_EstadoCuenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_EstadoCuenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException
    {
		return (I_Z_OrdenPago)MTable.get(getCtx(), I_Z_OrdenPago.Table_Name)
			.getPO(getZ_OrdenPago_ID(), get_TrxName());	}

	/** Set Z_OrdenPago ID.
		@param Z_OrdenPago_ID Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID (int Z_OrdenPago_ID)
	{
		if (Z_OrdenPago_ID < 1) 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, Integer.valueOf(Z_OrdenPago_ID));
	}

	/** Get Z_OrdenPago ID.
		@return Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OrdenPago getZ_OrdenPago_To() throws RuntimeException
    {
		return (I_Z_OrdenPago)MTable.get(getCtx(), I_Z_OrdenPago.Table_Name)
			.getPO(getZ_OrdenPago_To_ID(), get_TrxName());	}

	/** Set Z_OrdenPago_To_ID.
		@param Z_OrdenPago_To_ID 
		Referencia a un documento de Orden de Pago
	  */
	public void setZ_OrdenPago_To_ID (int Z_OrdenPago_To_ID)
	{
		if (Z_OrdenPago_To_ID < 1) 
			set_Value (COLUMNNAME_Z_OrdenPago_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OrdenPago_To_ID, Integer.valueOf(Z_OrdenPago_To_ID));
	}

	/** Get Z_OrdenPago_To_ID.
		@return Referencia a un documento de Orden de Pago
	  */
	public int getZ_OrdenPago_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPago_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_Pago getZ_Pago() throws RuntimeException
    {
		return (I_Z_Pago)MTable.get(getCtx(), I_Z_Pago.Table_Name)
			.getPO(getZ_Pago_ID(), get_TrxName());	}

	/** Set Z_Pago ID.
		@param Z_Pago_ID Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID)
	{
		if (Z_Pago_ID < 1) 
			set_Value (COLUMNNAME_Z_Pago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Pago_ID, Integer.valueOf(Z_Pago_ID));
	}

	/** Get Z_Pago ID.
		@return Z_Pago ID	  */
	public int getZ_Pago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Pago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_Pago getZ_Pago_To() throws RuntimeException
    {
		return (I_Z_Pago)MTable.get(getCtx(), I_Z_Pago.Table_Name)
			.getPO(getZ_Pago_To_ID(), get_TrxName());	}

	/** Set Z_Pago_To_ID.
		@param Z_Pago_To_ID 
		Referencia a un documento de Pago
	  */
	public void setZ_Pago_To_ID (int Z_Pago_To_ID)
	{
		if (Z_Pago_To_ID < 1) 
			set_Value (COLUMNNAME_Z_Pago_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Pago_To_ID, Integer.valueOf(Z_Pago_To_ID));
	}

	/** Get Z_Pago_To_ID.
		@return Referencia a un documento de Pago
	  */
	public int getZ_Pago_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Pago_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException
    {
		return (I_Z_ResguardoSocio)MTable.get(getCtx(), I_Z_ResguardoSocio.Table_Name)
			.getPO(getZ_ResguardoSocio_ID(), get_TrxName());	}

	/** Set Z_ResguardoSocio ID.
		@param Z_ResguardoSocio_ID Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID (int Z_ResguardoSocio_ID)
	{
		if (Z_ResguardoSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_ResguardoSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ResguardoSocio_ID, Integer.valueOf(Z_ResguardoSocio_ID));
	}

	/** Get Z_ResguardoSocio ID.
		@return Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ResguardoSocio getZ_ResguardoSocio_To() throws RuntimeException
    {
		return (I_Z_ResguardoSocio)MTable.get(getCtx(), I_Z_ResguardoSocio.Table_Name)
			.getPO(getZ_ResguardoSocio_To_ID(), get_TrxName());	}

	/** Set Z_ResguardoSocio_To_ID.
		@param Z_ResguardoSocio_To_ID 
		Referencia a un Resguardo Socio Destino
	  */
	public void setZ_ResguardoSocio_To_ID (int Z_ResguardoSocio_To_ID)
	{
		if (Z_ResguardoSocio_To_ID < 1) 
			set_Value (COLUMNNAME_Z_ResguardoSocio_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ResguardoSocio_To_ID, Integer.valueOf(Z_ResguardoSocio_To_ID));
	}

	/** Get Z_ResguardoSocio_To_ID.
		@return Referencia a un Resguardo Socio Destino
	  */
	public int getZ_ResguardoSocio_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocio_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_TransferSaldo getZ_TransferSaldo() throws RuntimeException
    {
		return (I_Z_TransferSaldo)MTable.get(getCtx(), I_Z_TransferSaldo.Table_Name)
			.getPO(getZ_TransferSaldo_ID(), get_TrxName());	}

	/** Set Z_TransferSaldo ID.
		@param Z_TransferSaldo_ID Z_TransferSaldo ID	  */
	public void setZ_TransferSaldo_ID (int Z_TransferSaldo_ID)
	{
		if (Z_TransferSaldo_ID < 1) 
			set_Value (COLUMNNAME_Z_TransferSaldo_ID, null);
		else 
			set_Value (COLUMNNAME_Z_TransferSaldo_ID, Integer.valueOf(Z_TransferSaldo_ID));
	}

	/** Get Z_TransferSaldo ID.
		@return Z_TransferSaldo ID	  */
	public int getZ_TransferSaldo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_TransferSaldo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_TransferSaldo getZ_TransferSaldo_To() throws RuntimeException
    {
		return (I_Z_TransferSaldo)MTable.get(getCtx(), I_Z_TransferSaldo.Table_Name)
			.getPO(getZ_TransferSaldo_To_ID(), get_TrxName());	}

	/** Set Z_TransferSaldo_To_ID.
		@param Z_TransferSaldo_To_ID 
		Referencia a un documento de Transferencia de Saldo
	  */
	public void setZ_TransferSaldo_To_ID (int Z_TransferSaldo_To_ID)
	{
		if (Z_TransferSaldo_To_ID < 1) 
			set_Value (COLUMNNAME_Z_TransferSaldo_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_TransferSaldo_To_ID, Integer.valueOf(Z_TransferSaldo_To_ID));
	}

	/** Get Z_TransferSaldo_To_ID.
		@return Referencia a un documento de Transferencia de Saldo
	  */
	public int getZ_TransferSaldo_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_TransferSaldo_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}