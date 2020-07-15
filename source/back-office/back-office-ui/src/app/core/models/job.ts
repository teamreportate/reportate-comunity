/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    24-05-19
 * author:  fmontero
 **/

export class Job {
  groupName: string | null;
  jobName: string | null;
  scheduleTime: string | null;
  lastFiredTime: string | null;
  nextFireTime: string | null;
  jobStatus: string | null;
  cronExpression: string | null;

  // parametros convenientes para manejar el componente de job
  idPosSucursal: number | null;
  isPos: boolean | null;
  isCufdJob: boolean | null;
  isSyncJob: boolean | null;
  empresaId: number | null;

  startNowCallback: any;
  restartCallback: any;
  pauseCallback: any;
  editableJobCallback: any;
  isEditable: boolean;
  constructor() {}
}

export const getData = (length: number): Job[] => {
  let array: Job[] = [];
  for (let i = 0; i < length; i++) {
    const job = new Job();
    job.groupName = `GROUP-${i}`;
    job.jobName = `NAME-${i}`;
    job.scheduleTime = '2019-05-24T14:14:19.219+0000';
    job.lastFiredTime = '2019-05-24T14:14:19.219+0000';
    job.nextFireTime = i%2===0?'FINALIZADO': 'REPROGRAMADO';
    job.jobStatus = i%2===0?'ACTIVO': 'INACTIVO';
    array.push(job);
  }
  return array;
};
