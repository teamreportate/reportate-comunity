/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {MuState} from './mu-state';

export class AuthGroup extends AbstractAuditable<number>{
  nombre: string | null;
  descripcion: string | null;
  estadoGrupo: MuState;
  // prop descartable para nuevos objetos
  checked: boolean | null;

  constructor() {super();}
}
