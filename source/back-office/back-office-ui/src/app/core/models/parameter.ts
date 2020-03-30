/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/

import {DataType} from '../enums/data-type';
import {AbstractAuditable} from './abstract-auditable';

export class Parameter extends AbstractAuditable<number> {
  codigo: string ;
  descripcion: string ;
  valorCadena: string | null ;
  valorCadenaLob: string | null ;
  valorBooleano: boolean | null ;
  valorNumerico: number | null ;
  valorFecha: any | null ;
  tipoDato: DataType;
  datosSensibles: boolean ;
  value: any;
  idSfeGrupoParametro: any;

  grupoCodigo: string;
  grupoDescripcion: string;
  constructor() {super(); }
}
