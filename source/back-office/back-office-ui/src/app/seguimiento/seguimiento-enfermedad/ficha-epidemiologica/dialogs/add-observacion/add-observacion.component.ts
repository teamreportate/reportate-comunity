import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PacienteService} from '../../../../../core/services/http-services/paciente.service';
import {EnfermedadService} from '../../../../../core/services/http-services/enfermedad.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../../../core/models/dto/custom-options';
import {Page} from '../../../../../core/utils/paginator/page';
import {ClicComponent} from '../../../../../core/utils/clic-component';
import {DiagnosticoService} from '../../../../../core/services/http-services/diagnostico.service';

@Component({
  selector: 'app-add-observacion',
  templateUrl: './add-observacion.component.html',
  styleUrls: ['./add-observacion.component.sass']
})
export class AddObservacionComponent extends ClicComponent implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;

  public enfermedades: any[];
  public form: FormGroup;
  public today: Date;
  flex: number;

  constructor(private service: DiagnosticoService, private enfermedadService: EnfermedadService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<AddObservacionComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    console.warn(this.data);
    this.form = this.builder.group({
      estadoDiagnostico: new FormControl(this.data.estado, Validators.compose([Validators.required, Validators.maxLength(100)])),
      observacion: new FormControl(this.data.observacion, Validators.compose([Validators.required, Validators.maxLength(4000)])),
    });

  }

  close(): void {
    this.dialogRef.close(null);
  }

  save() {
    if (this.form.valid) {
      this.blockUi.start('Procesando solicitud...');
      this.service.updateDiagnosticoState(this.data.idDiagnostico, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = {title: 'Actualizar Diagnostico', detail: 'Diagnostico actualizado satisfactoriamente.', object: response.body};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) {
          this.notifierError(error);
        }
      });
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
