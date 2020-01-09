import {TechnologyStatisticsInPolandQuery} from '../../store/technology-statistics-in-poland/technologyStatisticsInPoland.query';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {TechnologyStatistics} from "../../models/technologyStatistics.interfaces";
import {MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-technology-statistics-view',
  templateUrl: './technology-statistics-in-world.component.html',
  styleUrls: ['./technology-statistics-in-world.component.css']
})
export class TechnologyStatisticsInWorldComponent implements OnInit, OnDestroy {

  totalLinkedin: number[] = [];
  totalIndeed: number[] = [];
  totalOffers: number[] = [];
  showSpinner = false;
  technologyList: TechnologyStatistics[] = [];
  languageData = null;
  frameworkData = null;
  devOpsData = null;
  displayedColumns: string[] = ['name', 'linkedin', 'indeed', 'total'];
  @ViewChild('languageTable', {static: true}) public languageTable: MatSort;
  @ViewChild('frameworkTable', {static: true}) public frameworkTable: MatSort;
  @ViewChild('devOpsTable', {static: true}) public devOpsTable: MatSort;
  private subscriptions: Subscription[] = [];
  private subscription: Subscription;

  constructor(private resultInputService: ResultInputService, private technologyQuery: TechnologyStatisticsInPolandQuery) {

    this.subscriptions.push(this.technologyQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerTechnology$.subscribe(() => {
      this.technologyList.length = 0;
      this.technologyQuery.updateSpinner(true);
      this.showSpinner = true;
    }));

    this.subscription = this.resultInputService.fillTechnologyTable$.subscribe((technologies: TechnologyStatistics[]) => {
      this.technologyList = technologies;
      this.technologyList.filter(x => x.name.toLowerCase() === 'html').map(x => x.name = 'HTML/CSS');
      this.fillTable(this.technologyList);

      this.technologyQuery.updateSpinner(false);
      this.showSpinner = false;

      this.technologyQuery.updateTechnologies(this.technologyList);
    });
  }

  ngOnInit() {
    this.subscriptions.push(this.technologyQuery.getTechnologies()
      .subscribe(technologies => {
        if (technologies.length !== 0 && !this.showSpinner) {
          this.fillTable(technologies);
        }
      }));
  }

  fillTable(technologies: TechnologyStatistics[]) {
    this.technologyList = technologies;

    this.languageData = new MatTableDataSource(this.technologyList.filter(technology => technology.type.toLowerCase() === 'language'));
    this.frameworkData = new MatTableDataSource(this.technologyList.filter(technology => technology.type.toLowerCase() === 'framework'));
    this.devOpsData = new MatTableDataSource(this.technologyList.filter(technology => technology.type.toLowerCase() === 'devops'));

    this.languageData.sort = this.languageTable;
    this.frameworkData.sort = this.frameworkTable;
    this.devOpsData.sort = this.devOpsTable;

    this.languageTable.disableClear = true;
    this.frameworkTable.disableClear = true;
    this.devOpsTable.disableClear = true;

    this.totalLinkedin[0] = this.getTotalLinkedin('language');
    this.totalLinkedin[1] = this.getTotalLinkedin('framework');
    this.totalLinkedin[2] = this.getTotalLinkedin('devops');

    this.totalIndeed[0] = this.getTotalIndeed('language');
    this.totalIndeed[1] = this.getTotalIndeed('framework');
    this.totalIndeed[2] = this.getTotalIndeed('devops');

    this.totalOffers[0] = this.getTotalOverall('language');
    this.totalOffers[1] = this.getTotalOverall('framework');
    this.totalOffers[2] = this.getTotalOverall('devops');
  }

  getTotalLinkedin(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.linkedin).reduce((sum, current) => sum + current);
  }

  getTotalIndeed(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.indeed).reduce((sum, current) => sum + current);
  }

  getTotalOverall(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLocaleLowerCase() === type)
      .map(t => t.linkedin + t.indeed)
      .reduce((sum, current) => sum + current);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());

    if (!this.showSpinner) {
      this.subscription.unsubscribe();
    }
  }

}
