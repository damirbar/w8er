import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import {FormsModule} from '@angular/forms';

import {
  MatInputModule,
  MatCardModule,
  MatButtonModule,
  MatToolbarModule,
  MatExpansionModule,
  MatSidenavModule,
  MatSidenavContent,
  MatSidenav,
  MatDialogModule, MatIconModule
} from '@angular/material';



import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {PostCreateComponent} from './posts/post-create/post-create.component';
import {PostListComponent} from './posts/post-list/post-list.component';
import {PostsService} from './posts/posts.service';
import { LoginComponent } from './user-forms/login/login.component';
import {LoginService} from './user-forms/login.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { HttpErrorHandler } from './http-error-handler.service';
import { MessageService } from './message.service';
import { RestRequestsComponent } from './rests/rest-requests/rest-requests.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import { UserProfileComponent } from './user-profile/user-profile.component';
import {AuthInterceptor} from './auth-interceptor.service';
import {RouterModule} from '@angular/router';
import { AgmCoreModule } from '@agm/core';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PostCreateComponent,
    PostListComponent,
    LoginComponent,
    RestRequestsComponent,
    UserProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatExpansionModule,
    HttpClientModule,
    HttpModule,
    FlexLayoutModule,
    MatSidenavModule,
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyAyKg00vFRU05qHOX6GTCR7ANb9RRYSj_o'
    }),
    MatDialogModule,
    MatIconModule
  ],
  providers: [
    PostsService,
    LoginService,
    HttpErrorHandler,
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent],
  entryComponents: [LoginComponent]
})
export class AppModule { }
