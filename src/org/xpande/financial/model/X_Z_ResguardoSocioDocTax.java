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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_ResguardoSocioDocTax
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ResguardoSocioDocTax extends PO implements I_Z_ResguardoSocioDocTax, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170802L;

    /** Standard Constructor */
    public X_Z_ResguardoSocioDocTax (Properties ctx, int Z_ResguardoSocioDocTax_ID, String trxName)
    {
      super (ctx, Z_ResguardoSocioDocTax_ID, trxName);
      /** if (Z_ResguardoSocioDocTax_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_TaxCategory_ID (0);
			setC_Tax_ID (0);
			setTaxAmt (Env.ZERO);
			setZ_ResguardoSocioDoc_ID (0);
			setZ_ResguardoSocioDocTax_ID (0);
			setZ_ResguardoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ResguardoSocioDocTax (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ResguardoSocioDocTax[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_C_TaxCategory getC_TaxCategory() throws RuntimeException
    {
		return (I_C_TaxCategory)MTable.get(getCtx(), I_C_TaxCategory.Table_Name)
			.getPO(getC_TaxCategory_ID(), get_TrxName());	}

	/** Set Tax Category.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Tax Category.
		@return Tax Category
	  */
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Tax getC_Tax() throws RuntimeException
    {
		return (I_C_Tax)MTable.get(getCtx(), I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Amount.
		@param TaxAmt 
		Tax Amount for a document
	  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Tax Amount.
		@return Tax Amount for a document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_Z_ResguardoSocioDoc getZ_ResguardoSocioDoc() throws RuntimeException
    {
		return (I_Z_ResguardoSocioDoc)MTable.get(getCtx(), I_Z_ResguardoSocioDoc.Table_Name)
			.getPO(getZ_ResguardoSocioDoc_ID(), get_TrxName());	}

	/** Set Z_ResguardoSocioDoc ID.
		@param Z_ResguardoSocioDoc_ID Z_ResguardoSocioDoc ID	  */
	public void setZ_ResguardoSocioDoc_ID (int Z_ResguardoSocioDoc_ID)
	{
		if (Z_ResguardoSocioDoc_ID < 1) 
			set_Value (COLUMNNAME_Z_ResguardoSocioDoc_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ResguardoSocioDoc_ID, Integer.valueOf(Z_ResguardoSocioDoc_ID));
	}

	/** Get Z_ResguardoSocioDoc ID.
		@return Z_ResguardoSocioDoc ID	  */
	public int getZ_ResguardoSocioDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocioDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ResguardoSocioDocTax ID.
		@param Z_ResguardoSocioDocTax_ID Z_ResguardoSocioDocTax ID	  */
	public void setZ_ResguardoSocioDocTax_ID (int Z_ResguardoSocioDocTax_ID)
	{
		if (Z_ResguardoSocioDocTax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ResguardoSocioDocTax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ResguardoSocioDocTax_ID, Integer.valueOf(Z_ResguardoSocioDocTax_ID));
	}

	/** Get Z_ResguardoSocioDocTax ID.
		@return Z_ResguardoSocioDocTax ID	  */
	public int getZ_ResguardoSocioDocTax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocioDocTax_ID);
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