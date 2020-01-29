import {CategoryStatisticsQuery} from '../../store/category-statistics/categoryStatistics.query';
import {ItJobOffersInWorldQuery} from '../../store/it-job-offers-in-world/itJobOffersInWorld.query';
import {ItJobOffersInPolandQuery} from '../../store/it-job-offers-in-poland/itJobOffersInPoland.query';
import {ResultInputService} from '../../services/result-input.service';
import {Observable, Subscription} from 'rxjs';
import {HttpService} from '../../services/http.service';
import {FormControl} from '@angular/forms';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {map, startWith} from 'rxjs/operators';
import {MatSnackBar} from "@angular/material";
import {TechnologyStatisticsInPolandQuery} from "../../store/technology-statistics-in-poland/technologyStatisticsInPoland.query";
import {TechnologyStatisticsInWorldQuery} from "../../store/technology-statistics-in-world/technologyStatisticsInWorld.query";

@Component({
  selector: 'app-main-input-field',
  templateUrl: './main-input-field.component.html',
  styleUrls: ['./main-input-field.component.css']
})

export class MainInputFieldComponent implements OnInit, OnDestroy {

  isDisabled = false;
  searchInput = new FormControl('');
  cityInputList = ['All Cities', 'Warszawa', 'Kraków', 'Wrocław', 'Gdańsk', 'Poznań', 'Łódź', 'Lublin', 'Bydgoszcz', 'Białystok',
    'Szczecin', 'Katowice', 'Rzeszów', 'Kielce', 'Olsztyn', 'Zielona Góra', 'Opole', 'Toruń', 'Gorzów Wielkopolski',];
  countryInputList = ['All Countries', 'Poland', 'United States', 'United Kingdom', 'Germany', 'France', 'Singapore', 'Hong Kong', 'Bahrain',
    'Taiwan', 'Korea', 'Netherlands', 'Israel', 'India', 'Belgium', 'Philippines', 'Japan', 'Vietnam', 'Pakistan', 'Kuwait',
    'Luxembourg', 'Qatar', 'Switzerland', 'Italy', 'China', 'Indonesia', 'Czech Republic', 'Denmark', 'Thailand',
    'United Arab Emirates', 'Portugal', 'Austria', 'Turkey', 'Hungary', 'Spain', 'Romania', 'Greece', 'Ireland',
    'Mexico', 'South Africa', 'Colombia', 'Venezuela', 'Peru', 'Brazil', 'Chile', 'Sweden', 'New Zealand', 'Norway',
    'Finland', 'Argentina', 'Saudi Arabia', 'Oman', 'Russia', 'Canada', 'Australia'];
  technologyInputList = ['All Technologies', 'Java', 'Javascript', 'Typescript', 'C#', 'Python', 'PHP', 'C++', 'Ruby', 'Kotlin', 'Scala', 'Rust', 'Swift', 'Golang', 'Visual Basic',
    'Spring', 'Selenium', 'Android', 'Angular', 'React', 'Vue', 'Node', '.NET', 'Symfony', 'Laravel', 'iOS', 'Asp.net', 'Django', 'Unity',
    'Linux', 'Bash', 'Docker', 'Jenkins', 'Kubernetes', 'AWS', 'Azure', 'Google Cloud', 'Ansible', 'Terraform', 'TeamCity', 'Circle CI', 'ELK stack', 'Nginx'];
  filteredInputs: Observable<string[]>;
  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpService,
              private resultInputService: ResultInputService,
              private router: Router,
              private cityQuery: ItJobOffersInPolandQuery,
              private categoryQuery: CategoryStatisticsQuery,
              private technologyPolandQuery: TechnologyStatisticsInPolandQuery,
              private technologyWorldQuery: TechnologyStatisticsInWorldQuery,
              private countryQuery: ItJobOffersInWorldQuery,
              private snackBar: MatSnackBar) {

    switch (this.router.url) {
      case '/': {
        this.subscriptions.push(this.cityQuery.getInput()
          .subscribe(input => {
            this.searchInput.setValue(input);
          }));
        break;
      }
      case '/world': {
        this.subscriptions.push(this.countryQuery.getInput()
          .subscribe(input => {
            this.searchInput.setValue(input);
          }));
        break;
      }
      case '/technology-poland': {
        this.subscriptions.push(this.technologyPolandQuery.getInput()
          .subscribe(input => {
            this.searchInput.setValue(input);
          }));
        break;
      }
      case '/technology-world': {
        this.subscriptions.push(this.technologyWorldQuery.getInput()
          .subscribe(input => {
            this.searchInput.setValue(input);
          }));
        break;
      }
      case '/category': {
        this.subscriptions.push(this.categoryQuery.getInput()
          .subscribe(input => {
            this.searchInput.setValue(input);
          }));
        break;
      }
    }
  }

  ngOnInit() {
    this.filteredInputs = this.searchInput.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  getData() {
    if (this.searchInput.value !== '' && this.searchInput.value !== undefined) {

      this.isDisabled = true;

      setTimeout(() => {
        this.isDisabled = false;
      }, 1000);

      switch (this.router.url) {
        case '/': {
          this.resultInputService.showSpinnerCity();
          this.httpService.getItJobsOffersInPoland(this.searchInput.value)
            .subscribe(cities => {
              this.cityQuery.updateSpinner(false);
              this.resultInputService.fillCityTable(cities);
            }, () => {
              this.cityQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.cityQuery.updateMainInput(this.searchInput.value);
          break;
        }
        case '/world': {
          this.resultInputService.showSpinnerCountry();
          this.httpService.getItJobsOffersInWorld(this.searchInput.value)
            .subscribe(countries => {
              this.countryQuery.updateSpinner(false);
              this.resultInputService.fillCountryTable(countries);
            }, () => {
              this.countryQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.countryQuery.updateMainInput(this.searchInput.value);
          break;
        }
        case '/technology-poland': {
          this.resultInputService.showSpinnerTechnology();
          this.httpService.getTechnologyStatsInPoland(this.searchInput.value)
            .subscribe(technologies => {
              this.technologyPolandQuery.updateSpinner(false);
              this.resultInputService.fillTechnologyTable(technologies);
            }, () => {
              this.technologyPolandQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.technologyPolandQuery.updateMainInput(this.searchInput.value);
          break;
        }
        case '/technology-world': {
          this.resultInputService.showSpinnerTechnology();
          this.httpService.getTechnologyStatsInWorld(this.searchInput.value)
            .subscribe(technologies => {
              this.technologyWorldQuery.updateSpinner(false);
              this.resultInputService.fillTechnologyTable(technologies);
            }, () => {
              this.technologyWorldQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.technologyWorldQuery.updateMainInput(this.searchInput.value);
          break;
        }
        case '/category': {
          this.resultInputService.showSpinnerCategory();
          this.httpService.getCategoryStatsInPoland(this.searchInput.value)
            .subscribe(categories => {
              this.categoryQuery.updateSpinner(false);
              this.resultInputService.fillCategoryTable(categories);
            }, () => {
              this.categoryQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.categoryQuery.updateMainInput(this.searchInput.value);
          break;
        }
      }
    }
  }

  getInputGhostLabel(): String {
    if (this.router.url === '/technology-poland' || this.router.url === '/category') {
      return 'City';
    } else if (this.router.url === '/technology-world') {
      return 'Country';
    } else if (this.router.url === '/' || this.router.url === '/world') {
      return 'Technology';
    }
    return null;
  }

  openSnackBar() {
    this.snackBar.open('Server is not responding, please try again later.', 'Close', {
      duration: 10000,
    });
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    if (this.getInputGhostLabel() === 'City') {
      return this.cityInputList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    } else if (this.getInputGhostLabel() === 'Technology') {
      return this.technologyInputList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    } else if (this.getInputGhostLabel() === 'Country') {
      return this.countryInputList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    }
  }

}
