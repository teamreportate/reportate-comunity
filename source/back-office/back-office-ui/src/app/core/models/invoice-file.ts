/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: sfe-frontoffice-ui
 * package:
 * date:    14-02-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';

export class InvoiceFile extends AbstractAuditable<number> {

    nombre: string | null;
    longitud: number | null;
    tipo: string | null;
    tipoMime: string | null;
    archivoBase64: string | null;

    constructor() { super(); }
}
