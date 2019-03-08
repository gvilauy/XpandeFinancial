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
/** Generated Model - DO NOT CHANGE */
package org.xpande.financial.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_MedioPago
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MedioPago extends PO implements I_Z_MedioPago, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190308L;

    /** Standard Constructor */
    public X_Z_MedioPago (Properties ctx, int Z_MedioPago_ID, String trxName)
    {
      super (ctx, Z_MedioPago_ID, trxName);
      /** if (Z_MedioPago_ID == 0)
        {
			setAplicaEnCobro (true);
// Y
			setAplicaEnPago (true);
// Y
			setCarteraCobro (false);
// N
			setName (null);
			setTieneBanco (false);
// N
			setTieneBancoCobro (false);
// N
			setTieneCaja (false);
// N
			setTieneCajaCobro (false);
// N
			setTieneCtaBco (true);
// Y
			setTieneCtaBcoCobro (false);
// N
			setTieneEmision (false);
// N
			setTieneEmisionCobro (false);
// N
			setTieneFecEmi (false);
// N
			setTieneFecEmiCobro (false);
// N
			setTieneFecVenc (false);
// N
			setTieneFecVencCobro (false);
// N
			setTieneFolio (false);
// N
			setTieneFolioCobro (false);
// N
			setTieneNroRef (false);
// N
			setTieneNroRefCobro (false);
// N
			setValue (null);
			setZ_MedioPago_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MedioPago (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_MedioPago[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AplicaEnCobro.
		@param AplicaEnCobro 
		Si aplica o no a un Cobro
	  */
	public void setAplicaEnCobro (boolean AplicaEnCobro)
	{
		set_Value (COLUMNNAME_AplicaEnCobro, Boolean.valueOf(AplicaEnCobro));
	}

	/** Get AplicaEnCobro.
		@return Si aplica o no a un Cobro
	  */
	public boolean isAplicaEnCobro () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaEnCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set AplicaEnPago.
		@param AplicaEnPago 
		Si se aplica o no en un Pago
	  */
	public void setAplicaEnPago (boolean AplicaEnPago)
	{
		set_Value (COLUMNNAME_AplicaEnPago, Boolean.valueOf(AplicaEnPago));
	}

	/** Get AplicaEnPago.
		@return Si se aplica o no en un Pago
	  */
	public boolean isAplicaEnPago () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaEnPago);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CarteraCobro.
		@param CarteraCobro 
		Si un determinado medio de pago queda o no en cartera de la empresa cuando se recibe en un documento de cobro
	  */
	public void setCarteraCobro (boolean CarteraCobro)
	{
		set_Value (COLUMNNAME_CarteraCobro, Boolean.valueOf(CarteraCobro));
	}

	/** Get CarteraCobro.
		@return Si un determinado medio de pago queda o no en cartera de la empresa cuando se recibe en un documento de cobro
	  */
	public boolean isCarteraCobro () 
	{
		Object oo = get_Value(COLUMNNAME_CarteraCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set TieneBanco.
		@param TieneBanco 
		Si requiere información de Banco o no
	  */
	public void setTieneBanco (boolean TieneBanco)
	{
		set_Value (COLUMNNAME_TieneBanco, Boolean.valueOf(TieneBanco));
	}

	/** Get TieneBanco.
		@return Si requiere información de Banco o no
	  */
	public boolean isTieneBanco () 
	{
		Object oo = get_Value(COLUMNNAME_TieneBanco);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneBancoCobro.
		@param TieneBancoCobro 
		Si requiere información de Banco o no en un documento de Cobro
	  */
	public void setTieneBancoCobro (boolean TieneBancoCobro)
	{
		set_Value (COLUMNNAME_TieneBancoCobro, Boolean.valueOf(TieneBancoCobro));
	}

	/** Get TieneBancoCobro.
		@return Si requiere información de Banco o no en un documento de Cobro
	  */
	public boolean isTieneBancoCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneBancoCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCaja.
		@param TieneCaja 
		Si requiere o no una caja asociada
	  */
	public void setTieneCaja (boolean TieneCaja)
	{
		set_Value (COLUMNNAME_TieneCaja, Boolean.valueOf(TieneCaja));
	}

	/** Get TieneCaja.
		@return Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCaja);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCajaCobro.
		@param TieneCajaCobro 
		Si requiere o no una caja asociada en un documento de cobro
	  */
	public void setTieneCajaCobro (boolean TieneCajaCobro)
	{
		set_Value (COLUMNNAME_TieneCajaCobro, Boolean.valueOf(TieneCajaCobro));
	}

	/** Get TieneCajaCobro.
		@return Si requiere o no una caja asociada en un documento de cobro
	  */
	public boolean isTieneCajaCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCajaCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCtaBco.
		@param TieneCtaBco 
		Si requiere o no una cuenta bancaria asociada
	  */
	public void setTieneCtaBco (boolean TieneCtaBco)
	{
		set_Value (COLUMNNAME_TieneCtaBco, Boolean.valueOf(TieneCtaBco));
	}

	/** Get TieneCtaBco.
		@return Si requiere o no una cuenta bancaria asociada
	  */
	public boolean isTieneCtaBco () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCtaBco);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneCtaBcoCobro.
		@param TieneCtaBcoCobro 
		Si requiere o no una cuenta bancaria asociada en un documento de Cobro
	  */
	public void setTieneCtaBcoCobro (boolean TieneCtaBcoCobro)
	{
		set_Value (COLUMNNAME_TieneCtaBcoCobro, Boolean.valueOf(TieneCtaBcoCobro));
	}

	/** Get TieneCtaBcoCobro.
		@return Si requiere o no una cuenta bancaria asociada en un documento de Cobro
	  */
	public boolean isTieneCtaBcoCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCtaBcoCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneEmision.
		@param TieneEmision 
		Si un determinado medio de pago tiene o no emisión en modulo financiero
	  */
	public void setTieneEmision (boolean TieneEmision)
	{
		set_Value (COLUMNNAME_TieneEmision, Boolean.valueOf(TieneEmision));
	}

	/** Get TieneEmision.
		@return Si un determinado medio de pago tiene o no emisión en modulo financiero
	  */
	public boolean isTieneEmision () 
	{
		Object oo = get_Value(COLUMNNAME_TieneEmision);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneEmisionCobro.
		@param TieneEmisionCobro 
		Si un determinado medio de pago tiene o no emisión en un documento de Cobro
	  */
	public void setTieneEmisionCobro (boolean TieneEmisionCobro)
	{
		set_Value (COLUMNNAME_TieneEmisionCobro, Boolean.valueOf(TieneEmisionCobro));
	}

	/** Get TieneEmisionCobro.
		@return Si un determinado medio de pago tiene o no emisión en un documento de Cobro
	  */
	public boolean isTieneEmisionCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneEmisionCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecEmi.
		@param TieneFecEmi 
		Si lleva o no Fecha de Emisión
	  */
	public void setTieneFecEmi (boolean TieneFecEmi)
	{
		set_Value (COLUMNNAME_TieneFecEmi, Boolean.valueOf(TieneFecEmi));
	}

	/** Get TieneFecEmi.
		@return Si lleva o no Fecha de Emisión
	  */
	public boolean isTieneFecEmi () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecEmi);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecEmiCobro.
		@param TieneFecEmiCobro 
		Si lleva o no Fecha de Emisión en un documento de Cobro
	  */
	public void setTieneFecEmiCobro (boolean TieneFecEmiCobro)
	{
		set_Value (COLUMNNAME_TieneFecEmiCobro, Boolean.valueOf(TieneFecEmiCobro));
	}

	/** Get TieneFecEmiCobro.
		@return Si lleva o no Fecha de Emisión en un documento de Cobro
	  */
	public boolean isTieneFecEmiCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecEmiCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecVenc.
		@param TieneFecVenc 
		Si lleva o no Fecha de Vencimiento
	  */
	public void setTieneFecVenc (boolean TieneFecVenc)
	{
		set_Value (COLUMNNAME_TieneFecVenc, Boolean.valueOf(TieneFecVenc));
	}

	/** Get TieneFecVenc.
		@return Si lleva o no Fecha de Vencimiento
	  */
	public boolean isTieneFecVenc () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecVenc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFecVencCobro.
		@param TieneFecVencCobro 
		Si lleva o no Fecha de Vencimiento en un documento de Cobro
	  */
	public void setTieneFecVencCobro (boolean TieneFecVencCobro)
	{
		set_Value (COLUMNNAME_TieneFecVencCobro, Boolean.valueOf(TieneFecVencCobro));
	}

	/** Get TieneFecVencCobro.
		@return Si lleva o no Fecha de Vencimiento en un documento de Cobro
	  */
	public boolean isTieneFecVencCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFecVencCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFolio.
		@param TieneFolio 
		Si requiere o no un folio asociado de medios de pago
	  */
	public void setTieneFolio (boolean TieneFolio)
	{
		set_Value (COLUMNNAME_TieneFolio, Boolean.valueOf(TieneFolio));
	}

	/** Get TieneFolio.
		@return Si requiere o no un folio asociado de medios de pago
	  */
	public boolean isTieneFolio () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFolio);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneFolioCobro.
		@param TieneFolioCobro 
		Si requiere o no un folio asociado de medios de pago en un documento de cobro
	  */
	public void setTieneFolioCobro (boolean TieneFolioCobro)
	{
		set_Value (COLUMNNAME_TieneFolioCobro, Boolean.valueOf(TieneFolioCobro));
	}

	/** Get TieneFolioCobro.
		@return Si requiere o no un folio asociado de medios de pago en un documento de cobro
	  */
	public boolean isTieneFolioCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneFolioCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneNroRef.
		@param TieneNroRef 
		Si requiere o no numero de referencia
	  */
	public void setTieneNroRef (boolean TieneNroRef)
	{
		set_Value (COLUMNNAME_TieneNroRef, Boolean.valueOf(TieneNroRef));
	}

	/** Get TieneNroRef.
		@return Si requiere o no numero de referencia
	  */
	public boolean isTieneNroRef () 
	{
		Object oo = get_Value(COLUMNNAME_TieneNroRef);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TieneNroRefCobro.
		@param TieneNroRefCobro 
		Si requiere o no numero de referencia en un documento de Cobro
	  */
	public void setTieneNroRefCobro (boolean TieneNroRefCobro)
	{
		set_Value (COLUMNNAME_TieneNroRefCobro, Boolean.valueOf(TieneNroRefCobro));
	}

	/** Get TieneNroRefCobro.
		@return Si requiere o no numero de referencia en un documento de Cobro
	  */
	public boolean isTieneNroRefCobro () 
	{
		Object oo = get_Value(COLUMNNAME_TieneNroRefCobro);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Z_MedioPago ID.
		@param Z_MedioPago_ID Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID)
	{
		if (Z_MedioPago_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPago_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MedioPago_ID, Integer.valueOf(Z_MedioPago_ID));
	}

	/** Get Z_MedioPago ID.
		@return Z_MedioPago ID	  */
	public int getZ_MedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}