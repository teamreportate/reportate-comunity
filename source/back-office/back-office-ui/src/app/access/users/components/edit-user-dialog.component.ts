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
import {AccessService} from '../../access.service';
import {AuthUser} from '../../../core/models/AuthUser';
import {ClicComponent} from 'src/app/core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from 'src/app/core/models/dto/custom-options';
import {Page} from 'src/app/core/utils/paginator/page';
import {AuthGroup} from '../../../core/models/auth-group';

@Component({
  selector: 'app-edit-user',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Actualizar Usuario</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">

          <div fxFlex="100">
            <mat-form-field>
              <input matInput placeholder="Nombre De Usuario" formControlName="username" disabled>
              <mat-hint align="end">{{this.form.get('username').value? this.form.get('username').value.length:0}} / 50</mat-hint>
              <mat-error *ngIf="this.form.get('username').errors?.pattern && this.form.get('username').touched" class="text-danger font-11">Solo se permiten caracteres alfanumericos y guion bajo (_).</mat-error>
              <mat-error *ngIf="this.form.get('username').hasError('required') && this.form.get('username').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
              <mat-error *ngIf="this.form.get('username').hasError('maxlength') && this.form.get('username').touched" class="text-danger font-11">Solo se permite <strong>50 caracteres</strong>.</mat-error>
              <mat-error *ngIf="this.form.get('username').hasError('minlength') && this.form.get('username').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
            </mat-form-field>
          </div>

          <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Nombre Completo" formControlName="nombre" maxlength="100">
                <mat-hint align="end">{{this.form.get('nombre').value? this.form.get('nombre').value.length:0}} / 100</mat-hint>
                <mat-error *ngIf="this.form.get('nombre').hasError('required') && this.form.get('nombre').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('maxlength') && this.form.get('nombre').touched" class="text-danger font-11">Solo se permite <strong>100 caracteres</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('minlength') && this.form.get('nombre').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
              </mat-form-field>
            </div>
  
            <div fxFlex="100">
              <mat-form-field>
                <input matInput type="email" placeholder="Correo Electrónico" formControlName="email" maxlength="50">
                <mat-error *ngIf="form.get('email').hasError('email') && !form.hasError('required') ">
                    Por favor, introduce una dirección de correo electrónico válida
                </mat-error>
                <mat-error *ngIf="this.form.get('email').hasError('required') && this.form.get('email').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
              </mat-form-field>
            </div>
  
            <div fxFlex="100">
              <mat-form-field>
                <mat-label>Tipo de Usuario</mat-label>
                <mat-select formControlName="authType" disabled>
                  <mat-option value="AD">Active Directory</mat-option>
                  <mat-option value="PWD">Sistema</mat-option>
                </mat-select>
              </mat-form-field>
            </div>

            <mat-form-field>
              <mat-label>Asignar Grupos</mat-label>
              <mat-select formControlName="grupos" multiple [ngModel]="selected" (ngModelChange)="selected" >
                <mat-option *ngFor="let group of asignedGroupList" [value]="group">{{group.nombre}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.editUser()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
  `
})

export class EditUserDialogComponent extends ClicComponent implements OnInit {
  public form: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public error : string;

  asignedGroupList: any[];
  notasignedGroupList: any[];
  render: boolean;
  selected: string[] = [];

  constructor(private accessService: AccessService, private dialogRef: MatDialogRef<EditUserDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: AuthUser,
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
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.maxLength(100), Validators.minLength(5)])),
      email: new FormControl(this.data.email, Validators.compose([Validators.required, Validators.email])),
      username: new FormControl(this.data.username, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.minLength(5), Validators.maxLength(50)])),
      authType: new FormControl(this.data.authType),
      grupos: new FormControl(null)

    });

    this.form.get('username').disable();

    const userId: string = sessionStorage.getItem('USER_ID');
    this.accessService.requestUserGroups(userId).subscribe(response => {
      this.asignedGroupList = response.body;
      this.render = true;

      this.asignedGroupList.forEach(elemento => {
        this.selected.push(elemento);
      });

      this.accessService.requestNotUserGroups(userId).subscribe((response: any) => {
        this.notasignedGroupList = response.body;
        this.notasignedGroupList.reverse();
        this.notasignedGroupList.forEach((elemento: AuthGroup) => {
          if(elemento.estadoGrupo === 'ACTIVO' || elemento.estadoGrupo === 'BLOQUEADO')
          this.asignedGroupList.push(elemento);
        });
        this.render = true;

      }, error1 => {
        this.render = true;
      });
    }, error1 => {
      this.render = true;
    });
  }

  editUser() {
    this.error = null;
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      user.username = this.data.username;
      this.load = true;
      this.accessService.requestUserUpdate(user, String(this.data.id)).subscribe(response => {
        this.dialogRef.close(response.body);
      }, error1 => {
        this.load = false;
        if(error1) this.notifierError(error1);
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
