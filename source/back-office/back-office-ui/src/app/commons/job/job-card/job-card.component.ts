/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    27-05-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Job} from '../../../core/models/job';
import {JobService} from '../../../core/services/http-services/job.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../dialogs/confirm-dialog.component';
import {DataResult} from '../../../core/models/dto/data-result';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {ClicComponent} from '../../../core/utils/clic-component';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Page} from '../../../core/utils/paginator/page';

@Component({
  selector: 'app-job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.scss'],
  providers: [JobService]
})
export class JobCardComponent extends ClicComponent implements OnInit{
  @BlockUI() blockUI: NgBlockUI;
  @Input() public job: Job;
  @Input() public isNotPresent: boolean;
  @Input() public isActive: boolean;
  @Output() public successEmitter: EventEmitter<DataResult | any> = new EventEmitter<DataResult | any>();
  constructor(private jobService: JobService, private matDialog: MatDialog, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
  }

  onStartJob() {
    const textContent: string = 'Confirmar para poner el marcha el job.';
    const title: string = 'Iniciar Job';
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          try {
            this.job.restartCallback().subscribe(response => {
              this.blockUI.stop();
              this.successEmitter.emit(response.body);
            }, error => {
              this.blockUI.stop();
              if (error) this.notifierError(error);
            });
          } catch (e) {
            this.blockUI.stop();
            const notif = {error: {title: 'Iniciar Job', detail: 'Ocurrió un error inesperado.'}};
            this.notifierError(notif);
          }
        }
    });
  }

  onPauseJob() {
    const textContent: string = 'Confirmar para detener el job.';
    const title: string = 'Pausar Job';
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          try {
            this.job.pauseCallback().subscribe(response => {
              this.blockUI.stop();
              this.successEmitter.emit(response.body);
            }, error => {
              this.blockUI.stop();
              if (error) this.notifierError(error);
            });
          } catch (e) {
            this.blockUI.stop();
            const notif = {error: {title: 'Pausar Job', detail: 'Ocurrió un error inesperado.'}};
            this.notifierError(notif);
          }
        }
    });
  }

  onStartNow() {
    const textContent: string = 'Por favor confirme para iniciar la ejecución del Job';
    const title: string = 'Iniciar Job Ahora';
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title}))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          this.blockUI.start('Procesando solicitud...');
          try {
            this.job.startNowCallback().subscribe(response => {
              this.blockUI.stop();
              this.successEmitter.emit(response.body);
            }, error => {
              this.blockUI.stop();
              if (error.error) this.notifierError(error);
            });
          } catch (e) {
            this.blockUI.stop();
          }
        }
    });
  }

  editCronJob() {
    this.job.editableJobCallback();
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {
  }

  onLgScreen() {
  }

  onMdScreen() {
  }

  onSmScreen() {
  }

  onXsScreen() {
  }

  setPage(pageInfo: Page) {
  }

}
