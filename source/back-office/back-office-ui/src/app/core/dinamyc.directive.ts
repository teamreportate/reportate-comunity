/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    30-07-19
 * author:  fmontero
 **/
import {
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  Directive,
  Input,
  OnInit,
  Type,
  ViewContainerRef
} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {Invoicing} from './invoicing';

@Directive({selector: '[app-dynamic]'})
export class DynamicDirective implements Invoicing, OnInit {
  @Input() public detector: FormControl;
  @Input() public formGroupIn: FormGroup | FormControl;
  @Input() public lists: {[key: string]: any[]};
  component: ComponentRef<Invoicing>;
  private allComponents: {[type: string]: Type<Invoicing>} = {};
  constructor(private factoryResolver: ComponentFactoryResolver, private viewContainer: ViewContainerRef) {
  }

  ngOnInit() {
    this.detector.valueChanges.subscribe(change => {
      if (!change) {
        this.component.destroy();
        return;
      }
      if (this.component) this.component.destroy();
      if (!this.allComponents[this.detector.value]) {
        const currentSupportType: string = "[COMPUTARIZADA_ESTANDAR, COMPUTARIZADA_EDUCATIVO, COMPUTARIZADA_FINANCIERA]";
        throw new Error(`Tipo de componente: [${this.detector.value}] no soportado en esta version.
         \n Tipos soportados => [${currentSupportType}]`);
      }
      const currentComponent: ComponentFactory<Invoicing> = this.factoryResolver.resolveComponentFactory(this.allComponents[this.detector.value]);
      this.component = this.viewContainer.createComponent(currentComponent);
      this.component.instance.formGroupIn = this.formGroupIn;
      this.component.instance.lists = this.lists;
    });
  }
}
