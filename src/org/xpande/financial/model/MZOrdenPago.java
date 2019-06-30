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
import java.sql.PreparedStatement;
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
import org.xpande.financial.utils.FinancialUtils;

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

		// Obtengo lineas a procesar
		List<MZOrdenPagoMedio> mediosPagoList = this.getMediosPago();
		List<MZOrdenPagoLin> pagoLinInvList = this.getInvoices();
		List<MZOrdenPagoLin> pagoLinTransferList = this.getTransferSaldos();
		List<MZOrdenPagoLin> pagoLinAnticipoList = this.getAnticipos();
		List<MZOrdenPagoLin> pagoLinResgList = this.getResguardos();

		// Seteo flag en este documento que me indica si es una Orden para afectar solamente a un anticipo (etapa 1 del anticipo)
		this.setEsOrdenPagoAnticipo();

		// Obtengo importe total de anticipos afectados en este documento
		this.setTotalAnticiposAfectados();

		// Si es una orden para afectar anticipos en Etapa 1.
		if (this.isOrdPagoAnticipo()){
			// Doy vuelta signos de anticipos para que me queden positivos
			for (MZOrdenPagoLin ordenPagoLin: pagoLinAnticipoList){
				if (ordenPagoLin.getAmtDocument().compareTo(Env.ZERO) < 0){
					ordenPagoLin.setAmtDocument(ordenPagoLin.getAmtDocument().negate());
				}
				if (ordenPagoLin.getAmtOpen().compareTo(Env.ZERO) < 0){
					ordenPagoLin.setAmtOpen(ordenPagoLin.getAmtOpen().negate());
				}
				if (ordenPagoLin.getAmtAllocation().compareTo(Env.ZERO) < 0){
					ordenPagoLin.setAmtAllocation(ordenPagoLin.getAmtAllocation().negate());
					ordenPagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocationMT().negate());
				}
				ordenPagoLin.saveEx();
			}
			// Doy vuelta signo de medios de pago
			for (MZOrdenPagoMedio ordenPagoMedio: mediosPagoList){
				if (ordenPagoMedio.getTotalAmt().compareTo(Env.ZERO) < 0){
					ordenPagoMedio.setTotalAmt(ordenPagoMedio.getTotalAmt().negate());
					ordenPagoMedio.saveEx();
				}
			}
			// Doy vuelta monto total de la orden de pago
			if (this.getTotalAmt().compareTo(Env.ZERO) < 0){
				this.setTotalAmt(this.getTotalAmt().negate());
			}
		}

		// Valido condiciones para completar este documento
		m_processMsg = this.validateDocument(mediosPagoList, pagoLinInvList, pagoLinTransferList, pagoLinAnticipoList, pagoLinResgList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Emite medios de pago necesarios
		m_processMsg = this.emitirMediosPago(mediosPagoList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Si es una orden normal
		if (!this.isOrdPagoAnticipo()){
			// Afecta invoices asociadas a esta orden de pago
			m_processMsg = this.afectarInvoices(pagoLinInvList);
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}

			// Afecta transferencias de saldos asociados
			m_processMsg = this.afectarTransferSaldos(pagoLinTransferList);
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}

			// Afecta resguardos asociados
			m_processMsg = this.afectarResguardos(pagoLinResgList);
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}
		}

		// Afecta anticipos a proveedores asociados (en etapa 1 o 2)
		m_processMsg = this.afectarAnticipos(pagoLinAnticipoList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Impactos en modelo de estado de cuenta para este documento
		FinancialUtils.setEstadoCtaOrdenPago(getCtx(), this, true, get_TrxName());

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

					// Marca invoice como paga si ya no tengo monto pendiente de pago
					if (pagoLin.getAmtAllocation().compareTo(pagoLin.getAmtOpen()) == 0){
						MInvoice invoice = (MInvoice) pagoLin.getC_Invoice();
						invoice.setIsPaid(true);
						invoice.saveEx();
					}
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Afecta transferencias de saldos afectadas en esta orden de pago
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	private String afectarTransferSaldos(List<MZOrdenPagoLin> pagoLinList) {

		String message = null;

		try{
			for (MZOrdenPagoLin pagoLin: pagoLinList){
				if (pagoLin.getZ_TransferSaldo_ID() > 0){

					BigDecimal amtAllocation = pagoLin.getAmtAllocation();
					if (amtAllocation.compareTo(Env.ZERO) < 0){
						amtAllocation = amtAllocation.negate();
					}

					// Afecta cada comprobante por el monto de afectación
					MZTransferAfectacion transferAfecta = new MZTransferAfectacion(getCtx(), 0, get_TrxName());
					transferAfecta.setZ_OrdenPago_ID(this.get_ID());
					transferAfecta.setAD_Table_ID(this.get_Table_ID());
					transferAfecta.setAmtAllocation(amtAllocation);
					transferAfecta.setC_DocType_ID(this.getC_DocType_ID());
					transferAfecta.setZ_TransferSaldo_ID(pagoLin.getZ_TransferSaldo_ID());
					transferAfecta.setDateDoc(this.getDateDoc());
					transferAfecta.setDocumentNoRef(this.getDocumentNo());
					transferAfecta.setDueDate(pagoLin.getDueDateDoc());
					transferAfecta.setRecord_ID(this.get_ID());
					transferAfecta.setC_Currency_ID(pagoLin.getC_Currency_ID());
					transferAfecta.setAD_Org_ID(this.getAD_Org_ID());
					transferAfecta.saveEx();

					// Marca transferencia de saldo como paga sino le queda monto pendiente
					if (pagoLin.getAmtAllocation().compareTo(pagoLin.getAmtOpen()) == 0){
						MZTransferSaldo transferSaldo = (MZTransferSaldo) pagoLin.getZ_TransferSaldo();
						transferSaldo.setIsPaid(true);
						transferSaldo.saveEx();
					}
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Afecta anticipos a proveedores afectados en esta orden de pago
	 * Xpande. Created by Gabriel Vila on 3/24/19.
	 * @param pagoLinAnticiposList
	 * @return
	 */
	private String afectarAnticipos(List<MZOrdenPagoLin> pagoLinAnticiposList) {

		String message = null;

		try{
			for (MZOrdenPagoLin pagoLin: pagoLinAnticiposList){

				if (pagoLin.getZ_Pago_ID() > 0){

					// Si es una orden normal, consumo los anticipos y su saldo.
					if (!this.isOrdPagoAnticipo()){
						BigDecimal amtAllocation = pagoLin.getAmtAllocation();
						if (amtAllocation.compareTo(Env.ZERO) < 0){
							amtAllocation = amtAllocation.negate();
						}

						// Afecta cada comprobante por el monto de afectación
						MZPagoAfectacion pagoAfectacion = new MZPagoAfectacion(getCtx(), 0, get_TrxName());
						pagoAfectacion.setZ_OrdenPago_ID(this.get_ID());
						pagoAfectacion.setAD_Table_ID(this.get_Table_ID());
						pagoAfectacion.setAmtAllocation(amtAllocation);
						pagoAfectacion.setC_DocType_ID(this.getC_DocType_ID());
						pagoAfectacion.setZ_Pago_ID(pagoLin.getZ_Pago_ID());
						pagoAfectacion.setDateDoc(this.getDateDoc());
						pagoAfectacion.setDocumentNoRef(this.getDocumentNo());
						pagoAfectacion.setDueDate(pagoLin.getDueDateDoc());
						pagoAfectacion.setRecord_ID(this.get_ID());
						pagoAfectacion.setC_Currency_ID(pagoLin.getC_Currency_ID());
						pagoAfectacion.setAD_Org_ID(this.getAD_Org_ID());
						pagoAfectacion.saveEx();

					}
					else{ // Es una orden de pago para pagar anticipos en etapa 1.

						MZPago anticipo = (MZPago) pagoLin.getZ_Pago();
						anticipo.setZ_OrdenPago_To_ID(this.get_ID());
						anticipo.saveEx();
					}
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

				// Valido importe positivo y mayor a cero en este medio de pago
				if ((ordenMedioPago.getTotalAmt() == null) || (ordenMedioPago.getTotalAmt().compareTo(Env.ZERO) <= 0)){
					return "Debe indicar importe mayor a cero en medios de pago de este documento.";
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
				else{
					medioPagoItem.setZ_OrdenPago_ID(this.get_ID());
					medioPagoItem.saveEx();
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
	 * @param mediosPagoList
	 * @param pagoLinInvList
	 * @param pagoLinTransferList
	 * @param pagoLinAnticipoList
	 * @param pagoLinResgList
	 */
	private String validateDocument(List<MZOrdenPagoMedio> mediosPagoList, List<MZOrdenPagoLin> pagoLinInvList, List<MZOrdenPagoLin> pagoLinTransferList,
									List<MZOrdenPagoLin> pagoLinAnticipoList, List<MZOrdenPagoLin> pagoLinResgList) {

		String message = null;

		try{

			// Si es una orden de pago normal para afectar documentos
			if (!this.isOrdPagoAnticipo()){

				if ((this.getAmtPaymentRule() == null) || (this.getAmtPaymentRule().compareTo(this.getTotalAmt()) != 0)){
					return "El importe Total de Medios de Pago debe ser igual al importe Total de Documentos";
				}

				// Anticipos a procesar en etapa 2, tienen que haber pasado por etapa 1 y tener un recibo de proveedor asociado.
				// Valido información de anticipos antes de ser afectados por primera vez (Etapa 1).
				for (MZOrdenPagoLin ordenPagoLin: pagoLinAnticipoList){
					MZPago anticipo = (MZPago) ordenPagoLin.getZ_Pago();
					if (anticipo.getZ_Pago_To_ID() <= 0){
						return "No es posible procesar el Anticipo Nro.: " + anticipo.getDocumentNo() +
								" ya que el mismo no tiene aún un Recibo de Proveedor asociado.";
					}
				}
			}
			else{ // Es una orden de pago para afectar solamente anticipos en su etapa 1.

				// No puedo tener otros documentos que no sean anticipos
				if ((pagoLinInvList.size() > 0) || (pagoLinTransferList.size() > 0) || (pagoLinResgList.size() > 0)) {
					return "Esta Orden de Pago contiene un Anticipo que aún no fue afectado, por lo tanto no es " +
							"posible consumirlo con otros documentos.";
				}

				// Valido información de anticipos antes de ser afectados por primera vez (Etapa 1).
				for (MZOrdenPagoLin ordenPagoLin: pagoLinAnticipoList){

					// No permito la modificación de saldos en afectacion de anticipo en etapa 1. El pago será por el total siempre.
					if (ordenPagoLin.getAmtOpen().compareTo(ordenPagoLin.getAmtAllocation()) != 0){
						return "No es posible afectar un anticipo con monto diferente al monto total del mismo.";
					}

					// Si este anticipo ya fue afectado en etapa 1 en otra orden de pago, no lo puedo procesar de nuevo.
					MZPago anticipo = (MZPago) ordenPagoLin.getZ_Pago();
					if ((anticipo.getZ_OrdenPago_To_ID() > 0) && (anticipo.getZ_OrdenPago_To_ID() != this.get_ID())){
						MZOrdenPago ordenPagoAux = (MZOrdenPago) anticipo.getZ_OrdenPago_To();
						return "No es posible procesar el Anticipo Nro.: " + anticipo.getDocumentNo() +
								" ya que el mismo ya fue afectado en la Orden de Pago Nro.: " + ordenPagoAux.getDocumentNo();
					}
				}
			}

			/*
			// Valido que los documentos sigan con monto abierto sin cambios
			for (MZPagoLin pagoLin: pagoLinList){
				if (pagoLin.isSelected()){
					if (pagoLin.getC_Invoice_ID() > 0){

						BigDecimal amtOpen = FinancialUtils.getInvoiceAmtOpen(getCtx(), pagoLin.getC_Invoice_ID(), get_TrxName());
						if (amtOpen == null){
							amtOpen = pagoLin.getAmtDocument();
						}
						BigDecimal amtPagoLin = pagoLin.getAmtAllocation();

						// Para documentos que restan, me aseguro de considerar monto a pagar sin signo.
						if (amtPagoLin.compareTo(Env.ZERO) < 0) amtPagoLin = amtPagoLin.negate();

						if (amtOpen.compareTo(amtPagoLin) != 0){
							return "El monto pendiente del comprobante " + pagoLin.getDocumentNoRef() + " ha cambiado.\n" +
									"Por favor elimine la linea del comprobante y vuelva a cargarla para refrescar información.";
						}
					}
				}
			}

			// Valido que los medios de pago sigan disponibles para utilizar en un recibo
			for (MZPagoMedioPago pagoMedioPago: medioPagoList){
				if (pagoMedioPago.getZ_MedioPagoItem_ID() > 0){
					MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
					if (medioPagoItem.isEntregado()){
						return "El medio de pago número " + medioPagoItem.getNroMedioPago() + " ya fue entregado en otro comprobante.\n" +
								"Por favor utilice otro medio de pago en este documento.";
					}
				}
			}
			 */


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
	 * Obtiene medios de pago de esta orden de pago para un determinado item de medio de pago.
	 * Xpande. Created by Gabriel Vila on 5/10/19.
	 * @return
	 */
	public MZOrdenPagoMedio getMedioPagoByItem(int zMedioPagoItemID) {

		String whereClause = X_Z_OrdenPagoMedio.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_OrdenPagoMedio.COLUMNNAME_Z_MedioPagoItem_ID + " =" + zMedioPagoItemID;

		MZOrdenPagoMedio model = new Query(getCtx(), I_Z_OrdenPagoMedio.Table_Name, whereClause, get_TrxName()).first();

		return model;
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


	/***
	 * Obtiene y retorna lineas de orden de pago que se corresponden a transferencias de saldos.
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	public List<MZOrdenPagoLin> getTransferSaldos(){

		String whereClause = X_Z_OrdenPagoLin.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_OrdenPagoLin.COLUMNNAME_Z_TransferSaldo_ID + " is not null ";

		List<MZOrdenPagoLin> lines = new Query(getCtx(), I_Z_OrdenPagoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Método que obtiene y retorna orden de pago según número de documento recibido.
	 * Xpande. Created by Gabriel Vila on 6/7/19.
	 * @param ctx
	 * @param documentNo
	 * @param trxName
	 * @return
	 */
	public static MZOrdenPago getByDocumentNo(Properties ctx, String documentNo, String trxName){

		String whereClause = X_Z_OrdenPago.COLUMNNAME_DocumentNo + " ='" + documentNo + "' ";

		MZOrdenPago model = new Query(ctx, I_Z_OrdenPago.Table_Name, whereClause, trxName).first();

		return model;
	}

	/***
	 * Obtiene y retorna lineas de orden de pago que se corresponden a Anticipos a Proveedores
	 * Xpande. Created by Gabriel Vila on 3/24/19.
	 * @return
	 */
	public List<MZOrdenPagoLin> getAnticipos(){

		String whereClause = X_Z_OrdenPagoLin.COLUMNNAME_Z_OrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_OrdenPagoLin.COLUMNNAME_Z_Pago_ID + " is not null ";

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
				this.m_processMsg = "No se puede anular esta Orden de Pago porque tiene asociado el Recibo de Proveedor con número interno : " + pago.getDocumentNo() + ".\n" +
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
					emisionMedioPago.setModificable(true);
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

		// Impactos en estado de cuenta
		FinancialUtils.setEstadoCtaOrdenPago(getCtx(), this, false, get_TrxName());

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
							emisionMedioPago.setModificable(true);
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

		// Impactos en estado de cuenta
		FinancialUtils.setEstadoCtaOrdenPago(getCtx(), this, false, get_TrxName());

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
				}
			}

			// Anulo afectacion de transferencias de saldos
			List<MZOrdenPagoLin> pagoLinTransferList = this.getTransferSaldos();
			for (MZOrdenPagoLin pagoLin: pagoLinTransferList){

				if (pagoLin.getZ_TransferSaldo_ID() > 0){

					// Marca transferencia de saldo como no paga
					MZTransferSaldo transferSaldo = (MZTransferSaldo) pagoLin.getZ_TransferSaldo();
					transferSaldo.setIsPaid(false);
					transferSaldo.saveEx();

					// Anulo afectacion para esta transferencia de saldo
					action = " delete from z_transferafectacion where z_ordenpago_id =" + this.get_ID() +
								" and z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}

			// Anulo afectacion de anticipos a proveedores
			List<MZOrdenPagoLin> pagoLinAnticipoList = this.getAnticipos();
			for (MZOrdenPagoLin pagoLin: pagoLinAnticipoList){

				if (pagoLin.getZ_Pago_ID() > 0){

					// Desasocio orden de pago y anticipo
					action = " update z_pago set z_ordenpago_to_id = null " +
							 " where z_pago_id =" + pagoLin.getZ_Pago_ID();
					DB.executeUpdateEx(action, get_TrxName());

					// Anulo afectacion para este anticipo
					action = " delete from z_pagoafectacion where z_ordenpago_id =" + this.get_ID() +
							" and z_pago_id =" + pagoLin.getZ_Pago_ID();
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
				}
			}

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


	/***
	 * Setea marca en este documento para indicar si es una orden de pago solamente para afectar un anticipo, o si es un orden de pago normal.
	 * Xpande. Created by Gabriel Vila on 6/26/19.
	 */
	private void setEsOrdenPagoAnticipo() {

		String sql = "";

		try{

			// Tipo de documento para anticipos de pagos o cobranzas
			String docBaseType = "PPA";

			// Si tiene al menos una documento asociado que no es anticipo, y esta seleccionado, entonces no es una orden para
			// afectar solamente un anticipo.
			sql = " select count(*) contador " +
					" from z_ordenpagolin " +
					" where z_ordenpago_id =" + this.get_ID() +
					" and c_doctype_id not in " +
					" (select c_doctype_id from c_doctype where docbasetype ='" + docBaseType + "') ";
			int contador = DB.getSQLValueEx(get_TrxName(), sql);

			if (contador == 0){
				this.setOrdPagoAnticipo(true);
			}
			else{
				this.setOrdPagoAnticipo(false);
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


	/***
	 * Obtiene y setea monto total de anticipos afectados en este documento.
	 * Xpande. Created by Gabriel Vila on 6/26/19.
	 */
	private void setTotalAnticiposAfectados() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select coalesce(sum(a.AmtAllocationMT),0) as total " +
					" from z_ordenpagolin a " +
					" inner join c_doctype doc on a.c_doctype_id = doc.c_doctype_id " +
					" where a.z_ordenpago_id =" + this.get_ID() +
					" and doc.docbasetype in ('PPA','CCA') ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			if (rs.next()){
				this.setAmtAnticipo(rs.getBigDecimal("total"));
				if (this.getAmtAnticipo().compareTo(Env.ZERO) < 0){
					this.setAmtAnticipo(rs.getBigDecimal("total").negate());
				}
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}


}