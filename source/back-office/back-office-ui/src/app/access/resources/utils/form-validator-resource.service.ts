/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    08-04-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {AuthResource} from '../../../core/models/auth-resource';

@Injectable()
export class FormValidatorResourceService{
  constructor(private formBuilder: FormBuilder) {}

  formValidatorUpdate(resource: AuthResource): FormGroup {
    return this.formBuilder.group({
      id: [resource.id],
      version: [resource.version],
      createdDate: [resource.createdDate],
      lastModifiedDate: [resource.lastModifiedDate],
      createdBy: [resource.createdBy],
      lastModifiedBy: [resource.lastModifiedBy],
      estado: [resource.estado],
      idRecursoPadre: [resource.idRecursoPadre],
      nombre: [resource.nombre],
      url: [resource.url],
      icon: [resource.icon],
      descripcion: [resource.descripcion],
      ordenMenu: [resource.ordenMenu],
      listaRecursos: [resource.listaRecursos],
    });
  }
}
