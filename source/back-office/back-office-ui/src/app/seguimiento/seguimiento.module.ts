import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
// tslint:disable-next-line:max-line-length
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRippleModule,
  MatSelectModule,
  MatSlideToggleModule, MatSortModule,
  MatTabsModule,
  MatTooltipModule,
  MatExpansionModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {FlexLayoutModule, FlexModule} from '@angular/flex-layout';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {CommonsModule} from '../commons/commons.module';
import {SeguimientoRoute} from './seguimiento.routes';
import {SeguimientoEnfermedadComponent} from './seguimiento-enfermedad/seguimiento-enfermedad.component';
import {SharedModule} from '../shared/shared.module';
import {FichaEpidemiologicaComponent} from './seguimiento-enfermedad/ficha-epidemiologica/ficha-epidemiologica.component';
import {AddContactoComponent} from './seguimiento-enfermedad/ficha-epidemiologica/dialogs/add-contacto/add-contacto.component';
import {AddEnfermedadComponent} from './seguimiento-enfermedad/ficha-epidemiologica/dialogs/add-enfermedad/add-enfermedad.component';
import {AddPaisViajadoComponent} from './seguimiento-enfermedad/ficha-epidemiologica/dialogs/add-pais-viajado/add-pais-viajado.component';
import {PacienteService} from '../core/services/http-services/paciente.service';
import {PaisService} from '../core/services/http-services/pais.service';
import {EnfermedadService} from '../core/services/http-services/enfermedad.service';
import { GeolocalizacionCasosComponent } from './geolocalizacion-casos/geolocalizacion-casos.component';
import {ListSintomasComponent} from './seguimiento-enfermedad/ficha-epidemiologica/dialogs/list-sintomas/list-sintomas.component';
import {DiagnosticoService} from '../core/services/http-services/diagnostico.service';
import {AddObservacionComponent} from './seguimiento-enfermedad/ficha-epidemiologica/dialogs/add-observacion/add-observacion.component';

@NgModule({
  // tslint:disable-next-line:max-line-length
  declarations: [SeguimientoEnfermedadComponent, FichaEpidemiologicaComponent, AddContactoComponent, AddEnfermedadComponent, AddPaisViajadoComponent, ListSintomasComponent, GeolocalizacionCasosComponent, AddObservacionComponent],
  imports: [
    RouterModule.forChild(SeguimientoRoute),
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatDialogModule,
    MatListModule,
    MatCheckboxModule,
    MatSelectModule,
    MatTabsModule,
    MatRippleModule,
    MatSlideToggleModule,
    MatDatepickerModule,
    FormsModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    FlexLayoutModule,
    FlexModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    DragDropModule,
    CommonsModule,
    SharedModule,
    MatExpansionModule,
    MatSortModule,
  ], entryComponents: [AddContactoComponent, AddPaisViajadoComponent, AddEnfermedadComponent, ListSintomasComponent, AddObservacionComponent],
  providers: [PacienteService, PaisService, EnfermedadService, DiagnosticoService]
})
export class SeguimientoModule {
}
