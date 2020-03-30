import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {SfeProcess} from '../../core/enums/sfe-process';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MediaMatcher} from '@angular/cdk/layout';
import {BinnacleService} from '../../core/services/http-services/binnacle.service';
import * as moment from 'moment';
import {ClicComponent} from '../../core/utils/clic-component';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {CustomOptions} from '../../core/models/dto/custom-options';

@Component({
  selector: 'app-binnacle',
  templateUrl: './binnacle.component.html',
  providers: [BinnacleService]
})
export class BinnacleComponent extends ClicComponent implements OnInit {
  @BlockUI() blockUI: NgBlockUI;
  public render: boolean;
  public processList: string[];
  public form: FormGroup;
  private proceso: SfeProcess = SfeProcess.ADMINISTRACION;
  private startDate: any;
  private endDate: any;
  private message: string;
  public today: any;
  constructor(private binnacleService: BinnacleService, private formBuilder: FormBuilder, public changeDetector: ChangeDetectorRef, public media: MediaMatcher,
              private notifier: NotifierService) {
    super();
    moment.locale('es-BO');
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.form = this.initialForm();
    this.today = moment().toDate();
    this.render = false;
    this.initializePage(15, true);
    this.processList = [];
    for (let sfeProcessKey in SfeProcess) this.processList.push(sfeProcessKey);
    this.processList.sort((a, b) => a.localeCompare(b));
  }

  onProcessChange(param: any) {
    this.proceso = this.form.get('tipoProceso').value;
  }

  onSearch() {
    if (this.form.valid) {
      this.updateDate();
      if (this.endDate.isBefore(this.startDate)) {
        const notif = {error: {title: 'Filtrar Bitácora', detail: 'La fecha final no puede ser menor que la fecha inicial'}};
        this.notifierError(notif, 'warning');
        return;
      }
      this.render = false;
      this.initializePage(20, true);
    }
  }

  private initialForm(): FormGroup {
    this.startDate = moment().subtract(30, 'days');
    this.endDate = moment();
    this.message = '';
    return this.formBuilder.group({
      tipoProceso: new FormControl(SfeProcess.ADMINISTRACION, Validators.compose([Validators.required])),
      fechaInicial: new FormControl(this.startDate.toDate(), Validators.compose([Validators.required])),
      fechaFinal: new FormControl(this.endDate.toDate(), Validators.compose([Validators.required])),
      message: new FormControl(null)
    });
  }

  setPage(pageInfo: any) {
    this.pageControl.number = pageInfo.offset;

    this.blockUI.start('Recuperando lista de bitacora...');
    this.binnacleService.requestLogs(this.startDate.format('DD-MM-YYYY'), this.endDate.format('DD-MM-YYYY'), this.proceso,this.message, this.pageControl.number, this.pageControl.size).subscribe(response => {
      this.pageControl = response.body;
      this.render = true;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  updateDate() {
    this.startDate = moment(moment.utc(this.form.get('fechaInicial').value));
    this.endDate = moment(moment.utc(this.form.get('fechaFinal').value));
    this.message = !this.form.get('message').value ? '' : this.form.get('message').value;
    this.proceso = this.form.get('tipoProceso').value;
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl};
      this.notifier.show(customOptions);
    }
  }

  public filterFlex;
  onXsScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onSmScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onMdScreen() {
    this.filterFlex = 50;
    this.scrollH = true;
  }

  onLgScreen() {
    this.filterFlex = 33;
    this.scrollH = false;
  }

  onGtLgScreen() {
    this.filterFlex = 33;
    this.scrollH = false;
  }
}
