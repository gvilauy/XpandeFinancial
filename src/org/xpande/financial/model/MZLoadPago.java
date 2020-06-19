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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.impexp.ImpFormat;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.comercial.utils.ComercialUtils;
import org.xpande.core.utils.DateUtils;

/** Generated Model for Z_LoadPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZLoadPago extends X_Z_LoadPago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20200210L;

    /** Standard Constructor */
    public MZLoadPago (Properties ctx, int Z_LoadPago_ID, String trxName)
    {
      super (ctx, Z_LoadPago_ID, trxName);
    }

    /** Load Constructor */
    public MZLoadPago (Properties ctx, ResultSet rs, String trxName)
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

		// Obtengo lineas confirmadas cargadas desde el archivo de interface
		List<MZLoadPagoFile> loadPagoFileList = this.getLinesConfirmed();

		// Si no tengo lineas aviso y salgo.
		if (loadPagoFileList.size() <= 0){
			m_processMsg = "El documento no tiene lineas para procesar";
			return DocAction.STATUS_Invalid;
		}

		// Recorro lineas leídas desde archivo de interface
		for (MZLoadPagoFile loadPagoFile: loadPagoFileList){

			// Genero recibo a partir de este array de ordenes de pago
			MZPago pago = new MZPago(getCtx(), 0, get_TrxName());
			pago.setAD_Org_ID(loadPagoFile.getAD_OrgTrx_ID());
			pago.setIsSOTrx(this.isSOTrx());
			pago.setC_BPartner_ID(loadPagoFile.getC_BPartner_ID());
			pago.setC_Currency_ID(loadPagoFile.getC_Currency_ID());
			pago.setDateDoc(loadPagoFile.getDateTrx());
			pago.setDateAcct(loadPagoFile.getDateTrx());
			pago.setTieneRecibo(false);
			pago.setC_DocType_ID(this.getC_DocTypeTarget_ID());
			pago.setPayAmt(loadPagoFile.getTotalAmt());
			pago.setTotalMediosPago(loadPagoFile.getTotalAmt());
			pago.setDescription("Generada Automáticamente desde Generación de Recibos Cta.Cte.");
			pago.setAnticipo(true);
			pago.setZ_LoadPago_ID(this.get_ID());
			pago.saveEx();

			// Cargo medio de pago
			MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
			pagoMedioPago.setAD_Org_ID(pago.getAD_Org_ID());
			pagoMedioPago.setZ_Pago_ID(pago.get_ID());
			pagoMedioPago.setZ_MedioPago_ID(this.getZ_MedioPago_ID());
			pagoMedioPago.setDateEmitted(pago.getDateDoc());
			pagoMedioPago.setDueDate(pago.getDateDoc());
			pagoMedioPago.setTotalAmtMT(loadPagoFile.getTotalAmt());
			pagoMedioPago.setTotalAmt(loadPagoFile.getTotalAmt());
			pagoMedioPago.setC_Currency_ID(loadPagoFile.getC_Currency_ID());
			pagoMedioPago.setEmisionManual(false);
			pagoMedioPago.setMultiplyRate(Env.ONE);
			pagoMedioPago.setTieneCaja(false);
			pagoMedioPago.setTieneCtaBco(false);
			pagoMedioPago.setTieneFecEmi(true);
			pagoMedioPago.setTieneFecVenc(false);
			pagoMedioPago.setTieneFolio(false);
			pagoMedioPago.saveEx();

			// Instancio de nuevo el modelo de pago porque se actulizaron datos del mismo via DB directo y no quedan en el modelo
			int pagoIDAux = pago.get_ID();
			pago = new MZPago(getCtx(), pagoIDAux, get_TrxName());
			//pago.setPayAmt(pago.getTotalMediosPago());

			// Completo recibo
			if (!pago.processIt(DocAction.ACTION_Complete)){
				String message = pago.getProcessMsg();
				if (message == null){
					message = "Problemas al completar el Recibo";
				}
				m_processMsg = message;
				return DocAction.STATUS_Invalid;
			}
			pago.saveEx();
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
		String action = "";

		log.info("reActivateIt - " + toString());

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Obtengo y recorro lista de recibos generados
		List<MZPago> pagoList = MZPago.getByLoadPago(getCtx(), this.get_ID(), get_TrxName());
		for (MZPago pago: pagoList){

			// Reactivo este recibo
			if (!pago.reActivateIt()){
				m_processMsg = "No se pudo reactivar el recibo número : " + pago.getDocumentNo();
				if (pago.getProcessMsg() != null){
					m_processMsg += ". " + pago.getProcessMsg();
				}
				return false;
			}
			pago.saveEx();

			// ELimino el recibo
			pago.deleteEx(true);
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		this.setProcessed(false);
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
		return 0;
	}	//	getC_Currency_ID

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("MZLoadPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Obtiene y retorna lineas confirmadas de este documento.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 * @return
	 */
	public List<MZLoadPagoFile> getLinesConfirmed(){

		String whereClause = X_Z_LoadPagoFile.COLUMNNAME_Z_LoadPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_LoadPagoFile.COLUMNNAME_IsConfirmed + " ='Y' ";

		List<MZLoadPagoFile> lines = new Query(getCtx(), I_Z_LoadPagoFile.Table_Name, whereClause, get_TrxName()).setOrderBy(" AD_OrgTrx_ID, TaxID ").list();

		return lines;
	}

	/***
	 * Metodo que ejecuta el proceso de interface desde archivo para carga de pagos / cobros.
	 * Xpande. Created by Gabriel Vila on 2/10/20.
	 */
	public void executeInterface(){

		try{
			// Elimino información anterior.
			this.deleteFileData();

			// Lee lineas de archivo
			this.getDataFromFile();

			// Valida lineas de archivo y trae información asociada.
			this.setDataFromFile();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


	/***
	 * Elimina información leída desde archivo.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 */
	private void deleteFileData() {

		String action = "";

		try{
			action = " delete from " + I_Z_LoadPagoFile.Table_Name +
					" where " + X_Z_LoadPagoFile.COLUMNNAME_Z_LoadPago_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Proceso que lee archivo de interface.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 */
	public void getDataFromFile() {

		FileReader fReader = null;
		BufferedReader bReader = null;

		String lineaArchivo = null;
		String mensaje = "";
		String action = "";

		try{

			// Formato de importación de archivo de interface para carga de medios de pago
			ImpFormat formatoImpArchivo = ImpFormat.load("Financial_CargaPagoCobro");

			// Abro archivo
			File archivo = new File(this.getFileName());
			fReader = new FileReader(archivo);
			bReader = new BufferedReader(fReader);

			int contLineas = 0;
			int lineaID = 0;

			// Leo lineas del archivo
			lineaArchivo = bReader.readLine();

			while (lineaArchivo != null) {

				lineaArchivo = lineaArchivo.replace("'", "");
				//lineaArchivo = lineaArchivo.replace(",", "");
				contLineas++;

				lineaID = formatoImpArchivo.updateDB(lineaArchivo, getCtx(), get_TrxName());

				if (lineaID <= 0){
					MZLoadPagoFile loadPagoFile = new MZLoadPagoFile(getCtx(), 0, get_TrxName());
					loadPagoFile.setZ_LoadPago_ID(this.get_ID());
					loadPagoFile.setLineNumber(contLineas);
					loadPagoFile.setFileLineText(lineaArchivo);
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("Formato de Linea Incorrecto.");
					loadPagoFile.saveEx();
				}
				else{
					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_LoadPagoFile.Table_Name +
							" set " + X_Z_LoadPagoFile.COLUMNNAME_Z_LoadPago_ID + " = " + this.get_ID() + ", " +
							" LineNumber =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_LoadPagoFile.COLUMNNAME_Z_LoadPagoFile_ID + " = " + lineaID;
					DB.executeUpdateEx(action, get_TrxName());
				}

				lineaArchivo = bReader.readLine();
			}

			this.setQtyCount(contLineas);
			this.saveEx();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			if (bReader != null){
				try{
					bReader.close();
					if (fReader != null){
						fReader.close();
					}
				}
				catch (Exception e){
					log.log(Level.SEVERE, e.getMessage());
				}
			}
		}
	}

	/***
	 * Valida lineas leídas desde archivo y carga información asociada.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 */
	private void setDataFromFile() {

		try{

			int contadorOK = 0;
			int contadorError = 0;

			List<MZLoadPagoFile> loadPagoFileList = this.getLines();
			for (MZLoadPagoFile loadPagoFile : loadPagoFileList){

				if (loadPagoFile.getErrorMsg() != null){
					contadorError++;
					continue;
				}

				loadPagoFile.setIsConfirmed(true);

				int adOrgIDAux = 0;

				if (loadPagoFile.getAD_OrgTrx_ID() <= 0){
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("No existe Organización con ese Número o la misma debe ser distinta de CERO.");
				}
				else{
					MOrg orgTrx = new MOrg(getCtx(), loadPagoFile.getAD_OrgTrx_ID(), null);
					if ((orgTrx == null) || (orgTrx.get_ID() <= 0)){
						loadPagoFile.setIsConfirmed(false);
						loadPagoFile.setErrorMsg("No existe Organización con ese Número");
					}
					adOrgIDAux = orgTrx.get_ID();
				}

				if ((loadPagoFile.getTaxID() == null) || (loadPagoFile.getTaxID().trim().equalsIgnoreCase(""))){
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("Debe indicar Número de Identificación del Socio de Negocio, en la Linea del Archivo");
				}
				else{
					MBPartner partner = ComercialUtils.getPartnerByTaxID(getCtx(), loadPagoFile.getTaxID(), null);
					if ((partner == null) || (partner.get_ID() <= 0)){
						loadPagoFile.setIsConfirmed(false);
						loadPagoFile.setErrorMsg("No existe Socio de Negocio definido en el sistema con ese Número de Identificación : " + loadPagoFile.getTaxID());
					}
					else{
						loadPagoFile.setC_BPartner_ID(partner.get_ID());
					}
				}

				if ((loadPagoFile.getFechaCadena() == null) || (loadPagoFile.getFechaCadena().trim().equalsIgnoreCase(""))){
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("Debe indicar Fecha de Emisión");
				}
				else{
					Timestamp fecDoc = DateUtils.convertStringToTimestamp_ddMMyyyy(loadPagoFile.getFechaCadena());
					if (fecDoc == null){
						loadPagoFile.setIsConfirmed(false);
						loadPagoFile.setErrorMsg("Formato de Fecha de Emisión inválido : " + loadPagoFile.getFechaCadena());
					}
					loadPagoFile.setDateTrx(fecDoc);
				}

				if ((loadPagoFile.getTotalAmt() == null) || (loadPagoFile.getTotalAmt().compareTo(Env.ZERO) <= 0)){
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("Debe indicar Importe");
				}

				if (loadPagoFile.getC_Currency_ID() <= 0){
					loadPagoFile.setIsConfirmed(false);
					loadPagoFile.setErrorMsg("Debe indicar ID de Moneda");
				}
				else{
					MCurrency currency = new MCurrency(getCtx(), loadPagoFile.getC_Currency_ID(), null);
					if ((currency == null) || (currency.get_ID() <= 0)){
						loadPagoFile.setIsConfirmed(false);
						loadPagoFile.setErrorMsg("No existe Moneda definida en el sistema con ese número : " + loadPagoFile.getC_Currency_ID());
					}
				}

				if (loadPagoFile.isConfirmed()){
					contadorOK++;
				}
				else{
					contadorError++;
				}

				loadPagoFile.saveEx();
			}

			this.setQty(contadorOK);
			this.setQtyReject(contadorError);
			this.saveEx();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene y retorna lineas de este documento.
	 * Xpande. Created by Gabriel Vila on 2/10/20.
	 * @return
	 */
	public List<MZLoadPagoFile> getLines(){

		String whereClause = X_Z_LoadPagoFile.COLUMNNAME_Z_LoadPago_ID + " =" + this.get_ID();

		List<MZLoadPagoFile> lines = new Query(getCtx(), I_Z_LoadPagoFile.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

}