import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../commons/dialogs/confirm-dialog.component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Router} from '@angular/router';
import {AuthUser} from '../../core/models/AuthUser';
import {ClicComponent} from '../../core/utils/clic-component';
import {Page} from '../../core/utils/paginator/page';
import {AccessService} from '../access.service';
import {AddUserDialogComponent} from './components/add-user-dialog.component';
import {EditUserDialogComponent} from './components/edit-user-dialog.component';
import {MuState} from '../../core/models/mu-state';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {UserTokenComponent} from './user-token/user-token.component';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {GroupService} from '../../core/services/http-services/group.service';
import {ChangePasswordDialogComponent} from './components/change-password-dialog.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
})
export class UsersComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public userList: AuthUser[];
  private tmp: AuthUser[];
  constructor(private accessService: AccessService, private matDialog: MatDialog, private groupService: GroupService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher, private router: Router, private notifier: NotifierService) {
    super();
  }

  ngOnInit() {
    this.userList = this.tmp = null;
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start('Recuperando lista de usuarios...');
    this.accessService.requestUserList().subscribe(response => {
      this.userList = response.body;
      this.userList.sort((a: AuthUser, b: AuthUser) => a.nombre.localeCompare(b.nombre));
      this.tmp = this.userList;
      this.blockUI.stop()
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  createUser() {
    this.blockUI.start('Recuperando lista de roles');
    this.groupService.requestGroupList().subscribe(response => {
      this.blockUI.stop();
      this.matDialog.open(AddUserDialogComponent, this.dialogConfig(response.body))
        .afterClosed()
        .subscribe((created: AuthUser) => {
          if (created) {
            this.ngOnInit();
            const notif = {error: {title: 'Registro de Usuario', detail: 'Usuario registrado satisfactoriamente.'}};
            this.notifierError(notif, 'info');
          }
      });
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  editUser(user: AuthUser) {
    localStorage.setItem('USER_ID', String(user.id));
    const i = this.userList.indexOf(user);
    this.matDialog.open(EditUserDialogComponent, this.dialogConfig(user))
      .afterClosed()
      .subscribe((updated: AuthUser) => {
        if (updated) {
          this.ngOnInit();
          const notif = {error: {title: 'Actualización de Usuario', detail: 'Usuario actualizado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  changePassword(user: AuthUser) {
    localStorage.setItem('USER_ID', String(user.id));
    const i = this.userList.indexOf(user);
    this.matDialog.open(ChangePasswordDialogComponent, this.dialogConfig(user))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: 'Cambiar contraseña de usuario', detail: 'Contraseña actualizada satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  onChangeUserState(user: AuthUser) {
    const target = user.estadoUsuario === MuState.ACTIVO ? MuState.BLOQUEADO : MuState.ACTIVO;
    const textContent: string = `Confirmar para cambiar el estado del Usuario: ${user.username}, de ${user.estadoUsuario} -> ${target}`;
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Cambiar Estado del Usuario'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          this.accessService.requestChangeUserStatus(String(user.id)).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            const notif = {error: {title: `${user.estadoUsuario === MuState.ACTIVO ? 'Inhabilitar Usuario' : 'Habilitar Usuario'}`, detail: `${user.estadoUsuario === MuState.ACTIVO ? 'Usuario inhabilitado satisfactoriamente.' : 'Usuario habilitado satisfactoriamente.'}`}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) this.notifierError(error);
          });
        }
    });
  }

  toTokenLis(user: AuthUser) {
    localStorage.setItem(UserTokenComponent.USERID_TO_TOKENS, String(user.id));
    this.router.navigate(['accesos/users/token']);
  }


  onUserFilter(filterValue: string) {
    filterValue = filterValue.toLowerCase();

    let filter = filterValue;

    if('sistema'.includes(filterValue)){
      filter = 'pwd'
    }
    if('active directory'.includes(filterValue)){
      filter = 'ad'
    }

    this.userList = this.tmp.filter(item => {
        return item.nombre.toLowerCase().includes(filterValue)
        || item.authType.toLowerCase().includes(filter)
        || item.username.toLowerCase().includes(filterValue)
        || (item.estadoUsuario.toLowerCase().includes(filterValue))
    })

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
    this.dialogWidth = '750px';
  }

  onLgScreen() {
    this.flex = 15;
    this.dialogWidth = '750px';
  }

  onMdScreen() {
    this.flex = 25;
    this.dialogWidth = '750px';
  }

  onSmScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }

  onXsScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }

  setPage(pageInfo: Page) {}
}
