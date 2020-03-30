/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {Routes} from '@angular/router';
import {ParameterComponent} from './parameter/parameter.component';
import {DomainComponent} from './domain/domain.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';
import {AlarmComponent} from './alarm/alarm.component';
import {AlarmUsersComponent} from './alarm/components/alarm-users.component';
import {AlarmEditComponent} from './alarm/components/alarm-edit.component';
import {DomainCreateComponent} from './domain-create/domain-create.component';

// const menuBuilder: MenuBuilderService = new MenuBuilderService(new JwtHelperService());
//
// export const SettingsRoutes: Routes = menuBuilder.routesByModuleIdentifier('MODULO_CONFIGURACIONES');
export const SettingsRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'parameters',
        component: ParameterComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'dominios',
        component: DomainComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'domain-create',
        component: DomainCreateComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'alarmas',
        component: AlarmComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'alarmUsers',
        component: AlarmUsersComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'alarmEdit',
        component: AlarmEditComponent,
        canActivate: [AuthGuardService]
      }
    ]
  }
];
