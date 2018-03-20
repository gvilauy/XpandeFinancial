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

/** Generated Model for Z_InvoiceAfectacion
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_InvoiceAfectacion extends PO implements I_Z_InvoiceAfectacion, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180315L;

    /** Standard Constructor */
    public X_Z_InvoiceAfectacion (Properties ctx, int Z_InvoiceAfectacion_ID, String trxName)
    {
      super (ctx, Z_InvoiceAfectacion_ID, trxName);
      /** if (Z_InvoiceAfectacion_ID == 0)
        {
			setAD_Table_ID (0);
			setAmtAllocation (Env.ZERO);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_Invoice_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocumentNoRef (null);
			setIsValid (true);
// Y
			setRecord_ID (0);
			setZ_InvoiceAfectacion_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_InvoiceAfectacion (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_InvoiceAfectacion[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AmtAllocation.
		@param AmtAllocation 
		Monto afectación
	  */
	public void setAmtAllocation (BigDecimal AmtAllocation)
	{
		set_Value (COLUMNNAME_AmtAllocation, AmtAllocation);
	}

	/** Get AmtAllocation.
		@return Monto afectación
	  */
	public BigDecimal getAmtAllocation () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAllocation);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException
    {
		return (I_C_InvoicePaySchedule)MTable.get(getCtx(), I_C_InvoicePaySchedule.Table_Name)
			.getPO(getC_InvoicePaySchedule_ID(), get_TrxName());	}

	/** Set Invoice Payment Schedule.
		@param C_InvoicePaySchedule_ID 
		Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoicePaySchedule_ID, Integer.valueOf(C_InvoicePaySchedule_ID));
	}

	/** Get Invoice Payment Schedule.
		@return Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoicePaySchedule_ID);
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

	/** Set DocumentNoRef.
		@param DocumentNoRef 
		Numero de documento referenciado
	  */
	public void setDocumentNoRef (String DocumentNoRef)
	{
		set_Value (COLUMNNAME_DocumentNoRef, DocumentNoRef);
	}

	/** Get DocumentNoRef.
		@return Numero de documento referenciado
	  */
	public String getDocumentNoRef () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoRef);
	}

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenced Invoice.
		@param Ref_Invoice_ID Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
	}

	/** Get Referenced Invoice.
		@return Referenced Invoice	  */
	public int getRef_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_Afectacion getZ_Afectacion() throws RuntimeException
    {
		return (I_Z_Afectacion)MTable.get(getCtx(), I_Z_Afectacion.Table_Name)
			.getPO(getZ_Afectacion_ID(), get_TrxName());	}

	/** Set Z_Afectacion ID.
		@param Z_Afectacion_ID Z_Afectacion ID	  */
	public void setZ_Afectacion_ID (int Z_Afectacion_ID)
	{
		if (Z_Afectacion_ID < 1) 
			set_Value (COLUMNNAME_Z_Afectacion_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Afectacion_ID, Integer.valueOf(Z_Afectacion_ID));
	}

	/** Get Z_Afectacion ID.
		@return Z_Afectacion ID	  */
	public int getZ_Afectacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Afectacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_InvoiceAfectacion ID.
		@param Z_InvoiceAfectacion_ID Z_InvoiceAfectacion ID	  */
	public void setZ_InvoiceAfectacion_ID (int Z_InvoiceAfectacion_ID)
	{
		if (Z_InvoiceAfectacion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceAfectacion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceAfectacion_ID, Integer.valueOf(Z_InvoiceAfectacion_ID));
	}

	/** Get Z_InvoiceAfectacion ID.
		@return Z_InvoiceAfectacion ID	  */
	public int getZ_InvoiceAfectacion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_InvoiceAfectacion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException
    {
		return (I_Z_OrdenPago)MTable.get(getCtx(), I_Z_OrdenPago.Table_Name)
			.getPO(getZ_OrdenPago_ID(), get_TrxName());	}

	/** Set Z_OrdenPago ID.
		@param Z_OrdenPago_ID Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID (int Z_OrdenPago_ID)
	{
		if (Z_OrdenPago_ID < 1) 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, Integer.valueOf(Z_OrdenPago_ID));
	}

	/** Get Z_OrdenPago ID.
		@return Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_Pago getZ_Pago() throws RuntimeException
    {
		return (I_Z_Pago)MTable.get(getCtx(), I_Z_Pago.Table_Name)
			.getPO(getZ_Pago_ID(), get_TrxName());	}

	/** Set Z_Pago ID.
		@param Z_Pago_ID Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID)
	{
		if (Z_Pago_ID < 1) 
			set_Value (COLUMNNAME_Z_Pago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Pago_ID, Integer.valueOf(Z_Pago_ID));
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
}