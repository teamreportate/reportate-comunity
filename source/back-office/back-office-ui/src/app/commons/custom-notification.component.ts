import {Component, Input} from '@angular/core';
import {CustomOptions} from '../core/models/dto/custom-options';

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    14-09-19
 * author:  fmontero
 **/

@Component({
  selector: 'app-custom-notification',
  template: `
    <div fxLayout="row wrap" class="cursor-body">
      <div fxFlex="100"  class="notification-title">{{ notificationData.tile }}</div>
      <div fxFlex="100">{{ notificationData.message }}</div>
    </div>
  `,
  styles: [
    `
      .notification-title{
        text-align: left; 
        font-size: 15px; 
        font-weight: bold
      }
      
      .cursor-body {
        cursor: pointer;
        max-width: 650px
      }
    `
  ]
})
export class CustomNotificationComponent {
  @Input() notificationData: CustomOptions;
}
