import { Country } from '../../models/country.model';
import { City } from '../../models/city.model';
import { ResultInputService } from '../../services/result-input.service';
import { Observable } from 'rxjs';
import { Category } from '../../models/category.model';
import { HttpService } from '../../services/http.service';
import { Technology } from '../../models/technology.model';
import { FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-main-input-field',
  templateUrl: './main-input-field.component.html',
  styleUrls: ['./main-input-field.component.css']
})

export class MainInputFieldComponent implements OnInit {

  isDisabled = false;
  categoryList: Category[] = [];
  technologyList: Technology[] = [];
  cityList: City[] = [];
  countryList: Country[] = [];
  searchInput = new FormControl('');
  cityInputList = ['All Cities', 'Warszawa', 'Kraków', 'Wrocław', 'Gdańsk', 'Poznań', 'Łódź', 'Lublin', 'Bydgoszcz',
                   'Białystok', 'Szczecin', 'Katowice', 'Rzeszów', 'Kielce', 'Olsztyn', 'Zielona Góra', 'Opole'];
  technologyInputList = ['All IT Jobs', 'All Jobs', 'Java', 'Javascript', 'Typescript', '.NET', 'Python', 'PHP', 'C++', 'Ruby', 'Kotlin', 'Scala', 'Groovy', 'Swift', 'Objective-C', 'Viual Basic',
                         'Spring', 'Java EE', 'Android', 'Angular', 'React', 'Vue', 'Node', 'JQuery', 'Symfony', 'Laravel', 'iOS', 'Asp.net', 'Django', 'Unity',
                          'SQL', 'Linux', 'Git', 'Docker', 'Jenkins', 'Kubernetes', 'AWS', 'Azure', 'HTML', 'Maven', 'Gradle', 'Junit', 'Jira', 'Scrum'];
  filteredInputs: Observable<string[]>

  constructor(private httpService: HttpService,
    private resultInputService: ResultInputService,
    private router: Router) { }

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
    if (this.searchInput.value !== '') {

      this.isDisabled = true;

      setTimeout(() => {
        this.isDisabled = false;
      }, 3000);

      this.resultInputService.showSpinner();

      switch(this.router.url){
        case '/': {
          this.httpService.getCities(this.searchInput.value)
            .subscribe(cityList => {
              this.cityList = cityList;
              this.resultInputService.fillCityTable(cityList);
            });
          break;
        }
        case '/world': {
          this.httpService.getCountries(this.searchInput.value)
            .subscribe(countryList => {
              this.countryList = countryList;
              this.resultInputService.fillCountryTable(countryList);
            });
          break;
        }
        case '/technology': {
          this.httpService.getTechnologies(this.searchInput.value)
            .subscribe(technologyList => {
              this.technologyList = technologyList;
              this.resultInputService.fillTechnologyTable(technologyList);
            });
          break;
        }
        case '/category': {
          this.httpService.getCategories(this.searchInput.value)
            .subscribe(categoryList => {
              this.categoryList = categoryList;
              this.resultInputService.fillCategoryTable(categoryList);
            });
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

}