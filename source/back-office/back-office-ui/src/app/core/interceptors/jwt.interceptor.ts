import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor() {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // this.store.select(fromStore.selectAuthUser).subscribe(user => {
    //   if (user) {
    //     request = request.clone({
    //       setHeaders: {
    //         Authorization: `Bearer ${user.token}`
    //       }
    //     });
    //   }
    // });
  //   const auth = JSON.parse(localStorage.getItem('auth'));
  //   if (auth) {
  //     request = request.clone({
  //       setHeaders: {
  //         Authorization: `Bearer ${auth.token}`
  //       }
  //     });
  //   }
    return next.handle(request);
  }

}
