import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {Constants} from '../../../core/constants';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {PacienteService} from '../../../core/services/http-services/paciente.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../core/models/dto/custom-options';
import {Page} from '../../../core/utils/paginator/page';
import {ClicComponent} from '../../../core/utils/clic-component';
import {EnfermedadService} from '../../../core/services/http-services/enfermedad.service';
import {SintomaService} from '../../../core/services/http-services/sintoma.service';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-nuevo-dianostico',
  templateUrl: './nuevo-dianostico.component.html',
  styleUrls: ['./nuevo-dianostico.component.sass']
})
export class NuevoDianosticoComponent extends ClicComponent implements OnInit {
  selected = [];
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;
  filteredOptions: Observable<any[]>;
  listEnfermedades: any[] = [];
  enfermedadId: any;
  listSintomas: any[] = [];
  clasificiacion = Constants.CLASIFICACION_ENFERMEDAD2;
  public form: FormGroup;
  public today: Date;
  flex: number;

  constructor(private service: EnfermedadService, private servicePaciente: PacienteService, private serviceSintoma: SintomaService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<NuevoDianosticoComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();

  }


  ngOnInit() {
    console.warn(this.data);
    this.form = this.builder.group({
      enfermedadId: new FormControl(this.data.enfermedadId, Validators.compose([])),
      clasificacion: new FormControl(this.data.clasificacion, Validators.compose([Validators.required, Validators.maxLength(100)])),
      recomendacion: new FormControl(this.data.recomendacion, Validators.compose([Validators.required, Validators.maxLength(100)])),
    });
    this.service.getEnfermedades().subscribe(response => {
      this.listEnfermedades = response.body;
      this.filteredOptions = this.form.controls['enfermedadId'].valueChanges
        .pipe(
          startWith(''),
          map(value => typeof value === 'string' ? value : value.name),
          map(name => name ? this._filter(name) : this.listEnfermedades.slice())
        );
    });
    this.serviceSintoma.getSintomas().subscribe(response => {
      this.listSintomas = response.body;
    });
  }

  close(): void {
    this.dialogRef.close(null);
  }

  save() {
    console.warn(this.form);
    let dataBody = {
      enfermedadId: this.enfermedadId.id,
      sintomas: this.selected,
      recomendacion: this.form.controls['recomendacion'].value,
      clasificacion: this.form.controls['clasificacion'].value
    };
    this.blockUi.start('Procesando solicitud...');
    this.servicePaciente.createDiagnostico(this.data.pacienteId, dataBody).subscribe(response => {
      this.blockUi.stop();
      const msg = {title: 'Registrar Diagnostico', detail: 'Diagnostico registrado satisfactoriamente.', object: response.body};
      this.dialogRef.close(msg);
    }, error => {
      this.blockUi.stop();
      if (error) {
        this.notifierError(error);
      }
    });
  }

  private _filter(nombre: string): any[] {
    const filterValue = nombre.toLowerCase();
    const servicioSelected = this.isSelecciono(filterValue);
    if (servicioSelected == null) {
      this.enfermedadId = null;
    } else {
      this.enfermedadId = servicioSelected;
    }
    return this.listEnfermedades.filter(option => option.nombre.toLowerCase().indexOf(filterValue) > -1);
  }

  selectedOption(input: any) {
    console.warn(input);
  }

  cancel = () => this.dialogRef.close(null);

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      // tslint:disable-next-line:max-line-length
      const customOptions: CustomOptions = {
        type: type ? type : 'error',
        tile: error.error.title,
        message: error.error.detail,
        template: this.customNotificationTmpl
      };
      this.notifier.show(customOptions);
    }
  }

  onGtLgScreen() {
    this.flex = 10;
  }

  onLgScreen() {
    this.flex = 15;
  }

  onMdScreen() {
    this.flex = 25;
  }

  onSmScreen() {
    this.flex = 100;
  }

  onXsScreen() {
    this.flex = 100;
  }

  setPage(pageInfo: Page) {
  }

  onSelect({selected}) {
    console.log('Select Event', selected, this.selected);

    this.selected.splice(0, this.selected.length);
    this.selected.push(...selected);
  }


  private isSelecciono(name: any) {
    if (this.listEnfermedades !== null && this.listEnfermedades.length > 0) {
      for (let i = 0; i < this.listEnfermedades.length; i++) {
        if (this.listEnfermedades[i].nombre.toLowerCase() === name) {
          return this.listEnfermedades[i];
        }
      }
    }
    return null;
  }
}
