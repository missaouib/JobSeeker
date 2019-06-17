import { Injectable } from '@angular/core';
import { Category } from './../../models/category.model';
import { CategoryStore } from './category.store';

@Injectable({providedIn: 'root'})

export class CategoryService {
  constructor(private categoryStore: CategoryStore){}

  updateCategories(categoryState: Category[]) {
    this.categoryStore.set(categoryState);
  }

}
