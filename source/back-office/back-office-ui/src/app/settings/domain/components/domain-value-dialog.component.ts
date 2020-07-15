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
import {DomainValue} from '../../../core/models/domain-value';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {Domain} from '../../../core/models/domain';

@Component({
  selector: 'app-edit-domain-value',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>{{this.data.create ? 'Nuevo Valor de Dominio' : 'Actualizar Valor de Dominio'}}</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput type="text" maxlength="100" placeholder="Valor" formControlName="valor">
                <mat-hint align="end">{{this.form.get('valor').value? this.form.get('valor').value.length:0}} / 100</mat-hint>
                <mat-error *ngIf="this.form.get('valor').hasError('required') && this.form.get('valor').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
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
            <button mat-flat-button color="accent" type="button" (click)="this.editDomainValue()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class DomainValueDialogComponent extends ClicComponent implements OnInit  {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  public domainValue: DomainValue;
  private domain: Domain;
  constructor(private service: SettingsService, private dialogRef: MatDialogRef<DomainValueDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {domain: Domain, domainValue: DomainValue, create: boolean}, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.domainValue = this.data.domainValue;
    this.domain = this.data.domain;
    this.initializeForm();
  }

  editDomainValue() {
    if (this.form.valid) {
      if (this.data.create) this.storeDomainValue();
      else this.updateDomainValue();
    } else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({onlySelf: true});
      }
    }
  }

  updateDomainValue() {
    this.blockUI.start('Procesando solicitud...');
    this.service.requestUpdateDomainValue(this.domainValue.id, this.form.value).subscribe(response => {
      this.blockUI.stop();
      this.dialogRef.close(true);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  storeDomainValue() {
    this.blockUI.start('Procesando solicitud...');
    this.service.requestStoreDomainValue(this.domain.codigo, this.form.value).subscribe(response => {
      this.blockUI.stop();
      this.dialogRef.close(true);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  initializeForm = () => this.form = new FormGroup({
    valor: new FormControl(!this.data.create ? this.domainValue.valor : null, Validators.compose([Validators.required])),
    descripcion: new FormControl(!this.data.create ? this.domainValue.descripcion : null, Validators.compose([Validators.required])),
  });

  cancel() {
    this.dialogRef.close();
  }


  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }
  public flex: number ;
  onGtLgScreen() {
    this.flex = 10;
    this.dialogWidth = '650px';
  }

  onLgScreen() {
    this.flex = 15;
    this.dialogWidth = '650px';
  }

  onMdScreen() {
    this.flex = 25;
    this.dialogWidth = '60%';
  }

  onSmScreen() {
    this.flex = 100;
    this.dialogWidth = '80%';
  }

  onXsScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }

  setPage(pageInfo: Page) {}

}
