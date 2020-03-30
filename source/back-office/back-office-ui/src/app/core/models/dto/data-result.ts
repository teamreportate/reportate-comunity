import {Job} from '../job';
import {JobType} from '../../enums/job-type';

/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    31-05-19
 * author:  fmontero
 **/


export interface DataResult {
  jobType: JobType,
  job: Job
}
