import {Routes} from '@angular/router';
import {SeguimientoEnfermedadComponent} from './seguimiento-enfermedad/seguimiento-enfermedad.component';
import {AuthGuardService} from '../core/services/util-services/auth-guard.service';
import {FichaEpidemiologicaComponent} from './seguimiento-enfermedad/ficha-epidemiologica/ficha-epidemiologica.component';
import { GeolocalizacionCasosComponent } from './geolocalizacion-casos/geolocalizacion-casos.component';

export const SeguimientoRoute: Routes = [
  {
    path: '',
    children: [
      {
        path: 'diagnostico',
        component: SeguimientoEnfermedadComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'ficha-epidemiologica/:idPaciente',
        component: FichaEpidemiologicaComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'localizacion',
        component: GeolocalizacionCasosComponent,
        canActivate: [AuthGuardService]
      },
    ]
  }
];
