/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    06-04-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {AuthRole} from './auth-role';
import {AuthResource} from './auth-resource';

export class AuthPermission extends AbstractAuditable<number> {
  idRol: AuthRole;
  idRecurso: AuthResource;
  lectura: boolean;
  escritura: boolean;
  eliminacion: boolean;
  solicitud: boolean;
  autorizacion: boolean;
  constructor() {super();}
}
