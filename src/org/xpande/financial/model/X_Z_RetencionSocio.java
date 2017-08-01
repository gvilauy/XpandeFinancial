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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_RetencionSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetencionSocio extends PO implements I_Z_RetencionSocio, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170801L;

    /** Standard Constructor */
    public X_Z_RetencionSocio (Properties ctx, int Z_RetencionSocio_ID, String trxName)
    {
      super (ctx, Z_RetencionSocio_ID, trxName);
      /** if (Z_RetencionSocio_ID == 0)
        {
			setConsideraUnidadIndexada (false);
// N
			setEmitieDGI (false);
// N
			setName (null);
			setPeriodoControlado (false);
// N
			setPorcRetencion (Env.ZERO);
			setRetencionMontoAplica (null);
			setZ_RetencionSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetencionSocio (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetencionSocio[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtUnidadIndexada.
		@param AmtUnidadIndexada 
		Monto en unidades indexadas
	  */
	public void setAmtUnidadIndexada (BigDecimal AmtUnidadIndexada)
	{
		set_Value (COLUMNNAME_AmtUnidadIndexada, AmtUnidadIndexada);
	}

	/** Get AmtUnidadIndexada.
		@return Monto en unidades indexadas
	  */
	public BigDecimal getAmtUnidadIndexada () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtUnidadIndexada);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CodigoDGI.
		@param CodigoDGI 
		Código DGI de determinado comprobante
	  */
	public void setCodigoDGI (String CodigoDGI)
	{
		set_Value (COLUMNNAME_CodigoDGI, CodigoDGI);
	}

	/** Get CodigoDGI.
		@return Código DGI de determinado comprobante
	  */
	public String getCodigoDGI () 
	{
		return (String)get_Value(COLUMNNAME_CodigoDGI);
	}

	/** Set ConsideraUnidadIndexada.
		@param ConsideraUnidadIndexada 
		Si se considera o no monto en unidades indexadas
	  */
	public void setConsideraUnidadIndexada (boolean ConsideraUnidadIndexada)
	{
		set_Value (COLUMNNAME_ConsideraUnidadIndexada, Boolean.valueOf(ConsideraUnidadIndexada));
	}

	/** Get ConsideraUnidadIndexada.
		@return Si se considera o no monto en unidades indexadas
	  */
	public boolean isConsideraUnidadIndexada () 
	{
		Object oo = get_Value(COLUMNNAME_ConsideraUnidadIndexada);
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

	/** Set EmitieDGI.
		@param EmitieDGI 
		Documento emitido por DGI
	  */
	public void setEmitieDGI (boolean EmitieDGI)
	{
		set_Value (COLUMNNAME_EmitieDGI, Boolean.valueOf(EmitieDGI));
	}

	/** Get EmitieDGI.
		@return Documento emitido por DGI
	  */
	public boolean isEmitieDGI () 
	{
		Object oo = get_Value(COLUMNNAME_EmitieDGI);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set PeriodoControlado.
		@param PeriodoControlado 
		Si se controla por período contable o no
	  */
	public void setPeriodoControlado (boolean PeriodoControlado)
	{
		set_Value (COLUMNNAME_PeriodoControlado, Boolean.valueOf(PeriodoControlado));
	}

	/** Get PeriodoControlado.
		@return Si se controla por período contable o no
	  */
	public boolean isPeriodoControlado () 
	{
		Object oo = get_Value(COLUMNNAME_PeriodoControlado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PorcRetencion.
		@param PorcRetencion 
		Porcentaje Retención 
	  */
	public void setPorcRetencion (BigDecimal PorcRetencion)
	{
		set_Value (COLUMNNAME_PorcRetencion, PorcRetencion);
	}

	/** Get PorcRetencion.
		@return Porcentaje Retención 
	  */
	public BigDecimal getPorcRetencion () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PorcRetencion);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** RetencionMontoAplica AD_Reference_ID=1000012 */
	public static final int RETENCIONMONTOAPLICA_AD_Reference_ID=1000012;
	/** IMPUESTOS = IMPUESTOS */
	public static final String RETENCIONMONTOAPLICA_IMPUESTOS = "IMPUESTOS";
	/** SUBTOTAL = SUBTOTAL */
	public static final String RETENCIONMONTOAPLICA_SUBTOTAL = "SUBTOTAL";
	/** TOTAL = TOTAL */
	public static final String RETENCIONMONTOAPLICA_TOTAL = "TOTAL";
	/** Set RetencionMontoAplica.
		@param RetencionMontoAplica 
		Tipo de monto sobre el cual aplica una determinada Retención a Socio de Negocio
	  */
	public void setRetencionMontoAplica (String RetencionMontoAplica)
	{

		set_Value (COLUMNNAME_RetencionMontoAplica, RetencionMontoAplica);
	}

	/** Get RetencionMontoAplica.
		@return Tipo de monto sobre el cual aplica una determinada Retención a Socio de Negocio
	  */
	public String getRetencionMontoAplica () 
	{
		return (String)get_Value(COLUMNNAME_RetencionMontoAplica);
	}

	/** Set Z_RetencionSocio ID.
		@param Z_RetencionSocio_ID Z_RetencionSocio ID	  */
	public void setZ_RetencionSocio_ID (int Z_RetencionSocio_ID)
	{
		if (Z_RetencionSocio_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocio_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetencionSocio_ID, Integer.valueOf(Z_RetencionSocio_ID));
	}

	/** Get Z_RetencionSocio ID.
		@return Z_RetencionSocio ID	  */
	public int getZ_RetencionSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetencionSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}