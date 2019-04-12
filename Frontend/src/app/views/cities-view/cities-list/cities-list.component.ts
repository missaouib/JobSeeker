import { City } from './../../../models/city.model';
import { CityService } from './../../../services/city.service';
import { Component, ViewChild } from '@angular/core';
import { MatSort, MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-cities-list',
  templateUrl: './cities-list.component.html',
  styleUrls: ['./cities-list.component.css']
})

export class CitiesListComponent {

  showSpinner = false;

  citiesList: City[] = [];

  displayedColumns: string[] = [
    'name', 'population', 'jobAmount', 'jobOfferPer100kCitizens', 'areaSquareKilometers', 'destinyOfPopulation'];
  dataSource = new MatTableDataSource(this.citiesList);

  @ViewChild(MatSort) sort: MatSort;

  constructor(private cityService: CityService) {
    //subscribe showspinner
    //this.showSpinner = true;
    this.cityService.fillTable$.subscribe( cities => {
      this.citiesList = cities;
      this.dataSource = new MatTableDataSource(this.citiesList);
      this.dataSource.sort = this.sort;
      this.showSpinner = false;
    });
   }
}
