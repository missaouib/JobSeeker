import { first } from 'rxjs/operators';
import { CategoryQuery } from '../../store/category/category.query';
import {Component, ViewChild, OnInit, OnDestroy} from '@angular/core';
import {Category} from "../../models/category.interfaces";
import {MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-category-statistics-view',
  templateUrl: './category-statistics.component.html',
  styleUrls: ['./category-statistics.component.css']
})
export class CategoryStatisticsComponent implements OnInit, OnDestroy {

  totalOffers: number;
  isLanguage: boolean;
  showSpinner = false;
  categoryList: Category[] = [];
  dataSource = new MatTableDataSource(this.categoryList);
  displayedColumns: string[] = ['position', 'polishName', 'pracuj'];
  private subscriptions: Subscription[] = [];

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  constructor(private resultInputService: ResultInputService, private categoryQuery: CategoryQuery) {

    this.subscriptions.push(this.categoryQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerCategory$.subscribe(() => {
      this.categoryQuery.updateSpinner(true);
      this.showSpinner = true;
      this.categoryList.length = 0;
    }));

    this.resultInputService.fillCategoryTable$.pipe(first()).subscribe((categories: Category[]) => {
      this.categoryQuery.updateSpinner(false);
      this.showSpinner = false;
      this.fillTable(categories);
      this.categoryQuery.updateCategories(categories);
    });
  }

  ngOnInit() {
    this.subscriptions.push(this.categoryQuery.getCategories()
    .subscribe(categories => {
      if (categories.length !== 0 && !this.showSpinner) {
        this.fillTable(categories);
      }
    }));
  }

  fillTable(categories: Category[]){
    this.categoryList = categories;
    this.dataSource = new MatTableDataSource(this.categoryList);
    this.dataSource.sort = this.sort;
    this.sort.disableClear = true;

    this.totalOffers = this.categoryList.map(category => category.pracuj).reduce((sum, current) => sum + current);
    this.showSpinner = false;
  }

  switchLanguage() {
    this.isLanguage = !this.isLanguage;
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
