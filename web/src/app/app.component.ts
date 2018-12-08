import {Component, OnInit} from '@angular/core';
import {UserHolderService} from './user-holder.service';
import {LoginService} from './user-forms/login.service';
import {IUser} from './i-user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  title = 'w8erWebapp';

  constructor(public userHolderService: UserHolderService,
              public loginService: LoginService) {}


  ngOnInit() {
    if (!this.userHolderService.isLoggedIn()) {
      this.loginService.getUserWithToken()
        .subscribe(
          (data: IUser) => {

            console.log(typeof(data));
            if (data instanceof Array && data.length === 0) {
              console.log('Auto login unsuccessful!');
              return;
            }
            console.log('Auto login success. Authorized!');
            this.userHolderService.setUser(data);
          }
        );
    }
  }

}
