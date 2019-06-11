import {Component, ViewChild} from '@angular/core';
import {Category} from "../../models/category.model";
import {MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";

@Component({
  selector: 'app-category-statistics-view',
  templateUrl: './category-statistics.component.html',
  styleUrls: ['./category-statistics.component.css']
})
export class CategoryStatisticsComponent {

  totalOffers: number;
  showSpinner = false;
  categoryList: Category[] = [];
  dataSource = new MatTableDataSource(this.categoryList);
  displayedColumns: string[] = ['position', 'polishName', 'pracuj'];
  isLanguage: boolean;

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private resultInputService: ResultInputService) {
    this.resultInputService.showSpinner$.subscribe(() => {
      this.categoryList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCategoryTable$.subscribe(categories => {
      this.categoryList = categories;
      this.dataSource = new MatTableDataSource(this.categoryList);
      this.dataSource.sort = this.sort;
      this.sort.disableClear = true;

      this.totalOffers = this.categoryList.map(category => category.pracuj).reduce((sum, current) => sum + current);
      this.showSpinner = false;
    });
  }

  switchLanguage(){
    this.isLanguage = !this.isLanguage;
  }

}
