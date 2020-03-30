/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    28-01-19
 * author:  fmontero
 **/
import {Domain} from './domain';
import {AbstractAuditable} from './abstract-auditable';

export class DomainValue extends AbstractAuditable<number> {
  dominio: Domain;
  valor: any;
  descripcion: string;
  constructor () {super(); }
}
