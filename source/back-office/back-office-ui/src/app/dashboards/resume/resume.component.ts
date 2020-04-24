import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';
import { Data } from '../dashboard.type.js';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html'
})
export class ResumeComponent implements OnInit {

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
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: ['Confirmados', 'Activos', 'Recuperados', 'Decesos', 'Sospechosos',]
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: data.dias
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'Confirmados',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#FF0000'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#FF0000'
            }]
          },
          data: data.confirmados
        },
        {
          name: 'Activos',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#FFA500'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#FFA500'
            }]
          },
          data: data.positivos
        },
        {
          name: 'Recuperados',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#008000'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#008000'
            }]
          },
          data: data.recuperados
        },
        {
          name: 'Decesos',
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
          },
          data: data.decesos
        },
        {
          name: 'Sospechosos',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#FFFF00'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#FFFF00'
            }]
          },
          data: data.sospechosos
        }
      ]
    };

    this.myChart = this.echarts.init(document.getElementById('resume'));
    this.myChart.setOption(this.option);
  }

}

