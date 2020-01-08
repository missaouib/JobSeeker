import {Category} from '../../models/category.interfaces';
import {EntityState, EntityStore, StoreConfig} from '@datorama/akita';
import {Injectable} from '@angular/core';

export interface CategoryState extends EntityState<Category> {
}

@Injectable({providedIn: 'root'})
@StoreConfig({name: 'categories'})
export class CategoryStore extends EntityStore<CategoryState, Category> {

  constructor() {
    super();
  }
}
