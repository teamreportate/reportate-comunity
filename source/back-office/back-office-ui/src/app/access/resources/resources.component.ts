/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {AuthResource} from '../../core/models/auth-resource';
import {ResourceService} from '../../core/services/http-services/resource.service';
import {MatDialog} from '@angular/material';
import {UpdateResourceComponent} from './components/update-resource/update-resource.component';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../core/utils/paginator/page';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {MenuItems} from '../../shared/menu-items/menu-items';
import {AccessService} from '../access.service';
import {AUTH_DATA} from '../../../environments/environment';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.scss']
})
export class ResourcesComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public resourceList: AuthResource[];
  public tmp: AuthResource[];
  private parents: AuthResource[];
  public render: boolean;
  constructor(private service: AccessService, private resourceService: ResourceService, private matDialog: MatDialog, private menuitem: MenuItems,
              private notifier: NotifierService, private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start('Recuperando lista de recursos...');
    this.parents = [];
    this.resourceService.requestResourceList().subscribe(response => {
      this.loadList(response.body);
      this.tmp = this.resourceList;
      this.toggleFisrtTimeout();
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  loadList(resources: AuthResource[]) {
    const list = [];
    for (let i = 0; i < resources.length; i++) {
      if (resources[i].idRecursoPadre) list.push(this.copyParentProp(resources[i]));
      const padre = this.parents.find(item => item.id === resources[i].idRecursoPadre.id);
      if (!padre) this.parents.push(resources[i].idRecursoPadre);
    }

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

  onUpdateParent(group: {key: string, value: AuthResource[]}) {
    console.log(group, this.parents);
    const item = this.parents.find(i => String(i.id) === String(group.key));
    console.log(item);
    if (item) this.onUpdateResource(item);
  }

  onUpdateResource = (resource: AuthResource) =>
    this.matDialog.open(UpdateResourceComponent, this.dialogConfig({resource}))
      .afterClosed().subscribe(result => {
        if (result) {
          this.ngOnInit();
          const notif = {error: {title: 'Actualizar de Recurso', detail: 'Recurso actualizado satisfactoriamente'}};
          this.notifierError(notif, 'info');
          this.updateMenu();
        }
      });

  updateMenu() {
    this.service.requestUsernMenu().subscribe(response => {
      this.menuitem.menu(response.body);
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  onResourceFilter(filterValue: string) {
    filterValue = filterValue.toLowerCase();
    this.resourceList = this.tmp.filter(item => {
      const descripcion = item.descripcion ? item.descripcion : '';
      return item.nombre.toLowerCase().indexOf(filterValue) !== -1 ||
        item.url.toLowerCase().indexOf(filterValue) !== -1 ||
        descripcion.toLowerCase().indexOf(filterValue) !== -1 ||
        item.nombrePadre.toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
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

  onGtLgScreen() {
    this.scrollH = false;
  }

  onLgScreen() {
    this.scrollH = false;
  }

  onMdScreen() {
    this.scrollH = true;
  }

  onSmScreen() {
    this.scrollH = true;
  }

  onXsScreen() {
    this.scrollH = true;
  }

  setPage(pageInfo: Page) {}
}
