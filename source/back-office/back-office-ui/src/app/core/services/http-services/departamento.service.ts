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
import {Constants} from '../../constants';

@Injectable()
export class DepartamentoService {
  private endPoint: string;

  constructor(private httpClient: HttpClient) {}

  getDepartamentosAsignados(): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri + Constants.departmentApi;
    return this.httpClient.get<HttpResponse<any>>(this.endPoint, {observe: 'response'});
  }


}
