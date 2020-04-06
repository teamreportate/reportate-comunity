import {Injectable} from '@angular/core';
import {ResourceService} from '../../core/services/util-services/resource.service';

export interface BadgeItem {
  type?: string;
  value?: string;
}
export interface Saperator {
  name?: string;
  type?: string;
}
export interface SubChildren {
  state?: string;
  name?: string;
  type?: string;
}
export interface ChildrenItems {
  state?: string;
  name?: string;
  type?: string;
  child?: SubChildren[];
}

export interface Menu {
  parent?: string;
  state?: string;
  name?: string;
  type?: string;
  icon?: string;
  badge?: BadgeItem[];
  saperator?: Saperator[];
  children?: ChildrenItems[];
}

@Injectable()
export class MenuItems {
  public items:Menu[] = [];
  constructor(private resourceService: ResourceService) {
    const menu = JSON.parse(sessionStorage.getItem('objT'));
    if (menu) this.items = this.resourceService.getMenu(menu);
  }

  menu(menu) {
    sessionStorage.setItem('objT', JSON.stringify({menu}));
    this.items = this.resourceService.getMenu({menu});
  }
}
