import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { NgBlockUI, BlockUI } from 'ng-block-ui';

import { AccessService } from '../../access.service';
import { NotifierService } from 'angular-notifier';
import { GroupService } from 'src/app/core/services/http-services/group.service';

import { passwordMatchValidator } from '../../resources/utils/password-match-validator';
import { ClicComponent } from 'src/app/core/utils/clic-component';
import { MediaMatcher } from '@angular/cdk/layout';
import { Page } from 'src/app/core/utils/paginator/page';

import { AuthUser } from '../../../core/models/AuthUser';
import { AuthGroup } from '../../../core/models/auth-group';
import { CustomOptions } from 'src/app/core/models/dto/custom-options';

import { Department, SaludCentre, Municipaly } from '../user.type';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})
export class UserComponent extends ClicComponent implements OnInit {

  @BlockUI() blockUI: NgBlockUI;

  title = '';

  public form: FormGroup;
  public asswordForm: FormGroup;
  public confirm = true;
  public load = false;
  public typeUser: undefined | 'PWD' | 'AD';
  public error = null;
  public ocultar = true;

  private EMAIL_REGEX = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$';

  userId: number;
  userCloneId: number;

  groups: AuthGroup[] = [];

  departments: Department[] = [];
  filteredDepartments: Department[] = [];
  municipalities: Municipaly[] = [];
  filteredMunicipalities: Municipaly[] = [];
  saludCentres: SaludCentre[] = [];
  filteredSaludCentres: SaludCentre[] = [];

  isSelectAllDepartments = false;
  isSelectAllMunicipalities = false;
  isSelectAllSaludCentres = false;

  selectedDepartment = null;
  selectedMunicipaly = null;
  selectedSaludCentre = null;


  constructor(
    private accessService: AccessService,
    private notifier: NotifierService,
    private groupService: GroupService,
    private location: Location,
    private route: ActivatedRoute,
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
    this.initTypeAction();
  }

  initTypeAction() {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
      this.userCloneId = params['cid'];
      if (this.userId) {
        this.title = 'Editar usuario';
        this.getUserById(this.userId);
      } else {
        this.title = 'Nuevo usuario';
        /* if (this.entityCloneId) {
          this.getById(this.entityCloneId);
          return;
        } */
      }
    });
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

  getUserById(id: number) {
    this.accessService.requestGetUserById(id).subscribe(response => {
      const user = response.body;
      this.form.setValue({
        nombre: user.nombre,
        username: user.username,
        authType: user.authType,
        tipoUsuario: user.tipoUsuario,
        password: '',
        passwordConfirm: '',
        email: user.email,
        grupos: new FormControl(null),
        departamentos:  new FormControl(null),
        municipios:  new FormControl(null),
        centroSaluds:  new FormControl(null),
      });

      this.departments = user.departamentos;
      this.municipalities = user.municipios;
      this.saludCentres = user.centroSaluds;

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

  getDepartments() {
    this.accessService.requestAsignedDepartmentsList().subscribe(response => {
      this.departments = response.body;
      this.blockUI.stop();
    }, error => {
      this.blockUI.stop();
      if (error) this.notifierError(error);
    });
  }

  save() {
    this.userId > 0 ? this.updateUser() : this.createUser();
  }

  createUser() {
    this.error = null;
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      user.authType = 'SISTEMA';
      user.departamentos = this.departments.filter(x => x.asignado == true);
      user.municipios = this.municipalities.filter(x => x.asignado == true);
      user.centroSaluds = this.saludCentres.filter(x => x.asignado == true);
      const confirm: string = this.form.get('passwordConfirm').value;
      if (user.password !== confirm) {
        this.confirm = false;
        return;
      }
      this.load = true;
      this.accessService.requestUserStore(user, confirm).subscribe(response => {
        this.goBack();
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

  updateUser() {
    this.error = null;
    if (this.form.valid) {
      const user: AuthUser = this.form.value;
      user.id = this.userId;
      user.authType = 'SISTEMA';
      user.departamentos = this.departments.filter(x => x.asignado == true);
      user.municipios = this.municipalities.filter(x => x.asignado == true);
      user.centroSaluds = this.saludCentres.filter(x => x.asignado == true);
      const confirm: string = this.form.get('passwordConfirm').value;
      if (user.password !== confirm) {
        this.confirm = false;
        return;
      }
      this.load = true;
      this.accessService.requestUserUpdate(user, this.userId.toString()).subscribe(response => {
        this.goBack();
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

  goBack(): void {
    this.location.back();
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

  tabChanged(event: any) {
    switch (event.tab.textLabel) {
      case 'Departamentos':

        break;
      case 'Municipios':
        console.log(this.selectedDepartment);
        this.filterDepartments();
        break;
      case 'Centros de salud':
        console.log(this.selectedDepartment);
        this.filterDepartments();
        break;
      default:
        break;
    }
  }


  selectAllDepartament(iAsigned: boolean) {
    this.departments.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

  filterDepartments() {
    this.filteredDepartments = this.departments.filter(x => x.asignado == true);
  }

  filterMunicipalities(id: number) {
    console.log(this.selectedDepartment);
    id > 0 ? this.filteredMunicipalities = this.municipalities.filter(x => x.departamentoId == id) : this.filteredMunicipalities = [];
    this.filteredSaludCentres = [];
  }

  selectAllMunicipalities(iAsigned: boolean) {
    this.filteredMunicipalities.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

  filterSaludCetres(id: number) {
    id > 0 ? this.filteredSaludCentres = this.saludCentres.filter(x => x.municipioId == id) : this.filteredSaludCentres = [];
  }

  selectAllSaludCentres(iAsigned: boolean) {
    this.filteredSaludCentres.forEach(elem => {
      elem.asignado = iAsigned;
    });
  }

}

