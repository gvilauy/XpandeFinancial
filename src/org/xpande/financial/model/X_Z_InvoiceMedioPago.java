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

/** Generated Model for Z_InvoiceMedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_InvoiceMedioPago extends PO implements I_Z_InvoiceMedioPago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20201121L;

    /** Standard Constructor */
    public X_Z_InvoiceMedioPago (Properties ctx, int Z_InvoiceMedioPago_ID, String trxName)
    {
      super (ctx, Z_InvoiceMedioPago_ID, trxName);
      /** if (Z_InvoiceMedioPago_ID == 0)
        {
			setC_Currency_ID (0);
			setC_Invoice_ID (0);
			setEmisionManual (true);
// Y
			setIsReceipt (false);
// N
			setMultiplyRate (Env.ZERO);
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
			setTotalAmtMT (Env.ZERO);
			setZ_InvoiceMedioPago_ID (0);
			setZ_MedioPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_InvoiceMedioPago (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_InvoiceMedioPago[")
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

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
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

	/** Set Receipt.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Receipt.
		@return This is a sales transaction (receipt)
	  */
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_Value (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set TotalAmtMT.
		@param TotalAmtMT 
		Monto total en moneda de la transacción
	  */
	public void setTotalAmtMT (BigDecimal TotalAmtMT)
	{
		set_Value (COLUMNNAME_TotalAmtMT, TotalAmtMT);
	}

	/** Get TotalAmtMT.
		@return Monto total en moneda de la transacción
	  */
	public BigDecimal getTotalAmtMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmtMT);
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

	/** Set Z_InvoiceMedioPago ID.
		@param Z_InvoiceMedioPago_ID Z_InvoiceMedioPago ID	  */
	public void setZ_InvoiceMedioPago_ID (int Z_InvoiceMedioPago_ID)
	{
		if (Z_InvoiceMedioPago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceMedioPago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceMedioPago_ID, Integer.valueOf(Z_InvoiceMedioPago_ID));
	}

	/** Get Z_InvoiceMedioPago ID.
		@return Z_InvoiceMedioPago ID	  */
	public int getZ_InvoiceMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_InvoiceMedioPago_ID);
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
}