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
        data: ['Sospechosos', 'Descartados', 'Confirmados', 'Recuperados', 'Fallecidos']
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
          name: 'Sospechosos',
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
          },
          data: data.sospechosos
        },
        {
          name: 'Descartados',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#C777DB'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#C777DB'
            }]
          },
          data: data.negativos
        },
        {
          name: 'Confirmados',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#F4D03F'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#F4D03F'
            }]
          },
          data: data.confirmados
        },
        {
          name: 'Recuperados',
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
          },
          data: data.curados
        },
        {
          name: 'Fallecidos',
          type: 'line',
          areaStyle: {
            color: {
              colorStops: [{
                offset: 0, color: '#FB9678'
              }]
            }
          },
          color: {
            colorStops: [{
              offset: 0, color: '#FB9678'
            }]
          },
          data: data.fallecidos
        }
      ]
    };

    this.myChart = this.echarts.init(document.getElementById('resume'));
    this.myChart.setOption(this.option);
  }


}

