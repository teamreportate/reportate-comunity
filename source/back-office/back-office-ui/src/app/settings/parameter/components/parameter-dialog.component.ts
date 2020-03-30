/**
 * MC4 SRL
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
import {Parameter} from '../../../core/models/parameter';
import {DataType} from '../../../core/enums/data-type';
import * as moment from 'moment';

@Component({
  selector: 'app-edit-parameter',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Actualizar Parámetro</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Código de Parámetro" formControlName="codigo">
              </mat-form-field>
            </div>
            <div fxFlex="100">
              <mat-form-field>
                 <input matInput placeholder="Descripción" formControlName="descripcion">
              </mat-form-field>
            </div>
            <div fxFlex="100" class="text-center p-t-10">
                <strong>-- Tipo de Dato : {{this.parameter.tipoDato}} --</strong>
                <div fxLayout="row wrap" [ngSwitch]="this.parameter.tipoDato">
                    <div fxFlex="100" *ngSwitchCase="'CADENA'">
                      <mat-form-field>
                        <input matInput type="text" maxlength="255" placeholder="Valor del parámetro" formControlName="valor">
                      </mat-form-field>
                    </div>
                    <div fxFlex="100" *ngSwitchCase="'NUMERICO'">
                        <mat-form-field>
                            <input matInput type="number" placeholder="Valor del parámetro" formControlName="valor">
                        </mat-form-field>
                    </div>
                    <div fxFlex="100" fxLayoutAlign="start center" class="v-padding-10" *ngSwitchCase="'BOOLEANO'">
                        <div fxLayout="row wrap">
                            <div fxFlex="100" fxLayoutAlign="start center">Valor del parámetro</div>
                            <div fxFlex="100" fxLayoutAlign="start center">
                                <mat-slide-toggle [color]="'accent'" formControlName="valor">
                                    {{this.form.get('valor').value}}
                                </mat-slide-toggle>
                            </div>
                        </div>
                    </div>
                    <div fxFlex="100" *ngSwitchCase="'FECHA'">
                      <mat-form-field>
                        <input matInput [matDatepicker]="endDate" placeholder="Valor del parámetro" formControlName="valor">
                        <mat-datepicker-toggle matSuffix [for]="endDate"></mat-datepicker-toggle>
                        <mat-datepicker #endDate></mat-datepicker>
                      </mat-form-field>
                    </div>
                    <div fxFlex="100" *ngSwitchCase="'LOB'">
                        <mat-form-field>
                            <textarea matInput type="text" rows="7" placeholder="Valor del parámetro" formControlName="valor"></textarea>
                        </mat-form-field>
                    </div>
                </div>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-stroked-button color="warn" type="button" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" (click)="this.editParameter()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class ParameterDialogComponent extends ClicComponent implements OnInit  {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  public parameter: Parameter;
  constructor(private service: SettingsService, private dialogRef: MatDialogRef<ParameterDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Parameter, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.parameter = this.data;
    this.initializeForm();
  }

  editParameter() {
    if (this.form.valid) this.updateParameter();
    else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({onlySelf: true});
      }
    }
  }

  updateParameter() {
    const payload = this.buildPayload();
    this.blockUI.start('Procesando solicitud...');
    this.service.requestUpdateParameter(this.parameter.id, payload).subscribe(response => {
      this.blockUI.stop();
      this.dialogRef.close(true);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  buildPayload() {
    switch (this.parameter.tipoDato) {
      case DataType.CADENA: this.parameter.valorCadena = this.form.get('valor').value; break;
      case DataType.LOB: this.parameter.valorCadenaLob = this.form.get('valor').value; break;
      case DataType.NUMERICO: this.parameter.valorNumerico = Number(this.form.get('valor').value); break;
      case DataType.BOOLEANO: this.parameter.valorBooleano = Boolean(this.form.get('valor').value); break;
      case DataType.FECHA: this.parameter.valorFecha = moment(this.form.get('valor').value).toDate(); break;
    }
    return this.parameter;
  }

  initializeForm = () => this.form = new FormGroup({
    codigo: new FormControl({value: this.parameter.codigo, disabled: true}),
    descripcion: new FormControl(this.parameter.descripcion, Validators.compose([Validators.required])),
    valor: new FormControl(this.parameter.tipoDato === DataType.FECHA ? moment(this.parameter.value).toDate(): this.parameter.value, Validators.compose([Validators.required])),
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
