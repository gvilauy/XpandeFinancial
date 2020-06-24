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

/** Generated Interface for Z_FinancialConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_FinancialConfig 
{

    /** TableName=Z_FinancialConfig */
    public static final String Table_Name = "Z_FinancialConfig";

    /** AD_Table_ID=1000132 */
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

    /** Column name ControlaPagos */
    public static final String COLUMNNAME_ControlaPagos = "ControlaPagos";

	/** Set ControlaPagos.
	  * Si se controla o no pagos asociados a un determinado documento
	  */
	public void setControlaPagos (boolean ControlaPagos);

	/** Get ControlaPagos.
	  * Si se controla o no pagos asociados a un determinado documento
	  */
	public boolean isControlaPagos();

    /** Column name ControlaResguardos */
    public static final String COLUMNNAME_ControlaResguardos = "ControlaResguardos";

	/** Set ControlaResguardos.
	  * Si se controla o no que un documento tenga asociado un Resguardo
	  */
	public void setControlaResguardos (boolean ControlaResguardos);

	/** Get ControlaResguardos.
	  * Si se controla o no que un documento tenga asociado un Resguardo
	  */
	public boolean isControlaResguardos();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

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

    /** Column name DefaultDocCCD_ID */
    public static final String COLUMNNAME_DefaultDocCCD_ID = "DefaultDocCCD_ID";

	/** Set DefaultDocCCD_ID.
	  * Documento por defecto para Cobro a Clientes
	  */
	public void setDefaultDocCCD_ID (int DefaultDocCCD_ID);

	/** Get DefaultDocCCD_ID.
	  * Documento por defecto para Cobro a Clientes
	  */
	public int getDefaultDocCCD_ID();

    /** Column name DefaultDocPPD_ID */
    public static final String COLUMNNAME_DefaultDocPPD_ID = "DefaultDocPPD_ID";

	/** Set DefaultDocPPD_ID.
	  * Documento por defecto para Pago a Proveedores
	  */
	public void setDefaultDocPPD_ID (int DefaultDocPPD_ID);

	/** Get DefaultDocPPD_ID.
	  * Documento por defecto para Pago a Proveedores
	  */
	public int getDefaultDocPPD_ID();

    /** Column name DocAjusteCCD_ID */
    public static final String COLUMNNAME_DocAjusteCCD_ID = "DocAjusteCCD_ID";

	/** Set DocAjusteCCD_ID.
	  * ID de Documento para Ajuste de Cuenta Corriente Deudor
	  */
	public void setDocAjusteCCD_ID (int DocAjusteCCD_ID);

	/** Get DocAjusteCCD_ID.
	  * ID de Documento para Ajuste de Cuenta Corriente Deudor
	  */
	public int getDocAjusteCCD_ID();

    /** Column name DocAjustePPD_ID */
    public static final String COLUMNNAME_DocAjustePPD_ID = "DocAjustePPD_ID";

	/** Set DocAjustePPD_ID.
	  * ID de Documento para Ajuste de Cuenta Corriente Proveedor
	  */
	public void setDocAjustePPD_ID (int DocAjustePPD_ID);

	/** Get DocAjustePPD_ID.
	  * ID de Documento para Ajuste de Cuenta Corriente Proveedor
	  */
	public int getDocAjustePPD_ID();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Z_FinancialConfig_ID */
    public static final String COLUMNNAME_Z_FinancialConfig_ID = "Z_FinancialConfig_ID";

	/** Set Z_FinancialConfig ID	  */
	public void setZ_FinancialConfig_ID (int Z_FinancialConfig_ID);

	/** Get Z_FinancialConfig ID	  */
	public int getZ_FinancialConfig_ID();
}
