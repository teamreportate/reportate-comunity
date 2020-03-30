/**
 *Created by :Leonardo Bozo Ramos
 *Date       :19/7/2019
 *Project    :unicoWeb
 *Package    :
 */
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FooterComponent} from './component/footer.component';

@NgModule({
  declarations: [FooterComponent],
  imports: [
    CommonModule,
    FlexLayoutModule
  ],
  exports: [FooterComponent],
  entryComponents: [],
  providers: []
})
export class FooterModule {
}
