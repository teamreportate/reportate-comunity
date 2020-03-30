/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    06-04-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';

export class AuthResource extends AbstractAuditable<number> {
  idRecursoPadre: AuthResource;
  recursoPadre: number;
  nombre: string;
  nombrePadre: string;
  descripcionPadre: string;
  iconoPadre: string;
  url: string;
  icon: string;
  descripcion: string;
  ordenMenu: number;
  ordenPadre: number;
  listaRecursos: AuthResource[];
  componenteFront: string | null;
  constructor() {super();}
}
