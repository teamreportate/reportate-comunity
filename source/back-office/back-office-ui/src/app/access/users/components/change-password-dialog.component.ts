import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AuthUser} from '../../../core/models/AuthUser';
import {AccessService} from '../../access.service';
import {passwordMatchValidator} from '../../resources/utils/password-match-validator';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../../core/utils/clic-component';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Page} from '../../../core/utils/paginator/page';
import {NotifierService} from 'angular-notifier';

@Component({
  selector: 'app-change-password-dialog',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Cambiar Contraseña</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Nueva Contraseña" formControlName="password" [type]="ocultar ? 'password' : 'text'" (input)="onPasswordInput()" id="password" maxlength="20">
                <button mat-icon-button color="accent" matSuffix (click)="ocultar = !ocultar" [attr.aria-pressed]="ocultar">
                    <mat-icon>{{ocultar ? 'visibility_off' : 'visibility'}}</mat-icon>
                </button>
                  <mat-hint align="end">{{this.form.get('password').value? this.form.get('password').value.length:0}} / 20</mat-hint>
                <mat-error *ngIf="this.form.get('password').hasError('required') && this.form.get('password').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('password').hasError('maxlength') && this.form.get('password').touched" class="text-danger font-11">Solo se permite <strong>20 caracteres</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('password').hasError('minlength') && this.form.get('password').touched" class="text-danger font-11">Se requiere al menos <strong>8 caracteres</strong>.</mat-error>
              </mat-form-field>
            </div>
  
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="Confirmar Contraseña" formControlName="passwordConfirm" [type]="ocultar ? 'password' : 'text'" (input)="onPasswordInput()" maxlength="20">
                <button mat-icon-button color="accent" matSuffix (click)="ocultar = !ocultar" [attr.aria-pressed]="ocultar">
                    <mat-icon>{{ocultar ? 'visibility_off' : 'visibility'}}</mat-icon>
                </button>
                  <mat-hint align="end">{{this.form.get('passwordConfirm').value? this.form.get('passwordConfirm').value.length:0}} / 20</mat-hint>
                <mat-error *ngIf="this.form.get('passwordConfirm').hasError('required') && this.form.get('passwordConfirm').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="form.get('passwordConfirm').invalid && !form.get('password').hasError('required')">Las contraseñas no coinciden</mat-error>
              </mat-form-field>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="15px" >
            <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.changePassword()">Guardar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
        <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
    `
})

export class ChangePasswordDialogComponent extends ClicComponent implements OnInit {

  public form: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public ocultar = true;

  constructor(private accessService: AccessService, private dialogRef: MatDialogRef<ChangePasswordDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: AuthUser,
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.load = false;
    this.form = new FormGroup({
      password: new FormControl('', Validators.compose([Validators.required, Validators.maxLength(20), Validators.minLength(8)])),
      passwordConfirm: new FormControl('', Validators.compose([Validators.required])),
    }, {
      validators: passwordMatchValidator
    });
  }

  cancel() {
    this.dialogRef.close();
  }

  changePassword() {
    if (this.form.valid) {
      this.load = true;
      const user: AuthUser = this.form.value;
      const pass = this.form.get('password').value;
      const repass =  this.form.get('passwordConfirm').value;
      this.accessService.requestChangePassword(user, String(this.data.id), pass, repass).subscribe(response => {
        this.dialogRef.close(true);
      }, error1 => {
        this.load = false;
        if (error1) this.notifierError(error1);
      });
    } else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({onlySelf: true});
      }
    }
  }

  onPasswordInput() {
    if (this.form.hasError('passwordMismatch')){
      this.form.get('passwordConfirm').setErrors([{'passwordMismatch': true}]);
    } else {
      this.form.get('passwordConfirm').setErrors(null);
    }
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
