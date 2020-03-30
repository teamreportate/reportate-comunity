import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ConfirmDialogComponent} from './dialogs/confirm-dialog.component';
import {
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatTooltipModule
} from '@angular/material';
import {FlexLayoutModule} from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {InputDialogComponent} from './dialogs/input-dialog.component';
import {FailedComponent} from './notifications/failed.component';
import {SuccessComponent} from './notifications/success.component';
import {HttpStatus} from '../core/utils/http-util/http-status';
import {HeaderPageComponent} from './header-page.component';
import {ClicPageComponent} from './clic-page.component';
import {JobCardComponent} from './job/job-card/job-card.component';
import {JobHistoryComponent} from './job/job-history/job-history.component';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {LoginDialogComponent} from './dialogs/login-dialog.component';
import {DialogFileComponent} from './dialogs/dialog-file/dialog-file.component';
import {InvoiceErrosDialogComponent} from './dialogs/invoice-erros-dialog.component';
import {PdfViewerModule} from 'ng2-pdf-viewer';
import {CdkAccordionModule} from '@angular/cdk/accordion';
import {XmlPrettyPipe} from '../core/pipes/xml-pretty.pipe';
import {RequestResponseListComponent} from './request-response-list/request-response-list.component';
import {CustomNotificationComponent} from './custom-notification.component';
import {DeleteUnderline} from '../core/pipes/delete_underline.pipe';
import {MoneyPipe} from './pipes/money.pipe';
import {DecamelizePipe} from './pipes/decamelize.pipe';
import {ConfirmGeneracionHuellaComponent} from './dialogs/confirm-generacion-huella.component';
import {ValidarSinEspaciosDirective} from './commons.directive';
import {HistoryDetailComponent} from './job/history-detail.component';
import {CommonService} from './common.service';
import {LoginComponent} from './dialogs/login.component';
import {LoginCuisComponent} from './dialogs/login-cuis.component';

@NgModule({
  declarations: [
    ConfirmDialogComponent,
    InputDialogComponent,
    SuccessComponent,
    FailedComponent,
    ClicPageComponent,
    HeaderPageComponent,
    JobCardComponent,
    JobHistoryComponent,
    LoginDialogComponent,
    DialogFileComponent,
    InvoiceErrosDialogComponent,
    XmlPrettyPipe,
    RequestResponseListComponent,
    CustomNotificationComponent,
    DeleteUnderline,
    MoneyPipe,
    DecamelizePipe,
    ConfirmGeneracionHuellaComponent,
    ValidarSinEspaciosDirective,
    HistoryDetailComponent,
    LoginComponent,
    LoginCuisComponent
  ],
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatDividerModule,
    MatIconModule,
    FlexLayoutModule,
    FormsModule,
    NgxDatatableModule,
    ReactiveFormsModule,
    MatDividerModule,
    PdfViewerModule,
    CdkAccordionModule,
    MatDialogModule,
    MatExpansionModule,
    MatTooltipModule
  ],
  entryComponents: [
    ConfirmDialogComponent,
    InputDialogComponent,
    SuccessComponent,
    FailedComponent,
    LoginDialogComponent,
    DialogFileComponent,
    InvoiceErrosDialogComponent,
    CustomNotificationComponent,
    ConfirmGeneracionHuellaComponent,
    HistoryDetailComponent,
    LoginComponent,
    LoginCuisComponent
  ],
  providers: [HttpStatus, CommonService],
  exports: [
    ClicPageComponent,
    HeaderPageComponent,
    JobCardComponent,
    JobHistoryComponent,
    DialogFileComponent,
    InvoiceErrosDialogComponent,
    XmlPrettyPipe,
    RequestResponseListComponent,
    CustomNotificationComponent,
    DeleteUnderline,
    MoneyPipe,
    DecamelizePipe,
    ValidarSinEspaciosDirective,
    HistoryDetailComponent,
    LoginComponent,
    LoginCuisComponent
  ]
})
export class CommonsModule { }
