/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    13-08-19
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
import {AuthUser} from '../../../core/models/AuthUser';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-group-users-role',
  templateUrl: './group-users-role.component.html',
  styleUrls: ['./group-users-role.component.scss'],
  providers: [Location]
})

export class GroupUsersRoleComponent extends ClicComponent implements OnInit, AfterViewInit {
  @BlockUI() blockUI: NgBlockUI;

  public searchControl: FormControl = new FormControl();
  public searchControlNotUser: FormControl = new FormControl();

  public asignedUserList: AuthUser[];
  public notAsignedUserList: AuthUser[];
  public selectedUserList: AuthUser[];
  public selectedAddUserList: AuthUser[];
  private asignedTmp: AuthUser[];
  private notAsignedTmp: AuthUser[];
  public asigned: boolean;
  private toRemove: boolean;

  private action: string;
  public tmpUsersAsign: any[];
  public groupList: any[];
  public listAdd : any[];
  public listTemp : any [];
  ADD_USER = 'AGREGAR USUARIOS';
  DEL_USER = 'REMOVER USUARIOS';
  ADD_ROL = 'ASIGNAR ROLES';
  DEL_ROL = 'REMOVER ROLES';
  private groupId: string;

  nav;
  constructor(private accessService: AccessService, private matDialog: MatDialog, private notifier: NotifierService,
              private router: Router, public location: Location,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    this.searchControlsInitialize();
  }

  ngOnInit() {
    this.groupId = localStorage.getItem('GROUP_ID');
    this.funcionSistemaOperativo();
    this.initialListener(this.changeDetector, this.media);
    this.tmpUsersAsign = [];
    this.listAdd = [];
    this.listTemp = [];
    this.asigned = true;
    this.nav = '';
    this.selectedUserList = this.selectedAddUserList = [];
    const groupId: string = localStorage.getItem('GROUP_ID');

    this.accessService.requestGroupUsers(groupId).subscribe(response => {
      this.asignedUserList = this.asignedTmp = response.body;
      this.accessService.requestNotGroupUsers(groupId).subscribe(responseNotGroups => {
        let usuarios: any[] = [];
        responseNotGroups.body.forEach((elemento: AuthUser) => {
          if (elemento.estadoUsuario === 'ACTIVO')
            usuarios.push(elemento);
        });
        this.notAsignedUserList = this.notAsignedTmp = usuarios;
        const listaAsignada: any[] = this.tmpUsersAsign = response.body;
        const listaNoAsignada: any[] = usuarios;
        for (let i = 0 ; i < listaAsignada.length ; i++) {
          listaAsignada[i].enUso = true;
        }
        for (let i = 0 ; i < listaNoAsignada.length; i++) {
          listaNoAsignada[i].enUso = false;
        }
        this.groupList = (listaAsignada.concat(listaNoAsignada));
        this.listTemp = listaAsignada.concat(listaNoAsignada);
      }, error => {
        if (error) this.notifierError(error);
      });
    }, error => {
      if (error) this.notifierError(error);
    });
  }

  funcionSistemaOperativo() {
    const navInfo = window.navigator.appVersion.toLowerCase();
    console.log(navInfo);
    let so = 'Sistema Operativo';
    if ( navInfo.indexOf('win') !== -1) {
      so = 'Windows';
    } else if (navInfo.indexOf('linux') !== -1) {
      so = 'Linux';
    } else if (navInfo.indexOf('mac') !== -1) {
      so = 'Macintosh';
    }
    console.log(so);
    console.log('Nombre del navegador: ', navigator.appCodeName);
  }


  onCheckUsers({checked}, item) {
    const id = this.groupId;
    const busqueda = this.listAdd.find(resource => resource.asignacionId === item.id);
    if (busqueda) this.listAdd.splice(this.listAdd.indexOf(busqueda), 1);

    const busquedaAsignados = this.tmpUsersAsign.find(itemList => itemList.id === item.id);
    if (busquedaAsignados && !checked) this.listAdd.push({nuevo: false, id, asignacionId: item.id});
    else if (!busquedaAsignados && checked) this.listAdd.push({nuevo: true, id, asignacionId: item.id});

    this.actualizaBusqueda(checked, item);
  }

  saveConfig = () => this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({title: 'Configurar Usuarios del Grupo', textContent: 'Confirmar para guardar la configuración.'}))
      .afterClosed()
      .subscribe(confirm => confirm ? this.requestSaveConfiguration() : null);

  actualizaBusqueda(checked, item: any) {
    for (let i = 0 ; i < this.groupList.length ; i++) {
      if (this.groupList[i].id === item.id) {
        item.enUso = checked;
        this.groupList.splice(i, 1, item);
        this.listTemp.splice(i, 1, item);
        break;
      }
    }
  }
  private requestSaveConfiguration() {
    this.blockUI.start('Procesando solicitud...');
    this.accessService.requestConfigureUsersGroup(this.groupId, this.listAdd).subscribe(response => {
      this.blockUI.stop();
      this.ngOnInit();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  asignUsers() {
    const userId: string = localStorage.getItem('GROUP_ID');
    const callback = () => { return this.accessService.requestAddGroupUsers(userId, this.selectedAddUserList); };
    const textContent = `Confirmar para agregar los usuarios seleccionados al grupo`;
    this.action = this.ADD_USER;
    this.loadRequest(callback, textContent, 'Asignar Usuarios');
  }

  removeUsers() {
    const userId: string = localStorage.getItem('GROUP_ID');
    const callback = () => { return this.accessService.requestRemoveUsersFromGroup(userId, this.selectedUserList); };
    const textContent = `Confirmar para remover los usuarios seleccionados del grupo`;
    this.action = this.DEL_USER;
    this.loadRequest(callback, textContent, 'Remover Usuarios');
  }

  onCheck({selected}, toRemove: boolean) {
    this.toRemove = toRemove;
    if (this.toRemove) this.selectedUserList = selected;
    else this.selectedAddUserList = selected;
  }

  private searchControlsInitialize() {
    this.searchControl.valueChanges.subscribe((value: string) => {
      this.filterSearch(value.trim().toLowerCase());
    });
  }

  filterSearch = (value: string) => {
    this.groupList = this.listTemp.filter((item: AuthUser) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 || !value;
    });
  }

  filterSearchNotAasigned = (value: string) => {
    this.notAsignedUserList = this.groupList.filter((item: AuthUser) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        // item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  }

  ngAfterViewInit() {
  }

  loadRequest(callback: any, textContent: string, title: string) {
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: title}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          callback().subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            let message: string;
            switch(this.action){
              case this.ADD_USER:
                message = `Los usuarios seleccionados fueron asignados correctamente`;
                break;
              case this.DEL_USER:
                message = `Los usuarios seleccionados fueron removidos correctamente`;
                break;
              case this.ADD_ROL:
                message = `Los roles seleccionados fueron asignados correctamente`;
                break;
              case this.DEL_ROL:
                message = `Los roles seleccionados fueron removidos correctamente`;
                break;
            }
            const notif = {error: {title: 'Configurar usuarios de grupo', detail: message}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) this.notifierError(error);
          });
        }
    });
  }

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
