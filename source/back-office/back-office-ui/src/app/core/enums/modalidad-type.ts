/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-02-19
 * author:  Leonardo Bozo
 * **/


import {Modalidad} from '../models/modalidad';

export enum ModalidadType {
  NO_DEFINIDO = 0,
  ELECTRONICA = 1,
  COMPUTARIZADA = 2,
  MANUAL = 3
}

export const ModalidadList: Modalidad[] = [
  {name: 'NO DEFINIDO', value: ModalidadType.NO_DEFINIDO},
  {name: 'COMPUTARIZADA', value: ModalidadType.COMPUTARIZADA},
  {name: 'ELECTRONICA', value: ModalidadType.ELECTRONICA},
  {name: 'MANUAL', value: ModalidadType.MANUAL},
];
