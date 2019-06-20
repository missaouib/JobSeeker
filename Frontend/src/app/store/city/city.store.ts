import { City } from '../../models/city.interfaces';
import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

export interface CityState extends EntityState<City> {}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'cities'})
export class CityStore extends EntityStore<CityState, City> {

  constructor(){
    super();
  }
}
