import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FullComponent} from './layouts/full/full.component';
import {AppHeaderComponent} from './layouts/full/header/header.component';
import {SpinnerComponent} from './shared/spinner.component';
import {AppSidebarComponent} from './layouts/full/sidebar/sidebar.component';
import {AppBlankComponent} from './layouts/blank/blank.component';
import {FormsModule} from '@angular/forms';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HttpClientModule} from '@angular/common/http';
import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {SharedModule} from './shared/shared.module';
import {httpInterceptorProvider} from './core/providers/http-interceptor.provider';
import {CommonsModule} from './commons/commons.module';
import {JwtModule} from '@auth0/angular-jwt';
import {RouterModule} from '@angular/router';
import {AppRoutes} from './app.routing';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatDialogModule,
  MatIconModule,
  MatListModule,
  MatMenuModule,
  MatSidenavModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatTabsModule
} from '@angular/material';
import {dateFormatProvider} from './core/providers/date-format.provider';
import {BlockUIModule} from 'ng-block-ui';
import {NotifierModule, NotifierOptions} from 'angular-notifier';
import {AUTH_DATA} from '../environments/environment';
import { DynamicDirective } from './core/dinamyc.directive';
import { WserviceCardComponent } from './core/components/wservice-card.component';
import { FormatoFechaPipe } from './core/pipes/formato-fecha.pipe';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true,
  wheelSpeed: 2,
  wheelPropagation: true
};
export function tokenGetter() {
  const data = JSON.parse(sessionStorage.getItem(AUTH_DATA));
  if (data) return data.token;
  return null;
}


const customNotifierOptions: NotifierOptions = {
  position: {
    horizontal: {
      position: 'right',
      distance: 12
    },
    vertical: {
      position: 'bottom',
      distance: 12,
      gap: 10
    }
  },
  theme: 'material',
  behaviour: {
    autoHide: 5000,
    onClick: 'hide',
    onMouseover: 'pauseAutoHide',
    showDismissButton: true,
    stacking: 5
  },
  animations: {
    enabled: true,
    show: {
      preset: 'slide',
      speed: 300,
      easing: 'ease'
    },
    hide: {
      preset: 'fade',
      speed: 300,
      easing: 'ease',
      offset: 50
    },
    shift: {
      speed: 300,
      easing: 'ease'
    },
    overlap: 150
  }
};

@NgModule({
  declarations: [
    AppComponent,
    AppBlankComponent,
    FullComponent,
    AppHeaderComponent,
    SpinnerComponent,
    AppSidebarComponent,
    DynamicDirective,
    WserviceCardComponent,
    FormatoFechaPipe
  ],
  imports: [
    BrowserModule, // cualquier importacion de Modulos de angular.material.io hacerlos debajo de BrowserModule
    BrowserAnimationsModule, // importar BrowserAnimationsModule para habilitar animaciones de angular material io
    MatSidenavModule,
    MatIconModule,
    MatToolbarModule,
    MatCheckboxModule,
    MatListModule,
    MatSlideToggleModule,
    MatMenuModule,
    MatSnackBarModule,
    MatButtonModule,
    MatDialogModule,
    MatTabsModule,
    FormsModule,
    FlexLayoutModule,
    HttpClientModule,
    PerfectScrollbarModule,
    SharedModule,
    CommonsModule,
    BlockUIModule.forRoot(),
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
      }
    }),
    NotifierModule.withConfig(customNotifierOptions),
    RouterModule.forRoot(AppRoutes), // ruteo de recursos
  ],
  providers: [
    httpInterceptorProvider,
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    },
    dateFormatProvider
  ],
  bootstrap: [AppComponent]
})

export class AppModule {}
