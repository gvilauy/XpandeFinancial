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

import java.math.BigDecimal;
import java.util.List;

/** Generated Process for (Z_SeleccionDocumentosResguardo)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionResguardoDocs extends SeleccionResguardoDocsAbstract
{

	MZResguardoSocio resguardoSocio = null;

	@Override
	protected void prepare()
	{
		this.resguardoSocio = new MZResguardoSocio(getCtx(), this.getRecord_ID(), get_TrxName());
		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{

		try{

			List<Integer> recordIds =  getSelectionKeys();

			for (Integer key: recordIds){

				MInvoice invoice = new MInvoice(getCtx(), key.intValue(), get_TrxName());
				MDocType doc = (MDocType) invoice.getC_DocTypeTarget();

				String sql = " select round(coalesce(sum(taxamt),0),2) as taxamt from c_invoicetax where c_invoice_id =" + invoice.get_ID();
				BigDecimal taxAmt = DB.getSQLValueBDEx(null, sql);

				MZResguardoSocioDoc resguardoSocioDoc = new MZResguardoSocioDoc(getCtx(), 0, get_TrxName());
				resguardoSocioDoc.setZ_ResguardoSocio_ID(this.resguardoSocio.get_ID());

				// Comprobante
				String nroComprobante = invoice.get_ValueAsString("DocumentSerie") + invoice.getDocumentNo();
				resguardoSocioDoc.setC_Invoice_ID(invoice.get_ID());
				resguardoSocioDoc.setC_DocType_ID(doc.get_ID());
				resguardoSocioDoc.setDateDoc(invoice.getDateInvoiced());
				resguardoSocioDoc.setDocumentNoRef(nroComprobante);
				resguardoSocioDoc.setDocBaseType(doc.getDocBaseType());
				resguardoSocioDoc.setIsPaid(invoice.isPaid());

				// Tasa de cambio del comprobante cuando moneda es distinta a moneda de la emisión del resguardo
				// Se considera fecha del comprobante para obtener dicha tasa
				resguardoSocioDoc.setCurrencyRate(Env.ONE);
				BigDecimal multiplyRate = Env.ONE;
				if (invoice.getC_Currency_ID() != this.resguardoSocio.getC_Currency_ID()){

					BigDecimal currencyRate = CurrencyUtils.getCurrencyRateToAcctSchemaCurrency(getCtx(), invoice.getAD_Client_ID(), 0,
							invoice.getC_Currency_ID(), this.resguardoSocio.getC_Currency_ID(), 0, invoice.getDateInvoiced(), null);
					if ((currencyRate == null) || (currencyRate.compareTo(Env.ZERO) == 0)){
						throw new AdempiereException("No se pudo obtener tasa de cambio para Comprobante : " +  nroComprobante);
					}
					resguardoSocioDoc.setCurrencyRate(currencyRate);

					// Tasa de cambio por la cual multiplicar para convertir montos de comprobante en moneda de emisión de resguardo
					multiplyRate = MConversionRate.getRate(invoice.getC_Currency_ID(), this.resguardoSocio.getC_Currency_ID(),
							invoice.getDateInvoiced(), 0, invoice.getAD_Client_ID(), 0);
				}

				// Montos convertidos a moneda de la emisión del resguardo, según tasa de cambio a fecha del comprobante
				resguardoSocioDoc.setC_Currency_ID(invoice.getC_Currency_ID());

				BigDecimal amtSubtotal = (BigDecimal) invoice.get_Value("AmtSubtotal");
				if (amtSubtotal == null){
					amtSubtotal = invoice.getTotalLines();
				}
				resguardoSocioDoc.setAmtSubtotal(amtSubtotal.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));

				resguardoSocioDoc.setTaxAmt(taxAmt.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));

				BigDecimal amtRounding = (BigDecimal) invoice.get_Value("AmtRounding");
				if (amtRounding == null) amtRounding = Env.ZERO;
				resguardoSocioDoc.setAmtRounding(amtRounding.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));
				resguardoSocioDoc.setAmtTotal(invoice.getGrandTotal().multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));

				// Para documentos del tipo base APC (notas de crédito, etc.), se debe dar vuelta el signo de los importes
				if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
					resguardoSocioDoc.setAmtSubtotal(resguardoSocioDoc.getAmtSubtotal().negate());
					resguardoSocioDoc.setTaxAmt(resguardoSocioDoc.getTaxAmt().negate());
					resguardoSocioDoc.setAmtRounding(resguardoSocioDoc.getAmtRounding().negate());
					resguardoSocioDoc.setAmtTotal(resguardoSocioDoc.getAmtTotal().negate());
				}

				// Monto de retención
				resguardoSocioDoc.setAmtRetencion(Env.ZERO);

				resguardoSocioDoc.saveEx();

				// Detalle importes por impuesto para este comprobante (Esto es para el calculo de retenciones que aplican sobre impuestos)
				MInvoiceTax[] invoiceTaxes = invoice.getTaxes(true);
				for (int i = 0; i < invoiceTaxes.length; i++){
					MInvoiceTax invoiceTax = invoiceTaxes[i];
					MZResguardoSocioDocTax resguardoSocioDocTax = new MZResguardoSocioDocTax(getCtx(), 0, get_TrxName());
					resguardoSocioDocTax.setZ_ResguardoSocio_ID(this.resguardoSocio.get_ID());
					resguardoSocioDocTax.setZ_ResguardoSocioDoc_ID(resguardoSocioDoc.get_ID());
					resguardoSocioDocTax.setC_Invoice_ID(invoice.get_ID());
					resguardoSocioDocTax.setC_Tax_ID(invoiceTax.getC_Tax_ID());

					MTaxCategory taxCategory = (MTaxCategory)((MTax) invoiceTax.getC_Tax()).getC_TaxCategory();
					resguardoSocioDocTax.setC_TaxCategory_ID(taxCategory.get_ID());
					resguardoSocioDocTax.setTaxAmt(invoiceTax.getTaxAmt().multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP));

					if (doc.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
						resguardoSocioDocTax.setTaxAmt(resguardoSocioDocTax.getTaxAmt().negate());
					}
					resguardoSocioDocTax.saveEx();
				}

			}

			this.resguardoSocio.calcularRetenciones();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return "OK";
	}
}