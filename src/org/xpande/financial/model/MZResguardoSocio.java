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

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import dto.sisteco.SistecoConvertResponse;
import dto.sisteco.SistecoResponseDTO;
import dto.uy.gub.dgi.cfe.*;
import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.compiere.apps.ADialog;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.X_C_TaxGroup;
import org.xpande.cfe.model.MZCFEConfig;
import org.xpande.cfe.model.MZCFERespuestaProvider;
import org.xpande.cfe.utils.ProcesadorCFE;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.financial.utils.FinancialUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

/** Generated Model for Z_ResguardoSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZResguardoSocio extends X_Z_ResguardoSocio implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170802L;

    /** Standard Constructor */
    public MZResguardoSocio (Properties ctx, int Z_ResguardoSocio_ID, String trxName)
    {
      super (ctx, Z_ResguardoSocio_ID, trxName);
    }

    /** Load Constructor */
    public MZResguardoSocio (Properties ctx, ResultSet rs, String trxName)
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

		//	Std Period open?
		/*
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

		// Lista de documentos asociados a este resguardo
		List<MZResguardoSocioDoc> docs = this.getResguardoDocs();

		if (this.getDateAcct() == null){
			this.setDateAcct(this.getDateDoc());
		}

		// Validaciones del documento
		String message =  this.validateDocument(docs);
		if (message != null){
			m_processMsg = message;
			return DocAction.STATUS_Invalid;
		}

		// Si es contra-resguardo, seteo ADENDA para impresión
		MDocType docType = (MDocType) this.getC_DocType();
		if (docType.getDocBaseType().equalsIgnoreCase("RGC")){
			this.setDescription("CONTRADOCUMENTO DE E-RESGUARDO");
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

		// Impacto en estado de cuenta
		FinancialUtils.setEstadoCtaResguardo(getCtx(), this, true, get_TrxName());

		ProcesadorCFE procesadorCFE = new ProcesadorCFE(getCtx(), get_TrxName());
		m_processMsg = procesadorCFE.executeCFE(this, this.getAD_Org_ID(), this.getC_DocType_ID());
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}


		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt

	/***
	 * Validaciones al documento al momento de completar.
	 * Xpande. Created by Gabriel Vila on 9/9/17.
	 * @return
	 * @param docs
	 */
	private String validateDocument(List<MZResguardoSocioDoc> docs) {

		String message = null;

		try{

			// No se permite generar resguardo con monto negativo o cero
			if ((this.getTotalAmt() == null) || (this.getTotalAmt().compareTo(Env.ZERO) <= 0)){
				return "No se permite completar Resguardos con Monto menor o igual a cero.";
			}

			int cPeriodID = 0;

			// Recorro documentos de este resguardo para validaciones
			for (MZResguardoSocioDoc resguardoSocioDoc: docs){

				// Valido documentos del mismo periodo, ya que no es posible mezclar documentos de distintos periodos en un mismo resguardo.
				MPeriod period = MPeriod.get(getCtx(), resguardoSocioDoc.getDateDoc(), this.getAD_Org_ID());
				if ((period == null) || (period.get_ID() <= 0)){
					return "No se pudo obtener Período contable para Fecha : " + resguardoSocioDoc.getDateDoc();
				}
				if (cPeriodID > 0){
					if (period.get_ID() != cPeriodID){
						return "No se permite completar Resguardos conteniendo Documentos de distintos períodos.";
					}
				}
				else{
					cPeriodID = period.get_ID();
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
		log.info("voidIt - " + toString());
		return false;
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

		// Valido que este documento pueda reactivarse en caso que no se tenga que enviar como CFE.
		MZCFEConfig cfeConfig = MZCFEConfig.getDefault(getCtx(), get_TrxName());
		if ((cfeConfig != null) && (cfeConfig.get_ID() > 0)){
			boolean docsendCFE = cfeConfig.isDocSendCFE(this.getAD_Org_ID(), this.getC_DocType_ID());
			if (docsendCFE){
				m_processMsg = "No es posible reactivar este Documento ya que esta asociado a un Comprobante Electrónico CFE.";
				return false;
			}
		}

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// Impacto en estado de cuenta
		FinancialUtils.setEstadoCtaResguardo(getCtx(), this, false, get_TrxName());

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;


		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setDocAction(DOCACTION_Complete);

		return true;
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
      StringBuffer sb = new StringBuffer ("MZResguardoSocio[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Metodo para calcular importes de retenciones para los comprobantes seleccionados en este resguardos.
	 * Xpande. Created by Gabriel Vila on 8/9/17.
	 */
	public void calcularRetenciones() {

		String action = "", sql = "";

		try{
			// Limpio datos anteriores
			this.setTotalAmt(Env.ZERO);
			this.setTotalAmtME(Env.ZERO);

			action = " delete from z_resguardosocioret where z_resguardosocio_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " update z_resguardosociodoc set amtretencion = 0, amtretencionmo = 0 where z_resguardosocio_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_resguardosociodocret where z_resguardosocio_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Obtengo lista de retenciones asociadas a este socio de negocio
			List<MZRetencionSocioBPartner> retencionesSocioBPartner = MZRetencionSocio.getRetencionesBPartner(getCtx(), this.getC_BPartner_ID(), get_TrxName());

			// Si no tengo retenciones asociadas al socio, aviso y no hago nada
			if ((retencionesSocioBPartner == null) || (retencionesSocioBPartner.size() <= 0)){
				return;
			}

			// Obtengo lista de comprobantes a considerar
			List<MZResguardoSocioDoc> resguardoSocioDocs = this.getResguardoDocs();
			if ((resguardoSocioDocs == null) || (resguardoSocioDocs.size() <= 0)){
				return;
			}

			// Recorro retenciones del socio y calculo el monto a retener en caso que apliquen
			for (MZRetencionSocioBPartner retencionSocioBPartner: retencionesSocioBPartner){

				MZRetencionSocio retencionSocio = (MZRetencionSocio) retencionSocioBPartner.getZ_RetencionSocio();

				// Obtengo monto total base de comprobantes segun monto en el cual aplica este retencion (subtotal, total, impuestos)
				BigDecimal totalBase = this.getTotalBaseRetencion(retencionSocio, 0);

				// Si esta retención aplica segun monto mínimo en Unidades Indexadas
				if (retencionSocio.isConsideraUnidadIndexada()){

					// Convierto dicho monto a Unidades Indexadas según tasa de cambio a la fecha de este documento
					//BigDecimal multiplyRate = CurrencyUtils.getMultiplyRateToUnidadIndexada(getCtx(), this.getAD_Client_ID(), 0, this.getC_Currency_ID(), 0, this.getDateDoc(), null);
					MCurrency currencyUNI = MCurrency.get(getCtx(), "UNI");
					if ((currencyUNI == null) || (currencyUNI.get_ID() <= 0)){
						throw new AdempiereException("No se encuentra definida la moneda: Unidad Indexada");
					}

					BigDecimal multiplyRate = CurrencyUtils.getCurrencyRateToAcctSchemaCurrency(getCtx(), this.getAD_Client_ID(), 0, this.getC_Currency_ID(), currencyUNI.get_ID(),0, this.getDateDoc(), null);


					if ((multiplyRate == null) || (multiplyRate.compareTo(Env.ZERO) == 0)){
						throw new AdempiereException("No hay tasa de conversión para Unidades Indexadas en la fecha y moneda de este documeto.");
					}

					if (retencionSocio.getAmtUnidadIndexada() == null){
						retencionSocio.setAmtUnidadIndexada(Env.ZERO);
					}

					// Comparo mínimo monto en unidades indexadas de esta retención con el monto resultante de convertir el total base a unidades indexadas.
					BigDecimal totalBaseUI = totalBase.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
					if (totalBaseUI.compareTo(retencionSocio.getAmtUnidadIndexada()) < 0){
						// El total base no llega al mínimo en unidades indexadas para su cálculo, no hago nada con esta retencion.
						continue;
					}
				}

				// Guardo asociación de retencion con esta emisión de resguardo, ya que dicha retención aplica.
				MZResguardoSocioRet resguardoSocioRet = new MZResguardoSocioRet(getCtx(), 0, get_TrxName());
				resguardoSocioRet.setZ_ResguardoSocio_ID(this.get_ID());
				resguardoSocioRet.setZ_RetencionSocio_ID(retencionSocio.get_ID());
				resguardoSocioRet.setAmtBase(totalBase);
				resguardoSocioRet.setAmtRetencion(Env.ZERO);
				resguardoSocioRet.saveEx();

				// Aplico retencion a cada comprobante y voy sumarizando montos a retener tanto en el comprobante como en la retencion
				for (MZResguardoSocioDoc resguardoSocioDoc: resguardoSocioDocs){

					// Obtengo monto base de este comprobante segun monto en el cual aplica este retencion (subtotal, total, impuestos)
					BigDecimal totalBaseDoc = this.getTotalBaseRetencion(retencionSocio, resguardoSocioDoc.get_ID());

					// Aplico porcentaje de retencion a este comprobante
					BigDecimal amtRetencionDoc = totalBaseDoc.multiply(retencionSocio.getPorcRetencion()).setScale(0, BigDecimal.ROUND_HALF_UP);

					// Acumulo monto a retener en este comprobante en moneda del resguardo
					resguardoSocioDoc.setAmtRetencion(resguardoSocioDoc.getAmtRetencion().add(amtRetencionDoc));

					// Acumulo monto a retener este comprobante en moneda del comprobante
					BigDecimal amtRetencionMODoc = amtRetencionDoc;
					if (resguardoSocioDoc.getC_Currency_ID() != this.getC_Currency_ID()){
						if ((resguardoSocioDoc.getCurrencyRate() != null) && (resguardoSocioDoc.getCurrencyRate().compareTo(Env.ZERO) > 0)){
							amtRetencionMODoc = amtRetencionDoc.divide(resguardoSocioDoc.getCurrencyRate(), 2, BigDecimal.ROUND_HALF_UP);
						}
					}
					resguardoSocioDoc.setAmtRetencionMO(resguardoSocioDoc.getAmtRetencionMO().add(amtRetencionMODoc));

					resguardoSocioDoc.saveEx();

					// Acumulo monto a retener en la retencion
					resguardoSocioRet.setAmtRetencion(resguardoSocioRet.getAmtRetencion().add(amtRetencionDoc));

					// Acumulo monto a retener en la retencion en moneda extranjera solo cuando el documento es en moneda extranjera
					if (resguardoSocioDoc.getC_Currency_ID() != this.getC_Currency_ID()){
						resguardoSocioRet.setAmtRetencionME(resguardoSocioRet.getAmtRetencionME().add(amtRetencionMODoc));
					}

					// Guardo detalle de monto retenido a comprobante y concepto de retencion.
					MZResguardoSocioDocRet resguardoSocioDocRet = new MZResguardoSocioDocRet(getCtx(), 0, get_TrxName());
					resguardoSocioDocRet.setZ_ResguardoSocio_ID(this.get_ID());
					resguardoSocioDocRet.setZ_ResguardoSocioDoc_ID(resguardoSocioDoc.get_ID());
					resguardoSocioDocRet.setZ_RetencionSocio_ID(retencionSocio.get_ID());
					resguardoSocioDocRet.setAmtBase(totalBaseDoc);
					resguardoSocioDocRet.setAmtRetencion(amtRetencionDoc);
					resguardoSocioDocRet.setPorcRetencion(retencionSocio.getPorcRetencion());

					// Referencia
					Timestamp ts = resguardoSocioDoc.getDateDoc();
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYY");
					resguardoSocioDocRet.setReference(((MDocType) resguardoSocioDoc.getC_DocType()).getPrintName().trim() + " "
							+ resguardoSocioDoc.getDocumentNoRef() + " " + format.format(ts));

					resguardoSocioDocRet.saveEx();

					// Acumulo en total de esta emisión
					this.setTotalAmt(this.getTotalAmt().add(amtRetencionDoc));

					// Acumulo en total de esta emisión en moneda extranjera solo cuando el documento es en moneda extranjera
					if (resguardoSocioDoc.getC_Currency_ID() != this.getC_Currency_ID()){
						this.setTotalAmtME(this.getTotalAmtME().add(amtRetencionMODoc));
					}
				}
				resguardoSocioRet.saveEx();
			}

			this.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Obtiene y retorna lista de comprobantes a considerar en esta emisión de resguardo.
	 * Xpande. Created by Gabriel Vila on 8/2/17.
	 * @return
	 */
	public List<MZResguardoSocioDoc> getResguardoDocs(){

		String whereClause = X_Z_ResguardoSocioDoc.COLUMNNAME_Z_ResguardoSocio_ID + " =" + this.get_ID();

		List<MZResguardoSocioDoc> lines = new Query(getCtx(), I_Z_ResguardoSocioDoc.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lista de retenciones que aplican en esta emisión de resguardo.
	 * Xpande. Created by Gabriel Vila on 8/2/17.
	 * @return
	 */
	public List<MZResguardoSocioRet> getResguardoRets(){

		String whereClause = X_Z_ResguardoSocioRet.COLUMNNAME_Z_ResguardoSocio_ID + " =" + this.get_ID();

		List<MZResguardoSocioRet> lines = new Query(getCtx(), I_Z_ResguardoSocioRet.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene monto total base de comprobantes para una determinada retencion y tipo de monto a la cual aplica.
	 * Si viene ID de comprobante, solo obtengo monto base para dicho comprobante.
	 * Xpande. Created by Gabriel Vila on 8/2/17.
	 * @param retencionSocio
	 * @param zResguardoSocioDocID
	 * @return
	 */
	private BigDecimal getTotalBaseRetencion(MZRetencionSocio retencionSocio, int zResguardoSocioDocID){

		BigDecimal totalBase = Env.ZERO;
		String sql = "";

		try{

			String filtroDoc = "";
			if (zResguardoSocioDocID > 0){
				filtroDoc = " and z_resguardosociodoc_id =" + zResguardoSocioDocID;
			}

			// Obtengo suma de subtotales de comprobantes
			sql = " select round(sum(amtsubtotal), 2) from z_resguardosociodoc where z_resguardosocio_id =" + this.get_ID() + filtroDoc;
			BigDecimal sumSubtotalDocs = DB.getSQLValueBDEx(get_TrxName(), sql);

			// Obtengo suma de totales de comprobantes
			sql = " select round(sum(amttotal), 2) from z_resguardosociodoc where z_resguardosocio_id =" + this.get_ID() + filtroDoc;
			BigDecimal sumTotalDocs = DB.getSQLValueBDEx(get_TrxName(), sql);

			// Seteo monto total base para el cálculo segun sobre que monto aplica esta retencion
			if (retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_SUBTOTAL)){

				totalBase = sumSubtotalDocs;

				// Si esta retencion aplica solamente para jerarquias de productos, tengo que obtener monto base solamente considerando los productos
				// de dichas jerarquias dentro de los comprobantes a considerar.
					/*
					sql = " select round(sum(coalesce(il.amtsubtotal,0)),2) " +
							" from c_invoiceline il " +
							" inner join m_product prod on il.m_product_id = prod.m_product_id " +
							" where il.c_invoice_id in " +
							" (select c_invoice_id from z_resguardosociodoc where z_resguardosocio_id = 1000000 and docbasetype='API') " +
							" and prod.z_productoseccion_id > 0
					*/
			}
			else if (retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_TOTAL)){
				totalBase = sumTotalDocs;
			}
			else if (retencionSocio.getRetencionMontoAplica().equalsIgnoreCase(X_Z_RetencionSocio.RETENCIONMONTOAPLICA_IMPUESTOS)){
				sql = " select round(sum(coalesce(taxamt, 0)), 2) from z_resguardosociodoctax " +
						" where z_resguardosocio_id =" + this.get_ID() +
						" and c_taxcategory_id in (select c_taxcategory_id from z_retencionsociotax where z_retencionsocio_id =" + retencionSocio.get_ID() + ")" + filtroDoc;
				totalBase = DB.getSQLValueBDEx(get_TrxName(), sql);

				// Si no hay impuestos en comprobantes que apliquen la retencion, no tengo monto base.
				if (totalBase == null){
					totalBase = Env.ZERO;
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return totalBase;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Me aseguro de setear fecha contable
		this.setDateAcct(this.getDateDoc());

		return true;
	}

	@Override
	protected boolean beforeDelete() {

		// Si estoy eliminando un Contra-Resguardo, me aseguro de eliminar referencias contra su resguardo asociado
		MDocType docType = (MDocType) this.getC_DocType();
		if (docType.getDocBaseType().equalsIgnoreCase("RGC")){
			MZResguardoSocio resguardoSocioRef = (MZResguardoSocio) this.getZ_ResguardoSocio_Ref();
			if ((resguardoSocioRef != null) && (resguardoSocioRef.get_ID() > 0)){

				String action = " update z_resguardosocio set z_resguardosocio_ref_id = null where z_resguardosocio_id =" + resguardoSocioRef.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
		}

		return true;
	}


	/***
	 * Metodo que verifica si una determinada invoice tiene un resguardo emitido o no.
	 * Xpande. Created by Gabriel Vila on 9/18/17.
	 * @param ctx
	 * @param cInvoiceID : ID de la invoice a considerar
	 * @param trxName
	 * @return
	 */
    public static boolean invoiceTieneResguardo(Properties ctx, int cInvoiceID, String trxName) {

    	boolean value = false;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select rgd.c_invoice_id " +
					" from z_resguardosociodoc rgd " +
					" inner join z_resguardosocio rg on rgd.z_resguardosocio_id = rg.z_resguardosocio_id " +
					" inner join c_doctype doc on rg.c_doctype_id = doc.c_doctype_id " +
					" where rg.docstatus='CO' " +
					" and doc.docbasetype='RGU' " +
					" and rgd.c_invoice_id =" + cInvoiceID +
					" and rg.z_resguardosocio_id not in " +
					" (select coalesce(z_resguardosocio_ref_id,0) from z_resguardosocio " +
					" where docstatus='CO')";

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			if (rs.next()){
				value = true;
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
		finally {
		    DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return value;
    }

	/***
	 * Metodo que obtiene y retorna modelo de resguardo asociado a una determinada invoice.
	 * En caso de no tener retorna null.
	 * Xpande. Created by Gabriel Vila on 9/19/19.
	 * @param ctx
	 * @param cInvoiceID : ID de la invoice a considerar
	 * @param trxName
	 * @return
	 */
	public static MZResguardoSocioDoc getByInvoice(Properties ctx, int cInvoiceID, String trxName) {

		MZResguardoSocioDoc resguardoSocioDoc = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select rgd.z_resguardosociodoc_id " +
					" from z_resguardosociodoc rgd " +
					" inner join z_resguardosocio rg on rgd.z_resguardosocio_id = rg.z_resguardosocio_id " +
					" inner join c_doctype doc on rg.c_doctype_id = doc.c_doctype_id " +
					" where rg.docstatus='CO' " +
					" and doc.docbasetype='RGU' " +
					" and rgd.c_invoice_id =" + cInvoiceID +
					" and rg.z_resguardosocio_id not in " +
					" (select coalesce(z_resguardosocio_ref_id,0) from z_resguardosocio " +
					" where docstatus='CO')";

			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();

			if (rs.next()){
				resguardoSocioDoc = new MZResguardoSocioDoc(ctx, rs.getInt("z_resguardosociodoc_id"), trxName);
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		return resguardoSocioDoc;
	}
}