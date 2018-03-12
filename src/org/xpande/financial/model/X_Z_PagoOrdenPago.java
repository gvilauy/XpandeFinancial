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

/** Generated Model for Z_PagoOrdenPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PagoOrdenPago extends PO implements I_Z_PagoOrdenPago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180312L;

    /** Standard Constructor */
    public X_Z_PagoOrdenPago (Properties ctx, int Z_PagoOrdenPago_ID, String trxName)
    {
      super (ctx, Z_PagoOrdenPago_ID, trxName);
      /** if (Z_PagoOrdenPago_ID == 0)
        {
			setC_Currency_ID (0);
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setTotalAmt (Env.ZERO);
			setZ_OrdenPago_ID (0);
			setZ_Pago_ID (0);
			setZ_PagoOrdenPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PagoOrdenPago (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PagoOrdenPago[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set Z_PagoOrdenPago ID.
		@param Z_PagoOrdenPago_ID Z_PagoOrdenPago ID	  */
	public void setZ_PagoOrdenPago_ID (int Z_PagoOrdenPago_ID)
	{
		if (Z_PagoOrdenPago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PagoOrdenPago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PagoOrdenPago_ID, Integer.valueOf(Z_PagoOrdenPago_ID));
	}

	/** Get Z_PagoOrdenPago ID.
		@return Z_PagoOrdenPago ID	  */
	public int getZ_PagoOrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PagoOrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}