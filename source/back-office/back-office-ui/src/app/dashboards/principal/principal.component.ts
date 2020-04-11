import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import echarts from '../../../assets/js/echarts.min.js';

import { DashboardService } from '../../core/services/http-services/dashboard.service';
import { ClicComponent } from '../../core/utils/clic-component';
import { MediaMatcher } from '@angular/cdk/layout';
import { NotifierService } from 'angular-notifier';
import { Page } from '../../core/utils/paginator/page';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { CustomOptions } from '../../core/models/dto/custom-options';
import { Filter, Basic, Data } from '../dashboard.type.js';
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
  styleUrls: ['./principal.component.sass'],
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

  data: Data = new Data();

  option: any;

  departments: Department[] = [];
  municipalities: Municipaly[] = [];
  saludCentres: SaludCentre[] = [];
  enfermedades: Basic[] = [];

  list = [
    { departamento: 'Santa Cruz', sospechoso: 150, confirmado: 20, recuperado: 8, muerto: 1 },
    { departamento: 'Cochabamba', sospechoso: 90, confirmado: 15, recuperado: 5, muerto: 0 },
    { departamento: 'Beni', sospechoso: 88, confirmado: 10, recuperado: 3, muerto: 0 },
    { departamento: 'Pando', sospechoso: 85, confirmado: 10, recuperado: 3, muerto: 0 },
    { departamento: 'La paz', sospechoso: 79, confirmado: 9, recuperado: 4, muerto: 0 },
    { departamento: 'Tarija', sospechoso: 55, confirmado: 5, recuperado: 2, muerto: 0 },
    { departamento: 'Sucre', sospechoso: 55, confirmado: 6, recuperado: 2, muerto: 0 },
    { departamento: 'Potosí', sospechoso: 30, confirmado: 3, recuperado: 2, muerto: 0 },
    { departamento: 'Oruro', sospechoso: 20, confirmado: 1, recuperado: 1, muerto: 0 },
  ];

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
    this.from = new Date(this.from.setDate(this.from.getDate() - 3));
    this.form = new FormGroup({
      from: new FormControl(this.from, Validators.compose([Validators.required])),
      to: new FormControl(this.to, Validators.compose([Validators.required])),
    }, {
    });
    this.getByValorationRequest();
    this.getReportWithFiltersRequest();
  }

  getSetting() {
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
    this.enfermedadService.getEnfermedades().subscribe(response => {
      this.enfermedades = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getByValorationRequest() {
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
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();

      this.service.reportWithFiltersRequest(from, to, this.filter).subscribe(response => {

        this.data = response.body;
        this.resumeComponent.draw(this.data);
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

  draw(data: any[]) {
    this.option = {
      title: {
        text: '' + (this.from.getDate() + '/' + (this.from.getMonth() + 1) + '/' + this.from.getFullYear()) +
        '-' + (this.to.getDate() + '/' + (this.to.getMonth() + 1) + '/' + this.to.getFullYear())
      },
      legend: {},
      tooltip: {},
      dataset: {
        dimensions: ['registrado', 'alto', 'medio', 'bajo'],
        source: data
      },
      xAxis: { type: 'category' },
      yAxis: {},
      series: [
        { type: 'bar' },
        { type: 'bar' },
        { type: 'bar' }
      ]
    };
    this.myChart = this.echarts.init(document.getElementById('main'));
    this.myChart.setOption(this.option);
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

