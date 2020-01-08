import {Category} from '../../models/category.interfaces';
import {CategoryState, CategoryStore} from './category.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class CategoryQuery extends QueryEntity<CategoryState, Category> {

  constructor(protected store: CategoryStore) {
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

  getCategories() {
    return this.selectAll();
  }

  updateCategories(categoryState: Category[]) {
    this.store.set({...categoryState});
  }

  updateSpinner(showSpinner: boolean) {
    this.store.update(() => {
      return {
        showSpinner: showSpinner,
      };
    });
  }

  updateMainInput(mainInput: String) {
    this.store.update(() => {
      return {
        input: mainInput,
      };
    });
  }
}
