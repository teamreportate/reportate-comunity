/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Constantes} from '../../../../environments/environment';
import {Observable} from 'rxjs';
import {DomainValue} from '../../models/domain-value';
import { Constants } from '../../constants';

@Injectable()
export class DomainService {
  private endPoint: string;

  constructor(private httpClient: HttpClient) {}

  requestDomainValueList(searchCriteria: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.domainApi + `?criterioBusqueda=${searchCriteria}`;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestDomainValueUpdate(domain: DomainValue): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri.concat('valorDominio/').concat(String(domain.id));
    return this.httpClient.put<HttpResponse<any>>(this.endPoint, JSON.stringify(domain),{observe: 'response'});
  }

  requestDomainList(): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri.concat('dominios/');
    return this.httpClient.get<HttpResponse<any>>(this.endPoint, {observe: 'response'});
  }

}
