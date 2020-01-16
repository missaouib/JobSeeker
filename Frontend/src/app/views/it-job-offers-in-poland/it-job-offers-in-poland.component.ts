import {ItJobOffersInPolandQuery} from '../../store/it-job-offers-in-poland/itJobOffersInPoland.query';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";
import {JobOffer} from "../../models/jobOffer.interfaces";

@Component({
  selector: 'app-it-job-offers-view',
  templateUrl: './it-job-offers-in-poland.component.html',
  styleUrls: ['./it-job-offers-in-poland.component.css']
})
export class ItJobOffersInPolandComponent implements OnInit, OnDestroy {

  totalOffers: number[] = [];
  totalJobOffersSum: number;
  showSpinner = false;
  cityList: JobOffer[] = [];
  dataSource = new MatTableDataSource(this.cityList);
  displayedColumns: string[] = ['position', 'name', 'linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt', 'total', 'population', 'per100k', 'area', 'density'];
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  private subscriptions: Subscription[] = [];
  private subscription: Subscription;

  constructor(private resultInputService: ResultInputService, private cityQuery: ItJobOffersInPolandQuery) {

    this.subscriptions.push(this.cityQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerCity$.subscribe(() => {
      this.cityList.length = 0;
      this.cityQuery.updateSpinner(true);
      this.showSpinner = true;
    }));

    this.subscription = this.resultInputService.fillCityTable$.subscribe((cities: JobOffer[]) => {
      this.cityList = [...cities];
      this.cityList = this.cityList.filter(city => city.name !== 'All Cities');

      this.fillTable(this.cityList);
      this.cityQuery.updateSpinner(false);
      this.showSpinner = false;

      this.cityQuery.updateCities(this.cityList);
    });
  }

  ngOnInit() {
    this.subscriptions.push(this.cityQuery.getCities()
      .subscribe(cities => {
        if (cities.length !== 0 && !this.showSpinner) {
          this.fillTable(cities);
        }
      }));
  }

  fillTable(cities: JobOffer[]) {
    this.cityList = [...cities];

    this.totalOffers[0] = this.cityList.map(city => city.linkedin).reduce((sum, current) => sum + current);
    this.totalOffers[1] = this.cityList.map(city => city.indeed).reduce((sum, current) => sum + current);
    this.totalOffers[2] = this.cityList.map(city => city.pracuj).reduce((sum, current) => sum + current);
    this.totalOffers[3] = this.cityList.map(city => city.noFluffJobs).reduce((sum, current) => sum + current);
    this.totalOffers[4] = this.cityList.map(city => city.justJoinIt).reduce((sum, current) => sum + current);
    this.totalJobOffersSum = this.cityList.map(city => city.total).reduce((sum, current) => sum + current);

    this.dataSource = new MatTableDataSource(this.cityList);
    this.dataSource.sort = this.sort;
    this.sort.disableClear = true;
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());

    if (!this.showSpinner) {
      this.subscription.unsubscribe();
    }
  }

}
