import {Routes} from '@angular/router';
import { AuthGuardService } from '../core/services/util-services/auth-guard.service';
import {SeguimientoEnfermedadComponent} from './seguimiento-enfermedad/seguimiento-enfermedad.component';

export const SeguimientoRoute: Routes = [
  {
    path: '',
    children: [
      // {
      //   path: 'seguimientio-enfermedades',
      //   component: SeguimientoEnfermedadComponent,
      //   canActivate: [AuthGuardService]
      // }
    ]
  }
];
