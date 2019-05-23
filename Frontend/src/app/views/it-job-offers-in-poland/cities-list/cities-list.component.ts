import { City } from '../../../models/city.model';
import { CityService } from '../../../services/city.service';
import { Component, ViewChild } from '@angular/core';
import { MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-cities-list',
  templateUrl: './cities-list.component.html',
  styleUrls: ['./cities-list.component.css']
})

export class CitiesListComponent {

  totalOffers: number[] = [];
  totalJobOffersSum: number;
  today = Date.now();
  showSpinner = false;
  citiesList: City[] = [];
  dataSource = new MatTableDataSource(this.citiesList);
  displayedColumns: string[] = [
    'position', 'name', 'linkedinOffers', 'pracujOffers', 'noFluffJobsOffers', 'justJoinOffers', 'totalJobOffers',
    'population', 'jobOfferPer100kCitizens', 'areaSquareKilometers', 'destinyOfPopulation', 'averageFlatPrice'];

  @ViewChild(MatSort) sort: MatSort;

  constructor(private cityService: CityService) {
    this.cityService.showSpinner$.subscribe( () => {
      this.citiesList.length = 0;
      this.showSpinner = true;
    });

    this.cityService.fillTable$.subscribe( cities => {
      this.citiesList = cities;
      this.dataSource = new MatTableDataSource(this.citiesList);
      this.dataSource.sort = this.sort;
      this.sort.disableClear = true;

      this.totalOffers[0] = this.citiesList.map(city => city.linkedinOffers).reduce((sum, current) => sum + current);
      this.totalOffers[1] = this.citiesList.map(city => city.pracujOffers).reduce((sum, current) => sum + current);
      this.totalOffers[2] = this.citiesList.map(city => city.noFluffJobsOffers).reduce((sum, current) => sum + current);
      this.totalOffers[3] = this.citiesList.map(city => city.justJoinOffers).reduce((sum, current) => sum + current);
      this.totalJobOffersSum = this.citiesList.map(city => city.totalJobOffers).reduce((sum, current) => sum + current);

      this.showSpinner = false;
    });
   }

}
