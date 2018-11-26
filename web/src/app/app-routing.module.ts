import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RestRequestsComponent } from "./rests/rest-requests/rest-requests.component";

const routes: Routes = [
  {path: 'rest-requests', component: RestRequestsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
