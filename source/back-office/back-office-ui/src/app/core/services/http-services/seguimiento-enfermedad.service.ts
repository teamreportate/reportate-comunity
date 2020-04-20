/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    14-05-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constantes} from '../../../../environments/environment';
import {Constants} from '../../constants';

@Injectable()
export class SeguimientoEnfermedadService {

  constructor(private httpClient: HttpClient) {
  }

  // tslint:disable-next-line:max-line-length
  filterSeguimientoEnfermedad(nombrePaciente: string, centroSaludId: number, municipioID: number, enfermedadId: number, clasificacion: string, departamentoId: number, initial: string, final: string, page: number, size: number): Observable<HttpResponse<any>> {
    // tslint:disable-next-line:max-line-length
    const url = Constantes.baseUri + Constants.diagnosticoApi + `/listar-filtro/${page}/${size}?from=${initial}&to=${final}&departamentoId=${departamentoId}&municipioID=${municipioID}&centroSaludId=${centroSaludId}&nombrePaciente=${nombrePaciente}&clasificacion=${clasificacion}&enfermedadId=${enfermedadId}`;
    return this.httpClient.get(url, {observe: 'response'});
  }

  getListDepartamentoMunicipioCentros(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.listDiagnostico;
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }
  // tslint:disable-next-line:max-line-length
  filterLocalisacion(centroSaludId: number, municipioId: number, enfermedadId: number, clasificacion: string, departamentoId: number, initial: string, final: string): Observable<HttpResponse<any>> {
    // tslint:disable-next-line:max-line-length
    const url = Constantes.baseUri + Constants.diagnosticoApi + `/listar-mapa?from=${initial}&to=${final}&departamentoId=${departamentoId}&municipioId=${municipioId}&centroSaludId=${centroSaludId}&clasificacion=${clasificacion}&enfermedadId=${enfermedadId}`;
    return this.httpClient.get(url, {observe: 'response'});
  }

  getOcupaciones(): Observable<HttpResponse<any>> {
    // tslint:disable-next-line:max-line-length
    const url = Constantes.baseUri + Constants.ocupaciones + `/dominio-ocupaciones`;
    return this.httpClient.get(url, {observe: 'response'});
  }

  getReporteFichaEpidemiologica(id): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.reporteApi + id + '/ficha-epidemiologica';
    return this.httpClient.get<HttpResponse<any>>(url, {observe: 'response'});
  }
}
