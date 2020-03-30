import {AbstractAuditable} from './abstract-auditable';
import {InvoiceType} from '../enums/invoice-type';
import {Enterprise} from './enterprise';
import {Image} from './image';

export class Jasper extends AbstractAuditable<number> {

  constructor() {super(); }

  nombreJasper: string | null ;

  archivoJasperJrxml: Blob | null ;

  estadoJasper: string | null ;

  tipoDocumentoSector: InvoiceType | null ;

  sfeEmpresa: Enterprise | null ;

  sfeImagenLogo: Image | null ;
  sfeImagenMarcaAgua: Image | null ;

}
