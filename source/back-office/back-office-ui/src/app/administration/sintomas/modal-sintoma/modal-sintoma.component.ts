import { Component, OnInit, ViewEncapsulation, Output, Inject, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { Page } from 'src/app/core/utils/paginator/page';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AdministrationService } from '../../administration.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';

@Component({
  selector: 'app-modal-sintoma',
  templateUrl: './modal-sintoma.component.html',
  styleUrls: ['./modal-sintoma.component.scss'],
})
export class ModalSintomaComponent extends ClicComponent  implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;



  public form: FormGroup;
  public today: Date;
  flex: number;
  constructor(private service: AdministrationService, private builder: FormBuilder,
    private dialogRef: MatDialogRef<ModalSintomaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
}

  ngOnInit() {
    this.form = this.builder.group({
      id: new FormControl(this.data.id),
      // tslint:disable-next-line:max-line-length
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.maxLength(100)])),
      pregunta: new FormControl(this.data.pregunta, Validators.compose([Validators.required, Validators.maxLength(500)])),
      controlDiario: new FormControl(this.data.controlDiario),
      ayuda: new FormControl(this.data.ayuda, Validators.compose([Validators.maxLength(500)])),
    });
    if (this.operacion === 'CRE') {
      this.form.get('controlDiario').setValue(false);
    }
  }
  close(): void {
    this.dialogRef.close(null);
  }
  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestSaveSintoma(this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Registro de Sintoma', detail: 'Sintoma registrado satisfactoriamente.'};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    } else {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestUpdateSintoma(this.data.id, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Actualizar Sintoma', detail: 'Sintoma actualizado satisfactoriamente.'};
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
