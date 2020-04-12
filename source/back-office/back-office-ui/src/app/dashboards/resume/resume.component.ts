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
        data: ['Sospechosos', 'Confirmados', 'Recuperados', 'Fallecidos']
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
          areaStyle: {},
          data: data.sospechosos
        },
        {
          name: 'Confirmados',
          type: 'line',
          areaStyle: {},
          data: data.confirmados
        },
        {
          name: 'Recuperados',
          type: 'line',
          areaStyle: {},
          data: data.curados
        },
        {
          name: 'Fallecidos',
          type: 'line',
          areaStyle: {},
          data: data.fallecidos
        }
      ]
    };

    this.myChart = this.echarts.init(document.getElementById('resume'));
    this.myChart.setOption(this.option);
  }


}

