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
import org.xpande.comercial.utils.ComercialUtils;
import org.xpande.core.utils.CurrencyUtils;

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

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro de que se indique Organización distinta de *.
        if (this.getAD_Org_ID() <= 0){
            log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
            return false;
        }

        return true;
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

        if (ordenesPagos.size() <= 0){
            m_processMsg = "No hay Ordenes de Pago Generadas. Debe generar al menos una para poder Completar este Documento.";
            return DocAction.STATUS_Invalid;
        }

        for (MZOrdenPago ordenPago: ordenesPagos){

            // Completo orden de pago
            if (!ordenPago.processIt(DocAction.ACTION_Complete)){
                m_processMsg = ordenPago.getProcessMsg();
                return DocAction.STATUS_Invalid;
            }
            ordenPago.saveEx();
        }

        // Elimino documentos no procesados en ordenes de pago y socios de negocio sin ordenes generadas
        this.deleteNotProcessedData();

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
     * Al completarse elimino documentos y socios de negocio que no se consideraron en la generación de las ordenes de pago.
     * Xpande. Created by Gabriel Vila on 6/26/19.
     */
    private void deleteNotProcessedData() {

        String action = "";

        try{
            // Elimino socios de negocio con ningun documento procesado en ordenes de pago
            action = " delete from z_generaordenpagosocio " +
                        " where z_generaordenpago_id =" + this.get_ID() +
                        " and z_generaordenpagosocio_id not in " +
                        " (select distinct z_generaordenpagosocio_id " +
                        " from z_generaordenpagolin " +
                        " where z_generaordenpago_id =" + this.get_ID() +
                        " and isselected ='Y') ";
            DB.executeUpdateEx(action, get_TrxName());

            // Elimino lines no procesadas en ordenes de pago
            action = " delete from z_generaordenpagolin " +
                        " where z_generaordenpago_id =" + this.get_ID() +
                        " and isselected ='N'";
            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene y retorna lista de ordenes de pago generadas en este proceso.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    private List<MZOrdenPago> getOrdenesPago() {

        String whereClause = X_Z_OrdenPago.COLUMNNAME_Z_GeneraOrdenPago_ID + " =" + this.get_ID();

        List<MZOrdenPago> lines = new Query(getCtx(), I_Z_OrdenPago.Table_Name, whereClause, get_TrxName()).setOrderBy(" DocumentNo ").list();

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

            // Obtengo transferencias de saldos a considerar y genero lineas
            message = this.getTransferSaldos();
            if (message != null){
                return message;
            }

            // Obtengo anticipos a proveedores a considerar y genero lineas
            message = this.getAnticipos();
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
                whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
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

            // Filtro de Medio de Pago
            if (this.getPaymentRulePO() != null){
                whereClause += " AND bp.PaymentRulePo ='" + this.getPaymentRulePO() + "' ";
            }

            // Query
            sql = " select hdr.c_bpartner_id, hdr.z_resguardosocio_id, hdr.c_doctype_id, hdr.documentno, " +
                    " hdr.datedoc, hdr.c_currency_id, hdr.totalamt, doc.docbasetype, bp.PaymentRulePo " +
                    " from z_resguardosocio hdr " +
                    " inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
                    " inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
                    " where hdr.ad_client_id =" + this.getAD_Client_ID() +
                    " and hdr.ad_org_id =" + this.getAD_Org_ID() +
                    " and hdr.ispaid ='N' " +
                    " and hdr.docstatus='CO' " +
                    " and hdr.z_resguardosocio_ref_id is null " +
                    " and doc.docbasetype='RGU' " +
                    " and hdr.z_resguardosocio_id not in (select z_resguardosocio_id from z_generaordenpagolin " +
                    " where z_resguardosocio_id is not null " +
                    " and z_generaordenpago_id =" + this.get_ID() + ") "  +
                    " and hdr.z_resguardosocio_id not in (select coalesce(z_resguardosocio_id,0) as resg_id from z_ordenpagolin a " +
                    " inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
                    " where z_resguardosocio_id is not null and b.docstatus='CO') " +
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

                // Seteo medio de pago según el socio de negocio en caso de tener uno asociado.
                ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
                if (rs.getString("PaymentRulePO") != null){
                    medioPago = MZMedioPago.getByValue(getCtx(), rs.getString("PaymentRulePO"), null);
                    if ((medioPago != null) && (medioPago.get_ID() > 0)){
                        ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
                    }
                }

                ordenPagoLin.setZ_ResguardoSocio_ID(rs.getInt("z_resguardosocio_id"));
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
     * Obtiene anticipos a proveedores a considerar y genera lineas por cada uno de ellos.
     * Xpande. Created by Gabriel Vila on 3/24/19.
     * @return
     */
    private String getAnticipos() {

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
                whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
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

            // Filtro de Medio de Pago
            if (this.getPaymentRulePO() != null){
                whereClause += " AND bp.PaymentRulePo ='" + this.getPaymentRulePO() + "' ";
            }

            // Query
            sql = " select hdr.c_bpartner_id, hdr.z_pago_id, hdr.c_doctype_id, hdr.documentno, " +
                    " hdr.datedoc, hdr.c_currency_id, hdr.payamt, iop.amtopen, doc.docbasetype, bp.PaymentRulePo " +
                    " from z_pago hdr " +
                    " inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
                    " inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
                    " inner join zv_financial_pagoopen iop on hdr.z_pago_id = iop.z_pago_id " +
                    " where hdr.ad_client_id =" + this.getAD_Client_ID() +
                    " and hdr.ad_org_id =" + this.getAD_Org_ID() +
                    " and hdr.anticipo ='Y' " +
                    " and hdr.issotrx ='N' " +
                    " and hdr.docstatus='CO' " +
                    " and iop.amtopen > 0 " +
                    " and hdr.z_pago_id not in (select coalesce(l.z_pago_id,0) as pago_id from z_generaordenpagolin l " +
                    " where l.z_pago_id is not null " +
                    " and l.z_generaordenpago_id =" + this.get_ID() + ") " +

                    /*
                    " and hdr.z_pago_id not in (select a.z_pago_id from z_ordenpagolin a " +
                    " inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
                    " where a.z_pago_id is not null and b.docstatus='CO') " +
                     */

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

                BigDecimal amtDocument = rs.getBigDecimal("payamt");
                BigDecimal amtOpen = rs.getBigDecimal("amtopen");

                amtDocument = amtDocument.negate();
                amtOpen = amtOpen.negate();

                MZGeneraOrdenPagoLin ordenPagoLin = new MZGeneraOrdenPagoLin(getCtx(), 0, get_TrxName());
                ordenPagoLin.setZ_GeneraOrdenPago_ID(this.get_ID());
                ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());

                // Seteo medio de pago según el socio de negocio en caso de tener uno asociado.
                ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
                if (rs.getString("PaymentRulePO") != null){
                    medioPago = MZMedioPago.getByValue(getCtx(), rs.getString("PaymentRulePO"), null);
                    if ((medioPago != null) && (medioPago.get_ID() > 0)){
                        ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());
                    }
                }

                ordenPagoLin.setZ_Pago_ID(rs.getInt("z_pago_id"));
                ordenPagoLin.setAmtDocument(amtDocument);
                ordenPagoLin.setAmtAllocation(amtOpen);
                ordenPagoLin.setAmtOpen(amtOpen);
                ordenPagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
                ordenPagoLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
                ordenPagoLin.setDateDoc(rs.getTimestamp("datedoc"));
                ordenPagoLin.setDueDateDoc(rs.getTimestamp("datedoc"));
                ordenPagoLin.setDocumentNoRef(rs.getString("documentno"));
                ordenPagoLin.setDueDateMedioPago(ordenPagoLin.getDueDateDoc());
                ordenPagoLin.setEstadoAprobacion("APROBADO");

                // Seteo medio de pago según el socio de negocio en caso de tener uno asociado.
                if (rs.getString("PaymentRulePO") != null){
                    medioPago = MZMedioPago.getByValue(getCtx(), rs.getString("PaymentRulePO"), null);
                    if ((medioPago == null) || (medioPago.get_ID() <= 0)){
                        medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                    }
                }
                else{
                    medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                }
                ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());

                ordenPagoLin.setTieneDtosNC(false);
                ordenPagoLin.setResguardoEmitido(false);
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
                whereClause += " AND hdr.DateInvoiced <='" + this.getDateEmittedTo() + "' ";
            }
            if (this.getDueDateFrom() != null){
                whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) >='" + this.getDueDateFrom() + "' ";
            }
            if (this.getDueDateTo() != null){
                whereClause += " AND coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced) <='" + this.getDueDateTo() + "' ";
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

            // Filtro de Medio de Pago
            if (this.getPaymentRulePO() != null){
                whereClause += " AND bp.PaymentRulePo ='" + this.getPaymentRulePO() + "' ";
            }

            // Query
            sql = " select hdr.c_bpartner_id, hdr.c_invoice_id, hdr.c_doctypetarget_id, (hdr.documentserie || hdr.documentno) as documentno, " +
                    " hdr.dateinvoiced, hdr.c_currency_id, coalesce(ips.dueamt,hdr.grandtotal) as grandtotal, ips.c_invoicepayschedule_id, " +
                    " iop.amtopen, bp.PaymentRulePO, " +
                    " coalesce(hdr.isindispute,'N') as isindispute, doc.docbasetype, coalesce(hdr.TieneDtosNC,'N') as TieneDtosNC, " +
                    " coalesce(coalesce(ips.duedate, paymentTermDueDate(hdr.C_PaymentTerm_ID, hdr.DateInvoiced)), hdr.dateinvoiced)::timestamp without time zone  as duedate " +
                    " from c_invoice hdr " +
                    " inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
                    " inner join c_doctype doc on hdr.c_doctypetarget_id = doc.c_doctype_id " +
                    " inner join zv_financial_invopen iop on hdr.c_invoice_id = iop.c_invoice_id " +
                    " left outer join c_invoicepayschedule ips on hdr.c_invoice_id = ips.c_invoice_id " +
                    " where hdr.ad_client_id =" + this.getAD_Client_ID() +
                    " and hdr.ad_org_id =" + this.getAD_Org_ID() +
                    " and hdr.issotrx='N' " +
                    " and hdr.ispaid='N' " +
                    " and hdr.docstatus='CO' " +
                    " and iop.amtopen > 0 " +
                    " and hdr.c_invoice_id not in (select coalesce(c_invoice_id,0) as inv_id from z_generaordenpagolin " +
                    " where c_invoice_id is not null " +
                    " and z_generaordenpago_id =" + this.get_ID() + ") " +

                    /*
                    " and hdr.c_invoice_id not in (select c_invoice_id from z_ordenpagolin a " +
                    " inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
                    " where c_invoice_id is not null and b.docstatus='CO') " +
                     */

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
                        ordenPagoSocio.setName(partner.getName());
                        ordenPagoSocio.setC_Currency_ID(this.getC_Currency_ID());
                        ordenPagoSocio.setTotalAmt(Env.ZERO);
                        ordenPagoSocio.saveEx();
                    }
                }

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
                if (rs.getString("docbasetype").equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
                    esFactura = false;
                }

                BigDecimal amtDocument = rs.getBigDecimal("grandtotal");
                BigDecimal amtOpen = rs.getBigDecimal("amtopen");
                if (!esFactura){
                    amtDocument = amtDocument.negate();
                    amtOpen = amtOpen.negate();
                }

                MZGeneraOrdenPagoLin ordenPagoLin = new MZGeneraOrdenPagoLin(getCtx(), 0, get_TrxName());
                ordenPagoLin.setZ_GeneraOrdenPago_ID(this.get_ID());
                ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());
                ordenPagoLin.setAmtDocument(amtDocument);
                ordenPagoLin.setAmtOpen(amtOpen);
                ordenPagoLin.setAmtAllocation(amtOpen);
                ordenPagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
                ordenPagoLin.setC_DocType_ID(rs.getInt("c_doctypetarget_id"));
                ordenPagoLin.setDateDoc(rs.getTimestamp("dateinvoiced"));
                ordenPagoLin.setDueDateDoc(rs.getTimestamp("duedate"));
                ordenPagoLin.setDocumentNoRef(rs.getString("documentno"));
                ordenPagoLin.setDueDateMedioPago(ordenPagoLin.getDueDateDoc());
                ordenPagoLin.setEstadoAprobacion("APROBADO");
                ordenPagoLin.setC_Invoice_ID(rs.getInt("c_invoice_id"));

                if (cInvoicePayScheduleID > 0){
                    ordenPagoLin.setC_InvoicePaySchedule_ID(cInvoicePayScheduleID);
                }

                // Seteo medio de pago según el socio de negocio en caso de tener uno asociado.

                if (rs.getString("PaymentRulePO") != null){
                    medioPago = MZMedioPago.getByValue(getCtx(), rs.getString("PaymentRulePO"), null);
                    if ((medioPago == null) || (medioPago.get_ID() <= 0)){
                        medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                    }
                }
                else{
                    medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                }
                ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());

                boolean tieneDtosNC = (rs.getString("TieneDtosNC").equalsIgnoreCase("Y")) ? true : false;
                ordenPagoLin.setTieneDtosNC(tieneDtosNC);

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
     * Obtiene transferencias de saldos a considerar y genera lineas por cada uno de ellos.
     * Xpande. Created by Gabriel Vila on 3/14/19.
     * @return
     */
    private String getTransferSaldos(){

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
                whereClause = " AND hdr.DateDoc >='" + this.getDateEmittedFrom() + "' ";
            }
            if (this.getDateEmittedTo() != null){
                whereClause += " AND hdr.DateDoc <='" + this.getDateEmittedTo() + "' ";
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

            // Filtro de Medio de Pago
            if (this.getPaymentRulePO() != null){
                whereClause += " AND bp.PaymentRulePo ='" + this.getPaymentRulePO() + "' ";
            }

            // Query
            sql = " select hdr.c_bpartner_id, hdr.z_transfersaldo_id, hdr.c_invoice_id, hdr.c_doctype_id, hdr.documentno, " +
                    " hdr.datedoc, hdr.c_currency_id, hdr.grandtotal, hdr.C_BPartnerRelation_ID, " +
                    " iop.amtopen, bp.PaymentRulePO, doc.docbasetype " +
                    " from z_transfersaldo hdr " +
                    " inner join c_bpartner bp on hdr.c_bpartner_id = bp.c_bpartner_id " +
                    " inner join c_doctype doc on hdr.c_doctype_id = doc.c_doctype_id " +
                    " inner join zv_financial_tsopen iop on hdr.z_transfersaldo_id = iop.z_transfersaldo_id " +
                    " where hdr.ad_client_id =" + this.getAD_Client_ID() +
                    " and hdr.ad_org_id =" + this.getAD_Org_ID() +
                    " and hdr.issotrx='N' " +
                    " and hdr.ispaid='N' " +
                    " and hdr.docstatus='CO' " +
                    " and iop.amtopen > 0 " +
                    " and hdr.z_transfersaldo_id not in (select coalesce(z_transfersaldo_id,0) as transf_id from z_generaordenpagolin " +
                    " where z_transfersaldo_id is not null " +
                    " and z_generaordenpago_id =" + this.get_ID() + ") " +
                    /*
                    " and hdr.z_transfersaldo_id not in (select z_transfersaldo_id from z_ordenpagolin a " +
                    " inner join z_ordenpago b on a.z_ordenpago_id = b.z_ordenpago_id " +
                    " where z_transfersaldo_id is not null and b.docstatus='CO') " +

                     */
                    whereClause +
                    " order by hdr.c_bpartner_id ";

            int cBPartnerIDAux = 0;
            MZGeneraOrdenPagoSocio ordenPagoSocio = null;

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
                        ordenPagoSocio.setName(partner.getName());
                        ordenPagoSocio.setC_Currency_ID(this.getC_Currency_ID());
                        ordenPagoSocio.setTotalAmt(Env.ZERO);
                        ordenPagoSocio.saveEx();
                    }
                }

                BigDecimal amtDocument = rs.getBigDecimal("grandtotal");
                BigDecimal amtOpen = rs.getBigDecimal("amtopen");

                // Si la invoice asociada a esta transferencia es del tipo nota de credito, tengo que cambiar
                // el signo del monto de esta linea (esta transferencia se comporta como una nota de credito)
                MInvoice invoice = new MInvoice(getCtx(), rs.getInt("c_invoice_id"), null);
                if ((invoice != null) && (invoice.get_ID() > 0)){
                    MDocType docTypeInvoice = (MDocType) invoice.getC_DocTypeTarget();
                    if (docTypeInvoice.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
                        amtDocument = amtDocument.negate();
                        amtOpen = amtOpen.negate();
                    }
                }

                MZGeneraOrdenPagoLin ordenPagoLin = new MZGeneraOrdenPagoLin(getCtx(), 0, get_TrxName());
                ordenPagoLin.setZ_GeneraOrdenPago_ID(this.get_ID());
                ordenPagoLin.setZ_GeneraOrdenPagoSocio_ID(ordenPagoSocio.get_ID());
                ordenPagoLin.setAmtDocument(amtDocument);
                ordenPagoLin.setAmtOpen(amtOpen);
                ordenPagoLin.setAmtAllocation(amtOpen);
                ordenPagoLin.setC_Currency_ID(rs.getInt("c_currency_id"));
                ordenPagoLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
                ordenPagoLin.setDateDoc(rs.getTimestamp("datedoc"));
                ordenPagoLin.setDueDateDoc(dueDate);
                ordenPagoLin.setDocumentNoRef(rs.getString("documentno"));
                ordenPagoLin.setDueDateMedioPago(ordenPagoLin.getDueDateDoc());
                ordenPagoLin.setEstadoAprobacion("APROBADO");
                ordenPagoLin.setZ_TransferSaldo_ID(rs.getInt("z_transfersaldo_id"));
                if (rs.getInt("C_BPartnerRelation_ID") > 0){
                    ordenPagoLin.setC_BPartnerRelation_ID(rs.getInt("C_BPartnerRelation_ID"));
                }

                // Seteo medio de pago según el socio de negocio en caso de tener uno asociado.
                if (rs.getString("PaymentRulePO") != null){
                    medioPago = MZMedioPago.getByValue(getCtx(), rs.getString("PaymentRulePO"), null);
                    if ((medioPago == null) || (medioPago.get_ID() <= 0)){
                        medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                    }
                }
                else{
                    medioPago = MZMedioPago.getByValue(getCtx(), "S", null);
                }
                ordenPagoLin.setZ_MedioPago_ID(medioPago.get_ID());

                ordenPagoLin.setTieneDtosNC(false);
                ordenPagoLin.setResguardoEmitido(false);
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
            MClient client = new MClient(getCtx(), this.getAD_Client_ID(), null);

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

            // Hash para cotizaciones de monedas
            HashMap<Integer, Integer> hashCurrency = new HashMap<Integer, Integer>();

            // Obtengo y recorro socios de negocio considerados en este proceso
            List<MZGeneraOrdenPagoSocio> ordenPagoSocios = this.getSocios();
            for (MZGeneraOrdenPagoSocio ordenPagoSocio: ordenPagoSocios){

                // Obtengo lineas con documentos a considerar para este socio de negocio en la generacion de la orden de pago
                List<MZGeneraOrdenPagoLin> generaLins = ordenPagoSocio.getSelectedDocuments();

                // Si no tengo documentos seleccionados para este socio de negocio, no hay orden de pago
                if (generaLins.size() <= 0){
                    continue;
                }

                // Hashmap de montos a pagar por medio de pago a considerar en esta orden de pago.
                // Por ejemplo puede aceptar cheque y transferencia para esta orden.
                HashMap<Integer, BigDecimal> hashMediosPago = new HashMap<Integer, BigDecimal>();

                // Creo nuevo cabezal de orden de pago
                MZOrdenPago ordenPago = new MZOrdenPago(getCtx(), 0, get_TrxName());
                ordenPago.setZ_GeneraOrdenPago_ID(this.get_ID());
                ordenPago.setAD_Org_ID(this.getAD_Org_ID());
                ordenPago.setC_BPartner_ID(ordenPagoSocio.getC_BPartner_ID());
                ordenPago.setC_Currency_ID(cCurrencyBankAccount);
                ordenPago.setC_DocType_ID(docOP.get_ID());
                ordenPago.setDateDoc(this.getDateDoc());
                ordenPago.setTotalAmt(Env.ZERO);
                ordenPago.setDescription("Número de Generación : " + this.getDocumentNo());
                ordenPago.setIsPaid(false);
                ordenPago.saveEx();

                BigDecimal totalPago = Env.ZERO;
                Timestamp maxDueDate = fechaHoy;

                // Recorro lineas con documentos a considerar para este socio de negocio
                for (MZGeneraOrdenPagoLin generaLin: generaLins){

                    // Si el documento de esta linea se corresponde con una invoice
                    if (generaLin.getC_Invoice_ID() > 0){

                        MDocType docType = (MDocType) generaLin.getC_DocType();

                        // Refreso dato para esta invoice con respecto a si tiene o no un resguardo
                        generaLin.setResguardoEmitido(MZResguardoSocio.invoiceTieneResguardo(getCtx(), generaLin.getC_Invoice_ID(), get_TrxName()));
                        generaLin.saveEx();

                        // Valido que tenga resguardo.
                        // En caso de no tenerlo, verifico si aplican retenciones para esta invoice o no, en cuyo caso aviso y salgo.
                        if (!generaLin.isResguardoEmitido()){

                            // Si es factura (no es nota de credito)
                            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){

                                // Si el total de la factura no es CERO, sigo verificando
                                if (generaLin.getAmtDocument().compareTo(Env.ZERO) > 0){
                                    // No tiene resguardo emitido, verifico si para esta invoice se deben aplicar retenciones
                                    boolean aplicaReteneciones = MZRetencionSocio.invoiceAplicanRetenciones(getCtx(), generaLin.getC_Invoice_ID(), get_TrxName());
                                    if (aplicaReteneciones) {
                                        MBPartner partner = (MBPartner) ordenPago.getC_BPartner();
                                        return " No es posible generar orden de pago para el Socio de Negocio : " + partner.getName() + "\n" +
                                                " El mismo tiene un comprobante que requiere la emisión de resguardo : " + generaLin.getDocumentNoRef();
                                    }
                                }
                            }
                        }

                        // Si esta invoice tiene marcado que Lleva Nota de Cŕedito al Pago
                        if (generaLin.isTieneDtosNC()){
                            // Si es factura de compra
                            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
                                // Verifico si esta factura esta referenciada en algun documento de nota de credito
                                // Si no esta, aviso.
								/*
								if (!ComercialUtils.isInvoiceReferenced(getCtx(), generaLin.getC_Invoice_ID(), get_TrxName())){
									MBPartner partner = (MBPartner) ordenPago.getC_BPartner();
									return " No es posible generar orden de pago para el Socio de Negocio : " + partner.getName() + "\n" +
											" El mismo tiene un comprobante que requiere de una Nota de Crédito al Pago : " + generaLin.getDocumentNoRef();
								}
								*/
                            }
                        }
                    }

                    // Genero linea de orden de pago para este documento
                    MZOrdenPagoLin ordenPagoLin = new MZOrdenPagoLin(getCtx(), 0, get_TrxName());
                    ordenPagoLin.setZ_OrdenPago_ID(ordenPago.get_ID());
                    ordenPagoLin.setAD_Org_ID(ordenPago.getAD_Org_ID());
                    ordenPagoLin.setAmtAllocation(generaLin.getAmtAllocation());
                    ordenPagoLin.setAmtDocument(generaLin.getAmtDocument());
                    ordenPagoLin.setAmtOpen(generaLin.getAmtOpen());
                    ordenPagoLin.setC_Currency_ID(generaLin.getC_Currency_ID());
                    ordenPagoLin.setC_DocType_ID(generaLin.getC_DocType_ID());
                    ordenPagoLin.setDateDoc(generaLin.getDateDoc());
                    ordenPagoLin.setDueDateDoc(generaLin.getDueDateDoc());
                    ordenPagoLin.setDocumentNoRef(generaLin.getDocumentNoRef());
                    if (generaLin.getC_InvoicePaySchedule_ID() > 0){
                        ordenPagoLin.setC_InvoicePaySchedule_ID(generaLin.getC_InvoicePaySchedule_ID());
                    }
                    if (generaLin.getC_Invoice_ID() > 0){
                        ordenPagoLin.setC_Invoice_ID(generaLin.getC_Invoice_ID());
                    }
                    if (generaLin.getZ_ResguardoSocio_ID() > 0){
                        ordenPagoLin.setZ_ResguardoSocio_ID(generaLin.getZ_ResguardoSocio_ID());
                    }
                    if (generaLin.getZ_TransferSaldo_ID() > 0){
                        // Verifico que exista la transferencia de saldo en el sistema.
                        // Pasa seguido que la eliminan.
                        MZTransferSaldo transferSaldo = (MZTransferSaldo) generaLin.getZ_TransferSaldo();
                        if ((transferSaldo == null) || (transferSaldo.get_ID() <= 0)){
                            return "El Documento de Transferencia de Saldo número : " + generaLin.getDocumentNoRef() +
                                    " fue eliminado del sistema. Tiene que eliminarlo de los documentos a procesar" +
                                    " antes de generar las ordenes de pago.";
                        }
                        ordenPagoLin.setZ_TransferSaldo_ID(generaLin.getZ_TransferSaldo_ID());
                    }
                    if (generaLin.getZ_Pago_ID() > 0){
                        ordenPagoLin.setZ_Pago_ID(generaLin.getZ_Pago_ID());
                    }

                    // Agrego moneda para cotizaciones
                    if (!hashCurrency.containsKey(generaLin.getC_Currency_ID())){
                        hashCurrency.put(generaLin.getC_Currency_ID(), generaLin.getC_Currency_ID());
                    }

                    // Pagos multimoneda.
                    // Si este documento tiene moneda distinta a la moneda de la Orden de Pago,
                    // dabo traducir monto a pagar desde moneda documento a moneda orden de pago.
                    // La tasa de cambio a considerar es la correspondiente a la fecha de emisión de la orden de pago.
                    if (generaLin.getC_Currency_ID() != ordenPago.getC_Currency_ID()){

                        boolean amtMTCalculated = false;

                        // Para el caso de resguardos que tienen monto en moneda extranjera, no debo recalcular dicho monto a una tasa de cambio,
                        // cuando la orden de pago también es en moneda extranjera.
                        // Simplemente traigo el monto en moneda extranjera del resguardo al momento de su emisión
                        if (ordenPago.getC_Currency_ID() != client.getC_Currency_ID()){
                            if (generaLin.getZ_ResguardoSocio_ID() > 0){
                                MZResguardoSocio resguardoSocio = (MZResguardoSocio) generaLin.getZ_ResguardoSocio();
                                if ((resguardoSocio.getTotalAmtME() != null) && (resguardoSocio.getTotalAmtME().compareTo(Env.ZERO) > 0)){
                                    amtMTCalculated = true;
                                    ordenPagoLin.setMultiplyRate(null);
                                    ordenPagoLin.setAmtAllocationMT(resguardoSocio.getTotalAmtME().negate());
                                }
                            }
                        }

                        if (!amtMTCalculated){
                            // Obtengo tasa de cambio
                            BigDecimal multiplyRate = CurrencyUtils.getCurrencyRateToAcctSchemaCurrency(getCtx(), ordenPago.getAD_Client_ID(), 0,
                                    generaLin.getC_Currency_ID(), ordenPago.getC_Currency_ID(), 114, ordenPago.getDateDoc(), null);
                            if (multiplyRate == null){
                                return "No se pudo obtener Tasa de Cambio para Moneda : " + generaLin.getC_Currency_ID() + ", Fecha : " + ordenPago.getDateDoc().toString();
                            }
                            ordenPagoLin.setMultiplyRate(multiplyRate);
                            if (ordenPago.getC_Currency_ID() == client.getC_Currency_ID()){
                                ordenPagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocation().multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            else{
                                ordenPagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocation().divide(multiplyRate, 2, BigDecimal.ROUND_HALF_UP));
                            }
                        }
                    }
                    else{
                        ordenPagoLin.setMultiplyRate(Env.ONE);
                        ordenPagoLin.setAmtAllocationMT(ordenPagoLin.getAmtAllocation());
                    }

                    ordenPagoLin.saveEx();

                    totalPago = totalPago.add(ordenPagoLin.getAmtAllocationMT());

                    // Acumulo monto en medio de pago
                    if (hashMediosPago.containsKey(generaLin.getZ_MedioPago_ID())){
                        BigDecimal amtMedioPago = ((BigDecimal) hashMediosPago.get(generaLin.getZ_MedioPago_ID())).add(ordenPagoLin.getAmtAllocationMT());
                        hashMediosPago.put(generaLin.getZ_MedioPago_ID(), amtMedioPago);
                    }
                    else{
                        hashMediosPago.put(generaLin.getZ_MedioPago_ID(), ordenPagoLin.getAmtAllocationMT());
                    }

                    if (generaLin.getDueDateMedioPago() != null){
                        if (generaLin.getDueDateMedioPago().after(fechaHoy)){
                            maxDueDate = generaLin.getDueDateMedioPago();
                        }
                    }
                }

                // Actualizo monto total de orden de pago
                ordenPago.setTotalAmt(totalPago);
                ordenPago.setProcessing(false);
                ordenPago.saveEx();

                // Si no tengo marca de sugerir medios de pago emitidos y cargados al sistema de manera masiva por interface
                if (!this.isProcesaCargaMasiva()){
                    // Creo lineas para cada medio de pago a considerarse al completar la orden de pago
                    if (hashMediosPago != null){
                        for (HashMap.Entry<Integer, BigDecimal> entry : hashMediosPago.entrySet()){
                            MZOrdenPagoMedio ordenPagoMedio = new MZOrdenPagoMedio(getCtx(), 0, get_TrxName());
                            ordenPagoMedio.setZ_GeneraOrdenPago_ID(this.get_ID());
                            ordenPagoMedio.setAD_Org_ID(ordenPago.getAD_Org_ID());
                            ordenPagoMedio.setZ_OrdenPago_ID(ordenPago.get_ID());
                            ordenPagoMedio.setC_BankAccount_ID(this.getC_BankAccount_ID());
                            ordenPagoMedio.setDueDate(maxDueDate);
                            ordenPagoMedio.setC_Currency_ID(ordenPago.getC_Currency_ID());
                            ordenPagoMedio.setTotalAmt(entry.getValue());
                            ordenPagoMedio.setZ_MedioPago_ID(entry.getKey());
                            ordenPagoMedio.setZ_MedioPagoFolio_ID(this.getZ_MedioPagoFolio_ID());
                            ordenPagoMedio.setC_BPartner_ID(ordenPago.getC_BPartner_ID());

                            MZMedioPago medioPago = (MZMedioPago) ordenPagoMedio.getZ_MedioPago();
                            ordenPagoMedio.setTieneBanco(medioPago.isTieneBanco());
                            ordenPagoMedio.setTieneCaja(medioPago.isTieneCaja());
                            ordenPagoMedio.setTieneCtaBco(medioPago.isTieneCtaBco());
                            ordenPagoMedio.setTieneFecEmi(medioPago.isTieneFecEmi());
                            ordenPagoMedio.setTieneFecVenc(medioPago.isTieneFecVenc());
                            ordenPagoMedio.setTieneFolio(medioPago.isTieneFolio());
                            ordenPagoMedio.setTieneNroRef(medioPago.isTieneNroRef());

                            if (this.isSugerirFecha()){
                                if (ordenPagoMedio.isTieneFecEmi()){
                                    if (ordenPagoMedio.getDueDate() != null){
                                        if (!ordenPagoMedio.getDueDate().before(this.getDateDoc())){
                                            ordenPagoMedio.setDateEmitted(ordenPago.getDateDoc());
                                        }
                                    }
                                    else{
                                        ordenPagoMedio.setDateEmitted(ordenPago.getDateDoc());
                                    }
                                }
                            }

                            ordenPagoMedio.saveEx();
                        }
                    }
                }
                else{
                    // Cargo medios de pago emitidos para este socio de negocio y que fueron cargados masivamente por interface.
                    message = this.setOrdenMediosPagoCargaMasiva(ordenPago);
                    if (message != null){
                        return message;
                    }

                }

                // Si tengo monedas, actualizo tabla de monedas de este pago/cobro
                if (hashCurrency.size() > 0){
                    // Cargo monedas con tasa de cambio a la fecha para esta orden de pago
                    message = MZPagoMoneda.setMonedasOrdenPago(getCtx(), ordenPago.get_ID(), hashCurrency, get_TrxName());
                    if (message != null){
                        return message;
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
     * Obtiene y carga medios de pago provenientes de cargas masivas por interface. Los deja asociados a la orden
     * de pago recibida.
     * Xpande. Created by Gabriel Vila on 5/10/19.
     * @param ordenPago
     * @return
     */
    private String setOrdenMediosPagoCargaMasiva(MZOrdenPago ordenPago) {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.z_mediopagoitem_id, a.dateemitted, a.duedate, a.c_currency_id, a.totalamt, " +
                    " a.z_mediopago_id, a.z_mediopagofolio_id " +
                    " from z_mediopagoitem a " +
                    " inner join z_emisionmediopago b on a.z_emisionmediopago_id = b.z_emisionmediopago_id " +
                    " where a.c_bpartner_id =" + ordenPago.getC_BPartner_ID() +
                    " and a.ad_org_id =" + ordenPago.getAD_Org_ID() +
                    " and a.c_bankaccount_id =" + this.getC_BankAccount_ID() +
                    " and a.emitido ='Y' and a.entregado ='N' and a.anulado ='N' " +
                    " and b.docstatus ='CO' and b.z_loadmediopago_id > 0 " +
                    " order by a.dateemitted ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MZOrdenPagoMedio ordenPagoMedio = new MZOrdenPagoMedio(getCtx(), 0, get_TrxName());
                ordenPagoMedio.setZ_GeneraOrdenPago_ID(this.get_ID());
                ordenPagoMedio.setAD_Org_ID(ordenPago.getAD_Org_ID());
                ordenPagoMedio.setZ_OrdenPago_ID(ordenPago.get_ID());
                ordenPagoMedio.setC_BankAccount_ID(this.getC_BankAccount_ID());
                ordenPagoMedio.setDueDate(rs.getTimestamp("duedate"));
                ordenPagoMedio.setC_Currency_ID(ordenPago.getC_Currency_ID());
                ordenPagoMedio.setTotalAmt(rs.getBigDecimal("totalamt"));
                ordenPagoMedio.setZ_MedioPago_ID(rs.getInt("z_mediopago_id"));
                ordenPagoMedio.setZ_MedioPagoItem_ID(rs.getInt("z_mediopagoitem_id"));

                if (rs.getInt("z_mediopagofolio_id") > 0){
                    ordenPagoMedio.setZ_MedioPagoFolio_ID(rs.getInt("z_mediopagofolio_id"));
                }

                ordenPagoMedio.setC_BPartner_ID(ordenPago.getC_BPartner_ID());

                MZMedioPago medioPago = (MZMedioPago) ordenPagoMedio.getZ_MedioPago();
                ordenPagoMedio.setTieneBanco(medioPago.isTieneBanco());
                ordenPagoMedio.setTieneCaja(medioPago.isTieneCaja());
                ordenPagoMedio.setTieneCtaBco(medioPago.isTieneCtaBco());
                ordenPagoMedio.setTieneFecEmi(medioPago.isTieneFecEmi());
                ordenPagoMedio.setTieneFecVenc(medioPago.isTieneFecVenc());
                ordenPagoMedio.setTieneFolio(medioPago.isTieneFolio());
                ordenPagoMedio.setTieneNroRef(medioPago.isTieneNroRef());
                ordenPagoMedio.saveEx();
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
     * Obtiene y retorna socios a considerar en este proceso.
     * Xpande. Created by Gabriel Vila on 8/16/17.
     * @return
     */
    private List<MZGeneraOrdenPagoSocio> getSocios() {

        String whereClause = X_Z_GeneraOrdenPagoSocio.COLUMNNAME_Z_GeneraOrdenPago_ID + " =" + this.get_ID();

        List<MZGeneraOrdenPagoSocio> lines = new Query(getCtx(), I_Z_GeneraOrdenPagoSocio.Table_Name, whereClause, get_TrxName()).setOrderBy(" Name ").list();

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


			/*
			// Medio de pago por defecto por ahora CHEQUE
			MZMedioPago medioPago = MZMedioPago.getByValue(getCtx(), "S", null);

			// Actualizo informacion de medios de pago que no esten seteados, en lineas de documentos a procesar
			action = " update z_generaordenpagolin set z_mediopago_id =" + medioPago.get_ID()
					+ " where z_generaordenpago_id =" + this.get_ID()
					+ " and isselected ='Y' ";

			DB.executeUpdateEx(action, get_TrxName());
			*/

            // Me aseguro que no queden fechas de vencimiento de medios de pago menores a hoy en lineas de documentos a procesar
            action = " update z_generaordenpagolin set duedatemediopago ='" + fechaHoy + "' "
                    + " where z_generaordenpago_id =" + this.get_ID()
                    + " and isselected ='Y' "
                    + " and c_invoice_id > 0 "
                    + " and duedatemediopago <'" + fechaHoy + "'";
            //DB.executeUpdateEx(action, get_TrxName());

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

            // Si alguno de los distintos medios de pago a utilizarse para las ordenes, requiere folio
            boolean mediosPagoRequiereFolio = this.mediospagoRequiereFolio();

            if ((mediosPagoRequiereFolio) && (!this.isProcesaCargaMasiva())){
                if (this.getZ_MedioPagoFolio_ID() <= 0){
                    message = "Debe indicar Libreta para Cheques Diferidos.";
                    return message;
                }

                if (this.getZ_MedioPagoFolio_2_ID() <= 0){
                    message = "Debe indicar Libreta para Cheques Día";
                    return message;
                }
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

    /***
     * Metodo que verifica si alguno de los distintos medios de pago indicados en las lineas de documentos de esta generación,
     * tiene la marca que indica que requiere folio de medios de pago.
     * Xpande. Created by Gabriel Vila on 10/16/18.
     * @return
     */
    private boolean mediospagoRequiereFolio(){

        boolean result = false;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select distinct a.z_mediopago_id, mp.tienefolio " +
                    " from z_generaordenpagolin a " +
                    " inner join z_mediopago mp on a.z_mediopago_id = mp.z_mediopago_id " +
                    " where a.z_generaordenpago_id =" + this.get_ID();

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){
                if (rs.getString("tienefolio").equalsIgnoreCase("Y")){
                    result = true;
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

        return result;
    }

}
