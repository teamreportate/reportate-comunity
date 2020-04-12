import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Constantes} from '../../../../environments/environment';
import {Constants} from '../../constants';

@Injectable()
export class DiagnosticoService {
  private endPoint: string;

  constructor(private httpClient: HttpClient) {
  }

  getDiagnostico(idDiagnostico: string): Observable<HttpResponse<any>> {
    this.endPoint = Constantes.baseUri + Constants.diagnosticoApi + '/' + idDiagnostico + '/sintomas';
    return this.httpClient.get<HttpResponse<any>>(this.endPoint, {observe: 'response'});
  }


}
