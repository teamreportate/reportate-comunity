/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    19-06-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Alarm} from '../../core/models/alarm';
import {Router} from '@angular/router';
import {AlarmEditComponent} from './components/alarm-edit.component';
import {AlarmService} from '../../core/services/http-services/alarm.service';
import {AlarmUsersComponent} from './components/alarm-users.component';
import {Page} from '../../core/utils/paginator/page';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {MatDialog} from '@angular/material';
import {SettingsService} from '../settings.service';
import {UserPrincipalComponent} from './components/user-principal.component';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';

@Component({
  selector: 'app-alarm-component',
  templateUrl: './alarm.component.html',
  providers: [AlarmService]
})
export class AlarmComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public alarmList: any[];
  public tmp: any[];
  constructor(private settingService: SettingsService, private router: Router, private alarmService: AlarmService, private matDialog: MatDialog,
              private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.alarmList = [];
    this.blockUI.start('Recuperando lista de alarmas...');
    this.alarmService.requestAlarmList().subscribe(response => {
      this.alarmList = this.tmp = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  onAlarmEdit(alarm: any) {
    localStorage.setItem(AlarmEditComponent.ALARM_TO_EDIT, JSON.stringify(alarm));
    this.router.navigate(['configuracion/alarmEdit']);
  }

  filterAlarms(filterValue: string) {
    filterValue = filterValue.toLowerCase();
    this.alarmList = this.tmp.filter(item => {
      const description: string = item.descripcion ? item.descripcion : '';
      return item.nombre.toLowerCase().indexOf(filterValue) !== -1 ||
        item.tipoAlarma.toLowerCase().indexOf(filterValue) !== -1 ||
        description.toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
    });
  }

  onAlarmUsers(alarm: Alarm) {
    localStorage.setItem(AlarmUsersComponent.ALARM_TO_USERS, JSON.stringify(alarm));
    this.router.navigate(['configuracion/alarmUsers'])
  }

  setPrincipal(alarm: Alarm) {
    this.blockUI.start('Procesando solicitud...');
    this.settingService.requestAlarmUsers(alarm.id).subscribe(response => {
      this.blockUI.stop();
      const alarmUser = response.body;
      if (alarmUser['asignados'].length > 0) {
        this.matDialog.open(UserPrincipalComponent, this.dialogConfig({users: alarmUser.asignados, alarmId: alarm.id}))
          .afterClosed()
          .subscribe(result => {
            if (result) {
              const notif = {error: {title: 'Asignar Usuario Principal', detail: 'Usuario principal asignado satisfactoriamente'}};
              this.notifierError(notif, 'info');
            }
          });
      } else {
        const notif = {error: {title: 'Asignar Usuario Principal', detail: 'No se tienen usuarios asignados a esta alarma. Asigne al menos uno para definir un principal'}};
        this.notifierError(notif, 'warning');
      }
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });

  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;
  onGtLgScreen() {
    this.flex = 90;
  }

  onLgScreen() {
    this.flex = 90;
  }

  onMdScreen() {
    this.flex = 90;
  }

  onSmScreen() {
    this.flex = 100;
  }

  onXsScreen() {
    this.flex = 100;
  }

  setPage(pageInfo: Page) {}
}
