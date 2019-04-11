import { CityService } from './../services/city.service';
import { HttpService } from './../services/http.service';
import { Component } from '@angular/core';
import { City } from '../models/city.model';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-input-technology',
  templateUrl: './input-technology.component.html',
  styleUrls: ['./input-technology.component.css']
})
export class InputTechnologyComponent {

  constructor(private httpService: HttpService, private cityService: CityService) { }

  cityList: City[] = [];
  searchTechnology = new FormControl('');

  getData() {

    if (this.searchTechnology.value !== '') {
      this.httpService.getCities(this.searchTechnology.value)
        .subscribe(cityList => {
          this.cityList = cityList;
          console.log(cityList);
          this.cityService.fillTable(cityList);
        });
    }
  }
}
