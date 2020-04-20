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
export class PacienteService {
  private endPoint: string;

  constructor(private httpClient: HttpClient) {
  }

  getFichaEpidemiologica(idPaciente: string): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/ficha-epidemiologica';
    return this.httpClient.get<HttpResponse<any>>(this.endPoint, {observe: 'response'});
  }


  createDiagnostico(idPaciente: string, data: any): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/diagnostico-medico';
    return this.httpClient.post<HttpResponse<any>>(this.endPoint, data,{observe: 'response'});
  }

  updatePaciente(data: any) {
    const url = Constantes.baseUri + Constants.pacienteApi;
    return this.httpClient.put(url, JSON.stringify(data), {observe: 'response'});
  }

  updatePacienteId(pacienteId: string, data: any) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + pacienteId;
    return this.httpClient.put(url, JSON.stringify(data), {observe: 'response'});
  }

  updatePaisViajado(controlPaisId: string, data: any) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + controlPaisId + '/editar-pais';
    return this.httpClient.put(url, JSON.stringify(data), {observe: 'response'});
  }

  addPaisViajado(idPaciente: string, data: any) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/agregar-pais';
    return this.httpClient.put(url, JSON.stringify(data), {observe: 'response'});
  }

  eliminarEnfermedadBase(idPaciente: string, enfermedadId: string) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/' + enfermedadId + '/eliminar-enfermedad-base';
    return this.httpClient.put(url, null, {observe: 'response'});
  }

  eliminarPaisViajado(idPaciente: string, paisId: string) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/' + paisId + '/eliminar-pais-visitado';
    return this.httpClient.put(url, null, {observe: 'response'});
  }

  eliminarContacto(idContacto: string) {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idContacto + '/eliminar-contacto';
    return this.httpClient.put(url, {observe: 'response'});
  }

  addContacto(idPaciente: string, contact: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/' + 'agregar-contacto';
    return this.httpClient.post<HttpResponse<any>>(url, JSON.stringify(contact), {observe: 'response'});
  }

  addEnfermedad(idPaciente: string, enfermedad: any): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.pacienteApi + '/' + idPaciente + '/' + enfermedad + '/agregar-enfermedad-base';
    return this.httpClient.put<HttpResponse<any>>(url, null, {observe: 'response'});
  }
}
