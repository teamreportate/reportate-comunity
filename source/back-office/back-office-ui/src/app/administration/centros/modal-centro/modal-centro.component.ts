import { Component, OnInit, Output, Inject, ChangeDetectorRef } from '@angular/core';
import { ClicComponent } from '../../../core/utils/clic-component';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AdministrationService } from '../../administration.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ModalDepartamentoComponent } from '../../departamentos/modal-departamento/modal-departamento.component';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';

@Component({
  selector: 'app-modal-centro',
  templateUrl: './modal-centro.component.html',
  styles: []
})
export class ModalCentroComponent extends ClicComponent implements OnInit {

  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;

  public form: FormGroup;
  public today: Date;
  flex: number;
  validatorDecimal:  '^((([0-9]{1,2},)([0-9]{2},)*[0-9]{2})|([0-9]{1,2})),[0-9]{1,10}';

  constructor(private service: AdministrationService, private dialogRef: MatDialogRef<ModalCentroComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    console.log(data)
  }

  ngOnInit() {
    this.form = new FormGroup ({
      id: new FormControl(this.data.centro.id),
      municipioId: new FormControl(this.data.municipio.id),
      // tslint:disable-next-line:max-line-length
      nombre: new FormControl(this.data.centro.nombre, Validators.compose([Validators.required, Validators.maxLength(100)])),
      ciudad: new FormControl(this.data.centro.ciudad, Validators.compose([Validators.required, Validators.maxLength(100)])),
      zona: new FormControl(this.data.centro.zona, Validators.compose([Validators.required, Validators.maxLength(100)])),
      direccion: new FormControl(this.data.centro.direccion, Validators.compose([Validators.required, Validators.maxLength(100)])),
      latitud: new FormControl(this.data.centro.latitud, Validators.compose([Validators.required, Validators.pattern(this.validatorDecimal)])),
      longitud: new FormControl(this.data.centro.latitud, Validators.compose([Validators.required, Validators.pattern(this.validatorDecimal)])),
  });
  }

  close(): void {
    this.dialogRef.close(null);
  }

  save() {
    if (isNaN(this.data.centro.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestSaveCentro(this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Registro de Centro de Salud', detail: 'Centro de Salud registrado satisfactoriamente.'};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    } else {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestUpdateCentro(this.data.centro.id, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Actualizar Centro de Salud', detail: 'Centro de Salud actualizado satisfactoriamente.'};
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
