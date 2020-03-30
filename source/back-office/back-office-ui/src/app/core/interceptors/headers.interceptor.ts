/**
 * MC4
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    01-03-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AUTH_DATA, Constantes} from '../../../environments/environment';
import {Authentication} from '../../authentication/shared/authentication';

@Injectable()
export class HeadersInterceptor implements HttpInterceptor{

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = this.authorization(request);

    if (request.responseType && request.responseType !== 'json') return next.handle(request);

    if (!(request.body instanceof FormData)) request = this.setCommonHeaders(request);

    return next.handle(request);
  }

  authorization(request: HttpRequest<any>): HttpRequest<any> {
    if (request.url === Constantes.baseUri + 'auth/signin') return request;
    const auth: Authentication = JSON.parse(localStorage.getItem(AUTH_DATA));
    if (auth) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${auth.token}`,
        }
      });
    }
    return request;
  }

  private setCommonHeaders(request: HttpRequest<any>): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        'Content-type': 'application/json',
        'Accept': 'application/json'
      },
      responseType: 'json'
    });
  }

}
