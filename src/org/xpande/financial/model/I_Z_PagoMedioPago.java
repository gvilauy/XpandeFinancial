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
package org.xpande.financial.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for Z_PagoMedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_PagoMedioPago 
{

    /** TableName=Z_PagoMedioPago */
    public static final String Table_Name = "Z_PagoMedioPago";

    /** AD_Table_ID=1000187 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID(int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID(int C_BankAccount_ID);

	/** Get Bank Account.
	  * Account at the Bank
	  */
	public int getC_BankAccount_ID();

	public I_C_BankAccount getC_BankAccount() throws RuntimeException;

    /** Column name C_Bank_ID */
    public static final String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/** Set Bank.
	  * Bank
	  */
	public void setC_Bank_ID(int C_Bank_ID);

	/** Get Bank.
	  * Bank
	  */
	public int getC_Bank_ID();

	public I_C_Bank getC_Bank() throws RuntimeException;

    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/** Set Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID(int C_CashBook_ID);

	/** Get Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID();

	public I_C_CashBook getC_CashBook() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID(int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DateEmitted */
    public static final String COLUMNNAME_DateEmitted = "DateEmitted";

	/** Set DateEmitted.
	  * Fecha emisión de un documento
	  */
	public void setDateEmitted(Timestamp DateEmitted);

	/** Get DateEmitted.
	  * Fecha emisión de un documento
	  */
	public Timestamp getDateEmitted();

    /** Column name DocumentNoRef */
    public static final String COLUMNNAME_DocumentNoRef = "DocumentNoRef";

	/** Set DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public void setDocumentNoRef(String DocumentNoRef);

	/** Get DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public String getDocumentNoRef();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate(Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name EmisionManual */
    public static final String COLUMNNAME_EmisionManual = "EmisionManual";

	/** Set EmisionManual.
	  * Emisión manual de documentos
	  */
	public void setEmisionManual(boolean EmisionManual);

	/** Get EmisionManual.
	  * Emisión manual de documentos
	  */
	public boolean isEmisionManual();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive(boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt(boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

    /** Column name MultiplyRate */
    public static final String COLUMNNAME_MultiplyRate = "MultiplyRate";

	/** Set Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate(BigDecimal MultiplyRate);

	/** Get Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate();

    /** Column name TieneBanco */
    public static final String COLUMNNAME_TieneBanco = "TieneBanco";

	/** Set TieneBanco.
	  * Si requiere información de Banco o no
	  */
	public void setTieneBanco(boolean TieneBanco);

	/** Get TieneBanco.
	  * Si requiere información de Banco o no
	  */
	public boolean isTieneBanco();

    /** Column name TieneCaja */
    public static final String COLUMNNAME_TieneCaja = "TieneCaja";

	/** Set TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public void setTieneCaja(boolean TieneCaja);

	/** Get TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja();

    /** Column name TieneCtaBco */
    public static final String COLUMNNAME_TieneCtaBco = "TieneCtaBco";

	/** Set TieneCtaBco.
	  * Si requiere o no una cuenta bancaria asociada
	  */
	public void setTieneCtaBco(boolean TieneCtaBco);

	/** Get TieneCtaBco.
	  * Si requiere o no una cuenta bancaria asociada
	  */
	public boolean isTieneCtaBco();

    /** Column name TieneFecEmi */
    public static final String COLUMNNAME_TieneFecEmi = "TieneFecEmi";

	/** Set TieneFecEmi.
	  * Si lleva o no Fecha de Emisión
	  */
	public void setTieneFecEmi(boolean TieneFecEmi);

	/** Get TieneFecEmi.
	  * Si lleva o no Fecha de Emisión
	  */
	public boolean isTieneFecEmi();

    /** Column name TieneFecVenc */
    public static final String COLUMNNAME_TieneFecVenc = "TieneFecVenc";

	/** Set TieneFecVenc.
	  * Si lleva o no Fecha de Vencimiento
	  */
	public void setTieneFecVenc(boolean TieneFecVenc);

	/** Get TieneFecVenc.
	  * Si lleva o no Fecha de Vencimiento
	  */
	public boolean isTieneFecVenc();

    /** Column name TieneFolio */
    public static final String COLUMNNAME_TieneFolio = "TieneFolio";

	/** Set TieneFolio.
	  * Si requiere o no un folio asociado de medios de pago
	  */
	public void setTieneFolio(boolean TieneFolio);

	/** Get TieneFolio.
	  * Si requiere o no un folio asociado de medios de pago
	  */
	public boolean isTieneFolio();

    /** Column name TieneNroRef */
    public static final String COLUMNNAME_TieneNroRef = "TieneNroRef";

	/** Set TieneNroRef.
	  * Si requiere o no numero de referencia
	  */
	public void setTieneNroRef(boolean TieneNroRef);

	/** Get TieneNroRef.
	  * Si requiere o no numero de referencia
	  */
	public boolean isTieneNroRef();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt(BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name TotalAmtMT */
    public static final String COLUMNNAME_TotalAmtMT = "TotalAmtMT";

	/** Set TotalAmtMT.
	  * Monto total en moneda de la transacción
	  */
	public void setTotalAmtMT(BigDecimal TotalAmtMT);

	/** Get TotalAmtMT.
	  * Monto total en moneda de la transacción
	  */
	public BigDecimal getTotalAmtMT();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Z_MedioPagoFolio_ID */
    public static final String COLUMNNAME_Z_MedioPagoFolio_ID = "Z_MedioPagoFolio_ID";

	/** Set Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID(int Z_MedioPagoFolio_ID);

	/** Get Z_MedioPagoFolio ID	  */
	public int getZ_MedioPagoFolio_ID();

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio() throws RuntimeException;

    /** Column name Z_MedioPago_ID */
    public static final String COLUMNNAME_Z_MedioPago_ID = "Z_MedioPago_ID";

	/** Set Z_MedioPago ID	  */
	public void setZ_MedioPago_ID(int Z_MedioPago_ID);

	/** Get Z_MedioPago ID	  */
	public int getZ_MedioPago_ID();

	public I_Z_MedioPago getZ_MedioPago() throws RuntimeException;

    /** Column name Z_MedioPagoItem_ID */
    public static final String COLUMNNAME_Z_MedioPagoItem_ID = "Z_MedioPagoItem_ID";

	/** Set Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID(int Z_MedioPagoItem_ID);

	/** Get Z_MedioPagoItem ID	  */
	public int getZ_MedioPagoItem_ID();

	public I_Z_MedioPagoItem getZ_MedioPagoItem() throws RuntimeException;

    /** Column name Z_OrdenPago_ID */
    public static final String COLUMNNAME_Z_OrdenPago_ID = "Z_OrdenPago_ID";

	/** Set Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID(int Z_OrdenPago_ID);

	/** Get Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID();

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException;

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID(int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

	public I_Z_Pago getZ_Pago() throws RuntimeException;

    /** Column name Z_PagoMedioPago_ID */
    public static final String COLUMNNAME_Z_PagoMedioPago_ID = "Z_PagoMedioPago_ID";

	/** Set Z_PagoMedioPago ID	  */
	public void setZ_PagoMedioPago_ID(int Z_PagoMedioPago_ID);

	/** Get Z_PagoMedioPago ID	  */
	public int getZ_PagoMedioPago_ID();
}
