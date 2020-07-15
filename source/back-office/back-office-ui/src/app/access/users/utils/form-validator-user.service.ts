/**
 * Reportate
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * package:
 * date:    08-04-19
 * author:  fmontero
 **/
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserDto} from './user-dto';
import {Injectable} from '@angular/core';
import {AuthUser} from '../../../core/models/AuthUser';

@Injectable()
export class FormValidatorUserService{
  constructor(private formBuilder: FormBuilder) {}

  formValidatorCreate(userDto: UserDto): FormGroup {
    return this.formBuilder.group({
      nombre: [userDto.nombre, Validators.compose([Validators.required, Validators.minLength(5)])],
      username: [userDto.username, Validators.compose([Validators.required, Validators.minLength(5)])],
      password: [userDto.password],
      passwordConfirm: [userDto.passwordConfirm]
    });
  }

  formValidatorUpdate(user: AuthUser): FormGroup {
    return this.formBuilder.group({
      id:[user.id],
      version:[user.version],
      createdDate:[user.createdDate],
      lastModifiedDate:[user.lastModifiedDate],
      createdBy:[user.createdBy],
      lastModifiedBy:[user.lastModifiedBy],
      estado:[user.estado],
      nombre: [user.nombre],
      username: [user.username],
      password: [user.password],
      authType: [user.authType],
      tokenExpired: [user.tokenExpired],
      token: [user.token]
    });
  }
}
