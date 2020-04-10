import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';

@Component({
  selector: 'app-muertos',
  templateUrl: './muertos.component.html'
})
export class MuertosComponent implements OnInit {

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
        text: 'MUERTOS'
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
        data: [10, 5, 0, 2, 4, 5, 8, 12],
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#E1A793'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#E1A793'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('muerto'));
    this.myChart.setOption(this.option);
  }


}

