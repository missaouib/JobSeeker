import { TechnologyService } from '../../../services/technology.service';
import { HttpService } from '../../../services/http.service';
import { Technology } from '../../../models/technology.model';
import { FormControl } from '@angular/forms';
import { Component } from '@angular/core';

@Component({
  selector: 'app-city-input',
  templateUrl: './city-input.component.html',
  styleUrls: ['./city-input.component.css']
})
export class CityInputComponent {

  isDisabled = false;
  technologyList: Technology[] = [];
  searchCity = new FormControl('Poland');
  cityList = ['Poland', 'Warszawa', 'Kraków', 'Wrocław', 'Gdańsk', 'Poznań', 'Łódź', 'Lublin',
    'Bydgoszcz', 'Białystok', 'Szczecin', 'Katowice', 'Rzeszów', 'Kielce', 'Olsztyn', 'Zielona Góra', 'Opole'];

  constructor(private httpService: HttpService, private technologyService: TechnologyService) { }

  getData(){
    if (this.searchCity.value !== '') {

      this.isDisabled = true;

      setTimeout(() =>{
        this.isDisabled = false;
              }, 3000);

      this.technologyService.showSpinner();
      this.httpService.getTechnologies(this.searchCity.value)
      .subscribe(technologyList => {
        this.technologyList = technologyList;
        this.technologyService.fillTable(technologyList);
      });
    }
  }

}
