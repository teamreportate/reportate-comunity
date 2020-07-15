/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    07-04-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constants} from '../../constants';
import {Constantes} from '../../../../environments/environment';
import {AuthResource} from '../../models/auth-resource';

@Injectable()
export class ResourceService {
  constructor(private httpClient: HttpClient) {}

  requestResourceList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.resourceApi;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateResource(resource: AuthResource): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.resourceApi + `/${resource.id}`;
    return this.httpClient.put(url, JSON.stringify(resource), {observe: 'response'});
  }
}
