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

  from = new Date();
  to = new Date();

  echarts = echarts;
  myChart: any;

  data = [];

  option: any;


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

        this.blockUI.stop();
      }, error => {
        this.blockUI.stop();
        if (error) this.notifierError(error);
      });
    }
  }

  draw(data: any[]) {
    this.option = {
      legend: {},
      tooltip: {},
      dataset: {
        dimensions: ['registrado', 'Alto', 'Medio', 'Bajo'],
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

