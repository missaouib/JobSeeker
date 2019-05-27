import { CategoryService } from './../../../services/category.service';
import { MatTableDataSource, MatSort } from '@angular/material';
import { Category } from './../../../models/category.model';
import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css']
})
export class CategoriesListComponent {

  totalOffers: number;
  showSpinner = false;
  categoriesList: Category[] = [];
  dataSource = new MatTableDataSource(this.categoriesList);
  displayedColumns: string[] = ['position', 'polishName', 'pracujOffers'];

  @ViewChild(MatSort) sort: MatSort;

  constructor(private categoryService: CategoryService) {
    this.categoryService.showSpinner$.subscribe(() => {
      this.categoriesList.length = 0;
      this.showSpinner = true;
    });

    this.categoryService.fillTable$.subscribe(categories => {
      this.categoriesList = categories;
      this.dataSource = new MatTableDataSource(this.categoriesList);
      this.dataSource.sort = this.sort;
      this.sort.disableClear = true;

      this.totalOffers = this.categoriesList.map(category => category.pracujOffers).reduce((sum, current) => sum + current);
      this.showSpinner = false;
    });
  }

}
