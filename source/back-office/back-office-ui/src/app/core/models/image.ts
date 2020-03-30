import {AbstractAuditable} from './abstract-auditable';
import {Enterprise} from './enterprise';
import {ImageType} from '../enums/image-type';

export class Image extends AbstractAuditable<number> {

  constructor() {super(); }

  nombreImagen: string | null ;

  tipoImagen: ImageType | null ;

  imagenBytes: Blob | null ;

  estadoImagen: string | null ;

  sfeEmpresa: Enterprise | null ;

}
