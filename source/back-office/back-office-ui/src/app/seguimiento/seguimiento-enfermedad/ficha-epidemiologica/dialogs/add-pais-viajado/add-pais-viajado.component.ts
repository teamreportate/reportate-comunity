import {ChangeDetectorRef, Component, Inject, OnInit, Output} from '@angular/core';
import {ClicComponent} from '../../../../../core/utils/clic-component';
import {BlockUI, NgBlockUI} from 'ng-block-ui';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AdministrationService} from '../../../../../administration/administration.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {NotifierService} from 'angular-notifier';
import {MediaMatcher} from '@angular/cdk/layout';
import {CustomOptions} from '../../../../../core/models/dto/custom-options';
import {Page} from '../../../../../core/utils/paginator/page';
import {PaisService} from '../../../../../core/services/http-services/pais.service';
import {PacienteService} from '../../../../../core/services/http-services/paciente.service';

@Component({
  selector: 'app-add-pais-viajado',
  templateUrl: './add-pais-viajado.component.html',
  styleUrls: ['./add-pais-viajado.component.sass']
})
export class AddPaisViajadoComponent extends ClicComponent implements OnInit {
  @Output() operacion;
  @BlockUI() blockUi: NgBlockUI;


  paises: any[];
  public form: FormGroup;
  public today: Date;
  flex: number;

  constructor(private service: PacienteService, private servicioPais: PaisService, private builder: FormBuilder,
              private dialogRef: MatDialogRef<AddPaisViajadoComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private notifier: NotifierService,
              private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.servicioPais.getPais().subscribe(response => {
      this.paises = response.body;
      this.form = this.builder.group({
        id: new FormControl(this.data.id),
        // tslint:disable-next-line:max-line-length
        pais: new FormControl(this.data.pais, Validators.compose([Validators.required, Validators.maxLength(100)])),
        ciudad: new FormControl(this.data.ciudad, Validators.compose([Validators.required, Validators.maxLength(100)])),
        fechaViaje: new FormControl(this.data.fechaNacimiento, Validators.compose([Validators.required, Validators.maxLength(100)])),
      });
    });
  }

  close(): void {
    this.dialogRef.close(null);
  }

  save() {
    if (isNaN(this.data.id)) {
      this.blockUi.start('Procesando solicitud...');
      this.service.addPaisViajado(this.data.idPaciente, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = {title: 'Registro de Pais viajado', detail: 'Pais vijado registrado satisfactoriamente.'};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) {
          this.notifierError(error);
        }
      });
    } else {
      this.blockUi.start('Procesando solicitud...');
      this.service.addPaisViajado(this.data.idPaciente, this.form.value).subscribe(response => {
        this.blockUi.stop();
        const msg = {title: 'Actualizar Pais viajado', detail: 'Pais viajado actualizado satisfactoriamente.'};
        this.dialogRef.close(msg);
      }, error => {
        this.blockUi.stop();
        if (error) {
          this.notifierError(error);
        }
      });
    }
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

}
