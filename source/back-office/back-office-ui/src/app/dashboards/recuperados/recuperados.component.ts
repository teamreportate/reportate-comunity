import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';
import { Data } from '../dashboard.type.js';

@Component({
  selector: 'app-recuperados',
  templateUrl: './recuperados.component.html'
})
export class RecuperadosComponent implements OnInit {

  mostrar: boolean;
  option: any;

  echarts = echarts;
  myChart: any;

  constructor() {
    this.mostrar = false;
  }

  ngOnInit() {
  }

  draw(data: Data) {
    this.option = {
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: data.dias
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: data.curados,
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#2ECC71'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#2ECC71'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('recuperado'));
    this.myChart.setOption(this.option);
  }


}

