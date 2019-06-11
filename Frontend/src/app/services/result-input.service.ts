import { Country } from '../models/country.model';
import { Technology } from '../models/technology.model';
import { Category } from '../models/category.model';
import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { City } from '../models/city.model';

@Injectable({
  providedIn: 'root'
})

export class ResultInputService {

  fillCityTable$: Observable<any>;
  fillCountryTable$: Observable<any>;
  fillTechnologyTable$: Observable<any>;
  fillCategoryTable$: Observable<any>;

  private fillCityTableSubject = new Subject<any>();
  private fillCountryTableSubject = new Subject<any>();
  private fillTechnologyTableSubject = new Subject<any>();
  private fillCategoryTableSubject = new Subject<any>();

  showSpinner$: Observable<any>;
  private showSpinnerSubject = new Subject<any>();

  constructor() {
    this.fillCityTable$ = this.fillCityTableSubject.asObservable();
    this.fillCountryTable$ = this.fillCountryTableSubject.asObservable();
    this.fillTechnologyTable$ = this.fillTechnologyTableSubject.asObservable();
    this.fillCategoryTable$ = this.fillCategoryTableSubject.asObservable();
    this.showSpinner$ = this.showSpinnerSubject.asObservable();
  }

  fillCityTable(data: City[]) {
    this.fillCityTableSubject.next(data);
  }

  fillCountryTable(data: Country[]) {
    this.fillCountryTableSubject.next(data);
  }

  fillTechnologyTable(data: Technology[]) {
    this.fillTechnologyTableSubject.next(data);
  }

  fillCategoryTable(data: Category[]) {
    this.fillCategoryTableSubject.next(data);
  }

  showSpinner() {
    this.showSpinnerSubject.next();
  }
}
