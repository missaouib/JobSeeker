import {ItJobOffersInWorldQuery} from '../../store/it-job-offers-in-world/itJobOffersInWorld.query';
import {Component, DoCheck, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";
import {JobOffer} from "../../models/jobOffer.interfaces";

@Component({
  selector: 'app-it-job-offers-in-world',
  templateUrl: './it-job-offers-in-world.component.html',
  styleUrls: ['./it-job-offers-in-world.component.css']
})
export class ItJobOffersInWorldComponent implements DoCheck, OnInit, OnDestroy {

  totalOffers: number[] = [];
  totalJobOffersSum: number;
  showSpinner = false;
  pageIndex: number;
  pageLimit: number;
  countryList: JobOffer[] = [];
  dataSource = new MatTableDataSource(this.countryList);
  displayedColumns: string[] = ['position', 'name', 'linkedin', 'indeed', 'total', 'population', 'per100k', 'area', 'density'];
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  private subscriptions: Subscription[] = [];
  private subscription: Subscription;

  constructor(private resultInputService: ResultInputService, private countryQuery: ItJobOffersInWorldQuery) {

    this.subscriptions.push(this.countryQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerCountry$.subscribe(() => {
      this.countryList.length = 0;
      this.countryQuery.updateSpinner(true);
      this.showSpinner = true;
    }));

    this.subscription = this.resultInputService.fillCountryTable$.subscribe((countries: JobOffer[]) => {
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

  fillTable(countries: JobOffer[]) {
    this.countryList = countries;

    this.totalOffers[0] = this.countryList.map(country => country.linkedin).reduce((sum, current) => sum + current);
    this.totalOffers[1] = this.countryList.map(country => country.indeed).reduce((sum, current) => sum + current);
    this.totalJobOffersSum = this.countryList.map(country => country.total).reduce((sum, current) => sum + current);
    this.paginator._intl.itemsPerPageLabel = 'Total Offers: ' + this.totalOffers[0] + this.totalOffers[1];

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
