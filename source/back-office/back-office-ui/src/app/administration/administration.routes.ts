import {Routes} from '@angular/router';
import { AuthGuardService } from '../core/services/util-services/auth-guard.service';
import { PaisesComponent } from './paises/paises.component';
import { CentrosComponent } from './centros/centros.component';
import { DepartamentosComponent } from './departamentos/departamentos.component';
import { MunicipiosComponent } from './municipios/municipios.component';
import { SintomasComponent } from './sintomas/sintomas.component';
import { EnfermedadesComponent } from './enfermedades/enfermedades.component';

export const AdministrationRoute: Routes = [
  {
    path: '',
    children: [
      {
        path: 'paises',
        component: PaisesComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'centros',
        component: CentrosComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'departamentos',
        component: DepartamentosComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'enfermedades',
        component: EnfermedadesComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'municipios',
        component: MunicipiosComponent,
        canActivate: [AuthGuardService]
      },
      {
        path: 'sintomas',
        component: SintomasComponent,
        canActivate: [AuthGuardService]
      }
    ]
  }
];
