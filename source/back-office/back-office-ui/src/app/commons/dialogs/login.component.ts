import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {ClicComponent} from '../../core/utils/clic-component';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../core/utils/paginator/page';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CommonService} from '../common.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {AUTH_DATA} from '../../../environments/environment';

@Component({
  selector: 'app-login',
  template: `
    <mat-card>
      <mat-card-header class="header-component text-white">
        <mat-card-title>Iniciar Sesión Para Continuar</mat-card-title>
      </mat-card-header>
        <form>
            <mat-card-content>
                <form [formGroup]="loginForm" (ngSubmit)="loginSubmit()">
                    <div fxLayout="row wrap">
                        <div class="text-center" fxFlex="100">
                            <img alt="homepage" src="assets/images/background/logo.svg" width="70%">
                        </div>
                    </div>
                    <br>
                    <div fxLayout="row wrap">
                        <!-- col full-->
                        <div fxFlex.gt-sm="100" fxFlex.gt-xs="100" fxFlex="100">
                            <mat-form-field>
                                <input matInput sinEspacios type="text" formControlName="username" placeholder="Usuario" readonly>
                                <mat-error *ngIf="loginForm.hasError('required', ['username'])">Ingrese un nombre de usuario.</mat-error>
                            </mat-form-field>
                        </div>
                        <!-- col full-->
                        <div fxFlex.gt-sm="100" fxFlex.gt-xs="100" fxFlex="100">
                            <mat-form-field>
                                <input matInput type="password" autocomplete="false" formControlName="password" placeholder="Contraseña">
                                <mat-error *ngIf="loginForm.hasError('required', ['password'])">Ingrese una contraseña.</mat-error>
                            </mat-form-field>
                        </div>
                        <div fxFlex="100" class="content-center" *ngIf="this.isLoad">
                            <mat-spinner [diameter]="25"></mat-spinner>
                        </div>
                        <div fxFlex.gt-sm="100" fxFlex.gt-xs="100" fxFlex="100">
                            <button mat-raised-button color="accent" class="btn-block btn-lg m-t-20" type="submit" [disabled]="this.isLoad">Login</button>
                        </div>
                        <div fxFlex.gt-sm="100" fxFlex.gt-xs="100" fxFlex="100">
                            <button mat-stroked-button color="warn" class="btn-block btn-lg m-t-10" type="button" [disabled]="this.isLoad" (click)="this.logout()">Salir</button>
                        </div>
                    </div>
                </form>
            </mat-card-content>
        </form>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
        <app-custom-notification [notificationData]="notificationData"> </app-custom-notification>
    </ng-template>
  `
})

export class LoginComponent extends ClicComponent implements OnInit {
  public loginForm: FormGroup;
  public isLoad: boolean;
  constructor(private service: CommonService, private dialogRef: MatDialogRef<LoginComponent>, @Inject(MAT_DIALOG_DATA) private data: any,
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    const username = JSON.parse(localStorage.getItem(AUTH_DATA)).username;
    this.initialListener(this.changeDetector, this.media);
    this.notifierError({error: {title: 'Error Inesperado', detail: 'Tiempo de sesión finalizado.'}});
    this.isLoad = false;
    this.loginForm = new FormGroup({
      username: new FormControl(username, [Validators.required]),
      password: new FormControl(null, [Validators.required])
    });
  }

  loginSubmit() {
    this.service.requestLogin(this.loginForm.value).subscribe(response => {
      this.isLoad = false;
      localStorage.removeItem(AUTH_DATA);
      localStorage.setItem(AUTH_DATA, JSON.stringify(response.body));
      this.notifierError({error: {title: 'Iniciar sesión', detail: 'Se ha iniciado sesión correctamente.'}}, 'info');
      setTimeout(() => this.dialogRef.close(true), 200);
    }, error1 => {
      this.isLoad = false;
      if (error1) this.notifierError(error1);
    });
  }

  logout() {
    localStorage.removeItem(AUTH_DATA);
    this.dialogRef.close(false);
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {}

  onLgScreen() {}

  onMdScreen() {}

  onSmScreen() {}

  onXsScreen() {}

  setPage(pageInfo: Page) {}
}
