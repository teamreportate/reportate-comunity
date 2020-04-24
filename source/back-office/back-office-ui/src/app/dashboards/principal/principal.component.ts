import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import echarts from '../../../assets/js/echarts.min.js';

import { DashboardService } from '../../core/services/http-services/dashboard.service';
import { ClicComponent } from '../../core/utils/clic-component';
import { MediaMatcher } from '@angular/cdk/layout';
import { NotifierService } from 'angular-notifier';
import { Page } from '../../core/utils/paginator/page';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { CustomOptions } from '../../core/models/dto/custom-options';
import { Filter, Basic, Data, DataTotal, DataTotalNumber } from '../dashboard.type.js';
import { Department, Municipaly, SaludCentre } from 'src/app/access/users/user.type.js';
import { AccessService } from 'src/app/access/access.service.js';
import { EnfermedadService } from 'src/app/core/services/http-services/enfermedad.service';
import { ResumeComponent } from '../resume/resume.component';
import { ConfirmadoComponent } from '../confirmado/confirmado.component';
import { RecuperadosComponent } from '../recuperados/recuperados.component';
import { SospechosoComponent } from '../sospechoso/sospechoso.component';
import { FallecidosComponent } from '../fallecidos/fallecidos.component';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.scss'],
  providers: [DashboardService, AccessService, EnfermedadService]
})
export class PrincipalComponent extends ClicComponent implements OnInit, AfterViewInit {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  mostrar: boolean;

  panelOpenState = false;

  from = new Date();
  to = new Date();
  filter: Filter = new Filter();

  echarts = echarts;
  myChart: any;

  searchValue = '';

  data: Data = new Data();
  resumen: Data = new Data();

  option: any;

  departments: Department[] = [];
  municipalities: Municipaly[] = [];
  saludCentres: SaludCentre[] = [];
  enfermedades: Basic[] = [];

  totalsList: DataTotal = new DataTotal();

  total: DataTotalNumber = new DataTotalNumber();

  percentSospechosos = 0;
  percentDescartados = 0;
  percentConfirmados = 0;
  percentRecuperados = 0;
  percentFallecidos = 0;
  percentActivos = 0;

  @ViewChild(ResumeComponent) resumeComponent: ResumeComponent;
  @ViewChild(ConfirmadoComponent) confirmadoComponent: ConfirmadoComponent;
  @ViewChild(RecuperadosComponent) recuperadosComponent: RecuperadosComponent;
  @ViewChild(SospechosoComponent) sospechosoComponent: SospechosoComponent;
  @ViewChild(FallecidosComponent) fallecidosComponent: FallecidosComponent;

  constructor(
    private service: DashboardService,
    private accessService: AccessService,
    private enfermedadService: EnfermedadService,
    private changeDetector: ChangeDetectorRef,
    private media: MediaMatcher,
    private notifier: NotifierService) {
    super();
    this.mostrar = false;
  }
  ngAfterViewInit() {
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.getSetting();
    this.initForm();
  }

  initForm() {
    this.from = new Date(this.from.setDate(this.from.getDate() - 30));
    this.form = new FormGroup({
      from: new FormControl(this.from, Validators.compose([Validators.required])),
      to: new FormControl(this.to, Validators.compose([Validators.required])),
    }, {
    });
    this.updateData();
  }

  getSetting() {
    this.blockUI.start('Recuperando lista de departamentos y municipios');
    this.accessService.requestCompleteDepartmentsList().subscribe(response => {
      this.departments = response.body.departamentos;
      this.municipalities = response.body.municipios;
      this.saludCentres = response.body.centrosSalud;
      this.getEnfermedades();
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getEnfermedades() {
    this.blockUI.start('Recuperando lista de enfermedades');
    this.enfermedadService.getEnfermedades().subscribe(response => {
      this.enfermedades = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }


  getTotals() {
    this.blockUI.start('Actualizando totales');
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();

      this.service.reportTotals(from, to, this.filter).subscribe(response => {

        this.totalsList = response.body;

        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  getTotalNumbers() {
    this.blockUI.start('Actualizando totales');
    this.service.reportTotalNumbers(this.filter).subscribe(response => {

      this.total = response.body;
      this.percentConfirmados = (this.total.confirmados * 100) / this.total.confirmados;
      this.percentActivos = (this.total.positivos * 100) / this.total.confirmados;
      this.percentRecuperados = (this.total.recuperados * 100) / this.total.confirmados;
      this.percentFallecidos = (this.total.decesos * 100) / this.total.confirmados;


      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getByValorationRequest() {
    this.blockUI.start('Actualizando los datos');
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();
      this.service.byValorationRequest(from, to).subscribe(response => {

        this.draw(response.body.datas);
        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  getReportWithFiltersRequest() {
    this.blockUI.start('Actualizando gráficos...');
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();

      this.service.reportWithFiltersRequest(from, to, this.filter).subscribe(response => {
        this.data = response.body;
        this.confirmadoComponent.draw(this.data);
        this.sospechosoComponent.draw(this.data);
        this.recuperadosComponent.draw(this.data);
        this.fallecidosComponent.draw(this.data);

        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  reportResumenWithFiltersRequest() {
    this.blockUI.start('Actualizando gráficos...');
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();

      this.service.reportResumenWithFiltersRequest(from, to, this.filter).subscribe(response => {
        this.resumen = response.body;
        this.resumeComponent.draw(this.resumen);
        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  updateData() {
    this.getTotalNumbers();
    this.getByValorationRequest();
    this.reportResumenWithFiltersRequest();
    this.getReportWithFiltersRequest();
    this.getTotals();
  }

  draw(data: any[]) {
    this.option = {
      legend: {},
      tooltip: {},
      dataset: {
        dimensions: ['registrado', 'alto', 'medio', 'bajo'],
        source: data
      },
      xAxis: { type: 'category' },
      yAxis: {},
      series: [
        {
          type: 'bar',
          color: {
            colorStops: [{
              offset: 0, color: '#FF0000'
            }]
          }
        },
        {
          type: 'bar',
          color: {
            colorStops: [{
              offset: 0, color: '#FFA500'
            }]
          }
        },
        {
          type: 'bar',
          color: {
            colorStops: [{
              offset: 0, color: '#008000'
            }]
          }
        }
      ]
    };
    this.myChart = this.echarts.init(document.getElementById('main'));
    this.myChart.setOption(this.option);
  }

  print() {
    window.print();
  }








  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = { type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl };
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

