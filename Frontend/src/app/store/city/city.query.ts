import { City } from '../../models/city.interfaces';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';
import { CityStore, CityState} from './city.store';

@Injectable({providedIn: 'root'})
export class CityQuery extends QueryEntity<CityState, City> {

  constructor(protected store: CityStore){
    super(store);
  }

  getInput(){
    return this.select((state) => {
      return state.input;
    })
  }

  getCities() {
    return this.selectAll();
  }

  updateCities(cityState: City[]) {
    this.store.set({...cityState});
  }

  updateMainInput(mainInput: String){
    this.store.update(() => {
      return {
        input: mainInput,
      };
    });
  }
}
