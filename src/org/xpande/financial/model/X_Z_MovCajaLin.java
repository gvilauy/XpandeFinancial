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

/** Generated Model for Z_MovCajaLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MovCajaLin extends PO implements I_Z_MovCajaLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20201222L;

    /** Standard Constructor */
    public X_Z_MovCajaLin (Properties ctx, int Z_MovCajaLin_ID, String trxName)
    {
      super (ctx, Z_MovCajaLin_ID, trxName);
      /** if (Z_MovCajaLin_ID == 0)
        {
			setC_Charge_ID (0);
			setC_Currency_ID (0);
			setCurrencyRate (Env.ZERO);
			setTieneTasaCambio (false);
// N
			setTotalAmt (Env.ZERO);
			setTotalAmtMT (Env.ZERO);
			setZ_MovCaja_ID (0);
			setZ_MovCajaLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MovCajaLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MovCajaLin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Charge getC_Charge() throws RuntimeException
    {
		return (I_C_Charge)MTable.get(getCtx(), I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
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

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TieneTasaCambio.
		@param TieneTasaCambio 
		Si tiene o no tasa de cambio para conversión de monedas
	  */
	public void setTieneTasaCambio (boolean TieneTasaCambio)
	{
		set_Value (COLUMNNAME_TieneTasaCambio, Boolean.valueOf(TieneTasaCambio));
	}

	/** Get TieneTasaCambio.
		@return Si tiene o no tasa de cambio para conversión de monedas
	  */
	public boolean isTieneTasaCambio () 
	{
		Object oo = get_Value(COLUMNNAME_TieneTasaCambio);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set TotalAmtMT.
		@param TotalAmtMT 
		Monto total en moneda de la transacción
	  */
	public void setTotalAmtMT (BigDecimal TotalAmtMT)
	{
		set_Value (COLUMNNAME_TotalAmtMT, TotalAmtMT);
	}

	/** Get TotalAmtMT.
		@return Monto total en moneda de la transacción
	  */
	public BigDecimal getTotalAmtMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmtMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}

	public I_Z_MovCaja getZ_MovCaja() throws RuntimeException
    {
		return (I_Z_MovCaja)MTable.get(getCtx(), I_Z_MovCaja.Table_Name)
			.getPO(getZ_MovCaja_ID(), get_TrxName());	}

	/** Set Z_MovCaja ID.
		@param Z_MovCaja_ID Z_MovCaja ID	  */
	public void setZ_MovCaja_ID (int Z_MovCaja_ID)
	{
		if (Z_MovCaja_ID < 1) 
			set_Value (COLUMNNAME_Z_MovCaja_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MovCaja_ID, Integer.valueOf(Z_MovCaja_ID));
	}

	/** Get Z_MovCaja ID.
		@return Z_MovCaja ID	  */
	public int getZ_MovCaja_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MovCaja_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MovCajaLin ID.
		@param Z_MovCajaLin_ID Z_MovCajaLin ID	  */
	public void setZ_MovCajaLin_ID (int Z_MovCajaLin_ID)
	{
		if (Z_MovCajaLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MovCajaLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MovCajaLin_ID, Integer.valueOf(Z_MovCajaLin_ID));
	}

	/** Get Z_MovCajaLin ID.
		@return Z_MovCajaLin ID	  */
	public int getZ_MovCajaLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MovCajaLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}