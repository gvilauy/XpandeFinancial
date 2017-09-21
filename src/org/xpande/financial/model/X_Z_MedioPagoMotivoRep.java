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

/** Generated Model for Z_MedioPagoMotivoRep
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MedioPagoMotivoRep extends PO implements I_Z_MedioPagoMotivoRep, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170921L;

    /** Standard Constructor */
    public X_Z_MedioPagoMotivoRep (Properties ctx, int Z_MedioPagoMotivoRep_ID, String trxName)
    {
      super (ctx, Z_MedioPagoMotivoRep_ID, trxName);
      /** if (Z_MedioPagoMotivoRep_ID == 0)
        {
			setName (null);
			setSolicitaFecVenc (false);
// N
			setValue (null);
			setZ_MedioPagoMotivoRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MedioPagoMotivoRep (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MedioPagoMotivoRep[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set SolicitaFecVenc.
		@param SolicitaFecVenc 
		Solicita Fecha de Vencimiento si o no
	  */
	public void setSolicitaFecVenc (boolean SolicitaFecVenc)
	{
		set_Value (COLUMNNAME_SolicitaFecVenc, Boolean.valueOf(SolicitaFecVenc));
	}

	/** Get SolicitaFecVenc.
		@return Solicita Fecha de Vencimiento si o no
	  */
	public boolean isSolicitaFecVenc () 
	{
		Object oo = get_Value(COLUMNNAME_SolicitaFecVenc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Z_MedioPagoMotivoRep ID.
		@param Z_MedioPagoMotivoRep_ID Z_MedioPagoMotivoRep ID	  */
	public void setZ_MedioPagoMotivoRep_ID (int Z_MedioPagoMotivoRep_ID)
	{
		if (Z_MedioPagoMotivoRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoMotivoRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoMotivoRep_ID, Integer.valueOf(Z_MedioPagoMotivoRep_ID));
	}

	/** Get Z_MedioPagoMotivoRep ID.
		@return Z_MedioPagoMotivoRep ID	  */
	public int getZ_MedioPagoMotivoRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoMotivoRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}