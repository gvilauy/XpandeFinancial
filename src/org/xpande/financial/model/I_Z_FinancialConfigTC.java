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

/** Generated Interface for Z_FinancialConfigTC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_FinancialConfigTC 
{

    /** TableName=Z_FinancialConfigTC */
    public static final String Table_Name = "Z_FinancialConfigTC";

    /** AD_Table_ID=1000152 */
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

    /** Column name CargarTCCompra */
    public static final String COLUMNNAME_CargarTCCompra = "CargarTCCompra";

	/** Set CargarTCCompra.
	  * Si se carga Tasa de Cambio de Compra o no
	  */
	public void setCargarTCCompra(boolean CargarTCCompra);

	/** Get CargarTCCompra.
	  * Si se carga Tasa de Cambio de Compra o no
	  */
	public boolean isCargarTCCompra();

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

    /** Column name CodigoTC */
    public static final String COLUMNNAME_CodigoTC = "CodigoTC";

	/** Set CodigoTC.
	  * Código Tasa de Cambio para carga automática
	  */
	public void setCodigoTC(String CodigoTC);

	/** Get CodigoTC.
	  * Código Tasa de Cambio para carga automática
	  */
	public String getCodigoTC();

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

    /** Column name Z_FinancialConfig_ID */
    public static final String COLUMNNAME_Z_FinancialConfig_ID = "Z_FinancialConfig_ID";

	/** Set Z_FinancialConfig ID	  */
	public void setZ_FinancialConfig_ID(int Z_FinancialConfig_ID);

	/** Get Z_FinancialConfig ID	  */
	public int getZ_FinancialConfig_ID();

	public I_Z_FinancialConfig getZ_FinancialConfig() throws RuntimeException;

    /** Column name Z_FinancialConfigTC_ID */
    public static final String COLUMNNAME_Z_FinancialConfigTC_ID = "Z_FinancialConfigTC_ID";

	/** Set Z_FinancialConfigTC ID	  */
	public void setZ_FinancialConfigTC_ID(int Z_FinancialConfigTC_ID);

	/** Get Z_FinancialConfigTC ID	  */
	public int getZ_FinancialConfigTC_ID();
}
