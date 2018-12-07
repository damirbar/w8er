import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {HandleError, HttpErrorHandler} from '../http-error-handler.service';
import {catchError} from 'rxjs/internal/operators';
import {IUser} from "../i-user";

@Injectable({
  providedIn: 'root'
})
export class UserUpdatesService {


  private handleError: HandleError;
  private httpOptions = {};


  constructor(private http: HttpClient, httpErrorHandler: HttpErrorHandler) {
    this.handleError = httpErrorHandler.createHandleError('sService');

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
  }

  editProfile(user: IUser) {
    console.log('Called editProfile');
    return this.http.post('/user/edit-profile',
      {
        first_name: user.first_name,
        last_name: user.last_name,
        // user.birthday
        gender: user.gender,
        about_me: user.about_me,
        email: user.email,
        // address: user.address
        country: user.country,
        is_admin: user.is_admin
      },
      {headers: this.httpOptions})
      .pipe(
        catchError(this.handleError('login', []))
      );
  }

}
