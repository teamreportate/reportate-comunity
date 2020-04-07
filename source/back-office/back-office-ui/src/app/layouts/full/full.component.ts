import * as $ from 'jquery';
import {MediaMatcher} from '@angular/cdk/layout';
import {AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';

import {PerfectScrollbarConfigInterface} from 'ngx-perfect-scrollbar';
import {MatDialog} from '@angular/material';
import {ConfirmDialogComponent} from '../../commons/dialogs/confirm-dialog.component';
import {Router} from '@angular/router';
import {ClicComponent} from '../../core/utils/clic-component';
import {Page} from '../../core/utils/paginator/page';
import {AUTH_DATA, LOGIN_DIALOG_WIDTH} from '../../../environments/environment';

@Component({
  selector: 'app-full-layout',
  templateUrl: 'full.component.html',
  styleUrls: []
})
export class FullComponent extends ClicComponent implements OnInit, OnDestroy, AfterViewInit {
  mobileQuery: MediaQueryList;
  dir = 'ltr';
  green: boolean;
  blue: boolean;
  dark: boolean;
  minisidebar: boolean;
  boxed: boolean;
  danger: boolean;
  sidebarOpened;
  userName: string = "Usuario";

  public config: PerfectScrollbarConfigInterface = {};
  private _mobileQueryListener: () => void;

  constructor(private matDialog: MatDialog, private router: Router,
              public changeDetectorRef: ChangeDetectorRef, public media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetectorRef, this.media);
    this.mobileQuery = this.media.matchMedia('(min-width: 1500px)');
    this._mobileQueryListener = () => this.changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
    const _data = JSON.parse(sessionStorage.getItem(AUTH_DATA));
    this.userName = _data && _data.username ? _data.username : '';
  }

  ngOnDestroy = () => this.mobileQuery.removeListener(this._mobileQueryListener);

  ngAfterViewInit() {
    (<any>$('.srh-btn, .cl-srh-btn')).on('click', function() {
      (<any>$('.app-search')).toggle(200);
    });
  }

  logout = () => this.matDialog
    .open(ConfirmDialogComponent, this.confirmConfig({title: 'Cerrar Sesión', textContent: 'Confirme para cerrar sesión.'}))
    .afterClosed()
    .subscribe(result => {
      if (result) {
        sessionStorage.clear();
        window.location.reload();
      }
    });

  toDashboard = () => this.router.navigate(['dashboards/principal']);

  notifierError(error: any, type?: string) {}

  toForgot() {
    this.router.navigate(['/authentication/register']);
  }

  onGtLgScreen() {
    sessionStorage.setItem(LOGIN_DIALOG_WIDTH, JSON.stringify(this.confirmConfig(null)));
  }

  onLgScreen() {
    sessionStorage.setItem(LOGIN_DIALOG_WIDTH, JSON.stringify(this.confirmConfig(null)));
  }

  onMdScreen() {
    sessionStorage.setItem(LOGIN_DIALOG_WIDTH, JSON.stringify(this.confirmConfig(null)));
  }

  onSmScreen() {
    sessionStorage.setItem(LOGIN_DIALOG_WIDTH, JSON.stringify(this.confirmConfig(null)));
  }

  onXsScreen() {
    sessionStorage.setItem(LOGIN_DIALOG_WIDTH, JSON.stringify(this.confirmConfig(null)));
  }

  setPage(pageInfo: Page) {}
}
