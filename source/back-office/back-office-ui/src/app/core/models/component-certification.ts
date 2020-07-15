/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    16-09-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {SystemCertification} from './system-certification';

export class ComponentCertification extends AbstractAuditable<number> {
  codigoSin: number;
  descripcionSin: string;
  ifSfeCertificacionSistema: SystemCertification;
}
