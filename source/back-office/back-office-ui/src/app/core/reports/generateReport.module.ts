import { Injectable } from '@angular/core';
// import * as moment from 'moment';
// import 'moment-timezone';

@Injectable()
export class ReporteModule {

  constructor() { }

  impresionPdf(pNombreArchivo, pBase64) {


    const vUrlCompleta = 'data:application/pdf;base64,' + pBase64;
    const link = document.createElement('a');

    if (typeof link.download === 'string') {
      link.href = vUrlCompleta;
      link.download = pNombreArchivo;
     // + '_' + moment(moment.utc()).local().format('YYYYMMDDHHmmss') + '.pdf';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      window.open(vUrlCompleta);
    }
  }

  impresionExcel(pNombreArchivo, pBase64) {

    const vUrlCompleta = 'data:application/vnd.ms-excel;base64,' + pBase64;
    const link = document.createElement('a');

    if (typeof link.download === 'string') {
      link.href = vUrlCompleta;
      link.download = pNombreArchivo;
     // + '_' + moment(moment.utc()).local().format('YYYYMMDDHHmmss') + '.xls';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      window.open(vUrlCompleta);
    }
  }

  impresionDocumento(pNombreArchivo, pBase64, pMimeType) {

    const vUrlCompleta = 'data:' + pMimeType + ';base64,' + pBase64;
    const link = document.createElement('a');

    if (typeof link.download === 'string') {
      link.href = vUrlCompleta;
      link.download = pNombreArchivo;
     // + '_' + moment(moment.utc()).local().format('YYYYMMDDHHmmss') + '.xls';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    } else {
      window.open(vUrlCompleta);
    }
  }


}


