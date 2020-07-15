/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    07-08-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ClicComponent} from 'src/app/core/utils/clic-component';
import {Page} from 'src/app/core/utils/paginator/page';
import {CustomOptions} from 'src/app/core/models/dto/custom-options';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {SettingsService} from '../../settings.service';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {Domain} from '../../../core/models/domain';

@Component({
  selector: 'app-edit-domain',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Actualizar Dominio</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput type="text" maxlength="100" placeholder="Código del Dominio" formControlName="codigo">
              </mat-form-field>
            </div>
            <div fxFlex="100">
              <mat-form-field>
                <textarea matInput rows="5" maxlength="255" placeholder="Descripción" formControlName="descripcion"></textarea>
                <mat-hint align="end">{{this.form.get('descripcion').value? this.form.get('descripcion').value.length:0}} / 255</mat-hint>
                <mat-error *ngIf="this.form.get('descripcion').hasError('required') && this.form.get('descripcion').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
              </mat-form-field>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-stroked-button color="warn" type="button" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" (click)="this.editDomain()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class DomainDialogComponent extends ClicComponent implements OnInit  {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  public domain: Domain;
  constructor(private service: SettingsService, private dialogRef: MatDialogRef<DomainDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {domain: Domain}, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.domain = this.data.domain;
    this.initializeForm();
  }

  editDomain() {
    if (this.form.valid) this.updateDomain();
    else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({onlySelf: true});
      }
    }
  }

  updateDomain() {
    this.blockUI.start('Procesando solicitud...');
    this.service.requestUpdateDomain(this.domain.id, this.form.value).subscribe(response => {
      this.blockUI.stop();
      this.dialogRef.close(true);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  initializeForm = () => this.form = new FormGroup({
    codigo: new FormControl({value: this.domain.codigo, disabled: true}),
    descripcion: new FormControl(this.domain.descripcion, Validators.compose([Validators.required])),
  });

  cancel = () => this.dialogRef.close();

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }
  public flex: number ;
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
