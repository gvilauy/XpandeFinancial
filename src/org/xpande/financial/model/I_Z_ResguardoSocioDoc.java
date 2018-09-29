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

/** Generated Interface for Z_ResguardoSocioDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ResguardoSocioDoc 
{

    /** TableName=Z_ResguardoSocioDoc */
    public static final String Table_Name = "Z_ResguardoSocioDoc";

    /** AD_Table_ID=1000096 */
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

    /** Column name AmtRetencion */
    public static final String COLUMNNAME_AmtRetencion = "AmtRetencion";

	/** Set AmtRetencion.
	  * Monto retención
	  */
	public void setAmtRetencion(BigDecimal AmtRetencion);

	/** Get AmtRetencion.
	  * Monto retención
	  */
	public BigDecimal getAmtRetencion();

    /** Column name AmtRetencionMO */
    public static final String COLUMNNAME_AmtRetencionMO = "AmtRetencionMO";

	/** Set AmtRetencionMO.
	  * Monto retención en Moneda Origen que es igual a la moneda del documento
	  */
	public void setAmtRetencionMO(BigDecimal AmtRetencionMO);

	/** Get AmtRetencionMO.
	  * Monto retención en Moneda Origen que es igual a la moneda del documento
	  */
	public BigDecimal getAmtRetencionMO();

    /** Column name AmtRounding */
    public static final String COLUMNNAME_AmtRounding = "AmtRounding";

	/** Set AmtRounding.
	  * Monto de redondeo
	  */
	public void setAmtRounding(BigDecimal AmtRounding);

	/** Get AmtRounding.
	  * Monto de redondeo
	  */
	public BigDecimal getAmtRounding();

    /** Column name AmtSubtotal */
    public static final String COLUMNNAME_AmtSubtotal = "AmtSubtotal";

	/** Set AmtSubtotal.
	  * Subtotales para no mostrar impuestos incluídos
	  */
	public void setAmtSubtotal(BigDecimal AmtSubtotal);

	/** Get AmtSubtotal.
	  * Subtotales para no mostrar impuestos incluídos
	  */
	public BigDecimal getAmtSubtotal();

    /** Column name AmtTotal */
    public static final String COLUMNNAME_AmtTotal = "AmtTotal";

	/** Set AmtTotal.
	  * Monto total
	  */
	public void setAmtTotal(BigDecimal AmtTotal);

	/** Get AmtTotal.
	  * Monto total
	  */
	public BigDecimal getAmtTotal();

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

    /** Column name IsPaid */
    public static final String COLUMNNAME_IsPaid = "IsPaid";

	/** Set Paid.
	  * The document is paid
	  */
	public void setIsPaid(boolean IsPaid);

	/** Get Paid.
	  * The document is paid
	  */
	public boolean isPaid();

    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set Tax Amount.
	  * Tax Amount for a document
	  */
	public void setTaxAmt(BigDecimal TaxAmt);

	/** Get Tax Amount.
	  * Tax Amount for a document
	  */
	public BigDecimal getTaxAmt();

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

    /** Column name Z_ResguardoSocioDoc_ID */
    public static final String COLUMNNAME_Z_ResguardoSocioDoc_ID = "Z_ResguardoSocioDoc_ID";

	/** Set Z_ResguardoSocioDoc ID	  */
	public void setZ_ResguardoSocioDoc_ID(int Z_ResguardoSocioDoc_ID);

	/** Get Z_ResguardoSocioDoc ID	  */
	public int getZ_ResguardoSocioDoc_ID();

    /** Column name Z_ResguardoSocio_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_ID = "Z_ResguardoSocio_ID";

	/** Set Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID(int Z_ResguardoSocio_ID);

	/** Get Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException;
}
