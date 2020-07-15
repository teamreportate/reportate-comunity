/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-03-19
 * author:  fmontero
 **/
import {Component, Inject, OnInit} from '@angular/core';
import {MAT_SNACK_BAR_DATA} from '@angular/material';

@Component({
  selector: 'app-success',
  template:
      `<div fxLayout="row wrap">
        <div fxFlex="90" class="text-center">
          {{this.customMessage}}
        </div>
        <div fxFlex="10">
          <mat-icon>check_circle</mat-icon>
        </div>
      </div>`
})
export class SuccessComponent implements OnInit {
  public customMessage: string;
  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: any) { }

  ngOnInit() {
    this.customMessage = this.data && this.data.message? this.data.message : 'Procesa satisfactorio';
  }

}
