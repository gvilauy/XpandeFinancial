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
import org.xpande.financial.utils.FinancialUtils;

/** Generated Model for Z_TransferSaldo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZTransferSaldo extends X_Z_TransferSaldo implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20190313L;

    /** Standard Constructor */
    public MZTransferSaldo (Properties ctx, int Z_TransferSaldo_ID, String trxName)
    {
      super (ctx, Z_TransferSaldo_ID, trxName);
    }

    /** Load Constructor */
    public MZTransferSaldo (Properties ctx, ResultSet rs, String trxName)
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
			//options[newIndex++] = DocumentEngine.ACTION_ReActivate;
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

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
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


		// Obtengo fecha de vencimiento de la invoice referenciada
		// Fecha de Vencimiento de esta invoice, directamente de termino de pago
		String sql = " select paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate " +
				" from c_invoice " +
				" where c_invoice_id =" + this.getC_Invoice_ID();
		Timestamp dueDate = DB.getSQLValueTSEx(get_TrxName(), sql);
		if (dueDate == null){
			dueDate = this.getDateInvoiced();
		}

		// Afecto invoice referenciada
		this.afectarInvoice(dueDate);

		// Impacto en estado de cuenta
		FinancialUtils.setEstadoCtaTransferSaldo(getCtx(), this, dueDate, true, get_TrxName());

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

		// Valido que no este asociado a una orden de pago.
		m_processMsg = this.validateInOrdenPago();
		if (m_processMsg != null){
			return false;
		}

		// Valido que no este asociado a un pago.
		m_processMsg = this.validateInPago();
		if (m_processMsg != null){
			return false;
		}

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// Desafecto documentos asociados a este documento
		m_processMsg = this.desafectarDocumentos();
		if (m_processMsg != null)
			return false;

		// Impacto en estado de cuenta
		FinancialUtils.setEstadoCtaTransferSaldo(getCtx(), this, null, false, get_TrxName());

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
		/*
		log.info("reActivateIt - " + toString());
		setProcessed(false);
		if (reverseCorrectIt())
			return true;
		 */

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
      StringBuffer sb = new StringBuffer ("MZTransferSaldo[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Afecta invoice referenciada.
	 * Xpande. Created by Gabriel Vila on 3/13/19.
	 * @param dueDate
	 * @return
	 */
	private void afectarInvoice(Timestamp dueDate){

		try{

			MZInvoiceAfectacion	invoiceAfecta = new MZInvoiceAfectacion(getCtx(), 0, get_TrxName());

			invoiceAfecta.setAD_Table_ID(this.get_Table_ID());
			invoiceAfecta.setAmtAllocation(this.getGrandTotal());
			invoiceAfecta.setC_DocType_ID(this.getC_DocType_ID());
			invoiceAfecta.setC_Invoice_ID(this.getC_Invoice_ID());
			invoiceAfecta.setDateDoc(this.getDateDoc());
			invoiceAfecta.setDocumentNoRef(this.getDocumentNo());
			invoiceAfecta.setDueDate(dueDate);
			invoiceAfecta.setRecord_ID(this.get_ID());
			invoiceAfecta.setC_Currency_ID(this.getC_Currency_ID());
			invoiceAfecta.setAD_Org_ID(this.getAD_Org_ID());
			invoiceAfecta.setZ_TransferSaldo_ID(this.get_ID());
			invoiceAfecta.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Setea info de este documento según modelo de invoice recibido.
	 * Xpande. Created by Gabriel Vila on 3/13/19.
	 * @param invoice
	 * @return
	 */
	public String generateFromInvoice(MInvoice invoice) {

		String message = null;

		try{

			String docBaseType = "TSP";
			if (invoice.isSOTrx()){
				docBaseType = "TSC";
			}

			MDocType[] docTypes = MDocType.getOfDocBaseType(getCtx(), docBaseType);
			if (docTypes.length <= 0){
				return "Falta definir Documento para Transferencia de Saldo";
			}
			MDocType docType = docTypes[0];

			String documentNoRef = invoice.getDocumentNo();
			if (invoice.get_ValueAsString("DocumentSerie") != null){
				documentNoRef = invoice.get_ValueAsString("DocumentSerie").trim() + documentNoRef;
			}

			this.setAD_Client_ID(invoice.getAD_Client_ID());
			this.setAD_Org_ID(invoice.getAD_Org_ID());
			this.setC_Invoice_ID(invoice.get_ID());
			this.setC_BPartner_ID(invoice.get_ValueAsInt("C_BPartnerRelation_ID"));
			this.setC_BPartnerRelation_ID(invoice.getC_BPartner_ID());
			this.setC_Currency_ID(invoice.getC_Currency_ID());
			this.setC_DocTypeTarget_ID(invoice.getC_DocTypeTarget_ID());
			this.setC_DocType_ID(docType.get_ID());
			this.setDateDoc(invoice.getDateInvoiced());
			this.setDateInvoiced(invoice.getDateInvoiced());
			this.setDescription("Generada Automáticamente");
			this.setDocumentNoRef(invoice.getDocumentNo());
			this.setDocumentNoRef(documentNoRef);
			this.setGrandTotal(invoice.getGrandTotal());
			this.setIsSOTrx(invoice.isSOTrx());

			this.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Al anular o reactivar un documento debo desasociar la invoice referenciada.
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	private String desafectarDocumentos() {

		String message = null;
		String action = "";

		try{

			// Elimino Afectacion de invoices
			action = " delete from z_invoiceafectacion where z_transfersaldo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Valida si este documento esta asociado a un documento de Orden de Pago
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	private String validateInOrdenPago() {

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			sql = " select pl.z_transfersaldo_id, p.documentno, p.docstatus " +
					" from z_ordenpagolin pl " +
					" inner join z_ordenpago p on pl.z_ordenpago_id = p.z_ordenpago_id " +
					" where pl.z_transfersaldo_id =" + this.get_ID() +
					" and p.docstatus != 'VO' ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			if (rs.next()){
				message = " Este comprobante esta asociado a la Orden de Pago : " + rs.getString("documentno") +
						" (Estado Documento = " + rs.getString("docstatus") + ")";
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
	 * Valida si este documento esta asociado a un documento de Pago / Cobro
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	private String validateInPago() {

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			sql = " select pl.z_transfersaldo_id, p.documentno, p.docstatus " +
					" from z_pagolin pl " +
					" inner join z_pago p on pl.z_pago_id = p.z_pago_id " +
					" where pl.z_transfersaldo_id =" + this.get_ID() +
					" and pl.IsSelected ='Y' " +
					" and p.docstatus != 'VO' ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			if (rs.next()){
				if (this.isSOTrx()){
					message = " Este comprobante esta asociado al Cobro : " + rs.getString("documentno") +
							" (Estado Documento = " + rs.getString("docstatus") + ")";
				}
				else{
					message = " Este comprobante esta asociado al Pago : " + rs.getString("documentno") +
							" (Estado Documento = " + rs.getString("docstatus") + ")";
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

}