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

/** Generated Model for Z_PagoResguardo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PagoResguardo extends PO implements I_Z_PagoResguardo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180124L;

    /** Standard Constructor */
    public X_Z_PagoResguardo (Properties ctx, int Z_PagoResguardo_ID, String trxName)
    {
      super (ctx, Z_PagoResguardo_ID, trxName);
      /** if (Z_PagoResguardo_ID == 0)
        {
			setAmtDocument (Env.ZERO);
			setC_Currency_ID (0);
			setIsSelected (false);
// N
			setZ_Pago_ID (0);
			setZ_PagoResguardo_ID (0);
			setZ_ResguardoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PagoResguardo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PagoResguardo[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.xpande.financial.model.I_Z_Pago getZ_Pago() throws RuntimeException
    {
		return (org.xpande.financial.model.I_Z_Pago)MTable.get(getCtx(), org.xpande.financial.model.I_Z_Pago.Table_Name)
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

	/** Set Z_PagoResguardo ID.
		@param Z_PagoResguardo_ID Z_PagoResguardo ID	  */
	public void setZ_PagoResguardo_ID (int Z_PagoResguardo_ID)
	{
		if (Z_PagoResguardo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PagoResguardo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PagoResguardo_ID, Integer.valueOf(Z_PagoResguardo_ID));
	}

	/** Get Z_PagoResguardo ID.
		@return Z_PagoResguardo ID	  */
	public int getZ_PagoResguardo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PagoResguardo_ID);
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