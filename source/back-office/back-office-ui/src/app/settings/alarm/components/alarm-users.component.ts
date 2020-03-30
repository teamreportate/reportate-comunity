/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    19-06-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MatDialog, MatDialogRef} from '@angular/material';
import {ConfirmDialogComponent} from '../../../commons/dialogs/confirm-dialog.component';
import {AlarmService} from '../../../core/services/http-services/alarm.service';
import {Alarm} from '../../../core/models/alarm';
import {FormControl, Validators} from '@angular/forms';
import {AuthUser} from '../../../core/models/AuthUser';
import {UserAlarm} from '../../../core/enums/user-alarm';
import {ClicComponent} from '../../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../../core/utils/paginator/page';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../../core/models/dto/custom-options';

@Component({
  selector: 'app-alarm-users',
  templateUrl: './alarm-users.component.html',
  providers: [AlarmService],
  animations: [
    trigger('appear', [
      state('void', style({
        opacity: 0
      })),
      transition(':enter', [
        animate(750, style({
          opacity: 1
        }))
      ])
    ])
  ]
})
export class AlarmUsersComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  static ALARM_TO_USERS = 'ALARM_TO_USERS';
  public isAddUsers: boolean;
  public alarmUserList: any[];
  public selectedUsers: AuthUser[];
  public userList: AuthUser[];
  public btnText: string;
  public currentAlarm: Alarm;
  constructor(private notifier: NotifierService, private matDialog: MatDialog, private alarmService: AlarmService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.currentAlarm = JSON.parse(localStorage.getItem(AlarmUsersComponent.ALARM_TO_USERS));
    this.render = false;
    this.isAddUsers = false;
    this.btnText = this.isAddUsers ? 'Ver Usuarios' : 'Asignar';
    this.selectedUsers = [];
    this.blockUI.start('Recuperando alarmas de usuario');
    this.alarmService.requestUsersForAlarm(String(this.currentAlarm.id)).subscribe(response => {
      const map: {asignados: any[], noAsignados: any[]} = response.body;
      this.alarmUserList = map.asignados;
      this.userList = map.noAsignados;
      this.render = true;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  switchTables() {
    this.isAddUsers = !this.isAddUsers;
    this.btnText = this.isAddUsers ? 'Ver Usuarios' : 'Asignar';
  }

  onDisableAlarmUser(row: any) {
    const textContent: string = `Por favor confirme para ${row.estado ? 'Inhabilitar' : 'Habilitar'}`;
    this.matDialog.open(ConfirmDialogComponent, this.confirmConfig({textContent, title: 'Habilitar/Inhabilitar Usuarios de Alarma'}))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          row.estado = !row.estado;
          this.alarmUserList[this.alarmUserList.indexOf(row)] = row;
        }
    });
  }

  onSaveUserAlarmsSelected() {
    const userAlarmList: UserAlarm[] = [];
    this.selectedUsers.forEach(user => {
      const userAlarm: any = {
        idSfeAlarma: this.currentAlarm,
        idSfeUsuario: user,
      };
      userAlarmList.push(userAlarm);
    });
    if (userAlarmList.length > 0) {
      this.blockUI.start('Procesando cambios...');
      this.alarmService.requestStoreUserAlarms(String(this.currentAlarm.id), userAlarmList).subscribe(response => {
        this.blockUI.stop();
        this.ngOnInit();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  filterAlarms(value: string) {}

  notifierError(error: any) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;
  onGtLgScreen() {
    this.flex = 10;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onMdScreen() {
    this.flex = 15;
  }

  onSmScreen() {
    this.flex = 100;
  }

  onXsScreen() {
    this.flex = 100;
  }

  setPage(pageInfo: Page) {}
}

@Component({
  selector: 'app-alarmUser-dialog',
  template: `
    <mat-card>
      <mat-card-header class="header-component text-white">
        <mat-card-title>Correos en copia</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <div fxLayout="row wrap">
          <div fxFlex="90">
            <mat-form-field>
              <input matInput placeholder="Email" [formControl]="this.emailControl">
              <mat-error *ngIf="this.emailControl.hasError('required') && this.emailControl.touched">El email es requerido.</mat-error>
              <mat-error *ngIf="this.emailControl.hasError('pattern') && this.emailControl.touched">Ingrese un email valido.</mat-error>
            </mat-form-field>
          </div>
          <div fxFlex="10">
            <button mat-icon-button color="accent" [disabled]="!this.emailControl.valid" (click)="this.addEmail()"><mat-icon>add_circle</mat-icon></button>
          </div>
        </div>
        <div fxLayout="row wrap" *ngFor="let email of this.emails">
          <div fxFlex="90">
            <p>{{email}}</p>
          </div>
          <div fxFlex="10">
            <button mat-icon-button color="warn" (click)="this.onDelete(email)"><mat-icon>delete</mat-icon></button>
          </div>
        </div>
      </mat-card-content>
      <mat-card-actions fxLayout="row wrap" fxLayoutAlign="end center" fxLayoutGap="5px">
        <button mat-flat-button color="warn" (click)="this.onClose()">CANCELAR</button>
        <button mat-flat-button color="primary" (click)="this.noEmails()">SIN COPIAS</button>
        <button mat-flat-button color="accent" (click)="this.withEmails()">GUARDAR</button>
      </mat-card-actions>
    </mat-card>
  `
})
export class AlarmUserDialog implements OnInit {
  public emailControl: FormControl;
  public emails: string[];
  constructor(private matDialogRef: MatDialogRef<AlarmUserDialog>) {}
  ngOnInit() {
    this.emails = [];
    this.emailControl = new FormControl(null, Validators.compose([Validators.email]));
  }

  onClose() {this.matDialogRef.close(); }
  noEmails() {this.matDialogRef.close('NO_EMAILS'); }

  withEmails() {
    let emails: string = '';
    for (let i = 0; i < this.emails.length; i++) {
      if (i===0) emails = this.emails[0];
      else emails += `|${this.emails[i]}`;
    }
    this.matDialogRef.close(emails);
  }

  addEmail() {
    this.emails.push(this.emailControl.value);
    this.emailControl.setValue(null);
  }
}
