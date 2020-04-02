/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {Routes} from '@angular/router';
import {UsersComponent} from './users/users.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';
import {GroupsComponent} from './groups/groups.component';
import {RolesComponent} from './roles/roles.component';
import {ResourcesComponent} from './resources/resources.component';
import {UserGroupRoleComponent} from './users/user-group-role/user-group-role.component';
import {GroupUsersRoleComponent} from './groups/group-users-role/group-users-role.component';
import {RoleUserGroupComponent} from './roles/role-user-group/role-user-group.component';
import {UserTokenComponent} from './users/user-token/user-token.component';
import { UserComponent } from './users/user-form/user.component';

// const menuBuilder: MenuBuilderService = new MenuBuilderService(new JwtHelperService());
//
// export const AccessRouting: Routes = menuBuilder.routesByModuleIdentifier('MODULO_ACCESOS');
export const AccessRouting: Routes = [
  {
    path: '',
    children: [
      {
        path: 'users',
        component: UsersComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'users/config',
        component: UserGroupRoleComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'users/token',
        component: UserTokenComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'users/create',
        component: UserComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'groups',
        component: GroupsComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'groups/config',
        component: GroupUsersRoleComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'roles',
        component: RolesComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'roles/config',
        component: RoleUserGroupComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'resources',
        component: ResourcesComponent,
        canActivate: [AuthGuardService]
      }
    ]
  }
];
