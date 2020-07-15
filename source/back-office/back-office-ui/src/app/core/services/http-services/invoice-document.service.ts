/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-02-19
 * author:  fmontero
 **/

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

export class Productos {
  id_producto: number;
  producto: string;
  nombre_entidad: string;


  constructor(id_producto: number, producto: string, nombre_entidad: string) {
    this.id_producto = id_producto;
    this.producto = producto;
    this.nombre_entidad = nombre_entidad;
  }
}

@Injectable({
  providedIn: 'root'
})
export class InvoiceDocumentService {

  private endPoint: string;

  constructor(private httpClient: HttpClient) {}

  // requestInvoiceDocumentList(searchCriteria: string, branchId: string): Observable<InvoiceDocument[]> {
  //   this.endPoint = Constants.baseUrl()
  //     .concat('backoffice/documentosFiscales')
  //   return this.httpClient.get<InvoiceDocument[]>(this.endPoint, {observe: 'response'});
  // }


}
