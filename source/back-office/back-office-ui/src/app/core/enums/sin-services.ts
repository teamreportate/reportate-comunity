/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    21-05-19
 * author:  fmontero
 **/

import {ModalidadType} from './modalidad-type';

const servicios: any[] = [
  {value: 'FACTURA_ESTANDAR_COMPUTARIZADA', documentoSector: 1, modalidad: ModalidadType.COMPUTARIZADA, test: '/facturaEstandarComputarizada'},
  {value: 'FACTURA_ESTANDAR_ELECTRONICA', documentoSector: 1, modalidad: ModalidadType.ELECTRONICA, test: '/facturaEstandarElectronica'},
  {value: 'FACTURA_EDUCATIVO_COMPUTARIZADA', documentoSector: 2, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_EDUCATIVO_ELECTRONICA', documentoSector: 2, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_ALQUILER_COMPUTARIZADA', documentoSector: 3, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_ALQUILER_ELECTRONICA', documentoSector: 3, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_COMBUSTIBLE_COMPUTARIZADA', documentoSector: 4, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_COMBUSTIBLE_ELECTRONICA', documentoSector: 4, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_SERVICIO_BASICO_COMPUTARIZADA', documentoSector: 5, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_SERVICIO_BASICO_ELECTRONICA', documentoSector: 5, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_ALCANZADA_ICE_COMPUTARIZADA', documentoSector: 6, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_ALCANZADA_ICE_ELECTRONICA', documentoSector: 6, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_FINANCIERA_COMPUTARIZADA', documentoSector: 7, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_FINANCIERA_ELECTRONICA', documentoSector: 7, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_HOTEL_COMPUTARIZADA', documentoSector: 8, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_HOTEL_ELECTRONICA', documentoSector: 8, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_HOSPITAL_COMPUTARIZADA', documentoSector: 9, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_HOSPITAL_ELECTRONICA', documentoSector: 9, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_JUEGO_COMPUTARIZADA', documentoSector: 10, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_JUEGO_ELECTRONICA', documentoSector: 10, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'FACTURA_ESPECTACULO_PUBLICO_INTERNACIONAL', documentoSector: 11, modalidad: null, test: null},
  {value: 'NOTA_EXPORTACION', documentoSector: 12, modalidad: null, test: null},
  {value: 'NOTA_LIBRE_CONSIGNACION', documentoSector: 13, modalidad: null, test: null},
  {value: 'NOTA_ZONA_FRANCA', documentoSector: 14, modalidad: null, test: null},
  {value: 'NOTA_ESPECTACULO_PUBLICO_NACIONAL', documentoSector: 15, modalidad: null, test: null},
  {value: 'NOTA_SEGURIDAD_ALIMENTARIA', documentoSector: 16, modalidad: null, test: null},
  {value: 'NOTA_MONEDA_EXTRANJERA_COMPUTARIZADA', documentoSector: 17, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'NOTA_MONEDA_EXTRANJERA_ELECTRONICA', documentoSector: 17, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'NOTA_CREDITO_DEBITO_COMPUTARIZADA', documentoSector: 18, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'NOTA_CREDITO_DEBITO_ELECTRONICA', documentoSector: 18, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'NOTA_CONCILIACION', documentoSector: 19, modalidad: null, test: null},
  {value: 'BOLETO_AEREO', documentoSector: 20, modalidad: null, test: null},
  {value: 'NOTA_TURISMO_RECEPTIVO', documentoSector: 21, modalidad: null, test: null},
  {value: 'NOTA_TASA_CERO', documentoSector: 22, modalidad: null, test: null},
  {value: 'FACTURA_HIDROCARBURO_COMPUTARIZADA', documentoSector: 23, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_HIDROCARBURO_ELECTRONICA', documentoSector: 23, modalidad: ModalidadType.ELECTRONICA, test: null},

  {value: 'FACTURA_TELECOMUNICACIONES_COMPUTARIZADA', documentoSector: 29, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_TELECOMUNICACIONES_ELECTRONICA', documentoSector: 29, modalidad: ModalidadType.ELECTRONICA, test: null},

  {value: 'FACTURA_COMERCIAL_EXPORTACION_COMPUTARIZADA', documentoSector: 12, modalidad: ModalidadType.COMPUTARIZADA, test: null},
  {value: 'FACTURA_COMERCIAL_EXPORTACION_ELECTRONICA', documentoSector: 12, modalidad: ModalidadType.ELECTRONICA, test: null},
  {value: 'SERVICIO_OPERACIONES', documentoSector: null, modalidad: null, test: '/operaciones'},
  {value: 'SERVICIO_SINCRONIZACION', documentoSector: null, modalidad: null, test: '/sincronizacion'},
  {value: 'SERVICIO_EVENTOS_SIGNIFICATIVOS', documentoSector: null, modalidad: null, test: '/eventosSignificativos'},
  {value: 'INVOICE_VALIDATOR', documentoSector: null, modalidad: null, test: null},
];
