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

package org.xpande.financial.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.financial.model.MZResguardoSocio;
import org.xpande.financial.model.MZResguardoSocioDoc;
import org.xpande.financial.model.MZResguardoSocioDocTax;
import org.xpande.financial.model.MZResguardoSocioRet;

import java.math.BigDecimal;
import java.util.List;

/** Generated Process for (Z_SeleccionResguardoParaContra)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionResguardoContra extends SeleccionResguardoContraAbstract
{

	MZResguardoSocio resguardoSocioContra = null;

	@Override
	protected void prepare()
	{
		this.resguardoSocioContra = new MZResguardoSocio(getCtx(), this.getRecord_ID(), get_TrxName());
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		try{

			List<Integer> recordIds =  getSelectionKeys();

			// Solo permito seleccionar un resguardo para poder asociar a este contra-resguardo.
			if (recordIds.size() > 1){
				return "@Error@ " + "Debe seleccionar un solo Resguardo";
			}

			recordIds.stream().forEach( key -> {

				// Instancio resguardo seleccionado por el usuario para asociarse a este contra-resguardo.
				MZResguardoSocio resguardoSocioRef = new MZResguardoSocio(getCtx(), key.intValue(), get_TrxName());

				// Cargo datos del resguardo seleccionado en este contra-resguardo
				this.resguardoSocioContra.setZ_ResguardoSocio_Ref_ID(resguardoSocioRef.get_ID());
				this.resguardoSocioContra.setEsContraResguardo(true);
				this.resguardoSocioContra.setTotalAmt(resguardoSocioRef.getTotalAmt());
				this.resguardoSocioContra.saveEx();

				// Referencio resguardo a su contra-resguardo
				resguardoSocioRef.setZ_ResguardoSocio_Ref_ID(this.resguardoSocioContra.get_ID());
				resguardoSocioRef.saveEx();

				// Cargo retenciones
				List<MZResguardoSocioRet> resguardoSocioRets = resguardoSocioRef.getResguardoRets();
				for (MZResguardoSocioRet oldRet: resguardoSocioRets){
					MZResguardoSocioRet newRet = new MZResguardoSocioRet(getCtx(), 0, get_TrxName());
					newRet.setZ_RetencionSocio_ID(oldRet.getZ_RetencionSocio_ID());
					newRet.setZ_ResguardoSocio_ID(this.resguardoSocioContra.get_ID());
					newRet.setAmtRetencion(oldRet.getAmtRetencion());
					newRet.setAmtBase(oldRet.getAmtBase());
					newRet.saveEx();
				}

				// Cargo comprobantes
				List<MZResguardoSocioDoc> resguardoSocioDocs = resguardoSocioRef.getResguardoDocs();
				for (MZResguardoSocioDoc oldDoc: resguardoSocioDocs){
					MZResguardoSocioDoc newDoc = new MZResguardoSocioDoc(getCtx(), 0, get_TrxName());
					newDoc.setZ_ResguardoSocio_ID(this.resguardoSocioContra.get_ID());
					newDoc.setAmtRetencion(oldDoc.getAmtRetencion());
					newDoc.setAmtTotal(oldDoc.getAmtTotal());
					newDoc.setAmtRounding(oldDoc.getAmtRounding());
					newDoc.setTaxAmt(oldDoc.getTaxAmt());
					newDoc.setAmtSubtotal(oldDoc.getAmtSubtotal());
					newDoc.setCurrencyRate(oldDoc.getCurrencyRate());
					newDoc.setIsPaid(oldDoc.isPaid());
					newDoc.setDocBaseType(oldDoc.getDocBaseType());
					newDoc.setDocumentNoRef(oldDoc.getDocumentNoRef());
					newDoc.setDateDoc(oldDoc.getDateDoc());
					newDoc.setC_DocType_ID(oldDoc.getC_DocType_ID());
					newDoc.setC_Invoice_ID(oldDoc.getC_Invoice_ID());
					newDoc.setC_Currency_ID(oldDoc.getC_Currency_ID());
					newDoc.saveEx();

					// Cargo detalle de impuestos de este comprobante
					List<MZResguardoSocioDocTax> resguardoSocioDocTaxes = oldDoc.getDocTaxes();
					for (MZResguardoSocioDocTax oldDocTax: resguardoSocioDocTaxes){
						MZResguardoSocioDocTax newDocTax = new MZResguardoSocioDocTax(getCtx(), 0, get_TrxName());
						newDocTax.setZ_ResguardoSocioDoc_ID(newDoc.get_ID());
						newDocTax.setZ_ResguardoSocio_ID(newDoc.getZ_ResguardoSocio_ID());
						newDocTax.setTaxAmt(oldDocTax.getTaxAmt());
						newDocTax.setC_TaxCategory_ID(oldDocTax.getC_TaxCategory_ID());
						newDocTax.setC_Tax_ID(oldDocTax.getC_Tax_ID());
						newDocTax.setC_Invoice_ID(oldDocTax.getC_Invoice_ID());
						newDocTax.saveEx();
					}
				}

			});

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return "OK";
	}
}