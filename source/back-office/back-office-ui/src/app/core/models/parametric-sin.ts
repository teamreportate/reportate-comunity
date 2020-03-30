/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-02-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {ParametricType} from '../enums/parametric-type';
import {BranchOffice} from './branch-office';

export class ParametricSin extends AbstractAuditable<number> {

  constructor () {super(); }

  codigoSfe: string | null;

  codigoClasificadorSin: number | null;

  descripcionSin: string | null;

  tipoParametrica: ParametricType | null;

  idSfeSucursal: BranchOffice | null;

  enUso: boolean | null;

  principal: boolean | null;

  wasEdited: boolean;
  wasAdded: boolean;

  //for wizard sucursal config
  isSelected: boolean | false;
}
