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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_GeneraOrdenPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_GeneraOrdenPago extends PO implements I_Z_GeneraOrdenPago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190510L;

    /** Standard Constructor */
    public X_Z_GeneraOrdenPago (Properties ctx, int Z_GeneraOrdenPago_ID, String trxName)
    {
      super (ctx, Z_GeneraOrdenPago_ID, trxName);
      /** if (Z_GeneraOrdenPago_ID == 0)
        {
			setC_Currency_ID (0);
// 142
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setGroupLastDue (true);
// Y
			setIsApproved (false);
// N
			setProcesaCargaMasiva (false);
// N
			setProcessed (false);
// N
			setTipoFiltroSocioPago (null);
// INCLUIR
			setZ_GeneraOrdenPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GeneraOrdenPago (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GeneraOrdenPago[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency_2() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_2_ID(), get_TrxName());	}

	/** Set C_Currency_2_ID.
		@param C_Currency_2_ID 
		Moneda secundaria para procesos
	  */
	public void setC_Currency_2_ID (int C_Currency_2_ID)
	{
		if (C_Currency_2_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_2_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_2_ID, Integer.valueOf(C_Currency_2_ID));
	}

	/** Get C_Currency_2_ID.
		@return Moneda secundaria para procesos
	  */
	public int getC_Currency_2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_2_ID);
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
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
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

	/** Set GroupLastDue.
		@param GroupLastDue 
		Agrupar por ultimo vencimiento
	  */
	public void setGroupLastDue (boolean GroupLastDue)
	{
		set_Value (COLUMNNAME_GroupLastDue, Boolean.valueOf(GroupLastDue));
	}

	/** Get GroupLastDue.
		@return Agrupar por ultimo vencimiento
	  */
	public boolean isGroupLastDue () 
	{
		Object oo = get_Value(COLUMNNAME_GroupLastDue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** PaymentRulePO AD_Reference_ID=195 */
	public static final int PAYMENTRULEPO_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULEPO_Cash = "B";
	/** Credit Card = K */
	public static final String PAYMENTRULEPO_CreditCard = "K";
	/** DEPOSITO DIRECTO = T */
	public static final String PAYMENTRULEPO_DEPOSITODIRECTO = "T";
	/** Check = S */
	public static final String PAYMENTRULEPO_Check = "S";
	/** On Credit = P */
	public static final String PAYMENTRULEPO_OnCredit = "P";
	/** Direct Debit = D */
	public static final String PAYMENTRULEPO_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULEPO_Mixed = "M";
	/** TARJETA CORPORATIVA = C */
	public static final String PAYMENTRULEPO_TARJETACORPORATIVA = "C";
	/** FONDO FIJO = F */
	public static final String PAYMENTRULEPO_FONDOFIJO = "F";
	/** CANJE = J */
	public static final String PAYMENTRULEPO_CANJE = "J";
	/** TRANSFERENCIA BANCARIA = R */
	public static final String PAYMENTRULEPO_TRANSFERENCIABANCARIA = "R";
	/** Set Payment Rule.
		@param PaymentRulePO 
		Purchase payment option
	  */
	public void setPaymentRulePO (String PaymentRulePO)
	{

		set_Value (COLUMNNAME_PaymentRulePO, PaymentRulePO);
	}

	/** Get Payment Rule.
		@return Purchase payment option
	  */
	public String getPaymentRulePO () 
	{
		return (String)get_Value(COLUMNNAME_PaymentRulePO);
	}

	/** Set ProcesaCargaMasiva.
		@param ProcesaCargaMasiva 
		Si considera procesos de carga masiva o no
	  */
	public void setProcesaCargaMasiva (boolean ProcesaCargaMasiva)
	{
		set_Value (COLUMNNAME_ProcesaCargaMasiva, Boolean.valueOf(ProcesaCargaMasiva));
	}

	/** Get ProcesaCargaMasiva.
		@return Si considera procesos de carga masiva o no
	  */
	public boolean isProcesaCargaMasiva () 
	{
		Object oo = get_Value(COLUMNNAME_ProcesaCargaMasiva);
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

	/** TipoFiltroSocioPago AD_Reference_ID=1000016 */
	public static final int TIPOFILTROSOCIOPAGO_AD_Reference_ID=1000016;
	/** EXCLUIR SOCIOS DE NEGOCIO FILTRO = EXCLUIR */
	public static final String TIPOFILTROSOCIOPAGO_EXCLUIRSOCIOSDENEGOCIOFILTRO = "EXCLUIR";
	/** INCLUIR SOCIOS DE NEGOCIO FILTRO = INCLUIR */
	public static final String TIPOFILTROSOCIOPAGO_INCLUIRSOCIOSDENEGOCIOFILTRO = "INCLUIR";
	/** Set TipoFiltroSocioPago.
		@param TipoFiltroSocioPago 
		Tipo de filtro para socios de negocio al momento de generar pagos
	  */
	public void setTipoFiltroSocioPago (String TipoFiltroSocioPago)
	{

		set_Value (COLUMNNAME_TipoFiltroSocioPago, TipoFiltroSocioPago);
	}

	/** Get TipoFiltroSocioPago.
		@return Tipo de filtro para socios de negocio al momento de generar pagos
	  */
	public String getTipoFiltroSocioPago () 
	{
		return (String)get_Value(COLUMNNAME_TipoFiltroSocioPago);
	}

	/** Set Z_GeneraOrdenPago ID.
		@param Z_GeneraOrdenPago_ID Z_GeneraOrdenPago ID	  */
	public void setZ_GeneraOrdenPago_ID (int Z_GeneraOrdenPago_ID)
	{
		if (Z_GeneraOrdenPago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraOrdenPago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraOrdenPago_ID, Integer.valueOf(Z_GeneraOrdenPago_ID));
	}

	/** Get Z_GeneraOrdenPago ID.
		@return Z_GeneraOrdenPago ID	  */
	public int getZ_GeneraOrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraOrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio_2() throws RuntimeException
    {
		return (I_Z_MedioPagoFolio)MTable.get(getCtx(), I_Z_MedioPagoFolio.Table_Name)
			.getPO(getZ_MedioPagoFolio_2_ID(), get_TrxName());	}

	/** Set Z_MedioPagoFolio_2_ID.
		@param Z_MedioPagoFolio_2_ID 
		Folio secundario de medios de pago
	  */
	public void setZ_MedioPagoFolio_2_ID (int Z_MedioPagoFolio_2_ID)
	{
		if (Z_MedioPagoFolio_2_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_2_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_2_ID, Integer.valueOf(Z_MedioPagoFolio_2_ID));
	}

	/** Get Z_MedioPagoFolio_2_ID.
		@return Folio secundario de medios de pago
	  */
	public int getZ_MedioPagoFolio_2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoFolio_2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio() throws RuntimeException
    {
		return (I_Z_MedioPagoFolio)MTable.get(getCtx(), I_Z_MedioPagoFolio.Table_Name)
			.getPO(getZ_MedioPagoFolio_ID(), get_TrxName());	}

	/** Set Z_MedioPagoFolio ID.
		@param Z_MedioPagoFolio_ID Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID (int Z_MedioPagoFolio_ID)
	{
		if (Z_MedioPagoFolio_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_ID, Integer.valueOf(Z_MedioPagoFolio_ID));
	}

	/** Get Z_MedioPagoFolio ID.
		@return Z_MedioPagoFolio ID	  */
	public int getZ_MedioPagoFolio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoFolio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}