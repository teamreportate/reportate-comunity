/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    26-07-19
 * author:  fmontero
 **/

export enum EmisionType {
  NO_DEFINIDO = 'NO_DEFINIDO',
  ONLINE = 'ONLINE',
  OFFLINE = 'OFFLINE',
  ON_LINE_MASIVO = 'ON_LINE_MASIVO',
  CONTINGENCIA = 'CONTINGENCIA'
}

export function matchEmisionType(tipoEmision: EmisionType) {
  switch (tipoEmision) {
    case EmisionType.NO_DEFINIDO: return 0;
    case EmisionType.ONLINE: return 1;
    case EmisionType.OFFLINE: return 2;
    case EmisionType.ON_LINE_MASIVO: return 3;
    case EmisionType.CONTINGENCIA: return 4;
    default: return null;
  }
}
