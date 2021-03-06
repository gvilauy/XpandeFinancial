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

/** Generated Interface for Z_MedioPagoItem
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_MedioPagoItem 
{

    /** TableName=Z_MedioPagoItem */
    public static final String Table_Name = "Z_MedioPagoItem";

    /** AD_Table_ID=1000113 */
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

    /** Column name Anulado */
    public static final String COLUMNNAME_Anulado = "Anulado";

	/** Set Anulado.
	  * Si esta anulado o no
	  */
	public void setAnulado (boolean Anulado);

	/** Get Anulado.
	  * Si esta anulado o no
	  */
	public boolean isAnulado();

    /** Column name C_BankAccount_ID */
    public static final String COLUMNNAME_C_BankAccount_ID = "C_BankAccount_ID";

	/** Set Bank Account.
	  * Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID);

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
	public void setC_Bank_ID (int C_Bank_ID);

	/** Get Bank.
	  * Bank
	  */
	public int getC_Bank_ID();

	public I_C_Bank getC_Bank() throws RuntimeException;

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

    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/** Set Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID);

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
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name Conciliado */
    public static final String COLUMNNAME_Conciliado = "Conciliado";

	/** Set Conciliado.
	  * Conciliado si o no
	  */
	public void setConciliado (boolean Conciliado);

	/** Get Conciliado.
	  * Conciliado si o no
	  */
	public boolean isConciliado();

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
	public void setDateEmitted (Timestamp DateEmitted);

	/** Get DateEmitted.
	  * Fecha emisión de un documento
	  */
	public Timestamp getDateEmitted();

    /** Column name DateRefConcilia */
    public static final String COLUMNNAME_DateRefConcilia = "DateRefConcilia";

	/** Set DateRefConcilia.
	  * Fecha de conciliacion de medio de pago
	  */
	public void setDateRefConcilia (Timestamp DateRefConcilia);

	/** Get DateRefConcilia.
	  * Fecha de conciliacion de medio de pago
	  */
	public Timestamp getDateRefConcilia();

    /** Column name DateRefDeposito */
    public static final String COLUMNNAME_DateRefDeposito = "DateRefDeposito";

	/** Set DateRefDeposito.
	  * Fecha referencia de un documento de Deposito de Medio de Pago
	  */
	public void setDateRefDeposito (Timestamp DateRefDeposito);

	/** Get DateRefDeposito.
	  * Fecha referencia de un documento de Deposito de Medio de Pago
	  */
	public Timestamp getDateRefDeposito();

    /** Column name DateRefPago */
    public static final String COLUMNNAME_DateRefPago = "DateRefPago";

	/** Set DateRefPago.
	  * Fecha referencia de un documento de pago / cobro
	  */
	public void setDateRefPago (Timestamp DateRefPago);

	/** Get DateRefPago.
	  * Fecha referencia de un documento de pago / cobro
	  */
	public Timestamp getDateRefPago();

    /** Column name Depositado */
    public static final String COLUMNNAME_Depositado = "Depositado";

	/** Set Depositado.
	  * Si esta o no depositado
	  */
	public void setDepositado (boolean Depositado);

	/** Get Depositado.
	  * Si esta o no depositado
	  */
	public boolean isDepositado();

    /** Column name DocumentSerie */
    public static final String COLUMNNAME_DocumentSerie = "DocumentSerie";

	/** Set DocumentSerie.
	  * Serie de un Documento
	  */
	public void setDocumentSerie (String DocumentSerie);

	/** Get DocumentSerie.
	  * Serie de un Documento
	  */
	public String getDocumentSerie();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name Emitido */
    public static final String COLUMNNAME_Emitido = "Emitido";

	/** Set Emitido.
	  * Documento emitido
	  */
	public void setEmitido (boolean Emitido);

	/** Get Emitido.
	  * Documento emitido
	  */
	public boolean isEmitido();

    /** Column name Entregado */
    public static final String COLUMNNAME_Entregado = "Entregado";

	/** Set Entregado.
	  * Si esta entregado o no
	  */
	public void setEntregado (boolean Entregado);

	/** Get Entregado.
	  * Si esta entregado o no
	  */
	public boolean isEntregado();

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

    /** Column name IsForzedPaid */
    public static final String COLUMNNAME_IsForzedPaid = "IsForzedPaid";

	/** Set IsForzedPaid.
	  * Si esta o no marcada como pagada de manera forzada
	  */
	public void setIsForzedPaid (boolean IsForzedPaid);

	/** Get IsForzedPaid.
	  * Si esta o no marcada como pagada de manera forzada
	  */
	public boolean isForzedPaid();

    /** Column name IsOwn */
    public static final String COLUMNNAME_IsOwn = "IsOwn";

	/** Set IsOwn.
	  * Si le pertenece o no
	  */
	public void setIsOwn (boolean IsOwn);

	/** Get IsOwn.
	  * Si le pertenece o no
	  */
	public boolean isOwn();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/** Set Receipt.
	  * This is a sales transaction (receipt)
	  */
	public void setIsReceipt (boolean IsReceipt);

	/** Get Receipt.
	  * This is a sales transaction (receipt)
	  */
	public boolean isReceipt();

    /** Column name LeyendaImpresion1 */
    public static final String COLUMNNAME_LeyendaImpresion1 = "LeyendaImpresion1";

	/** Set LeyendaImpresion1.
	  * Leyenda de impresión
	  */
	public void setLeyendaImpresion1 (String LeyendaImpresion1);

	/** Get LeyendaImpresion1.
	  * Leyenda de impresión
	  */
	public String getLeyendaImpresion1();

    /** Column name LeyendaImpresion2 */
    public static final String COLUMNNAME_LeyendaImpresion2 = "LeyendaImpresion2";

	/** Set LeyendaImpresion2.
	  * Leyenda de impresión
	  */
	public void setLeyendaImpresion2 (String LeyendaImpresion2);

	/** Get LeyendaImpresion2.
	  * Leyenda de impresión
	  */
	public String getLeyendaImpresion2();

    /** Column name NroMedioPago */
    public static final String COLUMNNAME_NroMedioPago = "NroMedioPago";

	/** Set NroMedioPago.
	  * Numero de medio de pago
	  */
	public void setNroMedioPago (String NroMedioPago);

	/** Get NroMedioPago.
	  * Numero de medio de pago
	  */
	public String getNroMedioPago();

    /** Column name Reemplazado */
    public static final String COLUMNNAME_Reemplazado = "Reemplazado";

	/** Set Reemplazado.
	  * Reemplazado si o no
	  */
	public void setReemplazado (boolean Reemplazado);

	/** Get Reemplazado.
	  * Reemplazado si o no
	  */
	public boolean isReemplazado();

    /** Column name Ref_Cobro_ID */
    public static final String COLUMNNAME_Ref_Cobro_ID = "Ref_Cobro_ID";

	/** Set Ref_Cobro_ID.
	  * ID de un documento de Cobro referenciado.
	  */
	public void setRef_Cobro_ID (int Ref_Cobro_ID);

	/** Get Ref_Cobro_ID.
	  * ID de un documento de Cobro referenciado.
	  */
	public int getRef_Cobro_ID();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

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

    /** Column name Z_ConciliaMedioPago_ID */
    public static final String COLUMNNAME_Z_ConciliaMedioPago_ID = "Z_ConciliaMedioPago_ID";

	/** Set Z_ConciliaMedioPago ID	  */
	public void setZ_ConciliaMedioPago_ID (int Z_ConciliaMedioPago_ID);

	/** Get Z_ConciliaMedioPago ID	  */
	public int getZ_ConciliaMedioPago_ID();

	public I_Z_ConciliaMedioPago getZ_ConciliaMedioPago() throws RuntimeException;

    /** Column name Z_DepositoMedioPago_ID */
    public static final String COLUMNNAME_Z_DepositoMedioPago_ID = "Z_DepositoMedioPago_ID";

	/** Set Z_DepositoMedioPago ID	  */
	public void setZ_DepositoMedioPago_ID (int Z_DepositoMedioPago_ID);

	/** Get Z_DepositoMedioPago ID	  */
	public int getZ_DepositoMedioPago_ID();

	public I_Z_DepositoMedioPago getZ_DepositoMedioPago() throws RuntimeException;

    /** Column name Z_EmisionMedioPago_ID */
    public static final String COLUMNNAME_Z_EmisionMedioPago_ID = "Z_EmisionMedioPago_ID";

	/** Set Z_EmisionMedioPago ID	  */
	public void setZ_EmisionMedioPago_ID (int Z_EmisionMedioPago_ID);

	/** Get Z_EmisionMedioPago ID	  */
	public int getZ_EmisionMedioPago_ID();

	public I_Z_EmisionMedioPago getZ_EmisionMedioPago() throws RuntimeException;

    /** Column name Z_MedioPagoFolio_ID */
    public static final String COLUMNNAME_Z_MedioPagoFolio_ID = "Z_MedioPagoFolio_ID";

	/** Set Z_MedioPagoFolio ID	  */
	public void setZ_MedioPagoFolio_ID (int Z_MedioPagoFolio_ID);

	/** Get Z_MedioPagoFolio ID	  */
	public int getZ_MedioPagoFolio_ID();

	public I_Z_MedioPagoFolio getZ_MedioPagoFolio() throws RuntimeException;

    /** Column name Z_MedioPago_ID */
    public static final String COLUMNNAME_Z_MedioPago_ID = "Z_MedioPago_ID";

	/** Set Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID);

	/** Get Z_MedioPago ID	  */
	public int getZ_MedioPago_ID();

	public I_Z_MedioPago getZ_MedioPago() throws RuntimeException;

    /** Column name Z_MedioPagoIdent_ID */
    public static final String COLUMNNAME_Z_MedioPagoIdent_ID = "Z_MedioPagoIdent_ID";

	/** Set Z_MedioPagoIdent ID	  */
	public void setZ_MedioPagoIdent_ID (int Z_MedioPagoIdent_ID);

	/** Get Z_MedioPagoIdent ID	  */
	public int getZ_MedioPagoIdent_ID();

	public I_Z_MedioPagoIdent getZ_MedioPagoIdent() throws RuntimeException;

    /** Column name Z_MedioPagoItem_ID */
    public static final String COLUMNNAME_Z_MedioPagoItem_ID = "Z_MedioPagoItem_ID";

	/** Set Z_MedioPagoItem ID	  */
	public void setZ_MedioPagoItem_ID (int Z_MedioPagoItem_ID);

	/** Get Z_MedioPagoItem ID	  */
	public int getZ_MedioPagoItem_ID();

    /** Column name Z_MedioPagoItem_Rep_ID */
    public static final String COLUMNNAME_Z_MedioPagoItem_Rep_ID = "Z_MedioPagoItem_Rep_ID";

	/** Set Z_MedioPagoItem_Rep_ID.
	  * Item de  Medio de Pago reemplazante
	  */
	public void setZ_MedioPagoItem_Rep_ID (int Z_MedioPagoItem_Rep_ID);

	/** Get Z_MedioPagoItem_Rep_ID.
	  * Item de  Medio de Pago reemplazante
	  */
	public int getZ_MedioPagoItem_Rep_ID();

	public I_Z_MedioPagoItem getZ_MedioPagoItem_Rep() throws RuntimeException;

    /** Column name Z_MedioPagoReplace_ID */
    public static final String COLUMNNAME_Z_MedioPagoReplace_ID = "Z_MedioPagoReplace_ID";

	/** Set Z_MedioPagoReplace ID	  */
	public void setZ_MedioPagoReplace_ID (int Z_MedioPagoReplace_ID);

	/** Get Z_MedioPagoReplace ID	  */
	public int getZ_MedioPagoReplace_ID();

	public I_Z_MedioPagoReplace getZ_MedioPagoReplace() throws RuntimeException;

    /** Column name Z_MPagoCaja_ID */
    public static final String COLUMNNAME_Z_MPagoCaja_ID = "Z_MPagoCaja_ID";

	/** Set Z_MPagoCaja ID	  */
	public void setZ_MPagoCaja_ID (int Z_MPagoCaja_ID);

	/** Get Z_MPagoCaja ID	  */
	public int getZ_MPagoCaja_ID();

	public I_Z_MPagoCaja getZ_MPagoCaja() throws RuntimeException;

    /** Column name Z_OrdenPago_ID */
    public static final String COLUMNNAME_Z_OrdenPago_ID = "Z_OrdenPago_ID";

	/** Set Z_OrdenPago ID	  */
	public void setZ_OrdenPago_ID (int Z_OrdenPago_ID);

	/** Get Z_OrdenPago ID	  */
	public int getZ_OrdenPago_ID();

	public I_Z_OrdenPago getZ_OrdenPago() throws RuntimeException;

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID (int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

	public I_Z_Pago getZ_Pago() throws RuntimeException;
}
