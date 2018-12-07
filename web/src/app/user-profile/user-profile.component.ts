import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {IUser} from "../i-user";
import {UserUpdatesService} from "./user-updates.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.less']
})
export class UserProfileComponent implements OnInit {

  public numOfSidenavBtns = 13;
  public isClicked: boolean[] = new Array<boolean>(this.numOfSidenavBtns);
  public oldActive = 0;

  changeActiveBtn(newActive: number) {
      this.isClicked[this.oldActive] = false;
      this.isClicked[newActive]      = true;

      this.oldActive = newActive;
  }


  editProfile(form: NgForm) {
    if (form.invalid) {
      return;
    }

    const val = form.value;
    const user: IUser = null;
    user.first_name = val.fname;
    user.last_name = val.lname;
    user.email = val.email;
    user.about_me = val.public_info;
    user.is_admin = val.owner;



    this.userUpdatesService.editProfile(user)
      .subscribe(
        (data) => {
          console.log(typeof(data));
          if (data instanceof Array && data.length == 0) {
            console.log("No data received");
            return;
          }

          console.log("The user got a code");

        }
      );

  }


  constructor(public userUpdatesService: UserUpdatesService) { }

  ngOnInit() {
    this.isClicked[0] = true;
  }

}
