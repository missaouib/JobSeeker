import { ResultInputService } from '../../../services/result-input.service';
import { City } from '../../../models/city.model';
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
  showSpinner = false;
  citiesList: City[] = [];
  dataSource = new MatTableDataSource(this.citiesList);
  displayedColumns: string[] = [
    'position', 'name', 'linkedin', 'pracuj', 'noFluffJobs', 'justJoin', 'total', 'population', 'per100k', 'area', 'density'];

  @ViewChild(MatSort) sort: MatSort;

  constructor(private resultInputService: ResultInputService) {
    this.resultInputService.showSpinner$.subscribe( () => {
      this.citiesList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCityTable$.subscribe( cities => {
      this.citiesList = cities;
      this.dataSource = new MatTableDataSource(this.citiesList);
      this.dataSource.sort = this.sort;
      this.sort.disableClear = true;

      this.totalOffers[0] = this.citiesList.map(city => city.linkedin).reduce((sum, current) => sum + current);
      this.totalOffers[1] = this.citiesList.map(city => city.pracuj).reduce((sum, current) => sum + current);
      this.totalOffers[2] = this.citiesList.map(city => city.noFluffJobs).reduce((sum, current) => sum + current);
      this.totalOffers[3] = this.citiesList.map(city => city.justJoin).reduce((sum, current) => sum + current);
      this.totalJobOffersSum = this.citiesList.map(city => city.total).reduce((sum, current) => sum + current);

      this.showSpinner = false;
    });
   }

}
