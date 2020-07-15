/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    18-06-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {AlarmType} from '../enums/alarm-type';

export class Alarm extends AbstractAuditable<number> {
  nombre: string | null;
  descripcion: string;
  tipoAlarma: AlarmType;
  html: string;
  asunto: string;
  contenido: string;
  atributos: string;
  atributosSeleccionados: string;

  constructor() {
    super();
    this.nombre
      = this.descripcion
      = this.tipoAlarma
      = this.html
      = this.asunto
      = this.contenido
      = this.atributos
      = this.atributosSeleccionados
      = null;
  }
}
