import {CountryQuery} from '../../store/country/country.query';
import {Component, DoCheck, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Country} from "../../models/country.interfaces";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-it-job-offers-in-world',
  templateUrl: './it-job-offers-in-world.component.html',
  styleUrls: ['./it-job-offers-in-world.component.css']
})
export class ItJobOffersInWorldComponent implements DoCheck, OnInit, OnDestroy {

  totalOffers: number;
  showSpinner = false;
  pageIndex: number;
  pageLimit: number;
  countryList: Country[] = [];
  dataSource = new MatTableDataSource(this.countryList);
  displayedColumns: string[] = ['position', 'name', 'linkedin', 'population', 'per100k', 'area', 'density'];
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  private subscriptions: Subscription[] = [];
  private subscription: Subscription;

  constructor(private resultInputService: ResultInputService, private countryQuery: CountryQuery) {

    this.subscriptions.push(this.countryQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerCountry$.subscribe(() => {
      this.countryList.length = 0;
      this.countryQuery.updateSpinner(true);
      this.showSpinner = true;
    }));

    this.subscription = this.resultInputService.fillCountryTable$.subscribe((countries: Country[]) => {
      this.countryList = countries;
      this.fillTable(this.countryList);
      this.countryQuery.updateSpinner(false);
      this.showSpinner = false;

      this.countryQuery.updateCountries(this.countryList);
    });
  }

  ngDoCheck() {
    this.pageIndex = this.paginator.pageIndex;
    this.pageLimit = this.paginator.pageSize;
  }

  ngOnInit() {
    this.subscriptions.push(this.countryQuery.getCountries()
      .subscribe(countries => {
        if (countries.length !== 0 && !this.showSpinner) {
          this.fillTable(countries);
        }
      }));
  }

  fillTable(countries: Country[]) {
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

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());

    if (!this.showSpinner) {
      this.subscription.unsubscribe();
    }
  }
}
