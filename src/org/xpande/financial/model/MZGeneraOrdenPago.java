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
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

/** Generated Model for Z_GeneraOrdenPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZGeneraOrdenPago extends X_Z_GeneraOrdenPago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170816L;

    /** Standard Constructor */
    public MZGeneraOrdenPago (Properties ctx, int Z_GeneraOrdenPago_ID, String trxName)
    {
      super (ctx, Z_GeneraOrdenPago_ID, trxName);
    }

    /** Load Constructor */
    public MZGeneraOrdenPago (Properties ctx, ResultSet rs, String trxName)
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

			options[newIndex++] = DocumentEngine.ACTION_None;
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

		// Obtiene y recorre lista de ordenes de compra a generar
		List<MZOrdenPago> ordenesPagos = this.getOrdenesPago();
		for (MZOrdenPago ordenPago: ordenesPagos){

			// Completo orden de pago
			if (!ordenPago.processIt(DocAction.ACTION_Complete)){
				m_processMsg = ordenPago.getProcessMsg();
				return DocAction.STATUS_Invalid;
			}
			ordenPago.saveEx();
		}

		// Genera medios de pago necesarios
		//m_processMsg = this.generateMediosPago();
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


	/***
	 * Obtiene y retorna lista de ordenes de pago generadas en este proceso.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private List<MZOrdenPago> getOrdenesPago() {

		String whereClause = X_Z_OrdenPago.COLUMNNAME_Z_GeneraOrdenPago_ID + " =" + this.get_ID();

		List<MZOrdenPago> lines = new Query(getCtx(), I_Z_OrdenPago.Table_Name, whereClause, get_TrxName()).list();

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
      StringBuffer sb = new StringBuffer ("MZGeneraOrdenPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Obtiene documentos a considerar en este proceso.
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
			message = this.getResguardos();
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
	 * Obtiene resguardos a considerar y genera lineas por cada uno de ellos.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private String getResguardos() {

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			// Medio de pago por defecto en caso que el socio no tengo medio de pago predeterminado.
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

			String whereClause = "";

			// Filtros de fechas
			if (this.getDateEmittedFrom() != null){
				whereClause = " AND hdr.DateDoc >='" + this.getDateEmittedFrom() + "' ";
			}
			if (this.getDateEmittedTo() != null){
				whereClause = " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID();
			if (this.getC_Currency_2_ID() > 0){
				filtroMonedas = " AND hdr.c_currency_id IN (" + this.getC_Currency_ID() + ", " + this.getC_Currency_2_ID() + ") ";
			}
			whereClause += filtroMonedas;

			// Filtro de socios de negocio
			String filtroSocios = this.getFiltroSocios();
			if (filtroSocios != null){
				whereClause += " AND " + filtroSocios;
			}

			// Query
			sql = " select hdr.c_bpartner_id, hdr.z_resguardosocio_id, hdr.c_doctype_id, hdr.documentno, " +
					" hdr.datedoc, hdr.c_currency_id, hdr.totalamt, doc.docbasetype " +
					" from z_resguardosocio hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.z_resguardosocio_ref_id is null " +
					" and doc.docbasetype='RGU' " +
					" and hdr.z_resguardosocio_id not in (select z_resguardosocio_id from z_generaordenpagolin " +
					" where z_resguardosocio_id is not null " +
					" and z_generaordenpago_id =" + this.get_ID() + ") "  +
					whereClause +
					" order by hdr.c_bpartner_id ";

			int cBPartnerIDAux = 0;
			MZGeneraOrdenPagoSocio ordenPagoSocio = null;

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Corte por socio de negocio
				if (rs.getInt("c_bpartner_id") != cBPartnerIDAux){

					cBPartnerIDAux = rs.getInt("c_bpartner_id");

					// Obtengo modelo de socio a considerar en esta generación, si ya existe
					ordenPagoSocio = this.getOrdenPagoSocio(cBPartnerIDAux);
					if ((ordenPagoSocio == null) || (ordenPagoSocio.get_ID() <= 0)){
						MBPartner partner = new MBPartner(getCtx(), cBPartnerIDAux, null);
						ordenPagoSocio = new MZGeneraOrdenPagoSocio(getCtx(), 0, get_TrxName());
						ordenPagoSocio.setZ_GeneraOrdenPago_ID(this.get_ID());
						ordenPagoSocio.setC_BPartner_ID(cBPartnerIDAux);
						ordenPagoSocio.setTaxID(partner.getTaxID());
						ordenPagoSocio.setC_Currency_ID(this.getC_Currency_ID());
						ordenPagoSocio.setTotalAmt(Env.ZERO);
						ordenPagoSocio.saveEx();
					}
				}

				BigDecimal amtDocument = rs.getBigDecimal("totalamt");
				amtDocument = amtDocument.negate();

				MZGeneraOrdenPagoLin ordenPagoLin = new MZGeneraOrdenPagoLin(getCtx(), 0, get_TrxName());
				ordenPagoLin.setZ_GeneraOrdenPago_ID(this.get_ID());
				ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());
				ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
				ordenPagoLin.setAmtAllocation(amtDocument);
				ordenPagoLin.setAmtDocument(amtDocument);
				ordenPagoLin.setAmtOpen(amtDocument);
				ordenPagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				ordenPagoLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
				ordenPagoLin.setDateDoc(rs.getTimestamp("datedoc"));
				ordenPagoLin.setDueDateDoc(rs.getTimestamp("datedoc"));
				ordenPagoLin.setDocumentNoRef(rs.getString("documentno"));
				//ordenPagoLin.setDueDateMedioPago(ordenPagoLin.getDueDateDoc());
				ordenPagoLin.setEstadoAprobacion("APROBADO");
				//ordenPagoLin.setResguardoEmitido();
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

	/***
	 * Obtiene y retorna modelo de socio a considerar en este proceso según id de socio recibido.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @param cBPartnerID
	 * @return
	 */
	private MZGeneraOrdenPagoSocio getOrdenPagoSocio(int cBPartnerID) {

		String whereClause = X_Z_GeneraOrdenPagoSocio.COLUMNNAME_Z_GeneraOrdenPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_GeneraOrdenPagoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID;

		MZGeneraOrdenPagoSocio model = new Query(getCtx(), I_Z_GeneraOrdenPagoSocio.Table_Name, whereClause, get_TrxName()).first();

		return model;
	}


	/***
	 * Elimina documentos existentes.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 */
	private void deleteDocuments() {

		String action = "";

		try{

			action = " delete from z_generaordenpagosocio cascade where z_generaordenpago_id =" + this.get_ID();
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
			String filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID();
			if (this.getC_Currency_2_ID() > 0){
				filtroMonedas = " AND hdr.c_currency_id IN (" + this.getC_Currency_ID() + ", " + this.getC_Currency_2_ID() + ") ";
			}
			whereClause += filtroMonedas;

			// Filtro de socios de negocio
			String filtroSocios = this.getFiltroSocios();
			if (filtroSocios != null){
				whereClause += " AND " + filtroSocios;
			}

			// Query
		    sql = " select hdr.c_bpartner_id, hdr.c_invoice_id, hdr.c_doctypetarget_id, (hdr.documentserie || hdr.documentno) as documentno, " +
						" hdr.dateinvoiced, hdr.c_currency_id, coalesce(ips.dueamt,hdr.grandtotal) as grandtotal,  " +
						" coalesce(hdr.isindispute,'N') as isindispute, doc.docbasetype, " +
					" coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced)::timestamp without time zone  as duedate " +
					" from c_invoice hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctypetarget_id = doc.c_doctype_id " +
					" left outer join c_invoicepayschedule ips on hdr.c_invoice_id = ips.c_invoice_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.issotrx='N' " +
					" and hdr.c_invoice_id not in (select c_invoice_id from z_generaordenpagolin " +
					" where c_invoice_id is not null " +
					" and z_generaordenpago_id =" + this.get_ID() + ") " +
					whereClause +
					" order by hdr.c_bpartner_id ";

			int cBPartnerIDAux = 0;
			MZGeneraOrdenPagoSocio ordenPagoSocio = null;

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();
		
			while(rs.next()){

				// Corte por socio de negocio
				if (rs.getInt("c_bpartner_id") != cBPartnerIDAux){

					// Ajusto fecha de vencimiento de medios de pago de lineas para socio de negocio anterior
					if (cBPartnerIDAux > 0){
						// Si usuario indica que agrupa por mayor vencimiento
						if (this.isGroupLastDue()){
							this.groupForLastDueDate(ordenPagoSocio.get_ID());
						}
					}

					cBPartnerIDAux = rs.getInt("c_bpartner_id");

					// Obtengo modelo de socio a considerar en esta generación, si ya existe
					// Si no existe lo creo ahora
					ordenPagoSocio = this.getOrdenPagoSocio(cBPartnerIDAux);
					if ((ordenPagoSocio == null) || (ordenPagoSocio.get_ID() <= 0)){
						MBPartner partner = new MBPartner(getCtx(), cBPartnerIDAux, null);
						ordenPagoSocio = new MZGeneraOrdenPagoSocio(getCtx(), 0, get_TrxName());
						ordenPagoSocio.setZ_GeneraOrdenPago_ID(this.get_ID());
						ordenPagoSocio.setC_BPartner_ID(cBPartnerIDAux);
						ordenPagoSocio.setTaxID(partner.getTaxID());
						ordenPagoSocio.setC_Currency_ID(this.getC_Currency_ID());
						ordenPagoSocio.setTotalAmt(Env.ZERO);
						ordenPagoSocio.saveEx();
					}
				}

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
				ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());
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

				ordenPagoLin.setResguardoEmitido(MZResguardoSocio.invoiceTieneResguardo(getCtx(), ordenPagoLin.getC_Invoice_ID(), get_TrxName()));

				ordenPagoLin.saveEx();
			}

			// Ajusto fecha de vencimiento de medios de pago de lineas para socio de negocio anterior
			if (cBPartnerIDAux > 0){
				// Si usuario indica que agrupa por mayor vencimiento
				if (this.isGroupLastDue()){
					this.groupForLastDueDate(ordenPagoSocio.get_ID());
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
	 * Actualiza vencimiento de medios de pago de lineas para un determinado socio de negocio.
	 * Actualiza a ultima fecha de vencimiento de documentos.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @param zGeneraOrdenPagoSocioID
	 */
	private void groupForLastDueDate(int zGeneraOrdenPagoSocioID) {

		try {

			Timestamp today = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			String sql = " select max(duedatedoc) "
						+ " from z_generaordenpagolin "
						+ " where z_generaordenpago_id =" + this.get_ID()
						+ " and z_generaordenpagosocio_id =" + zGeneraOrdenPagoSocioID
						+ " and c_invoice_id > 0 ";

			Timestamp lastDueDate = DB.getSQLValueTSEx(get_TrxName(), sql);

			if (lastDueDate != null){

				if (lastDueDate.compareTo(today) <= 0){
					lastDueDate = today;
				}

				String action = " update z_generaordenpagolin "
						+ " set duedatemediopago ='" + lastDueDate + "' "
						+ " where z_generaordenpago_id =" + this.get_ID()
						+ " and z_generaordenpagosocio_id =" + zGeneraOrdenPagoSocioID;

				DB.executeUpdateEx(action, get_TrxName());
			}

		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	/***
	 * Obtiene filtro de socios de negocio a aplicar para obtener documentos.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private String getFiltroSocios() {

		String whereClause = null;

		try{

			String incluirSocios = " IN ";

			if (this.getTipoFiltroSocioPago().equalsIgnoreCase(X_Z_GeneraOrdenPago.TIPOFILTROSOCIOPAGO_EXCLUIRSOCIOSDENEGOCIOFILTRO)){
				incluirSocios = " NOT IN ";
			}

			// Verifico si tengo socios de negocio para filtrar
			String sql = " select count(*) from z_genopagofiltrosocio where z_generaordenpago_id =" + this.get_ID();
			int contador = DB.getSQLValue(get_TrxName(), sql);

			// Si no tengo, no hago nada
			if (contador <= 0){
				return whereClause;
			}

			// Tengo socios de negocio para filtrar
			whereClause = " hdr.c_bpartner_id " + incluirSocios +
					" (select c_bpartner_id from z_genopagofiltrosocio where z_generaordenpago_id =" + this.get_ID() + ") ";
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return whereClause;
	}


	/***
	 * Genera ordenes de pago en borrador con información de este proceso.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	public String generateOrdenes() {

		String message = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Medio de pago por defecto por ahora CHEQUE
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

			// Valida datos para la generación
			message =  this.validateGenerateOrdenes();
			if (message != null){
				return message;
			}

			// Elimino datos de generacion anterior, si existen.
			this.deleteOrdenes();

			// Actualiza datos necesarios para la generación
			this.refreshInfoLineas();

			// Documento para ordenes de pago
			MDocType[] docs = MDocType.getOfDocBaseType(getCtx(),"OOP");
			MDocType docOP = docs[0];

			// Moneda de las ordenes de pago = moneda de la cuenta bancaria seleccionada en la generacion
			int cCurrencyBankAccount = ((MBankAccount) this.getC_BankAccount()).getC_Currency_ID();

			// Obtengo y recorro socios de negocio considerados en este proceso
			List<MZGeneraOrdenPagoSocio> ordenPagoSocios = this.getSocios();
			for (MZGeneraOrdenPagoSocio ordenPagoSocio: ordenPagoSocios){

				// Creo nuevo cabezal de orden de pago
				MZOrdenPago ordenPago = new MZOrdenPago(getCtx(), 0, get_TrxName());
				ordenPago.setZ_GeneraOrdenPago_ID(this.get_ID());
				ordenPago.setC_BPartner_ID(ordenPagoSocio.getC_BPartner_ID());
				ordenPago.setC_Currency_ID(cCurrencyBankAccount);
				ordenPago.setC_DocType_ID(docOP.get_ID());
				ordenPago.setDateDoc(this.getDateDoc());
				ordenPago.setTotalAmt(Env.ZERO);
				ordenPago.setDescription("Número de Generación : " + this.getDocumentNo());
				ordenPago.saveEx();

				// Obtengo y recorro lineas con documentos a considerar para este socio de negocio en la generacion de la orden de pago
				BigDecimal totalPago = Env.ZERO;
				Timestamp maxDueDate = fechaHoy;
				List<MZGeneraOrdenPagoLin> generaLins = ordenPagoSocio.getSelectedDocuments();
				for (MZGeneraOrdenPagoLin generaLin: generaLins){

					// Si el documento de esta linea se corresponde con una invoice, valido que tenga resguardo.
					// En caso de no tenerlo, verifico si aplican retenciones para esta invoice o no, en cuyo caso aviso y salgo.
					if (generaLin.getC_Invoice_ID() > 0){
						// Refreso dato para esta invoice con respecto a si tiene o no un resguardo
						generaLin.setResguardoEmitido(MZResguardoSocio.invoiceTieneResguardo(getCtx(), generaLin.getC_Invoice_ID(), get_TrxName()));
						generaLin.saveEx();
						if (!generaLin.isResguardoEmitido()){
							// No tiene resguardo emitido, verifico si para esta invoice se deben aplicar retenciones
							boolean aplicaReteneciones = MZRetencionSocio.invoiceAplicanRetenciones(getCtx(), generaLin.getC_Invoice_ID(), get_TrxName());
							if (aplicaReteneciones) {
								MBPartner partner = (MBPartner) ordenPago.getC_BPartner();
								return " No es posible generar orden de pago para el Socio de Negocio : " + partner.getName() + "\n" +
										" El mismo tiene un comprobante que requiere la emisión de resguardo : " + generaLin.getDocumentNoRef();
							}
						}
					}

					// Genero linea de orden de pago para este documento
					MZOrdenPagoLin ordenPagoLin = new MZOrdenPagoLin(getCtx(), 0, get_TrxName());
					ordenPagoLin.setZ_OrdenPago_ID(ordenPago.get_ID());
					ordenPagoLin.setAmtAllocation(generaLin.getAmtAllocation());
					ordenPagoLin.setAmtDocument(generaLin.getAmtDocument());
					ordenPagoLin.setAmtOpen(generaLin.getAmtOpen());
					ordenPagoLin.setC_Currency_ID(generaLin.getC_Currency_ID());
					ordenPagoLin.setC_DocType_ID(generaLin.getC_DocType_ID());
					ordenPagoLin.setDateDoc(generaLin.getDateDoc());
					ordenPagoLin.setDueDateDoc(generaLin.getDueDateDoc());
					ordenPagoLin.setDocumentNoRef(generaLin.getDocumentNoRef());
					if (generaLin.getC_Invoice_ID() > 0){
						ordenPagoLin.setC_Invoice_ID(generaLin.getC_Invoice_ID());
					}
					if (generaLin.getZ_ResguardoSocio_ID() > 0){
						ordenPagoLin.setZ_ResguardoSocio_ID(generaLin.getZ_ResguardoSocio_ID());
					}
					ordenPagoLin.saveEx();

					totalPago = totalPago.add(ordenPagoLin.getAmtAllocation());

					if (generaLin.getDueDateMedioPago() != null){
						if (generaLin.getDueDateMedioPago().after(fechaHoy)){
							maxDueDate = generaLin.getDueDateMedioPago();
						}
					}
				}

				// Actualizo monto total de orden de pago
				ordenPago.setTotalAmt(totalPago);
				ordenPago.saveEx();

				// Creo linea para medio de pago a considerarse al completar la orden de pago
				MZOrdenPagoMedio ordenPagoMedio = new MZOrdenPagoMedio(getCtx(), 0, get_TrxName());
				ordenPagoMedio.setZ_GeneraOrdenPago_ID(this.get_ID());
				ordenPagoMedio.setZ_OrdenPago_ID(ordenPago.get_ID());
				ordenPagoMedio.setC_BankAccount_ID(this.getC_BankAccount_ID());
				ordenPagoMedio.setDueDate(maxDueDate);
				ordenPagoMedio.setC_Currency_ID(ordenPago.getC_Currency_ID());
				ordenPagoMedio.setTotalAmt(ordenPago.getTotalAmt());
				ordenPagoMedio.setZ_MedioPago_ID(medioPago.get_ID());
				ordenPagoMedio.setZ_MedioPagoFolio_ID(this.getZ_MedioPagoFolio_ID());
				ordenPagoMedio.saveEx();

			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Obtiene y retorna socios a considerar en este proceso.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private List<MZGeneraOrdenPagoSocio> getSocios() {

		String whereClause = X_Z_GeneraOrdenPagoSocio.COLUMNNAME_Z_GeneraOrdenPago_ID + " =" + this.get_ID();

		List<MZGeneraOrdenPagoSocio> lines = new Query(getCtx(), I_Z_GeneraOrdenPagoSocio.Table_Name, whereClause, get_TrxName()).list();

		return lines;

	}


	/***
	 * Actualiza información de lineas de este proceso, antes de generar las ordenes de pago en borrador.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 */
	private void refreshInfoLineas() {

		String action = "";

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp (System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Medio de pago por defecto por ahora CHEQUE
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

			// Actualizo informacion de medios de pago en lineas de documentos a procesar
			action = " update z_generaordenpagolin set z_mediopago_id =" + medioPago.get_ID()
					+ " where z_generaordenpago_id =" + this.get_ID()
					+ " and isselected ='Y' ";

			DB.executeUpdateEx(action, get_TrxName());

			// Me aseguro que no queden fechas de vencimiento de medios de pago menores a hoy en lineas de documentos a procesar
			action = " update z_generaordenpagolin set duedatemediopago ='" + fechaHoy + "' "
					+ " where z_generaordenpago_id =" + this.get_ID()
					+ " and isselected ='Y' "
					+ " and c_invoice_id > 0 "
					+ " and duedatemediopago <'" + fechaHoy + "'";
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Elimina ordenes en borrador generadas previamente en este proceso.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 */
	private void deleteOrdenes() {

		String action = "";

		try{
			action = " delete from z_ordenpago cascade where z_generaordenpago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Validaciones previas a la generación de ordenes de pago en borrador.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private String validateGenerateOrdenes() {

		String message = null;

		try{

			if (this.getC_BankAccount_ID() <= 0){
				message = "Debe indicar Cuenta Bancaria para Moneda 1 seleccionada.";
				return message;
			}

			if (this.getZ_MedioPagoFolio_ID() <= 0){
				message = "Debe indicar Libreta para Cheques Diferidos.";
				return message;
			}

			if (this.getZ_MedioPagoFolio_2_ID() <= 0){
				message = "Debe indicar Libreta para Cheques Día";
				return message;
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;

	}

	public String imprimirCheques(int zMedioPagoFolioID) {

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select opm.z_mediopagoitem_id " +
					" from z_ordenpagomedio opm " +
					" inner join z_mediopagoitem mpi on opm.z_mediopagoitem_id = mpi.z_mediopagoitem_id" +
					" where opm.z_ordenpago_id in" +
					" (select z_ordenpago_id from z_ordenpago where z_generaordenpago_id =" + this.get_ID() + ") " +
					" and mpi.z_mediopagofolio_id =" + zMedioPagoFolioID +
					" and mpi.isprinted='N' " +
					" order by mpi.nromediopago";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(getCtx(), rs.getInt("z_mediopagoitem_id"), get_TrxName());
				medioPagoItem.imprimir();
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
