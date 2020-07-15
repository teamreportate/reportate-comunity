/**
 * Reportate SRL
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
import {AuthGroup} from '../../../core/models/auth-group';
import {AccessService} from '../../access.service';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../../commons/dialogs/confirm-dialog.component';
import {AuthRole} from '../../../core/models/auth-role';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';

@Component({
  selector: 'app-user-group-role',
  templateUrl: './user-group-role.component.html',
  styleUrls: ['./user-group-role.component.scss'],
})

export class UserGroupRoleComponent extends ClicComponent implements OnInit, AfterViewInit {

  @BlockUI() blockUI: NgBlockUI;

  public searchControl: FormControl = new FormControl();
  public searchControlRole: FormControl = new FormControl();
  public searchControlNotGroup: FormControl = new FormControl();
  public searchControlNotRole: FormControl = new FormControl();

  public asignedGroupList: AuthGroup[];
  public notAsignedGroupList: AuthGroup[];
  public selectedGroupList: AuthGroup[];
  public selectedAddGroupList: AuthGroup[];
  private asignedTmp: AuthGroup[];
  private notAsignedTmp: AuthGroup[];
  public asigned: boolean;
  private toRemove: boolean;

  public asignedRolList: AuthRole[];
  public notAsignedRolList: AuthRole[];
  public selectedRolList: AuthRole[];
  public selectedAddRolList: AuthRole[];
  private asignedRoleTmp: AuthRole[];
  private notAsignedRoleTmp: AuthRole[];
  public asignedRole: boolean;
  private toRemoveRole: boolean;
  public renderRole: boolean;

  constructor(private accessService: AccessService, private matDialog: MatDialog, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    this.searchControl.valueChanges.subscribe((value: string) => {
      this.filterSearch(value.trim().toLowerCase());
    });

    this.searchControlRole.valueChanges.subscribe((value: string) => {
      this.filterSearchRole(value.trim().toLowerCase());
    });

    this.searchControlNotGroup.valueChanges.subscribe((value: string) => {
      this.filterSearchNotAasigned(value.trim().toLowerCase());
    });

    this.searchControlNotRole.valueChanges.subscribe((value: string) => {
      this.filterSearchRoleNotAsigned(value.trim().toLowerCase());
    });

  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.selectedGroupList = this.selectedAddGroupList = this.selectedRolList = this.selectedAddRolList = [];
    this.asigned = this.asignedRole = true;
    this.render = this.renderRole = false;

    const userId: string = sessionStorage.getItem('USER_ID');

    this.accessService.requestUserGroups(userId).subscribe(response => {
      this.asignedGroupList = this.asignedTmp = response.body;
      this.render = true;
      this.accessService.requestNotUserGroups(userId).subscribe(response => {
        this.notAsignedGroupList = this.notAsignedTmp = response.body;
        this.render = true;
      }, error1 => {
        this.render = true;
      });
    }, error1 => {
      this.render = true;
    });

    this.accessService.requestUserRoles(userId).subscribe(response => {
      this.asignedRolList = this.asignedRoleTmp = response.body;
      this.renderRole = true;
      this.accessService.requestNotUserRoles(userId).subscribe(response => {
        this.notAsignedRolList = this.notAsignedRoleTmp = response.body;
        this.renderRole = true;
      }, error1 => {
        this.renderRole = true;
      });
    }, error1 => {
      this.renderRole = true;
    });

  }

  asignGroup() {
    const userId: string = sessionStorage.getItem('USER_ID');
    const callback = () => { return this.accessService.requestAddUserGroups(userId, this.selectedAddGroupList); };
    const textContent = `Confirmar para agregar los grupos seleccionados al usuario`;
    this.loadRequest(callback, textContent);
  }

  removeGroups() {
    const userId: string = sessionStorage.getItem('USER_ID');
    const callback = () => { return this.accessService.requestRemoveGroupFromUser(userId, this.selectedGroupList); };
    const textContent = `Confirmar para remover los grupos asignados(no asignados) al usuario`;
    this.loadRequest(callback, textContent);
  }

  asignRoles() {
    const userId: string = sessionStorage.getItem('USER_ID');
    const callback = () => { return this.accessService.requestAddUserRoles(userId, this.selectedAddRolList); };
    const textContent = `Confirmar para agregar los grupos seleccionados al usuario`;
    this.loadRequest(callback, textContent);
  }

  removeRoles() {
    const userId: string = sessionStorage.getItem('USER_ID');
    const callback = () => { return this.accessService.requestRemoveRolesFromUser(userId, this.selectedRolList); };
    const textContent = `Confirmar para remover los roles seleccionados del usuario`;
    this.loadRequest(callback, textContent);
  }

  onCheck({selected}, toRemove: boolean) {
    this.toRemove = toRemove;
    if (this.toRemove) this.selectedGroupList = selected;
    else this.selectedAddGroupList = selected;
  }

  onCheckRole({selected}, toRemove: boolean) {
    this.toRemoveRole = toRemove;
    if (this.toRemoveRole) this.selectedRolList = selected;
    else this.selectedAddRolList = selected;
  }

  filterSearch = (value: string) => {
    this.asignedGroupList = this.asignedTmp.filter((item: AuthGroup) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  filterSearchRole = (value: string) => {
    this.asignedRolList = this.asignedRoleTmp.filter((item: AuthRole) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  filterSearchNotAasigned = (value: string) => {
    this.notAsignedGroupList = this.notAsignedTmp.filter((item: AuthGroup) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  filterSearchRoleNotAsigned = (value: string) => {
    this.notAsignedRolList = this.notAsignedRoleTmp.filter((item: AuthRole) => {
      return item.nombre.toLowerCase().indexOf(value) !== -1 ||
        item.descripcion.toLowerCase().indexOf(value) !== -1 ||
        !value;
    });
  };

  ngAfterViewInit() {
    setTimeout(()=> {
      this.table.onHeaderSelect = (event: any) => {
        if (this.table.selectAllRowsOnPage) {
          const first = this.table.bodyComponent.indexes.first;
          const last = this.table.bodyComponent.indexes.last;
          const allSelected = this.table.selected.length === (last - first);

          this.table.selected = [];

          if (!allSelected) {
            this.table.selected.push(...this.table._internalRows.slice(first, last));
          }
        } else {
          const allSelected = this.table.selected.length === this.table.rows.length;
          this.table.selected = [];
          if (!allSelected) {
            this.table.selected.push(...this.table.rows);
          }
        }

        this.table.select.emit({
          selected: this.table.selected
        });

        if (this.asigned) this.selectedGroupList = this.table.selected;
        else this.selectedAddGroupList = this.table.selected;

        if (this.asignedRole) this.selectedRolList = this.table.selected;
        else this.selectedAddRolList = this.table.selected;
        this.initialListener(this.changeDetector, this.media)
      }
    }, 1000);
  }

  loadRequest(callback: any, textContent: string) {
    const btnConfirm = 'CONFIRMAR';
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, btnConfirm}))
      .afterClosed()
      .subscribe(confirm => {
        if (confirm) {
          this.blockUI.start('Procesando solicitud...');
          callback().subscribe(response => {
            this.blockUI.stop();
            this.ngOnInit();
          }, error => this.blockUI.stop());
        }
    });
  }

  notifierError(error: any) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
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
