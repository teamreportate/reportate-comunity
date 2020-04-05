import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { AdministrationService } from '../administration.service';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';
import { ModalPaisComponent } from './modal-pais/modal-pais.component';
import {MuState} from '../../core/models/mu-state';
import { ConfirmDialogComponent } from 'src/app/commons/dialogs/confirm-dialog.component';

@Component({
  selector: 'app-paises',
  templateUrl: './paises.component.html',
  styleUrls: ['./paises.component.scss']
})
export class PaisesComponent extends ClicComponent implements OnInit  {

  constructor( public dialog: MatDialog, private service: AdministrationService, private notifier: NotifierService,
               private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }
  @BlockUI() blockUI: NgBlockUI;
  public list: any[] [];
  private temp: any[] = [] ;

  public flex: number;

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
      this.requestPais();
  }

  requestPais() {
    this.blockUI.start('Recuperando lista de paises...');
    this.service.requestPaisList().subscribe(response => {
      if (response.body && response.body.length > 0) {
        this.loadList(response.body); }
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
    if (row !== null) {
         temporal = row;
    } else {
      temporal = {};
    }
    this.dialog.open(ModalPaisComponent, this.dialogConfig(temporal))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
    });
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim().toLowerCase();

    this.list = this.temp.filter((item) => {
      return item.nombre.toLowerCase().indexOf(filterValue) !== -1 ||
        String(item.value).toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
    });
  }
  onChangePaisState(row: any) {
    const target = row.estado === MuState.ACTIVO ? MuState.INACTIVO : MuState.ACTIVO;
    const textContent: String = `Confirma eliminar el País: ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Eliminar País'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.service.requestChangePaisStatus(row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = {error: {title: 'Eliminar País', detail: 'País eliminado satisfactoriamente.'}};
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
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
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
  setPage(pageInfo: Page) {}

}
