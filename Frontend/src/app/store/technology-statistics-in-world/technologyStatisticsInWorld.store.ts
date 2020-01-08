import {TechnologyStatistics} from '../../models/technologyStatistics.interfaces';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';

export interface TechnologyState extends EntityState<TechnologyStatistics> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'technologies'})
export class TechnologyStatisticsInWorldStore extends EntityStore<TechnologyState, TechnologyStatistics> {

  constructor() {
    super();
  }
}
