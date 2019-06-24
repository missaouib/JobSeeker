import { first } from 'rxjs/operators';
import {TechnologyQuery} from '../../store/technology/technology.query';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Technology} from "../../models/technology.interfaces";
import {MatSort, MatTableDataSource} from "@angular/material";
import {ResultInputService} from "../../services/result-input.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-technology-statistics-view',
  templateUrl: './technology-statistics.component.html',
  styleUrls: ['./technology-statistics.component.css']
})
export class TechnologyStatisticsComponent implements OnInit, OnDestroy {

  totalPracuj: number[] = [];
  totalLinkedin: number[] = [];
  totalNoFluffJobs: number[] = [];
  totalJustJoin: number[] = [];
  totalOffers: number[] = [];
  showSpinner = false;
  technologyList: Technology[] = [];
  languageData = null;
  frameworkData = null;
  devOpsData = null;
  displayedColumns: string[] = ['name', 'linkedin', 'pracuj', 'noFluffJobs', 'justJoin', 'total'];
  private subscriptions: Subscription[] = [];

  @ViewChild('languageTable', { static: true }) public languageTable: MatSort;
  @ViewChild('frameworkTable', { static: true }) public frameworkTable: MatSort;
  @ViewChild('devOpsTable', { static: true }) public devOpsTable: MatSort;

  constructor(private resultInputService: ResultInputService, private technologyQuery: TechnologyQuery) {

    this.subscriptions.push(this.technologyQuery.getSpinner()
      .subscribe(spinnerStatus => {
        this.showSpinner = spinnerStatus;
      }));

    this.subscriptions.push(this.resultInputService.showSpinnerTechnology$.subscribe(() => {
      this.technologyList.length = 0;
      this.technologyQuery.updateSpinner(true);
      this.showSpinner = true;
    }));

    this.resultInputService.fillTechnologyTable$.pipe(first()).subscribe((technologies: Technology[]) => {
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

  fillTable(technologies: Technology[]){
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

    this.totalPracuj[0] = this.getTotalPracuj('language');
    this.totalPracuj[1] = this.getTotalPracuj('framework');
    this.totalPracuj[2] = this.getTotalPracuj('devops');

    this.totalNoFluffJobs[0] = this.getTotalNoFluffJobs('language');
    this.totalNoFluffJobs[1] = this.getTotalNoFluffJobs('framework');
    this.totalNoFluffJobs[2] = this.getTotalNoFluffJobs('devops');

    this.totalJustJoin[0] = this.getTotalJustJoin('language');
    this.totalJustJoin[1] = this.getTotalJustJoin('framework');
    this.totalJustJoin[2] = this.getTotalJustJoin('devops');

    this.totalOffers[0] = this.getTotalOverall('language');
    this.totalOffers[1] = this.getTotalOverall('framework');
    this.totalOffers[2] = this.getTotalOverall('devops');
  }

  getTotalLinkedin(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.linkedin).reduce((sum, current) => sum + current);
  }

  getTotalPracuj(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.pracuj).reduce((sum, current) => sum + current);
  }

  getTotalNoFluffJobs(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.noFluffJobs).reduce((sum, current) => sum + current);
  }

  getTotalJustJoin(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLowerCase() === type)
      .map(technology => technology.justJoin).reduce((sum, current) => sum + current);
  }

  getTotalOverall(type: String): number {
    return this.technologyList.filter(technology => technology.type.toLocaleLowerCase() === type)
      .map(t => t.linkedin + t.pracuj + t.noFluffJobs + t.justJoin)
      .reduce((sum, current) => sum + current);
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
