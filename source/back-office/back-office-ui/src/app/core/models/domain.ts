/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';

export class Domain extends AbstractAuditable<number> {
  codigo: string;
  descripcion: string ;

  constructor() {super(); }
}
