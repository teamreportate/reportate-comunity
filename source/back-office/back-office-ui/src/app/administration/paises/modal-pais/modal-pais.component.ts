import { Component, OnInit, ViewEncapsulation, Inject, ChangeDetectorRef, Output } from '@angular/core';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Parameter } from 'src/app/core/models/parameter';
import { AdministrationService } from '../../administration.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { Page } from 'src/app/core/utils/paginator/page';

@Component({
  selector: 'app-modal-pais',
  templateUrl: './modal-pais.component.html',
  styleUrls: ['./modal-pais.component.sass'],
})
export class ModalPaisComponent extends ClicComponent implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;



  public form: FormGroup;
  public today: Date;
  flex: number;
  constructor(private service: AdministrationService, private builder: FormBuilder, private dialogRef: MatDialogRef<ModalPaisComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
}

  ngOnInit() {
    this.form = this.builder.group({
      id: new FormControl(this.data.id),
      // tslint:disable-next-line:max-line-length
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.maxLength(100)])),
  });
  }
  close(): void {
    this.dialogRef.close(null);
  }
  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestSavePais(this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Registro de País', detail: 'País registrado satisfactoriamente.'};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) { this.notifierError(error); }
      });
    } else {
      this.blockUi.start('Procesando solicitud...');
      this.service.requestUpdatePais(this.data.id, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = { title: 'Actualizar País', detail: 'País actualizado satisfactoriamente.'};
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
