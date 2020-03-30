import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexLayoutModule} from '@angular/flex-layout';
import {RegisterComponent} from './change-pass/register.component';
import {AuthenticationRoutes} from './authentication.routing';
import {ErrorComponent} from './error/error.component';
import {ForgotComponent} from './forgot/forgot.component';
import {LockscreenComponent} from './lockscreen/lockscreen.component';
import {LoginComponent} from './login/login.component';
import {FormLoginComponent} from './login/form-login/form-login.component';
import {CommonsModule} from '../commons/commons.module';
import {PromptLogout} from './login/prompt-logout/prompt-logout';
import {AccessService} from '../access/access.service';
import {FooterModule} from '../core/footer/footer.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(AuthenticationRoutes),
    MatIconModule,
    MatCardModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatDialogModule,
    MatFormFieldModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    MatProgressSpinnerModule,
    CommonsModule,
    FooterModule,
  ],
  declarations: [
    ErrorComponent,
    ForgotComponent,
    LockscreenComponent,
    LoginComponent,
    FormLoginComponent,
    PromptLogout,
    RegisterComponent,
  ],
  providers: [AccessService]
})
export class AuthenticationModule {}
