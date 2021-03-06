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

/** Generated Interface for Z_ResguardoSocioDocRet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ResguardoSocioDocRet 
{

    /** TableName=Z_ResguardoSocioDocRet */
    public static final String Table_Name = "Z_ResguardoSocioDocRet";

    /** AD_Table_ID=1000138 */
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

    /** Column name AmtBase */
    public static final String COLUMNNAME_AmtBase = "AmtBase";

	/** Set AmtBase.
	  * Monto base
	  */
	public void setAmtBase(BigDecimal AmtBase);

	/** Get AmtBase.
	  * Monto base
	  */
	public BigDecimal getAmtBase();

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

    /** Column name PorcRetencion */
    public static final String COLUMNNAME_PorcRetencion = "PorcRetencion";

	/** Set PorcRetencion.
	  * Porcentaje Retención 
	  */
	public void setPorcRetencion(BigDecimal PorcRetencion);

	/** Get PorcRetencion.
	  * Porcentaje Retención 
	  */
	public BigDecimal getPorcRetencion();

    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

	/** Set Reference.
	  * Reference for this record
	  */
	public void setReference(String Reference);

	/** Get Reference.
	  * Reference for this record
	  */
	public String getReference();

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

	public I_Z_ResguardoSocioDoc getZ_ResguardoSocioDoc() throws RuntimeException;

    /** Column name Z_ResguardoSocioDocRet_ID */
    public static final String COLUMNNAME_Z_ResguardoSocioDocRet_ID = "Z_ResguardoSocioDocRet_ID";

	/** Set Z_ResguardoSocioDocRet ID	  */
	public void setZ_ResguardoSocioDocRet_ID(int Z_ResguardoSocioDocRet_ID);

	/** Get Z_ResguardoSocioDocRet ID	  */
	public int getZ_ResguardoSocioDocRet_ID();

    /** Column name Z_ResguardoSocio_ID */
    public static final String COLUMNNAME_Z_ResguardoSocio_ID = "Z_ResguardoSocio_ID";

	/** Set Z_ResguardoSocio ID	  */
	public void setZ_ResguardoSocio_ID(int Z_ResguardoSocio_ID);

	/** Get Z_ResguardoSocio ID	  */
	public int getZ_ResguardoSocio_ID();

	public I_Z_ResguardoSocio getZ_ResguardoSocio() throws RuntimeException;

    /** Column name Z_RetencionSocio_ID */
    public static final String COLUMNNAME_Z_RetencionSocio_ID = "Z_RetencionSocio_ID";

	/** Set Z_RetencionSocio ID	  */
	public void setZ_RetencionSocio_ID(int Z_RetencionSocio_ID);

	/** Get Z_RetencionSocio ID	  */
	public int getZ_RetencionSocio_ID();

	public I_Z_RetencionSocio getZ_RetencionSocio() throws RuntimeException;
}
