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

  showSpinner = false;
  totalLanguage = 0;
  totalFramework = 0;
  totalDevOps = 0;
  technologiesList: Technology[] = [];
  languageData = null;
  frameworkData = null;
  devOpsData = null;
  displayedColumns: string[] = ['name', 'pracujplJobOffers', 'linkedinJobOffers', 'nofluffjobsJobOffers'];

  @ViewChild(MatSort) sort: MatSort;

  constructor(private technologyService: TechnologyService) {

    console.log(this.technologiesList.length);
    this.technologyService.showSpinner$.subscribe(() => {
      this.technologiesList.length = 0;
      this.showSpinner = true;
    });

    this.technologyService.fillTable$.subscribe(technologies => {
      this.technologiesList = technologies;

      this.technologiesList.filter(x => x.name.toLowerCase() === 'html').map(x => x.name = 'HTML/CSS');

      this.languageData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language'));
      this.languageData.sort = this.sort;
      this.totalLanguage = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'language')
        .map(technology => technology.pracujplJobOffers).reduce((sum, current) => sum + current);

      this.frameworkData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework'));
      this.frameworkData.sort = this.sort;
      this.totalFramework = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'framework')
        .map(technology => technology.pracujplJobOffers).reduce((sum, current) => sum + current);

      this.devOpsData = new MatTableDataSource(this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops'));
      this.devOpsData.sort = this.sort;
      this.totalDevOps = this.technologiesList.filter(technology => technology.type.toLowerCase() === 'devops')
        .map(technology => technology.pracujplJobOffers).reduce((sum, current) => sum + current);

      this.showSpinner = false;
    });

  }


}