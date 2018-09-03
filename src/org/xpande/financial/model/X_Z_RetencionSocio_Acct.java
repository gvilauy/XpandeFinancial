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

/** Generated Model for Z_RetencionSocio_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetencionSocio_Acct extends PO implements I_Z_RetencionSocio_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180828L;

    /** Standard Constructor */
    public X_Z_RetencionSocio_Acct (Properties ctx, int Z_RetencionSocio_Acct_ID, String trxName)
    {
      super (ctx, Z_RetencionSocio_Acct_ID, trxName);
      /** if (Z_RetencionSocio_Acct_ID == 0)
        {
			setZ_RetencionSocio_Acct_ID (0);
			setZ_RetencionSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetencionSocio_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetencionSocio_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getRT_RetencionEmiPte_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getRT_RetencionEmiPte_Acct(), get_TrxName());	}

	/** Set RT_RetencionEmiPte_Acct.
		@param RT_RetencionEmiPte_Acct 
		Cuenta contable puente para reteneciones emitidas a proveedores
	  */
	public void setRT_RetencionEmiPte_Acct (int RT_RetencionEmiPte_Acct)
	{
		set_Value (COLUMNNAME_RT_RetencionEmiPte_Acct, Integer.valueOf(RT_RetencionEmiPte_Acct));
	}

	/** Get RT_RetencionEmiPte_Acct.
		@return Cuenta contable puente para reteneciones emitidas a proveedores
	  */
	public int getRT_RetencionEmiPte_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RT_RetencionEmiPte_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getRT_RetencionEmitida_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getRT_RetencionEmitida_Acct(), get_TrxName());	}

	/** Set RT_RetencionEmitida_Acct.
		@param RT_RetencionEmitida_Acct 
		Cuenta contable para reteneciones emitidas a proveedores
	  */
	public void setRT_RetencionEmitida_Acct (int RT_RetencionEmitida_Acct)
	{
		set_Value (COLUMNNAME_RT_RetencionEmitida_Acct, Integer.valueOf(RT_RetencionEmitida_Acct));
	}

	/** Get RT_RetencionEmitida_Acct.
		@return Cuenta contable para reteneciones emitidas a proveedores
	  */
	public int getRT_RetencionEmitida_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RT_RetencionEmitida_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getRT_RetencionRecibida_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getRT_RetencionRecibida_Acct(), get_TrxName());	}

	/** Set RT_RetencionRecibida_Acct.
		@param RT_RetencionRecibida_Acct 
		Cuenta contable para retenciones recibidas de clientes
	  */
	public void setRT_RetencionRecibida_Acct (int RT_RetencionRecibida_Acct)
	{
		set_Value (COLUMNNAME_RT_RetencionRecibida_Acct, Integer.valueOf(RT_RetencionRecibida_Acct));
	}

	/** Get RT_RetencionRecibida_Acct.
		@return Cuenta contable para retenciones recibidas de clientes
	  */
	public int getRT_RetencionRecibida_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RT_RetencionRecibida_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_RetencionSocio_Acct ID.
		@param Z_RetencionSocio_Acct_ID Z_RetencionSocio_Acct ID	  */
	public void setZ_RetencionSocio_Acct_ID (int Z_RetencionSocio_Acct_ID)
	{
		if (Z_RetencionSocio_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocio_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocio_Acct_ID, Integer.valueOf(Z_RetencionSocio_Acct_ID));
	}

	/** Get Z_RetencionSocio_Acct ID.
		@return Z_RetencionSocio_Acct ID	  */
	public int getZ_RetencionSocio_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetencionSocio_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_RetencionSocio getZ_RetencionSocio() throws RuntimeException
    {
		return (I_Z_RetencionSocio)MTable.get(getCtx(), I_Z_RetencionSocio.Table_Name)
			.getPO(getZ_RetencionSocio_ID(), get_TrxName());	}

	/** Set Z_RetencionSocio ID.
		@param Z_RetencionSocio_ID Z_RetencionSocio ID	  */
	public void setZ_RetencionSocio_ID (int Z_RetencionSocio_ID)
	{
		if (Z_RetencionSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_RetencionSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RetencionSocio_ID, Integer.valueOf(Z_RetencionSocio_ID));
	}

	/** Get Z_RetencionSocio ID.
		@return Z_RetencionSocio ID	  */
	public int getZ_RetencionSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetencionSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}