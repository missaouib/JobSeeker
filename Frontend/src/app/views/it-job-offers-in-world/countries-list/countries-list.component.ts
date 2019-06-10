import { ResultInputService } from '../../../services/result-input.service';
import { Country } from '../../../models/country.model';
import { MatTableDataSource, MatSort, MatPaginator } from '@angular/material';
import { Component, ViewChild, DoCheck } from '@angular/core';

@Component({
  selector: 'app-countries-list',
  templateUrl: './countries-list.component.html',
  styleUrls: ['./countries-list.component.css']
})
export class CountriesListComponent implements DoCheck {

  totalOffers: number;
  showSpinner = false;
  pageIndex: number;
  pageLimit: number;
  countriesList: Country[] = [];
  dataSource = new MatTableDataSource(this.countriesList);
  displayedColumns: string[] = ['position', 'name', 'linkedin', 'population', 'per100k', 'area', 'density'];

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator

  ngDoCheck(){
    this.pageIndex = this.paginator.pageIndex;
    this.pageLimit = this.paginator.pageSize;
  }

  constructor(private resultInputService: ResultInputService) {
    this.resultInputService.showSpinner$.subscribe(() => {
      this.countriesList.length = 0;
      this.showSpinner = true;
    });

    this.resultInputService.fillCountryTable$.subscribe(countries => {
      this.countriesList = countries;
      this.dataSource = new MatTableDataSource(this.countriesList);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.sort.disableClear = true;

      this.totalOffers = this.countriesList.map(city => city.linkedin).reduce((sum, current) => sum + current);
      this.paginator._intl.itemsPerPageLabel = 'Total: ' + this.totalOffers;
      this.showSpinner = false;
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
}
