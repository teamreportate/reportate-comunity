import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, Validators} from '@angular/forms';

/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    22-02-19
 * author:  fmontero
 **/

@Component({
  selector: 'app-confirm-generacion-huella',
  template:
      `<mat-card>
        <mat-card-header class="header-component text-white">
          <mat-card-title>Generación de Huellas</mat-card-title>    
        </mat-card-header>
        <mat-card-content>
          <form (submit)="this.onConfirmDialog()">
            <div fxLayout="row wrap">
              <div  fxFlex="100">
                <mat-card-content>
                  <mat-form-field>
                    <!--<input matInput type='text' [placeholder]="this.placeholder" [formControl]="this.inputControl" />-->

                    <input matInput [placeholder]="this.placeholder" sinEspacios [type]="ocultar ? 'password' : 'text'" [formControl]="this.inputControl" maxlength="255" autocomplete="off">
                    <button mat-icon-button color="accent" matSuffix (click)="ocultar = !ocultar" [attr.aria-pressed]="ocultar" type="button">
                      <mat-icon>{{ocultar ? 'visibility_off' : 'visibility'}}</mat-icon>
                    </button>
                  </mat-form-field>
                  <mat-hint *ngIf="this.inputControl.hasError('required') && this.inputControl.touched" class="text-danger font-11">Este campo es requerido.</mat-hint>
                </mat-card-content>
              </div>
            </div>
            <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="15px" >
              <button mat-stroked-button [color]="'warn'" [type]="'button'" (click)="this.onCancelDialog()">{{this.btnCancel}}</button>
              <button mat-flat-button [color]="'primary'" [type]="'submit'">{{this.btnConfirm}}</button>
            </mat-card-actions>
            <!--<div fxLayout="row wrap">-->
              <!--<div fxFlex="50" fxLayoutAlign="center">-->
                <!--<button mat-flat-button [color]="'primary'" [type]="'submit'">{{this.btnConfirm}}</button>-->
              <!--</div>-->
              <!--<div fxFlex="50" fxLayoutAlign="center">-->
                <!--<button mat-flat-button [color]="'warn'" [type]="'button'" (click)="this.onCancelDialog()">{{this.btnCancel}}</button>-->
              <!--</div>-->
            <!--</div>-->
          </form>
        </mat-card-content>
      </mat-card>`
})
export class ConfirmGeneracionHuellaComponent implements OnInit{
  /**
   * valor por defecto para el texto de contenido del dialogo de confirmacion
   * @type {string}
   */
  public textContent: string = 'Confirmar esta acción?';

  /**
   * valor por defecto para el texto del boton de confirmacion
   * @type {string}
   */
  public btnConfirm: string = 'Generar';

  /**
   * valor por defecto para el texto del boton cancelar
   * @type {string}
   */
  public btnCancel: string = 'Cancelar';

  public placeholder: string = 'Ingrese valor de login';

  public inputControl: FormControl;

  public ocultar = true;

  /**
   * @void configuracion de valores customizados
   */

  /**
   *
   * @param {MatDialogRef<ConfirmDialogComponent>} dialogRef
   * @param data: contiene el texto personalizado ejmp. data: {textContent: 'Contenido personalizado', btnConfirm: 'Aceptar', btnCancel: 'Cerrar'}
   *              ningun parametro es obligatorio
   */
  constructor(public dialogRef: MatDialogRef<ConfirmGeneracionHuellaComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.textContent = this.data && this.data.textContent? this.data.textContent: this.textContent;
    this.btnConfirm = this.data && this.data.btnConfirm? this.data.btnConfirm: this.btnConfirm;
    this.btnCancel = this.data && this.data.btnCancel? this.data.btnCancel: this.btnCancel;
    this.placeholder = this.data && this.data.placeholder? this.data.placeholder: this.placeholder;
    this.inputControl = new FormControl('', [Validators.required]);
  }

  onCancelDialog = () => this.dialogRef.close();

  onConfirmDialog() {
    if(this.inputControl.valid)
      this.dialogRef.close(this.inputControl.value);
  }


}
