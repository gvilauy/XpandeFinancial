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
import org.xpande.cfe.model.MZCFERespuestaProvider;
import org.xpande.core.utils.CurrencyUtils;

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


		this.cfe();

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
			action = " update z_resguardosocio set totalamt = 0 where z_resguardosocio_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_resguardosocioret where z_resguardosocio_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " update z_resguardosociodoc set amtretencion = 0 where z_resguardosocio_id =" + this.get_ID();
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
					BigDecimal multiplyRate = CurrencyUtils.getMultiplyRateToUnidadIndexada(getCtx(), this.getAD_Client_ID(), 0, this.getC_Currency_ID(), 0, this.getDateDoc(), null);
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
					BigDecimal amtRetencionDoc = totalBaseDoc.multiply(retencionSocio.getPorcRetencion()).setScale(2, BigDecimal.ROUND_HALF_UP);

					// Acumulo monto a retener en este comprobante
					resguardoSocioDoc.setAmtRetencion(resguardoSocioDoc.getAmtRetencion().add(amtRetencionDoc));
					resguardoSocioDoc.saveEx();

					// Acumulo monto a retener en la retencion
					resguardoSocioRet.setAmtRetencion(resguardoSocioRet.getAmtRetencion().add(amtRetencionDoc));

					// Acumulo en total de esta emisión
					this.setTotalAmt(this.getTotalAmt().add(amtRetencionDoc));

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

	private void cfe() {

		try{
			CFEEmpresasType objECfe = new CFEEmpresasType();
			CFEDefType objCfe = new CFEDefType();
			objECfe.setCFE(objCfe);

			//setObjCFE(objECfe);
			objCfe.setEResg(new CFEDefType.EResg());

			loadEncabezado_eResguardo(objECfe);
			loadDetalleProductosOServicios_eResguardo(objECfe);

			loadReferencia();

			loadCAE(objECfe);
			loadAdenda(objECfe);

			// Sigo
			if (objCfe.getEResg().getEncabezado() != null) {
				objCfe.getEResg().getEncabezado().setEmisor(null);
			}
			objCfe.getEResg().setTmstFirma(null);

			// Sigooo
			this.SendCfe(objCfe);



		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	private void loadEncabezado_eResguardo(CFEEmpresasType objECfe) {
		CFEDefType objCfe = objECfe.getCFE();

		CFEDefType.EResg.Encabezado encabezado = new CFEDefType.EResg.Encabezado();
		objCfe.getEResg().setEncabezado(encabezado);

		IdDocResg idDocResg = new IdDocResg();
		TotalesResg totales = new TotalesResg();

		encabezado.setIdDoc(idDocResg);
		encabezado.setTotales(totales);

		/*   1 */ objCfe.setVersion("1.0");

		//  AREA: Identificacion del Comprobante
		/*   2 */ idDocResg.setTipoCFE(BigInteger.valueOf(182));
		MDocType doc = (MDocType) this.getC_DocType();
		MSequence sec = new MSequence(getCtx(), doc.getDefiniteSequence_ID(), get_TrxName());
		if(sec.getPrefix() != null) {
			/*   3 */ idDocResg.setSerie(sec.getPrefix());
		} else {
			throw new AdempiereException("CFEMessages.IDDOC_003");
		}
		if(this.getDocumentNo() != null){ // Se obtiene nro de cae directamente del documentNo
			// Se quita serie del n�mero para enviar
			String documentNo = this.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresi�n regular para quitar todo lo que no es n�mero

			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");
			BigInteger numero = new BigInteger(docno);
			/*   4 */ idDocResg.setNro(numero);
		}
		else throw new AdempiereException("CFEMessages.IDDOC_004");
		if (this.getDateDoc() != null){
			/*   5 */ idDocResg.setFchEmis(Timestamp_to_XmlGregorianCalendar_OnlyDate(this.getDateDoc(), false));
		} else {
			throw new AdempiereException("CFEMessages.IDDOC_005");
		}
		/*   6-15 - Tipo de obligatoriedad 0 (No corresponde)*/

		//  AREA: Emisor
		MOrgInfo orgInfo = MOrgInfo.get(getCtx(), this.getAD_Org_ID(), get_TrxName());
		encabezado.setEmisor(loadEmisor(orgInfo, getCtx()));

		//  Area: Receptor
		MBPartner partner =  MBPartner.get(getCtx(), this.getC_BPartner_ID());
		encabezado.setReceptor(loadResguardoReceptor(partner, this.getDocumentNo(), getCtx()));

		// AREA: Totales Encabezado
		MCurrency mCurrency = (MCurrency) this.getC_Currency();
		if (mCurrency.getISO_Code() == null) throw new AdempiereException("CFEMessages.TOTALES_110");
		try {
			/* 110 */ totales.setTpoMoneda(TipMonType.valueOf(mCurrency.getISO_Code()));
			if (mCurrency.getC_Currency_ID() != 142) {

				BigDecimal currRate = CurrencyUtils.getCurrencyRateToAcctSchemaCurrency(getCtx(), this.getAD_Client_ID(), 0, this.getC_Currency_ID(), 142, 0, this.getDateDoc(), null);

				if (currRate.equals(Env.ZERO)) throw new AdempiereException("CFEMessages.TOTALES_111");
				/* 111 */ totales.setTpoCambio(currRate.setScale(3, BigDecimal.ROUND_HALF_UP));
			}
		} catch (AdempiereException ex){
			throw ex;
		} catch (Exception ex){
			throw new AdempiereException("CFEMessages.TOTALES_110_2");
		}

		// Totales por tipo de retencion / percepcion

		String sqlReten = " SELECT ret.Z_RetencionSocio_ID, ret.codigodgi, ret.emitiedgi, SUM(resl.amtretencion) total"
				+ " FROM Z_ResguardoSocio res"
				+ " INNER JOIN Z_ResguardoSocioRet resl ON res.Z_ResguardoSocio_ID = resl.Z_ResguardoSocio_ID"
				+ " INNER JOIN Z_RetencionSocio ret ON resl.Z_RetencionSocio_ID = ret.Z_RetencionSocio_ID"
				+ " WHERE res.Z_ResguardoSocio_ID = " + this.get_ID()
				+ " GROUP BY ret.Z_RetencionSocio_ID, ret.codigodgi, ret.emitiedgi";

		PreparedStatement pstmt = DB.prepareStatement (sqlReten, get_TrxName());
		ResultSet rs;

		BigDecimal totalSum = Env.ZERO;
		List<TotalesResg.RetencPercep> listRetPerc = totales.getRetencPercep();

		try {
			rs = pstmt.executeQuery();
			while (rs.next()){

				boolean isDgi = false;
				String codigo = rs.getString("codigodgi");
				try {
					isDgi = rs.getString("emitieDGI").equalsIgnoreCase("Y") ? true : false;
				} catch (Exception e) {}
				BigDecimal montoSum = rs.getBigDecimal("total");

				if (montoSum != null) {
					montoSum = montoSum.setScale(2, RoundingMode.HALF_UP);
				}

				// Valido que si no es una retenci�n de DGI, el codigo de retenci�n debe estar entre 9999001 y 9999999
				if (!isDgi) {
					int intCod = 0;
					try {
						intCod = Integer.valueOf(codigo);
					} catch (Exception e) { /* Si lanza excepci�n, queda en 0 */ }
					if (intCod < 9999001 || intCod > 9999999) {
						throw new AdempiereException("CFEMessages.TOTALES_127_OUTOFRANGE");
					}
				}
				TotalesResg.RetencPercep retPerc = new TotalesResg.RetencPercep();

				// Contra-Resguardo, doy vuelta el signo.
				if (doc.getDocBaseType().equalsIgnoreCase("RGC")) {
					montoSum = montoSum.negate();
				}
				/* 127 */ retPerc.setCodRet(codigo);
				/* 128 */ retPerc.setValRetPerc(montoSum);
				totalSum = totalSum.add(montoSum);

				listRetPerc.add(retPerc);

			}
		} catch (SQLException e) {
			throw new AdempiereException(e);
		}

		if (listRetPerc.size() == 0) {
			throw new AdempiereException("CFEMessages.TOTALES_125");
		}

		/* 125 */ totales.setMntTotRetenido(totalSum);
		/* 126 */ totales.setCantLinDet(listRetPerc.size());
	}


	public XMLGregorianCalendar Timestamp_to_XmlGregorianCalendar_OnlyDate(Timestamp timestamp, boolean withTime) {
		try {
			GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
			cal.setTime(timestamp);
			XMLGregorianCalendar xgcal;
			if (!withTime){
				xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
			} else {
				xgcal = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED );
				xgcal.setHour(cal.get(Calendar.HOUR_OF_DAY));
				xgcal.setMinute(cal.get(Calendar.MINUTE));
				xgcal.setSecond(cal.get(Calendar.SECOND));
				xgcal.setMillisecond(cal.get(Calendar.MILLISECOND));
				xgcal.setTimezone(-3*60); // GTM -3 en minutos

			}
			return xgcal;
		} catch (DatatypeConfigurationException e) {
			throw new AdempiereException(e);
		}
	}


	public Emisor loadEmisor(MOrgInfo orgInfo, Properties ctx) {
		Emisor emisor = new Emisor();

		if (orgInfo == null) throw new AdempiereException("CFEMessages.EMISOR_ORG");

		//if (orgInfo.getDUNS() == null) throw new AdempiereException("CFEMessages.EMISOR_040");
		///* 40  */ emisor.setRUCEmisor(orgInfo.getDUNS());

		//if (orgInfo.getrznsoc() == null) throw new AdempiereException(CFEMessages.EMISOR_041);
		///* 41  */ emisor.setRznSoc(orgInfo.getrznsoc());

		emisor.setRUCEmisor("212334750012");
		emisor.setRznSoc("212334750012");

		MOrg mOrg = MOrg.get(ctx, orgInfo.getAD_Org_ID());
		if (mOrg != null && mOrg.getName() != null) {
			/* 42  */ emisor.setNomComercial(MOrg.get(ctx, orgInfo.getAD_Org_ID()).getName());
		}
		///* 43  */ emisor.setGiroEmis(orgInfo.getgirotype());
		/* 44   - Tipo de obligatoriedad 3 (dato opcional) */
		///* 45  */ emisor.setCorreoEmisor(orgInfo.getEMail());

		emisor.setNomComercial("Supermercado Covadonga S.A.");
		//emisor.setGiroEmis(orgInfo.getgirotype()	);
		//emisor.setCorreoEmisor(orgInfo.getEMail());


		//MWarehouse casa = MWarehouse.get(ctx, orgInfo.getDropShip_Warehouse_ID());
		///* 46  */ emisor.setEmiSucursal(casa.getName());
		emisor.setEmiSucursal("Covadonga");


		//try {
			///* 47  */ emisor.setCdgDGISucur(BigInteger.valueOf(Long.valueOf(orgInfo.get_ValueAsString("TaxNo"))));
//		}catch(Exception ex){
			//throw new AdempiereException(CFEMessages.EMISOR_047);
//		}

		emisor.setCdgDGISucur(new BigInteger("1"));

		MLocation mLocation = (MLocation) orgInfo.getC_Location();

		//if (mLocation == null || mLocation.getAddress1() == null) throw new AdempiereException(CFEMessages.EMISOR_048);
		///* 48  */ emisor.setDomFiscal(mLocation.getAddress1());
		emisor.setDomFiscal("Progreso");

		//MLocalidades mLocalidades = (MLocalidades) mLocation.getUY_Localidades();
		//if (mLocalidades == null || mLocalidades.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_049);
		///* 49  */ emisor.setCiudad(mLocalidades.getName());
		emisor.setCiudad("PROGRESO");

		//MDepartamentos mDepartamentos = (MDepartamentos) mLocation.getUY_Departamentos();
		//if (mDepartamentos == null || mDepartamentos.getName() == null) throw new AdempiereException(CFEMessages.EMISOR_050);
		///* 50  */ emisor.setDepartamento(mDepartamentos.getName());

		emisor.setDepartamento("CANELONES");

		return emisor;
	}

	public ReceptorResg loadResguardoReceptor(MBPartner partner, String documentNo, Properties ctx) {
		ReceptorResg receptor = new ReceptorResg();

		// Cargo C_BPartnerLocation marcada para remito
		MBPartnerLocation partnerLocation = null;
		MBPartnerLocation[] locations = partner.getLocations(false);
		if (locations == null) throw new AdempiereException("CFEMessages.RECEPTOR_REM_NOLOCATIONDEF");
		for (MBPartnerLocation mbpl : locations) {
			if (mbpl.isRemitTo()) {

				if (partnerLocation == null) {
					partnerLocation = mbpl;
				} else {
					if (mbpl.getUpdated().compareTo(partnerLocation.getUpdated()) > 0) {
						partnerLocation = mbpl;
					}
				}
			}
		}
		if (partnerLocation == null) throw new AdempiereException("CFEMessages.RECEPTOR_REM_NOLOCATIONDEF");

		//  Area: Receptor

		int tipoDocRecep = 2;
		String docRecep = partner.getTaxID();

		/* 60  */ receptor.setTipoDocRecep(tipoDocRecep);
		MCountry mCountry = null;
		mCountry = MCountry.get(getCtx(), 336);

		if (mCountry == null) throw new AdempiereException("CFEMessages.RECEPTOR_61");
		/* 61  */ receptor.setCodPaisRecep(mCountry.getCountryCode());

		if (tipoDocRecep == 2 || tipoDocRecep == 3) {
			/* 62  */ receptor.setDocRecep(docRecep);
		} else if (tipoDocRecep == 4 || tipoDocRecep == 5 || tipoDocRecep == 6) {
			/* 62.1*/ receptor.setDocRecepExt(docRecep);
		}

		/* 63  */ receptor.setRznSocRecep(partner.getName());String dirRecep = null;
		MLocation location = (MLocation) partnerLocation.getC_Location();
		String add1 = location.getAddress1();
		if (add1 != null) {
			if (add1.length() <= 70)
				dirRecep = add1;
			else
				dirRecep = add1.substring(0, 70);
		}
		/* 64  */ receptor.setDirRecep(dirRecep);
		/* 65  */ receptor.setCiudadRecep(location.getCity());
		/* 66  */ receptor.setDeptoRecep(location.getRegionName());
		/* 66.1*/ receptor.setPaisRecep("Uruguay");
		try {
			///* 67  */ receptor.setCP(Integer.valueOf(partnerLocation.getUY_Localidades().getzipcode()));
		} catch (Exception ex) { }



		return receptor;
	}

	private void loadDetalleProductosOServicios_eResguardo(CFEEmpresasType objECfe) {
		CFEDefType objCfe = objECfe.getCFE();
		objCfe.getEResg().setDetalle(new CFEDefType.EResg.Detalle());
		List<ItemResg> itemResgs = objCfe.getEResg().getDetalle().getItem();
		List<MZResguardoSocioRet> mResguardoLines = this.getResguardoRets();

		if (mResguardoLines.size() > 200) {
			throw new AdempiereException("CFEMessages.TOTALES_126_2");
		}

		int position = 1;

		for (MZResguardoSocioRet mResguardoLine : mResguardoLines) {
			MZRetencionSocio mRetention = (MZRetencionSocio) mResguardoLine.getZ_RetencionSocio();
			if (mRetention == null) {
				throw new AdempiereException("CFEMessages.DETALLE_NORETENTION");
			}


			ItemResg itemResg = new ItemResg();

			/*   1 */ itemResg.setNroLinDet(position ++);

			MDocType docType = (MDocType) this.getC_DocType();

			// Contra-Resguardo
			if (docType.getDocBaseType().equalsIgnoreCase("RGC")) {
				itemResg.setIndFact(BigInteger.valueOf(9));
			}


			List<RetPercResg> listRetPercs = itemResg.getRetencPercep();
			RetPercResg retPersc = new RetPercResg();
			listRetPercs.add(retPersc);

			/*  20 */ retPersc.setCodRet(mRetention.getCodigoDGI());
			/*  21    Dato condicional, opcional, no se carga */

			/*  22 */ retPersc.setMntSujetoaRet(mResguardoLine.getAmtBase().setScale(2, RoundingMode.HALF_UP));
			/*  23 */ retPersc.setValRetPerc(mResguardoLine.getAmtRetencion().setScale(2, RoundingMode.HALF_UP));

			itemResgs.add(itemResg);
		}

	}

	private void loadCAE(CFEEmpresasType objECfe) {
		CFEDefType objCfe = objECfe.getCFE();

		CAEDataType caeDataType = new CAEDataType();
		objCfe.getEResg().setCAEData(caeDataType);

		caeDataType.setCAEID(new BigDecimal(90160202170.0).toBigInteger());
		caeDataType.setDNro(new BigDecimal(1).toBigInteger());
		caeDataType.setHNro(new BigDecimal(12000).toBigInteger());
		caeDataType.setFecVenc(Timestamp_to_XmlGregorianCalendar_OnlyDate(Timestamp.valueOf("2018-10-27 00:00:00"), false));//mDgiCae.getfechaVencimiento() Emi

	}

	protected void loadAdenda(CFEEmpresasType objECfe) {

		MDocType docType = (MDocType) this.getC_DocType();

		objECfe.setAdenda(this.getDescription());

		// Contra-Resguardo
		if (docType.getDocBaseType().equalsIgnoreCase("RGC")){
			objECfe.setAdenda("Corrección de e-Resguardo");
		}

	}


	private void SendCfe(CFEDefType cfeDefType) {

		try {

			CFEEmpresasType cfeEmpresasType = new CFEEmpresasType();
			cfeEmpresasType.setCFE(cfeDefType);

			File file = File.createTempFile("SistecoXMLCFE", ".xml");
			file.deleteOnExit();
			JAXBContext jaxbContext = JAXBContext.newInstance(CFEEmpresasType.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(cfeEmpresasType, file);

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String linea;
			String xml = "";
			while((linea=br.readLine())!=null) {
				xml += linea + "\n";
			}


//			// Quito namespaces
			xml = xml
//					//.replaceAll("xmlns:ns2=\"[a-zA-Z1-90:/.#]*\"", "")
//					//.replaceAll("xmlns:ns3=\"[a-zA-Z1-90:/.#]*\"", "")
//					//.replace("<CFE_Adenda  >", "<CFE_Adenda>")
//					//.replace("standalone=\"yes\"", "")
					.replace("<CFE xmlns:ns0=\"http://cfe.dgi.gub.uy\" version=\"1.0\">", "<ns0:CFE version=\"1.0\">")
					.replace("</CFE>","</ns0:CFE>")
					.replace("<CFE_Adenda ", "<ns0:CFE_Adenda xmlns:ns0=\"http://cfe.dgi.gub.uy\"")
					.replace("</CFE_Adenda>", "</ns0:CFE_Adenda>")
					.replace("xmlns:ns0=\"http://cfe.dgi.gub.uy\"xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "xmlns:ns0=\"http://cfe.dgi.gub.uy\" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"");

			// Guardo XML sin los namespace
			PrintWriter pw = new PrintWriter(file);
			pw.println(xml);
			pw.close();


			Service service = new Service();
			Call call = (Call) service.createCall();
			// Establecemos la dirección en la que está activado el WebService
			call.setTargetEndpointAddress(new java.net.URL("http://10.0.0.130/ws_efactura/ws_efactura.php"));

			//call.setOperationName(new QName("efac", "http://www.objetos.com.uy/efactura/"));
			// Establecemos el nombre del método a invocar
			call.setOperationName(new QName("http://www.objetos.com.uy/efactura/", "recepcionDocumento"));
			call.setSOAPActionURI("http://www.objetos.com.uy/efactura/recepcionDocumento");

			// Establecemos los parámetros que necesita el método
			// Observe que se deben especidicar correctamente tanto el nómbre como el tipo de datos. Esta información se puede obtener viendo el WSDL del servicio Web
			call.addParameter(new QName("entrada"), XMLType.XSD_STRING, ParameterMode.IN);

			// Especificamos el tipo de datos que devuelve el método.
			call.setReturnType(XMLType.XSD_STRING);

			// Invocamos el método
			String result = (String) call.invoke("http://www.objetos.com.uy/efactura/", "recepcionDocumento", new Object[] { "<![CDATA[" + xml + "]]>" });

			// Quitamos el CDATA, solo al comienzo y al final si estan en el string
			result = result.replaceAll("^<!\\[CDATA\\[", "").replaceAll("]]>$", "");


			// Guardo la respuesta de Sisteco
			File response = File.createTempFile("SistecoXMLCFEResponse", ".xml");
			response.deleteOnExit();
			FileWriter fichero = new FileWriter(response);
			PrintWriter pwResponse = new PrintWriter(fichero);
			pwResponse.print(result);
			pwResponse.close();

			SistecoResponseDTO cfeDtoSisteco = SistecoConvertResponse.getObjSistecoResponseDTO(result);

			// Si la respuesta contiene errores, lanzo una excepci�n
			if (cfeDtoSisteco.getStatus() != 0) {
				throw new AdempiereException("CFEMessages.CFE_ERROR_PROVEEDOR : " + cfeDtoSisteco.getDescripcion());
			}


			/*
			MCFEDataEnvelope mCfeDataEnvelope = new MCFEDataEnvelope(getCtx(), 0, get_TrxName());
			mCfeDataEnvelope.setProviderAgent(MCFEDataEnvelope.PROVIDERAGENT_Sisteco);
			mCfeDataEnvelope.saveEx();

			PO docPo = (PO) cfeDto;
			MCFEDocCFE docCfe = new MCFEDocCFE(getCtx(), 0, get_TrxName());
			docCfe.setAD_Table_ID(docPo.get_Table_ID());
			docCfe.setRecord_ID(docPo.get_ID());
			docCfe.setUY_CFE_DataEnvelope_ID(mCfeDataEnvelope.get_ID());
			try {
				docCfe.setC_DocType_ID(BigDecimal.valueOf(docPo.get_ValueAsInt("C_DocTypeTarget_ID")));
				docCfe.setDocumentNo(docPo.get_ValueAsString("documentNo"));
			} catch (Exception e2) {}
			docCfe.saveEx();


			MCFESistecoSRspCFE sistecoCfeResp = new MCFESistecoSRspCFE(getCtx(), 0, get_TrxName());
			sistecoCfeResp.setCFEStatus(String.valueOf(cfeDtoSisteco.getStatus()));
			sistecoCfeResp.setCFEDescripcion(cfeDtoSisteco.getDescripcion());
			if (sistecoCfeResp.getCFEStatus().equalsIgnoreCase("0")) {
				sistecoCfeResp.setCFETipo(BigDecimal.valueOf(cfeDtoSisteco.getTipoCFE()));
				sistecoCfeResp.setCFESerie(cfeDtoSisteco.getSerie());
				sistecoCfeResp.setCFEMro(cfeDtoSisteco.getMro());
				//sistecoCfeResp.setCFETmstFirma(cfeDtoSisteco.getTmstFirma());
				sistecoCfeResp.setCFEDigestValue(cfeDtoSisteco.getDigestValue());
				sistecoCfeResp.setCFEResolucion(String.valueOf(cfeDtoSisteco.getResolucion()));
				sistecoCfeResp.setCFEAnioResolucion(BigDecimal.valueOf(cfeDtoSisteco.getAnioResolucion()));
				sistecoCfeResp.setCFEUrlDocumentoDGI(cfeDtoSisteco.getUrlDocumentoDGI());
				sistecoCfeResp.setCFECAEID(cfeDtoSisteco.getCaeId());
				sistecoCfeResp.setCFEDNro(cfeDtoSisteco.getdNro());
				sistecoCfeResp.setCFEHNro(cfeDtoSisteco.gethNro());
				//sistecoCfeResp.setCFEFecVenc(cfeDtoSisteco.getFecVenc());
			}
			sistecoCfeResp.setUY_CFE_DocCFE_ID(docCfe.get_ID());
			sistecoCfeResp.saveEx();
			*/

			MZCFERespuestaProvider cfeRespuesta = new MZCFERespuestaProvider(getCtx(), 0, get_TrxName());
			cfeRespuesta.setAD_Table_ID(I_Z_ResguardoSocio.Table_ID);
			cfeRespuesta.setRecord_ID(this.get_ID());
			cfeRespuesta.setC_DocType_ID(this.getC_DocType_ID());
			cfeRespuesta.setDocumentNoRef(this.getDocumentNo());
			cfeRespuesta.setCFE_Status(String.valueOf(cfeDtoSisteco.getStatus()));
			cfeRespuesta.setCFE_Descripcion(cfeDtoSisteco.getDescripcion());
			if (cfeRespuesta.getCFE_Status().equalsIgnoreCase("0")){
				cfeRespuesta.setCFE_Tipo(BigDecimal.valueOf(cfeDtoSisteco.getTipoCFE()));
				cfeRespuesta.setCFE_Serie(cfeDtoSisteco.getSerie());
				cfeRespuesta.setCFE_Numero(cfeDtoSisteco.getMro());
				cfeRespuesta.setCFE_DigitoVerificador(cfeDtoSisteco.getDigestValue());
				cfeRespuesta.setCFE_Resolucion(String.valueOf(cfeDtoSisteco.getResolucion()));
				cfeRespuesta.setCFE_AnioResolucion(cfeDtoSisteco.getAnioResolucion());
				cfeRespuesta.setCFE_URL_DGI(cfeDtoSisteco.getUrlDocumentoDGI());
				cfeRespuesta.setCFE_CAE_ID(cfeDtoSisteco.getCaeId());
				cfeRespuesta.setCFE_NroInicial_CAE(cfeDtoSisteco.getdNro());
				cfeRespuesta.setCFE_NroFinal_CAE(cfeDtoSisteco.gethNro());
			}
			cfeRespuesta.saveEx();

		} catch (Exception e) {
			throw new AdempiereException(e);
		}

	}

	private void loadReferencia() {

		MDocType docType = (MDocType) this.getC_DocType();

		// Si no es Contra-Resguardo, no hago nada.
		if (!docType.getDocBaseType().equalsIgnoreCase("RGC")) {
			return;
		}

		// Obtengo resguardo asociado a este contra-resguardo
		MZResguardoSocio resguardoSocioRef = (MZResguardoSocio) this.getZ_ResguardoSocio_Ref();
		if ((resguardoSocioRef == null) || (resguardoSocioRef.get_ID() <= 0)) {
			throw new AdempiereException("CFEMessages.INFOREF_NOREF_182");
		}

		Referencia referencias = new Referencia();
		Referencia.Referencia1 referencia = new Referencia.Referencia1();
		referencias.getReferencia1().add(referencia);

		/*   1 */ referencia.setNroLinRef(1);

		///*   2    Siempre se referencia un resguardo a anular en el documento contraresguardo, no se debe indicar en este caso */
		//CfeType cfeTypeRef = getCFETypeFromInvoice(mResguardoRef);
		//if ((cfeTypeRef == CfeType.eTicket_NC || cfeTypeRef == CfeType.eTicket_NC) && cfeTypeRef != CfeType.eTicket)
//			throw new AdempiereException(CFEMessages.INFOREF_003_ASOCETICKET);
//		if ((cfeTypeRef == CfeType.eFactura_NC || cfeTypeRef == CfeType.eFactura_NC) && cfeTypeRef != CfeType.eFactura)
			//throw new AdempiereException(CFEMessages.INFOREF_003_ASOCEFACTURA);
		//try {
//			/*   3 */ referencia.setTpoDocRef(BigInteger.valueOf(Long.valueOf(CfeUtils.getCfeTypes().inverse().get(cfeTypeRef))));
		//} catch(Exception ex){
//			throw new AdempiereException(CFEMessages.INFOREF_003_PARSEERROR);
		//}

		referencia.setTpoDocRef(BigInteger.valueOf(182));

		MSequence sec = new MSequence(getCtx(), docType.getDefiniteSequence_ID(), null);
		if(sec.getPrefix() != null){
			/*   4 */ referencia.setSerie(sec.getPrefix());
		} else throw new AdempiereException("CFEMessages.INFOREF_004_NODEF");

		if (resguardoSocioRef.getDocumentNo() != null) {
			String documentNo = resguardoSocioRef.getDocumentNo();
			documentNo = documentNo.replaceAll("[^0-9]", ""); // Expresión regular para quitar todo lo que no es número
			String docno = org.apache.commons.lang.StringUtils.leftPad(String.valueOf(documentNo), 7, "0");

			/*   5 */ referencia.setNroCFERef(new BigInteger(docno));
		} else throw new AdempiereException("CFEMessages.INFOREF_005_NODEF");

		/*   6  Como se cuenta con un resguardo referenicado, no es necesario setear este campo */

		/*   7 */ referencia.setFechaCFEref(Timestamp_to_XmlGregorianCalendar_OnlyDate(resguardoSocioRef.getDateDoc(), false));
	}

}