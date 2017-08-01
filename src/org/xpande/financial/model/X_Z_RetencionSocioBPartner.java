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

/** Generated Model for Z_RetencionSocioBPartner
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetencionSocioBPartner extends PO implements I_Z_RetencionSocioBPartner, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170801L;

    /** Standard Constructor */
    public X_Z_RetencionSocioBPartner (Properties ctx, int Z_RetencionSocioBPartner_ID, String trxName)
    {
      super (ctx, Z_RetencionSocioBPartner_ID, trxName);
      /** if (Z_RetencionSocioBPartner_ID == 0)
        {
			setC_BPartner_ID (0);
			setZ_RetencionSocioBPartner_ID (0);
			setZ_RetencionSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetencionSocioBPartner (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetencionSocioBPartner[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_RetencionSocioBPartner ID.
		@param Z_RetencionSocioBPartner_ID Z_RetencionSocioBPartner ID	  */
	public void setZ_RetencionSocioBPartner_ID (int Z_RetencionSocioBPartner_ID)
	{
		if (Z_RetencionSocioBPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocioBPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocioBPartner_ID, Integer.valueOf(Z_RetencionSocioBPartner_ID));
	}

	/** Get Z_RetencionSocioBPartner ID.
		@return Z_RetencionSocioBPartner ID	  */
	public int getZ_RetencionSocioBPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetencionSocioBPartner_ID);
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