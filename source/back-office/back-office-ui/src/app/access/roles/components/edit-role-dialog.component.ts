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
import {AuthRole} from '../../../core/models/auth-role';
import {ClicComponent} from 'src/app/core/utils/clic-component';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from 'src/app/core/utils/paginator/page';
import {CustomOptions} from 'src/app/core/models/dto/custom-options';

@Component({
  selector: 'app-edit-role',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Actualizar Rol</mat-card-title></mat-card-header>
        <mat-card-content>
          <form (submit)="this.editRol()">
            <div fxLayout="row wrap" [formGroup]="this.form">
              <div fxFlex="100">
                <mat-form-field>
                  <input matInput placeholder="Nombre de Rol" formControlName="nombre" maxlength="50">
                  <mat-hint align="end">{{this.form.get('nombre').value? this.form.get('nombre').value.length:0}} / 50</mat-hint>
                  <mat-error *ngIf="this.form.get('nombre').errors?.pattern && this.form.get('nombre').touched" class="text-danger font-11">solo se permiten caracteres alfanumericos y guion bajo (_).</mat-error>
                  <mat-error *ngIf="this.form.get('nombre').hasError('required') && this.form.get('nombre').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('nombre').hasError('maxlength') && this.form.get('nombre').touched" class="text-danger font-11">Solo se permite <strong>50 caracteres</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('nombre').hasError('minlength') && this.form.get('nombre').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
                </mat-form-field>
              </div>
    
              <div fxFlex="100">
                <mat-form-field>
                  <input matInput placeholder="Descripción del Rol" formControlName="descripcion" maxlength="255">
                  <mat-hint align="end">{{this.form.get('descripcion').value? this.form.get('descripcion').value.length:0}} / 255</mat-hint>
                  <mat-error *ngIf="this.form.get('descripcion').hasError('required') && this.form.get('descripcion').touched" class="text-danger font-11">Este campo es <strong>requerido</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('descripcion').hasError('maxlength') && this.form.get('descripcion').touched" class="text-danger font-11">Solo se permite <strong>255 caracteres</strong>.</mat-error>
                  <mat-error *ngIf="this.form.get('descripcion').hasError('minlength') && this.form.get('descripcion').touched" class="text-danger font-11">Se requiere al menos <strong>5 caracteres</strong>.</mat-error>
                </mat-form-field>
              </div>
            </div>
            <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px" >
              <button mat-stroked-button color="warn" type="button" [disabled]="this.load" (click)="this.cancel()">Cancelar</button>
              <button mat-flat-button color="accent" type="submit" [disabled]="this.load" (click)="this.editRol()">Guardar</button>
            </mat-card-actions>
          </form>
        </mat-card-content>
    </mat-card>
    <ng-template #customNotification let-notificationData="notification">
      <app-custom-notification [notificationData]="notificationData"></app-custom-notification>
    </ng-template>
  `
})

export class EditRoleDialogComponent extends ClicComponent implements OnInit {
  public form: FormGroup;
  public confirm: boolean;
  public load: boolean;
  public error: string;
  constructor(private accessService: AccessService, private dialogRef: MatDialogRef<EditRoleDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: AuthRole,
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
      nombre: new FormControl(this.data.nombre, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'), Validators.maxLength(50), Validators.minLength(5)])),
      descripcion: new FormControl(this.data.descripcion, Validators.compose([Validators.required, Validators.maxLength(255), Validators.minLength(5)])),
    });
  }

  editRol() {
    this.error = null;
    if (this.form.valid) {
      const rol: AuthRole = this.form.value;
      this.load = true;
      this.accessService.requestRoleEdit(rol, String(this.data.id)).subscribe(response => {
        this.dialogRef.close(response.body);
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
