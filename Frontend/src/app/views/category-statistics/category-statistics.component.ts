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
  categoriesList: Category[] = [];
  dataSource = new MatTableDataSource(this.categoriesList);
  displayedColumns: string[] = ['position', 'polishName', 'pracuj'];
  isLanguage: boolean;

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private resultInputService: ResultInputService) {
    this.resultInputService.showSpinner$.subscribe(() => {
      this.categoriesList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCategoryTable$.subscribe(categories => {
      this.categoriesList = categories;
      this.dataSource = new MatTableDataSource(this.categoriesList);
      this.dataSource.sort = this.sort;
      this.sort.disableClear = true;

      this.totalOffers = this.categoriesList.map(category => category.pracuj).reduce((sum, current) => sum + current);
      this.showSpinner = false;
    });
  }

  switchLanguage(){
    this.isLanguage = !this.isLanguage;
  }

}
