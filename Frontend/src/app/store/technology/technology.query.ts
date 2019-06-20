import { Technology } from '../../models/technology.interfaces';

import { TechnologyState, TechnologyStore } from './technology.store';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class TechnologyQuery extends QueryEntity<TechnologyState, Technology> {

  constructor(protected store: TechnologyStore){
    super(store);
  }

  getInput(){
    return this.select((state) => {
      return state.input;
    })
  }

  getTechnologies() {
    return this.selectAll();
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
