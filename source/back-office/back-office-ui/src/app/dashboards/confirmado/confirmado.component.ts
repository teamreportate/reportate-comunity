import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';
import { Data } from '../dashboard.type.js';

@Component({
  selector: 'app-confirmado',
  templateUrl: './confirmado.component.html'
})
export class ConfirmadoComponent implements OnInit {

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
      title: {
        text: 'CONFIRMADOS'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: data.dias
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: data.confirmados,
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#6D7C87'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#6D7C87'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('confirmado'));
    this.myChart.setOption(this.option);
  }


}

