/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    27-01-19
 * author:  fmontero
 **/
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Parameter} from '../../core/models/parameter';
import {DataType} from '../../core/enums/data-type';
import {MatDialog} from '@angular/material';
import {MediaMatcher} from '@angular/cdk/layout';
import {ClicComponent} from '../../core/utils/clic-component';
import {Page} from '../../core/utils/paginator/page';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {SettingsService} from '../settings.service';
import {ParameterDialogComponent} from './components/parameter-dialog.component';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-parameter',
  templateUrl: './parameter.component.html',
  styles: [
    `.a-toggle {color: black; margin-right: 15px}`
  ]
})
export class ParameterComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public parameterList: any[];
  public parameterValueList: Parameter[];
  public parameterControl: FormControl;

  private temp: Parameter[] ;

  constructor( public dialog: MatDialog, private service: SettingsService, private notifier: NotifierService,
               private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    if (!this.parameterControl) {
      this.buildControl();
      this.requestParameter();
    } else this.parameterSelected(this.parameterControl.value);
  }

  requestParameter() {
    this.blockUI.start('Recuperando lista de parametros...');
    this.service.requestGroupParameterList().subscribe(response => {
      if (response.body && response.body.length > 0) this.loadList(response.body);
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  loadList(responseList: any[]) {
    responseList.sort((a, b) => a.grupo.localeCompare(b.grupo));
    this.parameterList = responseList;
    this.parameterControl.setValue(this.parameterList[0]);
  }

  buildControl() {
    this.parameterControl = new FormControl(null, Validators.required);
    this.parameterControl.valueChanges.subscribe(value => this.parameterSelected(value));
  }

  parameterSelected(domain: any) {
    this.blockUI.start('Recuperando lista de valores del parámetro...');
    this.service.requestParameterValueList(domain.id).subscribe(response => {
      this.parameterValueList = this.temp = response.body;
      this.filterParameterValue();
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  editParameter(row: Parameter) {
    this.dialog.open(ParameterDialogComponent, this.dialogConfig(row))
      .afterClosed()
      .subscribe(result => {
        if (result) {
          const notif = {error: {title: 'Actualizar Parámetro', detail: 'Parámetro actualizado satisfactoriamente.'}};
          this.notifierError(notif, 'info');
          this.ngOnInit();
        }
    });
  }

  applyFilter(filterValue: string): void {
    filterValue = filterValue.trim().toLowerCase();

    this.parameterValueList = this.temp.filter((item) => {
      return item.codigo.toLowerCase().indexOf(filterValue) !== -1 ||
        item.descripcion.toLowerCase().indexOf(filterValue) !== -1 ||
        String(item.value).toLowerCase().indexOf(filterValue) !== -1 ||
        !filterValue;
    });
  }

  filterParameterValue = () => this.parameterValueList.map(item => {
    switch (item.tipoDato) {
      case DataType.CADENA: item.value = item.valorCadena; break;
      case DataType.LOB: item.value = item.valorCadenaLob; break;
      case DataType.NUMERICO: item.value = item.valorNumerico; break;
      case DataType.BOOLEANO: item.value = item.valorBooleano; break;
      case DataType.FECHA: item.value = item.valorFecha; break;
      case DataType.UNDEFINED: item.value = DataType.UNDEFINED; break;
      default: item.value = ''; break;
    }
  });

  substringValue(value: string) {
    if (!value) value = '';
    if (value.length > 100) return value.substring(0, 100) + '........';
    return value;
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public flexSelect: number;
  onGtLgScreen() {
    this.flexSelect = 33;
  }

  onLgScreen() {
    this.flexSelect = 50;
  }

  onMdScreen() {
    this.flexSelect = 50;
  }

  onSmScreen() {
    this.flexSelect = 100;
  }

  onXsScreen() {
    this.flexSelect = 100;
  }

  setPage(pageInfo: Page) {}
}
