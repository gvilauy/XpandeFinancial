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

/** Generated Interface for Z_RetencionSocio_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_RetencionSocio_Acct 
{

    /** TableName=Z_RetencionSocio_Acct */
    public static final String Table_Name = "Z_RetencionSocio_Acct";

    /** AD_Table_ID=1000225 */
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

    /** Column name RT_RetencionEmiPte_Acct */
    public static final String COLUMNNAME_RT_RetencionEmiPte_Acct = "RT_RetencionEmiPte_Acct";

	/** Set RT_RetencionEmiPte_Acct.
	  * Cuenta contable puente para reteneciones emitidas a proveedores
	  */
	public void setRT_RetencionEmiPte_Acct(int RT_RetencionEmiPte_Acct);

	/** Get RT_RetencionEmiPte_Acct.
	  * Cuenta contable puente para reteneciones emitidas a proveedores
	  */
	public int getRT_RetencionEmiPte_Acct();

	public I_C_ValidCombination getRT_RetencionEmiPte_A() throws RuntimeException;

    /** Column name RT_RetencionEmitida_Acct */
    public static final String COLUMNNAME_RT_RetencionEmitida_Acct = "RT_RetencionEmitida_Acct";

	/** Set RT_RetencionEmitida_Acct.
	  * Cuenta contable para reteneciones emitidas a proveedores
	  */
	public void setRT_RetencionEmitida_Acct(int RT_RetencionEmitida_Acct);

	/** Get RT_RetencionEmitida_Acct.
	  * Cuenta contable para reteneciones emitidas a proveedores
	  */
	public int getRT_RetencionEmitida_Acct();

	public I_C_ValidCombination getRT_RetencionEmitida_A() throws RuntimeException;

    /** Column name RT_RetencionRecibida_Acct */
    public static final String COLUMNNAME_RT_RetencionRecibida_Acct = "RT_RetencionRecibida_Acct";

	/** Set RT_RetencionRecibida_Acct.
	  * Cuenta contable para retenciones recibidas de clientes
	  */
	public void setRT_RetencionRecibida_Acct(int RT_RetencionRecibida_Acct);

	/** Get RT_RetencionRecibida_Acct.
	  * Cuenta contable para retenciones recibidas de clientes
	  */
	public int getRT_RetencionRecibida_Acct();

	public I_C_ValidCombination getRT_RetencionRecibida_A() throws RuntimeException;

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

    /** Column name Z_RetencionSocio_Acct_ID */
    public static final String COLUMNNAME_Z_RetencionSocio_Acct_ID = "Z_RetencionSocio_Acct_ID";

	/** Set Z_RetencionSocio_Acct ID	  */
	public void setZ_RetencionSocio_Acct_ID(int Z_RetencionSocio_Acct_ID);

	/** Get Z_RetencionSocio_Acct ID	  */
	public int getZ_RetencionSocio_Acct_ID();

    /** Column name Z_RetencionSocio_ID */
    public static final String COLUMNNAME_Z_RetencionSocio_ID = "Z_RetencionSocio_ID";

	/** Set Z_RetencionSocio ID	  */
	public void setZ_RetencionSocio_ID(int Z_RetencionSocio_ID);

	/** Get Z_RetencionSocio ID	  */
	public int getZ_RetencionSocio_ID();

	public I_Z_RetencionSocio getZ_RetencionSocio() throws RuntimeException;
}
