import { Component, OnInit, Input } from '@angular/core';

import echarts from '../../../assets/js/echarts.min.js';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html'
})
export class ResumeComponent implements OnInit {

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
    const data = {
      dia: ['06/04/2020', '07/04/2020', '08/04/2020', '08/04/2020', '09/04/2020', '10/04/2020', '11/04/2020', '12/04/2020'],
      sospechoso: [100, 150, 20, 10, 45, 55, 90, 55],
      confirmado: [52, 50, 50, 80, 15, 35, 90, 36],
      recuperados: [80, 35, 20, 20, 15, 55, 70, 25],
      muerto: [10, 5, 0, 2, 4, 5, 8, 12]
    };
    this.option = {
      title: {
        text: 'RESUMEN'
      },
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
        data: []
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
          data: data.dia
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'Sospechoso',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.sospechoso
        },
        {
          name: 'Confirmado',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.confirmado
        },
        {
          name: 'Recuperado',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.recuperados
        },
        {
          name: 'Muerto',
          type: 'line',
          stack: '总量',
          areaStyle: {},
          data: data.muerto
        }
      ]
    };

    this.myChart = this.echarts.init(document.getElementById('resume'));
    this.myChart.setOption(this.option);
  }


}

