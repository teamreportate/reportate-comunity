import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ChartEvent, ChartType} from 'ng-chartist/dist/chartist.component';
import {MatDialog} from '@angular/material';
import {DashboardService} from '../../core/services/http-services/dashboard.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as moment from 'moment';
import {ClicComponent} from '../../core/utils/clic-component';
import {MediaMatcher} from '@angular/cdk/layout';
import {Router} from '@angular/router';
import {NotifierService} from 'angular-notifier';
import {Page} from '../../core/utils/paginator/page';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {BlockUI, NgBlockUI} from 'ng-block-ui';

import echarts from '../../../assets/js/echarts.min.js';

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


  echarts = echarts;
  myChart: any;

  option = {
    legend: {},
    tooltip: {},
    dataset: {
        dimensions: ['registrado', 'Alto', 'Medio', 'Bajo'],
        source: [
            {registrado: '01/04/2020', 'Alto': 43, 'Medio': 85, 'Bajo': 93},
            {registrado: '02/04/2020', 'Alto': 83, 'Medio': 73, 'Bajo': 55},
            {registrado: '03/04/2020', 'Alto': 86, 'Medio': 65, 'Bajo': 82},
            {registrado: '04/04/2020', 'Alto': 52, 'Medio': 53, 'Bajo': 39},
            {registrado: '05/04/2020', 'Alto': 72, 'Medio': 13, 'Bajo': 19},
            {registrado: '05/04/2020', 'Alto': 32, 'Medio': 10, 'Bajo': 29},
            {registrado: '07/04/2020', 'Alto': 42, 'Medio': 55, 'Bajo': 30},
            {registrado: '08/04/2020', 'Alto': 52, 'Medio': 53, 'Bajo': 39},
            {registrado: '09/04/2020', 'Alto': 72, 'Medio': 53, 'Bajo': 39},
            {registrado: '10/04/2020', 'Alto': 72, 'Medio': 12, 'Bajo': 30}
        ]
    },
    xAxis: {type: 'category'},
    yAxis: {},
    series: [
        {type: 'bar'},
        {type: 'bar'},
        {type: 'bar'}
    ]
};


  constructor(private dialog: MatDialog, private dashboardService: DashboardService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher, private router: Router, private notifier: NotifierService) {
    super();
    this.mostrar = false;
  }
  ngAfterViewInit() {
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);

    this.myChart = this.echarts.init(document.getElementById('main'));
    this. myChart.setOption(this.option);
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

