import {TechnologyStatistics} from '../../models/technology-statistics.interfaces';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';

export interface TechnologyWorldState extends EntityState<TechnologyStatistics> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'technologies-world'})
export class TechnologyStatisticsInWorldStore extends EntityStore<TechnologyWorldState, TechnologyStatistics> {

  constructor() {
    super();
  }
}
