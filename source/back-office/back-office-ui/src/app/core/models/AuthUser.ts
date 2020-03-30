/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    10-04-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {MuState} from './mu-state';

export class AuthUser extends AbstractAuditable<number>{
  nombre: string;
  username: string;
  email: string;
  password: string;
  authType: any;
  tokenExpired: string;
  token: string;
  grupo: any;
  estadoUsuario: MuState
}
