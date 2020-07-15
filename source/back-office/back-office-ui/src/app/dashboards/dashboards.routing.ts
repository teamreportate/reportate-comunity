/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    31-01-19
 * author:  fmontero
 **/
import {Routes} from '@angular/router';
import {PrincipalComponent} from './principal/principal.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';

export const DashboardsRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'principal',
        component: PrincipalComponent,
        canActivate: [AuthGuardService]
      }
    ]
  }
];
