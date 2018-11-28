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

/** Generated Interface for Z_GeneraOrdenPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_GeneraOrdenPago 
{

    /** TableName=Z_GeneraOrdenPago */
    public static final String Table_Name = "Z_GeneraOrdenPago";

    /** AD_Table_ID=1000106 */
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

    /** Column name C_Currency_2_ID */
    public static final String COLUMNNAME_C_Currency_2_ID = "C_Currency_2_ID";

	/** Set C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public void setC_Currency_2_ID(int C_Currency_2_ID);

	/** Get C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public int getC_Currency_2_ID();

	public I_C_Currency getC_Currency_2() throws RuntimeException;

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

    /** Column name DateEmittedFrom */
    public static final String COLUMNNAME_DateEmittedFrom = "DateEmittedFrom";

	/** Set DateEmittedFrom.
	  * Desde fecha emision
	  */
	public void setDateEmittedFrom(Timestamp DateEmittedFrom);

	/** Get DateEmittedFrom.
	  * Desde fecha emision
	  */
	public Timestamp getDateEmittedFrom();

    /** Column name DateEmittedTo */
    public static final String COLUMNNAME_DateEmittedTo = "DateEmittedTo";

	/** Set DateEmittedTo.
	  * Hasta fecha de emisión
	  */
	public void setDateEmittedTo(Timestamp DateEmittedTo);

	/** Get DateEmittedTo.
	  * Hasta fecha de emisión
	  */
	public Timestamp getDateEmittedTo();

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

    /** Column name DueDateFrom */
    public static final String COLUMNNAME_DueDateFrom = "DueDateFrom";

	/** Set DueDateFrom.
	  * Desde fecha vencimiento
	  */
	public void setDueDateFrom(Timestamp DueDateFrom);

	/** Get DueDateFrom.
	  * Desde fecha vencimiento
	  */
	public Timestamp getDueDateFrom();

    /** Column name DueDateTo */
    public static final String COLUMNNAME_DueDateTo = "DueDateTo";

	/** Set DueDateTo.
	  * Hasta fecha de vencimiento
	  */
	public void setDueDateTo(Timestamp DueDateTo);

	/** Get DueDateTo.
	  * Hasta fecha de vencimiento
	  */
	public Timestamp getDueDateTo();

    /** Column name GroupLastDue */
    public static final String COLUMNNAME_GroupLastDue = "GroupLastDue";

	/** Set GroupLastDue.
	  * Agrupar por ultimo vencimiento
	  */
	public void setGroupLastDue(boolean GroupLastDue);

	/** Get GroupLastDue.
	  * Agrupar por ultimo vencimiento
	  */
	public boolean isGroupLastDue();

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

    /** Column name PaymentRulePO */
    public static final String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/** Set Payment Rule.
	  * Purchase payment option
	  */
	public void setPaymentRulePO(String PaymentRulePO);

	/** Get Payment Rule.
	  * Purchase payment option
	  */
	public String getPaymentRulePO();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

    /** Column name ProcessButton2 */
    public static final String COLUMNNAME_ProcessButton2 = "ProcessButton2";

	/** Set ProcessButton2.
	  * Botón de Proceso
	  */
	public void setProcessButton2(String ProcessButton2);

	/** Get ProcessButton2.
	  * Botón de Proceso
	  */
	public String getProcessButton2();

    /** Column name ProcessButton3 */
    public static final String COLUMNNAME_ProcessButton3 = "ProcessButton3";

	/** Set ProcessButton3.
	  * Botón para proceso
	  */
	public void setProcessButton3(String ProcessButton3);

	/** Get ProcessButton3.
	  * Botón para proceso
	  */
	public String getProcessButton3();

    /** Column name ProcessButton4 */
    public static final String COLUMNNAME_ProcessButton4 = "ProcessButton4";

	/** Set ProcessButton4.
	  * Botón de Proceso
	  */
	public void setProcessButton4(String ProcessButton4);

	/** Get ProcessButton4.
	  * Botón de Proceso
	  */
	public String getProcessButton4();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing(boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TipoFiltroSocioPago */
    public static final String COLUMNNAME_TipoFiltroSocioPago = "TipoFiltroSocioPago";

	/** Set TipoFiltroSocioPago.
	  * Tipo de filtro para socios de negocio al momento de generar pagos
	  */
	public void setTipoFiltroSocioPago(String TipoFiltroSocioPago);

	/** Get TipoFiltroSocioPago.
	  * Tipo de filtro para socios de negocio al momento de generar pagos
	  */
	public String getTipoFiltroSocioPago();

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

    /** Column name Z_GeneraOrdenPago_ID */
    public static final String COLUMNNAME_Z_GeneraOrdenPago_ID = "Z_GeneraOrdenPago_ID";

	/** Set Z_GeneraOrdenPago ID	  */
	public void setZ_GeneraOrdenPago_ID(int Z_GeneraOrdenPago_ID);

	/** Get Z_GeneraOrdenPago ID	  */
	public int getZ_GeneraOrdenPago_ID();

    /** Column name Z_MedioPagoFolio_2_ID */
    public static final String COLUMNNAME_Z_MedioPagoFolio_2_ID = "Z_MedioPagoFolio_2_ID";

	/** Set Z_MedioPagoFolio_2_ID.
	  * Folio secundario de medios de pago
	  */
	public void setZ_MedioPagoFolio_2_ID(int Z_MedioPagoFolio_2_ID);

	/** Get Z_MedioPagoFolio_2_ID.
	  * Folio secundario de medios de pago
	  */
	public int getZ_MedioPagoFolio_2_ID();

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio_2() throws RuntimeException;

    /** Column name Z_MedioPagoFolio_ID */
    public static final String COLUMNNAME_Z_MedioPagoFolio_ID = "Z_MedioPagoFolio_ID";

	/** Set Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID(int Z_MedioPagoFolio_ID);

	/** Get Z_MedioPagoFolio ID	  */
	public int getZ_MedioPagoFolio_ID();

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio() throws RuntimeException;
}
