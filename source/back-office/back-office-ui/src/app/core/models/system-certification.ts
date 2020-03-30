/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    16-09-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {Enterprise} from './enterprise';

export class SystemCertification extends AbstractAuditable<number> {
  fechaCertificacion: string;
  descripcion: string;
  idSfeEmpresa: Enterprise;
}
