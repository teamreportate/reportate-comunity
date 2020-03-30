/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    03-04-19
 * author:  fmontero
 **/

export class Resource{
  id: any;
  version: any;
  createdDate: any;
  lastModifiedDate: any;
  createdBy: any;
  lastModifiedBy: any;
  estado: any;
  idRecursoPadre: any;
  nombre: any;
  url: any;
  icon: any;
  descripcion: any;
  ordenMenu: any;
  listaRecursos: Resource[] | null;
}
