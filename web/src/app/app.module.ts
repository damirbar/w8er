import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import {FormsModule} from "@angular/forms";

import { MatInputModule,
  MatCardModule,
  MatButtonModule,
  MatToolbarModule,
  MatExpansionModule } from '@angular/material';

import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {PostCreateComponent} from "./posts/post-create/post-create.component";
import {PostListComponent} from "./posts/post-list/post-list.component";
import {PostsService} from "./posts/posts.service";
import { LoginComponent } from './user-forms/login/login.component';
import {LoginService} from "./user-forms/login.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PostCreateComponent,
    PostListComponent,
    LoginComponent
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
    MatExpansionModule
  ],
  providers: [PostsService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
