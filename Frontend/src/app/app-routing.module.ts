import { CitiesViewComponent } from './views/cities-view/cities-view.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TechnologiesViewComponent } from './views/technologies-view/technologies-view.component';

const routes: Routes = [
  {
    path: '',
    component: CitiesViewComponent
  },
  {
    path: 'city',
    component: TechnologiesViewComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
