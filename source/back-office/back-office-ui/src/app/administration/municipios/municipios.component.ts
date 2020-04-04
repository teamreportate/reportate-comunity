import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from '../../core/utils/clic-component';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { Municipio } from 'src/app/core/models/dto/Municipio';
import { AdministrationService } from '../administration.service';
import { MatDialog } from '@angular/material';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { ModalMunicipioComponent } from '../municipios/modal-municipio/modal-municipio.component';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';
import { Departamento } from '../../core/models/dto/Departamento';
import { ConfirmDialogComponent } from '../../commons/dialogs/confirm-dialog.component';
import { MuState } from 'src/app/core/models/mu-state';

@Component({
  selector: 'app-municipios',
  templateUrl: './municipios.component.html',
  styleUrls: ['./municipios.component.sass']
})
export class MunicipiosComponent extends ClicComponent implements OnInit {


  @BlockUI() blockUI: NgBlockUI;
  public userList: Departamento[] = [];
  private departamento: Departamento;
  public muniList: Municipio[];
  public selectDepa: any;
  private tmp: Municipio[];

  constructor(private administrationService: AdministrationService, private dialog: MatDialog,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher,
    private router: Router, private notifier: NotifierService) {
      super();
    }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.obtenerMunicipios();
  }

  obtenerMunicipios() {
    this.userList = this.tmp = null;
    this.blockUI.start(`Recuperando lista de municipios...`);
    this.administrationService.requestDepartamentoList().subscribe(response => {
      this.userList = response.body;

      if (this.userList.length > 0) {
        if (this.departamento !== undefined) {
          const tmpDepa = this.userList.filter((dep: Departamento) =>{ return dep.id === this.departamento.id })[0];
          this.selectDepartamento(tmpDepa)
          this.selectDepa = tmpDepa;
        } else {
          const tmpDepa = this.userList[0];
          this.selectDepartamento(tmpDepa)
          this.selectDepa = tmpDepa;
        }
      }

      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }


  onChangeState(row: any) {
    const textContent: String = `Confirma eliminar Municipio: ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({ textContent, title: 'Eliminar Municipio' }))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.administrationService.requestEliminarMunicipioStatus(row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = { error: { title: `${'Eliminar Municipio'}`, detail: `${'Municipio eliminado satisfactoriamente.'}` } };
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) { this.notifierError(error); }
          });
        }
      });
  }

  selectDepartamento(depa) {
    this.departamento = depa;
    this.muniList = depa.municipios;
    this.muniList.sort((a: Municipio, b: Municipio) => a.nombre.localeCompare(b.nombre));
    this.tmp = this.muniList;
  }

  openDialog(row: any) {
    let temporal: any;
    if (row !== null) {
         temporal = row;
    } else {
      temporal = {};
    }
    this.dialog.open(ModalMunicipioComponent, {
      width: this.dialogWidth,
      minWidth: this.dialogWidth,
      panelClass: ['zero-padding', 'scroll-x-hidden'],
      data: {
        municipio: temporal,
        departamento: this.departamento
      }
    })
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          // this.ngOnInit();
          this.obtenerMunicipios();
          console.log(this.departamento)
          this.selectDepa = this.departamento;
          this.selectDepartamento(this.departamento)
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

    this.muniList = this.tmp.filter(item => {
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
