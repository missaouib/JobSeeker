import { TechnologyService } from '../../../services/technology.service';
import { MatTableDataSource, MatSort } from '@angular/material';
import { Technology } from '../../../models/technology.model';
import { Component, ViewChild } from '@angular/core';

@Component({
  selector: 'app-technologies-list',
  templateUrl: './technologies-list.component.html',
  styleUrls: ['./technologies-list.component.css']
})
export class TechnologiesListComponent {

  totalPracuj: number[] = [];
  totalLinkedin: number[] = [];
  totalNoFluffJobs: number[] = [];
  totalJustJoin: number[] = [];
  totalOffers: number[] = [];
  showSpinner = false;
  technologiesList: Technology[] = [];
  languageData = null;
  frameworkData = null;
  devOpsData = null;
  displayedColumns: string[] = ['name', 'linkedinOffers', 'pracujOffers', 'noFluffJobsOffers', 'justJoinOffer', 'totalJobOffers'];

  @ViewChild('languageTable') public languageTable: MatSort;
  @ViewChild('frameworkTable') public frameworkTable: MatSort;
  @ViewChild('devOpsTable') public devOpsTable: MatSort;

  constructor(private technologyService: TechnologyService) {
    this.technologyService.showSpinner$.subscribe(() => {
      this.technologiesList.length = 0;
      this.showSpinner = true;
    });

    this.technologyService.fillTable$.subscribe(technologies => {
      this.technologiesList = technologies;

      this.technologiesList.filter(x => x.name.toLowerCase() === 'html').map(x => x.name = 'HTML/CSS');

      this.languageData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language'));
      this.frameworkData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework'));
      this.devOpsData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops'));

      this.languageData.sort = this.languageTable;
      this.frameworkData.sort = this.frameworkTable;
      this.devOpsData.sort = this.devOpsTable;

      this.languageTable.disableClear = true;
      this.frameworkTable.disableClear = true;
      this.devOpsTable.disableClear = true;

      this.totalLinkedin[0] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language')
        .map(technology => technology.linkedinOffers).reduce((sum, current) => sum + current);
      this.totalLinkedin[1] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework')
        .map(technology => technology.linkedinOffers).reduce((sum, current) => sum + current);
      this.totalLinkedin[2] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops')
        .map(technology => technology.linkedinOffers).reduce((sum, current) => sum + current);

      this.totalPracuj[0] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language')
        .map(technology => technology.pracujOffers).reduce((sum, current) => sum + current);
      this.totalPracuj[1] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework')
        .map(technology => technology.pracujOffers).reduce((sum, current) => sum + current);
      this.totalPracuj[2] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops')
        .map(technology => technology.pracujOffers).reduce((sum, current) => sum + current);

      this.totalNoFluffJobs[0] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language')
        .map(technology => technology.noFluffJobsOffers).reduce((sum, current) => sum + current);
      this.totalNoFluffJobs[1] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework')
        .map(technology => technology.noFluffJobsOffers).reduce((sum, current) => sum + current);
      this.totalNoFluffJobs[2] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops')
        .map(technology => technology.noFluffJobsOffers).reduce((sum, current) => sum + current);

      this.totalJustJoin[0] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language')
        .map(technology => technology.justJoinOffers).reduce((sum, current) => sum + current);
      this.totalJustJoin[1] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework')
        .map(technology => technology.justJoinOffers).reduce((sum, current) => sum + current);
      this.totalJustJoin[2] = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops')
        .map(technology => technology.justJoinOffers).reduce((sum, current) => sum + current);

      this.totalOffers[0] = this.technologiesList.filter(technology => technology.type.toLocaleLowerCase() === 'language')
        .map(t => t.linkedinOffers + t.pracujOffers + t.noFluffJobsOffers + t.justJoinOffers)
        .reduce((sum, current) => sum + current);

      this.totalOffers[1] = this.technologiesList.filter(technology => technology.type.toLocaleLowerCase() === 'framework')
        .map(t => t.linkedinOffers + t.pracujOffers + t.noFluffJobsOffers + t.justJoinOffers)
        .reduce((sum, current) => sum + current);

      this.totalOffers[2] = this.technologiesList.filter(technology => technology.type.toLocaleLowerCase() === 'devops')
        .map(t => t.linkedinOffers + t.pracujOffers + t.noFluffJobsOffers + t.justJoinOffers)
        .reduce((sum, current) => sum + current);

      this.showSpinner = false;
    });

  }
}

