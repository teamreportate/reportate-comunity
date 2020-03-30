import {ClicComponent} from '../../core/utils/clic-component';
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {Page} from '../../core/utils/paginator/page';
import {MatDialog} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {SettingsService} from '../settings.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {Location} from '@angular/common';

@Component({
  selector: 'app-domain',
  templateUrl: './domain-create.component.html',
})
export class DomainCreateComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  public formValues: FormGroup;
  public domainValueList: any[];
  private count: number;
  constructor(public dialog: MatDialog, private service: SettingsService, private notifier: NotifierService,
              public location: Location,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.domainValueList = [];
    this.count = 1;
    this.initializeForm();
    this.initializeFormValues();
    this.newDomainValue();
  }

  initializeForm = () => this.form = new FormGroup({
    codigo: new FormControl(null, Validators.compose([Validators.required])),
    descripcion: new FormControl(null, Validators.compose([Validators.required])),
    listaValores: new FormControl(null, Validators.compose([Validators.required]))
  });

  initializeFormValues = () => this.formValues = new FormGroup({});

  newDomainValue() {
    if (!this.formValues.valid) {
      const notif = {error: {title: 'Nuevo Valor de Dominio', detail: 'Complete todos los valores de dominio para agregar más'}};
      this.notifierError(notif, 'warning');
      this.touchedFormValues();
      return;
    }

    this.formValues.addControl(`domainValue-${this.count}`, new FormGroup({
      valor: new FormControl(null, Validators.compose([Validators.required])),
      descripcion: new FormControl(null, Validators.compose([Validators.required]))
    }));

    this.domainValueList.unshift({
      groupControl: `domainValue-${this.count}`,
      count: this.count
    });

    this.count ++;
  }

  saveDomain() {
    if (!this.formValues.valid) {
      const notif = {error: {title: 'Registrar Dominio', detail: 'Complete todos los valores de dominio para guardar'}};
      this.notifierError(notif);
      this.touchedFormValues();
      return;
    }

    const items: any[] = [];
    const values = this.formValues.value;
    for (const key in values) items.push(values[key]);
    if (items.length < 1) {
      const notif = {error: {title: 'Registrar Dominio', detail: 'Agregue al menos un valor para el dominio'}};
      this.notifierError(notif);
      return;
    }

    this.form.get('listaValores').setValue(items);
    this.blockUI.start('Procesando solicitud...');
    this.service.requestStoreDomain(this.form.value).subscribe(response => {
      this.blockUI.stop();
      const notif = {error: {title: 'Registrar Dominio', detail: 'Dominio registrado satisfactoriamente'}};
      this.notifierError(notif, 'info');
      this.location.back();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  deleteDomainValue(domainValue) {
    const domain = this.domainValueList.find(item => item.count === domainValue.count);
    const i = this.domainValueList.indexOf(domain);
    this.domainValueList.splice(i, 1);
    this.formValues.removeControl(domainValue.groupControl);
  }

  touchedFormValues() {
    for (const controlsKey in this.form.controls) {
      if (this.form.controls[controlsKey].errors)
        this.form.controls[controlsKey].markAsTouched({onlySelf: true});
    }

    for (const controlsKey in this.formValues.controls) {
      const group: FormGroup = <FormGroup>this.formValues.get(controlsKey);
      for (const key in group.controls) {
        if (group.controls[key].errors)
          group.controls[key].markAsTouched({onlySelf: true});
      }
    }
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flex: number;
  public flexCard: number;
  onGtLgScreen() {
    this.flex = 51;
    this.flexCard = 33;
  }

  onLgScreen() {
    this.flex = 51;
    this.flexCard = 50;
  }

  onMdScreen() {
    this.flex = 70;
    this.flexCard = 50;
  }

  onSmScreen() {
    this.flex = 90;
    this.flexCard = 90;
  }

  onXsScreen() {
    this.flex = 100;
    this.flexCard = 100;
  }

  setPage(pageInfo: Page) {
  }
}
