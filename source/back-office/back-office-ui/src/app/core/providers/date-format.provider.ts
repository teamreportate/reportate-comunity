/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    07-06-19
 * author:  fmontero
 **/
import {Provider} from '@angular/core';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material';
import {CUSTOM_DATE_FORMAT, CustomDateAdapter} from '../utils/date-util/custom-date-adapter';

export const dateFormatProvider: Provider[] = [
  { provide: MAT_DATE_LOCALE, useValue: 'es-BO' },
  { provide: MAT_DATE_FORMATS, useValue: CUSTOM_DATE_FORMAT },
  { provide: DateAdapter, useClass: CustomDateAdapter }
];
