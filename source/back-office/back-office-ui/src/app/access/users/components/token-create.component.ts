/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    12-09-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../../core/utils/clic-component';
import {Page} from '../../../core/utils/paginator/page';
import {FormControl, FormGroup} from '@angular/forms';
import * as moment from 'moment';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AccessService} from '../../access.service';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';

@Component({
  selector: 'app-token-create',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Generar Nuevo Token</mat-card-title></mat-card-header>
      <mat-card-content>
        <div fxLayout="row wrap" [formGroup]="this.form">
          <div fxFlex="100" class="p-5 v-padding-10">
              <mat-slide-toggle color="accent" formControlName="indefinido">Crear con fecha de expiración indefinida</mat-slide-toggle>
          </div>
          <div fxFlex="80" class="h-padding-5" *ngIf="!this.form.get('indefinido').value">
            <mat-form-field>
              <input matInput [matDatepicker]="endDate" [min]="this.today" placeholder="Fecha de Expiración Para el Token" formControlName="fechaExpiracion" readonly>
              <mat-datepicker-toggle matSuffix [for]="endDate"></mat-datepicker-toggle>
              <mat-datepicker #endDate></mat-datepicker>
              <mat-error *ngIf="this.form.get('fechaExpiracion').hasError('required') && this.form.get('fechaExpiracion').touched">La fecha de expiración es requerida.</mat-error>
            </mat-form-field>
          </div>
          <div fxFlex="20" class="h-padding-5" *ngIf="!this.form.get('indefinido').value">
            <mat-form-field>
              <input matInput type="time" formControlName="hour" placeholder="Hora">
            </mat-form-field>
          </div>
          <div fxFlex="100" fxLayoutAlign="center center" *ngIf="this.load">
            <mat-spinner [diameter]="30"></mat-spinner>
          </div>
        </div>
        <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
          <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
          <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.generateToken()">Guardar</button>
        </mat-card-actions>
      </mat-card-content>
    </mat-card>
  `
})

export class TokenCreateComponent extends ClicComponent implements OnInit {
  public form: FormGroup;
  public load: boolean;
  public today: any;
  constructor(private service: AccessService, private notifier: NotifierService,
              private dialogRef: MatDialogRef<TokenCreateComponent>, @Inject(MAT_DIALOG_DATA) private data: {userId: number},
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.load = false;
    this.form = new FormGroup({
      fechaExpiracion: new FormControl(moment().toDate()),
      hour: new FormControl('23:59'),
      indefinido: new FormControl(false)
    });
    this.today = moment().toDate();
  }

  generateToken() {
    this.load = true;
    if (!Boolean(this.form.get('indefinido').value)) this.setDates();
    this.service.requestTokenUserGenerate(this.data.userId, this.form.value).subscribe(response => {
      this.load = false;
      this.dialogRef.close(response.body);
    }, error1 => {
      this.load = false;
    });
  }

  cancel() {
    this.dialogRef.close();
  }

  setDates() {
    let fecha = moment.utc(this.form.get('fechaExpiracion').value);
    let hora: moment.Moment = moment.utc(this.form.get('hour').value, 'HH:mm');
    let time = fecha.toDate();
    time.setHours(hora.hours());
    time.setMinutes(hora.minutes());
    time.setSeconds(0);
    time.setMilliseconds(0);
    fecha = moment.utc((time));
    this.form.get('fechaExpiracion').setValue(fecha.toDate());
  }

  notifierError(error: any) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;

  onXsScreen() {
    this.flex = 100;
  }

  onSmScreen() {
    this.flex = 25;
  }

  onMdScreen() {
    this.flex = 15;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onGtLgScreen() {
    this.flex = 10;
  }

  setPage(pageInfo: Page) {}

}
