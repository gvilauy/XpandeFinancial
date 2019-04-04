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

/** Generated Model for Z_LoadMedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZLoadMedioPago extends X_Z_LoadMedioPago implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20190402L;

    /** Standard Constructor */
    public MZLoadMedioPago (Properties ctx, int Z_LoadMedioPago_ID, String trxName)
    {
      super (ctx, Z_LoadMedioPago_ID, trxName);
    }

    /** Load Constructor */
    public MZLoadMedioPago (Properties ctx, ResultSet rs, String trxName)
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


		// Obtengo lineas confirmadas cargadas desde el archivo de interface
		List<MZLoadMedioPagoFile> loadMedioPagoFileList = this.getLinesConfirmed();

		// Obtengo lineas cargadas manualmente
		List<MZLoadMedioPagoMan> loadMedioPagoManList = this.getLinesManual();

		// Si no tengo lineas aviso y salgo.
		if (loadMedioPagoFileList.size() <= 0){
			if (loadMedioPagoManList.size() <= 0){
				m_processMsg = "El documento no tiene lineas para procesar";
				return DocAction.STATUS_Invalid;
			}
		}

		// Por ahora asumo solo carga de medios de pago del tipo CHEQUE
		MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

		int cBankAccountIDAux = 0;
		int nroMedioPagoDesde = 0;
		int nroMedioPagoHasta = 0;

		MZMedioPagoFolio medioPagoFolio = null;

		// Recorro lineas leídas desde archivo de interface que vienen ordenadas por cuenta bancaria (necesario para la creación de folios de medios de pago).
		for (MZLoadMedioPagoFile loadMedioPagoFile: loadMedioPagoFileList){

			// Corte por cuenta bancaria
			if (loadMedioPagoFile.getC_BankAccount_ID() != cBankAccountIDAux){

				MBankAccount bankAccount = (MBankAccount) loadMedioPagoFile.getC_BankAccount();

				// Creo Folio de medios de pago para esta cuenta bancaria
				medioPagoFolio = new MZMedioPagoFolio(getCtx(), 0, get_TrxName());
				medioPagoFolio.setC_BankAccount_ID(loadMedioPagoFile.getC_BankAccount_ID());
				medioPagoFolio.setC_Currency_ID(bankAccount.getC_Currency_ID());
				medioPagoFolio.setDescription("Generada Automáticamente en Carga de Medios de Pago Nro.: " + this.getDocumentNo());
				medioPagoFolio.setDisponible(false);
				medioPagoFolio.setDocumentSerie("CM");
				medioPagoFolio.setEmisionManual(true);
				medioPagoFolio.setIsExecuted(true);
				medioPagoFolio.setName("CARGA_" + this.getDocumentNo());
				medioPagoFolio.setAD_Org_ID(bankAccount.getAD_Org_ID());
				medioPagoFolio.setNumeroDesde(0);
				medioPagoFolio.setNumeroHasta(1);
				medioPagoFolio.setTIpoCheque(X_Z_MedioPagoFolio.TIPOCHEQUE_DIFERIDO);
				medioPagoFolio.setZ_MedioPago_ID(medioPago.get_ID());
				medioPagoFolio.saveEx();

				cBankAccountIDAux = loadMedioPagoFile.getC_BankAccount_ID();
			}

			// Creo item de medio de pago
			MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
			medioPagoItem.setZ_MedioPago_ID(medioPago.get_ID());
			medioPagoItem.setZ_MedioPagoFolio_ID(medioPagoFolio.get_ID());
			medioPagoItem.setEntregado(false);
			medioPagoItem.setAnulado(false);
			medioPagoItem.setC_BankAccount_ID(medioPagoFolio.getC_BankAccount_ID());
			medioPagoItem.setC_BPartner_ID(loadMedioPagoFile.getC_BPartner_ID());
			medioPagoItem.setC_Currency_ID(medioPagoFolio.getC_Currency_ID());
			medioPagoItem.setConciliado(false);
			medioPagoItem.setDateEmitted(loadMedioPagoFile.getDateEmitted());
			medioPagoItem.setDepositado(false);
			medioPagoItem.setDocumentSerie(medioPagoFolio.getDocumentSerie());
			medioPagoItem.setDueDate(loadMedioPagoFile.getDueDate());
			medioPagoItem.setEmitido(false);
			medioPagoItem.setIsOwn(true);
			medioPagoItem.setIsPrinted(true);
			medioPagoItem.setIsReceipt(false);
			medioPagoItem.setNroMedioPago(loadMedioPagoFile.getNroMedioPago());
			medioPagoItem.setTotalAmt(loadMedioPagoFile.getTotalAmt());
			medioPagoItem.saveEx();

			// Creo documento de Emision de medio de pago y lo completo.
			MZEmisionMedioPago emisionMedioPago = new MZEmisionMedioPago(getCtx(), 0, get_TrxName());
			emisionMedioPago.setZ_MedioPago_ID(medioPago.get_ID());
			emisionMedioPago.setAD_Org_ID(loadMedioPagoFile.getAD_Org_ID());
			emisionMedioPago.setZ_MedioPagoFolio_ID(medioPagoFolio.get_ID());
			emisionMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
			emisionMedioPago.setC_Currency_ID(medioPagoItem.getC_Currency_ID());
			emisionMedioPago.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
			emisionMedioPago.setC_BPartner_ID(medioPagoItem.getC_BPartner_ID());
			emisionMedioPago.setDateDoc(this.getDateDoc());
			emisionMedioPago.setDateEmitted(medioPagoItem.getDateEmitted());
			emisionMedioPago.setDueDate(medioPagoItem.getDueDate());
			emisionMedioPago.setTotalAmt(medioPagoItem.getTotalAmt());
			emisionMedioPago.setDescription(loadMedioPagoFile.getDescription());
			emisionMedioPago.saveEx();

			// Completo documento de emisión de medio de pago
			if (!emisionMedioPago.processIt(DocAction.ACTION_Complete)){
				m_processMsg = emisionMedioPago.getProcessMsg();
				return DocAction.STATUS_Invalid;
			}
			emisionMedioPago.saveEx();

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
      StringBuffer sb = new StringBuffer ("MZLoadMedioPago[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	/***
	 * Metodo que ejecuta el proceso de interface desde archivo para carga de medios de pago.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
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
			action = " delete from " + I_Z_LoadMedioPagoFile.Table_Name +
					" where " + X_Z_LoadMedioPagoFile.COLUMNNAME_Z_LoadMedioPago_ID + " =" + this.get_ID();
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
			ImpFormat formatoImpArchivo = ImpFormat.load("Financial_CargaMediosPago");

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
					MZLoadMedioPagoFile loadMedioPagoFile = new MZLoadMedioPagoFile(getCtx(), 0, get_TrxName());
					loadMedioPagoFile.setZ_LoadMedioPago_ID(this.get_ID());
					loadMedioPagoFile.setLineNumber(contLineas);
					loadMedioPagoFile.setFileLineText(lineaArchivo);
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Formato de Linea Incorrecto.");
					loadMedioPagoFile.saveEx();
					/*
					mensaje = "Error al procesar linea " + contLineas + " : " + lineaArchivo;
					throw new AdempiereException(mensaje);
					*/
				}
				else{
					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_LoadMedioPagoFile.Table_Name +
							" set " + X_Z_LoadMedioPagoFile.COLUMNNAME_Z_LoadMedioPago_ID + " = " + this.get_ID() + ", " +
							" LineNumber =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_LoadMedioPagoFile.COLUMNNAME_Z_LoadMedioPagoFile_ID + " = " + lineaID;
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

			List<MZLoadMedioPagoFile> loadMedioPagoFileList = this.getLines();
			for (MZLoadMedioPagoFile loadMedioPagoFile : loadMedioPagoFileList){

				if (loadMedioPagoFile.getErrorMsg() != null){
					contadorError++;
					continue;
				}

				loadMedioPagoFile.setIsConfirmed(true);

				if (loadMedioPagoFile.getAD_OrgTrx_ID() <= 0){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("No existe Organización con ese Número o la misma debe ser distinta de CERO.");
				}
				else{
					MOrg orgTrx = new MOrg(getCtx(), loadMedioPagoFile.getAD_OrgTrx_ID(), null);
					if ((orgTrx == null) || (orgTrx.get_ID() <= 0)){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("No existe Organización con ese Número");
					}
				}

				if ((loadMedioPagoFile.getTaxID() == null) || (loadMedioPagoFile.getTaxID().trim().equalsIgnoreCase(""))){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Número de Identificación del Socio de Negocio, en la Linea del Archivo");
				}
				else{
					MBPartner partner = ComercialUtils.getPartnerByTaxID(getCtx(), loadMedioPagoFile.getTaxID(), null);
					if ((partner == null) || (partner.get_ID() <= 0)){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("No existe Socio de Negocio definido en el sistema con ese Número de Identificación : " + loadMedioPagoFile.getTaxID());
					}
					else{
						loadMedioPagoFile.setC_BPartner_ID(partner.get_ID());
					}
				}

				if (loadMedioPagoFile.getC_BankAccount_ID() <= 0){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Cuenta Bancaria");
				}
				else{
					MBankAccount bankAccount = new MBankAccount(getCtx(), loadMedioPagoFile.getC_BankAccount_ID(), null);
					if ((bankAccount == null) || (bankAccount.get_ID() <= 0)){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("No existe Cuenta Bancaria definida en el sistema con ese ID : " + loadMedioPagoFile.getC_BankAccount_ID());
					}
					else {
						loadMedioPagoFile.setC_Currency_ID(bankAccount.getC_Currency_ID());
					}
				}

				if ((loadMedioPagoFile.getNroMedioPago() == null) || (loadMedioPagoFile.getNroMedioPago().trim().equalsIgnoreCase(""))){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Serie del Documento");
				}

				if ((loadMedioPagoFile.getFechaCadena() == null) || (loadMedioPagoFile.getFechaCadena().trim().equalsIgnoreCase(""))){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Fecha de Emisión");
				}
				else{
					Timestamp fecDoc = DateUtils.convertStringToTimestamp_ddMMyyyy(loadMedioPagoFile.getFechaCadena());
					if (fecDoc == null){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("Formato de Fecha de Emisión inválido : " + loadMedioPagoFile.getFechaCadena());
					}
					loadMedioPagoFile.setDateEmitted(fecDoc);
				}

				if ((loadMedioPagoFile.getVencCadena() == null) || (loadMedioPagoFile.getVencCadena().trim().equalsIgnoreCase(""))){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Fecha de Vencimiento");
				}
				else{
					Timestamp fecVenc = DateUtils.convertStringToTimestamp_ddMMyyyy(loadMedioPagoFile.getVencCadena());
					if (fecVenc == null){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("Formato de Fecha de Vencimiento inválido : " + loadMedioPagoFile.getVencCadena());
					}
					loadMedioPagoFile.setDueDate(fecVenc);
				}

				if ((loadMedioPagoFile.getFechaAcctCadena() == null) || (loadMedioPagoFile.getFechaAcctCadena().trim().equalsIgnoreCase(""))){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Fecha Contable");
				}
				else{
					Timestamp fecAcct = DateUtils.convertStringToTimestamp_ddMMyyyy(loadMedioPagoFile.getFechaAcctCadena());
					if (fecAcct == null){
						loadMedioPagoFile.setIsConfirmed(false);
						loadMedioPagoFile.setErrorMsg("Formato de Fecha Contable inválido : " + loadMedioPagoFile.getFechaAcctCadena());
					}
					loadMedioPagoFile.setDateAcct(fecAcct);
				}

				if ((loadMedioPagoFile.getTotalAmt() == null) || (loadMedioPagoFile.getTotalAmt().compareTo(Env.ZERO) <= 0)){
					loadMedioPagoFile.setIsConfirmed(false);
					loadMedioPagoFile.setErrorMsg("Debe indicar Importe");
				}

				if (loadMedioPagoFile.isConfirmed()){
					contadorOK++;
				}
				else{
					contadorError++;
				}

				loadMedioPagoFile.saveEx();
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
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 * @return
	 */
	public List<MZLoadMedioPagoFile> getLines(){

		String whereClause = X_Z_LoadMedioPagoFile.COLUMNNAME_Z_LoadMedioPago_ID + " =" + this.get_ID();

		List<MZLoadMedioPagoFile> lines = new Query(getCtx(), I_Z_LoadMedioPagoFile.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas confirmadas de este documento.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 * @return
	 */
	public List<MZLoadMedioPagoFile> getLinesConfirmed(){

		String whereClause = X_Z_LoadMedioPagoFile.COLUMNNAME_Z_LoadMedioPago_ID + " =" + this.get_ID() +
				" AND " + X_Z_LoadMedioPagoFile.COLUMNNAME_IsConfirmed + " ='Y' ";

		List<MZLoadMedioPagoFile> lines = new Query(getCtx(), I_Z_LoadMedioPagoFile.Table_Name, whereClause, get_TrxName()).setOrderBy(" C_BankAccount_ID, NroMedioPago ").list();

		return lines;
	}



	/***
	 * Obtiene y retorna lineas cargadas de manera manual.
	 * Xpande. Created by Gabriel Vila on 4/2/19.
	 * @return
	 */
	public List<MZLoadMedioPagoMan> getLinesManual(){

		String whereClause = X_Z_LoadMedioPagoMan.COLUMNNAME_Z_LoadMedioPago_ID + " =" + this.get_ID();

		List<MZLoadMedioPagoMan> lines = new Query(getCtx(), I_Z_LoadMedioPagoMan.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

}