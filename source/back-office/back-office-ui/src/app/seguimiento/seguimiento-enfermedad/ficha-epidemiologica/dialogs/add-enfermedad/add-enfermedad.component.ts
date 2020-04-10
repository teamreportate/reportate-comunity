import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {ClicComponent} from '../../../../../core/utils/clic-component';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../../../core/models/dto/custom-options';
import {Page} from '../../../../../core/utils/paginator/page';
import {PacienteService} from '../../../../../core/services/http-services/paciente.service';
import {PaisService} from '../../../../../core/services/http-services/pais.service';
import {EnfermedadService} from '../../../../../core/services/http-services/enfermedad.service';

@Component({
  selector: 'app-add-enfermedad',
  templateUrl: './add-enfermedad.component.html',
  styleUrls: ['./add-enfermedad.component.sass']
})
export class AddEnfermedadComponent extends ClicComponent implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;

  public enfermedades: any[];
  public form: FormGroup;
  public today: Date;
  flex: number;

  constructor(private service: PacienteService, private enfermedadService: EnfermedadService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<AddEnfermedadComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    console.warn(this.data);

    this.form = this.builder.group({
      enfermedadId: new FormControl(this.data.id, Validators.compose([Validators.required, Validators.maxLength(100)])),
    });
    this.enfermedadService.getEnfermedades().subscribe(response => {
      this.enfermedades = response.body;
    });
  }

  close(): void {
    this.dialogRef.close(null);
  }

  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.addEnfermedad(this.data.idPaciente, this.form.controls['enfermedadId'].value).subscribe(response => {
        this.blockUi.stop();
        const msg = {title: 'Registro de Enfermedad', detail: 'Enfermedad registrado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) {
          this.notifierError(error);
        }
      });
    } else {
      // this.blockUi.start('Procesando solicitud...');
      // this.service.u(this.data.id, this.form.value).subscribe(response => {
      //   this.blockUi.stop();
      //   const msg = { title: 'Actualizar Enfermedad', detail: 'Enfermedad actualizado satisfactoriamente.'};
      //   this.dialogRef.close(msg);
      // }, error => {
      //   this.blockUi.stop();
      //   if (error) { this.notifierError(error); }
      // });
    }
  }

  cancel = () => this.dialogRef.close(null);

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      // tslint:disable-next-line:max-line-length
      const customOptions: CustomOptions = {
        type: type ? type : 'error',
        tile: error.error.title,
        message: error.error.detail,
        template: this.customNotificationTmpl
      };
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

  setPage(pageInfo: Page) {
  }

}
