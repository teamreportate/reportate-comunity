import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';


/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    22-02-19
 * author:  fmontero
 **/

@Component({
  selector: 'app-confirm-dialog',
  template:
      `<mat-card>
        <mat-card-header class="header-component text-white">
          <mat-card-title>{{this.title}}</mat-card-title>
        </mat-card-header>
        <mat-card-content>
          <form (submit)="this.onConfirmDialog()">
            <div fxLayout="row wrap">
              <div  fxFlex="100">
                <h4 class="text-center">{{this.textContent}}</h4>
              </div>
              <div fxFlex="100">
                <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="5px">
                  <button mat-stroked-button color="warn" [type]="'button'" (click)="this.onCancelDialog()">{{this.btnCancel}}</button>
                  <button mat-flat-button color="accent" [type]="'submit'">{{this.btnConfirm}}</button>
                </mat-card-actions>
              </div>
            </div>
          </form>
        </mat-card-content>
      </mat-card>`
})
export class ConfirmDialogComponent implements OnInit{
  /**
   * valor por defecto para el texto de contenido del dialogo de confirmacion
   * @type {string}
   */
  public textContent: string = '¿Confirmar esta acción?';

  /**
   * valor por defecto para el texto del boton de confirmacion
   * @type {string}
   */
  public btnConfirm: string = 'Confirmar';

  /**
   * valor del titulo
   * @type {string}
   */
  public title: string = 'Confirmación';

  /**
   * valor por defecto para el texto del boton cancelar
   * @type {string}
   */
  public btnCancel: string = 'Cancelar';

  /**
   * @void configuracion de valores customizados
   */

  /**
   *
   * @param {MatDialogRef<ConfirmDialogComponent>} dialogRef
   * @param data: contiene el texto personalizado ejmp. data: {textContent: 'Contenido personalizado', btnConfirm: 'Aceptar', btnCancel: 'Cerrar'}
   *              ningun parametro es obligatorio
   */
  constructor(public dialogRef: MatDialogRef<ConfirmDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  onConfirmDialog() {
    this.dialogRef.close(true);
  }

  onCancelDialog() {
    this.dialogRef.close(false);
  }

  ngOnInit(): void {
    this.textContent = this.data && this.data.textContent? this.data.textContent: this.textContent;
    this.btnConfirm = this.data && this.data.btnConfirm? this.data.btnConfirm: this.btnConfirm;
    this.btnCancel = this.data && this.data.btnCancel? this.data.btnCancel: this.btnCancel;
    this.title = this.data && this.data.title? this.data.title: this.title;
  }

}
