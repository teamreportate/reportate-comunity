/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    27-05-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {HistoryDetailComponent} from '../history-detail.component';
import {MatDialog} from '@angular/material';
import {CommonService} from '../../common.service';
import {ClicComponent} from '../../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Page} from '../../../core/utils/paginator/page';
import {NotifierService} from 'angular-notifier';
import {BlockUI, NgBlockUI} from 'ng-block-ui';

@Component({
  selector: 'app-job-history',
  templateUrl: './job-history.component.html',
  styleUrls: ['./job-history.component.scss']
})
export class JobHistoryComponent extends ClicComponent implements OnInit{
  @BlockUI() blockUI: NgBlockUI;
  @Input() public jobHistory: any[];
  @Input() public type: any;
  @Input() public isNotPresent: boolean;
  @Input() public scrollH: boolean;
  constructor(private service: CommonService, private matDialog: MatDialog, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
  }

  viewErrors(history: any) {
    if (!this.type) {
      this.openDialog(history, null);
      return;
    }
    this.blockUI.start('Recuperando detalles del proceso...');
    this.service.requestXmlHistory(history.id, this.type).subscribe(response => {
      const request = response.body;
      this.blockUI.stop();
      this.openDialog(history, request);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  openDialog = (history, request) => this.matDialog.open(HistoryDetailComponent, this.dialogConfig({history, request}));

  classNameJobState(state: string) {
    switch (state) {
      case 'FINALIZADO':
        return 'finalized-job';
      case 'REPROGRAMADO':
        return 'warn-state';
      case 'WARNING':
        return 'warn-state';
      case 'ERROR':
        return 'error-job';
      case 'EJECUTANDO':
        return 'gray-state';
      default:
        return 'primary-state';
    }
  }

  enableAction(state: string) {
    return state !== 'FINALIZADO' && state !== 'EJECUTANDO';
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {}

  onLgScreen() {}

  onMdScreen() {}

  onSmScreen() {}

  onXsScreen() {}

  setPage(pageInfo: Page) {}

}
