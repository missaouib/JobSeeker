import {CategoryStatistics} from '../../models/categoryStatistics.interfaces';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';

export interface CategoryState extends EntityState<CategoryStatistics> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'categories'})
export class CategoryStatisticsStore extends EntityStore<CategoryState, CategoryStatistics> {

  constructor() {
    super();
  }
}
