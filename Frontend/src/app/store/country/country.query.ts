import { Country } from './../../models/country.model';
import { CountryState, CountryStore } from './country.store';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class CountryQuery extends QueryEntity<CountryState, Country> {

  constructor(protected store: CountryStore){
    super(store);
  }

  updateCountries(countryState: Country[]) {
    this.store.set({...countryState});
  }

  updateMainInput(mainInput: String) {
    this.store.update(() => {
      return {
        input: mainInput,
      };
    });
  }
}
