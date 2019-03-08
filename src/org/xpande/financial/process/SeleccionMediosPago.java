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
import org.compiere.util.Env;
import org.xpande.financial.model.*;

import java.util.HashMap;
import java.util.List;

/** Generated Process for (Z_SeleccionMediosPago)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionMediosPago extends SeleccionMediosPagoAbstract
{

	MZPago pago = null;

	@Override
	protected void prepare()
	{
		this.pago = new MZPago(getCtx(), this.getRecord_ID(), get_TrxName());
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

				// Inserto información de este medio de pago en el documento de pago actual
				MZPagoMedioPago pagoMedioPago = new MZPagoMedioPago(getCtx(), 0, get_TrxName());
				pagoMedioPago.setZ_Pago_ID(this.pago.get_ID());

				pagoMedioPago.setZ_MedioPago_ID(medioPagoItem.getZ_MedioPago_ID());
				if (medioPagoItem.getZ_MedioPagoFolio_ID() > 0){
					pagoMedioPago.setZ_MedioPagoFolio_ID(medioPagoItem.getZ_MedioPagoFolio_ID());
				}

				pagoMedioPago.setZ_MedioPagoItem_ID(medioPagoItem.get_ID());
				pagoMedioPago.setTotalAmtMT(medioPagoItem.getTotalAmt());
				pagoMedioPago.setTotalAmt(medioPagoItem.getTotalAmt());

				if (medioPagoItem.getC_BankAccount_ID() > 0){
					pagoMedioPago.setC_BankAccount_ID(medioPagoItem.getC_BankAccount_ID());
				}

				pagoMedioPago.setC_Currency_ID(medioPagoItem.getC_Currency_ID());
				pagoMedioPago.setDateEmitted(medioPagoItem.getDateEmitted());
				pagoMedioPago.setDocumentNoRef(medioPagoItem.getNroMedioPago());
				pagoMedioPago.setDueDate(medioPagoItem.getDueDate());
				pagoMedioPago.setEmisionManual(false);
				pagoMedioPago.setMultiplyRate(Env.ONE);

				MZMedioPago medioPago = (MZMedioPago) medioPagoItem.getZ_MedioPago();

				pagoMedioPago.setTieneCaja(medioPago.isTieneCaja());
				pagoMedioPago.setTieneCtaBco(medioPago.isTieneCtaBco());
				pagoMedioPago.setTieneFecEmi(medioPago.isTieneFecEmi());
				pagoMedioPago.setTieneFecVenc(medioPago.isTieneFecVenc());
				pagoMedioPago.setTieneFolio(medioPago.isTieneFolio());
				pagoMedioPago.setTieneNroRef(medioPago.isTieneNroRef());
				pagoMedioPago.setIsReceipt(medioPagoItem.isReceipt());
				pagoMedioPago.saveEx();

				// Guardo moneda en hash si aún no la tengo
				if (!hashCurrency.containsKey(pagoMedioPago.getC_Currency_ID())){
					hashCurrency.put(pagoMedioPago.getC_Currency_ID(), pagoMedioPago.getC_Currency_ID());
				}
			});

			// Si tengo monedas, actualizo tabla de monedas de este pago/cobro
			if (hashCurrency.size() > 0){

				// Cargo monedas con tasa de cambio a la fecha de este documento
				message = MZPagoMoneda.setMonedas(getCtx(), this.pago.get_ID(), hashCurrency, get_TrxName());
				if (message != null){
					return "@Error@ " + message;
				}

				// Actualizo tasa de cambio y monto en moneda transacción, en lineas y resguardos asociados a este documento.
				this.pago.updateRates(true);
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return "OK";
	}
}