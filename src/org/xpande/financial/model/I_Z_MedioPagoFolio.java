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

/** Generated Interface for Z_MedioPagoFolio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_MedioPagoFolio 
{

    /** TableName=Z_MedioPagoFolio */
    public static final String Table_Name = "Z_MedioPagoFolio";

    /** AD_Table_ID=1000112 */
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

    /** Column name Disponible */
    public static final String COLUMNNAME_Disponible = "Disponible";

	/** Set Disponible.
	  * Si un elemento esta Disponible o no
	  */
	public void setDisponible(boolean Disponible);

	/** Get Disponible.
	  * Si un elemento esta Disponible o no
	  */
	public boolean isDisponible();

    /** Column name DocumentSerie */
    public static final String COLUMNNAME_DocumentSerie = "DocumentSerie";

	/** Set DocumentSerie.
	  * Serie de un Documento
	  */
	public void setDocumentSerie(String DocumentSerie);

	/** Get DocumentSerie.
	  * Serie de un Documento
	  */
	public String getDocumentSerie();

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

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted(boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName(String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name NroMedioPagoDesde */
    public static final String COLUMNNAME_NroMedioPagoDesde = "NroMedioPagoDesde";

	/** Set NroMedioPagoDesde.
	  * Nunero de medio de pago desde
	  */
	public void setNroMedioPagoDesde(String NroMedioPagoDesde);

	/** Get NroMedioPagoDesde.
	  * Nunero de medio de pago desde
	  */
	public String getNroMedioPagoDesde();

    /** Column name NumeroDesde */
    public static final String COLUMNNAME_NumeroDesde = "NumeroDesde";

	/** Set NumeroDesde.
	  * Numero desde para Rango de Enteros
	  */
	public void setNumeroDesde(int NumeroDesde);

	/** Get NumeroDesde.
	  * Numero desde para Rango de Enteros
	  */
	public int getNumeroDesde();

    /** Column name NumeroHasta */
    public static final String COLUMNNAME_NumeroHasta = "NumeroHasta";

	/** Set NumeroHasta.
	  * Numero hasta para Rango de Enteros
	  */
	public void setNumeroHasta(int NumeroHasta);

	/** Get NumeroHasta.
	  * Numero hasta para Rango de Enteros
	  */
	public int getNumeroHasta();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

    /** Column name TIpoCheque */
    public static final String COLUMNNAME_TIpoCheque = "TIpoCheque";

	/** Set TIpoCheque.
	  * Tipo de cheque: diferido o día
	  */
	public void setTIpoCheque(String TIpoCheque);

	/** Get TIpoCheque.
	  * Tipo de cheque: diferido o día
	  */
	public String getTIpoCheque();

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

    /** Column name Z_MedioPago_ID */
    public static final String COLUMNNAME_Z_MedioPago_ID = "Z_MedioPago_ID";

	/** Set Z_MedioPago ID	  */
	public void setZ_MedioPago_ID(int Z_MedioPago_ID);

	/** Get Z_MedioPago ID	  */
	public int getZ_MedioPago_ID();

	public I_Z_MedioPago getZ_MedioPago() throws RuntimeException;
}
