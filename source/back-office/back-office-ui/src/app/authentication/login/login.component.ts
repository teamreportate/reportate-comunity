/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-01-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Authentication} from '../shared/authentication';
import {AuthenticationService} from '../../core/services/http-services/authentication.service';
import {Router} from '@angular/router';
import {ClicComponent} from '../../core/utils/clic-component';
import {Page} from '../../core/utils/paginator/page';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {MediaMatcher} from '@angular/cdk/layout';
import {NotifierService} from 'angular-notifier';
import {MatDialog} from '@angular/material';
import {JwtHelperService} from '@auth0/angular-jwt';
import {AccessService} from '../../access/access.service';
import {MenuItems} from '../../shared/menu-items/menu-items';
import {AUTH_DATA} from '../../../environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent extends ClicComponent implements OnInit {
  public isLoad: boolean = false;
  public error: string = null;
  constructor(private service: AccessService, private menuitem: MenuItems, private notifier: NotifierService, private router: Router, public jwtHelper: JwtHelperService,
              private authService: AuthenticationService, private matDialog: MatDialog,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.matDialog.closeAll();
    this.initialListener(this.changeDetector, this.media);
  }

  onLogin(credentials: Authentication) {
    this.isLoad = true;
    this.error = null;
    this.authService.requestStorageLogin(credentials).subscribe(response => {
      sessionStorage.clear();
      sessionStorage.setItem(AUTH_DATA, JSON.stringify(response.body));
      const token = this.jwtHelper.decodeToken(response.body.token);
       // this.router.navigate(['/access/groups']);
      this.service.requestUsernMenu(credentials.username).subscribe(responsemenu => {
        this.menuitem.menu(responsemenu.body);
        // sessionStorage.setItem('objT', JSON.stringify(responsemenu.body));
        if (token.tipoUsuario === 'MEDICO') {
          this.router.navigate(['/seguimiento/diagnostico']);
        } else {
          this.router.navigate(['/dashboards/principal']);
        }
        this.isLoad = false;
        this.error = null;
      }, error => {
        if (error) this.notifierError(error);
      });
    }, (error) => {
      this.isLoad = false;
      this.error = error && error.error ? error.error.message : null;
      if (error) this.notifierError(error);
    });
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {
  }

  onLgScreen() {
  }

  onMdScreen() {
  }

  onSmScreen() {
  }

  onXsScreen() {
  }

  setPage(pageInfo: Page) {
  }
}
