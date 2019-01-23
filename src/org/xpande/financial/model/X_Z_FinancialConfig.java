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

/** Generated Model for Z_FinancialConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_FinancialConfig extends PO implements I_Z_FinancialConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190122L;

    /** Standard Constructor */
    public X_Z_FinancialConfig (Properties ctx, int Z_FinancialConfig_ID, String trxName)
    {
      super (ctx, Z_FinancialConfig_ID, trxName);
      /** if (Z_FinancialConfig_ID == 0)
        {
			setValue (null);
			setZ_FinancialConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_FinancialConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_FinancialConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (I_C_PaymentTerm)MTable.get(getCtx(), I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DefaultDocCCD_ID.
		@param DefaultDocCCD_ID 
		Documento por defecto para Cobro a Clientes
	  */
	public void setDefaultDocCCD_ID (int DefaultDocCCD_ID)
	{
		if (DefaultDocCCD_ID < 1) 
			set_Value (COLUMNNAME_DefaultDocCCD_ID, null);
		else 
			set_Value (COLUMNNAME_DefaultDocCCD_ID, Integer.valueOf(DefaultDocCCD_ID));
	}

	/** Get DefaultDocCCD_ID.
		@return Documento por defecto para Cobro a Clientes
	  */
	public int getDefaultDocCCD_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefaultDocCCD_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DefaultDocPPD_ID.
		@param DefaultDocPPD_ID 
		Documento por defecto para Pago a Proveedores
	  */
	public void setDefaultDocPPD_ID (int DefaultDocPPD_ID)
	{
		if (DefaultDocPPD_ID < 1) 
			set_Value (COLUMNNAME_DefaultDocPPD_ID, null);
		else 
			set_Value (COLUMNNAME_DefaultDocPPD_ID, Integer.valueOf(DefaultDocPPD_ID));
	}

	/** Get DefaultDocPPD_ID.
		@return Documento por defecto para Pago a Proveedores
	  */
	public int getDefaultDocPPD_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefaultDocPPD_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Z_FinancialConfig ID.
		@param Z_FinancialConfig_ID Z_FinancialConfig ID	  */
	public void setZ_FinancialConfig_ID (int Z_FinancialConfig_ID)
	{
		if (Z_FinancialConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_FinancialConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_FinancialConfig_ID, Integer.valueOf(Z_FinancialConfig_ID));
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
}