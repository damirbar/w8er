import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogConfig} from "@angular/material";
import {LoginComponent} from "../user-forms/login/login.component";
import {UserHolderService} from "../user-holder.service";
import {LoginService} from "../user-forms/login.service";


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.less']
})
export class HeaderComponent implements OnInit {

  constructor(private dialog: MatDialog, public userHolderService: UserHolderService,
              public loginService: LoginService) { }

  openDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    this.dialog.open(LoginComponent, dialogConfig);
  }

  isLoggedIn() {
    return this.userHolderService.isLoggedIn();
  }

  logout() {
    this.userHolderService.removeUser();
    this.loginService.removeToken();
  }

  ngOnInit() {
  }

}
