import {Routes} from '@angular/router';
import {SeguimientoEnfermedadComponent} from './seguimiento-enfermedad/seguimiento-enfermedad.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';

export const SeguimientoRoute: Routes = [
  {
    path: '',
    children: [
      {
        path: 'diagnostico',
        component: SeguimientoEnfermedadComponent,
        canActivate: [AuthGuardService]
      }
    ]
  }
];
