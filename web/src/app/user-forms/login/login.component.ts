import {Component, Inject, OnInit} from '@angular/core';
import {FormGroup, NgForm} from '@angular/forms';
import {LoginService} from '../login.service';
import {Router} from '@angular/router';
import {IUser} from '../../i-user';
import {UserHolderService} from '../../user-holder.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  localData = {};
  codeRequestWasSuccessful = false;

  // Saving the number for retrying
  numberSaveForRetry = '';

  constructor(public loginService: LoginService, private router: Router, public userHolderService: UserHolderService,
              private dialogRef: MatDialogRef<LoginComponent>,
              @Inject(MAT_DIALOG_DATA) data) {}

  static numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
  }

  login(form: NgForm): void {
  if (form.invalid) {
    return;
  }

  const val = form.value.phonenum;
  this.numberSaveForRetry = val;
  console.log('Phone = ' + val);


  this.loginService.login(val)
    .subscribe(
      (data) => {
        console.log(typeof(data));
        if (data instanceof Array && data.length === 0) {
          console.log('No data received');
          this.codeRequestWasSuccessful = false;
          return;
        }
        this.codeRequestWasSuccessful = true;
        console.log('The user got a code');

      }
    );

}




  verifyCode(form: NgForm) {
    if (form.invalid) {
      return;
    }

    const val = form.value.vernum;

    console.log('Verify number = ' + val);


    this.loginService.verify(this.numberSaveForRetry, val)
      .subscribe(
        (data: IUser) => {

          console.log(typeof(data));
          if (data instanceof Array && data.length === 0) {
            console.log('Wrong code!');
            this.codeRequestWasSuccessful = false;
            return;
          }
          this.codeRequestWasSuccessful = true;
          console.log('Success. Authorized!');
          console.log(data);
          this.loginService.setToken(data.accessToken);
          this.userHolderService.setUser(data);
          this.closeDialog();

        }
      );

  }

  closeDialog() {
    this.dialogRef.close();
  }

  ngOnInit() {
  }

}
