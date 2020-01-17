import {TechnologyStatistics} from '../../models/technologyStatistics.interfaces';

import {TechnologyPolandState, TechnologyStatisticsInPolandStore} from './technologyStatisticsInPoland.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class TechnologyStatisticsInPolandQuery extends QueryEntity<TechnologyPolandState, TechnologyStatistics> {

  constructor(protected store: TechnologyStatisticsInPolandStore) {
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
