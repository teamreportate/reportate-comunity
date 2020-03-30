/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    24-06-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Alarm} from '../../../core/models/alarm';
import {UserService} from '../../../core/services/http-services/user.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {AlarmService} from '../../../core/services/http-services/alarm.service';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {ClicComponent} from '../../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {NotifierService} from 'angular-notifier';
import {Page} from '../../../core/utils/paginator/page';
import {AlarmType} from '../../../core/enums/alarm-type';

@Component({
  selector: 'app-alarm-edit',
  templateUrl: './alarm-edit.component.html',
  styleUrls: ['./alarm-edit.component.scss'],
  providers: [UserService, AlarmService]
})
export class AlarmEditComponent extends ClicComponent implements OnInit {
  static ALARM_TO_EDIT = 'ALARM_TO_EDIT';
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  public currentAlarm: Alarm;
  public allAttr: any[];
  public selectedAttr: any[];
  public pps: String[];
  public isGeneric: boolean;
  constructor(private userService: UserService, private matDialog: MatDialog, private alarmService: AlarmService,
              private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.currentAlarm = JSON.parse(localStorage.getItem(AlarmEditComponent.ALARM_TO_EDIT));
    this.isGeneric = this.currentAlarm.tipoAlarma === AlarmType.ERROR_GENERICO;
    this.parseLists();
    this.form = new FormGroup({
      id: new FormControl(this.currentAlarm.id),
      nombre: new FormControl(this.currentAlarm.nombre),
      descripcion: new FormControl(this.currentAlarm.descripcion),
      tipoAlarma: new FormControl(this.currentAlarm.tipoAlarma),
      html: new FormControl(this.currentAlarm.html),
      asunto: new FormControl(this.currentAlarm.asunto),
      atributos: new FormControl(this.currentAlarm.atributos),
      atributosSeleccionados: new FormControl(this.currentAlarm.atributosSeleccionados),
      contenido: new FormControl(this.currentAlarm.contenido),
      createdDate: new FormControl(this.currentAlarm.createdDate),
      lastModifiedDate: new FormControl(this.currentAlarm.lastModifiedDate),
      createdBy: new FormControl(this.currentAlarm.createdBy),
      lastModifiedBy: new FormControl(this.currentAlarm.lastModifiedBy),
      estado: new FormControl(this.currentAlarm.estado),

      cc: new FormControl(''),
      p: new FormControl(''),

    });
  }

  parseLists() {
    this.allAttr = [];
    this.selectedAttr = [];
    this.pps = [];
    const allStrAttr: string = this.currentAlarm.atributos;
    const selectedStrAttr: string = this.currentAlarm.atributosSeleccionados;
    const pharagraphs: string = this.currentAlarm.contenido;

    const allSplited: string[] = allStrAttr ? allStrAttr.split('|') : [];
    const selectedSplited: string[] = selectedStrAttr ? selectedStrAttr.split('|') : [];
    const pSplited: string[] = pharagraphs ? pharagraphs.split('|') : [];

    allSplited.forEach(attr => {
      this.allAttr.push({name: attr, use: selectedSplited.indexOf(attr) !== -1});
    });

    selectedSplited.forEach(attr => {
      this.selectedAttr.push({name: attr, use: true});
    });

    pSplited.forEach(p => {
      this.pps.push(p);
    });
  }

  checkAttr(event: any, item: any) {
    const i = this.allAttr.indexOf(item);
    const itemSelected = this.selectedAttr.find(attr => attr.name === item.name);
    this.allAttr[i].use = event.checked;
    if (event.checked) {
      this.selectedAttr.push(this.allAttr[i]);
    } else {
      const j = this.selectedAttr.indexOf(itemSelected);
      if (j > -1) this.selectedAttr.splice(j, 1);
    }
  }

  save() {

    if (this.form.valid) {
      this.blockUI.start('Procesando solicitud...');
      this.alarmService.requestUpdateAlarm(String(this.currentAlarm.id), this.form.value).subscribe(response => {
        this.blockUI.stop();
        localStorage.setItem(AlarmEditComponent.ALARM_TO_EDIT, JSON.stringify(response.body));
        this.ngOnInit();
        const notif = {error: {title: 'Actualizar Alarma', detail: 'Se actualizaron los datos de la alarma correctamente.'}};
        this.notifierError(notif, 'info');
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
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


@Component({
  selector: 'app-list-info',
  template: `
    <mat-card>
      <mat-card-header class="header-component text-white">
        <mat-card-title>{{this.data.title}}</mat-card-title>
      </mat-card-header>
      <mat-card-content>
        <div fxLayout="row wrap">
          <div fxFlex="100" *ngFor="let item of this.data.list">
            <div fxLayout="row wrap">
              <div fxFlex="90">
                <p>{{item}}</p>
              </div>
              <div fxFlex="10">
                <button mat-icon-button color="warn" (click)="this.onDelete(item)"><mat-icon>delete</mat-icon></button>
              </div>
            </div>
          </div>
        </div>
      </mat-card-content>
      <mat-card-actions fxLayout="row wrap" fxLayoutAlign="end center" fxLayoutGap="5px">
        <button mat-flat-button color="primary" (click)="this.onClose()">Aceptar</button>
      </mat-card-actions>
    </mat-card>
  `
})
export class ListDialog implements OnInit {

  constructor(public dialogRef: MatDialogRef<ListDialog>, @Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit() {}

  onDelete(item) {
    this.data.list.splice(this.data.list.indexOf(item), 1);
  }

  onClose() {
    this.dialogRef.close(this.data.list);
  }
}
