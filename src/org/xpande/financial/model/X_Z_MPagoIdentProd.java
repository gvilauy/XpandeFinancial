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

/** Generated Model for Z_MPagoIdentProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MPagoIdentProd extends PO implements I_Z_MPagoIdentProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191010L;

    /** Standard Constructor */
    public X_Z_MPagoIdentProd (Properties ctx, int Z_MPagoIdentProd_ID, String trxName)
    {
      super (ctx, Z_MPagoIdentProd_ID, trxName);
      /** if (Z_MPagoIdentProd_ID == 0)
        {
			setM_Product_ID (0);
			setZ_MedioPagoIdent_ID (0);
			setZ_MPagoIdentProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MPagoIdentProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MPagoIdentProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_Z_MedioPagoIdent getZ_MedioPagoIdent() throws RuntimeException
    {
		return (I_Z_MedioPagoIdent)MTable.get(getCtx(), I_Z_MedioPagoIdent.Table_Name)
			.getPO(getZ_MedioPagoIdent_ID(), get_TrxName());	}

	/** Set Z_MedioPagoIdent ID.
		@param Z_MedioPagoIdent_ID Z_MedioPagoIdent ID	  */
	public void setZ_MedioPagoIdent_ID (int Z_MedioPagoIdent_ID)
	{
		if (Z_MedioPagoIdent_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoIdent_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoIdent_ID, Integer.valueOf(Z_MedioPagoIdent_ID));
	}

	/** Get Z_MedioPagoIdent ID.
		@return Z_MedioPagoIdent ID	  */
	public int getZ_MedioPagoIdent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoIdent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MPagoIdentProd ID.
		@param Z_MPagoIdentProd_ID Z_MPagoIdentProd ID	  */
	public void setZ_MPagoIdentProd_ID (int Z_MPagoIdentProd_ID)
	{
		if (Z_MPagoIdentProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MPagoIdentProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MPagoIdentProd_ID, Integer.valueOf(Z_MPagoIdentProd_ID));
	}

	/** Get Z_MPagoIdentProd ID.
		@return Z_MPagoIdentProd ID	  */
	public int getZ_MPagoIdentProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MPagoIdentProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}