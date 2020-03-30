/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-01-19
 * author:  fmontero
 **/
import {Resource} from './resource';

export interface Authentication {
  username: string;
  password: string;
  token: string | null;
  listaRecursos: Resource[] | null;
}
