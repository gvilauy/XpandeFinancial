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
/** Generated Model - DO NOT CHANGE */
package org.xpande.financial.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_Pago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_Pago extends PO implements I_Z_Pago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190627L;

    /** Standard Constructor */
    public X_Z_Pago (Properties ctx, int Z_Pago_ID, String trxName)
    {
      super (ctx, Z_Pago_ID, trxName);
      /** if (Z_Pago_ID == 0)
        {
			setAnticipo (false);
// N
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
// N
			setIsSOTrx (false);
// N
			setPayAmt (Env.ZERO);
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setReciboAnticipo (false);
// N
			setTieneOrdenPago (true);
// Y
			setTieneRecibo (true);
// Y
			setTotalMediosPago (Env.ZERO);
			setZ_Pago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_Pago (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_Pago[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtAnticipo.
		@param AmtAnticipo 
		Monto de Anticipos
	  */
	public void setAmtAnticipo (BigDecimal AmtAnticipo)
	{
		set_Value (COLUMNNAME_AmtAnticipo, AmtAnticipo);
	}

	/** Get AmtAnticipo.
		@return Monto de Anticipos
	  */
	public BigDecimal getAmtAnticipo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAnticipo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Anticipo.
		@param Anticipo 
		Si un determinado pago/cobro es solamente un anticipo y por lo tanto no tiene documentos a afectar
	  */
	public void setAnticipo (boolean Anticipo)
	{
		set_Value (COLUMNNAME_Anticipo, Boolean.valueOf(Anticipo));
	}

	/** Get Anticipo.
		@return Si un determinado pago/cobro es solamente un anticipo y por lo tanto no tiene documentos a afectar
	  */
	public boolean isAnticipo () 
	{
		Object oo = get_Value(COLUMNNAME_Anticipo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency_To() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID_To(), get_TrxName());	}

	/** Set Currency To.
		@param C_Currency_ID_To 
		Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_Value (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Currency To.
		@return Target currency
	  */
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set DateEmittedFrom.
		@param DateEmittedFrom 
		Desde fecha emision
	  */
	public void setDateEmittedFrom (Timestamp DateEmittedFrom)
	{
		set_Value (COLUMNNAME_DateEmittedFrom, DateEmittedFrom);
	}

	/** Get DateEmittedFrom.
		@return Desde fecha emision
	  */
	public Timestamp getDateEmittedFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEmittedFrom);
	}

	/** Set DateEmittedTo.
		@param DateEmittedTo 
		Hasta fecha de emisión
	  */
	public void setDateEmittedTo (Timestamp DateEmittedTo)
	{
		set_Value (COLUMNNAME_DateEmittedTo, DateEmittedTo);
	}

	/** Get DateEmittedTo.
		@return Hasta fecha de emisión
	  */
	public Timestamp getDateEmittedTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEmittedTo);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		throw new IllegalArgumentException ("DifferenceAmt is virtual column");	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set DueDateFrom.
		@param DueDateFrom 
		Desde fecha vencimiento
	  */
	public void setDueDateFrom (Timestamp DueDateFrom)
	{
		set_Value (COLUMNNAME_DueDateFrom, DueDateFrom);
	}

	/** Get DueDateFrom.
		@return Desde fecha vencimiento
	  */
	public Timestamp getDueDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDateFrom);
	}

	/** Set DueDateTo.
		@param DueDateTo 
		Hasta fecha de vencimiento
	  */
	public void setDueDateTo (Timestamp DueDateTo)
	{
		set_Value (COLUMNNAME_DueDateTo, DueDateTo);
	}

	/** Get DueDateTo.
		@return Hasta fecha de vencimiento
	  */
	public Timestamp getDueDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDateTo);
	}

	/** Set ID_Virtual.
		@param ID_Virtual 
		Identificador virtual para tablas
	  */
	public void setID_Virtual (String ID_Virtual)
	{
		throw new IllegalArgumentException ("ID_Virtual is virtual column");	}

	/** Get ID_Virtual.
		@return Identificador virtual para tablas
	  */
	public String getID_Virtual () 
	{
		return (String)get_Value(COLUMNNAME_ID_Virtual);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set NroRecibo.
		@param NroRecibo 
		Número de Recibo asociado a un pago/cobro en módulo Financiero
	  */
	public void setNroRecibo (String NroRecibo)
	{
		set_Value (COLUMNNAME_NroRecibo, NroRecibo);
	}

	/** Get NroRecibo.
		@return Número de Recibo asociado a un pago/cobro en módulo Financiero
	  */
	public String getNroRecibo () 
	{
		return (String)get_Value(COLUMNNAME_NroRecibo);
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ProcessButton.
		@param ProcessButton ProcessButton	  */
	public void setProcessButton (String ProcessButton)
	{
		set_Value (COLUMNNAME_ProcessButton, ProcessButton);
	}

	/** Get ProcessButton.
		@return ProcessButton	  */
	public String getProcessButton () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton);
	}

	/** Set ProcessButton2.
		@param ProcessButton2 
		Botón de Proceso
	  */
	public void setProcessButton2 (String ProcessButton2)
	{
		set_Value (COLUMNNAME_ProcessButton2, ProcessButton2);
	}

	/** Get ProcessButton2.
		@return Botón de Proceso
	  */
	public String getProcessButton2 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton2);
	}

	/** Set ProcessButton3.
		@param ProcessButton3 
		Botón para proceso
	  */
	public void setProcessButton3 (String ProcessButton3)
	{
		set_Value (COLUMNNAME_ProcessButton3, ProcessButton3);
	}

	/** Get ProcessButton3.
		@return Botón para proceso
	  */
	public String getProcessButton3 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton3);
	}

	/** Set ProcessButton4.
		@param ProcessButton4 
		Botón de Proceso
	  */
	public void setProcessButton4 (String ProcessButton4)
	{
		set_Value (COLUMNNAME_ProcessButton4, ProcessButton4);
	}

	/** Get ProcessButton4.
		@return Botón de Proceso
	  */
	public String getProcessButton4 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton4);
	}

	/** Set ProcessButton5.
		@param ProcessButton5 
		Botón de Proceso
	  */
	public void setProcessButton5 (String ProcessButton5)
	{
		set_Value (COLUMNNAME_ProcessButton5, ProcessButton5);
	}

	/** Get ProcessButton5.
		@return Botón de Proceso
	  */
	public String getProcessButton5 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton5);
	}

	/** Set ProcessButton6.
		@param ProcessButton6 
		Botón de Proceso
	  */
	public void setProcessButton6 (String ProcessButton6)
	{
		set_Value (COLUMNNAME_ProcessButton6, ProcessButton6);
	}

	/** Get ProcessButton6.
		@return Botón de Proceso
	  */
	public String getProcessButton6 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton6);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ReciboAnticipo.
		@param ReciboAnticipo 
		Si un determinado pago/cobro es solamente un recibo hecho contra un anticipo
	  */
	public void setReciboAnticipo (boolean ReciboAnticipo)
	{
		set_Value (COLUMNNAME_ReciboAnticipo, Boolean.valueOf(ReciboAnticipo));
	}

	/** Get ReciboAnticipo.
		@return Si un determinado pago/cobro es solamente un recibo hecho contra un anticipo
	  */
	public boolean isReciboAnticipo () 
	{
		Object oo = get_Value(COLUMNNAME_ReciboAnticipo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Reference.
		@param Reference 
		Reference for this record
	  */
	public void setReference (String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Reference.
		@return Reference for this record
	  */
	public String getReference () 
	{
		return (String)get_Value(COLUMNNAME_Reference);
	}

	/** Set TieneOrdenPago.
		@param TieneOrdenPago 
		Si tiene o no ordenes de pago asociadas
	  */
	public void setTieneOrdenPago (boolean TieneOrdenPago)
	{
		set_Value (COLUMNNAME_TieneOrdenPago, Boolean.valueOf(TieneOrdenPago));
	}

	/** Get TieneOrdenPago.
		@return Si tiene o no ordenes de pago asociadas
	  */
	public boolean isTieneOrdenPago () 
	{
		Object oo = get_Value(COLUMNNAME_TieneOrdenPago);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneRecibo.
		@param TieneRecibo 
		Si un documento de pago/cobro tiene recibo asociado en módulo Financiero
	  */
	public void setTieneRecibo (boolean TieneRecibo)
	{
		set_Value (COLUMNNAME_TieneRecibo, Boolean.valueOf(TieneRecibo));
	}

	/** Get TieneRecibo.
		@return Si un documento de pago/cobro tiene recibo asociado en módulo Financiero
	  */
	public boolean isTieneRecibo () 
	{
		Object oo = get_Value(COLUMNNAME_TieneRecibo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TotalMediosPago.
		@param TotalMediosPago 
		Monto total de medios de pago considerados en un documento
	  */
	public void setTotalMediosPago (BigDecimal TotalMediosPago)
	{
		set_Value (COLUMNNAME_TotalMediosPago, TotalMediosPago);
	}

	/** Get TotalMediosPago.
		@return Monto total de medios de pago considerados en un documento
	  */
	public BigDecimal getTotalMediosPago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalMediosPago);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_Z_OrdenPago getZ_OrdenPago_To() throws RuntimeException
    {
		return (I_Z_OrdenPago)MTable.get(getCtx(), I_Z_OrdenPago.Table_Name)
			.getPO(getZ_OrdenPago_To_ID(), get_TrxName());	}

	/** Set Z_OrdenPago_To_ID.
		@param Z_OrdenPago_To_ID 
		Referencia a un documento de Orden de Pago
	  */
	public void setZ_OrdenPago_To_ID (int Z_OrdenPago_To_ID)
	{
		if (Z_OrdenPago_To_ID < 1) 
			set_Value (COLUMNNAME_Z_OrdenPago_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OrdenPago_To_ID, Integer.valueOf(Z_OrdenPago_To_ID));
	}

	/** Get Z_OrdenPago_To_ID.
		@return Referencia a un documento de Orden de Pago
	  */
	public int getZ_OrdenPago_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPago_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_Pago ID.
		@param Z_Pago_ID Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID)
	{
		if (Z_Pago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_Pago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_Pago_ID, Integer.valueOf(Z_Pago_ID));
	}

	/** Get Z_Pago ID.
		@return Z_Pago ID	  */
	public int getZ_Pago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Pago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_Pago getZ_Pago_To() throws RuntimeException
    {
		return (I_Z_Pago)MTable.get(getCtx(), I_Z_Pago.Table_Name)
			.getPO(getZ_Pago_To_ID(), get_TrxName());	}

	/** Set Z_Pago_To_ID.
		@param Z_Pago_To_ID 
		Referencia a un documento de Pago
	  */
	public void setZ_Pago_To_ID (int Z_Pago_To_ID)
	{
		if (Z_Pago_To_ID < 1) 
			set_Value (COLUMNNAME_Z_Pago_To_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Pago_To_ID, Integer.valueOf(Z_Pago_To_ID));
	}

	/** Get Z_Pago_To_ID.
		@return Referencia a un documento de Pago
	  */
	public int getZ_Pago_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Pago_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}