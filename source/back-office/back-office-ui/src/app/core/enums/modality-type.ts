/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-02-19
 * author:  Leonardo Bozo
 * **/


export enum ModalityType {
  ELECTRONICA = 'ELECTRONICA',
  COMPUTARIZADA = 'COMPUTARIZADA'
}


export const modalityFromString = (name: string) => {
  if (name === 'ELECTRONICA') return ModalityType.ELECTRONICA;

  if (name === 'COMPUTARIZADA') return ModalityType.COMPUTARIZADA;

  return undefined;
};

export const modalityFromNumber = (value: number) => {
  if (value === 1) return ModalityType.ELECTRONICA;

  if (value === 2) return ModalityType.COMPUTARIZADA;

  return undefined;
};
