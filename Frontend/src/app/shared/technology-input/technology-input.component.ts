import { Country } from './../../models/country.model';
import { ResultInputService } from './../../services/result-input.service';
import { City } from './../../models/city.model';
import { HttpService } from './../../services/http.service';
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-technology-input',
  templateUrl: './technology-input.component.html',
  styleUrls: ['./technology-input.component.css']
})
export class TechnologyInputComponent {

  constructor(private httpService: HttpService,
    private resultInputService: ResultInputService,
    private router: Router) { }

  isDisabled = false;
  cityList: City[] = [];
  countryList: Country[] = [];
  searchTechnology = new FormControl('IT category');

  getData() {

    if (this.searchTechnology.value !== '') {

      this.isDisabled = true;

      setTimeout(() => {
        this.isDisabled = false;
      }, 3000);

      if (this.router.url === '/') {
        this.resultInputService.showSpinner();
        this.httpService.getCities(this.searchTechnology.value)
        .subscribe(cityList => {
          this.cityList = cityList;
          this.resultInputService.fillCityTable(cityList);
        });
      } else {
        this.resultInputService.showSpinner();
        this.httpService.getCountries(this.searchTechnology.value)
          .subscribe(countryList => {
            this.countryList = countryList;
            this.resultInputService.fillCountryTable(countryList);
          });
      }
    }
  }
}
