import {AboutComponent} from './views/about/about.component';
import {HistoryDiagramsComponent} from './views/history-diagrams/history-diagrams.component';
import {ItJobOffersInPolandComponent} from './views/it-job-offers-in-poland/it-job-offers-in-poland.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TechnologyStatisticsInPolandComponent} from './views/technology-statistics-in-poland/technology-statistics-in-poland.component';
import {CategoryStatisticsComponent} from "./views/category-statistics/category-statistics.component";
import {ItJobOffersInWorldComponent} from "./views/it-job-offers-in-world/it-job-offers-in-world.component";
import {TechnologyStatisticsInWorldComponent} from "./views/technology-statistics-in-world/technology-statistics-in-world.component";

const routes: Routes = [
  {
    path: '',
    component: ItJobOffersInPolandComponent
  },
  {
    path: 'world',
    component: ItJobOffersInWorldComponent
  },
  {
    path: 'technology-poland',
    component: TechnologyStatisticsInPolandComponent
  },
  {
    path: 'technology-world',
    component: TechnologyStatisticsInWorldComponent
  },
  {
    path: 'category',
    component: CategoryStatisticsComponent
  },
  {
    path: 'history',
    component: HistoryDiagramsComponent
  },
  {
    path: 'about',
    component: AboutComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
