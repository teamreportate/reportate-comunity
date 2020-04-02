import { ChangeDetectorRef, Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AccessService } from '../../access.service';
import { AuthUser } from '../../../core/models/AuthUser';
import { AuthGroup } from '../../../core/models/auth-group';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { passwordMatchValidator } from '../../resources/utils/password-match-validator';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { Page } from 'src/app/core/utils/paginator/page';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { AddUserDialogComponent } from '../components/add-user-dialog.component';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.sass']
})
export class UserComponent extends ClicComponent implements OnInit {

  title = 'Nuevo usuario';

  public form: FormGroup;
  public asswordForm: FormGroup;
  public confirm = true;
  public load = false;
  public typeUser: undefined | 'PWD' | 'AD';
  public error = null;
  public ocultar = true;

  private EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";

  asignedGroupList: AuthGroup[];

  allDepartments = false;
  departments = [
    { id: 1, name: 'Santa Cruz', isSelect: false },
    { id: 2, name: 'La Paz', isSelect: false },
    { id: 3, name: 'Cochabamba', isSelect: false },
    { id: 4, name: 'Oruro', isSelect: false },
    { id: 5, name: 'Potosí', isSelect: false },
    { id: 6, name: 'Beni', isSelect: false },
    { id: 7, name: 'Pando', isSelect: false },
    { id: 8, name: 'Sucre', isSelect: false },
    { id: 9, name: 'Tarija', isSelect: false },
  ];

  allMunicipalities = false;
  selectedDepartment: number;
  municipalities = [
    { id: 1, name: 'Santa Cruz de la Sierra', isSelect: false, departmentId: 1 },
    { id: 2, name: 'Cotoca', isSelect: false, departmentId: 1 },
    { id: 3, name: 'El Torno', isSelect: false, departmentId: 1 },
    { id: 4, name: 'La Guardia', isSelect: false, departmentId: 1 },
    { id: 5, name: 'Porongo', isSelect: false, departmentId: 1 },
    { id: 6, name: 'San Matias', isSelect: false, departmentId: 1 },
    { id: 7, name: 'San José de Chiquitos', isSelect: false, departmentId: 1 },
    { id: 8, name: 'Pailón ', isSelect: false, departmentId: 1 },
    { id: 9, name: 'Roboré', isSelect: false, departmentId: 1 },
    { id: 10, name: 'Lagunillas ', isSelect: false, departmentId: 1 },
    { id: 11, name: 'Colcapirhua ', isSelect: false, departmentId: 3 },
    { id: 12, name: 'Tolata ', isSelect: false, departmentId: 3 },
    { id: 13, name: 'Tarata ', isSelect: false, departmentId: 3 },
    { id: 14, name: 'Pasorapa ', isSelect: false, departmentId: 3 },
    { id: 15, name: 'Aiquile ', isSelect: false, departmentId: 3 },
    { id: 16, name: 'El alto ', isSelect: false, departmentId: 2 },
    { id: 17, name: 'La paz', isSelect: false, departmentId: 2 },
    { id: 18, name: 'Viacha', isSelect: false, departmentId: 2 },
    { id: 19, name: 'Caranavi', isSelect: false, departmentId: 2 },
    { id: 20, name: 'Sica Sica', isSelect: false, departmentId: 2 },
    { id: 20, name: 'Coipasa', isSelect: false, departmentId: 4 },
    { id: 20, name: 'Chipaya', isSelect: false, departmentId: 4 },
    { id: 20, name: 'Choquecota', isSelect: false, departmentId: 4 },
    { id: 20, name: 'Soracachi', isSelect: false, departmentId: 4 },
  ];

  filteredMunicipalities = [];

  allSaludCentres = false;
  selectedSaludCentre: number;
  saludCentres = [
    { id: 1, name: 'Mapaizo', isSelect: false, municipalityId: 2 },
    { id: 2, name: 'Tarope', isSelect: false, municipalityId: 2 },
    { id: 3, name: 'La sagrada familia', isSelect: false, municipalityId: 3 },
    { id: 4, name: 'Limoncito', isSelect: false, municipalityId: 3 },
    { id: 5, name: 'Monte verde', isSelect: false, municipalityId: 3 },
    { id: 6, name: 'Taruma', isSelect: false, municipalityId: 3 },
    { id: 7, name: 'San Juan Bautista', isSelect: false, municipalityId: 5 },
    { id: 8, name: 'San Pedro ', isSelect: false, municipalityId: 5 },
    { id: 9, name: 'Sombrerito', isSelect: false, municipalityId: 5 },
    { id: 10, name: 'San Pedro ', isSelect: false, municipalityId: 5 },
    { id: 11, name: 'El pajonal', isSelect: false, municipalityId: 1 },
    { id: 12, name: 'Hamacas ', isSelect: false, municipalityId: 1 },
    { id: 13, name: 'La colorada', isSelect: false, municipalityId: 1 },
    { id: 14, name: 'La Fortaleza ', isSelect: false, municipalityId: 1 },
    { id: 15, name: 'Pampa de la Isla', isSelect: false, municipalityId: 1 },
    { id: 16, name: 'Palmar del Oratorio', isSelect: false, municipalityId: 1 },
    { id: 17, name: 'Perpetuo socorro', isSelect: false, municipalityId: 1 },
    { id: 18, name: 'San Luis', isSelect: false, municipalityId: 1 },
    { id: 19, name: 'Santa Rosita', isSelect: false, municipalityId: 1 },
    { id: 20, name: 'Tierras Nuevas', isSelect: false, municipalityId: 1 }
  ];

  filteredSaludCentres = [];


  constructor(
    private accessService: AccessService,
    private notifier: NotifierService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.form = new FormGroup({
      nombre: new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(100), Validators.minLength(5)])),
      username: new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'),
      Validators.minLength(5), Validators.maxLength(50)])),
      authType: new FormControl(null, Validators.compose([Validators.required])),
      password: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      passwordConfirm: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      email: new FormControl(null, Validators.compose([Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])),
      grupos: new FormControl(null)
    }, {
      validators: passwordMatchValidator
    });
    this.error = null;
    let grupos: any[] = [];
    this.asignedGroupList = grupos.reverse();
  }

  createUser() {
    this.error = null;

    if (this.form.controls['authType'].value === 'AD') {
      this.form.controls['password'].setValue('');
      this.form.controls['password'].setErrors(null);
      this.form.controls['passwordConfirm'].setValue('');
      this.form.controls['passwordConfirm'].setErrors(null);
    }
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      const confirm: string = this.form.get('passwordConfirm').value;
      if (user.password !== confirm) {
        this.confirm = false;
        return;
      }
      this.load = true;
      this.accessService.requestUserStore(user, confirm).subscribe(response => {
      }, error1 => {
        this.load = false;
        if (error1) this.notifierError(error1);
      });
    } else {
      for (const controlsKey in this.form.controls) {
        if (this.form.controls[controlsKey].errors)
          this.form.controls[controlsKey].markAsTouched({ onlySelf: true });
      }
    }
  }

  onPasswordInput() {
    if (this.form.hasError('passwordMismatch')) {
      this.form.get('passwordConfirm').setErrors([{ 'passwordMismatch': true }]);
    } else {
      this.form.get('passwordConfirm').setErrors(null);
    }
  }

  cancel() {

  }

  notifierError(error: any, type?: string) {
    if (error && error.error) {
      const customOptions: CustomOptions = { type: type ? type : 'error', tile: error.error.title, message: error.error.detail, template: this.customNotificationTmpl };
      this.notifier.show(customOptions);
    }
  }
  public flex: number;
  onGtLgScreen() {
    this.flex = 10;
    this.dialogWidth = '650px';
  }

  onLgScreen() {
    this.flex = 15;
    this.dialogWidth = '650px';
  }

  onMdScreen() {
    this.flex = 25;
    this.dialogWidth = '60%';
  }

  onSmScreen() {
    this.flex = 100;
    this.dialogWidth = '80%';
  }

  onXsScreen() {
    this.flex = 100;
    this.dialogWidth = '99%';
  }

  setPage(pageInfo: Page) { }


  selectAllDepartament(isSelect: boolean) {
    this.departments.forEach(elem => {
      elem.isSelect = isSelect;
    });
   }

   filterMunicipalities(id: number) {
     this.filteredMunicipalities = this.municipalities.filter(x => x.departmentId == id);
   }

   selectAllMunicipalities(isSelect: boolean) {
    this.filteredMunicipalities.forEach(elem => {
      elem.isSelect = isSelect;
    });
   }

   filterSaludCetres(id: number) {
     this.filteredSaludCentres = this.saludCentres.filter(x => x.municipalityId == id);
   }

   selectAllSaludCentres(isSelect: boolean) {
    this.filteredSaludCentres.forEach(elem => {
      elem.isSelect = isSelect;
    });
   }

}

