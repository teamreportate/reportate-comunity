import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {AuthUser} from '../../../core/models/AuthUser';
import {NotifierService} from 'angular-notifier';

@Component({
  selector: 'app-token-user-dialog',
  template: `
    <mat-card>
      <mat-card-header class="bg-primary-theme text-white"><mat-card-title>Token de Usuario</mat-card-title></mat-card-header>
        <mat-card-content>
          <div fxLayout="row wrap" [formGroup]="this.form">
            <div fxFlex="100">
              <mat-form-field>
                <textarea matInput placeholder="Token" formControlName="token" rows="3" #userinput readonly="true"></textarea>
                <button matTooltip="Copiar token" mat-icon-button matSuffix (click)="this.copyInputMessage(userinput)" >
                    <mat-icon>file_copy</mat-icon>
                </button>
              </mat-form-field>
            </div>
          </div>
          <mat-card-actions fxLayoutAlign="end center" fxLayoutGap="15px" >
            <button mat-stroked-button color="warn" type="button" (click)="this.cancel()">Cerrar</button>
          </mat-card-actions>
        </mat-card-content>
    </mat-card>
    `
})

export class TokenUserDialogComponent implements OnInit {

  public form: FormGroup;
  private notificacion: NotifierService;

  constructor(private notifier: NotifierService, private dialogRef: MatDialogRef<TokenUserDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: AuthUser) {
     this.notificacion = notifier;
  }

  ngOnInit() {
    this.form = new FormGroup({
      token: new FormControl(this.data),
    });
  }

  copyInputMessage(inputElement){
    inputElement.select();
    document.execCommand('copy');
    inputElement.setSelectionRange(0, 0);
    this.showNotification('Copiado al portapapeles');
  }

  showNotification(message: string ){
    this.notificacion.notify( 'default', message );
  }

  cancel() {
    this.dialogRef.close();
  }

}
