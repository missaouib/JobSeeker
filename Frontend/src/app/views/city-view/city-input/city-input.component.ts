import { TechnologyService } from '../../../services/technology.service';
import { HttpService } from '../../../services/http.service';
import { Technology } from '../../../models/technology.model';
import { FormControl } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-city-input',
  templateUrl: './city-input.component.html',
  styleUrls: ['./city-input.component.css']
})
export class CityInputComponent implements OnInit {

  technologyList: Technology[] = [];
  searchCity = new FormControl('');

  constructor(private httpService: HttpService, private cityService: TechnologyService) { }

  ngOnInit() {
  }

  getData(){

  }

}
