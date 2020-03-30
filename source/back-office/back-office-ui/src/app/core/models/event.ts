import {PointSale} from './point-sale';
import {BranchOffice} from './branch-office';
import {Enterprise} from './enterprise';
import {AbstractAuditable} from './abstract-auditable';
import {StateEvent} from '../enums/state-event';

export class SignificantEvent extends AbstractAuditable<number>{
    evento: string;
    codigoEvento: number;
    descripcion: string;
    fechaInicio: string;
    fechaFin: string;
    codigoRecepcionSin: number;
    stateEvento: StateEvent;
    tipoEvento: any;
    idSfeEmpresa: Enterprise;
    idSfeSucursal: BranchOffice;
    SfePuntoVenta: PointSale;

    constructor(){
        super();
    }
}
