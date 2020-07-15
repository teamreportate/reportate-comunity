/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    20-05-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {Timestamp} from 'rxjs';
import {Enterprise} from './enterprise';
import {ManualSyncState} from '../enums/manual-sync-state';

export class ManualSyncronization extends AbstractAuditable<number>{
  codigoAutorizacion: number | null;
  fechaInicio: Timestamp<Date> | null;
  fechaFin: Timestamp<Date> | null;
  mensage: string | null;
  stateSincronizacionManual: ManualSyncState | null;
  idSfeEmpresa: Enterprise | null;

  // extra parameter only for frontend
  startNow: boolean | null;
  constructor() {super();}
}
