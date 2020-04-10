import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';

@Component({
  selector: 'app-confirmado',
  templateUrl: './confirmado.component.html'
})
export class ConfirmadoComponent implements OnInit {

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
        text: 'CONFIRMADOS'
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
        data: [52, 50, 50, 80, 15, 35, 90, 36],
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

