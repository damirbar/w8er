import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RestRequestsComponent } from "./rests/rest-requests/rest-requests.component";
import {UserProfileComponent} from "./user-profile/user-profile.component";

const routes: Routes = [
  {path: 'rest-requests', component: RestRequestsComponent},
  {path: 'user-profile', component: UserProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
