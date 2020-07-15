/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-07-19
 * author:  fmontero
 **/
import {ParametricType} from '../../enums/parametric-type';
import {ParametricSin} from '../parametric-sin';


export interface ConfigurableParametrics {
  tipoParametrica: ParametricType;
  parametricaSinList: ParametricSin[];
  parametricaListaSinList: any[];
}
