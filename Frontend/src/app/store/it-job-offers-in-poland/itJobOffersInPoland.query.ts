import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';
import {CityState, ItJobOffersInPolandStore} from './itJobOffersInPoland.store';
import {JobOffer} from "../../models/jobOffer.interfaces";

@Injectable({providedIn: 'root'})
export class ItJobOffersInPolandQuery extends QueryEntity<CityState, JobOffer> {

  constructor(protected store: ItJobOffersInPolandStore) {
    super(store);
  }

  getInput() {
    return this.select((state) => {
      return state.input;
    });
  }

  getSpinner() {
    return this.select((state) => {
      return state.showSpinner;
    });
  }

  getCities() {
    return this.selectAll();
  }

  updateCities(cityState: JobOffer[]) {
    this.store.set({...cityState});
  }

  updateSpinner(showSpinner: boolean) {
    this.store.update(() => {
      return {
        showSpinner: showSpinner,
      };
    });
  }

  updateMainInput(mainInput: String) {
    this.store.update(() => {
      return {
        input: mainInput,
      };
    });
  }
}
