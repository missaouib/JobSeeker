import {AfterViewInit, Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {FormControl} from "@angular/forms";
import {MatSelect} from "@angular/material/select";
import {MatOption} from "@angular/material/core";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-choose-diagram',
  templateUrl: './choose-diagram.component.html',
  styleUrls: ['./choose-diagram.component.css']
})
export class ChooseDiagramComponent implements AfterViewInit {

  @ViewChild('jobPortal', {static: false}) jobPortals: MatSelect;
  @Output() eventEmitter = new EventEmitter<Object>();

  selectedType: string;
  selectedCityCountryTechnology: string;
  selectedPortals: string[] = [];
  selectedDateFrom: string;
  selectedDateTo: string;

  inputElements = [];
  fieldType: string;
  labelName: string;
  portals: string[] = [];
  minDate = new Date(2020, 0, 1);
  maxDate = new Date();

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

  constructor() {
    this.selectedType = 'itJobPoland';
    this.selectedCityCountryTechnology = 'All Technologies';
    this.selectedPortals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
    this.portals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
    this.selectedDateFrom = new DatePipe('en-US').transform(this.minDate, 'yyyy-MM-dd');
    this.selectedDateTo = new DatePipe('en-US').transform(this.maxDate, 'yyyy-MM-dd');
    this.inputElements = this.technologyInputList;
    this.fieldType = 'All Technologies';
    this.labelName = 'Technology';
  }

  ngAfterViewInit() {
    this.jobPortals.options.forEach((item: MatOption) => item.select());
    this.jobPortals.close();
  }

  emitEvent() {
    this.eventEmitter.emit({
      type: this.selectedType,
      cityCountryTechnology: this.selectedCityCountryTechnology,
      portals: this.selectedPortals,
      dateFrom: this.selectedDateFrom,
      dateTo: this.selectedDateTo
    })
  }

  OnTypeChange(event) {
    if (event === 'itJobPoland' || event === 'itJobWorld') {
      this.fieldType = 'All Technologies';
      this.selectedCityCountryTechnology = 'All Technologies';
      this.labelName = 'Technology';
      this.inputElements = this.technologyInputList;
    } else if (event === 'techStatsPoland' || event === 'categoryStats') {
      this.fieldType = 'All Cities';
      this.selectedCityCountryTechnology = 'All Cities';
      this.labelName = 'City';
      this.inputElements = this.cityInputList;
    } else if (event === 'techStatsWorld') {
      this.fieldType = 'All Countries';
      this.selectedCityCountryTechnology = 'All Countries';
      this.labelName = 'Country';
      this.inputElements = this.countryInputList;
    }

    if (event === 'itJobPoland' || event === 'techStatsPoland'){
      this.portals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
      this.selectedPortals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
    } else if (event === 'itJobWorld' || event === 'techStatsWorld') {
      this.portals = ['linkedin', 'indeed'];
      this.selectedPortals = ['linkedin', 'indeed'];
    } else if (event === 'categoryStats') {
      this.portals = ['indeed', 'pracuj'];
      this.selectedPortals = ['indeed', 'pracuj'];
    }

    this.selectedType = event;
    this.emitEvent();
  }


  OnCityCountryTechnologyChange(eventValue) {
    this.selectedCityCountryTechnology = eventValue;
    this.emitEvent();
  }

  OnPortalChange(eventValue) {
    this.selectedPortals = eventValue;
    this.emitEvent();
  }

  OnDateFromChange(eventValue) {
    this.selectedDateFrom = new DatePipe('en-US').transform(eventValue, 'yyyy-MM-dd');
    this.emitEvent();
  }

  OnDateToChange(eventValue) {
    this.selectedDateTo = new DatePipe('en-US').transform(eventValue, 'yyyy-MM-dd');
    this.emitEvent();
  }

}

