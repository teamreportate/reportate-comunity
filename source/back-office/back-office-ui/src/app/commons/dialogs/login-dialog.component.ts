/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    22-02-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AuthenticationService} from '../../core/services/http-services/authentication.service';
import {Authentication} from '../../authentication/shared/authentication';
import {ClicComponent} from 'src/app/core/utils/clic-component';
import {Page} from 'src/app/core/utils/paginator/page';
import {CustomOptions} from 'src/app/core/models/dto/custom-options';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {AUTH_DATA} from '../../../environments/environment';

@Component({
  selector: 'app-login-dialog',
  template:
    `
<mat-card>
  <mat-card-header class="header-component text-white">
    <mat-card-title>Confirmación de Usuario</mat-card-title>
  </mat-card-header>
  <form (submit)="this.onConfirmDialog()">
    <mat-card-content>
      <div fxLayout="row wrap">
        <div  fxFlex="100">
            <mat-form-field>
              <input matInput type='password' placeholder="Contraseña de Usuario" [formControl]="this.inputControl"/>
            </mat-form-field>
            <mat-hint *ngIf="this.inputControl.hasError('required') && this.inputControl.touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-hint>
        </div>
      </div>
    </mat-card-content>
    <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px">
      <button mat-stroked-button color="warn" type="button" (click)="this.exit()">Cancelar</button>
      <button mat-flat-button color="accent" type="submit" [disabled]="!this.inputControl.valid || this.loading">Confirmar</button>
    </mat-card-actions>
  </form>
</mat-card>
<ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"> </app-custom-notification>
</ng-template>
`
})
export class LoginDialogComponent extends ClicComponent implements OnInit{
  public inputControl: FormControl;
  public error: string;
  public loading: boolean = false;
  constructor(public dialogRef: MatDialogRef<LoginDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any, private authService: AuthenticationService,
  private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher)
  {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.inputControl = new FormControl(null, [Validators.required]);
  }

  exit() {
    this.dialogRef.close();
  }

  onConfirmDialog() {
    const data = JSON.parse(sessionStorage.getItem(AUTH_DATA));
    const body: Authentication = {username: data.username, password: this.inputControl.value, token: null, listaRecursos: null};
    this.loading = true;
    this.error = null;
    this.authService.requestStorageLogin(body).subscribe(response => {
      sessionStorage.setItem(AUTH_DATA, JSON.stringify(response.body));
      this.data.callback().subscribe(response => {
        this.loading = false;
        this.dialogRef.close(response.body);
      }, error1 => {
        this.loading = false;
        if(error1) this.notifierError(error1);
      });
    }, error1 => {
      this.loading = false;
      if (error1) this.notifierError(error1);
    });
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
