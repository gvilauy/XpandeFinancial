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

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/** Generated Model for Z_MedioPagoReplace
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZMedioPagoReplace extends X_Z_MedioPagoReplace implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170918L;

    /** Standard Constructor */
    public MZMedioPagoReplace (Properties ctx, int Z_MedioPagoReplace_ID, String trxName)
    {
      super (ctx, Z_MedioPagoReplace_ID, trxName);
    }

    /** Load Constructor */
    public MZMedioPagoReplace (Properties ctx, ResultSet rs, String trxName)
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

			//options[newIndex++] = DocumentEngine.ACTION_ReActivate;
			//options[newIndex++] = DocumentEngine.ACTION_Void;
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
		String action = "";

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

		// Obtengo lineas del documento
		List<MZMedioPagoReplaceLin> lines = this.getLines();

		// Valido condiciones para completar este documento
		m_processMsg = this.validateDocument(lines);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Recorro lineas de medios de pago seleccionados para reemplazar
		for (MZMedioPagoReplaceLin replaceLin: lines){

			// Instancio modelo de medio de pago a reemplazar
			MZMedioPagoItem OLD_medioPagoItem = (MZMedioPagoItem) replaceLin.getZ_MedioPagoItem();

			int newMedioPagoItemID = 0;
			String nrosMediosPago = "";

			// Recorro detalle de nuevos medios de pago que reemplararán a este viejo medio de pago
			List<MZMedioPagoReplaceDet> dets = replaceLin.getDetail();
			for (MZMedioPagoReplaceDet replaceDet: dets){

				// Instancio modelo de medio de pago, si es que tengo.
				MZMedioPago medioPago = (MZMedioPago) replaceDet.getZ_MedioPago();
				if ((medioPago == null) || (medioPago.get_ID() <= 0)){
					continue;
				}

				// Si este medio de pago no se emite por definición
				if (!medioPago.isTieneEmision()){
					continue;
				}

				if (replaceDet.getDateEmitted() == null){
					replaceDet.setDateEmitted(this.getDateDoc());
				}

				if (replaceDet.getDueDate() == null){
					replaceDet.setDueDate(replaceDet.getDateEmitted());
				}

				// Si no tengo item de medio de pago, y en caso de que este medio de pago requiera folio,
				// entonces obtengo el siguiente disponible del folio
				MZMedioPagoItem NEW_medioPagoItem = null;
				if (replaceDet.getZ_MedioPagoItem_ID() <= 0){

					if (medioPago.isTieneFolio()){
						if (replaceDet.getZ_MedioPagoFolio_ID() <= 0){
							return "Medio de pago no tiene Libreta asociada.";
						}
						NEW_medioPagoItem = ((MZMedioPagoFolio) replaceDet.getZ_MedioPagoFolio()).getCurrentNext();
						if ((NEW_medioPagoItem == null) || (NEW_medioPagoItem.get_ID() <= 0)){
							return "Libreta no tiene medios de pago disponibles para utilizar.";
						}
						NEW_medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
						replaceDet.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
					}
					else{
						// Creo el item de medio de pago sin folio asociado
						NEW_medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
						NEW_medioPagoItem.setZ_MedioPago_ID(replaceDet.getZ_MedioPago_ID());
						NEW_medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
						if (replaceDet.getC_BankAccount_ID() > 0){
							NEW_medioPagoItem.setC_BankAccount_ID(replaceDet.getC_BankAccount_ID());
						}
						if (replaceDet.getC_CashBook_ID() > 0){
							NEW_medioPagoItem.setC_CashBook_ID(replaceDet.getC_CashBook_ID());
						}

						NEW_medioPagoItem.setC_Currency_ID(replaceDet.getC_Currency_ID());

						if ((replaceDet.getDocumentNoRef() == null) || (replaceDet.getDocumentNoRef().trim().equalsIgnoreCase(""))){

							NEW_medioPagoItem.setNroMedioPago(String.valueOf(replaceDet.get_ID()));

							// Seteo numero de medio de pago en la linea de medio de pago
							action = " update z_MedioPagoReplaceDet set documentnoref ='" + NEW_medioPagoItem.getNroMedioPago() + "' " +
									" where z_MedioPagoReplaceDet_ID =" + replaceDet.get_ID();
							DB.executeUpdateEx(action, get_TrxName());

						}
						else{
							NEW_medioPagoItem.setNroMedioPago(replaceDet.getDocumentNoRef());
						}

						NEW_medioPagoItem.setDateEmitted(replaceDet.getDateEmitted());
						NEW_medioPagoItem.setDueDate(replaceDet.getDueDate());

						NEW_medioPagoItem.setIsReceipt(false);
						NEW_medioPagoItem.setEmitido(false);
						NEW_medioPagoItem.setTotalAmt(replaceDet.getTotalAmt());
						NEW_medioPagoItem.setIsOwn(true);
						NEW_medioPagoItem.setC_BPartner_ID(OLD_medioPagoItem.getC_BPartner_ID());
					}
					NEW_medioPagoItem.saveEx();
					replaceDet.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
				}
				else{
					NEW_medioPagoItem = (MZMedioPagoItem) replaceDet.getZ_MedioPagoItem();
					if ((NEW_medioPagoItem.getTotalAmt() == null) || (NEW_medioPagoItem.getTotalAmt().compareTo(Env.ZERO) == 0)){
						NEW_medioPagoItem.setTotalAmt(replaceDet.getTotalAmt());
					}
					NEW_medioPagoItem.setDateEmitted(replaceDet.getDateEmitted());
					NEW_medioPagoItem.setDueDate(replaceDet.getDueDate());
				}

				replaceDet.saveEx();

				// Realizo emisión para este medio de pago a considerar
				if (!NEW_medioPagoItem.isEmitido()){
					MZEmisionMedioPago emisionMedioPago = new MZEmisionMedioPago(getCtx(), 0, get_TrxName());
					emisionMedioPago.setZ_MedioPago_ID(replaceDet.getZ_MedioPago_ID());
					emisionMedioPago.setAD_Org_ID(this.getAD_Org_ID());

					if (replaceDet.getZ_MedioPagoFolio_ID() > 0){
						emisionMedioPago.setZ_MedioPagoFolio_ID(replaceDet.getZ_MedioPagoFolio_ID());
					}

					if ((NEW_medioPagoItem != null) && (NEW_medioPagoItem.get_ID() > 0)){
						emisionMedioPago.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
						emisionMedioPago.setC_Currency_ID(NEW_medioPagoItem.getC_Currency_ID());

						if (NEW_medioPagoItem.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(NEW_medioPagoItem.getC_BankAccount_ID());
						}
						if (NEW_medioPagoItem.getC_CashBook_ID() > 0){
							emisionMedioPago.setC_CashBook_ID(NEW_medioPagoItem.getC_CashBook_ID());
						}

					}
					else{
						emisionMedioPago.setReferenceNo(replaceDet.getDocumentNoRef());
						emisionMedioPago.setC_Currency_ID(replaceDet.getC_Currency_ID());

						if (replaceDet.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(replaceDet.getC_BankAccount_ID());
						}
						if (replaceDet.getC_CashBook_ID() > 0){
							emisionMedioPago.setC_CashBook_ID(replaceDet.getC_CashBook_ID());
						}
					}

					emisionMedioPago.setZ_MedioPagoReplace_ID(this.get_ID());
					emisionMedioPago.setC_BPartner_ID(OLD_medioPagoItem.getC_BPartner_ID());
					emisionMedioPago.setDateDoc(this.getDateDoc());
					emisionMedioPago.setDateEmitted(replaceDet.getDateEmitted());
					emisionMedioPago.setDueDate(replaceDet.getDueDate());
					emisionMedioPago.setTotalAmt(replaceDet.getTotalAmt());

					if (OLD_medioPagoItem.getZ_OrdenPago_ID() > 0){
						emisionMedioPago.setZ_OrdenPago_ID(OLD_medioPagoItem.getZ_OrdenPago_ID());
					}

					if (OLD_medioPagoItem.getZ_Pago_ID() > 0){
						emisionMedioPago.setZ_Pago_ID(OLD_medioPagoItem.getZ_Pago_ID());
					}

					emisionMedioPago.saveEx();

					// Completo documento de emisión de medio de pago
					if (!emisionMedioPago.processIt(DocAction.ACTION_Complete)){
						m_processMsg = emisionMedioPago.getProcessMsg();
						if (m_processMsg != null){
							return DocAction.STATUS_Invalid;
						}
					}
					emisionMedioPago.saveEx();
				}

				NEW_medioPagoItem.setEntregado(OLD_medioPagoItem.isEntregado());
				NEW_medioPagoItem.setZ_MedioPagoReplace_ID(this.get_ID());
				NEW_medioPagoItem.saveEx();

				// Asocio orden de pago y pago a nuevo item de medio de pago
				if (OLD_medioPagoItem.getZ_OrdenPago_ID() > 0){
					MZOrdenPago ordenPago = (MZOrdenPago) OLD_medioPagoItem.getZ_OrdenPago();
					MZOrdenPagoMedio ordenPagoMedio = ordenPago.getMedioPagoByItem(OLD_medioPagoItem.get_ID());
					if ((ordenPagoMedio == null) || (ordenPagoMedio.get_ID() <= 0)){
						ordenPagoMedio = new MZOrdenPagoMedio(getCtx(), 0, get_TrxName());
						ordenPagoMedio.setZ_OrdenPago_ID(ordenPago.get_ID());
						ordenPagoMedio.setAD_Org_ID(ordenPago.getAD_Org_ID());
						ordenPagoMedio.setZ_GeneraOrdenPago_ID(ordenPago.getZ_GeneraOrdenPago_ID());
						ordenPagoMedio.setC_BPartner_ID(NEW_medioPagoItem.getC_BPartner_ID());
					}
					ordenPagoMedio.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
					ordenPagoMedio.setZ_MedioPagoFolio_ID(NEW_medioPagoItem.getZ_MedioPagoFolio_ID());
					ordenPagoMedio.setZ_MedioPago_ID(NEW_medioPagoItem.getZ_MedioPago_ID());
					ordenPagoMedio.setC_BankAccount_ID(NEW_medioPagoItem.getC_BankAccount_ID());
					ordenPagoMedio.setDateEmitted(NEW_medioPagoItem.getDateEmitted());
					ordenPagoMedio.setDueDate(NEW_medioPagoItem.getDueDate());
					ordenPagoMedio.setC_Currency_ID(NEW_medioPagoItem.getC_Currency_ID());
					ordenPagoMedio.setTotalAmt(NEW_medioPagoItem.getTotalAmt());
					ordenPagoMedio.saveEx();
				}
				if (OLD_medioPagoItem.getZ_Pago_ID() > 0){
					MZPago pago = (MZPago) OLD_medioPagoItem.getZ_Pago();
					MZPagoMedioPago pagoMedioPago = pago.getMedioPagoByItem(OLD_medioPagoItem.get_ID());
					if ((pagoMedioPago == null) || (pagoMedioPago.get_ID() <= 0)){
						pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
						pagoMedioPago.setZ_Pago_ID(pago.get_ID());
						if (NEW_medioPagoItem.getZ_OrdenPago_ID() > 0){
							pagoMedioPago.setZ_OrdenPago_ID(NEW_medioPagoItem.getZ_OrdenPago_ID());
						}
						pagoMedioPago.setAD_Org_ID(pago.getAD_Org_ID());
						pagoMedioPago.setIsReceipt(false);
					}
					pagoMedioPago.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
					pagoMedioPago.setZ_MedioPagoFolio_ID(NEW_medioPagoItem.getZ_MedioPagoFolio_ID());
					pagoMedioPago.setZ_MedioPago_ID(NEW_medioPagoItem.getZ_MedioPago_ID());
					pagoMedioPago.setC_BankAccount_ID(NEW_medioPagoItem.getC_BankAccount_ID());
					pagoMedioPago.setDateEmitted(NEW_medioPagoItem.getDateEmitted());
					pagoMedioPago.setDueDate(NEW_medioPagoItem.getDueDate());
					pagoMedioPago.setC_Currency_ID(NEW_medioPagoItem.getC_Currency_ID());
					pagoMedioPago.setTotalAmt(NEW_medioPagoItem.getTotalAmt());
					pagoMedioPago.saveEx();
				}

				// Genero auditoría para este documento
				MZMedioPagoReplaceAud replaceAud = new MZMedioPagoReplaceAud(getCtx(), 0, get_TrxName());
				replaceAud.setAD_Org_ID(this.getAD_Org_ID());
				replaceAud.setZ_MedioPago_ID(NEW_medioPagoItem.getZ_MedioPago_ID());
				replaceAud.setZ_MedioPagoFolio_ID(NEW_medioPagoItem.getZ_MedioPagoFolio_ID());
				replaceAud.setZ_MedioPagoItem_ID(NEW_medioPagoItem.get_ID());
				replaceAud.setZ_MedioPagoItem_Old_ID(OLD_medioPagoItem.get_ID());
				replaceAud.setZ_MedioPagoReplace_ID(this.get_ID());
				if (NEW_medioPagoItem.getZ_OrdenPago_ID() > 0){
					replaceAud.setZ_OrdenPago_ID(NEW_medioPagoItem.getZ_OrdenPago_ID());
				}
				replaceAud.setC_BankAccount_ID(NEW_medioPagoItem.getC_BankAccount_ID());
				replaceAud.setC_BPartner_ID(OLD_medioPagoItem.getC_BPartner_ID());
				replaceAud.setC_Currency_ID(NEW_medioPagoItem.getC_Currency_ID());
				replaceAud.setDateEmitted(NEW_medioPagoItem.getDateEmitted());
				replaceAud.setDueDate(NEW_medioPagoItem.getDueDate());
				replaceAud.setNroMedioPago(NEW_medioPagoItem.getNroMedioPago());
				replaceAud.setTotalAmt(NEW_medioPagoItem.getTotalAmt());
				replaceAud.saveEx();

				// Datos para auditoria en el viejo medio de pago.
				newMedioPagoItemID = NEW_medioPagoItem.get_ID();
				if (!nrosMediosPago.equalsIgnoreCase("")){
					nrosMediosPago += ", ";
				}
				nrosMediosPago += NEW_medioPagoItem.getNroMedioPago();
			}

			// Anulo medio de pago a reemplazar
			OLD_medioPagoItem.setZ_MedioPagoReplace_ID(this.get_ID());
			OLD_medioPagoItem.setReemplazado(true);
			OLD_medioPagoItem.setZ_MedioPagoItem_Rep_ID(newMedioPagoItemID);
			OLD_medioPagoItem.saveEx();

			MZEmisionMedioPago OLD_emisionMedioPago = (MZEmisionMedioPago) OLD_medioPagoItem.getZ_EmisionMedioPago();
			OLD_emisionMedioPago.setDescription("Reemplazado por Medios de Pago : " + nrosMediosPago);
			if (!OLD_emisionMedioPago.processIt(DocAction.ACTION_Void)){
				if (OLD_emisionMedioPago.getProcessMsg() != null){
					m_processMsg = OLD_emisionMedioPago.getProcessMsg();
				}
				else{
					m_processMsg = "No se pudo Anular Medio de Pago : " + OLD_medioPagoItem.getNroMedioPago();
				}
				return DocAction.STATUS_Invalid;
			}
			OLD_emisionMedioPago.saveEx();
		}

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
	 * Validaciones del documento al momento de completar.
	 * Xpande. Created by Gabriel Vila on 9/27/17.
	 * @param lines
	 * @return
	 */
	private String validateDocument(List<MZMedioPagoReplaceLin> lines) {

		String message = null;

		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		if (lines.size() <= 0){
			return "El documento no tiene medios de pago para Reemplazar.";
		}

		// Valido detalle de nuevos medios de pago por cada linea del medio de pago que se quiere reemplazar
		for (MZMedioPagoReplaceLin replaceLin: lines){

			// Obtengo detalle de esta linea
			List<MZMedioPagoReplaceDet> dets = replaceLin.getDetail();
			if (dets.size() <= 0){
				return "No se indica nuevos medios de pago para el medio de pago a reemplazar con número: " + replaceLin.getNroMedioPago();
			}

			BigDecimal montoDetalle = Env.ZERO;
			// Recorro y valido detalle
			for (MZMedioPagoReplaceDet replaceDet: dets){

				/*
				if (replaceDet.getDateEmitted().before(fechaHoy)){
					replaceDet.setDateEmitted(fechaHoy);
					replaceDet.saveEx();
				}
				*/


				if ((replaceDet.getTotalAmt() == null) || (replaceDet.getTotalAmt().compareTo(Env.ZERO) <= 0)){
					return "Falta indicar importe mayor a cero, en el reemplazo de medio de pago con número : " + replaceLin.getNroMedioPago();
				}

				montoDetalle = montoDetalle.add(replaceDet.getTotalAmt());
			}

			if (montoDetalle.compareTo(replaceLin.getTotalAmt()) != 0){
				return "Suma de Importes incorrecta, en el reemplazo de medio de pago con número : " + replaceLin.getNroMedioPago();
			}
		}


		return message;
	}


	/***
	 * Obtiene y retorna lineas seleccionadas de este proceso.
	 * Xpande. Created by Gabriel Vila on 9/27/17.
	 * @return
	 */
	public List<MZMedioPagoReplaceLin> getLines() {

		String whereClause = X_Z_MedioPagoReplaceLin.COLUMNNAME_Z_MedioPagoReplace_ID + " =" + this.get_ID();

		List<MZMedioPagoReplaceLin> lines = new Query(getCtx(), I_Z_MedioPagoReplaceLin.Table_Name, whereClause, get_TrxName()).list();

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
		return closeIt();
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
		setProcessed(false);
		if (reverseCorrectIt())
			return true;
		return false;
	}	//	reActivateIt
	
	
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
      StringBuffer sb = new StringBuffer ("MZMedioPagoReplace[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	/***
	 * Obtiene y carga medios de pago a considerar en este proceso según filtros indicados por el usuarios.
	 * Xpande. Created by Gabriel Vila on 9/21/17.
	 * @return
	 */
	public String getDocuments() {

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			// Elimino lineas anteriores si las hay.
			this.deleteData();

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			String whereClause = " and mpi.z_mediopago_id =" + this.getZ_MedioPago_ID();

			if (this.getC_BPartner_ID() > 0){
				whereClause += " and mpi.c_bpartner_id =" + this.getC_BPartner_ID();
			}
			if (this.getNumeroDesde() > 0){
				whereClause += " and cast(mpi.nromediopago as numeric(10,0)) >=" + this.getNumeroDesde();
			}
			if (this.getNumeroHasta() > 0){
				whereClause += " and cast(mpi.nromediopago as numeric(10,0)) <=" + this.getNumeroHasta();
			}
			if (this.getDateEmittedFrom() != null){
				whereClause += " and mpi.dateemitted >='" + this.getDateEmittedFrom() + "'";
			}
			if (this.getDateEmittedTo() != null){
				whereClause += " and mpi.dateemitted <='" + this.getDateEmittedTo() + "'";
			}
			if (this.getDueDateFrom() != null){
				whereClause += " and mpi.duedate >='" + this.getDueDateFrom() + "'";
			}
			if (this.getDueDateTo() != null){
				whereClause += " and mpi.duedate <='" + this.getDueDateTo() + "'";
			}

		    sql = " select mpi.z_mediopagoitem_id " +
					" from z_mediopagoitem mpi " +
					" where mpi.c_currency_id =" + this.getC_Currency_ID() +
					//" and mpi.emitido ='Y' and mpi.anulado ='N' and mpi.entregado ='N' " + whereClause;
					" and mpi.emitido ='Y' and mpi.anulado ='N' and mpi.conciliado ='N' " + whereClause;

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Instancio modelo de item de medio de pago
				MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(getCtx(), rs.getInt("z_mediopagoitem_id"), get_TrxName());

				// Nueva linea para este proceso con datos del item
				MZMedioPagoReplaceLin replaceLin = new MZMedioPagoReplaceLin(getCtx(), 0, get_TrxName());
				replaceLin.setAD_Org_ID(this.getAD_Org_ID());
				replaceLin.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
				replaceLin.setZ_MedioPagoReplace_ID(this.get_ID());
				replaceLin.setZ_MedioPago_ID(medioPagoItem.getZ_MedioPago_ID());

				if (medioPagoItem.getZ_MedioPagoFolio_ID() > 0){
					replaceLin.setZ_MedioPagoFolio_ID(medioPagoItem.getZ_MedioPagoFolio_ID());
				}

				if (medioPagoItem.getZ_OrdenPago_ID() > 0){
					replaceLin.setZ_OrdenPago_ID(medioPagoItem.getZ_OrdenPago_ID());
				}

				if (medioPagoItem.getZ_Pago_ID() > 0){
					replaceLin.setZ_Pago_ID(medioPagoItem.getZ_Pago_ID());
				}

				replaceLin.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
				replaceLin.setC_BPartner_ID(medioPagoItem.getC_BPartner_ID());
				replaceLin.setC_Currency_ID(medioPagoItem.getC_Currency_ID());
				replaceLin.setDateEmitted(medioPagoItem.getDateEmitted());
				replaceLin.setDueDate(medioPagoItem.getDueDate());

				if (!replaceLin.getDueDate().before(fechaHoy)){
					replaceLin.setDueDateTo(replaceLin.getDueDate());
				}
				else{
					replaceLin.setDueDateTo(fechaHoy);
				}

				replaceLin.setIsSelected(false);
				replaceLin.setNroMedioPago(medioPagoItem.getNroMedioPago());
				replaceLin.setTotalAmt(medioPagoItem.getTotalAmt());
				replaceLin.saveEx();


				// Si el usuario indica flag de sugerir medio de pago nuevos
				if (this.isSelected()){
					// Nuevo medio de pago por defecto para este reemplazo
					MZMedioPagoReplaceDet replaceDet = new MZMedioPagoReplaceDet(getCtx(), 0, get_TrxName());
					replaceDet.setAD_Org_ID(this.getAD_Org_ID());
					replaceDet.setC_BankAccount_ID(replaceLin.getC_BankAccount_ID());
					replaceDet.setC_Currency_ID(replaceLin.getC_Currency_ID());
					replaceDet.setDateEmitted(this.getDateDoc());
					replaceDet.setDueDate(replaceLin.getDueDateTo());
					replaceDet.setTotalAmt(replaceLin.getTotalAmt());
					replaceDet.setZ_MedioPago_ID(replaceLin.getZ_MedioPago_ID());
					replaceDet.setZ_MedioPagoFolio_ID(replaceLin.getZ_MedioPagoFolio_ID());
					replaceDet.setZ_MedioPagoReplace_ID(this.get_ID());
					replaceDet.setZ_MedioPagoReplaceLin_ID(replaceLin.get_ID());
					replaceDet.saveEx();
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

		return message;
	}

	/***
	 * Elimino lineas anteriores.
	 * Xpande. Created by Gabriel Vila on 9/27/17.
	 */
	private void deleteData() {

		try{
			String action = " delete from z_mediopagoreplacelin cascade where z_mediopagoreplace_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}
}