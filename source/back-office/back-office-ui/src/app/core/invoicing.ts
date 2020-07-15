/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    30-07-19
 * author:  fmontero
 **/
import {FormControl, FormGroup} from '@angular/forms';

export interface Invoicing {
  formGroupIn?: FormGroup | FormControl;
  lists?: {[key: string]: any[]};
}
