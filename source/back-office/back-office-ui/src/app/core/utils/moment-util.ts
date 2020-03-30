/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    18-09-19
 * author:  fmontero
 **/

import * as moment from 'moment';

export class MomentUtil {
  static TIMESTAMP_FORMAT_YYYYMMDDHHmmss: string = 'YYYY-MM-DD HH:mm:ss';

  static momentToDate(data: moment.Moment) {
    return moment.utc(data).toDate();
  }
}
