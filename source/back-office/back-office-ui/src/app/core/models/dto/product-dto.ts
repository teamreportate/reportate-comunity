/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    30-07-19
 * author:  fmontero
 **/
import {Product} from '../product';

export interface ProductDto {
  productoSin: Product;
  codigosSfe: {codigoProductoSfe: string, codigoProductoSin: number}[];
}
