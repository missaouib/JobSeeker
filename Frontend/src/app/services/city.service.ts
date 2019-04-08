import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { City } from '../models/city.model';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  fillTable$: Observable<any>;
  private fillTableSubject = new Subject<any>();

  constructor() {
    this.fillTable$ = this.fillTableSubject.asObservable();
  }

  fillTable(cities: City[]){
    this.fillTableSubject.next(cities);
  }
}
