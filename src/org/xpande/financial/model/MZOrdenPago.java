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
package org.xpande.financial.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/** Generated Model for Z_OrdenPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZOrdenPago extends X_Z_OrdenPago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170816L;

    /** Standard Constructor */
    public MZOrdenPago (Properties ctx, int Z_OrdenPago_ID, String trxName)
    {
      super (ctx, Z_OrdenPago_ID, trxName);
    }

    /** Load Constructor */
    public MZOrdenPago (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index) {

		int newIndex = 0;

		if ((docStatus.equalsIgnoreCase(STATUS_Drafted))
				|| (docStatus.equalsIgnoreCase(STATUS_Invalid))
				|| (docStatus.equalsIgnoreCase(STATUS_InProgress))){

			options[newIndex++] = DocumentEngine.ACTION_Complete;

		}
		else if (docStatus.equalsIgnoreCase(STATUS_Completed)){

			//options[newIndex++] = DocumentEngine.ACTION_None;
			options[newIndex++] = DocumentEngine.ACTION_ReActivate;
			options[newIndex++] = DocumentEngine.ACTION_Void;
		}

		return newIndex;
	}


	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() +"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF

	
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	processIt
	
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
	//	setProcessing(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		/*
		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		*/

		//	Add up Amounts
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		//

		// Valido condiciones para completar este documento
		m_processMsg = this.validateDocument();
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Emite medios de pago necesarios
		List<MZOrdenPagoMedio> mediosPago = this.getMediosPago();
		m_processMsg = this.emitirMediosPago(mediosPago);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Afecta invoices asociadas a esta orden de pago
		List<MZOrdenPagoLin> pagoLinInvList = this.getInvoices();
		m_processMsg = this.afectarInvoices(pagoLinInvList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Afecta resguardos asociados
		List<MZOrdenPagoLin> pagoLinResgList = this.getResguardos();
		m_processMsg = this.afectarResguardos(pagoLinResgList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Impactos en estado de cuenta del socio de negocio
		this.setEstadoCuenta();

		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		//	Set Definitive Document No
		setDefiniteDocumentNo();

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt


	/***
	 * Al completarse la orden de pago, hace los impactos necesarios en el estado de cuenta del socio de negocio.
	 * Xpande. Created by Gabriel Vila on 3/24/18.
	 */
	private void setEstadoCuenta() {

		try{

			MDocType docType = (MDocType) this.getC_DocType();

			// Impacto documento en estado de cuenta
			MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(getCtx(), 0, get_TrxName());
			estadoCuenta.setZ_OrdenPago_ID(this.get_ID());
			estadoCuenta.setAD_Table_ID(this.get_Table_ID());
			estadoCuenta.setAmtSourceCr(Env.ZERO);
			estadoCuenta.setAmtSourceDr(this.getTotalAmt());
			estadoCuenta.setC_BPartner_ID(this.getC_BPartner_ID());
			estadoCuenta.setC_Currency_ID(this.getC_Currency_ID());
			estadoCuenta.setC_DocType_ID(this.getC_DocType_ID());
			estadoCuenta.setDateDoc(this.getDateDoc());
			estadoCuenta.setDocBaseType(docType.getDocBaseType());
			estadoCuenta.setDocumentNoRef(this.getDocumentNo());
			estadoCuenta.setIsSOTrx(false);
			estadoCuenta.setRecord_ID(this.get_ID());
			estadoCuenta.setAD_Org_ID(this.getAD_Org_ID());
			estadoCuenta.saveEx();
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Afecta resguardos utilizados en esta orden de pago
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 */
	private String afectarResguardos(List<MZOrdenPagoLin> pagoLinList) {

		String message = null;
		String action = "";

		try{
			for (MZOrdenPagoLin pagoLin: pagoLinList){
				if (pagoLin.getZ_ResguardoSocio_ID() > 0){
					MZResguardoSocio resguardoSocio = (MZResguardoSocio) pagoLin.getZ_ResguardoSocio();
					resguardoSocio.setZ_OrdenPago_ID(this.get_ID());
					resguardoSocio.setIsPaid(true);
					resguardoSocio.saveEx();

					// Afecto estado de cuenta de este resguardo
					action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + this.getDocumentNo() + "', " +
							 " z_ordenpago_id =" + this.get_ID() +
					 		 " where z_resguardosocio_id =" + pagoLin.getZ_ResguardoSocio_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Afecta invoices afectadas en esta orden de pago
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 */
	private String afectarInvoices(List<MZOrdenPagoLin> pagoLinList) {

		String message = null;

		try{
			for (MZOrdenPagoLin pagoLin: pagoLinList){
				if (pagoLin.getC_Invoice_ID() > 0){

					BigDecimal amtAllocation = pagoLin.getAmtAllocation();
					if (amtAllocation.compareTo(Env.ZERO) < 0){
						amtAllocation = amtAllocation.negate();
					}

					// Afecta cada comprobante por el monto de afectación
					MZInvoiceAfectacion invoiceAfecta = new MZInvoiceAfectacion(getCtx(), 0, get_TrxName());
					invoiceAfecta.setZ_OrdenPago_ID(this.get_ID());
					invoiceAfecta.setAD_Table_ID(this.get_Table_ID());
					invoiceAfecta.setAmtAllocation(amtAllocation);
					invoiceAfecta.setC_DocType_ID(this.getC_DocType_ID());
					invoiceAfecta.setC_Invoice_ID(pagoLin.getC_Invoice_ID());

					if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
						invoiceAfecta.setC_InvoicePaySchedule_ID(pagoLin.getC_InvoicePaySchedule_ID());
					}

					invoiceAfecta.setDateDoc(this.getDateDoc());
					invoiceAfecta.setDocumentNoRef(this.getDocumentNo());
					invoiceAfecta.setDueDate(pagoLin.getDueDateDoc());
					invoiceAfecta.setRecord_ID(this.get_ID());
					invoiceAfecta.setC_Currency_ID(pagoLin.getC_Currency_ID());
					invoiceAfecta.setAD_Org_ID(this.getAD_Org_ID());
					invoiceAfecta.saveEx();

					// Marca invoice como paga
					MInvoice invoice = (MInvoice) pagoLin.getC_Invoice();
					invoice.setIsPaid(true);
					invoice.saveEx();


					// Afecto estado de cuenta de esta invoice
					String action = "";
					if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
						action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + this.getDocumentNo() + "', " +
								 " z_ordenpago_id =" + this.get_ID() +
								 " where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
					}
					else{
						action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + this.getDocumentNo() + "', " +
								 " z_ordenpago_id =" + this.get_ID() +
								 " where c_invoice_id =" + pagoLin.getC_Invoice_ID();
					}
					DB.executeUpdateEx(action, get_TrxName());
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Emite medios de pago asociados a esta orden de pago.
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @param mediosPago
	 * @return
	 */
	private String emitirMediosPago(List<MZOrdenPagoMedio> mediosPago) {

		String message = null;
		String action = "";

		try{

			for (MZOrdenPagoMedio ordenMedioPago: mediosPago){

				// Instancio modelo de medio de pago, si es que tengo.
				MZMedioPago medioPago = (MZMedioPago) ordenMedioPago.getZ_MedioPago();
				if ((medioPago == null) || (medioPago.get_ID() <= 0)){
					continue;
				}

				// Si este medio de pago no se emite por definición
				if (!medioPago.isTieneEmision()){
					continue;
				}

				ordenMedioPago.setDateEmitted(this.getDateDoc());

				// Si no tengo item de medio de pago, y en caso de que este medio de pago requiera folio,
				// entonces obtengo el siguiente disponible del folio
				MZMedioPagoItem medioPagoItem = null;
				if (ordenMedioPago.getZ_MedioPagoItem_ID() <= 0){

					if (medioPago.isTieneFolio()){
						if (ordenMedioPago.getZ_MedioPagoFolio_ID() <= 0){
							return "Medio de pago no tiene Libreta asociada.";
						}
						medioPagoItem = ((MZMedioPagoFolio) ordenMedioPago.getZ_MedioPagoFolio()).getCurrentNext();
						if ((medioPagoItem == null) || (medioPagoItem.get_ID() <= 0)){
							return "Libreta no tiene medios de pago disponibles para utilizar.";
						}
						ordenMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
					}
					else{
						// Creo el item de medio de pago sin folio asociado
						medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
						medioPagoItem.setZ_MedioPago_ID(ordenMedioPago.getZ_MedioPago_ID());
						medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
						if (ordenMedioPago.getC_BankAccount_ID() > 0){
							medioPagoItem.setC_BankAccount_ID(ordenMedioPago.getC_BankAccount_ID());
						}

						medioPagoItem.setC_Currency_ID(ordenMedioPago.getC_Currency_ID());

						if ((ordenMedioPago.getDocumentNoRef() == null) || (ordenMedioPago.getDocumentNoRef().trim().equalsIgnoreCase(""))){

							medioPagoItem.setNroMedioPago(String.valueOf(ordenMedioPago.get_ID()));

							// Seteo numero de medio de pago en la linea de medio de pago de
							action = " update z_ordenpagomedio set documentnoref ='" + medioPagoItem.getNroMedioPago() + "' " +
									" where z_ordenpagomedio_id =" + ordenMedioPago.get_ID();
							DB.executeUpdateEx(action, get_TrxName());

						}
						else{
							medioPagoItem.setNroMedioPago(ordenMedioPago.getDocumentNoRef());
						}

						medioPagoItem.setDateEmitted(ordenMedioPago.getDateEmitted());
						medioPagoItem.setDueDate(ordenMedioPago.getDueDate());

						medioPagoItem.setIsReceipt(false);
						medioPagoItem.setEmitido(false);
						medioPagoItem.setTotalAmt(ordenMedioPago.getTotalAmt());
						medioPagoItem.setIsOwn(true);
						medioPagoItem.setC_BPartner_ID(this.getC_BPartner_ID());
					}
					medioPagoItem.saveEx();
					ordenMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
				}
				else{
					medioPagoItem = (MZMedioPagoItem) ordenMedioPago.getZ_MedioPagoItem();
				}

				ordenMedioPago.saveEx();

				// Si este medio de pago no esta emitido, lo hago ahora.
				if (!medioPagoItem.isEmitido()){

					// Realizo emisión para este medio de pago a considerar
					MZEmisionMedioPago emisionMedioPago = new MZEmisionMedioPago(getCtx(), 0, get_TrxName());
					emisionMedioPago.setZ_MedioPago_ID(ordenMedioPago.getZ_MedioPago_ID());
					emisionMedioPago.setAD_Org_ID(ordenMedioPago.getAD_Org_ID());

					if (ordenMedioPago.getZ_MedioPagoFolio_ID() > 0){
						emisionMedioPago.setZ_MedioPagoFolio_ID(ordenMedioPago.getZ_MedioPagoFolio_ID());
					}

					if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){
						emisionMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
						emisionMedioPago.setC_Currency_ID(medioPagoItem.getC_Currency_ID());

						if (medioPagoItem.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
						}
						if (medioPagoItem.getC_CashBook_ID() > 0){
							emisionMedioPago.setC_CashBook_ID(medioPagoItem.getC_CashBook_ID());
						}
					}
					else{
						emisionMedioPago.setReferenceNo(ordenMedioPago.getDocumentNoRef());
						emisionMedioPago.setC_Currency_ID(ordenMedioPago.getC_Currency_ID());

						if (ordenMedioPago.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(ordenMedioPago.getC_BankAccount_ID());
						}
					}

					emisionMedioPago.setZ_OrdenPago_ID(this.get_ID());
					emisionMedioPago.setC_BPartner_ID(this.getC_BPartner_ID());
					emisionMedioPago.setDateDoc(this.getDateDoc());
					emisionMedioPago.setDateEmitted(ordenMedioPago.getDateEmitted());
					emisionMedioPago.setDueDate(ordenMedioPago.getDueDate());
					emisionMedioPago.setTotalAmt(ordenMedioPago.getTotalAmt());
					emisionMedioPago.saveEx();

					// Completo documento de emisión de medio de pago
					if (!emisionMedioPago.processIt(DocAction.ACTION_Complete)){
						message = emisionMedioPago.getProcessMsg();
						return message;
					}
					emisionMedioPago.saveEx();
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;

	}


	/***
	 * Valida documento
	 * Xpande. Created by Gabriel Vila on 10/9/17.
 	 * @return
	 */
	private String validateDocument() {

		String message = null;

		try{
			if ((this.getAmtPaymentRule() == null) || (this.getAmtPaymentRule().compareTo(this.getTotalAmt()) != 0)){
				return "El importe Total de Medios de Pago debe ser igual al importe Total de Documentos";
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Obtiene y retorna lista de medios de pago de esta orden de pago.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	public List<MZOrdenPagoMedio> getMediosPago() {

		String whereClause = X_Z_OrdenPagoMedio.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID();

		List<MZOrdenPagoMedio> lines = new Query(getCtx(), I_Z_OrdenPagoMedio.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas de orden de pago que se corresponden a resguardos.
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 */
	public List<MZOrdenPagoLin> getResguardos(){

		String whereClause = X_Z_OrdenPagoLin.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_OrdenPagoLin.COLUMNNAME_Z_ResguardoSocio_ID + " is not null ";

		List<MZOrdenPagoLin> lines = new Query(getCtx(), I_Z_OrdenPagoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas de orden de pago que se corresponden a invoices.
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 */
	public List<MZOrdenPagoLin> getInvoices(){

		String whereClause = X_Z_OrdenPagoLin.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_OrdenPagoLin.COLUMNNAME_C_Invoice_ID + " is not null ";

		List<MZOrdenPagoLin> lines = new Query(getCtx(), I_Z_OrdenPagoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateDoc(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = null;
			int index = p_info.getColumnIndex("C_DocType_ID");
			if (index == -1)
				index = p_info.getColumnIndex("C_DocTypeTarget_ID");
			if (index != -1)		//	get based on Doc Type (might return null)
				value = DB.getDocumentNo(get_ValueAsInt(index), get_TrxName(), true);
			if (value != null) {
				setDocumentNo(value);
			}
		}
	}

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;


		String action = "";

		// Valido que esta orden de pago no este asociada a un recibo de proveedor que ademas esta completo.
		if (this.isPaid()){
			if (this.getZ_Pago_ID() > 0){
				MZPago pago = (MZPago) this.getZ_Pago();
				this.m_processMsg = "No se puede anular esta Orden de Pago porque tiene asociado el Recibo de Pago Nro.: " + pago.getDocumentNo() + ".\n" +
						"Debe Anular primero el Recibo, para luego Anular esta Orden.";
			}
			else{
				this.m_processMsg = "No se puede anular esta Orden de Pago porque tiene asociado un Recibo de Pago.";
			}
			return false;
		}


		// Anulo medios de pago emitidos en esta orden
		List<MZOrdenPagoMedio> ordenPagoMedioList = this.getMediosPago();
		for (MZOrdenPagoMedio ordenPagoMedio: ordenPagoMedioList){
			if (ordenPagoMedio.getZ_MedioPagoItem_ID() > 0){
				MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) ordenPagoMedio.getZ_MedioPagoItem();
				if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
					MZEmisionMedioPago emisionMedioPago = (MZEmisionMedioPago) medioPagoItem.getZ_EmisionMedioPago();
					if (!emisionMedioPago.processIt(DocAction.ACTION_Void)){
						this.m_processMsg = emisionMedioPago.getProcessMsg();
						return false;
					}
					emisionMedioPago.saveEx();
				}
				else{
					medioPagoItem.setAnulado(true);
					medioPagoItem.saveEx();
				}
			}
		}


		// Desafecto documentos asociados a este documento
		m_processMsg = this.desafectarDocumentos();
		if (m_processMsg != null)
			return false;


		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		this.setProcessed(true);
		this.setDocStatus(DOCSTATUS_Voided);
		this.setDocAction(DOCACTION_None);

		return true;
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());

		//	Close Not delivered Qty
		setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Valido que esta orden de pago no este asociada a un recibo de proveedor que ademas esta completo.
		if (this.isPaid()){
			if (this.getZ_Pago_ID() > 0){
				MZPago pago = (MZPago) this.getZ_Pago();
				this.m_processMsg = "No se puede Reactivar esta Orden de Pago porque tiene asociado el Recibo de Pago Nro.: " + pago.getDocumentNo() + ".\n" +
						"Debe Anular o Eliminar primero el Recibo, para luego Reactivar esta Orden.";
			}
			else{
				this.m_processMsg = "No se puede Reactivar esta Orden de Pago porque tiene asociado un Recibo de Pago.";
			}
			return false;
		}

		// Anulo emision de medios de pago emitidos en esta orden que no tengan folio asociado (esto para no anular cheques por ejemplo, pero si transferencias.)
		List<MZOrdenPagoMedio> ordenPagoMedioList = this.getMediosPago();
		for (MZOrdenPagoMedio ordenPagoMedio: ordenPagoMedioList){
			if (ordenPagoMedio.getZ_MedioPagoFolio_ID() <= 0){
				if (ordenPagoMedio.getZ_MedioPagoItem_ID() > 0){
					MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) ordenPagoMedio.getZ_MedioPagoItem();
					if (medioPagoItem.getZ_MedioPagoFolio_ID() <= 0){
						if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
							MZEmisionMedioPago emisionMedioPago = (MZEmisionMedioPago) medioPagoItem.getZ_EmisionMedioPago();
							if (!emisionMedioPago.processIt(DocAction.ACTION_Void)){
								this.m_processMsg = emisionMedioPago.getProcessMsg();
								return false;
							}
							emisionMedioPago.saveEx();
							emisionMedioPago.deleteEx(true);
						}

						// Desafecto item de medio de pago de esta orden y lo elimino
						String action = " update z_ordenpagomedio set z_mediopagoitem_id = null where z_ordenpagomedio_id =" + ordenPagoMedio.get_ID();
						DB.executeUpdateEx(action, get_TrxName());

						medioPagoItem.deleteEx(true);
					}
				}
			}
		}


		// Desafecto documentos asociados a este documento de pago/cobro
		m_processMsg = this.desafectarDocumentos();
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;


		this.setProcessed(false);
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setDocAction(DOCACTION_Complete);

		return true;

	}	//	reActivateIt

	private String desafectarDocumentos() {

		String message = null;
		String action = "";

		try{

			// Anulo afectacion de invoices
			List<MZOrdenPagoLin> pagoLinInvList = this.getInvoices();
			for (MZOrdenPagoLin pagoLin: pagoLinInvList){
				if (pagoLin.getC_Invoice_ID() > 0){

					// Marca invoice como no paga
					MInvoice invoice = (MInvoice) pagoLin.getC_Invoice();
					invoice.setIsPaid(false);
					invoice.saveEx();

					// Anulo afectacion para saldo de esta invoice
					action = " delete from z_invoiceafectacion where z_ordenpago_id =" + this.get_ID();
					if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
						action += " and c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
					}
					else{
						action += " and c_invoice_id =" + pagoLin.getC_Invoice_ID();
					}
					DB.executeUpdateEx(action, get_TrxName());


					// Anulo afectación en estado de cuenta para esta invoice y orden de pago
					if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
						action = " update z_estadocuenta set referenciapago = null, z_ordenpago_id = null " +
								" where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
					}
					else{
						action = " update z_estadocuenta set referenciapago = null, z_ordenpago_id = null " +
								" where c_invoice_id =" + pagoLin.getC_Invoice_ID();
					}
					DB.executeUpdateEx(action, get_TrxName());
				}
			}

			// Anulo afectacion de resguardos
			List<MZOrdenPagoLin> pagoLinResgList = this.getResguardos();
			for (MZOrdenPagoLin pagoLin: pagoLinResgList){
				if (pagoLin.getZ_ResguardoSocio_ID() > 0){

					// Marco resguardo como no pago
					action = " update z_resguardosocio set z_ordenpago_id = null, ispaid ='N' " +
							" where z_resguardosocio_id =" + pagoLin.getZ_ResguardoSocio_ID();
					DB.executeUpdateEx(action, get_TrxName());

					// Afecto estado de cuenta de este resguardo
					action = " update z_estadocuenta set referenciapago = null, z_ordenpago_id = null " +
							" where z_resguardosocio_id =" + pagoLin.getZ_ResguardoSocio_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}


			// Anulo orden de pago del estado de cuenta del socio de negocio
			action = " delete from z_estadocuenta where z_ordenpago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
	//	sb.append(": ")
	//		.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
	//		.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
	//	return getSalesRep_ID();
		return 0;
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return null;	//getTotalLines();
	}	//	getApprovalAmt
	
	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	public int getC_Currency_ID()
	{
	//	MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
	//	return pl.getC_Currency_ID();
		return super.getC_Currency_ID();
	}	//	getC_Currency_ID

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("MZOrdenPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	/***
	 * Actualiza totales de este documento, segun montos de lineas, resguardos y medios de pago.
	 * Xpande. Created by Gabriel Vila on 1/26/18.
	 */
	public void updateTotals(){

		String action = "", sql = "";

		try{
			// Obtengo suma de montos a pagar de documentos
			sql = " select sum(coalesce(amtallocationmt,0)) as total from z_ordenpagolin " +
					" where z_ordenpago_id =" + this.get_ID();
			BigDecimal sumLines = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumLines == null) sumLines = Env.ZERO;

			// Obtengo suma de montos de medios de pago
			sql = " select sum(coalesce(totalamt,0)) as total from z_ordenpagomedio " +
					" where z_ordenpago_id =" + this.get_ID();
			BigDecimal sumMedios = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumMedios == null) sumMedios = Env.ZERO;

			action = " update z_ordenpago set TotalAmt =" + sumLines + ", " +
					" AmtPaymentRule =" + sumMedios +
					" where z_ordenpago_id =" + this.get_ID();

			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


}