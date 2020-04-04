import { AbstractAuditable } from '../abstract-auditable';

export class Centro extends AbstractAuditable<number>{
    id: number;
    nombre: string;
    latitud: number;
    longitud: number;
    direccion: string;
    zona: string;
    ciudad: string;
    asignado: boolean;
}