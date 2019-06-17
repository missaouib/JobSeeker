import { CityQuery } from './../../store/city/city.query';
import { Component, ViewChild, OnInit } from '@angular/core';
import {City} from "../../models/city.model";
import {MatSort, MatTableDataSource, getMatIconFailedToSanitizeLiteralError} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";

@Component({
  selector: 'app-it-job-offers-view',
  templateUrl: './it-job-offers-in-poland.component.html',
  styleUrls: ['./it-job-offers-in-poland.component.css']
})
export class ItJobOffersInPolandComponent implements OnInit {

  totalOffers: number[] = [];
  totalJobOffersSum: number;
  showSpinner = false;
  cityList: City[] = [];
  dataSource = new MatTableDataSource(this.cityList);
  displayedColumns: string[] = [
    'position', 'name', 'linkedin', 'pracuj', 'noFluffJobs', 'justJoin', 'total', 'population', 'per100k', 'area', 'density'];

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private resultInputService: ResultInputService, private cityQuery: CityQuery) {
    this.resultInputService.showSpinner$.subscribe( () => {
      this.cityList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCityTable$.subscribe( cities => {
      this.fillTable(cities);
      this.cityQuery.updateCities(cities);
    });
  }

  ngOnInit() {
    this.cityQuery.selectAll()
      .subscribe(cities => {
        if (cities.length !== 0) {
          this.fillTable(cities);
        }
      });
  }

  fillTable(cities: City[]) {
    this.cityList = cities;
    this.cityList = this.cityList.filter(city => city.name !== 'All Cities');

    this.cityList.map(city => city.total = city.linkedin + city.pracuj + city.noFluffJobs + city.justJoin);
    this.cityList.map(city => city.per100k = Number((city.total / (city.population / 100000)).toFixed(2)));

    this.totalOffers[0] = this.cityList.map(city => city.linkedin).reduce((sum, current) => sum + current);
    this.totalOffers[1] = this.cityList.map(city => city.pracuj).reduce((sum, current) => sum + current);
    this.totalOffers[2] = this.cityList.map(city => city.noFluffJobs).reduce((sum, current) => sum + current);
    this.totalOffers[3] = this.cityList.map(city => city.justJoin).reduce((sum, current) => sum + current);
    this.totalJobOffersSum = this.cityList.map(city => city.total).reduce((sum, current) => sum + current);

    this.dataSource = new MatTableDataSource(this.cityList);
    this.dataSource.sort = this.sort;
    this.sort.disableClear = true;

    this.showSpinner = false;
  }

}
