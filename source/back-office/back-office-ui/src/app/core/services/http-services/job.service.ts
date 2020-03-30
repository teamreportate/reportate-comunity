/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    27-05-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constants} from '../../constants';
import {Constantes} from '../../../../environments/environment';

@Injectable()
export class JobService {

  constructor(private httpClient: HttpClient) {}

  requestCufdJob(id: string, isPos: boolean): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `cufdJob/${id}/${isPos}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestCuisJob(id: string, isPos: boolean): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `cuisJob/${id}/${isPos}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestSyncJob(nit: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `syncJob/${nit}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestJobHistory(jobName: string, groupName: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `job/historial/${jobName}/${groupName}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestExecuteCufJobNow(executeDto: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `startNowCufdJob`;
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(executeDto), {observe: 'response'});
  }

  requestExecuteSyncJobNow(empresaId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `syncJob/${empresaId}/ejecutar-ahora`;
    return this.httpClient.post<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestExecuteCuisJobNow(sucursalId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `startNowCuisJob/${sucursalId}`;
    return this.httpClient.post<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestvalidateCron(expresion: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.plannerApi + `/validar-expresion-cron?cronExpression=${expresion}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateCronMassive(empresaId: number, expresion: string) {
    const url = Constantes.baseUriFact + Constants.plannerApi + `/${empresaId}/reprogramar`;
    return this.httpClient.post<HttpResponse<any>>(url, expresion,{observe: 'response'});
  }

  requestvalidateCronOffline(expresion: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.plannerOfflineApi + `/validar-expresion-cron?cronExpression=${expresion}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateCronMassiveOffline(empresaId: number, expresion: string) {
    const url = Constantes.baseUriFact + Constants.plannerOfflineApi + `/${empresaId}/reprogramar`;
    return this.httpClient.post<HttpResponse<any>>(url, expresion,{observe: 'response'});
  }
  //JOBS INTEGRACION EXTERNA
  requestAllJobs(empresaId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.externalIntegration + `/${empresaId}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }
  requestJobInfo(groupName:string, jobName:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.externalIntegration + `/info?groupName=${groupName}&jobName=${jobName}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }
  //CONTROL DE JOBS Y BOTONES
  requestStartNow(empresaId: string, groupName: string, jobName: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.externalIntegration + `/${empresaId}/ejecutar-ahora?groupName=${groupName}&jobName=${jobName}`;
    return this.httpClient.post<HttpResponse<any>>(url, null, {observe: 'response'});
  }
  requestStopNow(groupName:string, jobName:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.externalIntegration + `/pausar?groupName=${groupName}&jobName=${jobName}`;
    return this.httpClient.post<HttpResponse<any>>(url, null, {observe: 'response'});
  }
  requestRestartNow(groupName:string, jobName:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `/reiniciar?groupName=${groupName}&jobName=${jobName}`;
    return this.httpClient.post<HttpResponse<any>>(url, null, {observe: 'response'});
  }
  requestValidateExpresionCron(cronExpression:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `/validar-expresion-cron?cronExpression=${cronExpression}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }
  requestReschedule(groupName:string, jobName:string, cronExpresion:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `/reprogramar?groupName=${groupName}&jobName=${jobName}`;
    return this.httpClient.post<HttpResponse<any>>(url, cronExpresion, {observe: 'response'});
  }
  requestHistorialJob(groupName:string, jobName:string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `job/historial/${jobName}/${groupName}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }


}
