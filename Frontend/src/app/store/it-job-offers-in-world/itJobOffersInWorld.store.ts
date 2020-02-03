import {Injectable} from '@angular/core';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {JobOffer} from "../../models/job-offer.interfaces";

export interface CountryState extends EntityState<JobOffer> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'countries'})
export class ItJobOffersInWorldStore extends EntityStore<CountryState, JobOffer> {

  constructor() {
    super();
  }
}
