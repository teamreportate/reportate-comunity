import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from '../../core/utils/clic-component';
import { BlockUI } from 'ng-block-ui';
import { NgBlockUI } from 'ng-block-ui/models/block-ui.model';
import { Page } from '../../core/utils/paginator/page';
import { AdministrationService } from '../administration.service';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Departamento } from '../../core/models/dto/Departamento';
import { MediaMatcher } from '@angular/cdk/layout';
import { ModalDepartamentoComponent } from './modal-departamento/modal-departamento.component';
import { ConfirmDialogComponent } from '../../commons/dialogs/confirm-dialog.component';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';

@Component({
  selector: 'app-departamentos',
  templateUrl: './departamentos.component.html',
  styleUrls: ['./departamentos.component.sass']
})
export class DepartamentosComponent extends ClicComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;
  public userList: Departamento[];
  private tmp: Departamento[];

  constructor(private administrationService: AdministrationService, private dialog: MatDialog,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher,
    private router: Router, private notifier: NotifierService) {
      super();
    }

  ngOnInit() {
    this.userList = this.tmp = null;
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start(`Recuperando lista de departamentos...`);
    this.administrationService.requestDepartamentoList().subscribe(response => {
      console.log(response.body)
      this.userList = response.body;
      this.userList.sort((a: Departamento, b: Departamento) => a.nombre.localeCompare(b.nombre));
      this.tmp = this.userList;
      this.blockUI.stop()
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  openDialog(row: any) {
    let temporal: any;
    if (row !== null) {
         temporal = row;
    } else {
      temporal = {};
    }
    this.dialog.open(ModalDepartamentoComponent, this.dialogConfig(temporal))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
    });
  }

  onChangeState(row: any) {
    const textContent: String = `Confirma eliminar el Departamento: ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({ textContent, title: 'Eliminar departamento' }))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.administrationService.requestDeleteDepartamentoStatus(row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = { error: { title: `${'Eliminar Departamento'}`, detail: `${'Departamento eliminado satisfactoriamente.'}` } };
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
      const customOptions: CustomOptions = {
        type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl
      };
      this.notifier.show(customOptions);
    }
  }

  onFilter(filterValue: string) {
    filterValue = filterValue.toLowerCase();

    this.userList = this.tmp.filter(item => {
        return item.nombre.toLowerCase().includes(filterValue)
        // || item.latitud.indexOf(filterValue) !== -1
        // || (item.longitud.includes(filterValue))
    });

  }

  public flex: number;
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
