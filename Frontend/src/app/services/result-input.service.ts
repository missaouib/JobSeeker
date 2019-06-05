import { Country } from './../models/country.model';
import { Technology } from './../models/technology.model';
import { Category } from './../models/category.model';
import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { City } from '../models/city.model';

@Injectable({
  providedIn: 'root'
})

export class ResultInputService {

  fillTable$: Observable<any>;
  private fillTableSubject = new Subject<any>();

  showSpinner$: Observable<any>;
  private showSpinnerSubject = new Subject<any>();

  constructor() {
    this.fillTable$ = this.fillTableSubject.asObservable();
    this.showSpinner$ = this.showSpinnerSubject.asObservable();
  }

  fillCityTable(data: City[]) {
    this.fillTableSubject.next(data);
  }

  fillCountryTable(data: Country[]) {
    this.fillTableSubject.next(data);
  }

  fillTechnologyTable(data: Technology[]) {
    this.fillTableSubject.next(data);
  }

  fillCategoryTable(data: Category[]) {
    this.fillTableSubject.next(data);
  }

  showSpinner() {
    this.showSpinnerSubject.next();
  }
}
