import {Routes} from '@angular/router';
import {FullComponent} from './layouts/full/full.component';
import {AppBlankComponent} from './layouts/blank/blank.component';

export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/authentication/login',
        pathMatch: 'full',
      },
      {
        path: 'dashboards',
        loadChildren: './dashboards/dashboards.module#DashboardsModule'
      },
      {
        path: 'accesos',
        loadChildren: './access/access.module#AccessModule'
      },
      {
        path: 'monitoreo'  ,
        loadChildren: './monitoring/monitoring.module#MonitoringModule'
      },
      {
        path: 'configuracion',
        loadChildren: './settings/settings.module#SettingsModule'
      },
      {
        path: 'administracion',
        loadChildren: './administration/administration.module#AdministrationModule'
      },
      {
        path: 'seguimiento',
        loadChildren: './seguimiento/seguimiento.module#SeguimientoModule'
      }
    ]
  },
  {
    path: '',
    component: AppBlankComponent,
    children: [
      {
        path: 'authentication',
        loadChildren: './authentication/authentication.module#AuthenticationModule'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'authentication/404'
  }
];
