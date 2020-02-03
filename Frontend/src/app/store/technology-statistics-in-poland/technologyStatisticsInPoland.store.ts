import {TechnologyStatistics} from '../../models/technology-statistics.interfaces';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';

export interface TechnologyPolandState extends EntityState<TechnologyStatistics> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'technologies-poland'})
export class TechnologyStatisticsInPolandStore extends EntityStore<TechnologyPolandState, TechnologyStatistics> {

  constructor() {
    super();
  }
}
