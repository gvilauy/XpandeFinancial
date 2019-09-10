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

/** Generated Interface for Z_EmisionMedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_EmisionMedioPago 
{

    /** TableName=Z_EmisionMedioPago */
    public static final String Table_Name = "Z_EmisionMedioPago";

    /** AD_Table_ID=1000126 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID(int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID(int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public I_C_Charge getC_Charge() throws RuntimeException;

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID(int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/** Set Charge amount.
	  * Charge Amount
	  */
	public void setChargeAmt(BigDecimal ChargeAmt);

	/** Get Charge amount.
	  * Charge Amount
	  */
	public BigDecimal getChargeAmt();

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

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate(BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct(Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc(Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription(String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction(String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus(String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo(String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name ExtornarAcct */
    public static final String COLUMNNAME_ExtornarAcct = "ExtornarAcct";

	/** Set ExtornarAcct.
	  * Si se debe o no dar vuelta el asiento contable de un documento
	  */
	public void setExtornarAcct(boolean ExtornarAcct);

	/** Get ExtornarAcct.
	  * Si se debe o no dar vuelta el asiento contable de un documento
	  */
	public boolean isExtornarAcct();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved(boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name Modificable */
    public static final String COLUMNNAME_Modificable = "Modificable";

	/** Set Modificable.
	  * Si este registro es o no modificable
	  */
	public void setModificable(boolean Modificable);

	/** Get Modificable.
	  * Si este registro es o no modificable
	  */
	public boolean isModificable();

    /** Column name PaymentRule */
    public static final String COLUMNNAME_PaymentRule = "PaymentRule";

	/** Set Payment Rule.
	  * How you pay the invoice
	  */
	public void setPaymentRule(String PaymentRule);

	/** Get Payment Rule.
	  * How you pay the invoice
	  */
	public String getPaymentRule();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted(boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name PostedText */
    public static final String COLUMNNAME_PostedText = "PostedText";

	/** Set PostedText.
	  * Texto informativo del estado de contabilización de un docuemento.
	  */
	public void setPostedText(String PostedText);

	/** Get PostedText.
	  * Texto informativo del estado de contabilización de un docuemento.
	  */
	public String getPostedText();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed(boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn(BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing(boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ReferenceNo */
    public static final String COLUMNNAME_ReferenceNo = "ReferenceNo";

	/** Set Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public void setReferenceNo(String ReferenceNo);

	/** Get Reference No.
	  * Your customer or vendor number at the Business Partner's site
	  */
	public String getReferenceNo();

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

    /** Column name Z_EmisionMedioPago_ID */
    public static final String COLUMNNAME_Z_EmisionMedioPago_ID = "Z_EmisionMedioPago_ID";

	/** Set Z_EmisionMedioPago ID	  */
	public void setZ_EmisionMedioPago_ID(int Z_EmisionMedioPago_ID);

	/** Get Z_EmisionMedioPago ID	  */
	public int getZ_EmisionMedioPago_ID();

    /** Column name Z_LoadMedioPago_ID */
    public static final String COLUMNNAME_Z_LoadMedioPago_ID = "Z_LoadMedioPago_ID";

	/** Set Z_LoadMedioPago ID	  */
	public void setZ_LoadMedioPago_ID(int Z_LoadMedioPago_ID);

	/** Get Z_LoadMedioPago ID	  */
	public int getZ_LoadMedioPago_ID();

	public I_Z_LoadMedioPago getZ_LoadMedioPago() throws RuntimeException;

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

    /** Column name Z_MedioPagoIdent_ID */
    public static final String COLUMNNAME_Z_MedioPagoIdent_ID = "Z_MedioPagoIdent_ID";

	/** Set Z_MedioPagoIdent ID	  */
	public void setZ_MedioPagoIdent_ID(int Z_MedioPagoIdent_ID);

	/** Get Z_MedioPagoIdent ID	  */
	public int getZ_MedioPagoIdent_ID();

	public I_Z_MedioPagoIdent getZ_MedioPagoIdent() throws RuntimeException;

    /** Column name Z_MedioPagoItem_ID */
    public static final String COLUMNNAME_Z_MedioPagoItem_ID = "Z_MedioPagoItem_ID";

	/** Set Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID(int Z_MedioPagoItem_ID);

	/** Get Z_MedioPagoItem ID	  */
	public int getZ_MedioPagoItem_ID();

	public I_Z_MedioPagoItem getZ_MedioPagoItem() throws RuntimeException;

    /** Column name Z_MedioPagoReplace_ID */
    public static final String COLUMNNAME_Z_MedioPagoReplace_ID = "Z_MedioPagoReplace_ID";

	/** Set Z_MedioPagoReplace ID	  */
	public void setZ_MedioPagoReplace_ID(int Z_MedioPagoReplace_ID);

	/** Get Z_MedioPagoReplace ID	  */
	public int getZ_MedioPagoReplace_ID();

	public I_Z_MedioPagoReplace getZ_MedioPagoReplace() throws RuntimeException;

    /** Column name Z_MedioPagoReplace_To_ID */
    public static final String COLUMNNAME_Z_MedioPagoReplace_To_ID = "Z_MedioPagoReplace_To_ID";

	/** Set Z_MedioPagoReplace_To_ID.
	  * Reemplazado por este ID de documento de Reemplazo de medio de pago
	  */
	public void setZ_MedioPagoReplace_To_ID(int Z_MedioPagoReplace_To_ID);

	/** Get Z_MedioPagoReplace_To_ID.
	  * Reemplazado por este ID de documento de Reemplazo de medio de pago
	  */
	public int getZ_MedioPagoReplace_To_ID();

	public I_Z_MedioPagoReplace getZ_MedioPagoReplace_To() throws RuntimeException;

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
}
