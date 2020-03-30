/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-02-19
 * author:  fmontero
 **/
import {AbstractPersistable} from './abstract-persistable';
import {Timestamp} from 'rxjs';

export abstract class AbstractAuditable<ID> extends AbstractPersistable<ID> {

  createdDate: Timestamp<Date> | null;

  lastModifiedDate: Timestamp<Date> | null;

  createdBy: Timestamp<Date> | null;

  lastModifiedBy: Timestamp<Date> | null;

  estado: boolean | null;

  constructor() {
    super();
    this.createdDate = this.lastModifiedDate = this.createdBy = this.lastModifiedBy = this.estado = null;
  }
}

