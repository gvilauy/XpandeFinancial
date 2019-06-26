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

/** Generated Interface for Z_EstadoCuenta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_EstadoCuenta 
{

    /** TableName=Z_EstadoCuenta */
    public static final String Table_Name = "Z_EstadoCuenta";

    /** AD_Table_ID=1000204 */
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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID(int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/** Set Source Credit.
	  * Source Credit Amount
	  */
	public void setAmtSourceCr(BigDecimal AmtSourceCr);

	/** Get Source Credit.
	  * Source Credit Amount
	  */
	public BigDecimal getAmtSourceCr();

    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/** Set Source Debit.
	  * Source Debit Amount
	  */
	public void setAmtSourceDr(BigDecimal AmtSourceDr);

	/** Get Source Debit.
	  * Source Debit Amount
	  */
	public BigDecimal getAmtSourceDr();

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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID(int C_Invoice_ID);

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
	public void setC_InvoicePaySchedule_ID(int C_InvoicePaySchedule_ID);

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
	public void setDateDoc(Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateRefOrdenPago */
    public static final String COLUMNNAME_DateRefOrdenPago = "DateRefOrdenPago";

	/** Set DateRefOrdenPago.
	  * Fecha referencia de Orden de Pago
	  */
	public void setDateRefOrdenPago(Timestamp DateRefOrdenPago);

	/** Get DateRefOrdenPago.
	  * Fecha referencia de Orden de Pago
	  */
	public Timestamp getDateRefOrdenPago();

    /** Column name DateRefPago */
    public static final String COLUMNNAME_DateRefPago = "DateRefPago";

	/** Set DateRefPago.
	  * Fecha referencia de un documento de pago / cobro
	  */
	public void setDateRefPago(Timestamp DateRefPago);

	/** Get DateRefPago.
	  * Fecha referencia de un documento de pago / cobro
	  */
	public Timestamp getDateRefPago();

    /** Column name DateRefResguardo */
    public static final String COLUMNNAME_DateRefResguardo = "DateRefResguardo";

	/** Set DateRefResguardo.
	  * Fecha referencia de un Resguardo a Socio de Negocio
	  */
	public void setDateRefResguardo(Timestamp DateRefResguardo);

	/** Get DateRefResguardo.
	  * Fecha referencia de un Resguardo a Socio de Negocio
	  */
	public Timestamp getDateRefResguardo();

    /** Column name DateRefTransfSaldo */
    public static final String COLUMNNAME_DateRefTransfSaldo = "DateRefTransfSaldo";

	/** Set DateRefTransfSaldo.
	  * Fecha referencia de documento de Transferencia de Saldo
	  */
	public void setDateRefTransfSaldo(Timestamp DateRefTransfSaldo);

	/** Get DateRefTransfSaldo.
	  * Fecha referencia de documento de Transferencia de Saldo
	  */
	public Timestamp getDateRefTransfSaldo();

    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

	/** Set Document BaseType.
	  * Logical type of document
	  */
	public void setDocBaseType(String DocBaseType);

	/** Get Document BaseType.
	  * Logical type of document
	  */
	public String getDocBaseType();

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

    /** Column name EstadoAprobacion */
    public static final String COLUMNNAME_EstadoAprobacion = "EstadoAprobacion";

	/** Set EstadoAprobacion.
	  * Estadode aprobacion de un comprobante
	  */
	public void setEstadoAprobacion(String EstadoAprobacion);

	/** Get EstadoAprobacion.
	  * Estadode aprobacion de un comprobante
	  */
	public String getEstadoAprobacion();

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

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx(boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID(int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name ReferenciaPago */
    public static final String COLUMNNAME_ReferenciaPago = "ReferenciaPago";

	/** Set ReferenciaPago.
	  * Referencia descriptiva para pagos
	  */
	public void setReferenciaPago(String ReferenciaPago);

	/** Get ReferenciaPago.
	  * Referencia descriptiva para pagos
	  */
	public String getReferenciaPago();

    /** Column name Ref_Pago_ID */
    public static final String COLUMNNAME_Ref_Pago_ID = "Ref_Pago_ID";

	/** Set Ref_Pago_ID.
	  * ID de Pago Referenciado
	  */
	public void setRef_Pago_ID(int Ref_Pago_ID);

	/** Get Ref_Pago_ID.
	  * ID de Pago Referenciado
	  */
	public int getRef_Pago_ID();

    /** Column name TipoSocioNegocio */
    public static final String COLUMNNAME_TipoSocioNegocio = "TipoSocioNegocio";

	/** Set TipoSocioNegocio.
	  * Tipo de Socio de Negocio
	  */
	public void setTipoSocioNegocio(String TipoSocioNegocio);

	/** Get TipoSocioNegocio.
	  * Tipo de Socio de Negocio
	  */
	public String getTipoSocioNegocio();

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

    /** Column name Z_Afectacion_ID */
    public static final String COLUMNNAME_Z_Afectacion_ID = "Z_Afectacion_ID";

	/** Set Z_Afectacion ID	  */
	public void setZ_Afectacion_ID(int Z_Afectacion_ID);

	/** Get Z_Afectacion ID	  */
	public int getZ_Afectacion_ID();

	public I_Z_Afectacion getZ_Afectacion() throws RuntimeException;

    /** Column name Z_EstadoCuenta_ID */
    public static final String COLUMNNAME_Z_EstadoCuenta_ID = "Z_EstadoCuenta_ID";

	/** Set Z_EstadoCuenta ID	  */
	public void setZ_EstadoCuenta_ID(int Z_EstadoCuenta_ID);

	/** Get Z_EstadoCuenta ID	  */
	public int getZ_EstadoCuenta_ID();

    /** Column name Z_OrdenPago_ID */
    public static final String COLUMNNAME_Z_OrdenPago_ID = "Z_OrdenPago_ID";

	/** Set Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID(int Z_OrdenPago_ID);

	/** Get Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID();

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException;

    /** Column name Z_OrdenPago_To_ID */
    public static final String COLUMNNAME_Z_OrdenPago_To_ID = "Z_OrdenPago_To_ID";

	/** Set Z_OrdenPago_To_ID.
	  * Referencia a un documento de Orden de Pago
	  */
	public void setZ_OrdenPago_To_ID(int Z_OrdenPago_To_ID);

	/** Get Z_OrdenPago_To_ID.
	  * Referencia a un documento de Orden de Pago
	  */
	public int getZ_OrdenPago_To_ID();

	public I_Z_OrdenPago getZ_OrdenPago_To() throws RuntimeException;

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID(int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

	public I_Z_Pago getZ_Pago() throws RuntimeException;

    /** Column name Z_Pago_To_ID */
    public static final String COLUMNNAME_Z_Pago_To_ID = "Z_Pago_To_ID";

	/** Set Z_Pago_To_ID.
	  * Referencia a un documento de Pago
	  */
	public void setZ_Pago_To_ID(int Z_Pago_To_ID);

	/** Get Z_Pago_To_ID.
	  * Referencia a un documento de Pago
	  */
	public int getZ_Pago_To_ID();

	public I_Z_Pago getZ_Pago_To() throws RuntimeException;

    /** Column name Z_ResguardoSocio_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_ID = "Z_ResguardoSocio_ID";

	/** Set Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID(int Z_ResguardoSocio_ID);

	/** Get Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException;

    /** Column name Z_ResguardoSocio_To_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_To_ID = "Z_ResguardoSocio_To_ID";

	/** Set Z_ResguardoSocio_To_ID.
	  * Referencia a un Resguardo Socio Destino
	  */
	public void setZ_ResguardoSocio_To_ID(int Z_ResguardoSocio_To_ID);

	/** Get Z_ResguardoSocio_To_ID.
	  * Referencia a un Resguardo Socio Destino
	  */
	public int getZ_ResguardoSocio_To_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio_To() throws RuntimeException;

    /** Column name Z_TransferSaldo_ID */
    public static final String COLUMNNAME_Z_TransferSaldo_ID = "Z_TransferSaldo_ID";

	/** Set Z_TransferSaldo ID	  */
	public void setZ_TransferSaldo_ID(int Z_TransferSaldo_ID);

	/** Get Z_TransferSaldo ID	  */
	public int getZ_TransferSaldo_ID();

	public I_Z_TransferSaldo getZ_TransferSaldo() throws RuntimeException;

    /** Column name Z_TransferSaldo_To_ID */
    public static final String COLUMNNAME_Z_TransferSaldo_To_ID = "Z_TransferSaldo_To_ID";

	/** Set Z_TransferSaldo_To_ID.
	  * Referencia a un documento de Transferencia de Saldo
	  */
	public void setZ_TransferSaldo_To_ID(int Z_TransferSaldo_To_ID);

	/** Get Z_TransferSaldo_To_ID.
	  * Referencia a un documento de Transferencia de Saldo
	  */
	public int getZ_TransferSaldo_To_ID();

	public I_Z_TransferSaldo getZ_TransferSaldo_To() throws RuntimeException;
}
