/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-05-19
 * author:  fmontero
 **/

export enum PosType {
  NO_DEFINIDO = 0,
  FIJO = 1,
  MOVIL = 2
}

export const posTypeFromKey = (key: string): PosType => {
  switch (key) {
    case 'NO_DEFINIDO':
      return PosType.NO_DEFINIDO;
    case 'FIJO':
      return PosType.NO_DEFINIDO;
    case 'MOVIL':
      return PosType.NO_DEFINIDO;
    default:
      return null;
  }
};
