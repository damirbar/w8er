import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

import {User} from "../user";
import { catchError } from 'rxjs/operators';
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';


@Injectable({
  providedIn: 'root'
})
export class LoginService {



  private handleError: HandleError;
  private httpOptions = {};


  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('sService');

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'//,
        // 'Authorization': 'my-auth-token'
      })
    };
  }

  login(phone_num:string) {
    console.log("CALLED LOGIN WITH PHONE = " + phone_num);
    return this.http.post('/auth/login-signup', {phone_number: phone_num},
      {headers: this.httpOptions})
      .pipe(
        catchError(this.handleError('login', []))
      );

  }

}
