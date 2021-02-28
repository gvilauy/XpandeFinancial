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

/** Generated Model for Z_LoadPagoFile
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_LoadPagoFile extends PO implements I_Z_LoadPagoFile, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210226L;

    /** Standard Constructor */
    public X_Z_LoadPagoFile (Properties ctx, int Z_LoadPagoFile_ID, String trxName)
    {
      super (ctx, Z_LoadPagoFile_ID, trxName);
      /** if (Z_LoadPagoFile_ID == 0)
        {
			setI_IsImported (false);
// N
			setIsConfirmed (false);
// N
			setIsOmitted (false);
// N
			setProcessed (false);
// N
			setZ_LoadPagoFile_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_LoadPagoFile (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_LoadPagoFile[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set DocumentNoRef.
		@param DocumentNoRef 
		Numero de documento referenciado
	  */
	public void setDocumentNoRef (String DocumentNoRef)
	{
		set_Value (COLUMNNAME_DocumentNoRef, DocumentNoRef);
	}

	/** Get DocumentNoRef.
		@return Numero de documento referenciado
	  */
	public String getDocumentNoRef () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoRef);
	}

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set FechaCadena.
		@param FechaCadena 
		Fecha en formato Cadena
	  */
	public void setFechaCadena (String FechaCadena)
	{
		set_Value (COLUMNNAME_FechaCadena, FechaCadena);
	}

	/** Get FechaCadena.
		@return Fecha en formato Cadena
	  */
	public String getFechaCadena () 
	{
		return (String)get_Value(COLUMNNAME_FechaCadena);
	}

	/** Set FileLineText.
		@param FileLineText FileLineText	  */
	public void setFileLineText (String FileLineText)
	{
		set_Value (COLUMNNAME_FileLineText, FileLineText);
	}

	/** Get FileLineText.
		@return FileLineText	  */
	public String getFileLineText () 
	{
		return (String)get_Value(COLUMNNAME_FileLineText);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_Value (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOmitted.
		@param IsOmitted 
		Omitida si o no
	  */
	public void setIsOmitted (boolean IsOmitted)
	{
		set_Value (COLUMNNAME_IsOmitted, Boolean.valueOf(IsOmitted));
	}

	/** Get IsOmitted.
		@return Omitida si o no
	  */
	public boolean isOmitted () 
	{
		Object oo = get_Value(COLUMNNAME_IsOmitted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LineNumber.
		@param LineNumber LineNumber	  */
	public void setLineNumber (int LineNumber)
	{
		set_Value (COLUMNNAME_LineNumber, Integer.valueOf(LineNumber));
	}

	/** Get LineNumber.
		@return LineNumber	  */
	public int getLineNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNumber);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Tax ID.
		@param TaxID 
		Tax Identification
	  */
	public void setTaxID (String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Tax ID.
		@return Tax Identification
	  */
	public String getTaxID () 
	{
		return (String)get_Value(COLUMNNAME_TaxID);
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

	/** Set Z_LoadPagoFile ID.
		@param Z_LoadPagoFile_ID Z_LoadPagoFile ID	  */
	public void setZ_LoadPagoFile_ID (int Z_LoadPagoFile_ID)
	{
		if (Z_LoadPagoFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_LoadPagoFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_LoadPagoFile_ID, Integer.valueOf(Z_LoadPagoFile_ID));
	}

	/** Get Z_LoadPagoFile ID.
		@return Z_LoadPagoFile ID	  */
	public int getZ_LoadPagoFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LoadPagoFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_LoadPago getZ_LoadPago() throws RuntimeException
    {
		return (I_Z_LoadPago)MTable.get(getCtx(), I_Z_LoadPago.Table_Name)
			.getPO(getZ_LoadPago_ID(), get_TrxName());	}

	/** Set Z_LoadPago ID.
		@param Z_LoadPago_ID Z_LoadPago ID	  */
	public void setZ_LoadPago_ID (int Z_LoadPago_ID)
	{
		if (Z_LoadPago_ID < 1) 
			set_Value (COLUMNNAME_Z_LoadPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_LoadPago_ID, Integer.valueOf(Z_LoadPago_ID));
	}

	/** Get Z_LoadPago ID.
		@return Z_LoadPago ID	  */
	public int getZ_LoadPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LoadPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}