/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';

export class Departamento extends AbstractAuditable<number> {
  id: number;
  nombre: string;
  asignado: boolean;

  constructor() {
    super();
  }
}
