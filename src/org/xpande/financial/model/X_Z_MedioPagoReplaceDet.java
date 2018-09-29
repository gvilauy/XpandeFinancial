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

/** Generated Model for Z_MedioPagoReplaceDet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MedioPagoReplaceDet extends PO implements I_Z_MedioPagoReplaceDet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171128L;

    /** Standard Constructor */
    public X_Z_MedioPagoReplaceDet (Properties ctx, int Z_MedioPagoReplaceDet_ID, String trxName)
    {
      super (ctx, Z_MedioPagoReplaceDet_ID, trxName);
      /** if (Z_MedioPagoReplaceDet_ID == 0)
        {
			setTotalAmt (Env.ZERO);
			setZ_MedioPago_ID (0);
			setZ_MedioPagoItem_ID (0);
			setZ_MedioPagoReplaceDet_ID (0);
			setZ_MedioPagoReplace_ID (0);
			setZ_MedioPagoReplaceLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MedioPagoReplaceDet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MedioPagoReplaceDet[")
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

	/** Set DateEmitted.
		@param DateEmitted 
		Fecha emisión de un documento
	  */
	public void setDateEmitted (Timestamp DateEmitted)
	{
		set_Value (COLUMNNAME_DateEmitted, DateEmitted);
	}

	/** Get DateEmitted.
		@return Fecha emisión de un documento
	  */
	public Timestamp getDateEmitted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEmitted);
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

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_Value (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_Z_MedioPago getZ_MedioPago() throws RuntimeException
    {
		return (I_Z_MedioPago)MTable.get(getCtx(), I_Z_MedioPago.Table_Name)
			.getPO(getZ_MedioPago_ID(), get_TrxName());	}

	/** Set Z_MedioPago ID.
		@param Z_MedioPago_ID Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID)
	{
		if (Z_MedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPago_ID, Integer.valueOf(Z_MedioPago_ID));
	}

	/** Get Z_MedioPago ID.
		@return Z_MedioPago ID	  */
	public int getZ_MedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoItem getZ_MedioPagoItem() throws RuntimeException
    {
		return (I_Z_MedioPagoItem)MTable.get(getCtx(), I_Z_MedioPagoItem.Table_Name)
			.getPO(getZ_MedioPagoItem_ID(), get_TrxName());	}

	/** Set Z_MedioPagoItem ID.
		@param Z_MedioPagoItem_ID Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID (int Z_MedioPagoItem_ID)
	{
		if (Z_MedioPagoItem_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoItem_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoItem_ID, Integer.valueOf(Z_MedioPagoItem_ID));
	}

	/** Get Z_MedioPagoItem ID.
		@return Z_MedioPagoItem ID	  */
	public int getZ_MedioPagoItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MedioPagoReplaceDet ID.
		@param Z_MedioPagoReplaceDet_ID Z_MedioPagoReplaceDet ID	  */
	public void setZ_MedioPagoReplaceDet_ID (int Z_MedioPagoReplaceDet_ID)
	{
		if (Z_MedioPagoReplaceDet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoReplaceDet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoReplaceDet_ID, Integer.valueOf(Z_MedioPagoReplaceDet_ID));
	}

	/** Get Z_MedioPagoReplaceDet ID.
		@return Z_MedioPagoReplaceDet ID	  */
	public int getZ_MedioPagoReplaceDet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoReplaceDet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoReplace getZ_MedioPagoReplace() throws RuntimeException
    {
		return (I_Z_MedioPagoReplace)MTable.get(getCtx(), I_Z_MedioPagoReplace.Table_Name)
			.getPO(getZ_MedioPagoReplace_ID(), get_TrxName());	}

	/** Set Z_MedioPagoReplace ID.
		@param Z_MedioPagoReplace_ID Z_MedioPagoReplace ID	  */
	public void setZ_MedioPagoReplace_ID (int Z_MedioPagoReplace_ID)
	{
		if (Z_MedioPagoReplace_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoReplace_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoReplace_ID, Integer.valueOf(Z_MedioPagoReplace_ID));
	}

	/** Get Z_MedioPagoReplace ID.
		@return Z_MedioPagoReplace ID	  */
	public int getZ_MedioPagoReplace_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoReplace_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoReplaceLin getZ_MedioPagoReplaceLin() throws RuntimeException
    {
		return (I_Z_MedioPagoReplaceLin)MTable.get(getCtx(), I_Z_MedioPagoReplaceLin.Table_Name)
			.getPO(getZ_MedioPagoReplaceLin_ID(), get_TrxName());	}

	/** Set Z_MedioPagoReplaceLin ID.
		@param Z_MedioPagoReplaceLin_ID Z_MedioPagoReplaceLin ID	  */
	public void setZ_MedioPagoReplaceLin_ID (int Z_MedioPagoReplaceLin_ID)
	{
		if (Z_MedioPagoReplaceLin_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoReplaceLin_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoReplaceLin_ID, Integer.valueOf(Z_MedioPagoReplaceLin_ID));
	}

	/** Get Z_MedioPagoReplaceLin ID.
		@return Z_MedioPagoReplaceLin ID	  */
	public int getZ_MedioPagoReplaceLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoReplaceLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}