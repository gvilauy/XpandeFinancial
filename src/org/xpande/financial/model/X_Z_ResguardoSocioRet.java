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

/** Generated Model for Z_ResguardoSocioRet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ResguardoSocioRet extends PO implements I_Z_ResguardoSocioRet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170927L;

    /** Standard Constructor */
    public X_Z_ResguardoSocioRet (Properties ctx, int Z_ResguardoSocioRet_ID, String trxName)
    {
      super (ctx, Z_ResguardoSocioRet_ID, trxName);
      /** if (Z_ResguardoSocioRet_ID == 0)
        {
			setAmtBase (Env.ZERO);
			setAmtRetencion (Env.ZERO);
			setZ_ResguardoSocio_ID (0);
			setZ_ResguardoSocioRet_ID (0);
			setZ_RetencionSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ResguardoSocioRet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ResguardoSocioRet[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtBase.
		@param AmtBase 
		Monto base
	  */
	public void setAmtBase (BigDecimal AmtBase)
	{
		set_Value (COLUMNNAME_AmtBase, AmtBase);
	}

	/** Get AmtBase.
		@return Monto base
	  */
	public BigDecimal getAmtBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtRetencion.
		@param AmtRetencion 
		Monto retención
	  */
	public void setAmtRetencion (BigDecimal AmtRetencion)
	{
		set_Value (COLUMNNAME_AmtRetencion, AmtRetencion);
	}

	/** Get AmtRetencion.
		@return Monto retención
	  */
	public BigDecimal getAmtRetencion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetencion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtRetencionME.
		@param AmtRetencionME 
		Monto a retener en moneda extranjera
	  */
	public void setAmtRetencionME (BigDecimal AmtRetencionME)
	{
		set_Value (COLUMNNAME_AmtRetencionME, AmtRetencionME);
	}

	/** Get AmtRetencionME.
		@return Monto a retener en moneda extranjera
	  */
	public BigDecimal getAmtRetencionME () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtRetencionME);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PorcRetencion.
		@param PorcRetencion 
		Porcentaje Retención 
	  */
	public void setPorcRetencion (BigDecimal PorcRetencion)
	{
		throw new IllegalArgumentException ("PorcRetencion is virtual column");	}

	/** Get PorcRetencion.
		@return Porcentaje Retención 
	  */
	public BigDecimal getPorcRetencion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PorcRetencion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Z_ResguardoSocioRet ID.
		@param Z_ResguardoSocioRet_ID Z_ResguardoSocioRet ID	  */
	public void setZ_ResguardoSocioRet_ID (int Z_ResguardoSocioRet_ID)
	{
		if (Z_ResguardoSocioRet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ResguardoSocioRet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ResguardoSocioRet_ID, Integer.valueOf(Z_ResguardoSocioRet_ID));
	}

	/** Get Z_ResguardoSocioRet ID.
		@return Z_ResguardoSocioRet ID	  */
	public int getZ_ResguardoSocioRet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ResguardoSocioRet_ID);
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