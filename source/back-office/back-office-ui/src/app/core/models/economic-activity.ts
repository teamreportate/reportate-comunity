import {AbstractAuditable} from './abstract-auditable';

export class EconomicActivity extends AbstractAuditable<number> {

  constructor() {super(); }

  grupo: string;

  subGrupo: string;

  codigo: string ;

  descripcion: string ;

}
