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

/** Generated Model for Z_MedioPagoItem
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_MedioPagoItem extends PO implements I_Z_MedioPagoItem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20210421L;

    /** Standard Constructor */
    public X_Z_MedioPagoItem (Properties ctx, int Z_MedioPagoItem_ID, String trxName)
    {
      super (ctx, Z_MedioPagoItem_ID, trxName);
      /** if (Z_MedioPagoItem_ID == 0)
        {
			setAnulado (false);
// N
			setC_Currency_ID (0);
			setConciliado (false);
// N
			setDepositado (false);
// N
			setEmitido (false);
// N
			setEntregado (false);
// N
			setIsForzedPaid (false);
// N
			setIsOwn (true);
// Y
			setIsPrinted (false);
// N
			setIsReceipt (false);
// N
			setNroMedioPago (null);
			setReemplazado (false);
// N
			setZ_MedioPago_ID (0);
			setZ_MedioPagoItem_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MedioPagoItem (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MedioPagoItem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Anulado.
		@param Anulado 
		Si esta anulado o no
	  */
	public void setAnulado (boolean Anulado)
	{
		set_Value (COLUMNNAME_Anulado, Boolean.valueOf(Anulado));
	}

	/** Get Anulado.
		@return Si esta anulado o no
	  */
	public boolean isAnulado () 
	{
		Object oo = get_Value(COLUMNNAME_Anulado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Conciliado.
		@param Conciliado 
		Conciliado si o no
	  */
	public void setConciliado (boolean Conciliado)
	{
		set_Value (COLUMNNAME_Conciliado, Boolean.valueOf(Conciliado));
	}

	/** Get Conciliado.
		@return Conciliado si o no
	  */
	public boolean isConciliado () 
	{
		Object oo = get_Value(COLUMNNAME_Conciliado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set DateRefConcilia.
		@param DateRefConcilia 
		Fecha de conciliacion de medio de pago
	  */
	public void setDateRefConcilia (Timestamp DateRefConcilia)
	{
		set_Value (COLUMNNAME_DateRefConcilia, DateRefConcilia);
	}

	/** Get DateRefConcilia.
		@return Fecha de conciliacion de medio de pago
	  */
	public Timestamp getDateRefConcilia () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefConcilia);
	}

	/** Set DateRefDeposito.
		@param DateRefDeposito 
		Fecha referencia de un documento de Deposito de Medio de Pago
	  */
	public void setDateRefDeposito (Timestamp DateRefDeposito)
	{
		set_Value (COLUMNNAME_DateRefDeposito, DateRefDeposito);
	}

	/** Get DateRefDeposito.
		@return Fecha referencia de un documento de Deposito de Medio de Pago
	  */
	public Timestamp getDateRefDeposito () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefDeposito);
	}

	/** Set DateRefPago.
		@param DateRefPago 
		Fecha referencia de un documento de pago / cobro
	  */
	public void setDateRefPago (Timestamp DateRefPago)
	{
		set_Value (COLUMNNAME_DateRefPago, DateRefPago);
	}

	/** Get DateRefPago.
		@return Fecha referencia de un documento de pago / cobro
	  */
	public Timestamp getDateRefPago () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateRefPago);
	}

	/** Set Depositado.
		@param Depositado 
		Si esta o no depositado
	  */
	public void setDepositado (boolean Depositado)
	{
		set_Value (COLUMNNAME_Depositado, Boolean.valueOf(Depositado));
	}

	/** Get Depositado.
		@return Si esta o no depositado
	  */
	public boolean isDepositado () 
	{
		Object oo = get_Value(COLUMNNAME_Depositado);
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

	/** Set Emitido.
		@param Emitido 
		Documento emitido
	  */
	public void setEmitido (boolean Emitido)
	{
		set_Value (COLUMNNAME_Emitido, Boolean.valueOf(Emitido));
	}

	/** Get Emitido.
		@return Documento emitido
	  */
	public boolean isEmitido () 
	{
		Object oo = get_Value(COLUMNNAME_Emitido);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Entregado.
		@param Entregado 
		Si esta entregado o no
	  */
	public void setEntregado (boolean Entregado)
	{
		set_Value (COLUMNNAME_Entregado, Boolean.valueOf(Entregado));
	}

	/** Get Entregado.
		@return Si esta entregado o no
	  */
	public boolean isEntregado () 
	{
		Object oo = get_Value(COLUMNNAME_Entregado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ID_Virtual.
		@param ID_Virtual 
		Identificador virtual para tablas
	  */
	public void setID_Virtual (String ID_Virtual)
	{
		throw new IllegalArgumentException ("ID_Virtual is virtual column");	}

	/** Get ID_Virtual.
		@return Identificador virtual para tablas
	  */
	public String getID_Virtual () 
	{
		return (String)get_Value(COLUMNNAME_ID_Virtual);
	}

	/** Set IsForzedPaid.
		@param IsForzedPaid 
		Si esta o no marcada como pagada de manera forzada
	  */
	public void setIsForzedPaid (boolean IsForzedPaid)
	{
		set_Value (COLUMNNAME_IsForzedPaid, Boolean.valueOf(IsForzedPaid));
	}

	/** Get IsForzedPaid.
		@return Si esta o no marcada como pagada de manera forzada
	  */
	public boolean isForzedPaid () 
	{
		Object oo = get_Value(COLUMNNAME_IsForzedPaid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOwn.
		@param IsOwn 
		Si le pertenece o no
	  */
	public void setIsOwn (boolean IsOwn)
	{
		set_Value (COLUMNNAME_IsOwn, Boolean.valueOf(IsOwn));
	}

	/** Get IsOwn.
		@return Si le pertenece o no
	  */
	public boolean isOwn () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
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

	/** Set LeyendaImpresion1.
		@param LeyendaImpresion1 
		Leyenda de impresión
	  */
	public void setLeyendaImpresion1 (String LeyendaImpresion1)
	{
		set_Value (COLUMNNAME_LeyendaImpresion1, LeyendaImpresion1);
	}

	/** Get LeyendaImpresion1.
		@return Leyenda de impresión
	  */
	public String getLeyendaImpresion1 () 
	{
		return (String)get_Value(COLUMNNAME_LeyendaImpresion1);
	}

	/** Set LeyendaImpresion2.
		@param LeyendaImpresion2 
		Leyenda de impresión
	  */
	public void setLeyendaImpresion2 (String LeyendaImpresion2)
	{
		set_Value (COLUMNNAME_LeyendaImpresion2, LeyendaImpresion2);
	}

	/** Get LeyendaImpresion2.
		@return Leyenda de impresión
	  */
	public String getLeyendaImpresion2 () 
	{
		return (String)get_Value(COLUMNNAME_LeyendaImpresion2);
	}

	/** Set NroMedioPago.
		@param NroMedioPago 
		Numero de medio de pago
	  */
	public void setNroMedioPago (String NroMedioPago)
	{
		set_Value (COLUMNNAME_NroMedioPago, NroMedioPago);
	}

	/** Get NroMedioPago.
		@return Numero de medio de pago
	  */
	public String getNroMedioPago () 
	{
		return (String)get_Value(COLUMNNAME_NroMedioPago);
	}

	/** Set Reemplazado.
		@param Reemplazado 
		Reemplazado si o no
	  */
	public void setReemplazado (boolean Reemplazado)
	{
		set_Value (COLUMNNAME_Reemplazado, Boolean.valueOf(Reemplazado));
	}

	/** Get Reemplazado.
		@return Reemplazado si o no
	  */
	public boolean isReemplazado () 
	{
		Object oo = get_Value(COLUMNNAME_Reemplazado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ref_Cobro_ID.
		@param Ref_Cobro_ID 
		ID de un documento de Cobro referenciado.
	  */
	public void setRef_Cobro_ID (int Ref_Cobro_ID)
	{
		if (Ref_Cobro_ID < 1) 
			set_Value (COLUMNNAME_Ref_Cobro_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Cobro_ID, Integer.valueOf(Ref_Cobro_ID));
	}

	/** Get Ref_Cobro_ID.
		@return ID de un documento de Cobro referenciado.
	  */
	public int getRef_Cobro_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Cobro_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_Z_ConciliaMedioPago getZ_ConciliaMedioPago() throws RuntimeException
    {
		return (I_Z_ConciliaMedioPago)MTable.get(getCtx(), I_Z_ConciliaMedioPago.Table_Name)
			.getPO(getZ_ConciliaMedioPago_ID(), get_TrxName());	}

	/** Set Z_ConciliaMedioPago ID.
		@param Z_ConciliaMedioPago_ID Z_ConciliaMedioPago ID	  */
	public void setZ_ConciliaMedioPago_ID (int Z_ConciliaMedioPago_ID)
	{
		if (Z_ConciliaMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_ConciliaMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ConciliaMedioPago_ID, Integer.valueOf(Z_ConciliaMedioPago_ID));
	}

	/** Get Z_ConciliaMedioPago ID.
		@return Z_ConciliaMedioPago ID	  */
	public int getZ_ConciliaMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConciliaMedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_DepositoMedioPago getZ_DepositoMedioPago() throws RuntimeException
    {
		return (I_Z_DepositoMedioPago)MTable.get(getCtx(), I_Z_DepositoMedioPago.Table_Name)
			.getPO(getZ_DepositoMedioPago_ID(), get_TrxName());	}

	/** Set Z_DepositoMedioPago ID.
		@param Z_DepositoMedioPago_ID Z_DepositoMedioPago ID	  */
	public void setZ_DepositoMedioPago_ID (int Z_DepositoMedioPago_ID)
	{
		if (Z_DepositoMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_DepositoMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_DepositoMedioPago_ID, Integer.valueOf(Z_DepositoMedioPago_ID));
	}

	/** Get Z_DepositoMedioPago ID.
		@return Z_DepositoMedioPago ID	  */
	public int getZ_DepositoMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_DepositoMedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_EmisionMedioPago getZ_EmisionMedioPago() throws RuntimeException
    {
		return (I_Z_EmisionMedioPago)MTable.get(getCtx(), I_Z_EmisionMedioPago.Table_Name)
			.getPO(getZ_EmisionMedioPago_ID(), get_TrxName());	}

	/** Set Z_EmisionMedioPago ID.
		@param Z_EmisionMedioPago_ID Z_EmisionMedioPago ID	  */
	public void setZ_EmisionMedioPago_ID (int Z_EmisionMedioPago_ID)
	{
		if (Z_EmisionMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_EmisionMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_EmisionMedioPago_ID, Integer.valueOf(Z_EmisionMedioPago_ID));
	}

	/** Get Z_EmisionMedioPago ID.
		@return Z_EmisionMedioPago ID	  */
	public int getZ_EmisionMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_EmisionMedioPago_ID);
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

	/** Set Z_MedioPagoItem ID.
		@param Z_MedioPagoItem_ID Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID (int Z_MedioPagoItem_ID)
	{
		if (Z_MedioPagoItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPagoItem_ID, Integer.valueOf(Z_MedioPagoItem_ID));
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

	public I_Z_MedioPagoItem getZ_MedioPagoItem_Rep() throws RuntimeException
    {
		return (I_Z_MedioPagoItem)MTable.get(getCtx(), I_Z_MedioPagoItem.Table_Name)
			.getPO(getZ_MedioPagoItem_Rep_ID(), get_TrxName());	}

	/** Set Z_MedioPagoItem_Rep_ID.
		@param Z_MedioPagoItem_Rep_ID 
		Item de  Medio de Pago reemplazante
	  */
	public void setZ_MedioPagoItem_Rep_ID (int Z_MedioPagoItem_Rep_ID)
	{
		if (Z_MedioPagoItem_Rep_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoItem_Rep_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoItem_Rep_ID, Integer.valueOf(Z_MedioPagoItem_Rep_ID));
	}

	/** Get Z_MedioPagoItem_Rep_ID.
		@return Item de  Medio de Pago reemplazante
	  */
	public int getZ_MedioPagoItem_Rep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoItem_Rep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MedioPagoReplace getZ_MedioPagoReplace() throws RuntimeException
    {
		return (I_Z_MedioPagoReplace)MTable.get(getCtx(), I_Z_MedioPagoReplace.Table_Name)
			.getPO(getZ_MedioPagoReplace_ID(), get_TrxName());	}

	/** Set Z_MedioPagoReplace ID.
		@param Z_MedioPagoReplace_ID Z_MedioPagoReplace ID	  */
	public void setZ_MedioPagoReplace_ID (int Z_MedioPagoReplace_ID)
	{
		if (Z_MedioPagoReplace_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoReplace_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoReplace_ID, Integer.valueOf(Z_MedioPagoReplace_ID));
	}

	/** Get Z_MedioPagoReplace ID.
		@return Z_MedioPagoReplace ID	  */
	public int getZ_MedioPagoReplace_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoReplace_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_MPagoCaja getZ_MPagoCaja() throws RuntimeException
    {
		return (I_Z_MPagoCaja)MTable.get(getCtx(), I_Z_MPagoCaja.Table_Name)
			.getPO(getZ_MPagoCaja_ID(), get_TrxName());	}

	/** Set Z_MPagoCaja ID.
		@param Z_MPagoCaja_ID Z_MPagoCaja ID	  */
	public void setZ_MPagoCaja_ID (int Z_MPagoCaja_ID)
	{
		if (Z_MPagoCaja_ID < 1) 
			set_Value (COLUMNNAME_Z_MPagoCaja_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MPagoCaja_ID, Integer.valueOf(Z_MPagoCaja_ID));
	}

	/** Get Z_MPagoCaja ID.
		@return Z_MPagoCaja ID	  */
	public int getZ_MPagoCaja_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MPagoCaja_ID);
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

	public I_Z_Pago getZ_Pago() throws RuntimeException
    {
		return (I_Z_Pago)MTable.get(getCtx(), I_Z_Pago.Table_Name)
			.getPO(getZ_Pago_ID(), get_TrxName());	}

	/** Set Z_Pago ID.
		@param Z_Pago_ID Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID)
	{
		if (Z_Pago_ID < 1) 
			set_Value (COLUMNNAME_Z_Pago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_Pago_ID, Integer.valueOf(Z_Pago_ID));
	}

	/** Get Z_Pago ID.
		@return Z_Pago ID	  */
	public int getZ_Pago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_Pago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}