/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-04-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constantes} from '../../../../environments/environment';
import {AuthPermission} from '../../models/auth-permission';
import {AuthRole} from '../../models/auth-role';
import {Constants} from '../../constants';

@Injectable()
export class RoleService{
  constructor(private httpClient: HttpClient) {}

  requestRoleList(): Observable<HttpResponse<any>> {
    let url: string = Constantes.baseUri + Constants.roleApi;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestChangeRoleStatus(roleId: string): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.roleApi + `/${roleId}/cambiarEstado`;
    return this.httpClient.put<HttpResponse<any>>(url, null, {observe: 'response'});
  }

  requestPermissions(roleId: string): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.roleApi + `/${roleId}/permisos`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'})
  }

  requestUpdatePermissions(roleId: string, currentPerms: AuthPermission[]): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.roleApi + `/${roleId}/actualizarPermisos`;
    return this.httpClient.put<HttpResponse<any>>(url, JSON.stringify(currentPerms), {observe: 'response'});
  }

  requestRoleCreate(role: AuthRole): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.roleApi;
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(role), {observe: 'response'});
  }

  requestRoleUpdate(roleId: string, role: AuthRole): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.roleApi + `/${roleId}`;
    return this.httpClient.put<HttpResponse<any>>(url, JSON.stringify(role), {observe: 'response'});
  }
}
