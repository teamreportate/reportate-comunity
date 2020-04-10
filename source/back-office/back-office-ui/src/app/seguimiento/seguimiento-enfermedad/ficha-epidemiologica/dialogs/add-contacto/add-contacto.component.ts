import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../../../core/models/dto/custom-options';
import {Page} from '../../../../../core/utils/paginator/page';
import {ClicComponent} from '../../../../../core/utils/clic-component';
import * as moment from 'moment';
import {Constants} from '../../../../../core/constants';
import {PacienteService} from '../../../../../core/services/http-services/paciente.service';

@Component({
  selector: 'app-add-contacto',
  templateUrl: './add-contacto.component.html',
  styleUrls: ['./add-contacto.component.sass']
})
export class AddContactoComponent  extends ClicComponent  implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;


  sexo = Constants.SEXOS;
  public form: FormGroup;
  public today: Date;
  flex: number;
  constructor(private service: PacienteService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<AddContactoComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    moment.locale('es-BO');

  }
/*
    "id": 0,
    "nombre": "string",
    "edad": 0,
    "genero": "FEMENINO",
    "gestacion": true,
    "tiempoGestacion": 0,
    "ocupacion": "string",
    "ci": "string",
    "fechaNacimiento": "string",
    "seguro": "string",
    "codigoSeguro": "string"
 */
  ngOnInit() {
    this.form = this.builder.group({
      id: new FormControl(this.data.id),
      // tslint:disable-next-line:max-line-length
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.maxLength(100)])),
      edad: new FormControl(this.data.edad, Validators.compose([Validators.required, Validators.maxLength(100)])),
      genero: new FormControl(this.data.genero, Validators.compose([Validators.required, Validators.maxLength(100)])),
      gestacion: new FormControl(this.data.gestacion, Validators.compose([Validators.required, Validators.maxLength(100)])),
      ocupacion: new FormControl(this.data.ocupacion, Validators.compose([Validators.required, Validators.maxLength(100)])),
      ci: new FormControl(this.data.tiempoGestacion, Validators.compose([Validators.required, Validators.maxLength(100)])),
      fechaNacimiento: new FormControl(this.data.fechaNacimiento, Validators.compose([Validators.required, Validators.maxLength(100)])),
      seguro: new FormControl(this.data.seguro, Validators.compose([Validators.required, Validators.maxLength(100)])),
      codigoSeguro: new FormControl(this.data.codigoSeguro, Validators.compose([Validators.required, Validators.maxLength(100)])),
      tiempoGestacion: new FormControl(this.data.tiempoGestacion, Validators.compose([Validators.required, Validators.maxLength(100)])),

    });
  }
  close(): void {
    this.dialogRef.close(null);
  }
  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.addContacto(this.data.idPaciente, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Registrar Contacto', detail: 'Contacto registrado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    } else {
      this.blockUi.start('Procesando solicitud...');
      this.service.updatePaciente(this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Actualizar Contacto', detail: 'Contacto actualizado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    }
  }

  cancel = () => this.dialogRef.close(null);

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      // tslint:disable-next-line:max-line-length
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }
  onGtLgScreen() {
    this.flex = 10;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onMdScreen() {
    this.flex = 25;
  }

  onSmScreen() {
    this.flex = 100;
  }

  onXsScreen() {
    this.flex = 100;
  }

  setPage(pageInfo: Page) {}

}
