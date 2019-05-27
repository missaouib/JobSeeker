import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';
import { Category } from '../models/category.model';

@Injectable({
  providedIn: 'root'
})

export class CategoryService {

  fillTable$: Observable<any>;
  private fillTableSubject = new Subject<any>();

  showSpinner$: Observable<any>;
  private showSpinnerSubject = new Subject<any>();

  constructor() {
    this.fillTable$ = this.fillTableSubject.asObservable();
    this.showSpinner$ = this.showSpinnerSubject.asObservable();
  }

  fillTable(cities: Category[]) {
    this.fillTableSubject.next(cities);
  }

  showSpinner() {
    this.showSpinnerSubject.next();
  }
}
