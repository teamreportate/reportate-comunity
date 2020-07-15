/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    26-08-19
 * author:  fmontero
 **/

import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constantes} from '../../environments/environment';
import {Constants} from '../core/constants';

@Injectable()
export class SettingsService {

  constructor(private http: HttpClient) {}


  requestUpdatePrincipal(alarmId: number, userId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.alarmApi + `/${alarmId}/principal/${userId}`;
    return this.http.post(url, null, {observe: 'response'});
  }

  requestAlarmUsers(alarmaId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.alarmApi + `/usuarios/${alarmaId}`;
    return this.http.get(url, {observe: 'response'});
  }

  requestDomainList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.domainApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestDomainValueList(domainId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.domainApi + `/${domainId}`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestStoreDomain(domain: any) {
    const url = Constantes.baseUri + Constants.domainApi;
    return this.http.post(url, JSON.stringify(domain), {observe: 'response'});
  }

  requestUpdateDomainValue(id: number, value: any) {
    const url = Constantes.baseUri + Constants.domainApi + `/valor/${id}`;
    return this.http.post(url, JSON.stringify(value), {observe: 'response'});
  }

  requestStoreDomainValue(codigo: string, value: any) {
    const url = Constantes.baseUri + Constants.domainApi + `/${codigo}/agregar-valor`;
    return this.http.post(url, JSON.stringify(value), {observe: 'response'});
  }

  requestUpdateDomain(domainId: number, value: any) {
    const url = Constantes.baseUri + Constants.domainApi + `/${domainId}`;
    return this.http.put(url, JSON.stringify(value), {observe: 'response'});
  }

  requestDeleteDomainValue(id: number) {
    const url = Constantes.baseUri + Constants.domainApi + `/valor/${id}/eliminar`;
    return this.http.put(url, {observe: 'response'});
  }

  requestGroupParameterList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.parameterApi + `/grupos`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestParameterValueList(grupoId: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.parameterApi + `/${grupoId}`;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateParameter(parameterId: number, value: any) {
    const url = Constantes.baseUri + Constants.parameterApi + `/${parameterId}`;
    return this.http.put(url, JSON.stringify(value), {observe: 'response'});
  }
}
