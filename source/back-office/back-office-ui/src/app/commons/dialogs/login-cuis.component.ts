import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-login-siat-component',
  template: `
      <mat-card>
          <mat-card-header class="header-component text-white">
              <mat-card-title>Credenciales SIAT</mat-card-title>
          </mat-card-header>
          <mat-card-content>
            <form (submit)="this.submit()">
                <div fxLayout="row wrap"  [formGroup]="this.form">
                    <div fxFlex="100">
                        <mat-form-field>
                            <input matInput type='text' placeholder="Usuario Para Solicitar CUIS" formControlName="user"/>
                        </mat-form-field>
                    </div>
                    <div  fxFlex="100">
                        <mat-form-field>
                            <input matInput type='text' placeholder="Contraseña Para Solicitar CUIS" formControlName="pass"/>
                        </mat-form-field>
                    </div>
                </div>
                <div fxLayout="row wrap">
                    <div fxFlex="100" fxLayoutAlign="end center" fxLayoutGap="5px">
                        <button mat-stroked-button [color]="'warn'" [type]="'button'" (click)="this.dialogRef.close()">Cancelar</button>
                        <button mat-flat-button [color]="'accent'" [disabled]="!this.form.valid" [type]="'submit'">Aceptar</button>
                    </div>
                </div>
            </form>
          </mat-card-content>
      </mat-card>
  `
})

export class LoginCuisComponent implements OnInit {
  public form: FormGroup;
  constructor(public dialogRef: MatDialogRef<LoginCuisComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {
    this.form = new FormGroup({
      user: this.data.user ? new FormControl(this.data.user, Validators.required) : new FormControl(null, Validators.required),
      pass: this.data.pass ? new FormControl(this.data.pass, Validators.required) : new FormControl(null, Validators.required),
    });
  }

  submit() {
    if (this.form.valid) {
      this.dialogRef.close(this.form.value);
    }
  }
}
