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


  sexos = Constants.SEXOS;
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
  ngOnInit() {
    this.form = this.builder.group({
      id: new FormControl(this.data.id),
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.maxLength(100)])),
      edad: new FormControl(this.data.edad, Validators.compose([Validators.required, Validators.maxLength(100)])),
      genero: new FormControl(this.data.genero, Validators.compose([Validators.required])),
      gestacion: new FormControl(this.data.gestacion, Validators.compose([])),
      tiempoGestacion: new FormControl(this.data.tiempoGestacion, Validators.compose([])),
      ocupacion: new FormControl(this.data.ocupacion, Validators.compose([])),
      ci: new FormControl(this.data.ci, Validators.compose([])),
      fechaNacimiento: new FormControl(new Date(this.data.fechaNacimiento), Validators.compose([])),
      seguro: new FormControl(this.data.seguro, Validators.compose([])),
      codigoSeguro: new FormControl(this.data.codigoSeguro, Validators.compose([])),
    });
  }
  close(): void {
    this.dialogRef.close(null);
  }
  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      const value = this.form.controls['fechaNacimiento'].value;
      this.form.controls['fechaNacimiento'].setValue(moment(this.form.controls['fechaNacimiento'].value).format('DD/MM/YYYY'));
      this.service.addContacto(this.data.idPaciente, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Registrar Contacto', detail: 'Contacto registrado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
        this.form.controls['fechaNacimiento'].setValue(value);

      }, error => {
        this.form.controls['fechaNacimiento'].setValue(value);

        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    } else {
      const value = this.form.controls['fechaNacimiento'].value;
      this.form.controls['fechaNacimiento'].setValue(moment(this.form.controls['fechaNacimiento'].value).format('DD/MM/YYYY'));
      this.blockUi.start('Procesando solicitud...');
      this.service.updatePacienteId(this.form.controls['id'].value,this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Actualizar Contacto', detail: 'Contacto actualizado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
        this.form.controls['fechaNacimiento'].setValue(value);

      }, error => {
        this.form.controls['fechaNacimiento'].setValue(value);

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
