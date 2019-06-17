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

  ngDoCheck(){
    this.pageIndex = this.paginator.pageIndex;
    this.pageLimit = this.paginator.pageSize;
  }

  ngOnInit() {

  }

  constructor(private resultInputService: ResultInputService) {
    this.resultInputService.showSpinner$.subscribe(() => {
      this.countryList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCountryTable$.subscribe(countries => {
      this.countryList = countries;
      this.dataSource = new MatTableDataSource(this.countryList);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.sort.disableClear = true;

      this.countryList.map(country => country.per100k = Number((country.linkedin / (country.population / 100000)).toFixed(2)));
      this.totalOffers = this.countryList.map(city => city.linkedin).reduce((sum, current) => sum + current);
      this.paginator._intl.itemsPerPageLabel = 'Total: ' + this.totalOffers;
      this.showSpinner = false;
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
