/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-02-19
 * author:  fmontero
 **/

export abstract class AbstractPersistable<ID> {

  id: ID | null;

  version: number | null;

  protected constructor() {
    this.id = this.version = null;
  }

}
