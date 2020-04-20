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
import {SfeProcess} from '../../enums/sfe-process';
import {Constants} from '../../constants';

@Injectable()
export class BinnacleService {

  constructor(private httpClient: HttpClient) {}

  requestLogs(initial: string, final: string, process: SfeProcess, query: string, page: number, size: number): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.logApi + `/listar/${page}/${size}?proceso=${process}&from=${initial}&to=${final}&q=${query}`;
    return this.httpClient.get(url, {observe: 'response'});
  }

  requestProcesos(): Observable<HttpResponse<any>> {
    const url = Constantes.baseUri + Constants.logApi + `/listar/procesos`;
    return this.httpClient.get(url, {observe: 'response'});
  }

}
