import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PacienteService} from '../../../core/services/http-services/paciente.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ClicComponent} from '../../../core/utils/clic-component';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {NotifierService} from 'angular-notifier';
import {Constants} from '../../../core/constants';
import {ConfirmDialogComponent} from '../../../commons/dialogs/confirm-dialog.component';
import {MatDialog} from '@angular/material';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {AddEnfermedadComponent} from './dialogs/add-enfermedad/add-enfermedad.component';
import {AddPaisViajadoComponent} from './dialogs/add-pais-viajado/add-pais-viajado.component';
import {AddContactoComponent} from './dialogs/add-contacto/add-contacto.component';
import * as moment from 'moment';
import {ListSintomasComponent} from './dialogs/list-sintomas/list-sintomas.component';

interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-ficha-epidemiologica',
  templateUrl: './ficha-epidemiologica.component.html',
  styleUrls: ['./ficha-epidemiologica.component.sass'],
  providers: []
})
export class FichaEpidemiologicaComponent extends ClicComponent implements OnInit {
  idPaciente = this.route.snapshot.paramMap.get('idPaciente');
  public form: FormGroup;
  private message: string;
  listEnfermedadesBase = [];
  listPaisesVisitados = [];
  listDiagnosticos = [];
  listContactos = [];
  public filterFlex;
  @BlockUI() blockUI: NgBlockUI;

  constructor(private route: ActivatedRoute, public dialog: MatDialog, private pacienteService: PacienteService, private formBuilder: FormBuilder, private notifier: NotifierService) {
    super();
    this.form = this.initial();
    moment.locale('es-BO');

  }

  estados = Constants.CLASIFICACION_ENFERMEDAD2;
  generos = Constants.SEXOS;

  ngOnInit() {
    this.pacienteService.getFichaEpidemiologica(this.idPaciente).subscribe(response => {
      this.form = this.initialForm(response.body);
      console.warn(response.body);
      this.listPaisesVisitados = response.body.paisesVisitados;
      this.listEnfermedadesBase = response.body.enfermedadesBase;
      this.listDiagnosticos = response.body.diagnosticos;
      this.listContactos = response.body.contactos;
    });
  }

  private initial(): FormGroup {
    return this.formBuilder.group({
      id: new FormControl('', Validators.compose([Validators.required])),
      nombre: new FormControl('', Validators.compose([Validators.required])),
      edad: new FormControl('', Validators.compose([Validators.required])),
      telefono: new FormControl('', Validators.compose([Validators.required])),
      ci: new FormControl('', Validators.compose([Validators.required])),
      departamento: new FormControl('', Validators.compose([Validators.required])),
      municipio: new FormControl('', Validators.compose([Validators.required])),
      genero: new FormControl('', Validators.compose([Validators.required])),
      ciudad: new FormControl('', Validators.compose([Validators.required])),
      direccion: new FormControl('', Validators.compose([Validators.required])),
      ocupacion: new FormControl('', Validators.compose([Validators.required])),
      gestacion: new FormControl('', Validators.compose([])),
      tiempoGestacion: new FormControl('', Validators.compose([])),
      fechaNacimiento: new FormControl('', Validators.compose([Validators.required])),
      seguro: new FormControl('', Validators.compose([])),
      codigoSeguro: new FormControl('', Validators.compose([])),
      ubicacion: new FormControl('', Validators.compose([Validators.required])),
    });
  }

  private initialForm(data: any): FormGroup {
    return this.formBuilder.group({
      id: new FormControl(data.id, Validators.compose([Validators.required])),
      nombre: new FormControl(data.nombre, Validators.compose([Validators.required])),
      edad: new FormControl(data.edad, Validators.compose([Validators.required])),
      telefono: new FormControl(data.telefono, Validators.compose([Validators.required])),
      ci: new FormControl(data.ci, Validators.compose([Validators.required])),
      departamento: new FormControl(data.departamento, Validators.compose([Validators.required])),
      municipio: new FormControl(data.municipio, Validators.compose([Validators.required])),
      genero: new FormControl(data.genero, Validators.compose([Validators.required])),
      ciudad: new FormControl(data.ciudad, Validators.compose([Validators.required])),
      direccion: new FormControl(data.direccion, Validators.compose([Validators.required])),
      ocupacion: new FormControl(data.ocupacion, Validators.compose([Validators.required])),
      gestacion: new FormControl(data.gestacion, Validators.compose([])),
      tiempoGestacion: new FormControl(data.tiempoGestacion, Validators.compose([])),
      fechaNacimiento: new FormControl(new Date(data.fechaNacimiento), Validators.compose([Validators.required])),
      seguro: new FormControl(data.seguro, Validators.compose([])),
      codigoSeguro: new FormControl(data.codigoSeguro, Validators.compose([])),
      ubicacion: new FormControl(data.ubicacion, Validators.compose([Validators.required])),
    });
  }

  setPage(pageInfo: any) {
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {
        type: type ? type : 'error',
        tile: error.error.title,
        message: error.error.detail,
        template: this.customNotificationTmpl
      };
      this.notifier.show(customOptions);
    }
  }

  onXsScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onSmScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onMdScreen() {
    this.filterFlex = 50;
    this.scrollH = true;
  }

  onLgScreen() {
    this.filterFlex = 50;
    this.scrollH = false;
  }

  onGtLgScreen() {
    this.filterFlex = 50;
    this.scrollH = false;
  }

  deleteContact(row: any) {
    const textContent: String = `Confirma eliminar el Contacto : ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Eliminar Contacto'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.pacienteService.eliminarContacto(row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = {error: {title: 'Eliminar Contacto', detail: 'Contacto eliminado satisfactoriamente.'}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) {
              this.notifierError(error);
            }
          });
        }
      });
  }

  deletePais(row: any) {
    const textContent: String = `Confirmar eliminar el Pais : ${row.pais}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Eliminar Pais viajado'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.pacienteService.eliminarPaisViajado(this.idPaciente, row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = {error: {title: 'Eliminar Pais Viajado', detail: 'Pais viajado eliminado satisfactoriamente.'}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) {
              this.notifierError(error);
            }
          });
        }
      });
  }

  deleteEnfermedad(row: any) {
    const textContent: String = `Confirma eliminar la Enfermedad: ${row.nombre}`;
    this.dialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Eliminar Enfermedad'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.pacienteService.eliminarEnfermedadBase(this.idPaciente, row.id).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            // tslint:disable-next-line:max-line-length
            const notif = {error: {title: 'Eliminar Enfermedad', detail: 'Enfermedad eliminado satisfactoriamente.'}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) {
              this.notifierError(error);
            }
          });
        }
      });
  }


  openDialogEnfermedad(row: any) {
    let temporal: any;
    if (row !== null) {
      temporal = row;
    } else {
      temporal = {};
    }
    temporal.idPaciente = this.idPaciente;
    const dialogRef = this.dialog.open(AddEnfermedadComponent, this.dialogConfig(temporal));
    dialogRef.afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
      });
  }

  openDialogPaisesViaje(row: any) {
    let temporal: any;
    if (row !== null) {
      temporal = row;
    } else {
      temporal = {};
    }
    temporal.idPaciente = this.idPaciente;

    const dialogRef = this.dialog.open(AddPaisViajadoComponent, this.dialogConfig(temporal));
    dialogRef.afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
      });
  }

  openDialogContactos(row: any) {
    let temporal: any;
    if (row !== null) {
      temporal = row;

    } else {
      temporal = {};
    }
    temporal.idPaciente = this.idPaciente;

    const dialogRef = this.dialog.open(AddContactoComponent, this.dialogConfig(temporal));
    dialogRef.afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: result.title, detail: result.detail}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
      });
  }

  openDialogSintomas(row: any) {
    const temporal: any = {};
    temporal.id = row.id;
    const dialogRef = this.dialog.open(ListSintomasComponent, this.dialogConfig(temporal));
    dialogRef.afterClosed()
      .subscribe(result => {

      });
  }


  updateDataPaciente() {
    if (this.form.valid) {
      this.blockUI.start('Procesando solicitud...');

      const value = this.form.controls['fechaNacimiento'].value;
      this.form.controls['fechaNacimiento'].setValue(moment(this.form.controls['fechaNacimiento'].value).format('DD/MM/YYYY'));
      this.pacienteService.updatePacienteId(this.idPaciente, this.form.value).subscribe(response => {
        this.blockUI.stop();
        // this.ngOnInit();
        // tslint:disable-next-line:max-line-length
        const notif = {error: {title: 'Datos de paciente', detail: 'Datos del paciente actualizado satisfactoriamente.'}};
        this.notifierError(notif, 'info');
        this.form.controls['fechaNacimiento'].setValue(value);
      }, error => {
        this.form.controls['fechaNacimiento'].setValue(value);

        this.blockUI.stop();
        if (error) {
          this.notifierError(error);
        }
      });
    }
  }
}
