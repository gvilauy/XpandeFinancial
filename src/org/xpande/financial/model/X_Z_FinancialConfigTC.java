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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_FinancialConfigTC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_FinancialConfigTC extends PO implements I_Z_FinancialConfigTC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171003L;

    /** Standard Constructor */
    public X_Z_FinancialConfigTC (Properties ctx, int Z_FinancialConfigTC_ID, String trxName)
    {
      super (ctx, Z_FinancialConfigTC_ID, trxName);
      /** if (Z_FinancialConfigTC_ID == 0)
        {
			setCargarTCCompra (false);
// N
			setC_Currency_ID (0);
			setCodigoTC (null);
			setZ_FinancialConfig_ID (0);
			setZ_FinancialConfigTC_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_FinancialConfigTC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_FinancialConfigTC[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CargarTCCompra.
		@param CargarTCCompra 
		Si se carga Tasa de Cambio de Compra o no
	  */
	public void setCargarTCCompra (boolean CargarTCCompra)
	{
		set_Value (COLUMNNAME_CargarTCCompra, Boolean.valueOf(CargarTCCompra));
	}

	/** Get CargarTCCompra.
		@return Si se carga Tasa de Cambio de Compra o no
	  */
	public boolean isCargarTCCompra () 
	{
		Object oo = get_Value(COLUMNNAME_CargarTCCompra);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set CodigoTC.
		@param CodigoTC 
		Código Tasa de Cambio para carga automática
	  */
	public void setCodigoTC (String CodigoTC)
	{
		set_Value (COLUMNNAME_CodigoTC, CodigoTC);
	}

	/** Get CodigoTC.
		@return Código Tasa de Cambio para carga automática
	  */
	public String getCodigoTC () 
	{
		return (String)get_Value(COLUMNNAME_CodigoTC);
	}

	public I_Z_FinancialConfig getZ_FinancialConfig() throws RuntimeException
    {
		return (I_Z_FinancialConfig)MTable.get(getCtx(), I_Z_FinancialConfig.Table_Name)
			.getPO(getZ_FinancialConfig_ID(), get_TrxName());	}

	/** Set Z_FinancialConfig ID.
		@param Z_FinancialConfig_ID Z_FinancialConfig ID	  */
	public void setZ_FinancialConfig_ID (int Z_FinancialConfig_ID)
	{
		if (Z_FinancialConfig_ID < 1) 
			set_Value (COLUMNNAME_Z_FinancialConfig_ID, null);
		else 
			set_Value (COLUMNNAME_Z_FinancialConfig_ID, Integer.valueOf(Z_FinancialConfig_ID));
	}

	/** Get Z_FinancialConfig ID.
		@return Z_FinancialConfig ID	  */
	public int getZ_FinancialConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FinancialConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_FinancialConfigTC ID.
		@param Z_FinancialConfigTC_ID Z_FinancialConfigTC ID	  */
	public void setZ_FinancialConfigTC_ID (int Z_FinancialConfigTC_ID)
	{
		if (Z_FinancialConfigTC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_FinancialConfigTC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_FinancialConfigTC_ID, Integer.valueOf(Z_FinancialConfigTC_ID));
	}

	/** Get Z_FinancialConfigTC ID.
		@return Z_FinancialConfigTC ID	  */
	public int getZ_FinancialConfigTC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FinancialConfigTC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}