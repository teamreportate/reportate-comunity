/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-01-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {AUTH_DATA, Constantes} from '../../../../environments/environment';
import {Authentication} from '../../../authentication/shared/authentication';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {Constants} from '../../constants';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient, private router: Router) {}

  requestStorageLogin(auth: Authentication): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri  + 'auth/signin';
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(auth), {observe: 'response'});
  }

  isLoggedIn() {
   return !!localStorage.getItem(AUTH_DATA);
  }

  changePassword(data: any): Observable<HttpResponse<any>> {
    const user = JSON.parse(localStorage.getItem(AUTH_DATA)).username;
    const url = Constantes.baseUri + 'auth/change-password';
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(data), { observe: 'response'});
  }
}
