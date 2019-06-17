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
import java.util.ArrayList;
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
import org.xpande.financial.utils.FinancialUtils;

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
			m_processMsg = dt.getName() + " - " +  "@PeriodClosed@";
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

		String action = "";

		// Me aseguro que la fecha del documento no sea mayor a hoy
		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (this.getDateDoc().after(fechaHoy)){
			this.setDateDoc(fechaHoy);
		}

		// Obtengo lineas a procesar
		List<MZPagoLin> pagoLinList = this.getSelectedLines();

		// Obtengo medios de pago a procesar
		List<MZPagoMedioPago> medioPagoList = this.getMediosPago();

		// Obtengo ordenes de pago asociadas a este documento (si existen)
		List<MZPagoOrdenPago> ordenPagoList = this.getOrdenesPagoReferenciadas();
		if (ordenPagoList.size() <= 0){
			this.setTieneOrdenPago(false);
		}

		// Validaciones del documento
		m_processMsg = this.validateDocument(pagoLinList, medioPagoList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Si no tengo monto en medios de pago, y tengo total del documento en negativo, doy vuelta el signo.
		// Esto es para recibos hechos por anticipos, que me quedaba el signo negativo en el total.
		if ((this.getTotalMediosPago() == null) || (this.getTotalMediosPago().compareTo(Env.ZERO) == 0)){
			if (this.getPayAmt().compareTo(Env.ZERO) < 0){
				this.setPayAmt(this.getPayAmt().negate());
			}
		}

		if (!this.isSOTrx()){
			// Si no tengo ordenes de pago
			if (!this.isTieneOrdenPago()){

				// Emite medios de pago cuando es un Pago y no esta referenciando ordenes de pago
				// (siempre y cuando los medios de pago no fueron emitidos previamente a mano).
				m_processMsg = this.emitirMediosPago(medioPagoList);

				// Marco medios de pago incluídos en anticipos asociados a este documennto, como entregados.
				m_processMsg = this.entregarMediosPagoAnticipos();
			}
			else{
				// Marca medios de pago que ya fueron emitidos en ordenes de pago, como entregados.
				m_processMsg = this.entregarMediosPago(medioPagoList);
			}
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}
		}
		else{
			// Para cobranzas, creo medios de pago si es necesario.
			m_processMsg = this.crearMediosPago(medioPagoList);
		}

		// Afecta ordendes de pago asociados a este pago, si este pago referencia ordenes de pago
		if (!this.isSOTrx()){
			m_processMsg = this.afectarOrdenesPago(ordenPagoList);
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}
		}

		// Afecta documentos asociadas a este pago/cobro.
		m_processMsg = this.afectarDocumentosLineas(pagoLinList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Afecta resguardos asociados a este pago
		if (!this.isSOTrx()){
			m_processMsg = this.afectarResguardos();
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}
		}

		// Obtengo importe total de anticipos afectados en este recibo
		if (!this.isAnticipo()){
			if ((this.getTotalMediosPago() != null) && (this.getTotalMediosPago().compareTo(Env.ZERO) != 0)){
				this.setTotalAnticiposAfectados();
			}
		}

		// Impactos en estado de cuenta del socio de negocio
		this.setEstadoCuenta();

		// Elimino lineas no seleccionadas
		action = " delete from z_pagolin " +
				 " where z_pago_id =" + this.get_ID() +
				 " and isselected ='N' ";
		DB.executeUpdateEx(action, get_TrxName());

		action = " delete from z_pagoresguardo " +
				" where z_pago_id =" + this.get_ID() +
				" and isselected ='N' ";
		DB.executeUpdateEx(action, get_TrxName());

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


	private void setTotalAnticiposAfectados() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select coalesce(sum(a.AmtAllocationMT),0) as total " +
					" from z_pagolin a " +
					" inner join c_doctype doc on a.c_doctype_id = doc.c_doctype_id " +
					" where a.z_pago_id =" + this.get_ID() +
					" and a.isselected ='Y' " +
					" and doc.docbasetype in ('PPA','CCA') ";


			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			if (rs.next()){
				this.setAmtAnticipo(rs.getBigDecimal("total"));
				if (this.getAmtAnticipo().compareTo(Env.ZERO) < 0){
					this.setAmtAnticipo(this.getAmtAnticipo().negate());
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
	}


	/***
	 * Cuando se trata de un cobro, debo crear los medios de pago según sea necesario.
	 * Xpande. Created by Gabriel Vila on 11/15/18
	 * @param medioPagoList
	 * @return
	 */
	private String crearMediosPago(List<MZPagoMedioPago> medioPagoList) {

		String message = null;
        String action = "";

		try{
			// No procede si es un pago
			if (!this.isSOTrx()){
				return null;
			}

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Recorre lista de medios de pago a emitir para este documento de pago
			for (MZPagoMedioPago pagoMedioPago: medioPagoList){

				/*
				if (pagoMedioPago.getDateEmitted() != null) {
					// Me aseguro fecha de emisión no mayor a hoy
					if (pagoMedioPago.getDateEmitted().before(fechaHoy)){
						pagoMedioPago.setDateEmitted(fechaHoy);
					}
				}
				*/

				MZMedioPagoItem medioPagoItem = null;

				// Si no tengo item de medio de pago, lo creo ahora
				if (pagoMedioPago.getZ_MedioPagoItem_ID() <= 0){

					medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
					medioPagoItem.setZ_MedioPago_ID(pagoMedioPago.getZ_MedioPago_ID());
					medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
					if (pagoMedioPago.getZ_MedioPagoFolio_ID() > 0){
						medioPagoItem.setZ_MedioPagoFolio_ID(pagoMedioPago.getZ_MedioPagoFolio_ID());
					}
					if (pagoMedioPago.getC_BankAccount_ID() > 0){
						medioPagoItem.setC_BankAccount_ID(pagoMedioPago.getC_BankAccount_ID());
					}

					medioPagoItem.setC_Currency_ID(pagoMedioPago.getC_Currency_ID());

					if ((pagoMedioPago.getDocumentNoRef() == null) || (pagoMedioPago.getDocumentNoRef().trim().equalsIgnoreCase(""))){

						medioPagoItem.setNroMedioPago(String.valueOf(pagoMedioPago.get_ID()));

						// Seteo numero de medio de pago en la linea de medio de pago de
						action = " update z_pagomediopago set documentnoref ='" + medioPagoItem.getNroMedioPago() + "' " +
										" where z_pagomediopago_id =" + pagoMedioPago.get_ID();
						DB.executeUpdateEx(action, get_TrxName());

					}
					else{
						medioPagoItem.setNroMedioPago(pagoMedioPago.getDocumentNoRef());
					}

					if (pagoMedioPago.getC_Bank_ID() > 0){
						medioPagoItem.setC_Bank_ID(pagoMedioPago.getC_Bank_ID());
					}

					medioPagoItem.setDateEmitted(pagoMedioPago.getDateEmitted());
					medioPagoItem.setDueDate(pagoMedioPago.getDueDate());

					medioPagoItem.setIsReceipt(true);
					medioPagoItem.setEmitido(true);
					medioPagoItem.setTotalAmt(pagoMedioPago.getTotalAmt());
					medioPagoItem.setIsOwn(false);
					medioPagoItem.setC_BPartner_ID(this.getC_BPartner_ID());
				}
				else{
					medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
				}

				// Marco medio de pago emitido
				medioPagoItem.setEmitido(true);
				medioPagoItem.setZ_Pago_ID(this.get_ID());

				// Si este medio de pago no queda en cartera de la empresa, lo marco como entregado para que no pueda ser utilizado como medio de pago
				// en un documento de pago.
				MZMedioPago medioPago = (MZMedioPago) pagoMedioPago.getZ_MedioPago();
				if (!medioPago.isCarteraCobro()){
					medioPagoItem.setEntregado(true);
				}

				medioPagoItem.saveEx();

				// Me aseguro de dejar asociado el medio de pago de este documento con el item de medio de pago.
				if (pagoMedioPago.getZ_MedioPagoItem_ID() <= 0){

                    action = " update z_pagomediopago set z_mediopagoitem_id =" + medioPagoItem.get_ID() +
                            " where z_pagomediopago_id =" + pagoMedioPago.get_ID();
                    DB.executeUpdateEx(action, get_TrxName());
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

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

        // Control de período contable
        MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());

        // Elimino asientos contables
        MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// Obtengo ordenes de pago asociadas a este documento (si existen)
		List<MZPagoOrdenPago> ordenPagoList = this.getOrdenesPagoReferenciadas();
		if (ordenPagoList.size() <= 0){
			this.setTieneOrdenPago(false);
		}

		// Si es un pago y no tiene asociado ordenes de pago
        if (!this.isSOTrx()){
			if (!this.isTieneOrdenPago()){
				// Anulo medios de pago emitidos en este pago/anticipo
				List<MZPagoMedioPago> pagoMedioPagoList = this.getMediosPago();
				for (MZPagoMedioPago pagoMedioPago: pagoMedioPagoList){
					if (pagoMedioPago.getZ_MedioPagoItem_ID() > 0){
						MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
						if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
							MZEmisionMedioPago emisionMedioPago = (MZEmisionMedioPago) medioPagoItem.getZ_EmisionMedioPago();
							emisionMedioPago.setModificable(true);
							if (!emisionMedioPago.processIt(DocAction.ACTION_Void)){
								this.m_processMsg = emisionMedioPago.getProcessMsg();
								return false;
							}
							emisionMedioPago.saveEx();
						}
						else{
							medioPagoItem.setAnulado(true);
							medioPagoItem.saveEx();
						}
					}
				}
			}
		}

		// Desafecto documentos asociados a este documento de pago/cobro
		m_processMsg = this.desafectarDocumentos(ordenPagoList);
		if (m_processMsg != null)
			return false;

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		this.setProcessed(true);
		this.setDocStatus(DOCSTATUS_Voided);
		this.setDocAction(DOCACTION_None);

		return true;

	}	//	voidIt


	/***
	 * Al anular o reactivar un documento de pago/cobro, debo desasociar los documentos que contenidos en el mismo.
	 * Xpande. Created by Gabriel Vila on 5/28/18.
	 * @return
	 * @param ordenPagoList
	 */
	private String desafectarDocumentos(List<MZPagoOrdenPago> ordenPagoList) {

		String message = null;
		String action = "";

		try{
			// Marco medios de pago como no entregados y los desasocio de esta documento de pago/cobro
			action = " update z_mediopagoitem set entregado='N', z_pago_id = null where z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Para pagos, me aseguro que si tengo medios de pago recibidos de terceros, les deje la referencia del documento de cobro.
			action = " update z_mediopagoitem set z_pago_id = ref_cobro_id where isreceipt='Y' and ref_cobro_id > 0 and z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Para pagos, desasocio ordenes de pago
			if (!this.isSOTrx()){

				if (ordenPagoList.size() > 0){
					action = " update z_ordenpago set ispaid='N', z_pago_id = null where z_pago_id =" + this.get_ID();
					DB.executeUpdateEx(action, get_TrxName());

					for (MZPagoOrdenPago pagoOrdenPago: ordenPagoList){

						MZOrdenPago ordenPago = (MZOrdenPago) pagoOrdenPago.getZ_OrdenPago();

						action = " update z_estadocuenta set referenciapago = null, z_pago_id = null " +
								" where z_ordenpago_id =" + ordenPago.get_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
				}
			}

			// Desasocio invoices cuando es un pago sin ordenes de pago asociadas o cuando es un cobro
			if ((this.isSOTrx()) || ((!this.isSOTrx()) && (!this.isTieneOrdenPago()))){
				action = " update c_invoice set ispaid ='N' where c_invoice_id in " +
						" (select c_invoice_id from z_pagolin where z_pago_id =" + this.get_ID() + ")";
				DB.executeUpdateEx(action, get_TrxName());
			}

			// Elimino Afectacion de invoices
			action = " update z_invoiceafectacion set z_pago_id = null where z_pago_id =" + this.get_ID() +
					" and z_ordenpago_id is not null ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_invoiceafectacion where z_pago_id =" + this.get_ID() +
					" and z_ordenpago_id is null ";
			DB.executeUpdateEx(action, get_TrxName());

			// Desasocio transferencias de saldo cuando es un pago sin ordenes de pago asociadas o cuando es un cobro
			if ((this.isSOTrx()) || ((!this.isSOTrx()) && (!this.isTieneOrdenPago()))){
				action = " update z_transfersaldo set ispaid ='N' where z_transfersaldo_id in " +
						" (select z_transfersaldo_id from z_pagolin where z_pago_id =" + this.get_ID() + ")";
				DB.executeUpdateEx(action, get_TrxName());
			}

			// Elimino Afectacion de Transferencias de saldo
			action = " update z_transferafectacion set z_pago_id = null where z_pago_id =" + this.get_ID() +
					 " and z_ordenpago_id is not null ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_transferafectacion where z_pago_id =" + this.get_ID() +
					" and z_ordenpago_id is null ";
			DB.executeUpdateEx(action, get_TrxName());

			// Elimino Afectacion de Anticipos
			action = " update z_pagoafectacion set ref_pago_id = null where ref_pago_id =" + this.get_ID() +
					" and z_ordenpago_id is not null ";
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_pagoafectacion where ref_pago_id =" + this.get_ID() +
					" and z_ordenpago_id is null ";
			DB.executeUpdateEx(action, get_TrxName());

			List<MZPagoLin> pagoLinList = this.getSelectedLines();
			for (MZPagoLin pagoLin: pagoLinList){

				if (pagoLin.getC_Invoice_ID() > 0){

					// Desafecto estado de cuenta de esta invoice cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){
						if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
							action = " update z_estadocuenta set referenciapago = null, z_pago_id = null " +
									" where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
						}
						else{
							action = " update z_estadocuenta set referenciapago = null, z_pago_id = null " +
									" where c_invoice_id =" + pagoLin.getC_Invoice_ID();
						}
						DB.executeUpdateEx(action, get_TrxName());
					}
					else{
						MZOrdenPago ordenPago = (MZOrdenPago) pagoLin.getZ_OrdenPago();
						if ((ordenPago != null) && (ordenPago.get_ID() > 0)){
							if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
								action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + ordenPago.getDocumentNo() + "', " +
										" z_ordenpago_id =" + ordenPago.get_ID() +
										" where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
							}
							else{
								action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + ordenPago.getDocumentNo() + "', " +
										" z_ordenpago_id =" + ordenPago.get_ID() +
										" where c_invoice_id =" + pagoLin.getC_Invoice_ID();
							}
							DB.executeUpdateEx(action, get_TrxName());
						}
					}
				}
				else if (pagoLin.getZ_TransferSaldo_ID() > 0){

					// Desafecto estado de cuenta de esta transferencia de saldo cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){

						action = " update z_estadocuenta set referenciapago = null, z_pago_id = null " +
								" where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
					else{
						MZOrdenPago ordenPago = (MZOrdenPago) pagoLin.getZ_OrdenPago();
						if ((ordenPago != null) && (ordenPago.get_ID() > 0)){

							action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + ordenPago.getDocumentNo() + "', " +
									" z_ordenpago_id =" + ordenPago.get_ID() +
									" where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
							DB.executeUpdateEx(action, get_TrxName());
						}
					}

				}
				else if (pagoLin.getRef_Pago_ID() > 0){

					// Desafecto estado de cuenta de este Anticipo cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){

						action = " update z_estadocuenta set referenciapago = null, ref_pago_id = null " +
								" where z_pago_id =" + pagoLin.getRef_Pago_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
					else{
						MZOrdenPago ordenPago = (MZOrdenPago) pagoLin.getZ_OrdenPago();
						if ((ordenPago != null) && (ordenPago.get_ID() > 0)){

							action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + ordenPago.getDocumentNo() + "', " +
									" z_ordenpago_id =" + ordenPago.get_ID() +
									" where z_pago_id =" + pagoLin.getRef_Pago_ID();
							DB.executeUpdateEx(action, get_TrxName());
						}
					}
				}
			}

			// Desasocio resguardos
			action = " update z_resguardosocio set ispaid='N', z_pago_id = null where z_pago_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			List<MZPagoResguardo> pagoResguardoList = this.getResguardos();
			for (MZPagoResguardo pagoResguardo: pagoResguardoList){

				if (!this.isTieneOrdenPago()){
					action = " update z_estadocuenta set referenciapago = null, z_pago_id = null " +
							 " where z_resguardosocio_id =" + pagoResguardo.getZ_ResguardoSocio_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
				else{
					MZOrdenPago ordenPago = (MZOrdenPago) pagoResguardo.getZ_OrdenPago();
					if ((ordenPago != null) && (ordenPago.get_ID() > 0)){
						action = " update z_estadocuenta set referenciapago ='ORDEN PAGO " + ordenPago.getDocumentNo() + "', " +
								" z_ordenpago_id =" + ordenPago.get_ID() +
								" where z_resguardosocio_id =" + pagoResguardo.getZ_ResguardoSocio_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
				}
			}

			// Desasocio info en estado de cuenta para este documento de pago/cobro
			action = " delete from z_estadocuenta where z_pago_id =" + this.get_ID() +
					" and ad_table_id =" + this.get_Table_ID();
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

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

        // Control de período contable
        MPeriod.testPeriodOpen(getCtx(), this.getDateDoc(), this.getC_DocType_ID(), this.getAD_Org_ID());

        // Elimino asientos contables
        MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// Obtengo ordenes de pago asociadas a este documento (si existen)
		List<MZPagoOrdenPago> ordenPagoList = this.getOrdenesPagoReferenciadas();
		if (ordenPagoList.size() <= 0){
			this.setTieneOrdenPago(false);
		}

		// Desafecto documentos asociados a este documento de pago/cobro
		m_processMsg = this.desafectarDocumentos(ordenPagoList);
		if (m_processMsg != null)
			return false;

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
				this.deleteDocuments(false);
			}

			HashMap<Integer, Integer> hashCurrency = new HashMap<Integer, Integer>();

			// Obtengo invoices a considerar y genero lineas
			message = this.getInvoices(hashCurrency);
			if (message != null){
				return message;
			}

			// Obtengo transferencias de saldos a considerar y genero lineas
			message = this.getTransferSaldos(hashCurrency);
			if (message != null){
				return message;
			}

			// Obtengo anticipos a considerar y genero lineas
			message = this.getAnticipos(hashCurrency);
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
				this.updateRates(false);
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
	 * @param onlyMediosPago : true si solo elimina medios de pago, false si elimina solamente facturas y resguardos.
	 */
	private void deleteDocuments(boolean onlyMediosPago) {

		String action = "";

		try{

			if (onlyMediosPago){

				action = " delete from z_pagomediopago where z_pago_id =" + this.get_ID();
				DB.executeUpdateEx(action, get_TrxName());

				return;
			}

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
				whereClause += " AND hdr.DateInvoiced <='" + this.getDateEmittedTo() + "' ";
			}
			if (this.getDueDateFrom() != null){
				whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) >='" + this.getDueDateFrom() + "' ";
			}
			if (this.getDueDateTo() != null){
				whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) <='" + this.getDueDateTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = "";
			if (this.getC_Currency_ID_To() > 0){
				filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID_To();
			}
			whereClause += filtroMonedas;

			// Query
			sql = " select hdr.c_bpartner_id, hdr.c_invoice_id, hdr.c_doctypetarget_id, (coalesce(hdr.documentserie,'') || hdr.documentno) as documentno, " +
					" hdr.dateinvoiced, hdr.c_currency_id, coalesce(ips.dueamt,hdr.grandtotal) as grandtotal, ips.c_invoicepayschedule_id, " +
					" iop.amtopen, " +
					" coalesce(hdr.isindispute,'N') as isindispute, doc.docbasetype, coalesce(hdr.TieneDtosNC,'N') as TieneDtosNC, " +
					" coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced)::timestamp without time zone  as duedate " +
					" from c_invoice hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctypetarget_id = doc.c_doctype_id " +
					" inner join zv_financial_invopen iop on hdr.c_invoice_id = iop.c_invoice_id " +
					" left outer join c_invoicepayschedule ips on hdr.c_invoice_id = ips.c_invoice_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.ad_org_id =" + this.getAD_Org_ID() +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.issotrx='" + ((this.isSOTrx()) ? "Y":"N") + "' " +
					" and hdr.docstatus='CO' " +
					" and iop.amtopen > 0 " +
					" and hdr.c_invoice_id not in (select c_invoice_id from z_pagolin " +
					" where c_invoice_id is not null " +
					" and z_pago_id =" + this.get_ID() + ") " +
					" and hdr.c_invoice_id not in (select a.c_invoice_id from z_ordenpagolin a " +
					" inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
					" where a.c_invoice_id is not null and b.docstatus='CO') " +
					whereClause +
					" order by hdr.dateinvoiced ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				boolean vencimientoOK = true;
				int cInvoicePayScheduleID = rs.getInt("c_invoicepayschedule_id");
				if (cInvoicePayScheduleID > 0){
					// Debo verificar que tenga saldo abierta en la vista de saldos abiertos por vencimiento

				}

				// Si es un vencimiento de factura y no tiene saldo abierto, no proceso esta linea.
				if (!vencimientoOK){
					continue;
				}

				boolean esFactura = true;
				if ((rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_APCredit))
				 || (rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_ARCredit))){
					esFactura = false;
				}

				BigDecimal amtDocument = rs.getBigDecimal("grandtotal");
				BigDecimal amtOpen = rs.getBigDecimal("amtopen");
				if (!esFactura){
					amtDocument = amtDocument.negate();
					amtOpen = amtOpen.negate();
				}

				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtDocument(amtDocument);
				pagoLin.setAmtOpen(amtOpen);
				pagoLin.setAmtAllocation(amtOpen);
				pagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoLin.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
				pagoLin.setDateDoc(rs.getTimestamp("dateinvoiced"));
				pagoLin.setDueDateDoc(rs.getTimestamp("duedate"));
				pagoLin.setDocumentNoRef(rs.getString("documentno"));
				pagoLin.setEstadoAprobacion("APROBADO");
				pagoLin.setC_Invoice_ID(rs.getInt("c_invoice_id"));
				if (cInvoicePayScheduleID > 0){
					pagoLin.setC_InvoicePaySchedule_ID(cInvoicePayScheduleID);
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
	 * Obtiene transferencias de saldos a considerar y genera lineas por cada uno de ellos.
	 * Xpande. Created by Gabriel Vila on 3/14/19.
	 * @return
	 */
	private String getTransferSaldos(HashMap<Integer, Integer> hashCurrency){

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
				whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = "";
			if (this.getC_Currency_ID_To() > 0){
				filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID_To();
			}
			whereClause += filtroMonedas;

			// Query
			sql = " select hdr.c_bpartner_id, hdr.z_transfersaldo_id, hdr.c_invoice_id, hdr.c_doctype_id, hdr.documentno, " +
					" hdr.datedoc, hdr.c_currency_id, hdr.grandtotal, " +
					" iop.amtopen, doc.docbasetype " +
					" from z_transfersaldo hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
					" inner join zv_financial_tsopen iop on hdr.z_transfersaldo_id = iop.z_transfersaldo_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.ad_org_id =" + this.getAD_Org_ID() +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.issotrx='" + ((this.isSOTrx()) ? "Y":"N") + "' " +
					" and hdr.docstatus='CO' " +
					" and iop.amtopen > 0 " +
					" and hdr.z_transfersaldo_id not in (select z_transfersaldo_id from z_pagolin " +
					" where z_transfersaldo_id is not null " +
					" and z_pago_id =" + this.get_ID() + ") " +
					" and hdr.z_transfersaldo_id not in (select a.z_transfersaldo_id from z_ordenpagolin a " +
					" inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
					" where a.z_transfersaldo_id is not null and b.docstatus='CO') " +
					whereClause +
					" order by hdr.datedoc ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Obtengo fecha de vencimiento de la invoice referenciada por este documento de transferencia de saldo
				// Fecha de Vencimiento de esta invoice, directamente de termino de pago
				sql = " select paymentTermDueDate(C_PaymentTerm_ID, DateInvoiced) as DueDate " +
						" from c_invoice " +
						" where c_invoice_id =" + rs.getInt("c_invoice_id");
				Timestamp dueDate = DB.getSQLValueTSEx(get_TrxName(), sql);
				if (dueDate == null){
					dueDate = rs.getTimestamp("datedoc");
				}

				BigDecimal amtDocument = rs.getBigDecimal("grandtotal");
				BigDecimal amtOpen = rs.getBigDecimal("amtopen");

				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtDocument(amtDocument);
				pagoLin.setAmtOpen(amtOpen);
				pagoLin.setAmtAllocation(amtOpen);
				pagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
				pagoLin.setDateDoc(rs.getTimestamp("datedoc"));
				pagoLin.setDueDateDoc(dueDate);
				pagoLin.setDocumentNoRef(rs.getString("documentno"));
				pagoLin.setEstadoAprobacion("APROBADO");
				pagoLin.setZ_TransferSaldo_ID(rs.getInt("z_transfersaldo_id"));
				pagoLin.setResguardoEmitido(false);

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
	 * Obtiene Anticipos a considerar y genera lineas por cada uno de ellos.
	 * Xpande. Created by Gabriel Vila on 8/16/17.
	 * @return
	 */
	private String getAnticipos(HashMap<Integer, Integer> hashCurrency){

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
				whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
			}
			if (this.getDueDateFrom() != null){
				whereClause += " AND hdr.DateDoc >='" + this.getDueDateFrom() + "' ";
			}
			if (this.getDueDateTo() != null){
				whereClause += " AND hdr.DateDoc <='" + this.getDueDateTo() + "' ";
			}

			// Filtros de monedas
			String filtroMonedas = "";
			if (this.getC_Currency_ID_To() > 0){
				filtroMonedas = " AND hdr.c_currency_id =" + this.getC_Currency_ID_To();
			}
			whereClause += filtroMonedas;

			// Query
			sql = " select hdr.c_bpartner_id, hdr.z_pago_id, hdr.c_doctype_id, hdr.documentno, " +
					" hdr.datedoc, hdr.c_currency_id, hdr.payamt, iop.amtopen, " +
					" doc.docbasetype " +
					" from z_pago hdr " +
					" inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
					" inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
					" inner join zv_financial_pagoopen iop on hdr.z_pago_id = iop.z_pago_id " +
					" where hdr.ad_client_id =" + this.getAD_Client_ID() +
					" and hdr.ad_org_id =" + this.getAD_Org_ID() +
					" and hdr.anticipo ='Y' " +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.issotrx='" + ((this.isSOTrx()) ? "Y":"N") + "' " +
					" and hdr.docstatus='CO' " +
					" and iop.amtopen > 0 " +
					" and hdr.z_pago_id not in (select l.z_pago_id from z_pagolin l " +
					" where l.z_pago_id is not null " +
					" and l.z_pago_id =" + this.get_ID() + ") " +
					" and hdr.z_pago_id not in (select a.z_pago_id from z_ordenpagolin a " +
					" inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
					" where a.z_pago_id is not null and b.docstatus='CO') " +
					whereClause +
					" order by hdr.datedoc ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				BigDecimal amtDocument = rs.getBigDecimal("payamt");
				BigDecimal amtOpen = rs.getBigDecimal("amtopen");

				amtDocument = amtDocument.negate();
				amtOpen = amtOpen.negate();

				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtDocument(amtDocument);
				pagoLin.setAmtOpen(amtOpen);
				pagoLin.setAmtAllocation(amtOpen);
				pagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
				pagoLin.setDateDoc(rs.getTimestamp("datedoc"));
				pagoLin.setDueDateDoc(rs.getTimestamp("datedoc"));
				pagoLin.setDocumentNoRef(rs.getString("documentno"));
				pagoLin.setEstadoAprobacion("APROBADO");

				pagoLin.setRef_Pago_ID(rs.getInt("z_pago_id"));

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
	 * Obtiene y retorna resguardos asociados a este modelo en un documento de pago.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoResguardo> getResguardos(){

		String whereClause = X_Z_PagoResguardo.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoResguardo> lines = new Query(getCtx(), I_Z_PagoResguardo.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna resguardos recibiddos del cliente de un documento de cobro.
	 * Xpande. Created by Gabriel Vila on 11/19/18
	 * @return
	 */
	public List<MZPagoResgRecibido> getResguardosRecibidos(){

		String whereClause = X_Z_PagoResgRecibido.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoResgRecibido> lines = new Query(getCtx(), I_Z_PagoResgRecibido.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna ordenes de pago asociadas a este modelo.
	 * Xpande. Created by Gabriel Vila on 1/24/18.
	 * @return
	 */
	public List<MZPagoOrdenPago> getOrdenesPagoReferenciadas(){

		String whereClause = X_Z_PagoOrdenPago.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID();

		List<MZPagoOrdenPago> lines = new Query(getCtx(), I_Z_PagoOrdenPago.Table_Name, whereClause, get_TrxName()).list();

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
	 * Obtiene medios de pago de esta documento para un determinado item de medio de pago.
	 * Xpande. Created by Gabriel Vila on 5/10/19.
	 * @return
	 */
	public MZPagoMedioPago getMedioPagoByItem(int zMedioPagoItemID) {

		String whereClause = X_Z_PagoMedioPago.COLUMNNAME_Z_Pago_ID + " =" + this.get_ID() +
				" AND " + X_Z_PagoMedioPago.COLUMNNAME_Z_MedioPagoItem_ID + " =" + zMedioPagoItemID;

		MZPagoMedioPago model = new Query(getCtx(), I_Z_PagoMedioPago.Table_Name, whereClause, get_TrxName()).first();

		return model;
	}


	/***
	 * Obtiene y retorna lista con modelos de medios de pago asociados a anticipos incluídos en este documento.
	 * Xpande. Created by Gabriel Vila on 5/2/19.
	 * @return
	 */
	public List<MZPagoMedioPago> getMediosPagoAnticipos(){

		List<MZPagoMedioPago> lines = new ArrayList<MZPagoMedioPago>();

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select ref_pago_id " +
					" from z_pagolin " +
					" where z_pago_id =" + this.get_ID() +
					" and ref_pago_id is not null ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				int idAnticipo = rs.getInt("ref_pago_id");
				if (idAnticipo > 0){
					MZPago anticipo =  new MZPago(getCtx(), idAnticipo, get_TrxName());
					if ((anticipo != null) && (anticipo.get_ID() > 0)){
						List<MZPagoMedioPago> linesAux = anticipo.getMediosPago();
						for (MZPagoMedioPago medioPago: linesAux){
							lines.add(medioPago);
						}
					}
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

			// Cuando no es un documento de anticipo
			if (!this.isAnticipo()){
				// Si tengo monto de medios de pago
				if ((this.getTotalMediosPago() != null) && (this.getTotalMediosPago().compareTo(Env.ZERO) != 0)){
					// Verifico que la diferencia entre total del documento y medios a pagar este dentro de la tolerancia permitida
					BigDecimal diferencia = this.getPayAmt().subtract(this.getTotalMediosPago());
					if (diferencia.compareTo(Env.ZERO) != 0){
						return "Hay diferencias entre el Total del Documento y el Total de Medios de Pago.";
					}
				}

				// Verifico que tenga lineas seleccionadas
				if (pagoLinList.size() <= 0){
					return "No hay Documentos seleccionados para afectar.";
				}

				// Valido que los documentos sigan con monto abierto sin cambios
				for (MZPagoLin pagoLin: pagoLinList){
					if (pagoLin.isSelected()){
						if (pagoLin.getC_Invoice_ID() > 0){

							BigDecimal amtOpen = FinancialUtils.getInvoiceAmtOpen(getCtx(), pagoLin.getC_Invoice_ID(), get_TrxName());
							BigDecimal amtPagoLin = pagoLin.getAmtAllocation();

							// Para documentos que restan, me aseguro de considerar monto a pagar sin signo.
							if (amtPagoLin.compareTo(Env.ZERO) < 0) amtPagoLin = amtPagoLin.negate();

							if (amtOpen.compareTo(amtPagoLin) != 0){
								return "El monto pendiente del comprobante " + pagoLin.getDocumentNoRef() + " ha cambiado.\n" +
										"Por favor elimine la linea del comprobante y vuelva a cargarla para refrescar información.";
							}
						}
					}
				}

				// Valido que los medios de pago sigan disponibles para utilizar en un recibo
				for (MZPagoMedioPago pagoMedioPago: medioPagoList){
					if (pagoMedioPago.getZ_MedioPagoItem_ID() > 0){
						MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
						if (medioPagoItem.isEntregado()){
							return "El medio de pago número " + medioPagoItem.getNroMedioPago() + " ya fue entregado en otro comprobante.\n" +
									"Por favor utilice otro medio de pago en este documento.";
						}
					}
				}
			}

			/*
			// Verifico que tenga medios de pago
			if (medioPagoList.size() <= 0){
				return "Debe indicar al menos un medio de pago.";
			}
			*/

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
				whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
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
					" and hdr.ispaid ='N' " +
					" and hdr.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and hdr.docstatus='CO' " +
					" and hdr.z_resguardosocio_ref_id is null " +
					" and doc.docbasetype='RGU' " +
					" and hdr.z_resguardosocio_id not in (select z_resguardosocio_id from z_pagoresguardo " +
					" where z_resguardosocio_id is not null " +
					" and z_pago_id =" + this.get_ID() + ") "  +
					" and hdr.z_resguardosocio_id not in (select a.z_resguardosocio_id from z_ordenpagolin a " +
					" inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
					" where a.z_resguardosocio_id is not null and b.docstatus='CO') " +
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
	 * Obtiene y carga ordenes de pago pendientes de
	 * @return
	 */
	public String getOrdenesPago() {

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			// Query
			sql = " select z_ordenpago_id " +
					" from z_ordenpago " +
					" where ad_client_id =" + this.getAD_Client_ID() +
					" and ad_org_id =" + this.getAD_Org_ID() +
					" and c_bpartner_id =" + this.getC_BPartner_ID() +
					" and c_currency_id =" + this.getC_Currency_ID() +
					" and docstatus='CO' " +
					" and ispaid='N' " +
					" and z_ordenpago_id not in (select z_ordenpago_id from z_pagoordenpago where z_pago_id =" + this.get_ID() + ") " +
					" order by datedoc ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Instancia orden de pago y carga info de la misma en este documento de pago.
				MZOrdenPago ordenPago = new MZOrdenPago(getCtx(), rs.getInt("z_ordenpago_id"), get_TrxName());
				this.setFromOrdenPago(ordenPago);

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
	 * Setea información desde Orden de Pago recibida.
	 * Xpande. Created by Gabriel Vila on 6/7/19.
	 * @param ordenPago
	 */
	public void setFromOrdenPago(MZOrdenPago ordenPago){

		try{

			MZPagoOrdenPago pagoOrdenPago = new MZPagoOrdenPago(getCtx(), 0, get_TrxName());
			pagoOrdenPago.setZ_Pago_ID(this.get_ID());
			pagoOrdenPago.setZ_OrdenPago_ID(ordenPago.get_ID());
			pagoOrdenPago.setC_Currency_ID(this.getC_Currency_ID());
			pagoOrdenPago.setDateTrx(ordenPago.getDateDoc());
			pagoOrdenPago.setTotalAmt(ordenPago.getTotalAmt());
			pagoOrdenPago.saveEx();

			// Cargo documentos de invoices afectados por la orden
			List<MZOrdenPagoLin> invList = ordenPago.getInvoices();
			for (MZOrdenPagoLin ordenPagoLin: invList){
				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocationMT());
				pagoLin.setAmtAllocation(ordenPagoLin.getAmtAllocation());
				pagoLin.setMultiplyRate(ordenPagoLin.getMultiplyRate());
				pagoLin.setAmtDocument(ordenPagoLin.getAmtDocument());
				pagoLin.setAmtOpen(ordenPagoLin.getAmtOpen());
				pagoLin.setC_Currency_ID(ordenPagoLin.getC_Currency_ID());
				pagoLin.setC_DocType_ID(ordenPagoLin.getC_DocType_ID());
				pagoLin.setC_Invoice_ID(ordenPagoLin.getC_Invoice_ID());
				pagoLin.setDateDoc(ordenPagoLin.getDateDoc());
				pagoLin.setDocumentNoRef(ordenPagoLin.getDocumentNoRef());
				pagoLin.setDueDateDoc(ordenPagoLin.getDueDateDoc());
				pagoLin.setEstadoAprobacion(X_Z_PagoLin.ESTADOAPROBACION_APROBADO);
				pagoLin.setIsSelected(true);
				pagoLin.setZ_OrdenPago_ID(ordenPago.get_ID());
				if (ordenPagoLin.getC_InvoicePaySchedule_ID() > 0){
					pagoLin.setC_InvoicePaySchedule_ID(ordenPagoLin.getC_InvoicePaySchedule_ID());
				}
				pagoLin.saveEx();
			}

			// Cargo documentos de transferencias de saldos afectados por la orden
			List<MZOrdenPagoLin> transferList = ordenPago.getTransferSaldos();
			for (MZOrdenPagoLin ordenPagoLin: transferList){
				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocationMT());
				pagoLin.setAmtAllocation(ordenPagoLin.getAmtAllocation());
				pagoLin.setMultiplyRate(ordenPagoLin.getMultiplyRate());
				pagoLin.setAmtDocument(ordenPagoLin.getAmtDocument());
				pagoLin.setAmtOpen(ordenPagoLin.getAmtOpen());
				pagoLin.setC_Currency_ID(ordenPagoLin.getC_Currency_ID());
				pagoLin.setC_DocType_ID(ordenPagoLin.getC_DocType_ID());
				pagoLin.setZ_TransferSaldo_ID(ordenPagoLin.getZ_TransferSaldo_ID());
				pagoLin.setDateDoc(ordenPagoLin.getDateDoc());
				pagoLin.setDocumentNoRef(ordenPagoLin.getDocumentNoRef());
				pagoLin.setDueDateDoc(ordenPagoLin.getDueDateDoc());
				pagoLin.setEstadoAprobacion(X_Z_PagoLin.ESTADOAPROBACION_APROBADO);
				pagoLin.setIsSelected(true);
				pagoLin.setZ_OrdenPago_ID(ordenPago.get_ID());
				pagoLin.saveEx();
			}

			// Cargo documentos de anticipos afectados por la orden
			List<MZOrdenPagoLin> antList = ordenPago.getAnticipos();
			for (MZOrdenPagoLin ordenPagoLin: antList){
				MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
				pagoLin.setZ_Pago_ID(this.get_ID());
				pagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocationMT());
				pagoLin.setAmtAllocation(ordenPagoLin.getAmtAllocation());
				pagoLin.setMultiplyRate(ordenPagoLin.getMultiplyRate());
				pagoLin.setAmtDocument(ordenPagoLin.getAmtDocument());
				pagoLin.setAmtOpen(ordenPagoLin.getAmtOpen());
				pagoLin.setC_Currency_ID(ordenPagoLin.getC_Currency_ID());
				pagoLin.setC_DocType_ID(ordenPagoLin.getC_DocType_ID());
				pagoLin.setRef_Pago_ID(ordenPagoLin.getZ_Pago_ID());
				pagoLin.setDateDoc(ordenPagoLin.getDateDoc());
				pagoLin.setDocumentNoRef(ordenPagoLin.getDocumentNoRef());
				pagoLin.setDueDateDoc(ordenPagoLin.getDueDateDoc());
				pagoLin.setEstadoAprobacion(X_Z_PagoLin.ESTADOAPROBACION_APROBADO);
				pagoLin.setIsSelected(true);
				pagoLin.setZ_OrdenPago_ID(ordenPago.get_ID());
				pagoLin.saveEx();
			}

			// Cargo medios de pago afectados por la orden y ya emitidos
			List<MZOrdenPagoMedio> medioList = ordenPago.getMediosPago();
			for (MZOrdenPagoMedio ordenPagoMedio: medioList){
				MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
				pagoMedioPago.setZ_Pago_ID(this.get_ID());
				pagoMedioPago.setZ_OrdenPago_ID(ordenPago.get_ID());
				pagoMedioPago.setZ_MedioPago_ID(ordenPagoMedio.getZ_MedioPago_ID());
				if (ordenPagoMedio.getZ_MedioPagoFolio_ID() > 0){
					pagoMedioPago.setZ_MedioPagoFolio_ID(ordenPagoMedio.getZ_MedioPagoFolio_ID());
				}
				if (ordenPagoMedio.getZ_MedioPagoItem_ID() > 0){
					pagoMedioPago.setZ_MedioPagoItem_ID(ordenPagoMedio.getZ_MedioPagoItem_ID());
				}
				pagoMedioPago.setTotalAmtMT(ordenPagoMedio.getTotalAmt());
				pagoMedioPago.setTotalAmt(ordenPagoMedio.getTotalAmt());
				pagoMedioPago.setC_BankAccount_ID(ordenPagoMedio.getC_BankAccount_ID());
				pagoMedioPago.setC_Currency_ID(ordenPagoMedio.getC_Currency_ID());
				pagoMedioPago.setDateEmitted(ordenPagoMedio.getDateEmitted());
				pagoMedioPago.setDocumentNoRef(ordenPagoMedio.getDocumentNoRef());
				pagoMedioPago.setDueDate(ordenPagoMedio.getDueDate());
				pagoMedioPago.setEmisionManual(false);
				pagoMedioPago.setMultiplyRate(Env.ONE);
				pagoMedioPago.setTieneCaja(false);
				pagoMedioPago.setTieneCtaBco(true);
				pagoMedioPago.setTieneFecEmi(true);
				pagoMedioPago.setTieneFecVenc(true);
				pagoMedioPago.setTieneFolio(true);
				pagoMedioPago.saveEx();
			}

			// Cargo resguardos afectados por la orden
			List<MZOrdenPagoLin> resgList = ordenPago.getResguardos();
			for (MZOrdenPagoLin ordenPagoLin: resgList){
				MZPagoResguardo pagoResguardo = new MZPagoResguardo(getCtx(), 0, get_TrxName());
				pagoResguardo.setZ_Pago_ID(this.get_ID());
				pagoResguardo.setZ_OrdenPago_ID(ordenPago.get_ID());
				pagoResguardo.setZ_ResguardoSocio_ID(ordenPagoLin.getZ_ResguardoSocio_ID());
				pagoResguardo.setDateTrx(ordenPagoLin.getDateDoc());
				pagoResguardo.setC_DocType_ID(ordenPagoLin.getC_DocType_ID());
				pagoResguardo.setAmtAllocation(ordenPagoLin.getAmtAllocation().negate());
				pagoResguardo.setAmtAllocationMT(ordenPagoLin.getAmtAllocationMT().negate());
				pagoResguardo.setAmtDocument(ordenPagoLin.getAmtDocument().negate());
				pagoResguardo.setC_Currency_ID(ordenPagoLin.getC_Currency_ID());
				pagoResguardo.setDocumentNoRef(ordenPagoLin.getDocumentNoRef());
				pagoResguardo.setIsSelected(true);
				pagoResguardo.setMultiplyRate(ordenPagoLin.getMultiplyRate());
				pagoResguardo.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Actualiza tasa de cambio y como consecuencia monto a pagar en moneda de transacción, en lineas y resguardos asociados
	 * a este documento de pago/cobro.
	 * Xpande. Created by Gabriel Vila on 1/26/18.
	 * @param onlyMediosPago : true si solo proceso medios de pago, false si proceso solamente facturas y resguardos.
	 */
	public void updateRates(boolean onlyMediosPago) {

		try{

			HashMap<Integer, MZPagoMoneda> hashCurrency = new HashMap<Integer, MZPagoMoneda>();

			// Si solo tengo que actualizar Tasa de Cabmio y montos en lineas de medios de pago
			if (onlyMediosPago){

				// Medios de Pago
				List<MZPagoMedioPago> pagoMedioPagoList = this.getMediosPago();
				for (MZPagoMedioPago pagoMedioPago: pagoMedioPagoList){

					MZPagoMoneda pagoMoneda = null;
					if (hashCurrency.containsKey(pagoMedioPago.getC_Currency_ID())){
						pagoMoneda = hashCurrency.get(pagoMedioPago.getC_Currency_ID());
					}
					else{
						pagoMoneda = MZPagoMoneda.getByCurrency(getCtx(), this.get_ID(), pagoMedioPago.getC_Currency_ID(), get_TrxName());
						hashCurrency.put(pagoMedioPago.getC_Currency_ID(), pagoMoneda);
					}
					if ((pagoMoneda != null) && (pagoMoneda.get_ID() > 0)){
						pagoMedioPago.setMultiplyRate(pagoMoneda.getMultiplyRate());
						pagoMedioPago.saveEx();
					}
				}

				return;
			}


			// Lineas
			List<MZPagoLin> pagoLinList = this.getLines();
			for (MZPagoLin pagoLin: pagoLinList){

				MZPagoMoneda pagoMoneda = null;
				if (hashCurrency.containsKey(pagoLin.getC_Currency_ID())){
					pagoMoneda = hashCurrency.get(pagoLin.getC_Currency_ID());
				}
				else{
					pagoMoneda = MZPagoMoneda.getByCurrency(getCtx(), this.get_ID(), pagoLin.getC_Currency_ID(), get_TrxName());
					hashCurrency.put(pagoLin.getC_Currency_ID(), pagoMoneda);
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
					hashCurrency.put(pagoResguardo.getC_Currency_ID(), pagoMoneda);
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

			// Obtengo suma de montos de resguardos segun sean entregados o recibidos
			BigDecimal sumResguardos = Env.ZERO;
			if (!this.isSOTrx()){
				sql = " select sum(coalesce(amtallocationmt,0)) as total from z_pagoresguardo " +
						" where z_pago_id =" + this.get_ID() +
						" and isselected ='Y'";
				sumResguardos = DB.getSQLValueBDEx(get_TrxName(), sql);
				if (sumResguardos == null) sumResguardos = Env.ZERO;
			}
			else{
				sql = " select sum(coalesce(amtallocationmt,0)) as total from z_pagoresgrecibido " +
						" where z_pago_id =" + this.get_ID();
				sumResguardos = DB.getSQLValueBDEx(get_TrxName(), sql);
				if (sumResguardos == null) sumResguardos = Env.ZERO;
			}

			// Obtengo suma de montos de medios de pago
			sql = " select sum(coalesce(totalamtmt,0)) as total from z_pagomediopago " +
					" where z_pago_id =" + this.get_ID();
			BigDecimal sumMedios = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (sumMedios == null) sumMedios = Env.ZERO;

			// Actualizo segun este documento sea o no un anticipo
			if (!this.isAnticipo()){
				action = " update z_pago set payamt =" + sumLines + ", " +
						" totalmediospago =" + sumMedios.add(sumResguardos) +
						" where z_pago_id =" + this.get_ID();
			}
			else{
				action = " update z_pago set payamt =" + sumMedios + ", " +
						" totalmediospago =" + sumMedios +
						" where z_pago_id =" + this.get_ID();
			}

			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Proceso que emite los medios de pago para un documento de pago.
	 * Xpande. Created by Gabriel Vila on 3/8/18.
	 * @param medioPagoList
	 * @return
	 */
	private String emitirMediosPago(List<MZPagoMedioPago> medioPagoList) {

		String message = null;
		String action = "";

		try{
			// No procede si es un cobro
			if (this.isSOTrx()){
				return null;
			}

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Recorre lista de medios de pago a emitir para este documento de pago
			for (MZPagoMedioPago pagoMedioPago: medioPagoList){

				// Por las dudas me aseguro que no venga un medio de pago que ya fue emitido en una orden de pago
				if (pagoMedioPago.getZ_OrdenPago_ID() > 0){
					continue;
				}

				// Instancio modelo de medio de pago, si es que tengo.
				MZMedioPago medioPago = (MZMedioPago) pagoMedioPago.getZ_MedioPago();
				if ((medioPago == null) || (medioPago.get_ID() <= 0)){
					continue;
				}

				// Si este medio de pago no se emite por definición
				if (!medioPago.isTieneEmision()){
					continue;
				}

				if (pagoMedioPago.getDateEmitted() == null){
					pagoMedioPago.setDateEmitted(this.getDateDoc());
				}

				if (pagoMedioPago.getDueDate() == null){
					pagoMedioPago.setDueDate(pagoMedioPago.getDateEmitted());
				}

				// Si no tengo item de medio de pago, y en caso de que este medio de pago requiera folio,
				// entonces obtengo el siguiente disponible del folio
				MZMedioPagoItem medioPagoItem = null;
				if (pagoMedioPago.getZ_MedioPagoItem_ID() <= 0){

					if (medioPago.isTieneFolio()){
						if (pagoMedioPago.getZ_MedioPagoFolio_ID() <= 0){
							return "Medio de pago no tiene Libreta asociada.";
						}
						medioPagoItem = ((MZMedioPagoFolio) pagoMedioPago.getZ_MedioPagoFolio()).getCurrentNext();
						if ((medioPagoItem == null) || (medioPagoItem.get_ID() <= 0)){
							return "Libreta no tiene medios de pago disponibles para utilizar.";
						}
						medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
						pagoMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
					}
					else{
						// Creo el item de medio de pago sin folio asociado
						medioPagoItem = new MZMedioPagoItem(getCtx(), 0, get_TrxName());
						medioPagoItem.setZ_MedioPago_ID(pagoMedioPago.getZ_MedioPago_ID());
						medioPagoItem.setAD_Org_ID(this.getAD_Org_ID());
						if (pagoMedioPago.getC_BankAccount_ID() > 0){
							medioPagoItem.setC_BankAccount_ID(pagoMedioPago.getC_BankAccount_ID());
						}
						if (pagoMedioPago.getC_CashBook_ID() > 0){
							medioPagoItem.setC_CashBook_ID(pagoMedioPago.getC_CashBook_ID());
						}

						medioPagoItem.setC_Currency_ID(pagoMedioPago.getC_Currency_ID());

						if ((pagoMedioPago.getDocumentNoRef() == null) || (pagoMedioPago.getDocumentNoRef().trim().equalsIgnoreCase(""))){

							medioPagoItem.setNroMedioPago(String.valueOf(pagoMedioPago.get_ID()));

							// Seteo numero de medio de pago en la linea de medio de pago
							action = " update z_pagomediopago set documentnoref ='" + medioPagoItem.getNroMedioPago() + "' " +
									" where z_pagomediopago_id =" + pagoMedioPago.get_ID();
							DB.executeUpdateEx(action, get_TrxName());

						}
						else{
							medioPagoItem.setNroMedioPago(pagoMedioPago.getDocumentNoRef());
						}

						medioPagoItem.setDateEmitted(pagoMedioPago.getDateEmitted());
						medioPagoItem.setDueDate(pagoMedioPago.getDueDate());

						medioPagoItem.setIsReceipt(false);
						medioPagoItem.setEmitido(false);
						medioPagoItem.setTotalAmt(pagoMedioPago.getTotalAmt());
						medioPagoItem.setIsOwn(true);
						medioPagoItem.setC_BPartner_ID(this.getC_BPartner_ID());
					}
					medioPagoItem.saveEx();
					pagoMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
				}
				else{
					medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
				}

				pagoMedioPago.saveEx();


				// Realizo emisión para este medio de pago a considerar
				if (!medioPagoItem.isEmitido()){
					MZEmisionMedioPago emisionMedioPago = new MZEmisionMedioPago(getCtx(), 0, get_TrxName());
					emisionMedioPago.setZ_MedioPago_ID(pagoMedioPago.getZ_MedioPago_ID());
					emisionMedioPago.setAD_Org_ID(this.getAD_Org_ID());

					if (pagoMedioPago.getZ_MedioPagoFolio_ID() > 0){
						emisionMedioPago.setZ_MedioPagoFolio_ID(pagoMedioPago.getZ_MedioPagoFolio_ID());
					}

					if ((medioPagoItem != null) && (medioPagoItem.get_ID() > 0)){
						emisionMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
						emisionMedioPago.setC_Currency_ID(medioPagoItem.getC_Currency_ID());

						if (medioPagoItem.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
						}
						if (medioPagoItem.getC_CashBook_ID() > 0){
							emisionMedioPago.setC_CashBook_ID(medioPagoItem.getC_CashBook_ID());
						}

					}
					else{
						emisionMedioPago.setReferenceNo(pagoMedioPago.getDocumentNoRef());
						emisionMedioPago.setC_Currency_ID(pagoMedioPago.getC_Currency_ID());

						if (pagoMedioPago.getC_BankAccount_ID() > 0){
							emisionMedioPago.setC_BankAccount_ID(pagoMedioPago.getC_BankAccount_ID());
						}
						if (pagoMedioPago.getC_CashBook_ID() > 0){
							emisionMedioPago.setC_CashBook_ID(pagoMedioPago.getC_CashBook_ID());
						}
					}

					emisionMedioPago.setZ_Pago_ID(this.get_ID());
					emisionMedioPago.setC_BPartner_ID(this.getC_BPartner_ID());
					emisionMedioPago.setDateDoc(this.getDateDoc());
					emisionMedioPago.setDateEmitted(pagoMedioPago.getDateEmitted());
					emisionMedioPago.setDueDate(pagoMedioPago.getDueDate());
					emisionMedioPago.setTotalAmt(pagoMedioPago.getTotalAmt());
					emisionMedioPago.saveEx();

					// Completo documento de emisión de medio de pago
					if (!emisionMedioPago.processIt(DocAction.ACTION_Complete)){
						message = emisionMedioPago.getProcessMsg();
						return message;
					}
					emisionMedioPago.saveEx();
				}


				// Si es un medio de pago recibido de terceros, me aseguro de no perder la referencia con el documento de cobro
				if (medioPagoItem.isReceipt()){
					medioPagoItem.setRef_Cobro_ID(medioPagoItem.getZ_Pago_ID());
				}
				medioPagoItem.setZ_Pago_ID(this.get_ID());

				// Marco medio de pago como entregado en este documento de pago cuando no es un anticipo.
				// Si es un anticipo, este medio de pago se marcará como entregado al momento de ingresar el recibo correspondiente al anticipo.
				if (!this.isAnticipo()){
					medioPagoItem.setEntregado(true);
				}

				medioPagoItem.saveEx();
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Proceso que marca los medios de pago para un documento de pago como entregados.
	 * Xpande. Created by Gabriel Vila on 5/24/18.
	 * @param medioPagoList
	 * @return
	 */
	private String entregarMediosPago(List<MZPagoMedioPago> medioPagoList) {

		String message = null;

		try{
			// No procede si es un cobro
			if (this.isSOTrx()){
				return null;
			}

			// Recorre lista de medios de pago de este pago
			for (MZPagoMedioPago pagoMedioPago: medioPagoList){

				// Si tengo item de medio de pago, lo marco como entregado
				if (pagoMedioPago.getZ_MedioPagoItem_ID() > 0){
					MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
					medioPagoItem.setZ_Pago_ID(this.get_ID());
					medioPagoItem.setEntregado(true);
					medioPagoItem.saveEx();
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Proceso que marca los medios de pago incluídos en anticipos asociados a este documento de pago como entregados.
	 * Xpande. Created by Gabriel Vila on 5/2/19.
	 * @return
	 */
	private String entregarMediosPagoAnticipos(){

		String message = null;

		String sql = "", action = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select ref_pago_id " +
					" from z_pagolin " +
					" where z_pago_id =" + this.get_ID() +
					" and ref_pago_id is not null ";
			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				int idAnticipo = rs.getInt("ref_pago_id");
				if (idAnticipo > 0){

					action = " update z_mediopagoitem set entregado ='Y', z_pago_id =" + this.get_ID() +
							" where z_mediopagoitem_id in " +
							" (select z_mediopagoitem_id from z_pagomediopago where z_pago_id =" + idAnticipo +
							" and z_mediopagoitem_id is not null) ";

					DB.executeUpdateEx(action, get_TrxName());
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
	 * Afecta documentos asociadas a este documento de pago/cobro.
	 * Xpande. Created by Gabriel Vila on 3/8/18.
	 * @param pagoLinList
	 * @return
	 */
	private String afectarDocumentosLineas(List<MZPagoLin> pagoLinList) {

		String message = null;
		String action = "";

		try{

			String documentNoRef = this.getDocumentNo();
			if ((this.getNroRecibo() != null) && (!this.getNroRecibo().trim().equalsIgnoreCase(""))){
				documentNoRef = this.getNroRecibo();
			}

			// Recorro comprobantes
			for (MZPagoLin pagoLin: pagoLinList){

				if (pagoLin.getC_Invoice_ID() > 0){

					// Afecta cada comprobante por el monto de afectación
					MZInvoiceAfectacion invoiceAfecta = null;

					// Si tengo orden de pago asociada, busco afectación de la invoice o vencimiento del a misma, para esta orden de pago y le actualizo el campo ID de pago.
					// Esto es porque ya esta afectado.
					if (pagoLin.getZ_OrdenPago_ID() > 0){
						if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
							invoiceAfecta = MZInvoiceAfectacion.getByInvoiceSchOrdenPago(getCtx(), pagoLin.getC_InvoicePaySchedule_ID(), pagoLin.getZ_OrdenPago_ID(), get_TrxName());
						}
						else {
							invoiceAfecta = MZInvoiceAfectacion.getByInvoiceOrdenPago(getCtx(), pagoLin.getC_Invoice_ID(), pagoLin.getZ_OrdenPago_ID(), get_TrxName());
						}
					}
					if (invoiceAfecta == null){

						BigDecimal amtAllocation = pagoLin.getAmtAllocation();
						if (amtAllocation.compareTo(Env.ZERO) < 0){
							amtAllocation = amtAllocation.negate();
						}

						invoiceAfecta = new MZInvoiceAfectacion(getCtx(), 0, get_TrxName());
						invoiceAfecta.setAD_Table_ID(this.get_Table_ID());
						invoiceAfecta.setAmtAllocation(amtAllocation);
						invoiceAfecta.setC_DocType_ID(this.getC_DocType_ID());
						invoiceAfecta.setC_Invoice_ID(pagoLin.getC_Invoice_ID());
						if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
							invoiceAfecta.setC_InvoicePaySchedule_ID(pagoLin.getC_InvoicePaySchedule_ID());
						}
						invoiceAfecta.setDateDoc(this.getDateDoc());
						invoiceAfecta.setDocumentNoRef(documentNoRef);
						invoiceAfecta.setDueDate(pagoLin.getDueDateDoc());
						invoiceAfecta.setRecord_ID(this.get_ID());
						invoiceAfecta.setC_Currency_ID(pagoLin.getC_Currency_ID());
						invoiceAfecta.setAD_Org_ID(this.getAD_Org_ID());
					}
					invoiceAfecta.setZ_Pago_ID(this.get_ID());
					invoiceAfecta.saveEx();

					// Me aseguro marca de invoice como paga cuando no viene de ordenes de pago
					if (!this.isTieneOrdenPago()){
						MInvoice invoice = (MInvoice) pagoLin.getC_Invoice();
						invoice.setIsPaid(true);
						invoice.saveEx();
					}

					// Afecto estado de cuenta de esta invoice cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){

						if (pagoLin.getC_InvoicePaySchedule_ID() > 0){
							action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
									 " z_pago_id =" + this.get_ID() +
									 " where c_invoicepayschedule_id =" + pagoLin.getC_InvoicePaySchedule_ID();
						}
						else{
							action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
									 " z_pago_id =" + this.get_ID() +
									 " where c_invoice_id =" + pagoLin.getC_Invoice_ID();
						}
						DB.executeUpdateEx(action, get_TrxName());
					}
				}
				else if (pagoLin.getZ_TransferSaldo_ID() > 0){

					// Afecta cada comprobante por el monto de afectación
					MZTransferAfectacion transferAfecta = null;

					// Si tengo orden de pago asociada, busco afectación de la transferencia de saldo para esta orden de pago y le actualizo el campo ID de pago.
					// Esto es porque ya esta afectado.
					if (pagoLin.getZ_OrdenPago_ID() > 0){
						transferAfecta = MZTransferAfectacion.getByTransferOrdenPago(getCtx(), pagoLin.getZ_TransferSaldo_ID(), pagoLin.getZ_OrdenPago_ID(), get_TrxName());
					}

					if (transferAfecta == null){

						BigDecimal amtAllocation = pagoLin.getAmtAllocation();
						if (amtAllocation.compareTo(Env.ZERO) < 0){
							amtAllocation = amtAllocation.negate();
						}

						transferAfecta = new MZTransferAfectacion(getCtx(), 0, get_TrxName());
						transferAfecta.setAD_Table_ID(this.get_Table_ID());
						transferAfecta.setAmtAllocation(amtAllocation);
						transferAfecta.setC_DocType_ID(this.getC_DocType_ID());
						transferAfecta.setZ_TransferSaldo_ID(pagoLin.getZ_TransferSaldo_ID());
						transferAfecta.setDateDoc(this.getDateDoc());
						transferAfecta.setDocumentNoRef(documentNoRef);
						transferAfecta.setDueDate(pagoLin.getDueDateDoc());
						transferAfecta.setRecord_ID(this.get_ID());
						transferAfecta.setC_Currency_ID(pagoLin.getC_Currency_ID());
						transferAfecta.setAD_Org_ID(this.getAD_Org_ID());
					}
					transferAfecta.setZ_Pago_ID(this.get_ID());
					transferAfecta.saveEx();

					// Me aseguro marca de documento de transferencia de saldo como paga cuando no viene de ordenes de pago
					if (!this.isTieneOrdenPago()){
						MZTransferSaldo transferSaldo = (MZTransferSaldo) pagoLin.getZ_TransferSaldo();
						transferSaldo.setIsPaid(true);
						transferSaldo.saveEx();
					}

					// Afecto estado de cuenta de este documento de transferencia de saldo cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){

						action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
								" z_pago_id =" + this.get_ID() +
								" where z_transfersaldo_id =" + pagoLin.getZ_TransferSaldo_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
				}
				else if (pagoLin.getRef_Pago_ID() > 0){

					// Afecta cada comprobante por el monto de afectación, cuando no es el recibo del anticipo
					if (((this.getTotalMediosPago() != null) && (this.getTotalMediosPago().compareTo(Env.ZERO) != 0))
						|| ((this.getTotalMediosPago().compareTo(Env.ZERO) == 0) && (this.getPayAmt().compareTo(Env.ZERO) == 0))) {

						MZPagoAfectacion pagoAfectacion = null;

						// Si tengo orden de pago asociada, busco afectación del anticipo, para esta orden de pago y le actualizo el campo ID de pago.
						// Esto es porque ya esta afectado.
						if (pagoLin.getZ_OrdenPago_ID() > 0){
							pagoAfectacion = MZPagoAfectacion.getByPagoOrdenPago(getCtx(), pagoLin.getRef_Pago_ID(), pagoLin.getZ_OrdenPago_ID(), get_TrxName());
						}
						if (pagoAfectacion == null){

							BigDecimal amtAllocation = pagoLin.getAmtAllocation();
							if (amtAllocation.compareTo(Env.ZERO) < 0){
								amtAllocation = amtAllocation.negate();
							}

							pagoAfectacion = new MZPagoAfectacion(getCtx(), 0, get_TrxName());
							pagoAfectacion.setAD_Table_ID(this.get_Table_ID());
							pagoAfectacion.setAmtAllocation(amtAllocation);
							pagoAfectacion.setC_DocType_ID(this.getC_DocType_ID());
							pagoAfectacion.setRef_Pago_ID(this.get_ID());
							pagoAfectacion.setDateDoc(this.getDateDoc());
							pagoAfectacion.setDocumentNoRef(documentNoRef);
							pagoAfectacion.setDueDate(pagoLin.getDueDateDoc());
							pagoAfectacion.setRecord_ID(this.get_ID());
							pagoAfectacion.setC_Currency_ID(pagoLin.getC_Currency_ID());
							pagoAfectacion.setAD_Org_ID(this.getAD_Org_ID());
						}
						pagoAfectacion.setZ_Pago_ID(pagoLin.getRef_Pago_ID());
						pagoAfectacion.saveEx();
					}

					// Afecto estado de cuenta de esta anticipo cuando es cobro o es un pago que no esta referenciando ordenes de pago.
					if (!this.isTieneOrdenPago()){
						action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
								" ref_pago_id =" + this.get_ID() +
								" where z_pago_id =" + pagoLin.getRef_Pago_ID();
					}
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Afecta resguardos utilizados en este pago
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 */
	private String afectarResguardos() {

		String message = null;
		String action = "";

		try{

			// No aplica para cobros.
			if (this.isSOTrx()){
				return null;
			}

			String documentNoRef = this.getDocumentNo();
			if ((this.getNroRecibo() != null) && (!this.getNroRecibo().trim().equalsIgnoreCase(""))){
				documentNoRef = this.getNroRecibo();
			}


			List<MZPagoResguardo> pagoResguardoList = this.getResguardos();
			for (MZPagoResguardo pagoResguardo: pagoResguardoList){

				if (pagoResguardo.isSelected()){
					MZResguardoSocio resguardoSocio = (MZResguardoSocio) pagoResguardo.getZ_ResguardoSocio();
					resguardoSocio.setZ_Pago_ID(this.get_ID());
					resguardoSocio.setIsPaid(true);
					resguardoSocio.saveEx();

					// Afecto estado de cuenta de este resguardo, cuando el pago no esta referenciando ordenes de pago
					if (!this.isTieneOrdenPago()){

						action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
								 " z_pago_id =" + this.get_ID() +
								 " where z_resguardosocio_id =" + resguardoSocio.get_ID();
						DB.executeUpdateEx(action, get_TrxName());
					}
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Afecta ordenes de pago utilizados en este pago
	 * Xpande. Created by Gabriel Vila on 3/12/18.
	 * @return
	 * @param ordenPagoList
	 */
	private String afectarOrdenesPago(List<MZPagoOrdenPago> ordenPagoList) {

		String message = null;
		String action = "";

		try{

			// No aplica para cobros.
			if (this.isSOTrx()){
				return null;
			}

			// No hago nada si no tengo ordenes de pago referenciadas en este pago
			if (ordenPagoList.size() <= 0){
				return null;
			}

			String documentNoRef = this.getDocumentNo();
			if ((this.getNroRecibo() != null) && (!this.getNroRecibo().trim().equalsIgnoreCase(""))){
				documentNoRef = this.getNroRecibo();
			}


			for (MZPagoOrdenPago pagoOrdenPago: ordenPagoList){
				MZOrdenPago ordenPago = (MZOrdenPago) pagoOrdenPago.getZ_OrdenPago();
				ordenPago.setZ_Pago_ID(this.get_ID());
				ordenPago.setIsPaid(true);
				ordenPago.saveEx();

				action = " update z_estadocuenta set referenciapago ='RECIBO " + documentNoRef + "', " +
						 " z_pago_id =" + this.get_ID() +
						 " where z_ordenpago_id =" + ordenPago.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Al completarse el pago / cobro se hacen los impactos necesarios en el estado de cuenta del socio de negocio.
	 * Xpande. Created by Gabriel Vila on 3/24/18.
	 */
	private void setEstadoCuenta() {

		try{

			// Si es un pago que referencia ordenes de pago, no hago nada.
			if (this.isTieneOrdenPago()){
				return;
			}

			MDocType docType = (MDocType) this.getC_DocType();

			// Impacto documento en estado de cuenta
			MZEstadoCuenta estadoCuenta = new MZEstadoCuenta(getCtx(), 0, get_TrxName());
			estadoCuenta.setZ_Pago_ID(this.get_ID());
			estadoCuenta.setAD_Table_ID(this.get_Table_ID());
			estadoCuenta.setC_BPartner_ID(this.getC_BPartner_ID());
			estadoCuenta.setC_Currency_ID(this.getC_Currency_ID());
			estadoCuenta.setC_DocType_ID(this.getC_DocType_ID());
			estadoCuenta.setDateDoc(this.getDateDoc());
			estadoCuenta.setDocBaseType(docType.getDocBaseType());
			if ((this.getNroRecibo() != null) && (!this.getNroRecibo().trim().equalsIgnoreCase(""))){
				estadoCuenta.setDocumentNoRef(this.getNroRecibo());
			}
			else{
				estadoCuenta.setDocumentNoRef(this.getDocumentNo());
			}

			estadoCuenta.setIsSOTrx(this.isSOTrx());

			if (!this.isSOTrx()){
				if (!this.isAnticipo()){
					estadoCuenta.setAmtSourceCr(Env.ZERO);
					estadoCuenta.setAmtSourceDr(this.getPayAmt());
				}
				else{
					estadoCuenta.setAmtSourceDr(Env.ZERO);
					estadoCuenta.setAmtSourceCr(this.getPayAmt());
				}
			}
			else{
				if (!this.isAnticipo()){
					estadoCuenta.setAmtSourceCr(this.getPayAmt());
					estadoCuenta.setAmtSourceDr(Env.ZERO);
				}
				else{
					estadoCuenta.setAmtSourceDr(this.getPayAmt());
					estadoCuenta.setAmtSourceCr(Env.ZERO);
				}
			}

			estadoCuenta.setRecord_ID(this.get_ID());
			estadoCuenta.setAD_Org_ID(this.getAD_Org_ID());
			estadoCuenta.saveEx();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (this.getAD_Org_ID() == 0){
			log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
			return false;
		}

		return true;
	}


	@Override
	protected boolean beforeDelete() {

		String message = "No se pudo eliminar este registro. ";
		String action = "";

		try{

			// Antes de eliminar el recibo, me aseguro de anular y eliminar medios de pago asociados a cajas (cashbook)
			List<MZPagoMedioPago> pagoMedioPagoList = this.getMediosPago();
			for (MZPagoMedioPago pagoMedioPago: pagoMedioPagoList){
				if (pagoMedioPago.getC_CashBook_ID() > 0){
					if (pagoMedioPago.getZ_MedioPagoItem_ID() > 0){
						MZMedioPagoItem medioPagoItem = (MZMedioPagoItem) pagoMedioPago.getZ_MedioPagoItem();
						if (medioPagoItem.getZ_EmisionMedioPago_ID() > 0){
							// Anulo emisión asociada a este item de medio de pago y la elimino
							MZEmisionMedioPago emisionMedioPago = (MZEmisionMedioPago) medioPagoItem.getZ_EmisionMedioPago();
							if (!emisionMedioPago.processIt(DocAction.ACTION_Void)){
								if (emisionMedioPago.getProcessMsg() != null){
									message = message + emisionMedioPago.getProcessMsg();
								}
								log.saveError("Error", message);
								return false;
							}
							emisionMedioPago.saveEx();

							action = " update z_mediopagoitem set z_emisionmediopago_id = null where z_mediopagoitem_id =" + medioPagoItem.get_ID();
							DB.executeUpdateEx(action, get_TrxName());

							emisionMedioPago.deleteEx(true);
						}
						medioPagoItem.setEntregado(false);
						medioPagoItem.setAnulado(true);
						medioPagoItem.saveEx();
					}
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return true;
	}

	/***
	 * Obtiene y carga medios de pago emitidos para el socio de negocio del documento y ademas medios de pago de terceros que no
	 * esten entregados pero si en cartera (emitidos).
	 * Xpande. Created by Gabriel Vila on 11/14/18.
	 * @return
	 */
	public String getMediosPagoEmitidos(String tipoAccion){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			boolean getMPNuevos = true;

			if (!tipoAccion.equalsIgnoreCase("NUEVOS")){
				getMPNuevos = false;
			}

			// Elimino generacion anterior en caso de que el usuario asi lo indique
			if (!getMPNuevos){
				this.deleteDocuments(true);
			}

			HashMap<Integer, Integer> hashCurrency = new HashMap<Integer, Integer>();

		    sql = " select i.z_mediopagoitem_id, i.z_mediopago_id, i.z_mediopagofolio_id, i.totalamt, " +
					" i.c_bankaccount_id, i.c_currency_id, i.dateemitted, i.nromediopago, i.duedate " +
					" from z_mediopagoitem i " +
					" where i.c_bpartner_id =" + this.getC_BPartner_ID() +
					" and i.isReceipt ='N' " +
					" and i.emitido ='Y' and i.entregado='N' and i.anulado='N' " +
					" and i.depositado ='N' and i.conciliado ='N' " +
					" and i.z_mediopagoitem_id not in " +
					" (select z_mediopagoitem_id from z_pagomediopago where z_pago_id =" + this.get_ID() + ") ";

		    pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
				pagoMedioPago.setZ_Pago_ID(this.get_ID());

				pagoMedioPago.setZ_MedioPago_ID(rs.getInt("z_mediopago_id"));
				if (rs.getInt("z_mediopagofolio_id") > 0){
					pagoMedioPago.setZ_MedioPagoFolio_ID(rs.getInt("z_mediopagofolio_id"));
				}

				pagoMedioPago.setZ_MedioPagoItem_ID(rs.getInt("z_mediopagoitem_id"));
				pagoMedioPago.setTotalAmtMT(rs.getBigDecimal("totalamt"));
				pagoMedioPago.setTotalAmt(rs.getBigDecimal("totalamt"));

				if (rs.getInt("c_bankaccount_id") > 0){
					pagoMedioPago.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				}

				//pagoMedioPago.setC_BankAccount_ID(rs.getInt("c_bankaccount_id"));
				pagoMedioPago.setC_Currency_ID(rs.getInt("c_currency_id"));
				pagoMedioPago.setDateEmitted(rs.getTimestamp("dateemitted"));
				pagoMedioPago.setDocumentNoRef(rs.getString("nromediopago"));
				pagoMedioPago.setDueDate(rs.getTimestamp("duedate"));
				pagoMedioPago.setEmisionManual(false);
				pagoMedioPago.setMultiplyRate(Env.ONE);

				MZMedioPago medioPago = new MZMedioPago(getCtx(), rs.getInt("z_mediopago_id"), null);

				pagoMedioPago.setTieneCaja(medioPago.isTieneCaja());
				pagoMedioPago.setTieneCtaBco(medioPago.isTieneCtaBco());
				pagoMedioPago.setTieneFecEmi(medioPago.isTieneFecEmi());
				pagoMedioPago.setTieneFecVenc(medioPago.isTieneFecVenc());
				pagoMedioPago.setTieneFolio(medioPago.isTieneFolio());
				pagoMedioPago.setTieneNroRef(medioPago.isTieneNroRef());
				pagoMedioPago.saveEx();

				// Guardo moneda en hash si aún no la tengo
				if (!hashCurrency.containsKey(pagoMedioPago.getC_Currency_ID())){
					hashCurrency.put(pagoMedioPago.getC_Currency_ID(), pagoMedioPago.getC_Currency_ID());
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
				this.updateRates(true);
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
	 * Genera un documento de pago o cobro para una determinada invoice con medio de pago Efectivo.
	 * Xpande. Created by Gabriel Vila on 1/22/19.
	 * @param invoice
	 * @return
	 */
    public String generateFromCashInvoice(MInvoice invoice) {

		String message = null;

		try{

			// Obtengo modelo de Configuraciones financieras
			MZFinancialConfig financialConfig = MZFinancialConfig.getDefault(getCtx(), null);

			// Documento de pago o cobro según tipo de invoice.
			MDocType docType = null;
			if (invoice.isSOTrx()){
				if (financialConfig.getDefaultDocCCD_ID() <= 0){
					return "Falta parametrizar Documento por Defecto para Cobros a Clientes, en Configuraciones Financieras.";
				}
				docType = new MDocType(getCtx(), financialConfig.getDefaultDocCCD_ID(), get_TrxName());
			}
			else{
				if (financialConfig.getDefaultDocPPD_ID() <= 0){
					return "Falta parametrizar Documento por Defecto para Pagos a Proveedores, en Configuraciones Financieras.";
				}
				docType = new MDocType(getCtx(), financialConfig.getDefaultDocPPD_ID(), get_TrxName());
			}

			// Seteo atributos del cabezal del documento de pago/cobro.
			this.setAD_Client_ID(invoice.getAD_Client_ID());
			this.setAD_Org_ID(invoice.getAD_Org_ID());
			this.setC_DocType_ID(docType.get_ID());
			this.setIsSOTrx(invoice.isSOTrx());
			this.setC_BPartner_ID(invoice.getC_BPartner_ID());
			this.setC_Currency_ID(invoice.getC_Currency_ID());
			this.setDateDoc(invoice.getDateInvoiced());
			this.setPayAmt(invoice.getGrandTotal());
			this.setTotalMediosPago(invoice.getGrandTotal());
			this.setTieneOrdenPago(false);
			this.setTieneRecibo(false);
			this.saveEx();

			// Linea para Documento Invoice origen de este pago/cobro.
			MZPagoLin pagoLin = new MZPagoLin(getCtx(), 0, get_TrxName());
			pagoLin.setAD_Org_ID(this.getAD_Org_ID());
			pagoLin.setZ_Pago_ID(this.get_ID());
			pagoLin.setAmtAllocation(this.getPayAmt());
			pagoLin.setAmtAllocationMT(this.getPayAmt());
			pagoLin.setAmtDocument(this.getPayAmt());
			pagoLin.setAmtOpen(this.getPayAmt());
			pagoLin.setC_Currency_ID(this.getC_Currency_ID());
			pagoLin.setC_DocType_ID(invoice.getC_DocTypeTarget_ID());
			pagoLin.setC_Invoice_ID(invoice.get_ID());
			pagoLin.setDateDoc(invoice.getDateInvoiced());

			String documentNoRef = invoice.getDocumentNo();
			if (invoice.get_ValueAsString("DocumentSerie") != null){
				documentNoRef = invoice.get_ValueAsString("DocumentSerie").trim() + documentNoRef;
			}
			pagoLin.setDocumentNoRef(documentNoRef);
			pagoLin.setDueDateDoc(invoice.getDateInvoiced());
			pagoLin.setEstadoAprobacion(X_Z_PagoLin.ESTADOAPROBACION_APROBADO);
			pagoLin.setIsSelected(true);
			pagoLin.setMultiplyRate(Env.ONE);
			pagoLin.setResguardoEmitido(false);
			pagoLin.saveEx();

			// Linea de medio de pago Efectivo
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), invoice.getPaymentRule(), null);
			MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
			pagoMedioPago.setZ_Pago_ID(this.get_ID());
			pagoMedioPago.setAD_Org_ID(this.getAD_Org_ID());
			pagoMedioPago.setZ_MedioPago_ID(medioPago.get_ID());
			pagoMedioPago.setC_CashBook_ID(invoice.get_ValueAsInt("C_CashBook_ID"));
			pagoMedioPago.setC_Currency_ID(this.getC_Currency_ID());
			pagoMedioPago.setEmisionManual(true);
			pagoMedioPago.setMultiplyRate(Env.ONE);
			pagoMedioPago.setTotalAmt(this.getPayAmt());
			pagoMedioPago.setTotalAmtMT(this.getPayAmt());

			if (this.isSOTrx()){
				pagoMedioPago.setTieneBanco(medioPago.isTieneBancoCobro());
				pagoMedioPago.setTieneCaja(medioPago.isTieneCajaCobro());
				pagoMedioPago.setTieneCtaBco(medioPago.isTieneCtaBcoCobro());
				pagoMedioPago.setTieneFecEmi(medioPago.isTieneFecEmiCobro());
				pagoMedioPago.setTieneFecVenc(medioPago.isTieneFecVencCobro());
				pagoMedioPago.setTieneFolio(medioPago.isTieneFolioCobro());
				pagoMedioPago.setTieneNroRef(medioPago.isTieneNroRefCobro());
			}
			else{
				pagoMedioPago.setTieneBanco(medioPago.isTieneBanco());
				pagoMedioPago.setTieneCaja(medioPago.isTieneCaja());
				pagoMedioPago.setTieneCtaBco(medioPago.isTieneCtaBco());
				pagoMedioPago.setTieneFecEmi(medioPago.isTieneFecEmi());
				pagoMedioPago.setTieneFecVenc(medioPago.isTieneFecVenc());
				pagoMedioPago.setTieneFolio(medioPago.isTieneFolio());
				pagoMedioPago.setTieneNroRef(medioPago.isTieneNroRef());
			}
			pagoMedioPago.saveEx();

			// Completo documento
			if (!this.processIt(DOCACTION_Complete)){
				return this.m_processMsg;
			}

			this.saveEx();
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
    }

}