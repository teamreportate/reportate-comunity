import {AbstractAuditable} from './abstract-auditable';

export class Huellas extends AbstractAuditable<number> {

  constructor() {super(); }

  id: number | null ;

  nombre: string | null ;

  rutaArchivo: string | null ;

  crc: string | null ;

  md5: string | null ;

  tamano: number | null;

}

export interface HuellasSave  {
  nombreJar: string,
  md5: string,
  crc: string,
  peso: string,
  ruta: string
}

