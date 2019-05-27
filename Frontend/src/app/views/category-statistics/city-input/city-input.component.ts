import { CategoryService } from './../../../services/category.service';
import { HttpService } from './../../../services/http.service';
import { FormControl } from '@angular/forms';
import { Category } from './../../../models/category.model';
import { Component } from '@angular/core';

@Component({
  selector: 'app-city-input',
  templateUrl: './city-input.component.html',
  styleUrls: ['./city-input.component.css']
})
export class CityInputComponent {

  isDisabled = false;
  categoryList: Category[] = [];
  searchCity = new FormControl('Poland');
  cityList = ['Poland', 'Warszawa', 'Krakow', 'Wroclaw', 'Gdansk', 'Poznan', 'Lodz', 'Lublin', 'Bydgoszcz', 'Bialystok', 'Szczecin', 'Katowice', 'Rzeszow', 'Kielce', 'Olsztyn', 'Zielona Gora', 'Opole'];

  constructor(private httpService: HttpService, private categoryService: CategoryService) { }

  getData() {
    if (this.searchCity.value !== '') {

      this.isDisabled = true;

      console.log('witam');

      setTimeout(() => {
        this.isDisabled = false;
      }, 3000);

      this.categoryService.showSpinner();
      this.httpService.getCategories(this.searchCity.value)
        .subscribe(categoryList => {
          this.categoryList = categoryList;
          this.categoryService.fillTable(categoryList);
        });
    }
  }
}
