import {AboutComponent} from './views/about/about.component';
import {HistoryDiagramsComponent} from './views/history-diagrams/history-diagrams.component';
import {ItJobOffersInPolandComponent} from './views/it-job-offers-in-poland/it-job-offers-in-poland.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TechnologyStatisticsComponent} from './views/technology-statistics/technology-statistics.component';
import {CategoryStatisticsComponent} from "./views/category-statistics/category-statistics.component";
import {ItJobOffersInWorldComponent} from "./views/it-job-offers-in-world/it-job-offers-in-world.component";

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
    path: 'technology',
    component: TechnologyStatisticsComponent
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
