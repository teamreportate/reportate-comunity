/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    05-04-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AuthRole} from '../../core/models/auth-role';
import {RoleService} from '../../core/services/http-services/role.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../commons/dialogs/confirm-dialog.component';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../core/utils/paginator/page';
import {Router} from '@angular/router';
import {AccessService} from '../access.service';
import {AddRoleDialogComponent} from './components/add-role-dialog.component';
import {EditRoleDialogComponent} from './components/edit-role-dialog.component';
import {MuState} from '../../core/models/mu-state';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.scss'],
})
export class RolesComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public roleList: AuthRole[];
  private tmp: AuthRole[];
  public render: boolean;
  constructor(private roleService: RoleService, private accessService: AccessService, public matDialog: MatDialog,
              private router: Router, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start('Recuperando lista de roles...');
    this.accessService.requestRoleList().subscribe(response => {
      this.roleList = response.body;
      this.roleList.sort((a: AuthRole, b: AuthRole) => a.nombre.localeCompare(b.nombre));
      this.tmp = this.roleList;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  createRole() {
    this.matDialog.open(AddRoleDialogComponent, this.dialogConfig(null))
      .afterClosed()
      .subscribe((created: AuthRole) => {
        if (created) {
          this.ngOnInit();
          const notif = {error: {title: 'Registro de rol', detail: 'Rol registrado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  editRole(row: AuthRole) {
    const i = this.roleList.indexOf(row);
    this.matDialog.open(EditRoleDialogComponent, this.dialogConfig(row))
      .afterClosed()
      .subscribe((updated: AuthRole) => {
        if (updated) {
          this.ngOnInit();
          const notif = {error: {title: 'Actualización de rol', detail: 'Rol actualizado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  changeRoleState(role: AuthRole) {
    const target = role.estadoRol === MuState.ACTIVO ? MuState.BLOQUEADO : MuState.ACTIVO;
    const textContent: string = `Confirmar para cambiar el estado del Rol: ${role.nombre}, de ${role.estadoRol} -> ${target}`;
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title:'Cambiar Estado del Rol'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando cambio de estado del Rol');
          this.accessService.requestChangeRoleStatus(String(role.id)).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            const notif = {error: {title: `${role.estadoRol === MuState.ACTIVO ? 'Inhabilitar rol' : 'Habilitar rol'}`, detail: `${role.estadoRol === MuState.ACTIVO ? 'Rol inhabilitado satisfactoriamente.' : 'Rol habilitado satisfactoriamente.'}`}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) this.notifierError(error);
          });
        }
    });
  }

  onRoleFilter(filterValue: string) {
    filterValue = filterValue.toLowerCase();
    this.roleList = this.tmp.filter(item => {
      return item.nombre.toLowerCase().includes(filterValue)
      || item.descripcion.toLowerCase().includes(filterValue)
    });
  }

  configRole(role: AuthRole) {
    localStorage.setItem('ROLE_ID', String(role.id));
    this.router.navigate(['accesos/roles/config']);
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number ;
  onGtLgScreen() {
    this.flex = 10;
    this.scrollH = false;
  }

  onLgScreen() {
    this.flex = 15;
    this.scrollH = false;
  }

  onMdScreen() {
    this.flex = 25;
    this.scrollH = true;
  }

  onSmScreen() {
    this.flex = 100;
    this.scrollH = true;
  }

  onXsScreen() {
    this.flex = 100;
    this.scrollH = true;
  }

  setPage(pageInfo: Page) {}
}
