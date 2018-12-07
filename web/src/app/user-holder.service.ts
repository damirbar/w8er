import { Injectable } from '@angular/core';
import {IUser} from "./i-user";

@Injectable({
  providedIn: 'root'
})
export class UserHolderService {

  user: IUser;

  constructor() {
    this.user = null;
  }

  setUser(user: IUser) {
    this.user = user;
    console.log("I got the user " + user);
  }

  removeUser() {
    this.user = null;
  }

  isLoggedIn() {
    return this.user != null;
  }
}
