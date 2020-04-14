import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {PrincipalComponent} from './principal/principal.component';
import {MatCardModule, MatProgressSpinnerModule} from '@angular/material';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {DemoMaterialModule} from './demo-material-module';
import {ChartistModule} from 'ng-chartist';
import {ChartsModule} from 'ng2-charts';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {DashboardsRoutes} from './dashboards.routing';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonsModule} from '../commons/commons.module';
import { ResumeComponent } from './resume/resume.component';
import { SospechosoComponent } from './sospechoso/sospechoso.component';
import { ConfirmadoComponent } from './confirmado/confirmado.component';
import { RecuperadosComponent } from './recuperados/recuperados.component';
import { FallecidosComponent } from './fallecidos/fallecidos.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [PrincipalComponent, ResumeComponent, SospechosoComponent, ConfirmadoComponent, RecuperadosComponent, FallecidosComponent],
  imports: [
    CommonModule,
    CommonsModule,
    RouterModule.forChild(DashboardsRoutes),
    DemoMaterialModule,
    MatCardModule,
    MatProgressSpinnerModule,
    FlexModule,
    FlexLayoutModule,
    ChartistModule,
    FormsModule,
    ReactiveFormsModule,
    ChartsModule,
    NgxDatatableModule,
    SharedModule
  ]
})
export class DashboardsModule { }
