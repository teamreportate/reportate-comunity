import {Product} from '../product';

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-07-19
 * author:  fmontero
 **/

export class GroupedProducts {
  letter: string;
  products: Product[];
  constructor(letter: string, products: Product[]) {
    this.letter = letter;
    this.products = products;
  }
}
