/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    26-08-19
 * author:  fmontero
 **/


import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../../core/utils/clic-component';
import {Page} from '../../../core/utils/paginator/page';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, Validators} from '@angular/forms';
import {SettingsService} from '../../settings.service';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';

@Component({
  selector: 'app-user-principal',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Usuario Principal de Alarma</mat-card-title></mat-card-header>
      <mat-card-content>
        <div fxLayout="row wrap">
          <div fxFlex="100">
            <mat-form-field>
              <mat-select placeholder="Usuario Principal" [formControl]="this.control">
                <mat-option *ngFor="let user of this.data.users" [value]="user.idSfeUsuario.id">
                  {{user.idSfeUsuario.username}}
                </mat-option>
              </mat-select>
            </mat-form-field>
          </div>
          <div fxFlex="100" fxLayoutAlign="center center" *ngIf="this.load">
            <mat-spinner [diameter]="30"></mat-spinner>
          </div>
        </div>
        <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
          <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
          <button mat-flat-button color="accent" type="button" [disabled]="this.load" (click)="this.updatePrincipal()">Guardar</button>
        </mat-card-actions>
      </mat-card-content>
    </mat-card>
  `
})

export class UserPrincipalComponent extends ClicComponent implements OnInit {
  public control: FormControl;
  public load: boolean;
  constructor(private notifier: NotifierService, private settingService: SettingsService, private dialogRef: MatDialogRef<UserPrincipalComponent>, @Inject(MAT_DIALOG_DATA) public data: {users: any, alarmId: number}, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.control = new FormControl(null, Validators.required);
    this.load = false;
  }

  cancel() {
    this.dialogRef.close(false);
  }

  updatePrincipal() {
    this.load = true;
    this.settingService.requestUpdatePrincipal(this.data.alarmId, this.control.value).subscribe(response => {
      this.dialogRef.close(true);
      this.load = false;
    }, error1 => {
      this.load = false;
    });
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

  setPage(pageInfo: Page) {
  }

}
