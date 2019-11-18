import { Technology } from '../../models/technology.interfaces';
import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

export interface TechnologyState extends EntityState<Technology> {}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'technologies'})
export class TechnologyStore extends EntityStore<TechnologyState, Technology> {

  constructor(){
    super();
  }
}
