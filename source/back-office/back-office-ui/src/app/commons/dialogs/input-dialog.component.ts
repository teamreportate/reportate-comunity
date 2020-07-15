/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    22-02-19
 * author:  fmontero
 **/

import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ConfirmDialogComponent} from './confirm-dialog.component';

@Component({
  selector: 'app-confirm-dialog',
  template:
    `
<mat-card>
  <mat-card-header class="header-component text-white">
    <mat-card-title>{{this.textContent}}</mat-card-title>
    <!--<div  class="text-left">{{this.textContent}}</div>-->
  </mat-card-header>
  <form (submit)="this.onConfirmDialog()">
    <div fxLayout="row wrap">
      <div  fxFlex="100">
        <mat-card-content>
          <mat-form-field>
            <input matInput type='text' [placeholder]="this.placeholder" [formControl]="this.inputControl"/>
          </mat-form-field>
          <mat-hint *ngIf="this.inputControl.hasError('required') && this.inputControl.touched" class="text-danger font-11">Este campo es requerido.</mat-hint>
        </mat-card-content>
      </div>
    </div>
    <div fxLayout="row wrap">
      <div fxFlex="50" fxLayoutAlign="center">
        <button mat-flat-button [color]="'primary'" [type]="'submit'">{{this.btnConfirm}}</button>
      </div>
      <div fxFlex="50" fxLayoutAlign="center">
        <button mat-stroked-button [color]="'warn'" [type]="'button'" (click)="this.onCancelDialog()">{{this.btnCancel}}</button>
      </div>
    </div>
  </form>
</mat-card>
`
})
export class InputDialogComponent implements OnInit{
  /**
   * valor por defecto para el texto de contenido del dialogo de confirmacion
   * @type {string}
   */
  public textContent: string = 'Confirmar esta acción?';

  /**
   * valor por defecto para el texto del boton de confirmacion
   * @type {string}
   */
  public btnConfirm: string = 'CONFIRMAR';

  /**
   * valor por defecto para el texto del boton cancelar
   * @type {string}
   */
  public btnCancel: string = 'CANCELAR';

  public placeholder: string = 'Introduzca el valor';

  public inputControl: FormControl;

  /**
   *
   * @param {MatDialogRef<ConfirmDialogComponent>} dialogRef
   * @param data: contiene el texto personalizado ejmp. data: {textContent: 'Contenido personalizado', btnConfirm: 'Aceptar', btnCancel: 'Cerrar'}
   *              ningun parametro es obligatorio
   */
  constructor(public dialogRef: MatDialogRef<InputDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.textContent = this.data && this.data.textContent? this.data.textContent: this.textContent;
    this.btnConfirm = this.data && this.data.btnConfirm? this.data.btnConfirm: this.btnConfirm;
    this.btnCancel = this.data && this.data.btnCancel? this.data.btnCancel: this.btnCancel;
    this.placeholder = this.data && this.data.placeholder? this.data.placeholder: this.placeholder;
    this.inputControl = new FormControl('', [Validators.required]);
  }

  onCancelDialog(): void {
    this.dialogRef.close();
  }

  onConfirmDialog(): void {
    if(this.inputControl.valid)
      this.dialogRef.close(this.inputControl.value);
  }

}
