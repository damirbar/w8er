import { Component, OnInit } from '@angular/core';
import {FormGroup, NgForm} from "@angular/forms";
import {LoginService} from "../login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {

  form:FormGroup;

  constructor(public loginService: LoginService, private router: Router){}

  // onAddPost(form: NgForm) {
  //   // const post: Post = {title: this.enteredTitle, content: this.enteredContent};
  //
  //   if (form.invalid) {
  //     return;
  //   }
  //   // const post: Post = {title: form.value.title, content: form.value.content};
  //   // this.postCreated.emit(post);
  //   this.postsService.addPost(form.value.title, form.value.content);
  // }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    return !(charCode > 31 && (charCode < 48 || charCode > 57));


  }

  login(form: NgForm) {
    if (form.invalid) {
      return;
    }

    console.log(form.value.phonenum);
    const val = form.value.phonenum;


    this.loginService.login(val)
      .subscribe(
        () => {
          console.log("User is logged in");
          // this.router.navigateByUrl('/');
        }
      );

  }



  ngOnInit() {
  }

}
