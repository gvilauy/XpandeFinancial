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

/** Generated Model for Z_MedioPagoFolio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MedioPagoFolio extends PO implements I_Z_MedioPagoFolio, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190107L;

    /** Standard Constructor */
    public X_Z_MedioPagoFolio (Properties ctx, int Z_MedioPagoFolio_ID, String trxName)
    {
      super (ctx, Z_MedioPagoFolio_ID, trxName);
      /** if (Z_MedioPagoFolio_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_Currency_ID (0);
			setDisponible (true);
// Y
			setEmisionManual (false);
// N
			setIsExecuted (false);
// N
			setName (null);
			setNumeroDesde (0);
			setNumeroHasta (0);
			setZ_MedioPagoFolio_ID (0);
			setZ_MedioPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MedioPagoFolio (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MedioPagoFolio[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (I_C_BankAccount)MTable.get(getCtx(), I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
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

	/** Set Disponible.
		@param Disponible 
		Si un elemento esta Disponible o no
	  */
	public void setDisponible (boolean Disponible)
	{
		set_Value (COLUMNNAME_Disponible, Boolean.valueOf(Disponible));
	}

	/** Get Disponible.
		@return Si un elemento esta Disponible o no
	  */
	public boolean isDisponible () 
	{
		Object oo = get_Value(COLUMNNAME_Disponible);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DocumentSerie.
		@param DocumentSerie 
		Serie de un Documento
	  */
	public void setDocumentSerie (String DocumentSerie)
	{
		set_Value (COLUMNNAME_DocumentSerie, DocumentSerie);
	}

	/** Get DocumentSerie.
		@return Serie de un Documento
	  */
	public String getDocumentSerie () 
	{
		return (String)get_Value(COLUMNNAME_DocumentSerie);
	}

	/** Set EmisionManual.
		@param EmisionManual 
		Emisión manual de documentos
	  */
	public void setEmisionManual (boolean EmisionManual)
	{
		set_Value (COLUMNNAME_EmisionManual, Boolean.valueOf(EmisionManual));
	}

	/** Get EmisionManual.
		@return Emisión manual de documentos
	  */
	public boolean isEmisionManual () 
	{
		Object oo = get_Value(COLUMNNAME_EmisionManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set NroMedioPagoDesde.
		@param NroMedioPagoDesde 
		Nunero de medio de pago desde
	  */
	public void setNroMedioPagoDesde (String NroMedioPagoDesde)
	{
		set_Value (COLUMNNAME_NroMedioPagoDesde, NroMedioPagoDesde);
	}

	/** Get NroMedioPagoDesde.
		@return Nunero de medio de pago desde
	  */
	public String getNroMedioPagoDesde () 
	{
		return (String)get_Value(COLUMNNAME_NroMedioPagoDesde);
	}

	/** Set NumeroDesde.
		@param NumeroDesde 
		Numero desde para Rango de Enteros
	  */
	public void setNumeroDesde (int NumeroDesde)
	{
		set_Value (COLUMNNAME_NumeroDesde, Integer.valueOf(NumeroDesde));
	}

	/** Get NumeroDesde.
		@return Numero desde para Rango de Enteros
	  */
	public int getNumeroDesde () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumeroDesde);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set NumeroHasta.
		@param NumeroHasta 
		Numero hasta para Rango de Enteros
	  */
	public void setNumeroHasta (int NumeroHasta)
	{
		set_Value (COLUMNNAME_NumeroHasta, Integer.valueOf(NumeroHasta));
	}

	/** Get NumeroHasta.
		@return Numero hasta para Rango de Enteros
	  */
	public int getNumeroHasta () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_NumeroHasta);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ProcessButton.
		@param ProcessButton ProcessButton	  */
	public void setProcessButton (String ProcessButton)
	{
		set_Value (COLUMNNAME_ProcessButton, ProcessButton);
	}

	/** Get ProcessButton.
		@return ProcessButton	  */
	public String getProcessButton () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton);
	}

	/** TIpoCheque AD_Reference_ID=1000015 */
	public static final int TIPOCHEQUE_AD_Reference_ID=1000015;
	/** DIFERIDO = DIFERIDO */
	public static final String TIPOCHEQUE_DIFERIDO = "DIFERIDO";
	/** DIA = DIA */
	public static final String TIPOCHEQUE_DIA = "DIA";
	/** Set TIpoCheque.
		@param TIpoCheque 
		Tipo de cheque: diferido o día
	  */
	public void setTIpoCheque (String TIpoCheque)
	{

		set_Value (COLUMNNAME_TIpoCheque, TIpoCheque);
	}

	/** Get TIpoCheque.
		@return Tipo de cheque: diferido o día
	  */
	public String getTIpoCheque () 
	{
		return (String)get_Value(COLUMNNAME_TIpoCheque);
	}

	/** Set Z_MedioPagoFolio ID.
		@param Z_MedioPagoFolio_ID Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID (int Z_MedioPagoFolio_ID)
	{
		if (Z_MedioPagoFolio_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoFolio_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoFolio_ID, Integer.valueOf(Z_MedioPagoFolio_ID));
	}

	/** Get Z_MedioPagoFolio ID.
		@return Z_MedioPagoFolio ID	  */
	public int getZ_MedioPagoFolio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoFolio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPago getZ_MedioPago() throws RuntimeException
    {
		return (I_Z_MedioPago)MTable.get(getCtx(), I_Z_MedioPago.Table_Name)
			.getPO(getZ_MedioPago_ID(), get_TrxName());	}

	/** Set Z_MedioPago ID.
		@param Z_MedioPago_ID Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID)
	{
		if (Z_MedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPago_ID, Integer.valueOf(Z_MedioPago_ID));
	}

	/** Get Z_MedioPago ID.
		@return Z_MedioPago ID	  */
	public int getZ_MedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}