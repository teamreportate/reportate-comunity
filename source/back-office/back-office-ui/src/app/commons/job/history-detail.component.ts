import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {ClicComponent} from '../../core/utils/clic-component';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {Page} from '../../core/utils/paginator/page';

@Component({
  selector: 'app-invoice-errors-dialog',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white">
        <mat-card-title>Detalles del Proceso</mat-card-title>
      </mat-card-header>
      <mat-dialog-content>
        <div fxLayout="colum wrap" class="h-padding-10 m-5" style="background-color: #f2f2f2; border-radius: 4px">
          <div fxFlex="100">
            <span>Grupo del Job: <strong>{{this.history.groupName}}</strong></span>
          </div>
            <div fxFlex="100">
                <span>Nombre del Job: <strong>{{this.history.jobName}}</strong></span>
            </div>
          <div fxFlex="100">
            <span>Inicio de Ejecución: <strong>{{this.history.fechaInicio | date: 'dd/MM/yyyy, H:mm'}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>Fecha de envío: <strong>{{this.history.fechaFin ? (this.history.fechaFin | date: 'dd/MM/yyyy, H:mm') : 'sin fecha'}}</strong></span>
          </div>
        </div>
        <div fxLayout="column wrap" class="h-padding-10" *ngIf="!this.errors">
          <div fxFlex="100" class="p-t-10 p-b-10">
            <strong class="text-center">Sin errores que mostrar</strong>
          </div>
        </div>
        <div fxLayout="column wrap" class="h-padding-10" *ngIf="this.errors">
          <div fxFlex="100" class="p-t-5" *ngFor="let item of this.errors">
            <div fxLayout="row wrap" style="border: 1px solid #fc4b6c; border-radius: 4px">
              <div fxFlex="25">
                <div fxLayout="column wrap">
                  <div fxFlex="100" fxLayoutAlign="center center" style="background-color: rgba(244,94,94,0.07); height: 20px; text-align: justify; border-radius: 4px; font-size: 12px"><strong style="margin: 2px">Codigo de error</strong></div>
                  <div fxFlex="100" fxLayoutAlign="center center" style="background-color: rgba(244,94,94,0.07); height: 50px;"><span style="text-align: justify; font-size: 15px">{{item.codigo}}</span></div>
                </div>
              </div>
              <div fxFlex="75" style="text-align: justify; font-size: 12px">
                <span style="margin: 10px">{{item.mensaje}}</span>
              </div>
            </div>
          </div>
        </div>
        <div fxLayout="column wrap" class="h-padding-10" *ngIf="this.request">
            <div fxFlex="100" class="p-t-5 text-center"><strong>XML de Solicitud SIN</strong></div>
            <div fxFlex="100">
                <textarea style="" class="form-control clipboard tareaxml" id="xml-request" name="xml-request" cols="180" rows="16" [readonly]="true">{{this.request.requestXml}}</textarea>
            </div>
            <div fxFlex="100" class="p-t-5 text-center"><strong>XML de Respuesta SIN</strong></div>
            <div fxFlex="100">
                <textarea class="form-control clipboard tareaxml" id="xml-response" name="xml-response" cols="180" rows="10" [readonly]="true">{{this.request.response.responseXml}}</textarea>
            </div>
        </div>
      </mat-dialog-content>
      <mat-card-content fxLayoutAlign="end center">
        <button mat-flat-button color="accent" (click)="this.dialogRef.close()">Cerrar</button>
      </mat-card-content>
    </mat-card>
  `,
  styles: [
    `
    .tareaxml {
      border-radius: 4px;
      font-size: 12px; font-weight: 600; background-color: #f7f6f2
    }
    `
  ]
})

export class HistoryDetailComponent extends ClicComponent implements OnInit {
  public history: any;
  public request: any;
  public errors: {codigo: any, mensaje: any}[];
  constructor(public dialogRef: MatDialogRef<HistoryDetailComponent>, @Inject(MAT_DIALOG_DATA) private data: {history: any, request: any},
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.history = this.data.history;
    this.request = this.data.request;
    try {
      this.errors = this.history.codigosErrores && this.history.codigosErrores.length > 0 ? JSON.parse(this.history.codigosErrores) : null;
    } catch (e) {
      this.errors = null;
    }
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
