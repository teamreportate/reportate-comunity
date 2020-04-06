/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    12-09-19
 * author:  fmontero
 **/

import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../../core/utils/clic-component';
import {Page} from '../../../core/utils/paginator/page';
import {AccessService} from '../../access.service';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {MatDialog} from '@angular/material';
import {TokenCreateComponent} from '../components/token-create.component';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {AuthUser} from '../../../core/models/AuthUser';
import {TokenUserDialogComponent} from '../components/token-user-dialog.component';
import {MuState} from '../../../core/models/mu-state';
import {ConfirmDialogComponent} from '../../../commons/dialogs/confirm-dialog.component';

@Component({
  selector: 'app-user-token',
  templateUrl: './user-token.component.html',
  styleUrls: ['./user-token.component.scss']
})

export class UserTokenComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;

  static USERID_TO_TOKENS = 'USERID_TO_TOKENS';

  public tokenList: any[];
  constructor(private service: AccessService, private matDialog: MatDialog, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    const userId: number = Number(sessionStorage.getItem(UserTokenComponent.USERID_TO_TOKENS));
    this.initialListener(this.changeDetector, this.media);
    this.blockUI.start('Recuperando lista de token...');
    this.service.requestUserTokenList(userId).subscribe(response => {
      this.blockUI.stop();
      this.tokenList = response.body;
    }, error1 => this.blockUI.stop());
  }

  newToken() {
    const userId: number = Number(sessionStorage.getItem(UserTokenComponent.USERID_TO_TOKENS));
    this.matDialog.open(TokenCreateComponent, this.confirmConfig({userId}))
      .afterClosed()
      .subscribe(created => {
        if (created) {
          this.tokenList.unshift(created);
          this.tokenList = [...this.tokenList];
          const notif = {error: {title: 'Generar token de usuario', detail: 'Token de usuario generado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
        }
    });
  }

  viewToken = (usuario: AuthUser) => this.matDialog.open(TokenUserDialogComponent, this.dialogConfig(usuario.token));

  tokenString = (token: string) => { return `${token.substring(0, (token.length / 2))} ${token.substring((token.length / 2), token.length)}`; };

  changeState(token) {
    console.log(token);
    const target = token.estado === MuState.ACTIVO ? MuState.BLOQUEADO : MuState.ACTIVO;
    const textContent: string = `Confirmar para cambiar el estado del Token: de ${token.estado} -> ${target}`;
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title:'Cambiar Estado del Token'}))
        .afterClosed()
        .subscribe(confirm => {
          if (confirm) {
            this.blockUI.start('Procesando cambio de estado del Token');
            this.service.requestChangueStateToken(token.id).subscribe(response => {
              this.blockUI.stop();
              this.ngOnInit();
              const notif = {error: {title: `${token.estado === MuState.ACTIVO ? 'Inhabilitar Token' : 'Habilitar Token'}`, detail: `${token.estado === MuState.ACTIVO ? 'Token inhabilitado satisfactoriamente.' : 'Token habilitado satisfactoriamente.'}`}};
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
