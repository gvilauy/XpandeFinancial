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

/** Generated Interface for Z_PagoResguardo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_PagoResguardo 
{

    /** TableName=Z_PagoResguardo */
    public static final String Table_Name = "Z_PagoResguardo";

    /** AD_Table_ID=1000189 */
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

    /** Column name AmtAllocation */
    public static final String COLUMNNAME_AmtAllocation = "AmtAllocation";

	/** Set AmtAllocation.
	  * Monto afectación
	  */
	public void setAmtAllocation(BigDecimal AmtAllocation);

	/** Get AmtAllocation.
	  * Monto afectación
	  */
	public BigDecimal getAmtAllocation();

    /** Column name AmtAllocationMT */
    public static final String COLUMNNAME_AmtAllocationMT = "AmtAllocationMT";

	/** Set AmtAllocationMT.
	  * Monto afectación en moneda de la transacción
	  */
	public void setAmtAllocationMT(BigDecimal AmtAllocationMT);

	/** Get AmtAllocationMT.
	  * Monto afectación en moneda de la transacción
	  */
	public BigDecimal getAmtAllocationMT();

    /** Column name AmtDocument */
    public static final String COLUMNNAME_AmtDocument = "AmtDocument";

	/** Set AmtDocument.
	  * Monto documento
	  */
	public void setAmtDocument(BigDecimal AmtDocument);

	/** Get AmtDocument.
	  * Monto documento
	  */
	public BigDecimal getAmtDocument();

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

    /** Column name IsSelected */
    public static final String COLUMNNAME_IsSelected = "IsSelected";

	/** Set Selected	  */
	public void setIsSelected(boolean IsSelected);

	/** Get Selected	  */
	public boolean isSelected();

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

    /** Column name Z_Pago_ID */
    public static final String COLUMNNAME_Z_Pago_ID = "Z_Pago_ID";

	/** Set Z_Pago ID	  */
	public void setZ_Pago_ID(int Z_Pago_ID);

	/** Get Z_Pago ID	  */
	public int getZ_Pago_ID();

	public I_Z_Pago getZ_Pago() throws RuntimeException;

    /** Column name Z_PagoResguardo_ID */
    public static final String COLUMNNAME_Z_PagoResguardo_ID = "Z_PagoResguardo_ID";

	/** Set Z_PagoResguardo ID	  */
	public void setZ_PagoResguardo_ID(int Z_PagoResguardo_ID);

	/** Get Z_PagoResguardo ID	  */
	public int getZ_PagoResguardo_ID();

    /** Column name Z_ResguardoSocio_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_ID = "Z_ResguardoSocio_ID";

	/** Set Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID(int Z_ResguardoSocio_ID);

	/** Get Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException;
}
