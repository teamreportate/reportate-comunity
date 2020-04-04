import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from '../../core/utils/clic-component';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { Departamento } from 'src/app/core/models/dto/Departamento';
import { Municipio } from 'src/app/core/models/dto/Municipio';
import { AdministrationService } from '../administration.service';
import { MatDialog } from '@angular/material';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { ConfirmDialogComponent } from 'src/app/commons/dialogs/confirm-dialog.component';
import { ModalMunicipioComponent } from '../municipios/modal-municipio/modal-municipio.component';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';
import { ModalCentroComponent } from './modal-centro/modal-centro.component';
import { Centro } from 'src/app/core/models/dto/Centro';

@Component({
  selector: 'app-centros',
  templateUrl: './centros.component.html',
  styleUrls: ['./centros.component.sass']
})
export class CentrosComponent extends ClicComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;
  public depaList: Departamento[] = [];
  private municipio: Municipio;
  private departamento: Departamento;
  public muniList: Municipio[];
  public centroList: Centro[] = [];
  public selectDepa: any;
  private tmp: Centro[];
  selectMuni: any;
  

  constructor(private administrationService: AdministrationService, private dialog: MatDialog,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher,
    private router: Router, private notifier: NotifierService) {
      super();
    }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.obtenerDepartamentos();
  }

  obtenerDepartamentos() {
    this.depaList = this.tmp = null;
    this.blockUI.start(`Recuperando lista de municipios...`);
    this.administrationService.requestDepartamentoList().subscribe(response => {
      this.depaList = response.body;

      if (this.depaList.length > 0) {
        if (this.departamento !== undefined) {
          const tmpDepa = this.depaList.filter((dep: Departamento) =>{ return dep.id === this.departamento.id })[0];
          this.selectDepartamento(tmpDepa)
          this.selectDepa = tmpDepa;
        } else {
          const tmpDepa = this.depaList[0];
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
    const textContent: String = `Confirma eliminar Centro de Salud: ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({ textContent, title: 'Eliminar Centro de Salud' }))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.administrationService.requestEliminarCentroStatus(row.id).subscribe(response => {
            this.blockUI.stop();
            this.obtenerCentros(this.municipio);
            // this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = { error: { title: `${'Eliminar Centro de Salud'}`, detail: `${'Centro de Salud eliminado satisfactoriamente.'}` } };
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
  }

  selectMunicipio(muni: any) {
    console.log(muni);
    this.municipio = muni;

    this.obtenerCentros(muni);
  }

  obtenerCentros(muni) {
    this.administrationService.requestCentroList(muni.id).subscribe( response => {
      console.log(response)
      this.centroList = response;

      if (this.centroList.length > 0) {
        this.centroList.sort((a: Municipio, b: Municipio) => a.nombre.localeCompare(b.nombre));
        this.tmp = this.centroList;
      }

      this.blockUI.stop();
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
    this.dialog.open(ModalCentroComponent, {
      width: this.dialogWidth,
      minWidth: this.dialogWidth,
      panelClass: ['zero-padding', 'scroll-x-hidden'],
      data: {
        centro: temporal,
        municipio: this.municipio,
        departamento: this.departamento
      }
    })
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          // this.ngOnInit();
          // this.obtenerDepartamentos();
          // console.log(this.departamento)
          // this.selectDepa = this.departamento;
          // this.selectDepartamento(this.departamento)
          this.obtenerCentros(this.municipio);
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

    this.centroList = this.tmp.filter(item => {
        return item.nombre.toLowerCase().includes(filterValue)
        || item.zona.toLowerCase().includes(filterValue)
        || item.direccion.toLowerCase().includes(filterValue)
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
