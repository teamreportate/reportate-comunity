/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    06-02-19
 * author:  fmontero
 **/
import {AbstractAuditable} from './abstract-auditable';
import {BranchOffice} from './branch-office';


export class Product extends AbstractAuditable<number> {

  codigoProductoSfe: string | null;

  codigoProductoSin: number | null;

  codigoNandinaSin: string | null;

  codigoActividadSin: number | null;

  descripcionProductoSin: string | null;

  idSfeSucursal: BranchOffice | null;

  codigosSfe: any[];

  constructor () { super(); }

  static fromJson(json: any): Product {
    const product: Product = new Product();
    product.id = json['id'];
    product.version = json['version'];
    product.createdDate = json['createdDate'];
    product.lastModifiedDate = json['lastModifiedDate'];
    product.createdBy = json['createdBy'];
    product.lastModifiedBy = json['lastModifiedBy'];
    product.estado = json['estado'];
    product.codigoProductoSfe = json['codigoProductoSfe'];
    product.codigoProductoSin = json['codigoProductoSin'];
    product.codigoNandinaSin = json['codigoNandinaSin'];
    product.codigoActividadSin = json['codigoActividadSin'];
    product.descripcionProductoSin = json['descripcionProductoSin'];
    product.idSfeSucursal = json['idSfeSucursal'];
    return product;
  }
}
