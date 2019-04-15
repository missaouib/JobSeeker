import { TechnologyService } from './../../../services/technology.service';
import { MatTableDataSource, MatSort } from '@angular/material';
import { Technology } from './../../../models/technology.model';
import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-technologies-list',
  templateUrl: './technologies-list.component.html',
  styleUrls: ['./technologies-list.component.css']
})
export class TechnologiesListComponent {

  total = 0;
  showSpinner = false;
  technologiesList: Technology[] = [];
  dataSource = new MatTableDataSource(this.technologiesList);
  displayedColumns: string[] = ['name', 'jobOffersAmount'/*, 'type', 'jobOffersAmount', 'type', 'jobOffersAmount'*/];

  @ViewChild(MatSort) sort: MatSort;


  constructor(private technologyService: TechnologyService) {

    this.technologyService.fillTable$.subscribe(technologies => {
      this.technologiesList = technologies;
      console.log(this.technologiesList);
      this.dataSource = new MatTableDataSource(this.technologiesList);
      this.dataSource.sort = this.sort;

      //this.total = this.technologiesList.map(city => city.jobAmount).reduce((sum, current) => sum + current);

      //this.showSpinner = false;
    });

  }


}
