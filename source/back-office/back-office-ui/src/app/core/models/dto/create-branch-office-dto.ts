import {ModalityType} from '../../enums/modality-type';

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    30-05-19
 * author:  fmontero
 **/

export class CreateBranchOfficeDto<T> {
  dataObject: T;
  modality: ModalityType;
  certId: number;
  cuis: string;
  cuisDate: any;
  constructor() {
    this.dataObject = this.modality = this.certId = this.cuis = this.cuisDate = null;
  }
}
