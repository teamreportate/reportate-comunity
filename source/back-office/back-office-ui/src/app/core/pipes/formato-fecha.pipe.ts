import {Pipe, PipeTransform} from '@angular/core';
import * as moment from 'moment';


@Pipe({
  name: 'formatoFecha'
})
export class FormatoFechaPipe implements PipeTransform {
  transform(pFecha: any[], pFormato: any): any {
    const vTempFecha = moment(moment.utc(moment(pFecha))).local().format(pFormato);
    return vTempFecha;
  }
}
