import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

import {User} from "../user";
import { catchError, retry } from 'rxjs/operators';
import {throwError} from "rxjs/index";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class LoginService {



  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError(
      'Something bad happened; please try again later.');
  };


  constructor(private http: HttpClient) {
  }

  login(phone_num:string) {
    return this.http.post<User>('/auth/login-signup', {phone_number: phone_num})
      .pipe(
        catchError(this.handleError)
      );
      // .then(function(data) {
      //   console.log("DATA = " + data);
      // })
    // this is just the HTTP call,
    // we still need to handle the reception of the token
    //   .shareReplay();
  }

}
