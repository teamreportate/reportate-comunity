import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import {AuthenticationService} from '../../core/services/http-services/authentication.service';

const newPassword = new FormControl('', Validators.compose([Validators.required, Validators.minLength(8)]));
const confirmPassword = new FormControl('', CustomValidators.equalTo(newPassword));

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  public form: FormGroup;
  constructor(private fb: FormBuilder, private router: Router, private userService: AuthenticationService) {}

  ngOnInit() {
    const user = JSON.parse(localStorage.getItem('data')).username;
    this.form = this.fb.group({
      username: [
        user,
        Validators.compose([CustomValidators.username])
      ],
      newPassword: newPassword,
      confirmPassword: confirmPassword,
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value);
      this.userService.changePassword(this.form.value).subscribe(response => {
          this.router.navigate(['/authentication/login']);
      });
    }
  }
}
