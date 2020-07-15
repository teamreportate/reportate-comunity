/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ParameterComponent} from './parameter/parameter.component';
import {DomainComponent} from './domain/domain.component';

import {
  MatBadgeModule,
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatOptionModule,
  MatPaginatorModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSlideToggleModule,
  MatTableModule,
  MatTabsModule,
  MatTooltipModule
} from '@angular/material';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {DomainService} from '../core/services/http-services/domain.service';
import {CommonsModule} from '../commons/commons.module';
import {SettingsRoutes} from './settings.routing';
import {RouterModule} from '@angular/router';
import {AlarmComponent} from './alarm/alarm.component';
import {AlarmUserDialog, AlarmUsersComponent} from './alarm/components/alarm-users.component';
import {AlarmEditComponent, ListDialog} from './alarm/components/alarm-edit.component';
import {JwtHelperService} from '@auth0/angular-jwt';
import {MenuBuilderService} from '../core/services/util-services/menu-builder.service';
import {SettingsService} from './settings.service';
import {UserPrincipalComponent} from './alarm/components/user-principal.component';
import {DomainCreateComponent} from './domain-create/domain-create.component';
import {DomainValueDialogComponent} from './domain/components/domain-value-dialog.component';
import {DomainDialogComponent} from './domain/components/domain-dialog.component';
import {ParameterDialogComponent} from './parameter/components/parameter-dialog.component';

const menuBuilder: MenuBuilderService = new MenuBuilderService(new JwtHelperService());

@NgModule({
  declarations: [
    ParameterComponent,
    ParameterDialogComponent,
    DomainComponent,
    DomainCreateComponent,
    DomainValueDialogComponent,
    DomainDialogComponent,
    AlarmComponent,
    AlarmUsersComponent,
    AlarmEditComponent,
    ListDialog,
    AlarmUserDialog,
    UserPrincipalComponent
  ],
  imports: [
    CommonModule,
    CommonsModule,
    MatCardModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatTableModule,
    MatTabsModule,
    MatPaginatorModule,
    MatInputModule,
    MatSlideToggleModule,
    MatChipsModule,
    MatIconModule,
    MatMenuModule,
    MatDialogModule,
    MatButtonModule,
    MatTooltipModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatCheckboxModule,
    MatBadgeModule,
    FormsModule,
    ReactiveFormsModule,
    FlexModule,
    FlexLayoutModule,
    NgxDatatableModule,
    MatOptionModule,
    MatSelectModule,
    RouterModule.forChild(SettingsRoutes)
  ],
  providers: [DomainService, SettingsService],
  entryComponents: [
    ListDialog,
    AlarmUserDialog,
    UserPrincipalComponent,
    DomainValueDialogComponent,
    DomainDialogComponent,
    ParameterDialogComponent
  ]
})
export class SettingsModule { }
