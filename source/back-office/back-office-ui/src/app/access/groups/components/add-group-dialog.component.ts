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
import {AccessService} from '../../access.service';
import {AuthGroup} from '../../../core/models/auth-group';
import {ClicComponent} from 'src/app/core/utils/clic-component';
import {Page} from 'src/app/core/utils/paginator/page';
import {CustomOptions} from 'src/app/core/models/dto/custom-options';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {AuthRole} from '../../../core/models/auth-role';

@Component({
  selector: 'app-add-group',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Nuevo Grupo</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Nombre del Grupo" formControlName="nombre" maxlength="50">
                <mat-hint align="end">{{this.form.get('nombre').value? this.form.get('nombre').value.length:0}} / 50</mat-hint>
                <mat-error *ngIf="this.form.get('nombre').errors?.pattern && this.form.get('nombre').touched" class="text-danger font-11">solo se permiten caracteres alfanumericos y guion bajo (_).</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('required') && this.form.get('nombre').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('maxlength') && this.form.get('nombre').touched" class="text-danger font-11">Solo se permite <strong>50 caracteres</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('minlength') && this.form.get('nombre').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
              </mat-form-field>
            </div>
  
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Descripción del Grupo" formControlName="descripcion" maxlength="255">
                <mat-hint align="end">{{this.form.get('descripcion').value? this.form.get('descripcion').value.length:0}} / 255</mat-hint>
                <mat-error *ngIf="this.form.get('descripcion').hasError('required') && this.form.get('descripcion').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('descripcion').hasError('maxlength') && this.form.get('descripcion').touched" class="text-danger font-11">Solo se permite <strong>255 caracteres</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('descripcion').hasError('minlength') && this.form.get('descripcion').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
              </mat-form-field>
            </div>

            <mat-form-field>
              <mat-label>Asignar Roles</mat-label>
              <mat-select formControlName="roles" multiple>
                <mat-option *ngFor="let rol of asignedRolList" [value]="rol">{{rol.nombre}}</mat-option>
              </mat-select>
            </mat-form-field>

            <div fxFlex="100" fxLayoutAlign="center center" *ngIf="!this.load && this.error">
              <div class="request-error-message">{{this.error}}</div>
            </div>
            <div fxFlex="100" fxLayoutAlign="center center" *ngIf="this.load">
              <mat-spinner [diameter]="30"></mat-spinner>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.createGroup()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class AddGroupDialogComponent extends ClicComponent implements OnInit  {
  public form: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public error: string;

  render: boolean;
  asignedRolList: any[];

  constructor(private accessService: AccessService, private dialogRef: MatDialogRef<AddGroupDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher)
              {
                super();
              }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.confirm = true;
    this.load = false;
    this.error = null;
    this.form = new FormGroup({
      nombre: new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.maxLength(50), Validators.minLength(5)])),
      descripcion: new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(255), Validators.minLength(5)])),
      roles: new FormControl(null)
    });
    let rol: any[] = [];
    this.data.forEach((elemento: AuthRole) => {
      if (elemento.estadoRol === 'ACTIVO')
        rol.push(elemento);
    });
    this.asignedRolList = this.data.reverse();
  }

  createGroup() {
    this.error = null;
    if (this.form.valid) {
      const group: AuthGroup = this.form.value;
      this.load = true;
      this.accessService.requestGroupStore(group).subscribe(response => {
        this.dialogRef.close(response.body);
      }, error1 => {
        this.load = false
        if (error1) this.notifierError(error1);
      });
    } else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({onlySelf: true});
      }
    }
  }

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
