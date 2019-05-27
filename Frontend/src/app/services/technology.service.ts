import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { Technology } from '../models/technology.model';

@Injectable({
  providedIn: 'root'
})

export class TechnologyService {

  fillTable$: Observable<any>;
  private fillTableSubject = new Subject<any>();

  showSpinner$: Observable<any>;
  private showSpinnerSubject = new Subject<any>();

  constructor() {
    this.fillTable$ = this.fillTableSubject.asObservable();
    this.showSpinner$ = this.showSpinnerSubject.asObservable();
  }

  fillTable(cities: Technology[]){
    this.fillTableSubject.next(cities);
  }

  showSpinner() {
    this.showSpinnerSubject.next();
  }
}
