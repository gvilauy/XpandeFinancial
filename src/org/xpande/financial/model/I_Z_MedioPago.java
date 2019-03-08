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

/** Generated Interface for Z_MedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_MedioPago 
{

    /** TableName=Z_MedioPago */
    public static final String Table_Name = "Z_MedioPago";

    /** AD_Table_ID=1000107 */
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

    /** Column name AplicaEnCobro */
    public static final String COLUMNNAME_AplicaEnCobro = "AplicaEnCobro";

	/** Set AplicaEnCobro.
	  * Si aplica o no a un Cobro
	  */
	public void setAplicaEnCobro(boolean AplicaEnCobro);

	/** Get AplicaEnCobro.
	  * Si aplica o no a un Cobro
	  */
	public boolean isAplicaEnCobro();

    /** Column name AplicaEnPago */
    public static final String COLUMNNAME_AplicaEnPago = "AplicaEnPago";

	/** Set AplicaEnPago.
	  * Si se aplica o no en un Pago
	  */
	public void setAplicaEnPago(boolean AplicaEnPago);

	/** Get AplicaEnPago.
	  * Si se aplica o no en un Pago
	  */
	public boolean isAplicaEnPago();

    /** Column name CarteraCobro */
    public static final String COLUMNNAME_CarteraCobro = "CarteraCobro";

	/** Set CarteraCobro.
	  * Si un determinado medio de pago queda o no en cartera de la empresa cuando se recibe en un documento de cobro
	  */
	public void setCarteraCobro(boolean CarteraCobro);

	/** Get CarteraCobro.
	  * Si un determinado medio de pago queda o no en cartera de la empresa cuando se recibe en un documento de cobro
	  */
	public boolean isCarteraCobro();

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

    /** Column name TieneBanco */
    public static final String COLUMNNAME_TieneBanco = "TieneBanco";

	/** Set TieneBanco.
	  * Si requiere información de Banco o no
	  */
	public void setTieneBanco(boolean TieneBanco);

	/** Get TieneBanco.
	  * Si requiere información de Banco o no
	  */
	public boolean isTieneBanco();

    /** Column name TieneBancoCobro */
    public static final String COLUMNNAME_TieneBancoCobro = "TieneBancoCobro";

	/** Set TieneBancoCobro.
	  * Si requiere información de Banco o no en un documento de Cobro
	  */
	public void setTieneBancoCobro(boolean TieneBancoCobro);

	/** Get TieneBancoCobro.
	  * Si requiere información de Banco o no en un documento de Cobro
	  */
	public boolean isTieneBancoCobro();

    /** Column name TieneCaja */
    public static final String COLUMNNAME_TieneCaja = "TieneCaja";

	/** Set TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public void setTieneCaja(boolean TieneCaja);

	/** Get TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja();

    /** Column name TieneCajaCobro */
    public static final String COLUMNNAME_TieneCajaCobro = "TieneCajaCobro";

	/** Set TieneCajaCobro.
	  * Si requiere o no una caja asociada en un documento de cobro
	  */
	public void setTieneCajaCobro(boolean TieneCajaCobro);

	/** Get TieneCajaCobro.
	  * Si requiere o no una caja asociada en un documento de cobro
	  */
	public boolean isTieneCajaCobro();

    /** Column name TieneCtaBco */
    public static final String COLUMNNAME_TieneCtaBco = "TieneCtaBco";

	/** Set TieneCtaBco.
	  * Si requiere o no una cuenta bancaria asociada
	  */
	public void setTieneCtaBco(boolean TieneCtaBco);

	/** Get TieneCtaBco.
	  * Si requiere o no una cuenta bancaria asociada
	  */
	public boolean isTieneCtaBco();

    /** Column name TieneCtaBcoCobro */
    public static final String COLUMNNAME_TieneCtaBcoCobro = "TieneCtaBcoCobro";

	/** Set TieneCtaBcoCobro.
	  * Si requiere o no una cuenta bancaria asociada en un documento de Cobro
	  */
	public void setTieneCtaBcoCobro(boolean TieneCtaBcoCobro);

	/** Get TieneCtaBcoCobro.
	  * Si requiere o no una cuenta bancaria asociada en un documento de Cobro
	  */
	public boolean isTieneCtaBcoCobro();

    /** Column name TieneEmision */
    public static final String COLUMNNAME_TieneEmision = "TieneEmision";

	/** Set TieneEmision.
	  * Si un determinado medio de pago tiene o no emisión en modulo financiero
	  */
	public void setTieneEmision(boolean TieneEmision);

	/** Get TieneEmision.
	  * Si un determinado medio de pago tiene o no emisión en modulo financiero
	  */
	public boolean isTieneEmision();

    /** Column name TieneEmisionCobro */
    public static final String COLUMNNAME_TieneEmisionCobro = "TieneEmisionCobro";

	/** Set TieneEmisionCobro.
	  * Si un determinado medio de pago tiene o no emisión en un documento de Cobro
	  */
	public void setTieneEmisionCobro(boolean TieneEmisionCobro);

	/** Get TieneEmisionCobro.
	  * Si un determinado medio de pago tiene o no emisión en un documento de Cobro
	  */
	public boolean isTieneEmisionCobro();

    /** Column name TieneFecEmi */
    public static final String COLUMNNAME_TieneFecEmi = "TieneFecEmi";

	/** Set TieneFecEmi.
	  * Si lleva o no Fecha de Emisión
	  */
	public void setTieneFecEmi(boolean TieneFecEmi);

	/** Get TieneFecEmi.
	  * Si lleva o no Fecha de Emisión
	  */
	public boolean isTieneFecEmi();

    /** Column name TieneFecEmiCobro */
    public static final String COLUMNNAME_TieneFecEmiCobro = "TieneFecEmiCobro";

	/** Set TieneFecEmiCobro.
	  * Si lleva o no Fecha de Emisión en un documento de Cobro
	  */
	public void setTieneFecEmiCobro(boolean TieneFecEmiCobro);

	/** Get TieneFecEmiCobro.
	  * Si lleva o no Fecha de Emisión en un documento de Cobro
	  */
	public boolean isTieneFecEmiCobro();

    /** Column name TieneFecVenc */
    public static final String COLUMNNAME_TieneFecVenc = "TieneFecVenc";

	/** Set TieneFecVenc.
	  * Si lleva o no Fecha de Vencimiento
	  */
	public void setTieneFecVenc(boolean TieneFecVenc);

	/** Get TieneFecVenc.
	  * Si lleva o no Fecha de Vencimiento
	  */
	public boolean isTieneFecVenc();

    /** Column name TieneFecVencCobro */
    public static final String COLUMNNAME_TieneFecVencCobro = "TieneFecVencCobro";

	/** Set TieneFecVencCobro.
	  * Si lleva o no Fecha de Vencimiento en un documento de Cobro
	  */
	public void setTieneFecVencCobro(boolean TieneFecVencCobro);

	/** Get TieneFecVencCobro.
	  * Si lleva o no Fecha de Vencimiento en un documento de Cobro
	  */
	public boolean isTieneFecVencCobro();

    /** Column name TieneFolio */
    public static final String COLUMNNAME_TieneFolio = "TieneFolio";

	/** Set TieneFolio.
	  * Si requiere o no un folio asociado de medios de pago
	  */
	public void setTieneFolio(boolean TieneFolio);

	/** Get TieneFolio.
	  * Si requiere o no un folio asociado de medios de pago
	  */
	public boolean isTieneFolio();

    /** Column name TieneFolioCobro */
    public static final String COLUMNNAME_TieneFolioCobro = "TieneFolioCobro";

	/** Set TieneFolioCobro.
	  * Si requiere o no un folio asociado de medios de pago en un documento de cobro
	  */
	public void setTieneFolioCobro(boolean TieneFolioCobro);

	/** Get TieneFolioCobro.
	  * Si requiere o no un folio asociado de medios de pago en un documento de cobro
	  */
	public boolean isTieneFolioCobro();

    /** Column name TieneNroRef */
    public static final String COLUMNNAME_TieneNroRef = "TieneNroRef";

	/** Set TieneNroRef.
	  * Si requiere o no numero de referencia
	  */
	public void setTieneNroRef(boolean TieneNroRef);

	/** Get TieneNroRef.
	  * Si requiere o no numero de referencia
	  */
	public boolean isTieneNroRef();

    /** Column name TieneNroRefCobro */
    public static final String COLUMNNAME_TieneNroRefCobro = "TieneNroRefCobro";

	/** Set TieneNroRefCobro.
	  * Si requiere o no numero de referencia en un documento de Cobro
	  */
	public void setTieneNroRefCobro(boolean TieneNroRefCobro);

	/** Get TieneNroRefCobro.
	  * Si requiere o no numero de referencia en un documento de Cobro
	  */
	public boolean isTieneNroRefCobro();

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
	public void setValue(String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Z_MedioPago_ID */
    public static final String COLUMNNAME_Z_MedioPago_ID = "Z_MedioPago_ID";

	/** Set Z_MedioPago ID	  */
	public void setZ_MedioPago_ID(int Z_MedioPago_ID);

	/** Get Z_MedioPago ID	  */
	public int getZ_MedioPago_ID();
}
