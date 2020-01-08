import {Technology} from '../../models/technology.interfaces';

import {TechnologyState, TechnologyStore} from './technology.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class TechnologyQuery extends QueryEntity<TechnologyState, Technology> {

  constructor(protected store: TechnologyStore) {
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

  updateTechnologies(technologyState: Technology[]) {
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
