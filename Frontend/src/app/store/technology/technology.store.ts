import { Technology } from './../../models/technology.model';
import { EntityState, EntityStore, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

export interface TechnologyState extends EntityState<Technology> {}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'categories'})
export class TechnologyStore extends EntityStore<TechnologyState, Technology> {

  constructor(){
    super();
  }
}
