import {Country} from '../../models/country.interfaces';
import {Injectable} from '@angular/core';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';

export interface CountryState extends EntityState<Country> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'countries'})
export class CountryStore extends EntityStore<CountryState, Country> {

  constructor() {
    super();
  }
}
