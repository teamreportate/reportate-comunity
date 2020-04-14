import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import * as moment from 'moment';
import {CustomOptions} from '../../../../../core/models/dto/custom-options';
import {Page} from '../../../../../core/utils/paginator/page';
import {ClicComponent} from '../../../../../core/utils/clic-component';
import {DiagnosticoService} from '../../../../../core/services/http-services/diagnostico.service';

@Component({
  selector: 'app-list-sintomas',
  templateUrl: './list-sintomas.component.html',
  styleUrls: ['./list-sintomas.component.sass']
})
export class ListSintomasComponent extends ClicComponent implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;

  listSintomas: any[];

  public form: FormGroup;
  flex: number;

  constructor(private service: DiagnosticoService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<ListSintomasComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
    moment.locale('es-BO');
  }


  ngOnInit() {
    this.service.getDiagnostico(this.data.id).subscribe(response => {
      this.listSintomas = response.body;
    });
  }

  cancel = () => this.dialogRef.close(null);

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      // tslint:disable-next-line:max-line-length
      const customOptions: CustomOptions = {
        type: type ? type : 'error',
        tile: error.error.title,
        message: error.error.detail,
        template: this.customNotificationTmpl
      };
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {
    this.flex = 10;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onMdScreen() {
    this.flex = 25;
  }

  onSmScreen() {
    this.flex = 100;
  }

  onXsScreen() {
    this.flex = 100;
  }

  setPage(pageInfo: Page) {
  }
}
