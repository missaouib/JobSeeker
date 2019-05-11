import { ItJobOffersViewComponent } from './views/it-job-offers-view/it-job-offers-view.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TechnologyStatisticsViewComponent } from './views/technology-statistics-view/technology-statistics-view.component';
import {CategoryStatisticsViewComponent} from "./views/category-statistics-view/category-statistics-view.component";

const routes: Routes = [
  {
    path: '',
    component: ItJobOffersViewComponent
  },
  {
    path: 'technology',
    component: TechnologyStatisticsViewComponent
  },
  {
    path: 'category',
    component: CategoryStatisticsViewComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
