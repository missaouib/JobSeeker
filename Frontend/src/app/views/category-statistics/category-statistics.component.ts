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

  totalPracuj: number;
  totalIndeed: number;
  isLanguage: boolean;
  showSpinner = false;
  categoryList: CategoryStatistics[] = [];
  pracujList: CategoryStatistics[] = [];
  indeedList: CategoryStatistics[] = [];
  dataSourceIndeed = new MatTableDataSource(this.indeedList);
  dataSourcePracuj = new MatTableDataSource(this.pracujList);
  displayedColumnsPracuj: string[] = ['position', 'polishName', 'pracuj'];
  displayedColumnsIndeed: string[] = ['position', 'polishName', 'indeed'];

  @ViewChild('indeedTable', {static: true}) indeedTable: MatSort;
  @ViewChild('pracujTable', {static: true}) pracujTable: MatSort;
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
    this.indeedList = categories.filter(category => category.indeed >= 0);
    this.pracujList = categories.filter(category => category.pracuj >= 0);

    this.dataSourceIndeed = new MatTableDataSource(this.indeedList);
    this.dataSourceIndeed.sort = this.indeedTable;
    this.indeedTable.disableClear = true;

    this.dataSourcePracuj = new MatTableDataSource(this.pracujList);
    this.dataSourcePracuj.sort = this.pracujTable;
    this.pracujTable.disableClear = true;

    this.totalPracuj = this.categoryList.map(category => category.pracuj).reduce((sum, current) => sum + current);
    this.totalIndeed = this.categoryList.map(category => category.indeed).reduce((sum, current) => sum + current);
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
