/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    19-08-19
 * author:  fmontero
 **/


import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../core/utils/clic-component';
import {Page} from '../core/utils/paginator/page';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {JobService} from '../core/services/http-services/job.service';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../core/models/dto/custom-options';

@Component({
  selector: 'app-cron-maker',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Editar Expresión CRON</mat-card-title></mat-card-header>
      <mat-card-content>
        <form (submit)="this.editCronExpression()">
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <input matInput placeholder="EXPRESION CRON" formControlName="expresion">
                <mat-error *ngIf="this.form.get('expresion').hasError('required') && this.form.get('expresion').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                <mat-error *ngIf="this.form.get('expresion').hasError('minlength') && this.form.get('expresion').touched" class="text-danger font-11">Se requiere al menos <strong>8 caracteres</strong>.</mat-error>
              </mat-form-field>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
            <button mat-flat-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
            <button mat-flat-button color="accent" type="submit" [disabled]="this.load" (click)="this.editCronExpression()">Guardar</button>
          </mat-card-actions>
        </form>
      </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
        <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>

  `,
  providers: [JobService]
})

export class CronMakerDialogComponent extends ClicComponent implements OnInit {
  public form: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public error: string;
  constructor(private dialogRef: MatDialogRef<CronMakerDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
              private jobService: JobService, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.confirm = true;
    this.load = false;
    this.error = null;
    this.form = new FormGroup({
      expresion: new FormControl(this.data.current, Validators.compose([Validators.required, Validators.minLength(8)])),
    });
  }

  editCronExpression() {
    if (this.form.valid) {
      this.load = true;
      this.error = null;
      this.jobService.requestvalidateCron(this.form.get('expresion').value).subscribe(response => {
        const valid: boolean = response.body;
        if (valid) {
          this.data.callback(this.form.get('expresion').value).subscribe(response => {
            this.dialogRef.close(response.body);
          }, error => {
            this.load = false;
            if(error) this.notifierError(error);
          });
        } else {
          this.load = false;
          const notif = {error: {title: 'Validación de expresión CRON', detail: 'La expresión CRON es inválida'}};
          this.notifierError(notif);
          return;
        }
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
