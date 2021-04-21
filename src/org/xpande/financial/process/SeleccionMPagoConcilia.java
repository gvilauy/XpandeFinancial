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
import org.xpande.financial.model.*;

import java.util.HashMap;
import java.util.List;

/** Generated Process for (Z_SeleccionMediosPagoConcilia)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.1
 */
public class SeleccionMPagoConcilia extends SeleccionMPagoConciliaAbstract
{
	MZConciliaMedioPago conciliaMedioPago = null;

	@Override
	protected void prepare()
	{
		this.conciliaMedioPago = new MZConciliaMedioPago(getCtx(), this.getRecord_ID(), get_TrxName());
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		try{

			String message = null;

			HashMap<Integer, Integer> hashCurrency = new HashMap<Integer, Integer>();

			List<Integer> recordIds =  getSelectionKeys();

			recordIds.stream().forEach( key -> {

				// Instancio medio de pago seleccionado
				MZMedioPagoItem medioPagoItem = new MZMedioPagoItem(getCtx(), key.intValue(), get_TrxName());

				// Inserto información de este medio de pago en el documento de actual
				MZConciliaMPagoLin conciliaMPagoLin = new MZConciliaMPagoLin(getCtx(), 0, get_TrxName());
				conciliaMPagoLin.setAD_Org_ID(medioPagoItem.getAD_Org_ID());
				conciliaMPagoLin.setZ_ConciliaMedioPago_ID(this.conciliaMedioPago.get_ID());
				conciliaMPagoLin.setZ_MedioPago_ID(medioPagoItem.getZ_MedioPago_ID());
				conciliaMPagoLin.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
				conciliaMPagoLin.setTotalAmt(medioPagoItem.getTotalAmt());
				conciliaMPagoLin.setC_BPartner_ID(medioPagoItem.getC_BPartner_ID());
				conciliaMPagoLin.setC_Currency_ID(medioPagoItem.getC_Currency_ID());
				conciliaMPagoLin.setDateEmitted(medioPagoItem.getDateEmitted());
				conciliaMPagoLin.setDocumentNoRef(medioPagoItem.getNroMedioPago());
				conciliaMPagoLin.setDueDate(medioPagoItem.getDueDate());
				conciliaMPagoLin.saveEx();
			});
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return "OK";
	}
}