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
import org.compiere.impexp.MImpFormat;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.xpande.core.utils.DateUtils;

/** Generated Model for Z_LoadExtBanco
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZLoadExtBanco extends X_Z_LoadExtBanco implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20191115L;

    /** Standard Constructor */
    public MZLoadExtBanco (Properties ctx, int Z_LoadExtBanco_ID, String trxName)
    {
      super (ctx, Z_LoadExtBanco_ID, trxName);
    }

    /** Load Constructor */
    public MZLoadExtBanco (Properties ctx, ResultSet rs, String trxName)
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
		return 0;
	}	//	getC_Currency_ID

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("MZLoadExtBanco[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Obtiene y retorna lineas de este documento.
	 * Xpande. Created by Gabriel Vila on 11/20/19.
	 * @return
	 */
	public List<MZLoadExtBancoLin> getLines(){

		String whereClause = X_Z_LoadExtBancoLin.COLUMNNAME_Z_LoadExtBanco_ID + " =" + this.get_ID();

		List<MZLoadExtBancoLin> lines = new Query(getCtx(), I_Z_LoadExtBancoLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Metodo que ejecuta el proceso de interface desde archivo para carga de extractos bancarios.
	 * Xpande. Created by Gabriel Vila on 11/20/19.
	 */
	public String executeInterface(){

		try{

			// Instancio modelo de banco asociado a la cuenta bancaria seleccionada
			MBank bank = (MBank) ((MBankAccount) this.getC_BankAccount()).getC_Bank();
			if (bank.get_ValueAsInt("AD_ImpFormat_ID") <= 0){
				return "El banco : " + bank.getName() + ", no tiene configurado un Formato para carga de Extractos Bancarios";
			}

			// Formato de importación de archivo de interface para carga de extractos bancarios
			MImpFormat mimp = new MImpFormat(getCtx(), bank.get_ValueAsInt("AD_ImpFormat_ID"), null);
			ImpFormat formatoImpArchivo = ImpFormat.load(mimp.getName().trim());

			// Elimino información anterior.
			this.deleteFileData();

			// Lee lineas de archivo
			this.getDataFromFile(formatoImpArchivo);

			// Valida lineas de archivo y trae información asociada.
			this.setDataFromFile(mimp.getName());

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return null;
	}

	/***
	 * Elimina información leída desde archivo.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 */
	private void deleteFileData() {

		String action = "";

		try{
			action = " delete from " + I_Z_LoadExtBancoLin.Table_Name +
					" where " + X_Z_LoadExtBancoLin.COLUMNNAME_Z_LoadExtBanco_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/***
	 * Lee archivo con extractos bancarios y procesa según de que banco se trate.
	 * Xpande. Created by Gabriel Vila on 11/20/19.
	 * @param formatoImpArchivo
	 */
	private String getDataFromFile(ImpFormat formatoImpArchivo) {

		FileReader fReader = null;
		BufferedReader bReader = null;

		String action = "";
		String lineaArchivo = null;

		try {

			// Abro archivo
			File archivo = new File(this.getFileName());
			fReader = new FileReader(archivo);
			bReader = new BufferedReader(fReader);

			int contLineas = 0;
			int lineaID = 0;

			// Leo lineas del archivo
			lineaArchivo = bReader.readLine();

			while (lineaArchivo != null) {

				lineaArchivo = lineaArchivo.replace(",\"", ";\"");
				lineaArchivo = lineaArchivo.replace("'", "");
				lineaArchivo = lineaArchivo.replace("\"", "");
				//lineaArchivo = lineaArchivo.replace(",", "");

				contLineas++;

				lineaID = formatoImpArchivo.updateDB(lineaArchivo, getCtx(), get_TrxName());

				if (lineaID <= 0){

					MZLoadExtBancoLin extBancoLin = new MZLoadExtBancoLin(getCtx(), 0, get_TrxName());
					extBancoLin.setZ_LoadExtBanco_ID(this.get_ID());
					extBancoLin.setLineNumber(contLineas);
					extBancoLin.setFileLineText(lineaArchivo);
					extBancoLin.setIsConfirmed(false);
					extBancoLin.setErrorMsg("Formato de Linea Incorrecto.");
					extBancoLin.saveEx();
				}
				else{
					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_LoadExtBancoLin.Table_Name +
							" set " + X_Z_LoadExtBancoLin.COLUMNNAME_Z_LoadExtBanco_ID + " = " + this.get_ID() + ", " +
							" LineNumber =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_LoadExtBancoLin.COLUMNNAME_Z_LoadExtBancoLin_ID + " = " + lineaID;
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

		return null;
	}

	/***
	 * Valida lineas leídas desde archivo y carga información asociada.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 */
	private void setDataFromFile(String nombreFormatoImpArchivo) {

		try{

			int contadorOK = 0;
			int contadorError = 0;

			List<MZLoadExtBancoLin> extBancoLinList = this.getLines();
			for (MZLoadExtBancoLin extBancoLin : extBancoLinList){

				if (extBancoLin.getErrorMsg() != null){
					contadorError++;
					continue;
				}

				extBancoLin.setIsConfirmed(true);

				// Valido fecha del movimiento
				if ((extBancoLin.getFechaCadena() == null) || (extBancoLin.getFechaCadena().trim().equalsIgnoreCase(""))){
					extBancoLin.setIsConfirmed(false);
					extBancoLin.setErrorMsg("No se pudo obtener Fecha de Movimiento");
				}
				else{

					Timestamp fecDoc = null;

					// El formato de la fecha depende del banco.
					if (nombreFormatoImpArchivo.toUpperCase().contains("BBVA")){
						// Ej: 2019-12-31
						fecDoc = DateUtils.convertStringToTimestamp_yyyyMMdd(extBancoLin.getFechaCadena(), "-");
					}
					if (nombreFormatoImpArchivo.toUpperCase().contains("ITAU")){
						// Ej: 01DIC19
						String dia = extBancoLin.getFechaCadena().substring(0,1);

						String mes = extBancoLin.getFechaCadena().substring(2,4);
						if (mes.equalsIgnoreCase("ENE")) mes = "01";
						else if (mes.equalsIgnoreCase("FEB")) mes = "02";
						else if (mes.equalsIgnoreCase("MAR")) mes = "03";
						else if (mes.equalsIgnoreCase("ABR")) mes = "04";
						else if (mes.equalsIgnoreCase("MAY")) mes = "05";
						else if (mes.equalsIgnoreCase("JUN")) mes = "06";
						else if (mes.equalsIgnoreCase("JUL")) mes = "07";
						else if (mes.equalsIgnoreCase("AGO")) mes = "08";
						else if (mes.equalsIgnoreCase("SEP")) mes = "09";
						else if (mes.equalsIgnoreCase("OCT")) mes = "10";
						else if (mes.equalsIgnoreCase("NOV")) mes = "11";
						else if (mes.equalsIgnoreCase("DIC")) mes = "12";

						String anio = "20" + extBancoLin.getFechaCadena().substring(5,6);
						String fechaAux = anio + "-" + mes + "-" + dia;

						fecDoc = DateUtils.convertStringToTimestamp_yyyyMMdd(fechaAux, "-");
					}
					if (nombreFormatoImpArchivo.toUpperCase().contains("HSBC")){
						fecDoc = DateUtils.convertStringToTimestamp_ddMMyyyy(extBancoLin.getFechaCadena(), "");
					}
					if (nombreFormatoImpArchivo.toUpperCase().contains("SANTANDER")){
						fecDoc = DateUtils.convertStringToTimestamp_ddMMyyyy(extBancoLin.getFechaCadena(), "/");
					}
;
					if (fecDoc == null){
						extBancoLin.setIsConfirmed(false);
						extBancoLin.setErrorMsg("Formato de Fecha de Movimiento inválido : " + extBancoLin.getFechaCadena());
					}
					extBancoLin.setDateTrx(fecDoc);
				}

				if (extBancoLin.isConfirmed()){
					contadorOK++;
				}
				else{
					contadorError++;
				}

				extBancoLin.saveEx();
			}

			this.setQty(contadorOK);
			this.setQtyReject(contadorError);
			this.saveEx();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


}