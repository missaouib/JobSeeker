import { Injectable } from '@angular/core';
import { ResultInputService } from './../../services/result-input.service';
import { CategoryStore } from './category.store';

@Injectable({providedIn: 'root'})

export class CategoryService {
  constructor(private categoryStore: CategoryStore, private ResultInputService: ResultInputService){}

  setCategories(){
    this.ResultInputService.fillCategoryTable$
      .subscribe(categories => {
        this.categoryStore.set(categories);
      })
  }

}
