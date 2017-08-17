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

/** Generated Model for Z_OrdenPagoLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OrdenPagoLin extends PO implements I_Z_OrdenPagoLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170816L;

    /** Standard Constructor */
    public X_Z_OrdenPagoLin (Properties ctx, int Z_OrdenPagoLin_ID, String trxName)
    {
      super (ctx, Z_OrdenPagoLin_ID, trxName);
      /** if (Z_OrdenPagoLin_ID == 0)
        {
			setZ_OrdenPago_ID (0);
			setZ_OrdenPagoLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OrdenPagoLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OrdenPagoLin[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set AmtDocument.
		@param AmtDocument 
		Monto documento
	  */
	public void setAmtDocument (BigDecimal AmtDocument)
	{
		set_Value (COLUMNNAME_AmtDocument, AmtDocument);
	}

	/** Get AmtDocument.
		@return Monto documento
	  */
	public BigDecimal getAmtDocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtDocument);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtOpen.
		@param AmtOpen 
		Monto pendiente
	  */
	public void setAmtOpen (BigDecimal AmtOpen)
	{
		set_Value (COLUMNNAME_AmtOpen, AmtOpen);
	}

	/** Get AmtOpen.
		@return Monto pendiente
	  */
	public BigDecimal getAmtOpen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtOpen);
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

	/** Set DueDateDoc.
		@param DueDateDoc 
		Vencimiento del documento
	  */
	public void setDueDateDoc (Timestamp DueDateDoc)
	{
		set_Value (COLUMNNAME_DueDateDoc, DueDateDoc);
	}

	/** Get DueDateDoc.
		@return Vencimiento del documento
	  */
	public Timestamp getDueDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDateDoc);
	}

	public org.xpande.financial.model.I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException
    {
		return (org.xpande.financial.model.I_Z_OrdenPago)MTable.get(getCtx(), org.xpande.financial.model.I_Z_OrdenPago.Table_Name)
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

	/** Set Z_OrdenPagoLin ID.
		@param Z_OrdenPagoLin_ID Z_OrdenPagoLin ID	  */
	public void setZ_OrdenPagoLin_ID (int Z_OrdenPagoLin_ID)
	{
		if (Z_OrdenPagoLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OrdenPagoLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OrdenPagoLin_ID, Integer.valueOf(Z_OrdenPagoLin_ID));
	}

	/** Get Z_OrdenPagoLin ID.
		@return Z_OrdenPagoLin ID	  */
	public int getZ_OrdenPagoLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPagoLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException
    {
		return (I_Z_ResguardoSocio)MTable.get(getCtx(), I_Z_ResguardoSocio.Table_Name)
			.getPO(getZ_ResguardoSocio_ID(), get_TrxName());	}

	/** Set Z_ResguardoSocio ID.
		@param Z_ResguardoSocio_ID Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID (int Z_ResguardoSocio_ID)
	{
		if (Z_ResguardoSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_ResguardoSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ResguardoSocio_ID, Integer.valueOf(Z_ResguardoSocio_ID));
	}

	/** Get Z_ResguardoSocio ID.
		@return Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}