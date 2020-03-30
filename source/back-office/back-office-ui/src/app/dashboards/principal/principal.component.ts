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
  constructor(private dialog: MatDialog, private dashboardService: DashboardService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher, private router: Router, private notifier: NotifierService) {
    super();
    this.mostrar = false;
  }
  ngAfterViewInit() {
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
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

