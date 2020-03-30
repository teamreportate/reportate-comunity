import {Injectable} from '@angular/core';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable()
export class ResourceService {
  private resources: any;
  constructor(public jwtHelper: JwtHelperService) {

  }

  getMenu(resources) {
    let items: any[] = [];
    resources.menu.sort((left, rigth): number => {
      if (left.ordenMenu < rigth.ordenMenu) return -1;
      if (left.ordenMenu > rigth.ordenMenu) return 1;
      return 0;
    });
    resources.menu.forEach(item=>{
      if (item.listaRecursos.length>0) {
        // ordenacion de los recursos hijos del recurso actual
        item.listaRecursos.sort((left, rigth): number => {
          if (left.ordenMenu < rigth.ordenMenu) return -1;
          if (left.ordenMenu > rigth.ordenMenu) return 1;
          return 0;
        });
        // crear un item separador con el recurso padre actual
        const menu = {parent: '', state: item.url, name: item.nombre, type: 'sub', icon: item.icon, children: []};
        //creacion de los items tipo link con los recursos hijos del item actual
        item.listaRecursos.forEach(subitem => {
          //if (subitem.ordenMenu) {
            const itemlink = {parent: item.url, state: subitem.url, name: subitem.nombre, type: 'link', icon: subitem.icon, enable: true};
            menu.children.push(itemlink);
          //}
        });

        items.push(menu);
      }
    });
    return items;
  }

  getPermisosByResource(resourceName: string) {
    return this.resources.permisos.find(item => item.idRecurso.nombre === resourceName);
  }
}
