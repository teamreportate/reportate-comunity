/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-05-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {BranchOffice} from './branch-office';
import {PosType} from '../enums/pos-type';
import {PosState} from '../enums/pos-state';

export class PointSale extends AbstractAuditable<number> {
  tipo: PosType | null;
  codigoPos: string | null;
  codigoPosSin: number | null;
  nombre: string | null;
  descripcion: string | null;
  nombreSequencia: string | null;
  estadoPos: PosState;
  codigoSistemaProveedor: string | null;
  sfeSucursal: BranchOffice | null;
}
