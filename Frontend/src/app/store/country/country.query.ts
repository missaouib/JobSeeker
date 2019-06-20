import { Country } from '../../models/country.interfaces';
import { CountryState, CountryStore } from './country.store';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class CountryQuery extends QueryEntity<CountryState, Country> {

  constructor(protected store: CountryStore){
    super(store);
  }

  getInput(){
    return this.select((state) => {
      return state.input;
    })
  }

  getCountries() {
    return this.selectAll();
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
