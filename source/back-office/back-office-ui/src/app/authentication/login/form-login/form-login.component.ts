/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    30-01-19
 * author:  fmontero
 **/
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Authentication} from '../../shared/authentication';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.sass']
})
export class FormLoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl(null, [Validators.required]),
    password: new FormControl(null, [Validators.required])
  });

  @Output() submitted = new EventEmitter<Authentication>();

  @Input() public error: string;
  @Input() public isLoad: boolean;

  @Input() set disabled(isDisabled: boolean) {
    if(isDisabled) this.loginForm.disable();
    else this.loginForm.enable();
  }

  constructor(private matDialog: MatDialog){}

  ngOnInit() { }

  loginFormSubmit() {
    const value: Authentication = this.loginForm.value;
    if(this.loginForm.valid) {
      this.submitted.emit(value);
    }
  }
}
