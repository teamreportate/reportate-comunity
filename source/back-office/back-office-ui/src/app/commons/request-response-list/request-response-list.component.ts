/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    13-09-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../core/utils/clic-component';
import {Page} from '../../core/utils/paginator/page';
import {saveAs} from 'file-saver';
import * as moment from 'moment';
import {DOCUMENT} from '@angular/common';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';

@Component({
  selector: 'app-request-response-list',
  templateUrl: './request-response-list.component.html',
  styleUrls: ['./request-response-list.component.scss']
})

export class RequestResponseListComponent extends ClicComponent implements OnInit {
  static REQUEST_RESPONSE_LIST_TO_VIEW = 'REQUEST_RESPONSE_LIST_TO_VIEW';
  static REQUEST_RESPONSE_ENTITY_TO_VIEW = 'REQUEST_RESPONSE_ENTITY_TO_VIEW';
  static REQUEST_RESPONSE_ENTITY_TYPE_TO_VIEW = 'REQUEST_RESPONSE_ENTITY_TYPE_TO_VIEW';

  public entity: any;
  public type: number;
  public requestResponseList: any[];
  constructor(@Inject(DOCUMENT) private document, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.requestResponseList = JSON.parse(localStorage.getItem(RequestResponseListComponent.REQUEST_RESPONSE_LIST_TO_VIEW));
    this.entity = JSON.parse(localStorage.getItem(RequestResponseListComponent.REQUEST_RESPONSE_ENTITY_TO_VIEW));
    this.type = Number(localStorage.getItem(RequestResponseListComponent.REQUEST_RESPONSE_ENTITY_TYPE_TO_VIEW));
  }

  xmlDownload(event: MouseEvent, item: any) {
    event.stopPropagation();
    const fileName = `REQUEST_${this.type === 1 ? 'DOCUMENTO_FISCAL' : 'PAQUETE'}_${item.procesoSfe}_${moment.utc(item.fechaInicio).format('YYYY-MM-DD')}.xml`;
    const request: string = `XML DE PETICIÓN\n\n${item['requestXml']}`;
    const response: string = item.response ? `\n\n\n\nXML DE RESPUESTA\n\n${item.response.responseXml}` : '';
    const content: string = request + response;
    const blob = new Blob([content], {
      type: 'text/plain;charset=utf-8'
    });
    saveAs(blob, fileName);
  }

  copyToClipboard(item): void {
    let listener = (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain;charset=utf-8', (item));
      e.preventDefault();
    };

    this.document.addEventListener('copy', listener);
    this.document.execCommand('copy');
    this.document.removeEventListener('copy', listener);
    const notif = {error: {title: 'Copiar XML', detail: 'XML copiado al portapapeles.'}};
    this.notifierError(notif, 'info');
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }


  public flex: number;

  onXsScreen() {
    this.flex = 100;
  }

  onSmScreen() {
    this.flex = 25;
  }

  onMdScreen() {
    this.flex = 15;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onGtLgScreen() {
    this.flex = 10;
  }

  setPage(pageInfo: Page) {
  }

}
