/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-02-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {InvoiceDocument} from './invoice-document';
import {ParametricSin} from './parametric-sin';

export class InvoiceDocumentDetail extends AbstractAuditable<number> {

  actividadEconomica: string | null;

  cantidad: number | null;

  descripcion: string | null;

  montoDescuentoDetalle: number | null;

  numeroSerie: string | null;

  precioUnitario: number | null;

  subTotal: number | null;

  codigoProducto: number | null;

  codigoProductoSfe: string | null;

  idSfeDocumentoFiscal: InvoiceDocument | null;

  unidadMedida: ParametricSin | null;

  trsCodigoProducto: any | null;
  trsUnidadMedida: any | null;
  codigoUnidadMedida: any | null;
  numeroImei: any | null;
  marcaIce: any | null;
  alicuotaEspecifica: any | null;
  alicuotaPorcentual: any | null;
  cantidadLitros: any | null;
  codigoActividadEconomica: any | null;
  porcentajeIehd: any | null;
  codigoNandina: any | null;
  codigoDetalleTransaccion: any | null;
  trsSfeDetalleDocumentoFiscalExtra: any | null;

  cantidadDevuelta: number;
  checked: boolean;

  constructor() {super(); }

  /**
   * :codigoProducto>83144</soap:codigoProducto>
   <soap:descripcion>curitas en caja</soap:descripcion>
   <soap:cantidad>1</soap:cantidad>                 INTRODUCE MANUAL(cantidad de elementos a devolver)
   <soap:precioUnitario>5</soap:precioUnitario>
   <soap:subtotal>5</soap:subtotal>
   <soap:codigoDetalleTransaccion>1</soap:codigoDetalleTransaccion>         INTRODUCE MANUAL
   <soap:codi
   */

}
