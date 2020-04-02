import { AbstractAuditable } from '../abstract-auditable';

export class Departamento extends AbstractAuditable<number>{
    id: number;
    nombre: string;
    latitud: number;
    longitud: number;
    municipios: any[];
}