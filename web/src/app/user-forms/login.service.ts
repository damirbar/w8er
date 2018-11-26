import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

import {User} from "../user";
import { catchError } from 'rxjs/operators';
import { HttpErrorHandler, HandleError } from '../http-error-handler.service';
import {Observable} from "rxjs/index";
import {HttpParamsOptions} from "@angular/common/http/src/params";


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
      // .then(function(data) {
      //   console.log("DATA = " + data);
      // })
    // this is just the HTTP call,
    // we still need to handle the reception of the token
    //   .shareReplay();
  }

}
