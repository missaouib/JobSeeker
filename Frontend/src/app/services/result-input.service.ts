import {TechnologyStatistics} from '../models/technology-statistics.interfaces';
import {CategoryStatistics} from '../models/category-statistics.interfaces';
import {Observable, Subject} from 'rxjs';
import {Injectable} from '@angular/core';
import {JobOffer} from "../models/job-offer.interfaces";

@Injectable({providedIn: 'root'})
export class ResultInputService {

  fillCityTable$: Observable<any>;
  fillCountryTable$: Observable<any>;
  fillTechnologyTable$: Observable<any>;
  fillCategoryTable$: Observable<any>;
  showSpinnerCity$: Observable<any>;
  showSpinnerCountry$: Observable<any>;
  showSpinnerTechnology$: Observable<any>;
  showSpinnerCategory$: Observable<any>;
  private fillCityTableSubject = new Subject<any>();
  private fillCountryTableSubject = new Subject<any>();
  private fillTechnologyTableSubject = new Subject<any>();
  private fillCategoryTableSubject = new Subject<any>();
  private showSpinnerCitySubject = new Subject<any>();
  private showSpinnerCountrySubject = new Subject<any>();
  private showSpinnerTechnologySubject = new Subject<any>();
  private showSpinnerCategorySubject = new Subject<any>();

  constructor() {
    this.fillCityTable$ = this.fillCityTableSubject.asObservable();
    this.fillCountryTable$ = this.fillCountryTableSubject.asObservable();
    this.fillTechnologyTable$ = this.fillTechnologyTableSubject.asObservable();
    this.fillCategoryTable$ = this.fillCategoryTableSubject.asObservable();

    this.showSpinnerCity$ = this.showSpinnerCitySubject.asObservable();
    this.showSpinnerCountry$ = this.showSpinnerCountrySubject.asObservable();
    this.showSpinnerTechnology$ = this.showSpinnerTechnologySubject.asObservable();
    this.showSpinnerCategory$ = this.showSpinnerCategorySubject.asObservable();
  }

  fillCityTable(data: JobOffer[]) {
    this.fillCityTableSubject.next(data);
  }

  fillCountryTable(data: JobOffer[]) {
    this.fillCountryTableSubject.next(data);
  }

  fillTechnologyTable(data: TechnologyStatistics[]) {
    this.fillTechnologyTableSubject.next(data);
  }

  fillCategoryTable(data: CategoryStatistics[]) {
    this.fillCategoryTableSubject.next(data);
  }

  showSpinnerCity() {
    this.showSpinnerCitySubject.next();
  }

  showSpinnerCountry() {
    this.showSpinnerCountrySubject.next();
  }

  showSpinnerTechnology() {
    this.showSpinnerTechnologySubject.next();
  }

  showSpinnerCategory() {
    this.showSpinnerCategorySubject.next();
  }

}
