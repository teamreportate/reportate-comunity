/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    13-05-19
 * author:  fmontero
 **/
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Constants } from '../../constants';
import { Constantes } from '../../../../environments/environment';
import { filter } from 'rxjs-compat/operator/filter';

@Injectable()
export class DashboardService {
  constructor(private httpClient: HttpClient) { }

  requestHealthSystem(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.healthSystemApi;
    return this.httpClient.get(url, { observe: 'response' });
  }

  requestChart(date: any = null, interval: number = null): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.fiscalDocumentApi + `/estadistica-emision?fecha=${date}&intervalo=${interval}`;
    return this.httpClient.get(url, { observe: 'response' });
  }

  byValorationRequest(from: string, to: string): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.diagnosticoApi + '/listar-por-valoracion?from=' + from + '&to=' + to;
    return this.httpClient.get<HttpResponse<any>>(url, { observe: 'response' });

  }

  reportWithFiltersRequest(from: string, to: string, filterParam: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.diagnosticoApi +
    '/listar-por-estado-diagnostico?from=' + from + '&to=' + to
    + '&departamentoId=' + filterParam.departamentoId
    + '&municipioId=' + filterParam.municipioId
    + '&centroSaludId=' + filterParam.centroSaludId
    + '&enfermedadId=' + filterParam.enfermedadId;
    return this.httpClient.get<HttpResponse<any>>(url, { observe: 'response' });

  }

  reportResumenWithFiltersRequest(from: string, to: string, filterParam: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.diagnosticoApi +
      '/listar-total-por-estado-diagnostico?from=' + from + '&to=' + to
      + '&departamentoId=' + filterParam.departamentoId
      + '&municipioId=' + filterParam.municipioId
      + '&centroSaludId=' + filterParam.centroSaludId
      + '&enfermedadId=' + filterParam.enfermedadId;
    return this.httpClient.get<HttpResponse<any>>(url, { observe: 'response' });

  }

  reportTotals(from: string, to: string, filterParam: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.diagnosticoApi +
    '/listar-total-lugar?from=' + from + '&to=' + to
    + '&departamentoId=' + filterParam.departamentoId
    + '&municipioId=' + filterParam.municipioId
    + '&centroSaludId=' + filterParam.centroSaludId
    + '&enfermedadId=' + filterParam.enfermedadId;
    return this.httpClient.get<HttpResponse<any>>(url, { observe: 'response' });

  }
}
