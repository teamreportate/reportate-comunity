import {AbstractAuditable} from './abstract-auditable';
import {ModalityType} from '../enums/modality-type';
import {StateType} from '../enums/state-type';
import {SystemType} from '../enums/system-type';

export class Enterprise extends AbstractAuditable<number> {

  constructor() {super(); }

  codigoSistema: string | null ;

  contribuyente: string | null ;

  email: string | null ;

  empresa: string | null ;

  modalidad: ModalityType | null ;

  nit: number | null ;

  representanteLegal: string | null ;

  telefonoContacto: string | null ;

  estadoEmpresa: StateType | null ;

  generarNumeroFactura: boolean | null;

  tipoSistema: SystemType | null;

}
