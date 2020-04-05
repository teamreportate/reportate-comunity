import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../core/models/dto/custom-options';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import * as moment from 'moment';
import {ClicComponent} from '../../core/utils/clic-component';
import {SeguimientoEnfermedadService} from '../../core/services/http-services/seguimiento-enfermedad.service';
import {EnfermedadService} from '../../core/services/http-services/enfermedad.service';
import {DepartamentoService} from '../../core/services/http-services/departamento.service';
import {Enfermedad} from '../../core/models/enfermedad';
import {Departamento} from '../../core/models/departamento';
import {Constants} from '../../core/constants';
import { Municipio } from 'src/app/core/models/dto/Municipio';
import { Centro } from 'src/app/core/models/dto/Centro';


@Component({
  selector: 'app-seguimiento-enfermedad',
  templateUrl: './seguimiento-enfermedad.component.html',
  styleUrls: ['./seguimiento-enfermedad.component.sass'],
  providers: [SeguimientoEnfermedadService, EnfermedadService, DepartamentoService]
})
export class SeguimientoEnfermedadComponent extends ClicComponent implements OnInit {

  // tslint:disable-next-line:max-line-length
  constructor(private seguimientoEnfermedadService: SeguimientoEnfermedadService,
    private enfermedadService: EnfermedadService,
    private formBuilder: FormBuilder, public changeDetector: ChangeDetectorRef, public media: MediaMatcher,
              private notifier: NotifierService) {
    super();
    this.clasificacionList = Constants.CLASIFICACION_ENFERMEDAD;
    moment.locale('es-BO');
  }
  @BlockUI() blockUI: NgBlockUI;
  public render: boolean;
  public enfermedadList: Enfermedad[] = [];
  public departamentoList: Departamento[] = [];
  public municipioList: Municipio[] = [];
  public centroList: Centro[] = [];
  public clasificacionList: any[] = [];
  public form: FormGroup;
  private enfermedadId;
  private departamentoId;
  private clasificacion;

  tempMunicipio: Municipio[] = [];
  tempCentro: Centro[] = [];

  private startDate: any;
  private endDate: any;
  private message: string;
  public today: any;

  public filterFlex;

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.form = this.initialForm();
    this.today = moment().toDate();
    this.render = false;
    this.initializePage(15, false );
    this.enfermedadService.getEnfermedades().subscribe(response => {
      this.enfermedadList = response.body;
    });
    this.getListForSelect();
  }


  onSearch() {
    if (!this.form.valid) {
      this.updateDate();
      if (this.endDate.isBefore(this.startDate)) {
        const notif = {error: {title: 'Filtrado de Diagnóstico', detail: 'La fecha final no puede ser menor que la fecha inicial'}};
        this.notifierError(notif, 'warning');
        return;
      }
      this.render = false;
      this.initializePage(20, true);
    }
  }

  selectMunicipio(object: Departamento) {
    this.tempMunicipio = [];
    this.tempCentro = [];
    this.tempMunicipio = this.municipioList.filter((dto: Municipio) => dto.departamentoId === object.id);
  }
  selectCentro(object: Municipio) {
    this.tempCentro = [];
    this.tempCentro = this.centroList.filter((dto: Centro) => dto.municipioId === object.id);
  }

  private getListForSelect() {
    this.seguimientoEnfermedadService.getListDepartamentoMunicipioCentros().subscribe(response => {
      this.departamentoList = response.body.departamentos;
      this.municipioList = response.body.municipios;
      this.centroList = response.body.centrosSalud;
    });
  }
  private initialForm(): FormGroup {
    this.startDate = moment().subtract(30, 'days');
    this.endDate = moment();
    this.message = '';
    return this.formBuilder.group({
      enfermedadId: new FormControl('', Validators.compose([Validators.required])),
      departamentoId: new FormControl('', Validators.compose([Validators.required])),
      municipioID: new FormControl('', Validators.compose([Validators.required])),
      centroSaludId: new FormControl('', Validators.compose([Validators.required])),
      clasificacion: new FormControl('', Validators.compose([Validators.required])),
      nombrePaciente: new FormControl('', Validators.compose([Validators.required])),
      from: new FormControl(this.startDate.toDate(), Validators.compose([Validators.required])),
      to: new FormControl(this.endDate.toDate(), Validators.compose([Validators.required])),
    });
  }

  setPage(pageInfo: any) {
    this.pageControl.number = pageInfo.offset;
    this.blockUI.start('Recuperando lista de bitacora...');
    // tslint:disable-next-line:max-line-length
    this.seguimientoEnfermedadService.filterSeguimientoEnfermedad(this.enfermedadId, this.clasificacion, this.departamentoId, this.startDate.format('DD/MM/YYYY'), this.endDate.format('DD/MM/YYYY'), this.pageControl.number, this.pageControl.size).subscribe(response => {
      this.pageControl = response.body;
      this.render = true;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) {
        this.notifierError(error);
      }
    });
  }

  updateDate() {
    this.startDate = moment(moment.utc(this.form.get('from').value));
    this.endDate = moment(moment.utc(this.form.get('to').value));
    this.departamentoId = this.form.get('departamentoId').value;
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



  viewCoordenada(myURL: any) {
    console.warn(myURL);
    const title = 'UBICACION';
    // const myWidth = 1000;
    // const myHeight = 900;
    // const left = (screen.width - myWidth) / 2;
    // const top = (screen.height - myHeight) / 4;
    // tslint:disable-next-line:max-line-length
    window.open(myURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no');
  }

  validateNumber(telefono: string) {
    if (telefono.length > 8 || !telefono) {
      return false;
    } else {
      return true;
    }
  }
}
