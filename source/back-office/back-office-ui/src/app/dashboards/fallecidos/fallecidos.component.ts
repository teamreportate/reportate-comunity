import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';
import { Data } from '../dashboard.type.js';

@Component({
  selector: 'app-fallecidos',
  templateUrl: './fallecidos.component.html'
})
export class FallecidosComponent implements OnInit {

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
        data: data.decesos,
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#000000'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#000000'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('muerto'));
    this.myChart.setOption(this.option);
  }


}

