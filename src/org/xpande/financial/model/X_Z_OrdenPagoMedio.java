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

/** Generated Model for Z_OrdenPagoMedio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OrdenPagoMedio extends PO implements I_Z_OrdenPagoMedio, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190910L;

    /** Standard Constructor */
    public X_Z_OrdenPagoMedio (Properties ctx, int Z_OrdenPagoMedio_ID, String trxName)
    {
      super (ctx, Z_OrdenPagoMedio_ID, trxName);
      /** if (Z_OrdenPagoMedio_ID == 0)
        {
			setC_Currency_ID (0);
			setTieneBanco (false);
// N
			setTieneCaja (false);
// N
			setTieneCtaBco (true);
// Y
			setTieneFecEmi (false);
// N
			setTieneFecVenc (false);
// N
			setTieneFolio (false);
// N
			setTieneIdent (false);
// N
			setTieneNroRef (false);
// N
			setTotalAmt (Env.ZERO);
			setZ_MedioPago_ID (0);
			setZ_OrdenPago_ID (0);
			setZ_OrdenPagoMedio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OrdenPagoMedio (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OrdenPagoMedio[")
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

	public I_C_Bank getC_Bank() throws RuntimeException
    {
		return (I_C_Bank)MTable.get(getCtx(), I_C_Bank.Table_Name)
			.getPO(getC_Bank_ID(), get_TrxName());	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
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

	public I_C_CashBook getC_CashBook() throws RuntimeException
    {
		return (I_C_CashBook)MTable.get(getCtx(), I_C_CashBook.Table_Name)
			.getPO(getC_CashBook_ID(), get_TrxName());	}

	/** Set Cash Book.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Cash Book.
		@return Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
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

	/** Set DateEmitted.
		@param DateEmitted 
		Fecha emisión de un documento
	  */
	public void setDateEmitted (Timestamp DateEmitted)
	{
		set_Value (COLUMNNAME_DateEmitted, DateEmitted);
	}

	/** Get DateEmitted.
		@return Fecha emisión de un documento
	  */
	public Timestamp getDateEmitted () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateEmitted);
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

	/** Set Due Date.
		@param DueDate 
		Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set TieneBanco.
		@param TieneBanco 
		Si requiere información de Banco o no
	  */
	public void setTieneBanco (boolean TieneBanco)
	{
		set_Value (COLUMNNAME_TieneBanco, Boolean.valueOf(TieneBanco));
	}

	/** Get TieneBanco.
		@return Si requiere información de Banco o no
	  */
	public boolean isTieneBanco () 
	{
		Object oo = get_Value(COLUMNNAME_TieneBanco);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCaja.
		@param TieneCaja 
		Si requiere o no una caja asociada
	  */
	public void setTieneCaja (boolean TieneCaja)
	{
		set_Value (COLUMNNAME_TieneCaja, Boolean.valueOf(TieneCaja));
	}

	/** Get TieneCaja.
		@return Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCaja);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCtaBco.
		@param TieneCtaBco 
		Si requiere o no una cuenta bancaria asociada
	  */
	public void setTieneCtaBco (boolean TieneCtaBco)
	{
		set_Value (COLUMNNAME_TieneCtaBco, Boolean.valueOf(TieneCtaBco));
	}

	/** Get TieneCtaBco.
		@return Si requiere o no una cuenta bancaria asociada
	  */
	public boolean isTieneCtaBco () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCtaBco);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecEmi.
		@param TieneFecEmi 
		Si lleva o no Fecha de Emisión
	  */
	public void setTieneFecEmi (boolean TieneFecEmi)
	{
		set_Value (COLUMNNAME_TieneFecEmi, Boolean.valueOf(TieneFecEmi));
	}

	/** Get TieneFecEmi.
		@return Si lleva o no Fecha de Emisión
	  */
	public boolean isTieneFecEmi () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecEmi);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecVenc.
		@param TieneFecVenc 
		Si lleva o no Fecha de Vencimiento
	  */
	public void setTieneFecVenc (boolean TieneFecVenc)
	{
		set_Value (COLUMNNAME_TieneFecVenc, Boolean.valueOf(TieneFecVenc));
	}

	/** Get TieneFecVenc.
		@return Si lleva o no Fecha de Vencimiento
	  */
	public boolean isTieneFecVenc () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecVenc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFolio.
		@param TieneFolio 
		Si requiere o no un folio asociado de medios de pago
	  */
	public void setTieneFolio (boolean TieneFolio)
	{
		set_Value (COLUMNNAME_TieneFolio, Boolean.valueOf(TieneFolio));
	}

	/** Get TieneFolio.
		@return Si requiere o no un folio asociado de medios de pago
	  */
	public boolean isTieneFolio () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFolio);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneIdent.
		@param TieneIdent 
		Si un medio de pago tiene o no identificadores para pagos
	  */
	public void setTieneIdent (boolean TieneIdent)
	{
		set_Value (COLUMNNAME_TieneIdent, Boolean.valueOf(TieneIdent));
	}

	/** Get TieneIdent.
		@return Si un medio de pago tiene o no identificadores para pagos
	  */
	public boolean isTieneIdent () 
	{
		Object oo = get_Value(COLUMNNAME_TieneIdent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneNroRef.
		@param TieneNroRef 
		Si requiere o no numero de referencia
	  */
	public void setTieneNroRef (boolean TieneNroRef)
	{
		set_Value (COLUMNNAME_TieneNroRef, Boolean.valueOf(TieneNroRef));
	}

	/** Get TieneNroRef.
		@return Si requiere o no numero de referencia
	  */
	public boolean isTieneNroRef () 
	{
		Object oo = get_Value(COLUMNNAME_TieneNroRef);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_Z_GeneraOrdenPago getZ_GeneraOrdenPago() throws RuntimeException
    {
		return (I_Z_GeneraOrdenPago)MTable.get(getCtx(), I_Z_GeneraOrdenPago.Table_Name)
			.getPO(getZ_GeneraOrdenPago_ID(), get_TrxName());	}

	/** Set Z_GeneraOrdenPago ID.
		@param Z_GeneraOrdenPago_ID Z_GeneraOrdenPago ID	  */
	public void setZ_GeneraOrdenPago_ID (int Z_GeneraOrdenPago_ID)
	{
		if (Z_GeneraOrdenPago_ID < 1) 
			set_Value (COLUMNNAME_Z_GeneraOrdenPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_GeneraOrdenPago_ID, Integer.valueOf(Z_GeneraOrdenPago_ID));
	}

	/** Get Z_GeneraOrdenPago ID.
		@return Z_GeneraOrdenPago ID	  */
	public int getZ_GeneraOrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraOrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio() throws RuntimeException
    {
		return (I_Z_MedioPagoFolio)MTable.get(getCtx(), I_Z_MedioPagoFolio.Table_Name)
			.getPO(getZ_MedioPagoFolio_ID(), get_TrxName());	}

	/** Set Z_MedioPagoFolio ID.
		@param Z_MedioPagoFolio_ID Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID (int Z_MedioPagoFolio_ID)
	{
		if (Z_MedioPagoFolio_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoFolio_ID, Integer.valueOf(Z_MedioPagoFolio_ID));
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

	public I_Z_MedioPagoItem getZ_MedioPagoItem() throws RuntimeException
    {
		return (I_Z_MedioPagoItem)MTable.get(getCtx(), I_Z_MedioPagoItem.Table_Name)
			.getPO(getZ_MedioPagoItem_ID(), get_TrxName());	}

	/** Set Z_MedioPagoItem ID.
		@param Z_MedioPagoItem_ID Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID (int Z_MedioPagoItem_ID)
	{
		if (Z_MedioPagoItem_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoItem_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoItem_ID, Integer.valueOf(Z_MedioPagoItem_ID));
	}

	/** Get Z_MedioPagoItem ID.
		@return Z_MedioPagoItem ID	  */
	public int getZ_MedioPagoItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException
    {
		return (I_Z_OrdenPago)MTable.get(getCtx(), I_Z_OrdenPago.Table_Name)
			.getPO(getZ_OrdenPago_ID(), get_TrxName());	}

	/** Set Z_OrdenPago ID.
		@param Z_OrdenPago_ID Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID (int Z_OrdenPago_ID)
	{
		if (Z_OrdenPago_ID < 1) 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OrdenPago_ID, Integer.valueOf(Z_OrdenPago_ID));
	}

	/** Get Z_OrdenPago ID.
		@return Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_OrdenPagoMedio ID.
		@param Z_OrdenPagoMedio_ID Z_OrdenPagoMedio ID	  */
	public void setZ_OrdenPagoMedio_ID (int Z_OrdenPagoMedio_ID)
	{
		if (Z_OrdenPagoMedio_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OrdenPagoMedio_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OrdenPagoMedio_ID, Integer.valueOf(Z_OrdenPagoMedio_ID));
	}

	/** Get Z_OrdenPagoMedio ID.
		@return Z_OrdenPagoMedio ID	  */
	public int getZ_OrdenPagoMedio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OrdenPagoMedio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}