import {CategoryStatisticsQuery} from '../../store/category-statistics/categoryStatistics.query';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CategoryStatistics} from "../../models/categoryStatistics.interfaces";
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
  categoryList: CategoryStatistics[] = [];
  dataSource = new MatTableDataSource(this.categoryList);
  displayedColumns: string[] = ['position', 'polishName', 'pracuj'];
  @ViewChild(MatSort, {static: true}) sort: MatSort;
  private subscriptions: Subscription[] = [];
  private subscription: Subscription;

  constructor(private resultInputService: ResultInputService, private categoryQuery: CategoryStatisticsQuery) {

    this.subscriptions.push(this.categoryQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerCategory$.subscribe(() => {
      this.categoryQuery.updateSpinner(true);
      this.showSpinner = true;
      this.categoryList.length = 0;
    }));

    this.subscription = this.resultInputService.fillCategoryTable$.subscribe((categories: CategoryStatistics[]) => {
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

  fillTable(categories: CategoryStatistics[]) {
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

    if (!this.showSpinner) {
      this.subscription.unsubscribe();
    }
  }

}
