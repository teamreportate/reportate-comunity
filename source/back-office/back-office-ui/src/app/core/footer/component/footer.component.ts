import {Component, OnInit} from '@angular/core';
import { YEAR_CURRENT } from '../../constantes/fechaActual';

/**
 *Created by :Leonardo Bozo Ramos
 *Date       :19/7/2019
 *Project    :unicoWeb
 *Package    :
 */
@Component({
  selector: 'app-footer',
  template:
      `
    <div class="footer-container">
      <div class="container">
        <div class="row">
<!--          <a class=" icon-footer" href="https://www.facebook.com/mc4.bo/" target="_blank">-->
<!--            <i class="fa fa-facebook-square fa-lg"> </i>-->
<!--          </a>-->
<!--          <a class="icon-footer" href="https://twitter.com/mc4bolivia" target="_blank">-->
<!--            <i class="fa fa-twitter-square fa-lg"> </i>-->
<!--          </a>-->
<!--          <a class="icon-footer" href="https://www.linkedin.com/in/mc4-srl-376a4274/" target="_blank">-->
<!--            <i class="fa fa-linkedin-square fa-lg "> </i>-->
<!--          </a>-->
          <div style="margin: auto;" class="text-center text-footer"> © {{currentYear}} MC4. TODOS LOS DERECHOS RESERVADOS V.1.0.
            <a href="http://mc4.com.bo/" target="_blank"><img src="assets/images/background/login-back.png" width="50" height="25"></a>
          </div>
        </div>
      </div>
    </div>`,
  styleUrls: ['./footer-style.css']
})
export class FooterComponent implements OnInit {
  currentYear: number;

  constructor() {
    this.currentYear = YEAR_CURRENT;
  }


  ngOnInit() {
  }
}
