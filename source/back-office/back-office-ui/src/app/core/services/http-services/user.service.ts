/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {Constantes} from '../../../../environments/environment';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from '../../../access/users/utils/user-dto';
import {Constants} from '../../constants';

@Injectable()

export class UserService {

  constructor(private httpClient: HttpClient) {}

  requestUserList(): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.userApi;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestChangeUserState(userId: string): Observable<HttpResponse<any>> {
    let url: string = Constantes.baseUri + Constants.userApi + `/${userId}/cambiarEstado`;
    return this.httpClient.put<HttpResponse<any>>(url, {observe: 'response'});
  }

  request(): Observable<UserDto> {
    return this.httpClient.get<UserDto>('',{});
  }

  requestCreateUser(data: any): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.userApi;
    let headers: HttpHeaders = new HttpHeaders(data.user);
    return this.httpClient.post<Observable<any>>(url, JSON.stringify(data.groups), {observe: 'response', headers: headers});
  }

  requestAllGroups(userId: string):Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.userApi + `/${userId}/listarGrupos`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateUser(user: any): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.userApi + `/${user.id}`;
    return this.httpClient.put<HttpResponse<any>>(url, JSON.stringify(user), {observe: 'response'});
  }

  requestUpdateUserGroups(data: any): Observable<HttpResponse<any>> {
    let url = Constantes.baseUri + Constants.userApi + `/${data.user.id}/asignarGrupos`;
    return this.httpClient.put<HttpResponse<any>>(url, JSON.stringify(data.groups), {observe: 'response'});
  }
}
