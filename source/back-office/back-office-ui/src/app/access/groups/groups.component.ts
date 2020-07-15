/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    04-04-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {GroupService} from '../../core/services/http-services/group.service';
import {AuthGroup} from '../../core/models/auth-group';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../commons/dialogs/confirm-dialog.component';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../core/utils/paginator/page';
import {AddGroupDialogComponent} from './components/add-group-dialog.component';
import {EditGroupDialogComponent} from './components/edit-group-dialog.component';
import {Router} from '@angular/router';
import {MuState} from '../../core/models/mu-state';
import {AccessService} from '../access.service';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss'],
})
export class GroupsComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public groupList: AuthGroup[];
  private tmp: AuthGroup[];
  constructor(private groupService: GroupService, private accessService: AccessService, private matDialog: MatDialog,
              private router: Router, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start('Recuperando lista de grupos...');
    this.groupService.requestGroupList().subscribe(response => {
      this.groupList = response.body;
      this.groupList.sort((a: AuthGroup, b: AuthGroup) => a.nombre.localeCompare(b.nombre));
      this.tmp = this.groupList;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  createGroup() {
    this.blockUI.start('Recuperando lista de roles');
    this.accessService.requestRoleList().subscribe(response => {
      this.blockUI.stop();
      this.matDialog.open(AddGroupDialogComponent, this.dialogConfig(response.body))
        .afterClosed()
        .subscribe((created: AuthGroup) => {
            if (created) {
              this.ngOnInit();
              const notif = {error: {title: 'Registro de grupo', detail: 'Grupo registrado satisfactoriamente.'}};
              this.notifierError(notif, 'info');
            }
      });
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  configGroup(row: AuthGroup) {
    sessionStorage.setItem('GROUP_ID', String(row.id));
    this.router.navigate(['accesos/groups/config']);
  }

  changeGroupState(group: AuthGroup) {
    const target = group.estadoGrupo === MuState.ACTIVO ? MuState.BLOQUEADO : MuState.ACTIVO;
    const textContent: string = `Confirmar para cambiar el estado del Grupo: ${group.nombre}, de ${group.estadoGrupo} -> ${target}`;
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Cambiar Estado del Grupo'}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando cambio de estado del Grupo');
          this.accessService.requestChangeGroupStatus(String(group.id)).subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
            const notif = {error: {title: `${group.estadoGrupo === MuState.ACTIVO ? 'Inhabilitar grupo' : 'Habilitar grupo'}`, detail: `${group.estadoGrupo === MuState.ACTIVO ? 'Grupo inhabilitado satisfactoriamente.' : 'Grupo habilitado satisfactoriamente.'}`}};
            this.notifierError(notif, 'info');
          }, error => {
            this.blockUI.stop();
            if (error) this.notifierError(error);
          });
        }
    });


  }

  editGroup(row: AuthGroup) {
    sessionStorage.setItem('GROUP_ID', String(row.id));
    const i = this.groupList.indexOf(row);
    this.matDialog.open(EditGroupDialogComponent, this.dialogConfig(row))
      .afterClosed()
      .subscribe((updated: AuthGroup) => {
        if (updated) {
          this.ngOnInit();
          const notif = {error: {title: 'Actualización de grupo', detail: 'Grupo actualizado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  onGroupFilter(filterValue: string) {
    filterValue = filterValue.toLowerCase();
    this.groupList = this.tmp.filter(item => {
      return item.nombre.toLowerCase().includes(filterValue)
      || item.descripcion.toLowerCase().includes(filterValue);
    });
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
