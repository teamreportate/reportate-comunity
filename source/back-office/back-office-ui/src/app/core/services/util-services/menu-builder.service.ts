/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    27-06-19
 * author:  fmontero
 **/
import {JwtHelperService} from '@auth0/angular-jwt';
import {Injectable} from '@angular/core';
import {Route, Routes} from '@angular/router';
import {MenuDto} from '../../models/dto/menu-dto';
import {AuthResource} from '../../models/auth-resource';
import {UsersComponent} from '../../../access/users/users.component';
import {GroupsComponent} from '../../../access/groups/groups.component';
import {ResourcesComponent} from '../../../access/resources/resources.component';
import {RolesComponent} from '../../../access/roles/roles.component';
import {ParameterComponent} from '../../../settings/parameter/parameter.component';
import {DomainComponent} from '../../../settings/domain/domain.component';
import {AlarmComponent} from '../../../settings/alarm/alarm.component';
import {AuthGuardService} from './auth-guard.service';
import {AlarmUsersComponent} from '../../../settings/alarm/components/alarm-users.component';
import {AlarmEditComponent} from '../../../settings/alarm/components/alarm-edit.component';

@Injectable({
  providedIn: 'root'
})
export class MenuBuilderService {
  private components: {[key: string]: any};

  constructor(private jwtHelperService: JwtHelperService) {
    this.components = {
      'MODULO_ACCESOS': null,
      'USUARIOS': UsersComponent,
      'GRUPOS': GroupsComponent,
      'ROLES': RolesComponent,
      'RECURSOS': ResourcesComponent,
      
      // 'MODULO_ADMINISTRACION': null,
      // 'EMPRESAS': EnterpriseComponent,
      // 'SUCURSALES': BranchOfficeComponent,
      // 'EVENTOS': EventComponent,
      // 'EMPRESA_CREATE': EnterpriseWizardComponent,
      // 'EMPRESA_CONFIG': EnterpriseConfigComponent,
      // 'EMPRESA_SYNC_MANUAL': ManualSynchronizationComponent,
      // 'SUCURSAL_CREAR': BranchOfficeCreateComponent,
      // 'SUCURSAL_VER': BranchOfficeViewComponent,
      // 'PUNTO_VENTA': PointSaleComponent,
      // 'PUNTO_VENTA_VER': PointSaleViewComponent,
      // 'PUNTO_VENTA_CREATE': PointSaleCreateComponent,

      'MODULO_CONFIGURACIONES': null,
      'PARAMETROS': ParameterComponent,
      'DOMINIOS': DomainComponent,
      'ALARMAS': AlarmComponent,
      'ALARMA_USUARIOS': AlarmUsersComponent,
      'ALARMA_EDIT': AlarmEditComponent,  
    };
  }

  routesByModuleIdentifier(identifier: any): Routes {
    const routes: Route[] = [];
    const menuDto: MenuDto = this.jwtHelperService.decodeToken(JSON.parse(localStorage.getItem('data')).token);
    const accessMenu: AuthResource = menuDto.menu.find((menu: AuthResource) => menu.componenteFront === identifier);

    if (accessMenu) {
      accessMenu.listaRecursos.sort((left, rigth): number => {
        if (left.ordenMenu < rigth.ordenMenu) return -1;
        if (left.ordenMenu > rigth.ordenMenu) return 1;
        return 0;
      });
      accessMenu.listaRecursos.forEach((resource: AuthResource) => {
        const route: Route = {
          path: resource.url,
          component: this.components[resource.componenteFront],
          canActivate: [AuthGuardService]
        };
        routes.push(route);
      });
    }
    return routes;
  }
}

