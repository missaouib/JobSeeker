import {TechnologyStatistics} from '../../models/technologyStatistics.interfaces';

import {TechnologyWorldState, TechnologyStatisticsInWorldStore} from './technologyStatisticsInWorld.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class TechnologyStatisticsInWorldQuery extends QueryEntity<TechnologyWorldState, TechnologyStatistics> {

  constructor(protected store: TechnologyStatisticsInWorldStore) {
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

  getTechnologies() {
    return this.selectAll();
  }

  updateSpinner(showSpinner: boolean) {
    this.store.update(() => {
      return {
        showSpinner: showSpinner,
      };
    });
  }

  updateTechnologies(technologyState: TechnologyStatistics[]) {
    this.store.set({...technologyState});
  }

  updateMainInput(mainInput: String) {
    this.store.update(() => {
      return {
        input: mainInput,
      };
    });
  }
}
