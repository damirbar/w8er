import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {catchError} from 'rxjs/operators';
import {HttpErrorHandler, HandleError} from '../http-error-handler.service';
import {IUser} from '../i-user';


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
        'Content-Type': 'application/json'// ,
        // 'Authorization': 'my-auth-token'
      })
    };
  }

  login(phone_num: string) {
    console.log('CALLED LOGIN WITH PHONE = ' + phone_num);
    return this.http.post('/auth/login-signup', {phone_number: phone_num},
      {headers: this.httpOptions})
      .pipe(
        catchError(this.handleError('login', []))
      );
  }

  verify(phone_num: string, ver_num: string) {
    console.log('CALLED VERIFY WITH PHONE = ' + phone_num + ' AND PASSWORD = ' + ver_num);
    return this.http.post<IUser>('/auth/verify', {phone_number: phone_num, password: ver_num},
      {headers: this.httpOptions})
      .pipe(
        catchError(this.handleError('verify', []))
      );
  }


  setToken(token: string): boolean {
    if (token) {
      localStorage.setItem('token', token);
      return true;
    }
    console.log('Token NOT found in setToken');
    return false;

  }

  removeToken(): void {
    localStorage.removeItem('token');
  }

  hasToken(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      return true;
    }
    return false;
  }

  getUserWithToken() {
    if (this.hasToken()) {
      return this.http.get('/user/get-profile',
        {headers: this.httpOptions})
        .pipe(
          catchError(this.handleError('login', []))
        );
    }
  }

}
