import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';
import {JobOffer} from "../../models/jobOffer.interfaces";

export interface CityState extends EntityState<JobOffer> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'cities'})
export class ItJobOffersInPolandStore extends EntityStore<CityState, JobOffer> {

  constructor() {
    super();
  }
}
