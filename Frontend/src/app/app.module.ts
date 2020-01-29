import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AkitaNgDevtools} from '@datorama/akita-ngdevtools';
import {environment} from '../environments/environment';

import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatTableModule} from '@angular/material/table';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule, MatSortModule} from '@angular/material';
import {MatTabsModule} from '@angular/material/tabs';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatRadioModule} from '@angular/material/radio';
import {MatSliderModule} from '@angular/material/slider';
import {MatSelectModule} from '@angular/material/select';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";

import {HttpService} from './services/http.service';
import {ItJobOffersInPolandComponent} from "./views/it-job-offers-in-poland/it-job-offers-in-poland.component";
import {ItJobOffersInWorldComponent} from './views/it-job-offers-in-world/it-job-offers-in-world.component';
import {TechnologyStatisticsInPolandComponent} from './views/technology-statistics-in-poland/technology-statistics-in-poland.component';
import {CategoryStatisticsComponent} from './views/category-statistics/category-statistics.component';
import {MainInputFieldComponent} from './shared/main-input-field/main-input-field.component';
import {SpaceBetween3Chars} from './pipes/space-between-3-chars.pipe';
import {AboutComponent} from './views/about/about.component';
import {HistoryDiagramsComponent} from './views/history-diagrams/history-diagrams.component';
import {TechnologyStatisticsInWorldComponent} from "./views/technology-statistics-in-world/technology-statistics-in-world.component";
import {BarChartModule, LineChartModule} from "@swimlane/ngx-charts";
import {ChooseDiagramComponent} from "./views/history-diagrams/choose-diagram/choose-diagram.component";
import {MatFormFieldModule} from "@angular/material/form-field";


@NgModule({
  declarations: [
    AppComponent,
    MainInputFieldComponent,
    ItJobOffersInPolandComponent,
    ItJobOffersInWorldComponent,
    TechnologyStatisticsInPolandComponent,
    TechnologyStatisticsInWorldComponent,
    CategoryStatisticsComponent,
    HistoryDiagramsComponent,
    ChooseDiagramComponent,
    AboutComponent,
    SpaceBetween3Chars
  ],
  imports: [
    environment.production ? [] : AkitaNgDevtools.forRoot(),
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
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
    MatAutocompleteModule,
    MatSlideToggleModule,
    MatPaginatorModule,
    MatSnackBarModule,
    BarChartModule,
    LineChartModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
