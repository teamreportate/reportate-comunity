import {AbstractAuditable} from './abstract-auditable';

export class Cuis extends AbstractAuditable<number> {

  constructor () {super(); }

  cuis: string | null;

  fechaInicio: Date | null;

  fechaFin: Date | null;

  fechaSolicitud: Date | null;

  codSucursalSin: string | null;

  codSucursal: string | null;

}
