/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    07-08-19
 * author:  fmontero
 **/

import { ChangeDetectorRef, Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AccessService } from '../../access.service';
import { AuthUser } from '../../../core/models/AuthUser';
import { AuthGroup } from '../../../core/models/auth-group';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { passwordMatchValidator } from '../../resources/utils/password-match-validator';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { Page } from 'src/app/core/utils/paginator/page';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';

@Component({
  selector: 'app-add-user',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Nuevo Usuario</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                  <input matInput placeholder="Nombre de Usuario" autocomplete="false" formControlName="username" maxlength="50">
                  <mat-hint align="end">{{this.form.get('username').value? this.form.get('username').value.length:0}} / 50</mat-hint>
                  <mat-error *ngIf="this.form.get('username').errors?.pattern && this.form.get('username').touched"
                  class="text-danger font-11">Sólo se permiten caracteres alfanuméricos y guión bajo (_).</mat-error>
                  <mat-error *ngIf="this.form.get('username').hasError('required') && this.form.get('username').touched"
                  class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('username').hasError('maxlength') && this.form.get('username').touched"
                  class="text-danger font-11">Sólo se permite <strong>50 caracteres</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('username').hasError('minlength') && this.form.get('username').touched"
                  class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
                </mat-form-field>
            </div>

            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Nombre Completo" formControlName="nombre" maxlength="100">
                <mat-hint align="end">{{this.form.get('nombre').value? this.form.get('nombre').value.length:0}} / 100</mat-hint>
                <mat-error *ngIf="form.get('passwordConfirm').invalid && !form.get('password').hasError('required')">
                Las contraseñas no coinciden</mat-error>
                <mat-error *ngIf="this.form.get('nombre').hasError('required') && this.form.get('nombre').touched"
                class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
              </mat-form-field>
            </div>

            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Correo Electrónico" formControlName="email" maxlength="50">
                <mat-error *ngIf="form.get('email').errors?.pattern && !form.hasError('required') ">
                    Por favor, introduce una dirección de correo electrónico válida
                </mat-error>
                <mat-error *ngIf="this.form.get('email').hasError('required') && this.form.get('email').touched"
                class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
              </mat-form-field>
            </div>


            <div fxFlex="100">
              <mat-form-field>
                <mat-label>Tipo de Usuario</mat-label>
                <mat-select formControlName="authType" required>
                  <mat-option value="AD">Active Directory</mat-option>
                  <mat-option value="PWD">Sistema</mat-option>
                </mat-select>
              </mat-form-field>
            </div>
            <ng-container *ngIf="form.controls['authType'] && form.controls['authType'].value === 'PWD'">
              <div fxFlex="100">
                <mat-form-field>
                  <input matInput type="password" autocomplete="false" placeholder="Contraseña de Usuario"
                  formControlName="password" [type]="ocultar ? 'password' : 'text'" (input)="onPasswordInput()"
                  id="password" maxlength="20" >
                  <button mat-icon-button color="accent" matSuffix (click)="ocultar = !ocultar" [attr.aria-pressed]="ocultar">
                      <mat-icon>{{ocultar ? 'visibility_off' : 'visibility'}}</mat-icon>
                  </button>
                  <mat-hint align="end">{{this.form.get('password').value? this.form.get('password').value.length:0}} / 20</mat-hint>
                  <mat-error *ngIf="this.form.get('password').hasError('required') && this.form.get('password').touched"
                  class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('password').hasError('maxlength') && this.form.get('password').touched"
                  class="text-danger font-11">Solo se permite <strong>20 caracteres</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('password').hasError('minlength') && this.form.get('password').touched"
                  class="text-danger font-11">Se requiere al menos <strong>8 caracteres</strong>.</mat-error>
                </mat-form-field>
              </div>

              <div fxFlex="100">
                <mat-form-field>
                  <input matInput type="password" placeholder="Confirmar Contraseña" autocomplete="false"
                  formControlName="passwordConfirm" [type]="ocultar ? 'password' : 'text'" (input)="onPasswordInput()" maxlength="20">
                  <button mat-icon-button color="accent" matSuffix (click)="ocultar = !ocultar" [attr.aria-pressed]="ocultar">
                    <mat-icon>{{ocultar ? 'visibility_off' : 'visibility'}}</mat-icon>
                  </button>
                  <mat-hint align="end">{{this.form.get('passwordConfirm').value? this.form.get('passwordConfirm').value.length:0}} / 20
                  </mat-hint>
                  <mat-error *ngIf="this.form.get('passwordConfirm').hasError('required') && this.form.get('passwordConfirm').touched"
                  class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="form.get('passwordConfirm').invalid && !form.get('password').hasError('required')">
                Las contraseñas no coinciden</mat-error>
                </mat-form-field>
              </div>

            </ng-container>

            <mat-form-field>
              <mat-label>Asignar Grupos</mat-label>
              <mat-select formControlName="grupos" multiple>
                <mat-option *ngFor="let group of asignedGroupList" [value]="group">{{group.nombre}}</mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.createUser()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
        <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class AddUserDialogComponent extends ClicComponent implements OnInit {
  public form: FormGroup;
  public asswordForm: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public typeUser: undefined | 'PWD' | 'AD';
  public error: string;
  public ocultar = true;

  private EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";

  asignedGroupList: AuthGroup[];

  constructor(
    private accessService: AccessService,
    private dialogRef: MatDialogRef<AddUserDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.confirm = true;
    this.load = false;
    this.error = null;
    this.form = new FormGroup({
      nombre: new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(100), Validators.minLength(5)])),
      username: new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'),
      Validators.minLength(5), Validators.maxLength(50)])),
      authType: new FormControl(null, Validators.compose([Validators.required])),
      password: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      passwordConfirm: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      email: new FormControl(null, Validators.compose([Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])),
      grupos: new FormControl(null)
    }, {
      validators: passwordMatchValidator
    });
    this.error = null;
    let grupos: any[] = [];
    this.data.forEach((elemento: AuthGroup) => {
      if (elemento.estadoGrupo === 'ACTIVO')
        grupos.push(elemento);
    });
    this.asignedGroupList = grupos.reverse();

  }

  createUser() {
    this.error = null;

    if (this.form.controls['authType'].value === 'AD') {
      this.form.controls['password'].setValue('');
      this.form.controls['password'].setErrors(null);
      this.form.controls['passwordConfirm'].setValue('');
      this.form.controls['passwordConfirm'].setErrors(null);
    }
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      const confirm: string = this.form.get('passwordConfirm').value;
      if (user.password !== confirm) {
        this.confirm = false;
        return;
      }
      this.load = true;
      this.accessService.requestUserStore(user, confirm).subscribe(response => {
        this.dialogRef.close(response.body);
      }, error1 => {
        this.load = false;
        if (error1) this.notifierError(error1);
      });
    } else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({ onlySelf: true });
      }
    }
  }

  onPasswordInput() {
    if (this.form.hasError('passwordMismatch')) {
      this.form.get('passwordConfirm').setErrors([{ 'passwordMismatch': true }]);
    } else {
      this.form.get('passwordConfirm').setErrors(null);
    }
  }

  cancel() {
    this.dialogRef.close();
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = { type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl };
      this.notifier.show(customOptions);
    }
  }
  public flex: number;
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

  setPage(pageInfo: Page) { }


}

