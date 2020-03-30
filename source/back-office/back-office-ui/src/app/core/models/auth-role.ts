/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-04-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {MuState} from './mu-state';

export class AuthRole extends AbstractAuditable<number>{
  nombre: string;
  descripcion: string;
  estadoRol: MuState;
  constructor() { super(); }
}
