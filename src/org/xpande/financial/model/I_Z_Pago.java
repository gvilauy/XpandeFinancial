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

/** Generated Interface for Z_Pago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_Pago 
{

    /** TableName=Z_Pago */
    public static final String Table_Name = "Z_Pago";

    /** AD_Table_ID=1000185 */
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

    /** Column name AmtAnticipo */
    public static final String COLUMNNAME_AmtAnticipo = "AmtAnticipo";

	/** Set AmtAnticipo.
	  * Monto de Anticipos
	  */
	public void setAmtAnticipo (BigDecimal AmtAnticipo);

	/** Get AmtAnticipo.
	  * Monto de Anticipos
	  */
	public BigDecimal getAmtAnticipo();

    /** Column name Anticipo */
    public static final String COLUMNNAME_Anticipo = "Anticipo";

	/** Set Anticipo.
	  * Si un determinado pago/cobro es solamente un anticipo y por lo tanto no tiene documentos a afectar
	  */
	public void setAnticipo (boolean Anticipo);

	/** Get Anticipo.
	  * Si un determinado pago/cobro es solamente un anticipo y por lo tanto no tiene documentos a afectar
	  */
	public boolean isAnticipo();

    /** Column name AnticipoDirecto */
    public static final String COLUMNNAME_AnticipoDirecto = "AnticipoDirecto";

	/** Set AnticipoDirecto.
	  * Si un determinado pago/cobro es de Anticipo (campo Anticipo = TRUE) y además este ancitipo es directo, es decir tiene medios de pago asociados.
	  */
	public void setAnticipoDirecto (boolean AnticipoDirecto);

	/** Get AnticipoDirecto.
	  * Si un determinado pago/cobro es de Anticipo (campo Anticipo = TRUE) y además este ancitipo es directo, es decir tiene medios de pago asociados.
	  */
	public boolean isAnticipoDirecto();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

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
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_Currency_ID_To */
    public static final String COLUMNNAME_C_Currency_ID_To = "C_Currency_ID_To";

	/** Set Currency To.
	  * Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To);

	/** Get Currency To.
	  * Target currency
	  */
	public int getC_Currency_ID_To();

	public I_C_Currency getC_Currency_To() throws RuntimeException;

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

    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/** Set Charge amount.
	  * Charge Amount
	  */
	public void setChargeAmt (BigDecimal ChargeAmt);

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

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

    /** Column name DateEmittedFrom */
    public static final String COLUMNNAME_DateEmittedFrom = "DateEmittedFrom";

	/** Set DateEmittedFrom.
	  * Desde fecha emision
	  */
	public void setDateEmittedFrom (Timestamp DateEmittedFrom);

	/** Get DateEmittedFrom.
	  * Desde fecha emision
	  */
	public Timestamp getDateEmittedFrom();

    /** Column name DateEmittedTo */
    public static final String COLUMNNAME_DateEmittedTo = "DateEmittedTo";

	/** Set DateEmittedTo.
	  * Hasta fecha de emisión
	  */
	public void setDateEmittedTo (Timestamp DateEmittedTo);

	/** Get DateEmittedTo.
	  * Hasta fecha de emisión
	  */
	public Timestamp getDateEmittedTo();

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name DueDateFrom */
    public static final String COLUMNNAME_DueDateFrom = "DueDateFrom";

	/** Set DueDateFrom.
	  * Desde fecha vencimiento
	  */
	public void setDueDateFrom (Timestamp DueDateFrom);

	/** Get DueDateFrom.
	  * Desde fecha vencimiento
	  */
	public Timestamp getDueDateFrom();

    /** Column name DueDateTo */
    public static final String COLUMNNAME_DueDateTo = "DueDateTo";

	/** Set DueDateTo.
	  * Hasta fecha de vencimiento
	  */
	public void setDueDateTo (Timestamp DueDateTo);

	/** Get DueDateTo.
	  * Hasta fecha de vencimiento
	  */
	public Timestamp getDueDateTo();

    /** Column name ExtornarAcct */
    public static final String COLUMNNAME_ExtornarAcct = "ExtornarAcct";

	/** Set ExtornarAcct.
	  * Si se debe o no dar vuelta el asiento contable de un documento
	  */
	public void setExtornarAcct (boolean ExtornarAcct);

	/** Get ExtornarAcct.
	  * Si se debe o no dar vuelta el asiento contable de un documento
	  */
	public boolean isExtornarAcct();

    /** Column name ID_Virtual */
    public static final String COLUMNNAME_ID_Virtual = "ID_Virtual";

	/** Set ID_Virtual.
	  * Identificador virtual para tablas
	  */
	public void setID_Virtual (String ID_Virtual);

	/** Get ID_Virtual.
	  * Identificador virtual para tablas
	  */
	public String getID_Virtual();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name NroRecibo */
    public static final String COLUMNNAME_NroRecibo = "NroRecibo";

	/** Set NroRecibo.
	  * Número de Recibo asociado a un pago/cobro en módulo Financiero
	  */
	public void setNroRecibo (String NroRecibo);

	/** Get NroRecibo.
	  * Número de Recibo asociado a un pago/cobro en módulo Financiero
	  */
	public String getNroRecibo();

    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/** Set Payment amount.
	  * Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt);

	/** Get Payment amount.
	  * Amount being paid
	  */
	public BigDecimal getPayAmt();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton (String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

    /** Column name ProcessButton2 */
    public static final String COLUMNNAME_ProcessButton2 = "ProcessButton2";

	/** Set ProcessButton2.
	  * Botón de Proceso
	  */
	public void setProcessButton2 (String ProcessButton2);

	/** Get ProcessButton2.
	  * Botón de Proceso
	  */
	public String getProcessButton2();

    /** Column name ProcessButton3 */
    public static final String COLUMNNAME_ProcessButton3 = "ProcessButton3";

	/** Set ProcessButton3.
	  * Botón para proceso
	  */
	public void setProcessButton3 (String ProcessButton3);

	/** Get ProcessButton3.
	  * Botón para proceso
	  */
	public String getProcessButton3();

    /** Column name ProcessButton4 */
    public static final String COLUMNNAME_ProcessButton4 = "ProcessButton4";

	/** Set ProcessButton4.
	  * Botón de Proceso
	  */
	public void setProcessButton4 (String ProcessButton4);

	/** Get ProcessButton4.
	  * Botón de Proceso
	  */
	public String getProcessButton4();

    /** Column name ProcessButton5 */
    public static final String COLUMNNAME_ProcessButton5 = "ProcessButton5";

	/** Set ProcessButton5.
	  * Botón de Proceso
	  */
	public void setProcessButton5 (String ProcessButton5);

	/** Get ProcessButton5.
	  * Botón de Proceso
	  */
	public String getProcessButton5();

    /** Column name ProcessButton6 */
    public static final String COLUMNNAME_ProcessButton6 = "ProcessButton6";

	/** Set ProcessButton6.
	  * Botón de Proceso
	  */
	public void setProcessButton6 (String ProcessButton6);

	/** Get ProcessButton6.
	  * Botón de Proceso
	  */
	public String getProcessButton6();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ReciboAnticipo */
    public static final String COLUMNNAME_ReciboAnticipo = "ReciboAnticipo";

	/** Set ReciboAnticipo.
	  * Si un determinado pago/cobro es solamente un recibo hecho contra un anticipo
	  */
	public void setReciboAnticipo (boolean ReciboAnticipo);

	/** Get ReciboAnticipo.
	  * Si un determinado pago/cobro es solamente un recibo hecho contra un anticipo
	  */
	public boolean isReciboAnticipo();

    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

	/** Set Reference.
	  * Reference for this record
	  */
	public void setReference (String Reference);

	/** Get Reference.
	  * Reference for this record
	  */
	public String getReference();

    /** Column name TieneOrdenPago */
    public static final String COLUMNNAME_TieneOrdenPago = "TieneOrdenPago";

	/** Set TieneOrdenPago.
	  * Si tiene o no ordenes de pago asociadas
	  */
	public void setTieneOrdenPago (boolean TieneOrdenPago);

	/** Get TieneOrdenPago.
	  * Si tiene o no ordenes de pago asociadas
	  */
	public boolean isTieneOrdenPago();

    /** Column name TieneRecibo */
    public static final String COLUMNNAME_TieneRecibo = "TieneRecibo";

	/** Set TieneRecibo.
	  * Si un documento de pago/cobro tiene recibo asociado en módulo Financiero
	  */
	public void setTieneRecibo (boolean TieneRecibo);

	/** Get TieneRecibo.
	  * Si un documento de pago/cobro tiene recibo asociado en módulo Financiero
	  */
	public boolean isTieneRecibo();

    /** Column name TotalMediosPago */
    public static final String COLUMNNAME_TotalMediosPago = "TotalMediosPago";

	/** Set TotalMediosPago.
	  * Monto total de medios de pago considerados en un documento
	  */
	public void setTotalMediosPago (BigDecimal TotalMediosPago);

	/** Get TotalMediosPago.
	  * Monto total de medios de pago considerados en un documento
	  */
	public BigDecimal getTotalMediosPago();

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

    /** Column name Z_Cobrador_ID */
    public static final String COLUMNNAME_Z_Cobrador_ID = "Z_Cobrador_ID";

    /** Column name Z_LoadPago_ID */
    public static final String COLUMNNAME_Z_LoadPago_ID = "Z_LoadPago_ID";

	/** Set Z_LoadPago ID	  */
	public void setZ_LoadPago_ID (int Z_LoadPago_ID);

	/** Get Z_LoadPago ID	  */
	public int getZ_LoadPago_ID();

	public I_Z_LoadPago getZ_LoadPago() throws RuntimeException;

    /** Column name Z_OrdenPago_To_ID */
    public static final String COLUMNNAME_Z_OrdenPago_To_ID = "Z_OrdenPago_To_ID";

	/** Set Z_OrdenPago_To_ID.
	  * Referencia a un documento de Orden de Pago
	  */
	public void setZ_OrdenPago_To_ID (int Z_OrdenPago_To_ID);

	/** Get Z_OrdenPago_To_ID.
	  * Referencia a un documento de Orden de Pago
	  */
	public int getZ_OrdenPago_To_ID();

	public I_Z_OrdenPago getZ_OrdenPago_To() throws RuntimeException;

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

    /** Column name Z_Pago_To_ID */
    public static final String COLUMNNAME_Z_Pago_To_ID = "Z_Pago_To_ID";

	/** Set Z_Pago_To_ID.
	  * Referencia a un documento de Pago
	  */
	public void setZ_Pago_To_ID (int Z_Pago_To_ID);

	/** Get Z_Pago_To_ID.
	  * Referencia a un documento de Pago
	  */
	public int getZ_Pago_To_ID();

	public I_Z_Pago getZ_Pago_To() throws RuntimeException;
}
