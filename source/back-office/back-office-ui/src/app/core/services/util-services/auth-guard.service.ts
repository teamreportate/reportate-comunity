/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    30-01-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {AuthenticationService} from '../http-services/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor(private authService: AuthenticationService, private router: Router) {}

  canActivate() {
    const isLogged = this.authService.isLoggedIn();
    if (isLogged) return true;
    else {
      this.router.navigate(['/authentication/login']);
      return false;
    }
  }
}
