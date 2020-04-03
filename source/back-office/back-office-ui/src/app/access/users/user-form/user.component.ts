import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AccessService } from '../../access.service';
import { AuthUser } from '../../../core/models/AuthUser';
import { AuthGroup } from '../../../core/models/auth-group';
import { passwordMatchValidator } from '../../resources/utils/password-match-validator';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { NotifierService } from 'angular-notifier';
import { MediaMatcher } from '@angular/cdk/layout';
import { Page } from 'src/app/core/utils/paginator/page';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';
import { GroupService } from 'src/app/core/services/http-services/group.service';
import { NgBlockUI, BlockUI } from 'ng-block-ui';

import { Department, Group, SaludCentre, Municipaly } from '../user.type';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent extends ClicComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;

  title = 'Nuevo usuario';

  public form: FormGroup;
  public asswordForm: FormGroup;
  public confirm = true;
  public load = false;
  public typeUser: undefined | 'PWD' | 'AD';
  public error = null;
  public ocultar = true;

  private EMAIL_REGEX = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";

  groups: AuthGroup[] = [];

  departments: Department[] = [];
  municipalities: Municipaly[] = [];
  filteredMunicipalities: Municipaly[] = [];
  saludCentres: SaludCentre[] = [];
  filteredSaludCentres: SaludCentre[] = [];

  isSelectAllDepartments = false;
  isSelectAllMunicipalities = false;
  isSelectAllSaludCentres = false;

  selectedDepartment: number;
  selectedSaludCentre: number;


  constructor(
    private accessService: AccessService,
    private notifier: NotifierService,
    private groupService: GroupService,
    private changeDetector: ChangeDetectorRef, private media: MediaMatcher) {
    super();
  }

  ngOnInit() {
    this.initialListener(this.changeDetector, this.media);
    this.getGroups();
    this.getDepartments();
    this.getMunicipalities();
    this.getSaludCentres();
    this.initForm();
  }

  initForm() {
    this.form = new FormGroup({
      nombre: new FormControl(null, Validators.compose([Validators.required, Validators.maxLength(100), Validators.minLength(5)])),
      username: new FormControl(null, Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z0-9_]*$'),
      Validators.minLength(5), Validators.maxLength(50)])),
      authType: new FormControl(null),
      tipoUsuario: new FormControl(null, Validators.compose([Validators.required])),
      password: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      passwordConfirm: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(8), Validators.maxLength(20)])),
      email: new FormControl(null, Validators.compose([Validators.required,
      Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')])),
      grupos: new FormControl(null),
      departamentos: new FormControl(null),
      municipios: new FormControl(null),
      centroSaluds: new FormControl(null)
    }, {
      validators: passwordMatchValidator
    });
  }

  getGroups() {
    this.blockUI.start('Recuperando lista de grupos');
    this.groupService.requestGroupList().subscribe(response => {
      this.groups = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getDepartments() {
    this.accessService.requestAsignedDepartmentsList().subscribe(response => {
      this.departments = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getMunicipalities() {
    this.accessService.requestAsignedMunicipalitiesList().subscribe(response => {
      this.municipalities = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  getSaludCentres() {
    this.accessService.requestAsignedSaludCentreList().subscribe(response => {
      this.saludCentres = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  createUser() {
    this.error = null;
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      user.authType = 'SISTEMA';
      user.departamentos = this.departments;
      user.municipios = this.municipalities;
      user.centroSaluds = this.saludCentres;
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



  selectAllDepartament(iAsigned: boolean) {
    this.departments.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

  filterMunicipalities(id: number) {
    this.municipalities.filter(x => x.departamentoId == 1);
    console.log(this.municipalities);
  }

  selectAllMunicipalities(iAsigned: boolean) {
    this.municipalities.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

  filterSaludCetres(id: number) {
    // this.filteredSaludCentres = this.saludCentres.filter(x => x.municipalityId == id);
  }

  selectAllSaludCentres(iAsigned: boolean) {
    this.saludCentres.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

}

