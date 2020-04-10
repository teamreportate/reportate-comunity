import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';

@Component({
  selector: 'app-sospechoso',
  templateUrl: './sospechoso.component.html'
})
export class SospechosoComponent implements OnInit {

  @Input() data = [];

  mostrar: boolean;
  option: any;

  echarts = echarts;
  myChart: any;

  constructor() {
    this.mostrar = false;
  }

  ngOnInit() {
    this.draw();
  }

  draw() {
    this.option = {
      title: {
        text: 'SOSPECHOSOS'
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['06/04/2020', '07/04/2020', '08/04/2020', '08/04/2020', '09/04/2020', '10/04/2020', '11/04/2020', '12/04/2020']
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: [100, 150, 20, 10, 45, 55, 90, 55],
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#D4716E'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#D4716E'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('sospechoso'));
    this.myChart.setOption(this.option);
  }


}

