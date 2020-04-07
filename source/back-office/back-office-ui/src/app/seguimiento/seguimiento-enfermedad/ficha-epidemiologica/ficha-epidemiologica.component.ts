import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-ficha-epidemiologica',
  templateUrl: './ficha-epidemiologica.component.html',
  styleUrls: ['./ficha-epidemiologica.component.sass']
})
export class FichaEpidemiologicaComponent implements OnInit {
  idPaciente = this.route.snapshot.paramMap.get('idPaciente');

  constructor( private route: ActivatedRoute) { }

  ngOnInit() {
  }

}
