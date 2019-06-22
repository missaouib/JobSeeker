import {CategoryQuery} from '../../store/category/category.query';
import {TechnologyQuery} from '../../store/technology/technology.query';
import {CountryQuery} from '../../store/country/country.query';
import {CityQuery} from '../../store/city/city.query';
import {ResultInputService} from '../../services/result-input.service';
import {Observable, Subscription} from 'rxjs';
import {HttpService} from '../../services/http.service';
import {FormControl} from '@angular/forms';
import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {map, startWith} from 'rxjs/operators';
import {MatSnackBar} from "@angular/material";

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
  technologyInputList = ['All IT Jobs', 'All Jobs', 'Java', 'Javascript', 'Typescript', '.NET', 'Python', 'PHP', 'C++', 'Ruby', 'Kotlin', 'Scala', 'Groovy', 'Swift', 'Objective-C', 'Visual Basic',
                         'Spring', 'Java EE', 'Android', 'Angular', 'React', 'Vue', 'Node', 'JQuery', 'Symfony', 'Laravel', 'iOS', 'Asp.net', 'Django', 'Unity',
                         'SQL', 'Linux', 'Git', 'Docker', 'Jenkins', 'Kubernetes', 'AWS', 'Azure', 'HTML', 'Maven', 'Gradle', 'Junit', 'Jira', 'Scrum'];
  filteredInputs: Observable<string[]>;
  subscriptions: Subscription[] = [];

  constructor(private httpService: HttpService,
              private resultInputService: ResultInputService,
              private router: Router,
              private cityQuery: CityQuery,
              private categoryQuery: CategoryQuery,
              private technologyQuery: TechnologyQuery,
              private countryQuery: CountryQuery,
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
        case '/technology': {
          this.subscriptions.push(this.technologyQuery.getInput()
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

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    if (this.isCityOrTechnologyLabel()) {
      return this.cityInputList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    } else if (!this.isCityOrTechnologyLabel()) {
      return this.technologyInputList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
    }
  }

  getData() {
    if (this.searchInput.value !== '' && this.searchInput.value !== undefined) {

      this.isDisabled = true;

      setTimeout(() => {
        this.isDisabled = false;
      }, 3000);

      switch(this.router.url){
        case '/': {
          this.resultInputService.showSpinnerCity();
          this.httpService.getCities(this.searchInput.value)
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
          this.httpService.getCountries(this.searchInput.value)
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
        case '/technology': {
          this.resultInputService.showSpinnerTechnology();
          this.httpService.getTechnologies(this.searchInput.value)
            .subscribe(technologies => {
              this.technologyQuery.updateSpinner(false);
              this.resultInputService.fillTechnologyTable(technologies);
            }, () => {
              this.technologyQuery.updateSpinner(false);
              this.openSnackBar();
            });
          this.technologyQuery.updateMainInput(this.searchInput.value);
          break;
        }
        case '/category': {
          this.resultInputService.showSpinnerCategory();
          this.httpService.getCategories(this.searchInput.value)
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

  isCityOrTechnologyLabel(): Boolean {
    if (this.router.url === '/technology' || this.router.url === '/category') {
      return true;
    } else if (this.router.url === '/' || this.router.url === '/world') {
      return false;
    }
    return null;
  }

  openSnackBar() {
    this.snackBar.open('Server is not responding, please try again later.', 'Close', {});
  }

  ngOnDestroy() {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
