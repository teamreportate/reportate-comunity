/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    27-06-19
 * author:  fmontero
 **/
import {AuthPermission} from '../auth-permission';
import {AuthResource} from '../auth-resource';

export interface MenuDto {
  sub: string | null;
  permisos: AuthPermission[] | null;
  menu: AuthResource[] | null;
  iat: any | null;
  exp: any | null;
}
