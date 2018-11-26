import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

import {User} from "../user";
import { catchError } from 'rxjs/operators';
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';


@Injectable({
  providedIn: 'root'
})
export class LoginService {



  private handleError: HandleError;


  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('sService');
  }

  login(phone_num:string) {
    return this.http.post<User>('/auth/login-signup', {phone_number: phone_num})
      .pipe(
        catchError(this.handleError('login', []))
      );
      // .then(function(data) {
      //   console.log("DATA = " + data);
      // })
    // this is just the HTTP call,
    // we still need to handle the reception of the token
    //   .shareReplay();
  }

}
