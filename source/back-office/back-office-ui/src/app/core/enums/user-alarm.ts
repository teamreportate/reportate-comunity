/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    18-06-19
 * author:  fmontero
 **/
import {AbstractAuditable} from '../models/abstract-auditable';
import {Alarm} from '../models/alarm';
import {AuthUser} from '../models/AuthUser';

export class UserAlarm extends AbstractAuditable<number> {
  email: string;
  ccList: string;
  idSfeAlarma: Alarm;
  idSfeUsuario: AuthUser;
}
