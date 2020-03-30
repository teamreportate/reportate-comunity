/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-02-19
 * author:  fmontero
 **/
import {Enterprise} from './enterprise';
import {AbstractAuditable} from './abstract-auditable';
import {BranchState} from '../enums/branch-state';

export class BranchOffice extends AbstractAuditable<number> {

  nombre: string | null;

  codigoSucursal: string | null;

  codigoSucursalSin: number | null;

  descripcion: string | null;

  direccion: string | null;

  nombreSequencia: string | null;

  estadoSucursal: BranchState | null;

  idSfeEmpresa: Enterprise | null;

  telefono: number | null;

  departamento: string | null;

  municipioDepartamento: string | null;

  ciudad: string | null;

  zona: string | null;

  constructor() {super(); }

}
