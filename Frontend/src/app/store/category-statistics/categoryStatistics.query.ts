import {CategoryStatistics} from '../../models/category-statistics.interfaces';
import {CategoryState, CategoryStatisticsStore} from './categoryStatistics.store';
import {Injectable} from '@angular/core';
import {QueryEntity} from '@datorama/akita';

@Injectable({providedIn: 'root'})
export class CategoryStatisticsQuery extends QueryEntity<CategoryState, CategoryStatistics> {

  constructor(protected store: CategoryStatisticsStore) {
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

  updateCategories(categoryState: CategoryStatistics[]) {
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
