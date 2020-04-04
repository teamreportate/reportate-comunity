import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AdministrationRoute } from './administration.routes';
// tslint:disable-next-line:max-line-length
import { MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatIconModule, MatTooltipModule, MatDialogModule, MatListModule, MatCheckboxModule, MatSelectModule, MatTabsModule, MatRippleModule, MatSlideToggleModule, MatDatepickerModule, MatProgressBarModule, MatProgressSpinnerModule, MatMenuModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { FlexLayoutModule, FlexModule } from '@angular/flex-layout';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { CommonsModule } from '../commons/commons.module';
import { AdministrationService } from './administration.service';
import { PaisesComponent } from './paises/paises.component';
import { MunicipiosComponent } from './municipios/municipios.component';
import { CentrosComponent } from './centros/centros.component';
import { SintomasComponent } from './sintomas/sintomas.component';
import { EnfermedadesComponent } from './enfermedades/enfermedades.component';
import { DepartamentosComponent } from './departamentos/departamentos.component';
import { ModalPaisComponent } from './paises/modal-pais/modal-pais.component';
import { ModalSintomaComponent } from './sintomas/modal-sintoma/modal-sintoma.component';
import { ModalEnfermedadComponent } from './enfermedades/modal-enfermedad/modal-enfermedad.component';
import { ModalDepartamentoComponent } from './departamentos/modal-departamento/modal-departamento.component';
import { ModalMunicipioComponent } from './municipios/modal-municipio/modal-municipio.component';
import { ModalCentroComponent } from './centros/modal-centro/modal-centro.component';

@NgModule({
  declarations: [PaisesComponent, MunicipiosComponent, CentrosComponent, SintomasComponent, EnfermedadesComponent, DepartamentosComponent, ModalPaisComponent, ModalSintomaComponent, ModalEnfermedadComponent, ModalDepartamentoComponent, ModalMunicipioComponent, ModalCentroComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(AdministrationRoute),
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
    CommonsModule
  ],
  providers: [AdministrationService],
  entryComponents: [ModalPaisComponent, ModalSintomaComponent, ModalEnfermedadComponent, ModalDepartamentoComponent, ModalMunicipioComponent, ModalCentroComponent]
})
export class AdministrationModule { }
