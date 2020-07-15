/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * author:  fmontero
 **/
import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {FormValidatorResourceService} from '../../utils/form-validator-resource.service';
import {ResourceService} from '../../../../core/services/http-services/resource.service';
import {icons} from '../../../../core/utils/icons';

@Component({
  selector: 'app-update-resource',
  templateUrl: './update-resource.component.html',
  styleUrls: ['./update-resource.component.sass'],
  providers: [FormValidatorResourceService]
})
export class UpdateResourceComponent implements OnInit {
  public form: FormGroup;
  public iconList: string[];
  constructor(private resourceService: ResourceService, public dialogRef: MatDialogRef<UpdateResourceComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.createValidators(this.data.resource);
    this.iconList = icons;
  }

  formUpdateSubmit() {
    if (this.form.valid) {
      this.resourceService.requestUpdateResource(this.form.value).subscribe(response => {
        this.dialogRef.close(this.form.value);
      });
    }
  }

  onCancel() {
    this.dialogRef.close();
  }

  createValidators(resource: any) {
    this.form = new FormGroup({
      id: new FormControl(resource.id),
      nombre: new FormControl(resource.nombre, Validators.required),
      descripcion: new FormControl(resource.descripcion, Validators.compose([Validators.required])),
      url: new FormControl({value: resource.url, disabled: true}),
      icon: new FormControl(resource.icon, Validators.required),
      ordenMenu: new FormControl(resource.ordenMenu, [Validators.max(99), Validators.min(1)])
    });
  }

}
