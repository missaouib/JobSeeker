import { NavigationBarComponent } from './views/navigation-bar/navigation-bar.component';
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

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpService } from './services/http.service';
import { CitiesListComponent } from './views/technology-view/cities-list/cities-list.component';
import { TechnologyInputComponent } from './views/technology-view/technology-input/technology-input.component';
import { TechnologyViewComponent } from './views/technology-view/technology-view.component';
import { CityViewComponent } from './views/city-view/city-view.component';
import { TechnologiesListComponent } from './views/city-view/technologies-list/technologies-list.component';
import { CityInputComponent } from './views/city-view/city-input/city-input.component';

//animations
//empty = all it
//block button per 5 sec
//suma data?
//diagrams

@NgModule({
  declarations: [
    AppComponent,
    CitiesListComponent,
    TechnologyInputComponent,
    TechnologyViewComponent,
    CityViewComponent,
    TechnologiesListComponent,
    CityInputComponent,
    NavigationBarComponent
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
    MatProgressSpinnerModule,
    MatTabsModule,
    MatCardModule,
    ReactiveFormsModule
  ],
  providers: [HttpService],
  bootstrap: [AppComponent]
})
export class AppModule { }
