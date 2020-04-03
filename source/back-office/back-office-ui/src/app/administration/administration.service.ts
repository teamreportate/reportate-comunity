import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpResponse, HttpClient } from '@angular/common/http';
import { Constantes } from 'src/environments/environment';
import { Constants } from '../core/constants';

@Injectable()
export class AdministrationService {

  constructor(private http: HttpClient) { }

  requestPaisList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.paisApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdatePais(id: number, object: any): Observable<any> {
    return this.http.put<any>( Constantes.baseUri + Constants.paisApi + '/' + id, JSON.stringify(object));
  }

  requestSavePais(object: any): Observable<any> {
    return this.http.post<any>( Constantes.baseUri + Constants.paisApi, JSON.stringify(object));
  }
  requestChangePaisStatus(id: number): Observable<any> {
    return this.http.delete<any>( Constantes.baseUri + Constants.paisApi + '/' + id + '/cambiar-estado');
  }

  requestSintomaList(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.sintomasApi;
    return this.http.get<HttpResponse<any>>(url, {observe: 'response'});
  }

  requestUpdateSintoma(id: number, object: any): Observable<any> {
    return this.http.put<any>( Constantes.baseUri + Constants.sintomasApi + '/' + id, JSON.stringify(object));
  }

  requestSaveSintoma(object: any): Observable<any> {
    return this.http.post<any>( Constantes.baseUri + Constants.sintomasApi, JSON.stringify(object));
  }
  requestChangeSintomaStatus(id: number): Observable<any> {
    return this.http.delete<any>( Constantes.baseUri + Constants.sintomasApi + '/' + id + '/cambiar-estado');
  }
}
