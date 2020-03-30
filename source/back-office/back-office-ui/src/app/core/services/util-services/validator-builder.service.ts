/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    29-06-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ValidatorFn} from '@angular/forms/src/directives/validators';

export interface PropertyValidation {
  property: string;
  validators: (ValidatorFn | null | undefined)[];
}

@Injectable({providedIn: 'root'})
export class ValidatorBuilderService {

  constructor(private formBuilder: FormBuilder) {}

  builderFromModel(model: any, propertyValidations: PropertyValidation[]): FormGroup | null {
    const controls: {[p: string]: any} = {};
    for (const key in model) {
      const propValidation: PropertyValidation = propertyValidations.find((item: PropertyValidation) => item.property === key);
      controls[key] = propValidation ? this.formBuilder.control(model[key], Validators.compose(propValidation.validators)): null;
    }

    return this.formBuilder.group(controls);
  }
}
