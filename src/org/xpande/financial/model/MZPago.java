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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/** Generated Model for Z_Pago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZPago extends X_Z_Pago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20180124L;

    /** Standard Constructor */
    public MZPago (Properties ctx, int Z_Pago_ID, String trxName)
    {
      super (ctx, Z_Pago_ID, trxName);
    }

    /** Load Constructor */
    public MZPago (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("MZPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Obtiene documentos con saldo y resguardos a considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @param tipoAccion
	 * @return
	 */
	public String getDocumentos(String tipoAccion) {

		String message = null;

		try{

			boolean getDocumentosNuevos = true;

			if (!tipoAccion.equalsIgnoreCase("NUEVOS")){
				getDocumentosNuevos = false;
			}

			// Elimino generacion anterior en caso de que el usuario asi lo indique
			if (!getDocumentosNuevos){
				this.deleteDocuments();
			}

			// Obtengo invoices a considerar y genero lineas
			message = this.getInvoices();
			if (message != null){
				return message;
			}

			// Obtengo resguardos a considerar y genero lineas
			//message = this.getResguardos();
			if (message != null){
				return message;
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Elimina documentos existentes a considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 */
	private void deleteDocuments() {

		String action = "";

		try{

			action = " delete from z_pagolin where z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_pagomoneda where z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_pagoresguardo where z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene invoices a considerar y genera lineas por cada uno de ellos.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private String getInvoices(){

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			// Medio de pago por defecto en caso que el socio no tengo medio de pago predeterminado (por defecto CHEQUE)
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

			String whereClause = "";

			// Filtros de fechas
			if (this.getDateEmittedFrom() != null){
				whereClause = " AND hdr.DateInvoiced >='" + this.getDateEmittedFrom() + "' ";
			}
			if (this.getDateEmittedTo() != null){
				whereClause = " AND hdr.DateInvoiced <='" + this.getDateEmittedTo() + "' ";
			}
			if (this.getDueDateFrom() != null){
				whereClause = " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) >='" + this.getDueDateFrom() + "' ";
			}
			if (this.getDueDateTo() != null){
				whereClause = " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) <='" + this.getDueDateTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = "";
			if (this.getC_Currency_ID_To() > 0){
				filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID_To();
			}
			whereClause += filtroMonedas;

			// Query
			sql = " select hdr.c_bpartner_id, hdr.c_invoice_id, hdr.c_doctypetarget_id, (hdr.documentserie || hdr.documentno) as documentno, " +
					" hdr.dateinvoiced, hdr.c_currency_id, coalesce(ips.dueamt,hdr.grandtotal) as grandtotal,  " +
					" coalesce(hdr.isindispute,'N') as isindispute, doc.docbasetype, coalesce(hdr.TieneDtosNC,'N') as TieneDtosNC, " +
					" coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced)::timestamp without time zone  as duedate " +
					" from c_invoice hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctypetarget_id = doc.c_doctype_id " +
					" left outer join c_invoicepayschedule ips on hdr.c_invoice_id = ips.c_invoice_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.issotrx='N' " +
					" and hdr.docstatus='CO' " +
					" and hdr.c_invoice_id not in (select c_invoice_id from z_generaordenpagolin " +
					" where c_invoice_id is not null " +
					" and z_generaordenpago_id =" + this.get_ID() + ") " +
					" and hdr.c_invoice_id not in (select c_invoice_id from z_ordenpagolin a " +
					" inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
					" where c_invoice_id is not null and b.docstatus='CO') " +
					whereClause +
					" order by hdr.c_bpartner_id ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				boolean esFactura = true;
				if (rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
					esFactura = false;
				}

				BigDecimal amtDocument = rs.getBigDecimal("grandtotal");
				if (!esFactura){
					amtDocument = amtDocument.negate();
				}

				MZGeneraOrdenPagoLin ordenPagoLin = new MZGeneraOrdenPagoLin(getCtx(), 0, get_TrxName());
				ordenPagoLin.setZ_GeneraOrdenPago_ID(this.get_ID());
				//ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());
				ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
				ordenPagoLin.setAmtAllocation(amtDocument);
				ordenPagoLin.setAmtDocument(amtDocument);
				ordenPagoLin.setAmtOpen(amtDocument);
				ordenPagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				ordenPagoLin.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
				ordenPagoLin.setDateDoc(rs.getTimestamp("dateinvoiced"));
				ordenPagoLin.setDueDateDoc(rs.getTimestamp("duedate"));
				ordenPagoLin.setDocumentNoRef(rs.getString("documentno"));
				ordenPagoLin.setDueDateMedioPago(ordenPagoLin.getDueDateDoc());
				ordenPagoLin.setEstadoAprobacion("APROBADO");
				ordenPagoLin.setC_Invoice_ID(rs.getInt("c_invoice_id"));

				boolean tieneDtosNC = (rs.getString("TieneDtosNC").equalsIgnoreCase("Y")) ? true : false;
				ordenPagoLin.setTieneDtosNC(tieneDtosNC);

				ordenPagoLin.setResguardoEmitido(MZResguardoSocio.invoiceTieneResguardo(getCtx(), ordenPagoLin.getC_Invoice_ID(), get_TrxName()));

				ordenPagoLin.saveEx();
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