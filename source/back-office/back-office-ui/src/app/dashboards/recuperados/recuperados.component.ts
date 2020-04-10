import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';

@Component({
  selector: 'app-recuperados',
  templateUrl: './recuperados.component.html'
})
export class RecuperadosComponent implements OnInit {

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
        text: 'RECUPERADOS'
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
        data: [80, 35, 20, 20, 15, 55, 70, 25],
        type: 'line',
        areaStyle: {
          color: {
            colorStops: [{
              offset: 0, color: '#90BCC2'
            }]
          }
        },
        color: {
          colorStops: [{
            offset: 0, color: '#90BCC2'
          }]
        }
      }]
    };

    this.myChart = this.echarts.init(document.getElementById('recuperado'));
    this.myChart.setOption(this.option);
  }


}

