/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    07-06-19
 * author:  fmontero
 **/
import {NativeDateAdapter} from '@angular/material';
import * as moment from 'moment';

const KEY: string = 'dateInput';
export const CUSTOM_DATE_FORMAT = {
  parse: {
    dateInput: {month: 'short', year: 'numeric', day: 'numeric'}
  },
  display: {
    dateInput: KEY,
    monthYearLabel: {year: 'numeric', month: 'short'},
    dateA11yLabel: {year: 'numeric', month: 'long', day: 'numeric'},
    monthYearA11yLabel: {year: 'numeric', month: 'long'},
  }
};

export class CustomDateAdapter extends NativeDateAdapter{

  format(date: Date, displayFormat: any): string {
    moment.locale('es-BO');
    if (displayFormat === KEY) return moment(moment.utc(date)).format('DD/MM/YYYY');
    else return moment(moment.utc(date)).format('ll');
  }

}
