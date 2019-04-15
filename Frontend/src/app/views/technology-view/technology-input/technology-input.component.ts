import { CityService } from '../../../services/city.service';
import { HttpService } from '../../../services/http.service';
import { City } from '../../../models/city.model';
import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-technology-input',
  templateUrl: './technology-input.component.html',
  styleUrls: ['./technology-input.component.css']
})
export class TechnologyInputComponent {

  constructor(private httpService: HttpService, private cityService: CityService) { }

  isDisabled = false;
  cityList: City[] = [];
  searchTechnology = new FormControl('');

  getData() {

    if (this.searchTechnology.value !== '') {

      this.isDisabled = true;

      setTimeout(() =>{
        this.isDisabled = false;
              }, 3000);

      this.cityService.showSpinner();
      this.httpService.getCities(this.searchTechnology.value)
        .subscribe(cityList => {
          this.cityList = cityList;
          this.cityService.fillTable(cityList);
        });
    }
  }
}