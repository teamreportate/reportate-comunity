/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    09-08-19
 * author:  fmontero
 **/

import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../../core/utils/clic-component';
import {Page} from '../../../core/utils/paginator/page';
import {FormControl} from '@angular/forms';
import {AccessService} from '../../access.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../../commons/dialogs/confirm-dialog.component';
import {AuthResource} from '../../../core/models/auth-resource';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Location} from '@angular/common';

@Component({
  selector: 'app-role-user-group',
  templateUrl: './role-user-group.component.html',
  styleUrls: ['./role-user-group.component.scss'],
  providers: [Location]
})

export class RoleUserGroupComponent extends ClicComponent implements OnInit, AfterViewInit {
  @BlockUI() blockUI: NgBlockUI;
  public checked = false;
  private parents: AuthResource[];
  public resourceList: any[];
  public tmp: AuthResource[];
  public tmpAsignados: any[];
  public listAdd: any[] ;
  private roleId: string;
  public searchControlResource: FormControl = new FormControl();
  public searchControlNotResource: FormControl = new FormControl();

  public asignedResourceList: AuthResource[];
  public notAsignedResourceList: AuthResource[];
  public selectedResourceList: AuthResource[];
  public selectedAddResourceList: AuthResource[];
  private asignedResourceTmp: AuthResource[];
  private notAsignedResourceTmp: AuthResource[];
  public asignedResource: boolean;
  private toRemoveResource: boolean;

  private action: string;
  ADD_USER = 'AGREGAR USUARIOS';
  DEL_USER = 'REMOVER USUARIOS';
  ADD_GRO = 'AGREGAR GRUPOS';
  DEL_GRO = 'REMOVER GRUPOS';
  ADD_RES = 'AGREGAR RECURSOS';
  DEL_RES = 'REMOVER RECURSOS';

  constructor(private accessService: AccessService, private matDialog: MatDialog, private notifier: NotifierService,
              public location: Location,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    this.searchControlsInitialize();
  }

  ngOnInit() {
    this.roleId = sessionStorage.getItem('ROLE_ID');
    this.parents = [];
    this.listAdd = [];
    this.initialListener(this.changeDetector, this.media);
    this.selectedResourceList = this.selectedAddResourceList = [];
    this.asignedResource = true;
    const roleId: string = sessionStorage.getItem('ROLE_ID');

    this.accessService.requestRoleResources(roleId).subscribe(response => {
      this.asignedResourceList = this.asignedResourceTmp = response.body;
      this.accessService.requestNotRoleResources(roleId).subscribe(responseNotResource => {
        this.notAsignedResourceList = this.notAsignedResourceTmp = responseNotResource.body;
        const listaAsignada: any[] = this.tmpAsignados = response.body;
        const listaNoAsignada: any[] = responseNotResource.body;
        for (let i = 0 ; i < listaAsignada.length; i++) {
          listaAsignada[i].enUso = true;
        }
        for (let i = 0 ; i < listaNoAsignada.length; i++) {
          listaNoAsignada[i].enUso = false;
        }
        this.loadList(listaAsignada.concat(listaNoAsignada));
        this.tmp = this.resourceList;
        this.toggleFisrtTimeout();
      }, error => {
        if (error) this.notifierError(error);
      });
    }, error => {
      if (error) this.notifierError(error);
    });

  }

  loadList(resources: AuthResource[]) {
    const list = [];
    for (let i = 0; i < resources.length; i++)
      if (resources[i].idRecursoPadre) list.push(this.copyParentProp(resources[i]));
      else this.parents.push(resources[i]);

    list.sort((a: AuthResource, b: AuthResource) => a.ordenPadre - b.ordenPadre);
    this.resourceList = list;
  }
  copyParentProp(item) {
    item.recursoPadre = item.idRecursoPadre.id;
    item.nombrePadre = item.idRecursoPadre.nombre;
    item.descripcionPadre = item.idRecursoPadre.descripcion;
    item.iconoPadre = item.idRecursoPadre.icon;
    item.ordenPadre = item.idRecursoPadre.ordenMenu;
    return item;
  }
  onCheckGroup({checked}, item) {
    const list: any[] = this.tmp.filter((costItem) => {
      return costItem.idRecursoPadre.id === item.id;
    });
    for (const resource of list) {
      this.onCheck({checked}, resource);
      for (let i = 0 ; i < this.resourceList.length; i++) {
        if (this.resourceList[i].id === resource.id) {
          this.resourceList[i].enUso = checked;
          break;
        }
      }
    }
  }

  onCheck({checked}, item) {
    const id: string = this.roleId;
    const busqueda = this.listAdd.find(resource => resource.asignacionId === item.id);
    if (busqueda) this.listAdd.splice(this.listAdd.indexOf(busqueda), 1);
    const busquedaAsignados = this.tmpAsignados.find(itemList => itemList.id === item.id);
    if (busquedaAsignados && !checked) this.listAdd.push({nuevo: false, id, asignacionId: item.id});
    else if (!busquedaAsignados && checked) this.listAdd.push({nuevo: true, id, asignacionId: item.id});
  }
  // onCheck({checked}, item) {
  //   const roleId: string = sessionStorage.getItem('ROLE_ID');
  //   const busqueda = this.listAdd.find(resource => resource.recursoId === item.id);
  //   if (busqueda) {
  //     const index2 = this.listAdd.indexOf(busqueda);
  //     this.listAdd.splice(index2, 1);
  //   }
  //   const busquedaAsignados = this.tmpAsignados.find((itemList) => {
  //     return itemList.id === item.id;
  //   });
  //   if (busquedaAsignados && !checked) {
  //     const dto = {nuevo: false, roleId, recursoId: item.id};
  //     this.listAdd.push(dto);
  //   } else if (!busquedaAsignados && checked) {
  //     const dto = {nuevo: true, roleId, recursoId: item.id};
  //     this.listAdd.push(dto);
  //   }
  // }

  saveConfig = () => this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({title: 'Configurar Recursos del Rol', textContent: 'Confirmar para guardar la configuración.'}))
      .afterClosed()
      .subscribe(confirm => confirm ? this.requestSaveConfiguration() : null);

  requestSaveConfiguration() {
    this.blockUI.start('Procesando solicitud...');
    this.accessService.requestConfigureResourceRole(this.roleId, this.listAdd).subscribe(response => {
      this.blockUI.stop();
      this.ngOnInit();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  asignResources() {
    const roleId: string = sessionStorage.getItem('ROLE_ID');
    const callback = () => { return this.accessService.requestAddRoleResources(roleId, this.selectedAddResourceList); };
    const textContent = `Confirmar para agregar los recursos seleccionados al rol`;
    this.action = this.ADD_RES;
    this.loadRequest(callback, textContent, 'Asignar Recursos');
  }

  removeResources() {
    const roleId: string = sessionStorage.getItem('ROLE_ID');
    const callback = () => { return this.accessService.requestRemoveResourcesFromRole(roleId, this.selectedResourceList); };
    const textContent = `Confirmar para remover los recursos seleccionados del rol`;
    this.action = this.DEL_RES;
    this.loadRequest(callback, textContent, 'Remover Recursos');
  }

  onCheckResources({selected}, toRemove: boolean) {
    this.toRemoveResource = toRemove;
    if (this.toRemoveResource) this.selectedResourceList = selected;
    else this.selectedAddResourceList = selected;
  }

  filterSearchResources = (value: string) => {
    this.asignedResourceList = this.asignedResourceTmp.filter((item: AuthResource) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  filterSearchNotAsignedResources = (value: string) => {
    this.notAsignedResourceList = this.notAsignedResourceTmp.filter((item: AuthResource) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  ngAfterViewInit() {
    // setTimeout(() => {
    //   this.table.onHeaderSelect = (event: any) => {
    //     if (this.table.selectAllRowsOnPage) {
    //       const first = this.table.bodyComponent.indexes.first;
    //       const last = this.table.bodyComponent.indexes.last;
    //       const allSelected = this.table.selected.length === (last - first);

    //       this.table.selected = [];

    //       if (!allSelected) {
    //         this.table.selected.push(...this.table._internalRows.slice(first, last));
    //       }
    //     } else {
    //       const allSelected = this.table.selected.length === this.table.rows.length;
    //       this.table.selected = [];
    //       if (!allSelected) {
    //         this.table.selected.push(...this.table.rows);
    //       }
    //     }

    //     this.table.select.emit({
    //       selected: this.table.selected
    //     });

    //     if (this.asignedResource) this.selectedResourceList = this.table.selected;
    //     else this.selectedAddResourceList = this.table.selected;
    //   }
    // }, 1000);
  }

  loadRequest(callback: any, textContent: string, title: string) {
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title}))
      .afterClosed()
      .subscribe(confirm => {
      if (confirm) {
        this.blockUI.start('Procesando solicitud...');
        callback().subscribe(response => {
          this.blockUI.stop();
          this.ngOnInit();
          let message: string;
          switch (this.action) {
            case this.ADD_USER:
              message = `Los usuarios seleccionados fueron asignados correctamente`;
              break;
            case this.DEL_USER:
              message = `Los usuarios seleccionados fueron removidos correctamente`;
              break;
            case this.ADD_GRO:
              message = `Los grupos seleccionados fueron asignados correctamente`;
              break;
            case this.DEL_GRO:
              message = `Los grupos seleccionados fueron removidos correctamente`;
              break;
            case this.ADD_RES:
              message = `Los recursos seleccionados fueron asignados correctamente`;
            break;
            case this.DEL_RES:
              message = `Los recursos seleccionados fueron removidos correctamente`;
            break;
          }
          const notif = {error: {title: 'Configuración de recursos de rol', detail: message}};
          this.notifierError(notif, 'info');
        }, error => {
          this.blockUI.stop();
          if (error) this.notifierError(error);
        });
      }
    });
  }

  searchControlsInitialize() {
    this.searchControlResource.valueChanges.subscribe((value: string) => {
      this.filterSearchResources(value.trim().toLowerCase());
    });

    this.searchControlNotResource.valueChanges.subscribe((value: string) => {
      this.filterSearchNotAsignedResources(value.trim().toLowerCase());
    });
  }

  toggleExpandGroup = (group)  => this.table.groupHeader.toggleExpandGroup(group);

  toggleFisrtTimeout = () => setTimeout(() => {
    const groups = this.table.groupedRows;
    if (groups.length > 0) this.toggleExpandGroup(groups[0]);
  }, 500);

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;

  onXsScreen() {
    this.flex = 100;
  }

  onSmScreen() {
    this.flex = 25;
  }

  onMdScreen() {
    this.flex = 15;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onGtLgScreen() {
    this.flex = 10;
  }

  setPage(pageInfo: Page) {
  }

}
