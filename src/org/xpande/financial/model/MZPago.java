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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

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

		// Obtengo lineas a procesar
		List<MZPagoLin> pagoLinList = this.getSelectedLines();

		// Obtengo medios de pago a procesar
		List<MZPagoMedioPago> medioPagoList = this.getMediosPago();

		// Validaciones del documento
		m_processMsg = this.validateDocument(pagoLinList, medioPagoList);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;


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

			HashMap<Integer, Integer> hashCurrency = new HashMap<Integer, Integer>();

			// Obtengo invoices a considerar y genero lineas
			message = this.getInvoices(hashCurrency);
			if (message != null){
				return message;
			}

			// En caso de documentos de PAGO, obtengo resguardos a considerar y genero lineas
			if (!this.isSOTrx()){
				message = this.getResguardos(hashCurrency);
				if (message != null){
					return message;
				}
			}

			// Si tengo monedas, actualizo tabla de monedas de este pago/cobro
			if (hashCurrency.size() > 0){

				// Cargo monedas con tasa de cambio a la fecha de este documento
				message = MZPagoMoneda.setMonedas(getCtx(), this.get_ID(), hashCurrency, get_TrxName());
				if (message != null){
					return message;
				}

				// Actualizo tasa de cambio y monto en moneda transacción, en lineas y resguardos asociados a este documento.
				this.updateRates();
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

			action = " update z_pago set payamt=0, totalmediospago = 0 where z_pago_id =" + this.get_ID();
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
	private String getInvoices(HashMap<Integer, Integer> hashCurrency){

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{



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
					" hdr.dateinvoiced, hdr.c_currency_id, coalesce(ips.dueamt,hdr.grandtotal) as grandtotal, ips.c_invoicepayschedule_id, " +
					" coalesce(hdr.isindispute,'N') as isindispute, doc.docbasetype, coalesce(hdr.TieneDtosNC,'N') as TieneDtosNC, " +
					" coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced)::timestamp without time zone  as duedate " +
					" from c_invoice hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctypetarget_id = doc.c_doctype_id " +
					" left outer join c_invoicepayschedule ips on hdr.c_invoice_id = ips.c_invoice_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.ad_org_id =" + this.getAD_Org_ID() +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.issotrx='" + ((this.isSOTrx()) ? "Y":"N") + "' " +
					" and hdr.docstatus='CO' " +
					" and hdr.c_invoice_id not in (select c_invoice_id from z_pagolin " +
					" where c_invoice_id is not null " +
					" and z_pago_id =" + this.get_ID() + ") " +
					whereClause +
					" order by hdr.dateinvoiced ";

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

				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtAllocation(amtDocument);
				pagoLin.setAmtDocument(amtDocument);
				pagoLin.setAmtOpen(amtDocument);
				pagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoLin.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
				pagoLin.setDateDoc(rs.getTimestamp("dateinvoiced"));
				pagoLin.setDueDateDoc(rs.getTimestamp("duedate"));
				pagoLin.setDocumentNoRef(rs.getString("documentno"));
				pagoLin.setEstadoAprobacion("APROBADO");
				pagoLin.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				if (rs.getInt("c_invoicepayschedule_id") > 0){
					pagoLin.setC_InvoicePaySchedule_ID(rs.getInt("c_invoicepayschedule_id"));
				}
				pagoLin.setResguardoEmitido(MZResguardoSocio.invoiceTieneResguardo(getCtx(), pagoLin.getC_Invoice_ID(), get_TrxName()));

				pagoLin.saveEx();

				// Guardo moneda en hash si aún no la tengo
				if (!hashCurrency.containsKey(pagoLin.getC_Currency_ID())){
					hashCurrency.put(pagoLin.getC_Currency_ID(), pagoLin.getC_Currency_ID());
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
	 * Obtiene y retorna lineas seleccionadas a considerarse en este modelo.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoLin> getSelectedLines(){

		String whereClause = X_Z_PagoLin.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID() +
				" AND " + X_Z_PagoLin.COLUMNNAME_IsSelected + " ='Y'";

		List<MZPagoLin> lines = new Query(getCtx(), I_Z_PagoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas asociadas a este modelo.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoLin> getLines(){

		String whereClause = X_Z_PagoLin.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoLin> lines = new Query(getCtx(), I_Z_PagoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna resguardos asociados a este modelo.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoResguardo> getResguardos(){

		String whereClause = X_Z_PagoResguardo.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoResguardo> lines = new Query(getCtx(), I_Z_PagoResguardo.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna medios de pago asociados a este modelo.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoMedioPago> getMediosPago(){

		String whereClause = X_Z_PagoMedioPago.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoMedioPago> lines = new Query(getCtx(), I_Z_PagoMedioPago.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Validaciones del documento al momento de completar.
	 * Xpande. Created by Gabriel Vila on 1/10/18.
	 * @param pagoLinList
	 * @param medioPagoList
	 * @return
	 */
	private String validateDocument(List<MZPagoLin> pagoLinList, List<MZPagoMedioPago> medioPagoList) {

		String message = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Si el documento esta marcado con recibo, verifico que se haya ingresado el numero del recibo
			if (this.isTieneRecibo()){
				if ((this.getNroRecibo() == null) || (this.getNroRecibo().trim().equalsIgnoreCase(""))){
					return "Debe indicar Número de Recibo";
				}
			}

			// Verifico que la diferencia entre total del documento y medios a pagar este dentro de la tolerancia permitida
			BigDecimal diferencia = this.getPayAmt().subtract(this.getTotalMediosPago());
			if (diferencia.compareTo(Env.ZERO) != 0){
				return "Hay diferencias entre el Total del Documento y el Total de Medios de Pago.";
			}

			// Verifico que tenga lineas seleccionadas
			if (pagoLinList.size() <= 0){
				return "No hay Documentos seleccionados para afectar.";
			}

			// Verifico que tenga medios de pago
			if (medioPagoList.size() <= 0){
				return "Debe indicar al menos un medio de pago.";
			}


		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;

	}

	/***
	 * Obtiene resguardos a considerar y genera lineas por cada uno de ellos.
	 * Xpande. Created by Gabriel Vila on 1/26/18.
	 * @return
	 */
	private String getResguardos(HashMap<Integer, Integer> hashCurrency) {

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			String whereClause = "";

			// Filtros de fechas
			if (this.getDateEmittedFrom() != null){
				whereClause = " AND hdr.DateDoc >='" + this.getDateEmittedFrom() + "' ";
			}
			if (this.getDateEmittedTo() != null){
				whereClause = " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = "";
			if (this.getC_Currency_ID_To() > 0){
				filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID_To();
			}
			whereClause += filtroMonedas;

			// Query
			sql = " select hdr.c_bpartner_id, hdr.z_resguardosocio_id, hdr.c_doctype_id, hdr.documentno, " +
					" hdr.datedoc, hdr.c_currency_id, hdr.totalamt, doc.docbasetype " +
					" from z_resguardosocio hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.ad_org_id =" + this.getAD_Org_ID() +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.docstatus='CO' " +
					" and hdr.z_resguardosocio_ref_id is null " +
					" and doc.docbasetype='RGU' " +
					" and hdr.z_resguardosocio_id not in (select z_resguardosocio_id from z_pagoresguardo " +
					" where z_resguardosocio_id is not null " +
					" and z_pago_id =" + this.get_ID() + ") "  +
					whereClause +
					" order by hdr.datedoc ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				BigDecimal amtDocument = rs.getBigDecimal("totalamt");
				//amtDocument = amtDocument.negate();

				MZPagoResguardo pagoResguardo = new MZPagoResguardo(getCtx(), 0, get_TrxName());
				pagoResguardo.setZ_Pago_ID(this.get_ID());
				pagoResguardo.setZ_ResguardoSocio_ID(rs.getInt("z_resguardosocio_id"));
				pagoResguardo.setAmtAllocation(amtDocument);
				pagoResguardo.setAmtDocument(amtDocument);
				pagoResguardo.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoResguardo.setC_DocType_ID(rs.getInt("c_doctype_id"));
				pagoResguardo.setDateTrx(rs.getTimestamp("datedoc"));
				pagoResguardo.setDocumentNoRef(rs.getString("documentno"));
				pagoResguardo.saveEx();

				// Guardo moneda en hash si aún no la tengo
				if (!hashCurrency.containsKey(pagoResguardo.getC_Currency_ID())){
					hashCurrency.put(pagoResguardo.getC_Currency_ID(), pagoResguardo.getC_Currency_ID());
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
	 * Actualiza tasa de cambio y como consecuencia monto a pagar en moneda de transacción, en lineas y resguardos asociados
	 * a este documento de pago/cobro.
	 * Xpande. Created by Gabriel Vila on 1/26/18.
	 */
	private void updateRates() {

		try{

			HashMap<Integer, MZPagoMoneda> hashCurrency = new HashMap<Integer, MZPagoMoneda>();

			// Lineas
			List<MZPagoLin> pagoLinList = this.getLines();
			for (MZPagoLin pagoLin: pagoLinList){

				MZPagoMoneda pagoMoneda = null;
				if (hashCurrency.containsKey(pagoLin.getC_Currency_ID())){
					pagoMoneda = hashCurrency.get(pagoLin.getC_Currency_ID());
				}
				else{
					pagoMoneda = MZPagoMoneda.getByCurrency(getCtx(), this.get_ID(), pagoLin.getC_Currency_ID(), get_TrxName());
				}
				if ((pagoMoneda != null) && (pagoMoneda.get_ID() > 0)){
					pagoLin.setMultiplyRate(pagoMoneda.getMultiplyRate());
					pagoLin.saveEx();
				}
			}

			// Resguardos
			List<MZPagoResguardo> resguardoList = this.getResguardos();
			for (MZPagoResguardo pagoResguardo: resguardoList){

				MZPagoMoneda pagoMoneda = null;
				if (hashCurrency.containsKey(pagoResguardo.getC_Currency_ID())){
					pagoMoneda = hashCurrency.get(pagoResguardo.getC_Currency_ID());
				}
				else{
					pagoMoneda = MZPagoMoneda.getByCurrency(getCtx(), this.get_ID(), pagoResguardo.getC_Currency_ID(), get_TrxName());
				}
				if ((pagoMoneda != null) && (pagoMoneda.get_ID() > 0)){
					pagoResguardo.setMultiplyRate(pagoMoneda.getMultiplyRate());
					pagoResguardo.saveEx();
				}
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


	/***
	 * Actualiza totales de este documento, segun montos de lineas, resguardos y medios de pago.
	 * Xpande. Created by Gabriel Vila on 1/26/18.
	 */
	public void updateTotals(){

		String action = "", sql = "";

		try{
			// Obtengo suma de montos a pagar de documentos
			sql = " select sum(coalesce(amtallocationmt,0)) as total from z_pagolin " +
					" where z_pago_id =" + this.get_ID() +
					" and isselected ='Y'";
			BigDecimal sumLines = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumLines == null) sumLines = Env.ZERO;

			// Obtengo suma de montos de resguardos
			sql = " select sum(coalesce(amtallocationmt,0)) as total from z_pagoresguardo " +
					" where z_pago_id =" + this.get_ID() +
					" and isselected ='Y'";
			BigDecimal sumResguardos = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumResguardos == null) sumResguardos = Env.ZERO;

			// Obtengo suma de montos de medios de pago
			sql = " select sum(coalesce(totalamt,0)) as total from z_pagomediopago " +
					" where z_pago_id =" + this.get_ID();
			BigDecimal sumMedios = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumMedios == null) sumMedios = Env.ZERO;

			action = " update z_pago set payamt =" + sumLines + ", " +
					" totalmediospago =" + sumMedios.add(sumResguardos) +
					" where z_pago_id =" + this.get_ID();

			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

}