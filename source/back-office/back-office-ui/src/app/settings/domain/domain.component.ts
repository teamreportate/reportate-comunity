/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {DomainValue} from '../../core/models/domain-value';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Page} from '../../core/utils/paginator/page';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {Router} from '@angular/router';
import {DomainValueDialogComponent} from './components/domain-value-dialog.component';
import {ConfirmDialogComponent} from '../../commons/dialogs/confirm-dialog.component';
import {SettingsService} from '../settings.service';
import {Domain} from '../../core/models/domain';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-domain',
  templateUrl: './domain.component.html',
})
export class DomainComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public domainList: Domain[];
  public domainValueList: DomainValue[];
  public domainControl: FormControl;

  private temp: DomainValue[];
  constructor(public dialog: MatDialog, private notifier: NotifierService, private router: Router,
              private matDialog: MatDialog, private service: SettingsService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    if (!this.domainControl) {
      this.buildControl();
      this.requestDomain();
    } else this.domainSelected(this.domainControl.value);
  }

  requestDomain() {
    this.blockUI.start('Recuperando lista de dominios...');
    this.service.requestDomainList().subscribe(response => {
      if (response.body && response.body.length > 0) this.loadList(response.body);
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  loadList(responseList: Domain[]) {
    responseList.sort((a, b) => a.codigo.localeCompare(b.codigo));
    this.domainList = responseList;
    this.domainControl.setValue(this.domainList[0]);
  }

  buildControl() {
    this.domainControl = new FormControl(null, Validators.required);
    this.domainControl.valueChanges.subscribe(value => this.domainSelected(value));
  }

  domainSelected(domain: Domain) {
    this.blockUI.start('Recuperando lista de valores del dominio...');
    this.service.requestDomainValueList(domain.id).subscribe(response => {
      this.domainValueList = this.temp = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  editDomainValue = (domainValue: DomainValue) => this.openDialog(this.domainControl.value, domainValue, false);

  addDomainValue = () => this.openDialog(this.domainControl.value, null, true);

  deleteDomainValue(domainValue: DomainValue) {
    const title = 'Eliminar Valor del Dominio';
    const textContent = 'Confirmar para eliminar el valor del dominio';
    this.matDialog
      .open(ConfirmDialogComponent, this.confirmConfig({title, textContent}))
      .afterClosed()
      .subscribe(confirm => this.afterDeleted(confirm, domainValue))
  }

  afterDeleted(confirm: any, domainValue: DomainValue) {
    if (confirm) {
      this.blockUI.start('Procesando solicitud...');
      this.service.requestDeleteDomainValue(domainValue.id).subscribe(response => {
        this.blockUI.stop();
        const notif = {error: {title: 'Eliminar Valor de Dominio', detail: 'Valor de Dominio eliminado satisfactoriamente'}};
        this.notifierError(notif, 'info');
        this.ngOnInit();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  openDialog = (domain: Domain, domainValue: DomainValue, create: boolean) => this.matDialog
    .open(DomainValueDialogComponent, this.dialogConfig({domain, domainValue, create}))
    .afterClosed()
    .subscribe(result => this.afterCreateUpdate(result, create));

  afterCreateUpdate(result: any, create: boolean) {
    if (result) {
      const title = create ? 'Registrar Valor de Dominio' : 'Actualizar Valor de Dominio';
      const detail = create ? 'Valor de dominio registrado satisfactoriamente' : 'Valor de dominio actualizado satisfactoriamente';
      const notif = {error: {title, detail}};
      this.notifierError(notif, 'info');
      this.ngOnInit();
    }
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim().toLowerCase();
    this.domainValueList = this.temp.filter((item) => {
      return item.valor.toLowerCase().indexOf(filterValue) !== -1 ||
        item.descripcion.toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
    });
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;
  public flexSelect: number;
  onGtLgScreen() {
    this.flex = 10;
    this.flexSelect = 33;
  }

  onLgScreen() {
    this.flex = 15;
    this.flexSelect = 50;
  }

  onMdScreen() {
    this.flex = 15;
    this.flexSelect = 50;
  }

  onSmScreen() {
    this.flex = 100;
    this.flexSelect = 100;
  }

  onXsScreen() {
    this.flex = 100;
    this.flexSelect = 100;
  }

  setPage(pageInfo: Page) {}
}
