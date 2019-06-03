import { Observable } from 'rxjs';
import { Category } from './../../models/category.model';
import { CategoryService } from './../../services/category.service';
import { TechnologyService } from './../../services/technology.service';
import { HttpService } from './../../services/http.service';
import { Technology } from './../../models/technology.model';
import { FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-city-input',
  templateUrl: './city-input.component.html',
  styleUrls: ['./city-input.component.css']
})

export class CityInputComponent implements OnInit {

  isDisabled = false;
  categoryList: Category[] = [];
  technologyList: Technology[] = [];
  searchCity = new FormControl('');
  cityList = ['Poland', 'Warszawa', 'Kraków', 'Wrocław', 'Gdańsk', 'Poznań', 'Łódź', 'Lublin',
    'Bydgoszcz', 'Białystok', 'Szczecin', 'Katowice', 'Rzeszów', 'Kielce', 'Olsztyn', 'Zielona Góra', 'Opole'];
  filteredCities: Observable<string[]>

  constructor(private httpService: HttpService,
    private technologyService: TechnologyService,
    private categoryService: CategoryService,
    private router: Router) { }

  ngOnInit(){
    this.filteredCities = this.searchCity.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.cityList.filter(option => option.toLowerCase().indexOf(filterValue) === 0);
  }

  getData() {
    if (this.searchCity.value !== '') {

      this.isDisabled = true;

      setTimeout(() => {
        this.isDisabled = false;
      }, 3000);

      if(this.router.url === '/technology'){
        this.technologyService.showSpinner();
        this.httpService.getTechnologies(this.searchCity.value)
          .subscribe(technologyList => {
            this.technologyList = technologyList;
            this.technologyService.fillTable(technologyList);
          });
      } else {
        this.categoryService.showSpinner();
        this.httpService.getCategories(this.searchCity.value)
          .subscribe(categoryList => {
            this.categoryList = categoryList;
            this.categoryService.fillTable(categoryList);
          });
      }
    }
  }

}
