import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from './core/services/http-services/authentication.service';
import {JwtHelperService} from '@auth0/angular-jwt';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit{
  title = 'back-office-ui';
  constructor(private router: Router, private authService: AuthenticationService, private helper: JwtHelperService) { }

  ngOnInit() {
    if (this.authService.isLoggedIn() && !this.helper.isTokenExpired()) {
      const last = sessionStorage.getItem('LAST_URL');
      if (last) this.router.navigate([last]);
      else this.router.navigate(['/dashboards/principal']);
    } else this.router.navigate(['/authentication/login']);
    let cssRules = `
      color: #0CA4FD;
      text-align: center;
      font-size: 30px;
      font-weight: bold;
      text-shadow: 1px 1px 5px rgb(249, 162, 34);
      filter: dropshadow(color=rgb(249, 162, 34), offx=1, offy=1);
    `;
    console.log("%cBASE-PROJECT", cssRules);
  }
}
// TODO string date to moment => pizza.cookedOn = moment(json.cookedOn, 'mm-dd-yyyy hh:mm');
