/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-05-19
 * author:  fmontero
 **/

export enum PosState {
  ACTIVO = 'ACTIVO',
  INACTIVO = 'INACTIVO',
  ELIMINADO = 'ELIMINADO',
  PENDIENTE = 'PENDIENTE'
}

export const postStateFromKey = (key: string): PosState => {
  switch (key) {
    case 'ACTIVO':
      return PosState.ACTIVO;
    case 'BLOQUEADO':
      return PosState.INACTIVO;
    case 'ELIMINADO':
      return PosState.ELIMINADO;
    case 'PENDIENTE':
      return PosState.PENDIENTE;
    default:
      return null;
  }
};
