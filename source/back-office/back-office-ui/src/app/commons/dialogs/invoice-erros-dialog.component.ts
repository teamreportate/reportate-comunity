/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    21-08-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';


import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {ClicComponent} from '../../core/utils/clic-component';
import {InvoiceDocument} from '../../core/models/invoice-document';
import {Page} from '../../core/utils/paginator/page';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';


@Component({
  selector: 'app-invoice-errors',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white">
        <mat-card-title>ERRORES DE DOCUMENTO FISCAL</mat-card-title>
      </mat-card-header>
      <mat-dialog-content>
        <div fxLayout="colum wrap" class="h-padding-10 m-5" style="background-color: #f2f2f2; border-radius: 4px">
          <div fxFlex="100">
            <span>Factura Nro: <strong>{{this.invoice.numeroFactura}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>Fecha de emisión: <strong>{{this.invoice.fechaEmision | date: 'dd/MM/yyyy, H:mm'}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>Fecha de envío: <strong>{{this.invoice.fechaEnvio ? (this.invoice.fechaEnvio | date: 'dd/MM/yyyy, H:mm') : 'sin fecha'}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>NIT Emisor: <strong>{{this.invoice.nitEmisor}}</strong></span>
          </div>
        </div>
        <div fxLayout="colum wrap" class="h-padding-10 m-5" style="background-color: #f2f2f2; border-radius: 4px">
          <div fxFlex="100">
            <span>Tipo de emisión: <strong>{{this.invoice.tipoEmision}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>Modalidad: <strong>{{this.invoice.modalidad}}</strong></span>
          </div>
          <div fxFlex="100">
            <span>Método de pago: <strong>{{this.invoice.metodoPago.descripcionSin}}</strong></span>
          </div>
        </div>
        <div fxLayout="colum wrap" class="h-padding-10 m-5" style="background-color: #f2f2f2; border-radius: 4px;">
          <div fxFlex="100">
            <span>Razon Social: <strong>{{this.invoice.nombreRazonSocial}}</strong></span>
          </div>
        </div>

        <div fxLayout="column wrap" class="h-padding-10" *ngIf="!this.errors">
          <div fxFlex="100" class="p-t-5 text-center">
            Sin errores que mostrar
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
      </mat-dialog-content>
    </mat-card>
  `
})

export class InvoiceErrosDialogComponent extends ClicComponent implements OnInit {
  public invoice: InvoiceDocument;
  public errors: {codigo: any, mensaje: any}[];
  constructor(private dialogRef: MatDialogRef<InvoiceErrosDialogComponent>, @Inject(MAT_DIALOG_DATA) private data: {invoice: InvoiceDocument},
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.invoice = this.data.invoice;
    try {
      this.errors = this.invoice.codigosErroresSin && this.invoice.codigosErroresSin.length > 0 ? JSON.parse(this.invoice.codigosErroresSin) : null;
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
