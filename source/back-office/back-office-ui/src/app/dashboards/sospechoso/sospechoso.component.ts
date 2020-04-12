import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';
import { Data } from './../dashboard.type';

@Component({
  selector: 'app-sospechoso',
  templateUrl: './sospechoso.component.html'
})
export class SospechosoComponent implements OnInit {

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
        data: data.sospechosos,
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#01C0C8'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#01C0C8'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('sospechoso'));
    this.myChart.setOption(this.option);
  }


}

