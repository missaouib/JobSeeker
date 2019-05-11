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
import { CitiesListComponent } from './views/it-job-offers-view/cities-list/cities-list.component';
import { TechnologyInputComponent } from './views/it-job-offers-view/technology-input/technology-input.component';
import { TechnologyStatisticsViewComponent } from './views/technology-statistics-view/technology-statistics-view.component';
import { TechnologiesListComponent } from './views/technology-statistics-view/technologies-list/technologies-list.component';
import { CityInputComponent } from './views/technology-statistics-view/city-input/city-input.component';
import { CategoryStatisticsViewComponent } from './views/category-statistics-view/category-statistics-view.component';
import {ItJobOffersViewComponent} from "./views/it-job-offers-view/it-job-offers-view.component";

@NgModule({
  declarations: [
    AppComponent,
    ItJobOffersViewComponent,
    TechnologyStatisticsViewComponent,
    CategoryStatisticsViewComponent,
    TechnologiesListComponent,
    CityInputComponent,
    CitiesListComponent,
    TechnologyInputComponent
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
