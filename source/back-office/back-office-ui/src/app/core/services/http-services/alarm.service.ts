/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    24-06-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Alarm} from '../../models/alarm';
import {Constants} from '../../constants';
import {Constantes} from '../../../../environments/environment';
import {UserAlarm} from '../../enums/user-alarm';

@Injectable()
export class AlarmService {
  constructor(private httpClient: HttpClient) {}


  requestAlarmList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.alarmApi;
    return this.httpClient.get(url, {observe: 'response'});
  }

  requestUsersForAlarm(alarmaId: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.alarmApi + `/usuarios/${alarmaId}`;
    return this.httpClient.get(url, {observe: 'response'});
  }

  requestUpdateAlarm(alarmaId: string, alarm: Alarm): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.alarmApi + `/actualizar/${alarmaId}`;
    return this.httpClient.put(url, JSON.stringify(alarm), {observe: 'response'});
  }

  requestStoreUserAlarms(alarmId: string, userAlarmList: UserAlarm[]) {
    const url = Constantes.baseUri + Constants.alarmApi + `/asignacion/${alarmId}`;
    return this.httpClient.post(url, JSON.stringify(userAlarmList), {observe: 'response'});
  }
}
