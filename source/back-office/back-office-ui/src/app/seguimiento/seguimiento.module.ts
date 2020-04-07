import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
// tslint:disable-next-line:max-line-length
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRippleModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatTabsModule,
  MatTooltipModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {CommonsModule} from '../commons/commons.module';
import {SeguimientoRoute} from './seguimiento.routes';
import {SeguimientoEnfermedadComponent} from './seguimiento-enfermedad/seguimiento-enfermedad.component';
import { SharedModule } from '../shared/shared.module';
import { FichaEpidemiologicaComponent } from './seguimiento-enfermedad/ficha-epidemiologica/ficha-epidemiologica.component';

@NgModule({
  declarations: [SeguimientoEnfermedadComponent, FichaEpidemiologicaComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(SeguimientoRoute),
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatDialogModule,
    MatListModule,
    MatCheckboxModule,
    MatSelectModule,
    MatTabsModule,
    MatRippleModule,
    MatSlideToggleModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    FlexLayoutModule,
    FlexModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    DragDropModule,
    CommonsModule,
    SharedModule
  ],
  providers: []
})
export class SeguimientoModule { }
