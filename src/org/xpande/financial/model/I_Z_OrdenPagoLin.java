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

/** Generated Interface for Z_OrdenPagoLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_OrdenPagoLin 
{

    /** TableName=Z_OrdenPagoLin */
    public static final String Table_Name = "Z_OrdenPagoLin";

    /** AD_Table_ID=1000118 */
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
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AmtAllocation */
    public static final String COLUMNNAME_AmtAllocation = "AmtAllocation";

	/** Set AmtAllocation.
	  * Monto afectación
	  */
	public void setAmtAllocation (BigDecimal AmtAllocation);

	/** Get AmtAllocation.
	  * Monto afectación
	  */
	public BigDecimal getAmtAllocation();

    /** Column name AmtAllocationMT */
    public static final String COLUMNNAME_AmtAllocationMT = "AmtAllocationMT";

	/** Set AmtAllocationMT.
	  * Monto afectación en moneda de la transacción
	  */
	public void setAmtAllocationMT (BigDecimal AmtAllocationMT);

	/** Get AmtAllocationMT.
	  * Monto afectación en moneda de la transacción
	  */
	public BigDecimal getAmtAllocationMT();

    /** Column name AmtDocument */
    public static final String COLUMNNAME_AmtDocument = "AmtDocument";

	/** Set AmtDocument.
	  * Monto documento
	  */
	public void setAmtDocument (BigDecimal AmtDocument);

	/** Get AmtDocument.
	  * Monto documento
	  */
	public BigDecimal getAmtDocument();

    /** Column name AmtOpen */
    public static final String COLUMNNAME_AmtOpen = "AmtOpen";

	/** Set AmtOpen.
	  * Monto pendiente
	  */
	public void setAmtOpen (BigDecimal AmtOpen);

	/** Get AmtOpen.
	  * Monto pendiente
	  */
	public BigDecimal getAmtOpen();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

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
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoicePaySchedule_ID */
    public static final String COLUMNNAME_C_InvoicePaySchedule_ID = "C_InvoicePaySchedule_ID";

	/** Set Invoice Payment Schedule.
	  * Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID);

	/** Get Invoice Payment Schedule.
	  * Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID();

	public I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException;

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
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocumentNoRef */
    public static final String COLUMNNAME_DocumentNoRef = "DocumentNoRef";

	/** Set DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public void setDocumentNoRef (String DocumentNoRef);

	/** Get DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public String getDocumentNoRef();

    /** Column name DueDateDoc */
    public static final String COLUMNNAME_DueDateDoc = "DueDateDoc";

	/** Set DueDateDoc.
	  * Vencimiento del documento
	  */
	public void setDueDateDoc (Timestamp DueDateDoc);

	/** Get DueDateDoc.
	  * Vencimiento del documento
	  */
	public Timestamp getDueDateDoc();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name MultiplyRate */
    public static final String COLUMNNAME_MultiplyRate = "MultiplyRate";

	/** Set Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate);

	/** Get Multiply Rate.
	  * Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate();

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

    /** Column name Z_OrdenPago_ID */
    public static final String COLUMNNAME_Z_OrdenPago_ID = "Z_OrdenPago_ID";

	/** Set Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID (int Z_OrdenPago_ID);

	/** Get Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID();

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException;

    /** Column name Z_OrdenPagoLin_ID */
    public static final String COLUMNNAME_Z_OrdenPagoLin_ID = "Z_OrdenPagoLin_ID";

	/** Set Z_OrdenPagoLin ID	  */
	public void setZ_OrdenPagoLin_ID (int Z_OrdenPagoLin_ID);

	/** Get Z_OrdenPagoLin ID	  */
	public int getZ_OrdenPagoLin_ID();

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

	public I_Z_Pago getZ_Pago() throws RuntimeException;

    /** Column name Z_ResguardoSocio_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_ID = "Z_ResguardoSocio_ID";

	/** Set Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID (int Z_ResguardoSocio_ID);

	/** Get Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException;

    /** Column name Z_TransferSaldo_ID */
    public static final String COLUMNNAME_Z_TransferSaldo_ID = "Z_TransferSaldo_ID";

	/** Set Z_TransferSaldo ID	  */
	public void setZ_TransferSaldo_ID (int Z_TransferSaldo_ID);

	/** Get Z_TransferSaldo ID	  */
	public int getZ_TransferSaldo_ID();

	public I_Z_TransferSaldo getZ_TransferSaldo() throws RuntimeException;
}
