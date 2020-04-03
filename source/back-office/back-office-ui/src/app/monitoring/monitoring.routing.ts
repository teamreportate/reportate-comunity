/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {Routes} from '@angular/router';
import {BinnacleComponent} from './binnacle/binnacle.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';
import {SeguimientoEnfermedadComponent} from '../seguimiento/seguimiento-enfermedad/seguimiento-enfermedad.component';


export const MonitoringRoutes: Routes = [
      {
        path: 'bitacora',
        component: SeguimientoEnfermedadComponent,
        canActivate: [AuthGuardService]
      }
];
