import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { AdministrationService } from '../administration.service';
import { MatDialog } from '@angular/material';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ConfirmDialogComponent } from 'src/app/commons/dialogs/confirm-dialog.component';
import { MuState } from 'src/app/core/models/mu-state';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';
import { ModalSintomaComponent } from './modal-sintoma/modal-sintoma.component';

@Component({
  selector: 'app-sintomas',
  templateUrl: './sintomas.component.html',
  styleUrls: ['./sintomas.component.scss']
})
export class SintomasComponent extends ClicComponent implements OnInit {
  constructor(public dialog: MatDialog, private service: AdministrationService, private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }
  @BlockUI() blockUI: NgBlockUI;
  public list: any[][];
  private temp: any[] = [];

  public flex: number;

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.requestSintoma();
  }

  requestSintoma() {
    this.blockUI.start('Recuperando lista de síntomas...');
    this.service.requestSintomaList().subscribe(response => {
      if (response.body && response.body.length > 0) {
        this.loadList(response.body);
      }
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) {
        this.notifierError(error);
      }
    });
  }

  loadList(responseList: any[]) {
    //  responseList.sort((a, b) => a.nombre.localeCompare(b.nombre)); orden por nombre
    this.list = responseList;
    this.temp = [...this.list];
  }


  openDialog(row: any) {
    let temporal: any;
    let operacion = '';
    if (row !== null) {
      temporal = row;
      operacion = 'UPD';
    } else {
      temporal = {};
      operacion = 'CRE';
    }
    const dialogRef = this.dialog.open(ModalSintomaComponent, this.dialogConfig(temporal));
    dialogRef.componentInstance.operacion = operacion;
    dialogRef.afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = { error: { title: result.title, detail: result.detail } };
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
      });
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim().toLowerCase();

    this.list = this.temp.filter((item) => {
      return item.nombre.toLowerCase().indexOf(filterValue) !== -1 ||
        item.pregunta.toLowerCase().indexOf(filterValue) !== -1 ||
        String(item.value).toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
    });
  }
  onChangeState(row: any) {
    const target = row.estado === MuState.ACTIVO ? MuState.INACTIVO : MuState.ACTIVO;
    const textContent: String = `Confirmar para cambiar el estado del Síntoma: ${row.nombre}, de ${row.estado} -> ${target}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({ textContent, title: 'Cambiar Estado del Síntoma' }))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.service.requestChangeSintomaStatus(row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = { error: { title: `${row.estado === MuState.ACTIVO ? 'Inhabilitar Síntoma' : 'Habilitar Síntoma'}`, detail: `${row.estado === MuState.ACTIVO ? 'Síntoma inhabilitado satisfactoriamente.' : 'Síntoma habilitado satisfactoriamente.'}` } };
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) { this.notifierError(error); }
          });
        }
      });
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      // tslint:disable-next-line:max-line-length
      const customOptions: CustomOptions = { type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl };
      this.notifier.show(customOptions);
    }
  }
  onGtLgScreen() {
    this.flex = 10;
    this.dialogWidth = '750px';
  }

  onLgScreen() {
    this.flex = 15;
    this.dialogWidth = '750px';
  }

  onMdScreen() {
    this.flex = 25;
    this.dialogWidth = '750px';
  }

  onSmScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }

  onXsScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }
  setPage(pageInfo: Page) { }


}
