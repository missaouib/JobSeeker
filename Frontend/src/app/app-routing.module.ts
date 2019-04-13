import { TechnologyViewComponent } from './views/technology-view/technology-view.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CityViewComponent } from './views/city-view/city-view.component';

const routes: Routes = [
  {
    path: '',
    component: TechnologyViewComponent
  },
  {
    path: 'city',
    component: CityViewComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
