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

  total = 0;
  today = Date.now();
  showSpinner = false;
  citiesList: City[] = [];
  dataSource = new MatTableDataSource(this.citiesList);
  displayedColumns: string[] = [
    'position', 'name', 'jobAmount', 'population', 'jobOfferPer100kCitizens', 'areaSquareKilometers', 'destinyOfPopulation', 'averageFlatPrice'];

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

      this.total = this.citiesList.map(city => city.jobAmount).reduce((sum, current) => sum + current);
      this.showSpinner = false;
    });
   }

}
