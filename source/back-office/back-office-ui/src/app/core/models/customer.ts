/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-02-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {AuthUser} from './AuthUser';

export class Customer extends AbstractAuditable<number> {
  codigoCliente: string | null;
  emailCliente: string | null;
  cellCliente: string | null;
  nit: string | null;
  usuario: AuthUser | null;
}
