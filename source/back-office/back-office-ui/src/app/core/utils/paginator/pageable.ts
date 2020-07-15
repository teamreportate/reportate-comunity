/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    10-07-19
 * author:  fmontero
 **/
import {Sort} from './sort';


export class Pageable {
  sort: Sort;
  pageSize: number;
  pageNumber: number;
  offset: number;
  paged: boolean;
  unpaged: boolean;
  constructor() {}
}
