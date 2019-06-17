import { Country } from './../../models/country.model';
import { CountryState, CountryStore } from './country.store';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class CountryQuery extends QueryEntity<CountryState, Country> {

  constructor(protected store: CountryStore){
    super(store);
  }

  updateCategories(CountryState: Country[]) {
    this.store.set(CountryState);
  }
}
