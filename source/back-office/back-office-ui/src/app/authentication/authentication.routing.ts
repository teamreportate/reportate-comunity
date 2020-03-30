import {Routes} from '@angular/router';

import {ErrorComponent} from './error/error.component';
import {ForgotComponent} from './forgot/forgot.component';
import {LockscreenComponent} from './lockscreen/lockscreen.component';
import {LoginComponent} from './login/login.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';
import {RegisterComponent} from './change-pass/register.component';

export const AuthenticationRoutes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: '404',
        component: ErrorComponent
      },
      {
        path: 'forgot',
        component: ForgotComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'lockscreen',
        component: LockscreenComponent,
      },
      {
        path: 'register',
        component: RegisterComponent
      }
    ]
  }
];
