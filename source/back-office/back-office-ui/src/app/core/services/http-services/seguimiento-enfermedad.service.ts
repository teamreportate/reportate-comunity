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

  filterSeguimientoEnfermedad(enfermedadId: string, clasificacion: string, departamentoId: string, initial: string, final: string, page: number, size: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri +  `/listar-filtro/${page}/${size}?from=${initial}&to=${final}&departamentoId=${enfermedadId}&clasificacion=${clasificacion}&enfermedadId=${departamentoId}`;
    return this.httpClient.get(url, {observe: 'response'});
  }

}
