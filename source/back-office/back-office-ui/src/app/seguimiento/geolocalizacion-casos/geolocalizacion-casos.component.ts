import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import * as Mapboxgl from 'mapbox-gl';
import { environment } from 'src/environments/environment';
import { SeguimientoEnfermedadService } from 'src/app/core/services/http-services/seguimiento-enfermedad.service';
import { EnfermedadService } from 'src/app/core/services/http-services/enfermedad.service';
import { DepartamentoService } from 'src/app/core/services/http-services/departamento.service';
import { NotifierService } from 'angular-notifier';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { MediaMatcher } from '@angular/cdk/layout';
import { Router } from '@angular/router';
import * as moment from 'moment';
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { Enfermedad } from 'src/app/core/models/enfermedad';
import { Departamento } from 'src/app/core/models/dto/Departamento';
import { Centro } from 'src/app/core/models/dto/Centro';
import { Municipio } from 'src/app/core/models/dto/Municipio';
import { Constants } from 'src/app/core/constants';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { UbicacionDiagnostico } from 'src/app/core/models/dto/ubicacion-diagnostico';
import { MapFormat } from 'src/app/core/models/dto/MapFormat';


@Component({
  selector: 'app-geolocalizacion-casos',
  templateUrl: './geolocalizacion-casos.component.html',
  styleUrls: ['./geolocalizacion-casos.component.css'],
  providers: [SeguimientoEnfermedadService, EnfermedadService, DepartamentoService]
})
export class GeolocalizacionCasosComponent extends ClicComponent implements OnInit {

  mapa: Mapboxgl.Map;
  panelOpenState = false;

  @BlockUI() blockUI: NgBlockUI;
  public render: boolean;
  public enfermedadList: Enfermedad[] = [];
  public departamentoList: Departamento[] = [];
  public municipioList: Municipio[] = [];
  public centroList: Centro[] = [];
  public clasificacionList: any[] = [];
  public form: FormGroup;
  private enfermedadId = 0;
  private departamentoId = 0;
  private municipioId = 0;
  private centroSaludId = 0;
  private clasificacion = 'TODOS';

  tempMunicipio: Municipio[] = [];
  tempCentro: Centro[] = [];

  private startDate: any;
  private endDate: any;
  private message: string;
  public today: any;

  listUbicaciones: UbicacionDiagnostico[] = [];
  listCovidSospechoso: MapFormat[] = [];
  listCovidConfirmado: MapFormat[] = [];
  listCovidNegativo: MapFormat[] = [];

  public filterFlex;
  constructor(private seguimientoEnfermedadService: SeguimientoEnfermedadService,
    private enfermedadService: EnfermedadService,
    private formBuilder: FormBuilder, public changeDetector: ChangeDetectorRef, public media: MediaMatcher,
    private notifier: NotifierService, private router: Router) {
    super();
    this.clasificacionList = Constants.CLASIFICACION_ENFERMEDAD;
    moment.locale('es-BO');
  }

  ngOnInit() {
    (Mapboxgl as any).accessToken = environment.services.apiKeyMaps;
    this.initialListener(this.changeDetector, this.media);
    this.form = this.initialForm();
    this.today = moment().toDate();
    this.render = false;
    this.enfermedadService.getEnfermedades().subscribe(response => {
      this.enfermedadList = response.body;
    });
    this.updateDate();
    this.getListForSelect();
    this.recuperarUbicaciones();
  }

  initMap() {
    this.mapa = new Mapboxgl.Map({
      container: 'mapa-mapbox', // container id
      style: 'mapbox://styles/mapbox/light-v10',
      zoom: 4,
      center: [-63.1733333, -17.8005371],
    });
    // this.marcador(-63.1688979, -17.8469692);
    this.mapa.addControl(new Mapboxgl.FullscreenControl());
    this.mapa.addControl(
      new Mapboxgl.GeolocateControl({
        positionOptions: {
          enableHighAccuracy: true
        },
        trackUserLocation: true
      })
    );
  }

  marcador(lng: number, lat: number) {
    const marker = new Mapboxgl.Marker({
      draggable: true
    })
      .setLngLat([lng, lat])
      .addTo(this.mapa);
    marker.on('drag', () => {
      console.warn(marker.getLngLat());
    });
  }

  marcarPuntos(list, estado, color) {
    this.mapa.on('load', () => {
      this.mapa.addSource(estado, {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': list
        }
      });
      this.mapa.addLayer({
        'id': estado,
        'type': 'circle',
        'source': estado,
        'paint': {
          'circle-radius': 8,
          'circle-color': color
        },
        'filter': ['==', '$type', 'Point']
      });
    });
    this.mostrarInfo(estado);
  }

  onSearch() {
    if (this.form.valid) {
      if (this.endDate.isBefore(this.startDate)) {
        const notif = { error: { title: 'Filtrado de Diagnósticos', detail: 'La fecha final no puede ser menor que la fecha inicial' } };
        this.notifierError(notif, 'warning');
        return;
      }
      this.updateDate();
      this.render = false;
      this.recuperarUbicaciones();
    }
  }

  selectMunicipio(object) {
    this.tempMunicipio = [];
    this.tempCentro = [];
    this.form.get('municipioID').setValue(0);
    this.form.get('centroSaludId').setValue(0);
    if (object !== 0) {
      this.tempMunicipio = this.municipioList.filter((dto: Municipio) => dto.departamentoId === object.id);
    }
  }

  selectCentro(object) {
    this.tempCentro = [];
    this.form.get('centroSaludId').setValue(0);
    if (object !== 0) {
      this.tempCentro = this.centroList.filter((dto: Centro) => dto.municipioId === object.id);
    }
  }

  private getListForSelect() {
    this.seguimientoEnfermedadService.getListDepartamentoMunicipioCentros().subscribe(response => {
      this.departamentoList = response.body.departamentos;
      this.municipioList = response.body.municipios;
      this.centroList = response.body.centrosSalud;
    });
  }

  private initialForm(): FormGroup {
    this.startDate = moment().subtract(30, 'days').subtract(4, 'hour');
    this.endDate = moment().subtract(4, 'hour');
    this.message = '';
    return this.formBuilder.group({
      enfermedadId: new FormControl(0, Validators.compose([Validators.required])),
      departamentoId: new FormControl(0, Validators.compose([Validators.required])),
      municipioID: new FormControl(0, Validators.compose([Validators.required])),
      centroSaludId: new FormControl(0, Validators.compose([Validators.required])),
      clasificacion: new FormControl('TODOS', Validators.compose([Validators.required])),
      from: new FormControl(this.startDate.toDate(), Validators.compose([Validators.required])),
      to: new FormControl(this.endDate.toDate(), Validators.compose([Validators.required])),
    });
  }

  setPage(pageInfo: any) {
  }

  recuperarUbicaciones() {
    this.initMap();
    this.listCovidSospechoso = [];
    this.listCovidConfirmado = [];
    this.listCovidNegativo = [];
    this.listUbicaciones = [];
    this.blockUI.start('Recuperando ubicación de los diagnósticos...');
    // tslint:disable-next-line:max-line-length
    this.seguimientoEnfermedadService.filterLocalisacion(this.centroSaludId, this.municipioId, this.enfermedadId, this.clasificacion, this.departamentoId, this.startDate.format('DD/MM/YYYY'), this.endDate.format('DD/MM/YYYY')).subscribe(response => {
      this.listUbicaciones = response.body;
      if (this.listUbicaciones.length === 0) {
        const notif = { error: { title: 'Filtrado de Diagnósticos', detail: 'No se encontraron resultados.' } };
        this.notifierError(notif, 'info');
      } else {
        for (const object of this.listUbicaciones) {
          const dtoMapFortma: MapFormat = new MapFormat(object);
          if (object.estadoDiagnostico === 'CONFIRMADO') {
            this.listCovidConfirmado.push(dtoMapFortma);
          }
          if (object.estadoDiagnostico === 'SOSPECHOSO') {
            this.listCovidSospechoso.push(dtoMapFortma);
          }
          if (object.estadoDiagnostico === 'NEGATIVO') {
            this.listCovidNegativo.push(dtoMapFortma);
          }
        }
        this.marcarPuntos(this.listCovidConfirmado, 'CONFIRMADO', 'red');
        this.marcarPuntos(this.listCovidSospechoso, 'SOSPECHOSO', 'yellow');
        this.marcarPuntos(this.listCovidNegativo, 'NEGATIVO', 'green');
      }
      this.render = true;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) {
        this.notifierError(error);
      }
    });
  }

  mostrarInfo(estado) {
    const popup = new Mapboxgl.Popup({
      closeButton: false,
      closeOnClick: false
    });

    this.mapa.on('mouseenter', estado, (e) => {
      // Change the cursor style as a UI indicator.
      this.mapa.getCanvas().style.cursor = 'pointer';

      const coordinates = e.features[0].geometry.coordinates.slice();
      const description = e.features[0].properties.description;

      // Ensure that if the map is zoomed out such that multiple
      // copies of the feature are visible, the popup appears
      // over the copy being pointed to.
      while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
        coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
      }

      // Populate the popup and set its coordinates
      // based on the feature found.
      popup
        .setLngLat(coordinates)
        .setHTML(description)
        .addTo(this.mapa);
    });

    this.mapa.on('mouseleave', estado, () => {
      this.mapa.getCanvas().style.cursor = '';
      popup.remove();
    });
  }
  updateDate() {
    this.startDate = moment(moment.utc(this.form.get('from').value));
    this.endDate = moment(moment.utc(this.form.get('to').value));
    this.departamentoId = this.form.get('departamentoId').value;
    this.municipioId = this.form.get('municipioID').value;
    this.centroSaludId = this.form.get('centroSaludId').value;
    this.enfermedadId = this.form.get('enfermedadId').value;
    this.clasificacion = this.form.get('clasificacion').value;
  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = {
        type: type ? type : 'error',
        tile: error.error.title,
        message: error.error.detail,
        template: this.customNotificationTmpl
      };
      this.notifier.show(customOptions);
    }
  }

  onXsScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onSmScreen() {
    this.filterFlex = 100;
    this.scrollH = true;
  }

  onMdScreen() {
    this.filterFlex = 50;
    this.scrollH = true;
  }

  onLgScreen() {
    this.filterFlex = 33;
    this.scrollH = false;
  }

  onGtLgScreen() {
    this.filterFlex = 33;
    this.scrollH = false;
  }
}
