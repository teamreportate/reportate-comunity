/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-03-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HttpStatus} from '../utils/http-util/http-status';
import {MatDialog} from '@angular/material';
import {Router} from '@angular/router';
import {NotifierService} from 'angular-notifier';
import {Constantes, LOGIN_DIALOG_WIDTH} from '../../../environments/environment';
import {LoginComponent} from '../../commons/dialogs/login.component';
import {Constants} from '../constants';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

  constructor(private httpStatus: HttpStatus, private notifier: NotifierService, private matDialog: MatDialog, private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(httpError => {
      if(httpError instanceof HttpErrorResponse) {
        if (httpError.status === 0) {
          this.notifier.show({type: 'error', message: 'No se puede establecer la conexión con el servidor.'});
          return throwError(null);
        }

        if (httpError.status === 404 || httpError.status === 405) {
          this.notifier.show({type: 'error', message: 'No se encontró el servicio solicitado.'});
          return throwError(null);
        }

        if (httpError.status === 403 && this.router.url !== Constants.LOGIN_PATH) {
          this.loginTimeout();
          return throwError(null);
        }
        if (httpError.status === 403 && this.router.url === Constants.LOGIN_PATH) return throwError(httpError);
        return throwError(httpError);
      } else {
        this.notifier.show({type: 'error', message: 'Ha ocurrido un error inesperado.'});
        return throwError(httpError);
      }
    }));
  }

  loginTimeout() {
    const config = JSON.parse(sessionStorage.getItem(LOGIN_DIALOG_WIDTH));
    this.matDialog.open(LoginComponent, config).afterClosed().subscribe((result) => {
      if (!result) this.router.navigate(['/authentication/login']);
    });
  }

}
