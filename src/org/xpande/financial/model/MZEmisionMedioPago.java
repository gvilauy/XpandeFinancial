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
import java.util.Date;
import java.util.Properties;

import dto.migrate.CFEInvoiCyType;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.AcctUtils;
import org.xpande.core.utils.DateUtils;

/** Generated Model for Z_EmisionMedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZEmisionMedioPago extends X_Z_EmisionMedioPago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170821L;

    /** Standard Constructor */
    public MZEmisionMedioPago (Properties ctx, int Z_EmisionMedioPago_ID, String trxName)
    {
      super (ctx, Z_EmisionMedioPago_ID, trxName);
    }

    /** Load Constructor */
    public MZEmisionMedioPago (Properties ctx, ResultSet rs, String trxName)
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

			options[newIndex++] = DocumentEngine.ACTION_Void;
			options[newIndex++] = DocumentEngine.ACTION_ReActivate;
			//options[newIndex++] = DocumentEngine.ACTION_None;

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

		/*
		// Quito esta validación ya que se pueden emitir manualmente cheques de fechas pasadas en caso de contingencia.
		// Me aseguro fecha de emisión no menor a hoy
		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (this.getDateEmitted().before(fechaHoy)){
			this.setDateEmitted(fechaHoy);
		}
		 */

		// Validaciones de este documento
		m_processMsg = this.validateDocument();
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Marco medio de pago como emitido
		if (this.getZ_MedioPagoItem_ID() > 0){
			MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
			medioPagoItem.setTotalAmt(this.getTotalAmt());
			medioPagoItem.setEmitido(true);
			medioPagoItem.setC_BPartner_ID(this.getC_BPartner_ID());
			medioPagoItem.setDateEmitted(this.getDateEmitted());
			medioPagoItem.setDueDate(this.getDueDate());
			medioPagoItem.setLeyendasImpresion();
			medioPagoItem.setZ_EmisionMedioPago_ID(this.get_ID());
			if (this.getZ_OrdenPago_ID() > 0){
				medioPagoItem.setZ_OrdenPago_ID(this.getZ_OrdenPago_ID());
			}
			if (this.getZ_Pago_ID() > 0){
				medioPagoItem.setZ_Pago_ID(this.getZ_Pago_ID());
			}
			if (this.getZ_MedioPagoReplace_ID() > 0){
				medioPagoItem.setZ_MedioPagoReplace_ID(this.getZ_MedioPagoReplace_ID());
			}
			medioPagoItem.saveEx();
		}

		// Si esta emision de medio de pago tiene como origen un documento de reemplazo, no hago el asiento aca, sino que se hace en el
		// documento mismo del reemplazo.
		if (this.getZ_MedioPagoReplace_ID() > 0){
			this.setPosted(true);
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


	private String validateDocument() {

		String message = null;

		try{


			/*
			// Quito esta validación ya que se pueden emitir manualmente cheques de fechas pasadas en caso de contingencia.
			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Valido fecha de emsión no menor a fecha de hoy
			if (this.getDateEmitted().before(fechaHoy)){
				return "Fecha de emisión no puede ser anterior a la fecha de hoy.";
			}
			*/

			// Valido que tenga organizacion
			if (this.getAD_Org_ID() <= 0){
				return "Debe indicar Organización para este Documento";
			}


			// Valido fecha de vencimiento no mayor a 180 dias a partir de fecha de emisión
			Date dateFechaAux = new Date(this.getDateEmitted().getTime());
			dateFechaAux =  DateUtils.addDays(dateFechaAux, 180);
			Timestamp maxDueDate = new Timestamp(dateFechaAux.getTime());
			if (this.getDueDate().after(maxDueDate)){
				return "Fecha de Vencimiento no puede ser mayor a 180 días con respecto a la Fecha de Emisión.";
			}

			if ((this.getTotalAmt() == null) || (this.getTotalAmt().compareTo(Env.ZERO) <= 0)){
				return "Debe indicar Importe.";
			}

			// Valido atributos del medio de pago
			MZMedioPago medioPago = (MZMedioPago) this.getZ_MedioPago();
			if (medioPago.isTieneFolio()){
				if (this.getZ_MedioPagoFolio_ID() <= 0){
					return "Debe indicar Libreta de Medios de Pago.";
				}
			}

			// Validaciones en item de medio de pago
			if (this.getZ_MedioPagoFolio_ID() > 0){

				// Valido que haya medio de pago seleccionado en caso de folios
				if (this.getZ_MedioPagoItem_ID() <= 0){
					return "Debe seleccionar Número de Medio de Pago a considerar.";
				}

				// Valido item no emitido
				MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();
				if (medioPagoItem.isEmitido()){
					return "El número de medio de pago seleccionado esta emitido. Seleccione otro número.";
				}

			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
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
		log.info(toString());

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// Si esta emisión esta asociada a una orden de pago no anulada o a un recibo de proveedor no anulado,
		// no permito la anulación de este documento.
		// Solo se puede anular una emisión de medio de pago que no este asociada.
		if (this.getZ_OrdenPago_ID() > 0){
			MZOrdenPago ordenPago = (MZOrdenPago) this.getZ_OrdenPago();
			if ((ordenPago != null) && (ordenPago.get_ID() > 0)){
				if (!ordenPago.getDocStatus().equalsIgnoreCase(DOCSTATUS_Voided)){
					if (!this.isModificable()){
						m_processMsg = "No es posible Anular este Documento ya que esta asociado a la Orden de Pago número : " + ordenPago.getDocumentNo() + "\n" +
								"Debe anular la Orden de Pago o reactivarla y quitar este medio de pago de la misma.";
						return false;
					}
				}
			}
		}
		if (this.getZ_Pago_ID() > 0){
			MZPago pago = (MZPago) this.getZ_Pago();
			if ((pago != null) && (pago.get_ID() > 0)){
				if (!pago.getDocStatus().equalsIgnoreCase(DOCSTATUS_Voided)){
					if (!this.isModificable()){
						m_processMsg = "No es posible Anular este Documento ya que esta asociado al Recibo de Proveedor con número interno : " + pago.getDocumentNo() + "\n" +
								"Debe anular este Recibo o reactivarlo y quitar este medio de pago del mismo.";
						return false;
					}
				}
			}
		}

		// Intancio modelo de item de medio de pago asociado a este emision
		MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();

		// Si este item NO fue reemplazado por otro, elimino contabilidad y chequeo periodo
		// En un reemplazo el asiento lo hace el documento de reemplazo de medio de pago.
		if (!medioPagoItem.isReemplazado()){
			MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());
			MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());
		}

		// Marco item de medio de pago como Anulado
		medioPagoItem.setEntregado(false);
		medioPagoItem.setAnulado(true);
		medioPagoItem.saveEx();

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
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

		// Si esta emisión esta asociada a una orden de pago no anulada o a un recibo de proveedor no anulado,
		// no permito la anulación de este documento.
		// Solo se puede anular una emisión de medio de pago que no este asociada.
		if (this.getZ_OrdenPago_ID() > 0){
			MZOrdenPago ordenPago = (MZOrdenPago) this.getZ_OrdenPago();
			if ((ordenPago != null) && (ordenPago.get_ID() > 0)){
				if (!ordenPago.getDocStatus().equalsIgnoreCase(DOCSTATUS_Voided)){
					m_processMsg = "No es posible Reactivar este Documento ya que esta asociado a la Orden de Pago número : " + ordenPago.getDocumentNo() + "\n" +
							"Debe anular la Orden de Pago o reactivarla y quitar este medio de pago de la misma.";
					return false;
				}
			}
		}

		if (this.getZ_Pago_ID() > 0){
			MZPago pago = (MZPago) this.getZ_Pago();
			if ((pago != null) && (pago.get_ID() > 0)){
				if (!pago.getDocStatus().equalsIgnoreCase(DOCSTATUS_Voided)){
					m_processMsg = "No es posible Reactivar este Documento ya que esta asociado al Recibo de Proveedor con número interno : " + pago.getDocumentNo() + "\n" +
							"Debe anular este Recibo o reactivarlo y quitar este medio de pago del mismo.";
					return false;
				}
			}
		}

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());

		if (this.getZ_MedioPagoItem_ID() > 0){

			MZMedioPagoItem pagoItem = (MZMedioPagoItem) this.getZ_MedioPagoItem();

			// Valido que el medio de pago emitido, siga estando solamente emitido para poder reactivarlo
			if (!pagoItem.IsOnlyEmitido()){
				m_processMsg = "El medio de pago tiene que estar solamente Emitido para poder ser Modificado";
				return false;
			}

			// Valido que el medio de pago no este asociado a una orden de pago
			if (pagoItem.getZ_OrdenPago_ID() > 0){

				MZOrdenPago ordenPago = (MZOrdenPago) this.getZ_OrdenPago();
				m_processMsg = "No se puede reactivar este medio de pago porque esta asociado a la Orden de Pago número : " + ordenPago.getDocumentNo();
				return false;
			}

			// Desafecto medio de pago
			m_processMsg = pagoItem.desafectar();
			if (m_processMsg != null)
				return false;
		}

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;


		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setDocAction(DOCACTION_Complete);

		return true;

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
      StringBuffer sb = new StringBuffer ("MZEmisionMedioPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (this.getAD_Org_ID() == 0){
			log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
			return false;
		}

		if (newRecord){
			if (this.getC_DocType_ID() <= 0){
				MDocType[] docsEmision = MDocType.getOfDocBaseType(getCtx(), "EMP");
				MDocType docEmision = docsEmision[0];
				this.setC_DocType_ID(docEmision.get_ID());
			}
		}

		// Seteo PaymentRule para el medio de pago asociado a esta emisión
		if ((newRecord) || (is_ValueChanged(X_Z_EmisionMedioPago.COLUMNNAME_Z_MedioPago_ID))){
			if (this.getZ_MedioPago_ID() > 0){
				this.setPaymentRule(((MZMedioPago) this.getZ_MedioPago()).getValue());
			}
		}

		return true;
	}
}