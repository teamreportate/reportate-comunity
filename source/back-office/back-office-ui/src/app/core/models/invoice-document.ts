/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:a back-office-ui
 * package:
 * date:a    05-02-19
 * author:a  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {InvoiceDocumentState} from '../enums/invoice-document-state';
import {InvoiceType} from '../enums/invoice-type';
import {ParametricSin} from './parametric-sin';
import {Customer} from './customer';
import {BranchOffice} from './branch-office';
import {InvoiceDocumentExtra} from './invoice-document-extra';
import {InvoiceDocumentDetail} from './invoice-document-detail';
import {PointSale} from './point-sale';

export class InvoiceDocument extends AbstractAuditable<number> {

  constructor () {super(); }

  ciudad: string | null;

  codigoCliente: string | null;

  codigoRecepcionSin: number | null;

  codigoRespuestaSfe: null;

  codigoRespuestaSin: number | null;

  complemento: string | null;

  consumoKws: number | null;

  consumoMetrosCubicos: number | null;

  cuf: string | null;
  caed: string | null;

  cufd: string | null;

  cuis: string | null;

  direccion: string | null;

  estadoDocumentoFiscal: InvoiceDocumentState | null;

  fechaEmision: string | null;

  tipoEmision: string | null;

  fechaEnvio: string | null;

  fechaRespuesta: null;

  gestion: number | null;

  idTransaccion: string | null;

  leyenda: string | null;

  mensajeRespuestaSfe: null;

  mes: string | null;

  montoDescuento: number | null;

  montoDescuentoLey1886: number | null;

  montoTotal: number | null;

  montoTotalOriginal: number | null;

  montoTotalArrendamientoFinanciero: number | null;

  montoTotalMoneda: number | null;

  montoTotalSujetoIva: number | null;

  montoTotalDevuelto: number | null;

  montoEfectivoCreditoDebito: number | null;

  nitEmisor: number | null;

  nombreEstudiante: string | null;

  nombreRazonSocial: string | null;

  numeroDocumento: string | null;

  modalidad: string | null;

  domicilioComprador: string | null;

  consumoKwh: number | null;
  descuentoSinAfectacion: number | null;
  montoIce: any | null;
  nombrePropietario: any | null;
  condicionPago: any | null;
  periodoEntrega: any | null;
  montoIehd: any | null;
  nombreRepresentanteLegal: any | null;
  ingresoDiferenciaCambio: any | null;
  codigoTipoOperacion: any | null;
  tipoCambioOficial: any | null;
  montodescuento: any | null;
  codigoExcepcionDocumento: any | null;

  numeroFactura: number | null;

  numeroMedidor: string | null;

  numeroTarjeta: number | null;

  periodoFacturado: string | null;

  tasaAlumbrado: number | null;

  tasaAseo: number | null;

  tipoCambio: number | null;

  tipoFactura: InvoiceType | null;

  usuario: string | null;

  zona: string | null;

  codigoMoneda: ParametricSin | null;

  idSfeCliente: Customer | null;

  idSfeSucursal: BranchOffice | null;

  idSfePuntoVenta: PointSale | null;

  metodoPago: ParametricSin | null;

  tipoDocumentoFiscal: ParametricSin | null;

  tipoDocumentoIdentidad: ParametricSin | null;

  tipoDocumentoSector: ParametricSin | null;


  sucursalFactura: string | null;

  agenciaFactura: string | null;

  direccionSucursal: string | null;

  motivoAnulacion: string | null;

  procesoBatch: boolean | null;

  codigosErroresSin: string;

  codigoIntegracion: string;

  trsTipoDocumentoSector: string | null;

  trsCodTipoDocumentoIdentidad: string | null;

  trsTipoDocumentoFiscal: string | null;

  trsCodigoSucursal: string | null;

  trsCodPuntoVenta: string | null;

  trsCodigoMetodoPago: string | null;

  trsCodigoMoneda: string | null;

  trsNumeroFactura: string | null;


  trsSfeDocumentoFiscalExtra: InvoiceDocumentExtra | null;

  trsListaSfeDetalleDocumentoFiscal: InvoiceDocumentDetail[] | null;


  id_producto: number;
  producto: string;
  nombre_entidad: string;

}
