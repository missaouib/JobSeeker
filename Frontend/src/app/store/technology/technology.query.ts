import { Technology } from './../../models/technology.model';

import { TechnologyState, TechnologyStore } from './technology.store';
import { Injectable } from '@angular/core';
import { QueryEntity } from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class TechnologyQuery extends QueryEntity<TechnologyState, Technology> {

  constructor(protected store: TechnologyStore){
    super(store);
  }

  updateTechnologies(technologyState: Technology[]) {
    this.store.set(technologyState);
  }
}
