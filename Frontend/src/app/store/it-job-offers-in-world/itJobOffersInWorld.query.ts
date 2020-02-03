import {CountryState, ItJobOffersInWorldStore} from './itJobOffersInWorld.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';
import {JobOffer} from "../../models/job-offer.interfaces";

@Injectable({providedIn: 'root'})
export class ItJobOffersInWorldQuery extends QueryEntity<CountryState, JobOffer> {

  constructor(protected store: ItJobOffersInWorldStore) {
    super(store);
  }

  getInput() {
    return this.select((state) => {
      return state.input;
    })
  }

  getSpinner() {
    return this.select((state) => {
      return state.showSpinner;
    });
  }

  getCountries() {
    return this.selectAll();
  }

  updateCountries(countryState: JobOffer[]) {
    this.store.set({...countryState});
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
