import { AfterViewInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog, throwMatDialogContentAlreadyAttachedError } from '@angular/material';
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

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.sass'],
  providers: [DashboardService]
})
export class PrincipalComponent extends ClicComponent implements OnInit, AfterViewInit {
  @BlockUI() blockUI: NgBlockUI;
  public form: FormGroup;
  mostrar: boolean;

  panelOpenState = false;

  from = new Date();
  to = new Date();

  echarts = echarts;
  echarts1 = echarts;
  myChart: any;
  myChart1: any;

  data = [];
  data1 = [];

  option: any;
  option1: any;

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


  constructor(private dialog: MatDialog, private service: DashboardService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher, private router: Router, private notifier: NotifierService) {
    super();
    this.mostrar = false;
  }
  ngAfterViewInit() {
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
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

    setTimeout(() => {
      this.getByValorationRequest();
    }, 500);
  }

  getByValorationRequest() {
    if (this.form.valid) {
      const formValue = this.form.value;
      const from = formValue.from.getDate() + '%2F' + (formValue.from.getMonth() + 1) + '%2F' + formValue.from.getFullYear();
      const to = formValue.to.getDate() + '%2F' + (formValue.to.getMonth() + 1) + '%2F' + formValue.to.getFullYear();
      this.service.byValorationRequest(from, to).subscribe(response => {

        this.data = response.body.datas;
        this.draw(this.data);
        this.draw1(this.data);
        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  draw(data1: any) {
    const data = {
      dia: ['06/04/2020', '07/04/2020', '08/04/2020', '08/04/2020', '09/04/2020', '10/04/2020', '11/04/2020', '12/04/2020'],
      sospechoso: [100, 150, 20, 10, 45, 55, 90, 55],
      confirmado: [52, 50, 50, 80, 15, 35, 90, 36],
      recuperados: [80, 35, 20, 20, 15, 55, 70, 25],
      muerto: [10, 5, 0, 2, 4, 5, 8, 12]
    };
    this.option = {
      title: {
        text: 'RESUMEN'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: []
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: data.dia
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'Sospechoso',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.sospechoso
        },
        {
          name: 'Confirmado',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.confirmado
        },
        {
          name: 'Recuperado',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.recuperados
        },
        {
          name: 'Muerto',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.muerto
        }
      ]
    };

    this.myChart = this.echarts.init(document.getElementById('main'));
    this.myChart.setOption(this.option);
  }

  draw1(data1: any) {
    this.option1 = {
      legend: {},
      tooltip: {},
      dataset: {
        dimensions: ['registrado', 'alto', 'medio', 'bajo'],
        source: data1
      },
      xAxis: { type: 'category' },
      yAxis: {},
      series: [
        { type: 'bar' },
        { type: 'bar' },
        { type: 'bar' }
      ]
    };

    this.myChart1 = this.echarts1.init(document.getElementById('main1'));
    this.myChart1.setOption(this.option1);
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

