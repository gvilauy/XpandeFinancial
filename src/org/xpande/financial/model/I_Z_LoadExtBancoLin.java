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

/** Generated Interface for Z_LoadExtBancoLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_LoadExtBancoLin 
{

    /** TableName=Z_LoadExtBancoLin */
    public static final String Table_Name = "Z_LoadExtBancoLin";

    /** AD_Table_ID=1000332 */
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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Organization.
	  * Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID);

	/** Get Trx Organization.
	  * Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx(Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name Description2 */
    public static final String COLUMNNAME_Description2 = "Description2";

	/** Set Description2.
	  * Observaciones secundarias
	  */
	public void setDescription2(String Description2);

	/** Get Description2.
	  * Observaciones secundarias
	  */
	public String getDescription2();

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

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg(String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name FechaCadena */
    public static final String COLUMNNAME_FechaCadena = "FechaCadena";

	/** Set FechaCadena.
	  * Fecha en formato Cadena
	  */
	public void setFechaCadena(String FechaCadena);

	/** Get FechaCadena.
	  * Fecha en formato Cadena
	  */
	public String getFechaCadena();

    /** Column name FileLineText */
    public static final String COLUMNNAME_FileLineText = "FileLineText";

	/** Set FileLineText	  */
	public void setFileLineText(String FileLineText);

	/** Get FileLineText	  */
	public String getFileLineText();

    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported(boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

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

    /** Column name IsConfirmed */
    public static final String COLUMNNAME_IsConfirmed = "IsConfirmed";

	/** Set Confirmed.
	  * Assignment is confirmed
	  */
	public void setIsConfirmed(boolean IsConfirmed);

	/** Get Confirmed.
	  * Assignment is confirmed
	  */
	public boolean isConfirmed();

    /** Column name IsOmitted */
    public static final String COLUMNNAME_IsOmitted = "IsOmitted";

	/** Set IsOmitted.
	  * Omitida si o no
	  */
	public void setIsOmitted(boolean IsOmitted);

	/** Get IsOmitted.
	  * Omitida si o no
	  */
	public boolean isOmitted();

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected(boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

    /** Column name LineNumber */
    public static final String COLUMNNAME_LineNumber = "LineNumber";

	/** Set LineNumber	  */
	public void setLineNumber(int LineNumber);

	/** Get LineNumber	  */
	public int getLineNumber();

    /** Column name NomMoneda */
    public static final String COLUMNNAME_NomMoneda = "NomMoneda";

	/** Set NomMoneda.
	  * Nombre de Moneda
	  */
	public void setNomMoneda(String NomMoneda);

	/** Get NomMoneda.
	  * Nombre de Moneda
	  */
	public String getNomMoneda();

    /** Column name NroCuenta */
    public static final String COLUMNNAME_NroCuenta = "NroCuenta";

	/** Set NroCuenta.
	  * Número de Cuenta
	  */
	public void setNroCuenta(String NroCuenta);

	/** Get NroCuenta.
	  * Número de Cuenta
	  */
	public String getNroCuenta();

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

    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";

	/** Set Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public void setUUID(String UUID);

	/** Get Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public String getUUID();

    /** Column name Z_LoadExtBanco_ID */
    public static final String COLUMNNAME_Z_LoadExtBanco_ID = "Z_LoadExtBanco_ID";

	/** Set Z_LoadExtBanco ID	  */
	public void setZ_LoadExtBanco_ID(int Z_LoadExtBanco_ID);

	/** Get Z_LoadExtBanco ID	  */
	public int getZ_LoadExtBanco_ID();

	public I_Z_LoadExtBanco getZ_LoadExtBanco() throws RuntimeException;

    /** Column name Z_LoadExtBancoLin_ID */
    public static final String COLUMNNAME_Z_LoadExtBancoLin_ID = "Z_LoadExtBancoLin_ID";

	/** Set Z_LoadExtBancoLin ID	  */
	public void setZ_LoadExtBancoLin_ID(int Z_LoadExtBancoLin_ID);

	/** Get Z_LoadExtBancoLin ID	  */
	public int getZ_LoadExtBancoLin_ID();
}
