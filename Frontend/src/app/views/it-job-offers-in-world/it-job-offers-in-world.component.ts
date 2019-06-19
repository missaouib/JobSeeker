import { CountryQuery } from './../../store/country/country.query';
import { Component, DoCheck, ViewChild, OnInit } from '@angular/core';
import {Country} from "../../models/country.model";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";

@Component({
  selector: 'app-it-job-offers-in-world',
  templateUrl: './it-job-offers-in-world.component.html',
  styleUrls: ['./it-job-offers-in-world.component.css']
})
export class ItJobOffersInWorldComponent implements DoCheck, OnInit {

  totalOffers: number;
  showSpinner = false;
  pageIndex: number;
  pageLimit: number;
  countryList: Country[] = [];
  dataSource = new MatTableDataSource(this.countryList);
  displayedColumns: string[] = ['position', 'name', 'linkedin', 'population', 'per100k', 'area', 'density'];

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator

  constructor(private resultInputService: ResultInputService, private countryQuery: CountryQuery) {
    this.resultInputService.showSpinner$.subscribe(() => {
      this.countryList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCountryTable$.subscribe(countries => {
      this.countryList = countries;
      this.countryList.map(country => country.per100k = Number((country.linkedin / (country.population / 100000)).toFixed(2)));
      this.fillTable(this.countryList);

      this.showSpinner = false;
      this.countryQuery.updateCountries(this.countryList);
    });
  }

  ngDoCheck() {
    this.pageIndex = this.paginator.pageIndex;
    this.pageLimit = this.paginator.pageSize;
  }

  ngOnInit() {
    this.countryQuery.selectAll()
      .subscribe(countries => {
        if (countries.length !== 0) {
          this.fillTable(countries);
        }
      });
  }

  fillTable(countries: Country[]){
    this.countryList = countries;

    this.totalOffers = this.countryList.map(city => city.linkedin).reduce((sum, current) => sum + current);
    this.paginator._intl.itemsPerPageLabel = 'Total Offers: ' + this.totalOffers;

    this.dataSource = new MatTableDataSource(this.countryList);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
    this.sort.disableClear = true;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
