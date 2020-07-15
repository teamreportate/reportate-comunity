/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UsersComponent} from './users/users.component';
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
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {UserService} from '../core/services/http-services/user.service';
import {GroupsComponent} from './groups/groups.component';
import {GroupService} from '../core/services/http-services/group.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RoleService} from '../core/services/http-services/role.service';
import {RolesComponent} from './roles/roles.component';
import {ResourcesComponent} from './resources/resources.component';
import {ResourceService} from '../core/services/http-services/resource.service';
import {UpdateResourceComponent} from './resources/components/update-resource/update-resource.component';
import {CommonsModule} from '../commons/commons.module';
import {AccessRouting} from './access.routing';
import {RouterModule} from '@angular/router';
import {UserGroupRoleComponent} from './users/user-group-role/user-group-role.component';
import {AccessService} from './access.service';
import {AddUserDialogComponent} from './users/components/add-user-dialog.component';
import {EditUserDialogComponent} from './users/components/edit-user-dialog.component';
import {AddGroupDialogComponent} from './groups/components/add-group-dialog.component';
import {EditGroupDialogComponent} from './groups/components/edit-group-dialog.component';
import {GroupUsersRoleComponent} from './groups/group-users-role/group-users-role.component';
import {RoleUserGroupComponent} from './roles/role-user-group/role-user-group.component';
import {AddRoleDialogComponent} from './roles/components/add-role-dialog.component';
import {EditRoleDialogComponent} from './roles/components/edit-role-dialog.component';
import {UserTokenComponent} from './users/user-token/user-token.component';
import {TokenCreateComponent} from './users/components/token-create.component';
import {ChangePasswordDialogComponent} from './users/components/change-password-dialog.component';
import {TokenUserDialogComponent} from './users/components/token-user-dialog.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { UserComponent } from './users/user-form/user.component';

import { SharedModule } from './../shared/shared.module';

@NgModule({
  declarations: [
    UsersComponent,
    GroupsComponent,
    RolesComponent,
    ResourcesComponent,
    UpdateResourceComponent,
    UserGroupRoleComponent,
    AddUserDialogComponent,
    EditUserDialogComponent,
    AddGroupDialogComponent,
    EditGroupDialogComponent,
    GroupUsersRoleComponent,
    RoleUserGroupComponent,
    AddRoleDialogComponent,
    EditRoleDialogComponent,
    ChangePasswordDialogComponent,
    TokenUserDialogComponent,
    UserTokenComponent,
    TokenCreateComponent,
    UserComponent
  ],
  imports: [
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
    RouterModule.forChild(AccessRouting),
    CommonsModule,
    SharedModule
  ],
  providers: [AccessService, UserService, GroupService, RoleService, ResourceService],
  entryComponents: [
    UpdateResourceComponent,
    AddUserDialogComponent,
    EditUserDialogComponent,
    AddGroupDialogComponent,
    EditGroupDialogComponent,
    ChangePasswordDialogComponent,
    ChangePasswordDialogComponent,
    TokenUserDialogComponent,
    AddRoleDialogComponent,
    EditRoleDialogComponent,
    TokenCreateComponent
  ]
})
export class AccessModule { }

