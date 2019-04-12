import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { City } from '../models/city.model';
//import { empty } from 'rxjs'
import { of } from 'rxjs';

const empty = of({})

@Injectable({
  providedIn: 'root'
})

export class CityService {

  fillTable$: Observable<any>;
  private fillTableSubject = new Subject<any>();

  showSpinner$: Observable<any>;
  private showSpinnerSubject = new Subject<any>();

  constructor() {
    this.fillTable$ = this.fillTableSubject.asObservable();
    this.showSpinner$ = this.showSpinnerSubject.asObservable();
  }

  fillTable(cities: City[]){
    this.fillTableSubject.next(cities);
  }

  showSpinner() {
    this.showSpinnerSubject.next();
  }
}
