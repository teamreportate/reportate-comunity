import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constantes} from '../../environments/environment';
import {Authentication} from '../authentication/shared/authentication';
import {Constants} from '../core/constants';

@Injectable()
export class CommonService {
  constructor(private http: HttpClient) {}

  requestXmlHistory(historyId: any, type: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUriFact + Constants.jobApi + `historial/${historyId}/request/${type}`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestLogin(auth: Authentication): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri  + 'auth/signin';
    return this.http.post<HttpResponse<any>>(url, JSON.stringify(auth), {observe: 'response'});
  }
}
