/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    31-01-19
 * author:  fmontero
 **/
import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material';


@Component({
  selector: 'app-logout-prompt',
  template: `
    <h3 mat-dialog-title>Salir</h3>

    <mat-dialog-content>
      Salir de CLIC?
    </mat-dialog-content>

    <mat-dialog-actions>
      <button mat-stroked-button (click)="cancel()">
        No
      </button>
      <button mat-flat-button (click)="confirm()">
        Si
      </button>
    </mat-dialog-actions>
  `,
  styleUrls: ['./prompt-logout.scss']
})
export class PromptLogout {
  constructor(private ref: MatDialogRef<PromptLogout>) {}

  cancel() { this.ref.close(false); }

  confirm() { this.ref.close(true); }
}
