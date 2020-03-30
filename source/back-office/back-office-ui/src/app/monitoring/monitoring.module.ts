import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {MonitoringRoutes} from './monitoring.routing';
import {BinnacleComponent} from './binnacle/binnacle.component';
import {
  MatButtonModule,
  MatCardModule,
  MatDatepickerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatSelectModule
} from '@angular/material';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {CommonsModule} from '../commons/commons.module';

@NgModule({
  declarations: [BinnacleComponent],
  imports: [
    CommonModule,
    CommonsModule,
    MatInputModule,
    MatCardModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDatepickerModule,
    MatIconModule,
    MatButtonModule,

    FlexModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    MatProgressSpinnerModule,
    RouterModule.forChild(MonitoringRoutes)
  ]
})
export class MonitoringModule { }
