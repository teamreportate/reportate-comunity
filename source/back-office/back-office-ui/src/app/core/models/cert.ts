/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    23-05-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';

export class Cert extends AbstractAuditable<number>{
  versionCertificate: string | null;
  issuer: string | null;
  startDate: string | null;
  endDate: string | null;
  subject: string | null;
  serialNumber: string | null;
  algoritmo: string | null;
  constructor() {super();}
}
