/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    04-07-19
 * author:  fmontero
 **/

import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'wservice-card',
  template: `
    <div class="content-body">
      <div class="header-component text-white">
        <div class="text-white text-center bold">Servicio Web - {{wServiceName}}</div>
      </div>
      <mat-card-content>
        <div fxLayout="row wrap" class="h-padding-5">
          <div fxFlex="100">
            <mat-form-field>
              <input matInput placeholder="URL de Servicio Web {{wServiceName | uppercase}}" [formControl]="this.form.get('rutaServicio')" maxlength="500">
                <mat-hint align="end">{{this.form.get('rutaServicio').value? this.form.get('rutaServicio').value.length:0}} / 500</mat-hint>
              <mat-error *ngIf="this.form.get('rutaServicio').hasError('required') && this.form.get('rutaServicio').touched" class="text-danger font-11">Proporcione una url para el servicio.</mat-error>
            </mat-form-field>
          </div>
          <div fxFlex="50" class="p-r-5">
            <mat-form-field>
              <input matInput type="number" min="1000" placeholder="Connection Timeout (milisegundos)" [formControl]="this.form.get('connectTimeOut')">
              <mat-error *ngIf="this.form.get('connectTimeOut').hasError('required') && this.form.get('connectTimeOut').touched" class="text-danger font-11">Proporciene un valor de connect timeout.</mat-error>
              <mat-error *ngIf="this.form.get('connectTimeOut').hasError('pattern') && this.form.get('connectTimeOut').touched" class="text-danger font-11">Solo se permiten dígitos.</mat-error>
              <mat-error *ngIf="this.form.get('connectTimeOut').hasError('max') && this.form.get('connectTimeOut').touched" class="text-danger font-11">El valor máximo permitido es 90000ms.</mat-error>
              <mat-error *ngIf="this.form.get('connectTimeOut').hasError('min') && this.form.get('connectTimeOut').touched" class="text-danger font-11">El valor mínimo permitido es 1000ms.</mat-error>
            </mat-form-field>
          </div>
          <div fxFlex="50" class="p-l-5">
            <mat-form-field>
              <input matInput type="number" min="1000" placeholder="Read Timeout (milisegundos)" [formControl]="this.form.get('readTimeOut')">
              <mat-error *ngIf="this.form.get('readTimeOut').hasError('required') && this.form.get('connectTimeOut').touched" class="text-danger font-11">Proporciene un valor de connect timeout.</mat-error>
              <mat-error *ngIf="this.form.get('readTimeOut').hasError('pattern') && this.form.get('connectTimeOut').touched" class="text-danger font-11">Solo se permiten dígitos.</mat-error>
              <mat-error *ngIf="this.form.get('readTimeOut').hasError('max') && this.form.get('connectTimeOut').touched" class="text-danger font-11">El valor máximo permitido es 90000ms.</mat-error>
              <mat-error *ngIf="this.form.get('readTimeOut').hasError('min') && this.form.get('connectTimeOut').touched" class="text-danger font-11">El valor mínimo permitido es 1000ms.</mat-error>
            </mat-form-field>
          </div>
        </div>
        <div fxLayout="row wrap" class="h-padding-5">
          <div fxFlex="100" fxLayoutAlign="start center" fxLayoutGap="5px">
            <button [disabled]="!this.form.valid || this.isCalled" mat-flat-button color="accent" type="button" (click)="this.testWService()">Validar Servicio</button>
            <mat-spinner *ngIf="this.isCalled" [diameter]="30"></mat-spinner>
            <div *ngIf="this.finalize">
                <div *ngIf="this.success" class="request-success-message"><span class="text-white" >Validación de servicio <strong>'{{this.message | deleteUnderline}}'</strong> exitoso</span></div>
                <div *ngIf="!this.success" class="request-error-message"><span class="text-white">{{this.message | deleteUnderline}}</span></div>
            </div>
          </div>
        </div>
      </mat-card-content>
    </div>
  `
})
export class WserviceCardComponent implements OnInit {
  @Input() public wServiceName: string;
  @Input() public form: FormGroup;
  @Input() public callback: any;
  @Input() public tag: any;
  @Output() receiver?: EventEmitter<any> = new EventEmitter<any>();
  public isCalled: boolean = false;
  public finalize: boolean = false;
  public success: boolean = false;
  public message: string;
  constructor() {}
  ngOnInit() {
    this.isCalled = false;
    this.finalize = false;
    this.success = false;
  }

  testWService() {
    this.isCalled = true;
    this.finalize = false;
    this.callback(this.form.value, this.tag).subscribe(response => {
      this.isCalled = false;
      this.finalize = true;
      this.success = response.body.status;
      this.message = response.body.message;
      this.receiver.emit(this.success);
    }, error => {
      this.finalize = true;
      this.success = false;
      this.isCalled = false;
    });
  }
}
