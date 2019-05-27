import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatSortModule } from '@angular/material';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material';
import { MatRadioModule } from '@angular/material/radio';
import { MatSliderModule } from '@angular/material/slider';
import { MatSelectModule } from '@angular/material/select';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpService } from './services/http.service';
import { CitiesListComponent } from './views/it-job-offers-in-poland/cities-list/cities-list.component';
import { TechnologyInputComponent } from './views/it-job-offers-in-poland/technology-input/technology-input.component';
import { TechnologyStatisticsComponent } from './views/technology-statistics/technology-statistics.component';
import { TechnologiesListComponent } from './views/technology-statistics/technologies-list/technologies-list.component';
import { CityInputComponent } from './views/technology-statistics/city-input/city-input.component';
import { CategoryStatisticsComponent } from './views/category-statistics/category-statistics.component';
import { ItJobOffersInPolandComponent } from "./views/it-job-offers-in-poland/it-job-offers-in-poland.component";
import { ItJobOffersInWorldComponent } from './views/it-job-offers-in-world/it-job-offers-in-world.component';
import { CategoriesListComponent } from './views/category-statistics/categories-list/categories-list.component';
import { SelectedCityComponent } from './views/category-statistics/selected-city/selected-city.component';

@NgModule({
  declarations: [
    AppComponent,
    ItJobOffersInPolandComponent,
    TechnologyStatisticsComponent,
    CategoryStatisticsComponent,
    TechnologiesListComponent,
    CityInputComponent,
    CitiesListComponent,
    TechnologyInputComponent,
    ItJobOffersInWorldComponent,
    CategoriesListComponent,
    SelectedCityComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatInputModule,
    MatIconModule,
    MatTableModule,
    MatSortModule,
    MatButtonModule,
    MatRadioModule,
    MatTooltipModule,
    MatSliderModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatCardModule,
    ReactiveFormsModule
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
