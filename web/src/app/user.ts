import {IUser} from './i-user';

export class User implements IUser {

  email: string;

  constructor(email: string) {
    this.email = email;
  }

}
