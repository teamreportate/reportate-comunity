/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Constants } from '../../constants';
import {Constantes} from '../../../../environments/environment';
import {AuthGroup} from '../../models/auth-group';
import {AuthRole} from '../../models/auth-role';


@Injectable()
export class GroupService{
  constructor(private httpClient: HttpClient) {}

  requestGroupList(): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestGroupCreate(group: AuthGroup): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi;
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(group),{observe: 'response'});
  }

  requestGroupUpdate(group: AuthGroup): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi + `/${group.id}`;
    return this.httpClient.put<HttpResponse<any>>(url, JSON.stringify(group), {observe: 'response'});
  }

  requestChangeState(groupId: string): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi + `/${groupId}/cambiarEstado`;
    return this.httpClient.put<HttpResponse<any>>(url,null ,{observe: 'response'});
  }

  requestRolesByGroup(groupId: string): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi + `/${groupId}/roles`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateRoles(groupId: string, currentRoles: AuthRole[]): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.groupApi + `/${groupId}/actualizarRoles`;
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(currentRoles), {observe: 'response'});
  }
}
