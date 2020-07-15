/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    06-06-19
 * author:  fmontero
 **/
import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Location} from '@angular/common';

@Component({
  selector: 'app-header-page',
  template: `
    <div class="header-page-component text-accent">
      <div fxLayout="row wrap" class="h-padding-5">
        <div fxFlex="10" fxLayoutAlign="start center"><button mat-icon-button fxLayoutAlign="start center" (click)="this.location.back()" ><mat-icon class="header-icons">arrow_back</mat-icon></button></div>
        <div fxFlex="80" fxLayoutAlign="center center"><div class="header-text-page">{{this.title}}</div></div>
        <div fxFlex="10" fxLayoutAlign="end center"><button mat-icon-button fxLayoutAlign="center center" (click)="this.reloadEmitter.emit()"><mat-icon class="header-icons">sync</mat-icon></button></div>
      </div>
    </div>
  `,
  providers: [Location]
})
export class HeaderPageComponent {
  @Input() public title: string;
  @Output() public reloadEmitter: EventEmitter<any> = new EventEmitter<any>();
  constructor(public location: Location) {}
}
