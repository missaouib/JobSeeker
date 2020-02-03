import {
  AfterViewChecked,
  AfterViewInit, ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Output,
  QueryList,
  ViewChild
} from '@angular/core';
import {MatSelect} from "@angular/material/select";
import {MatOption} from "@angular/material/core";
import {DatePipe} from "@angular/common";
import {InputListInterface} from "../../../shared/input-list.interface";

@Component({
  selector: 'app-choose-diagram',
  templateUrl: './choose-diagram.component.html',
  styleUrls: ['./choose-diagram.component.css']
})
export class ChooseDiagramComponent implements AfterViewInit {

  @ViewChild('jobPortal', {static: false}) jobPortals: MatSelect;
  @ViewChild('singlePortal', {static: false}) singlePortal: MatSelect;
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

  inputList = new InputListInterface();
  cityInputList = this.inputList.cityInputList;
  technologyInputList = this.inputList.technologyInputList;
  countryInputList = this.inputList.countryInputList;

  constructor(private changeDetector : ChangeDetectorRef) {
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
    this.selectAllCheckbox();
  }

  selectAllCheckbox() {
    if(this.selectedType != 'categoryStats') {
      this.jobPortals.options.forEach((item: MatOption) => item.select());
      this.jobPortals.close();
    }
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

    if (event === 'itJobPoland' || event === 'techStatsPoland') {
      this.portals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
      this.selectedPortals = ['linkedin', 'indeed', 'pracuj', 'noFluffJobs', 'justJoinIt'];
    } else if (event === 'itJobWorld' || event === 'techStatsWorld') {
      this.portals = ['linkedin', 'indeed'];
      this.selectedPortals = ['linkedin', 'indeed'];
    } else if (event === 'categoryStats') {
      this.portals = ['indeed', 'pracuj'];
      this.selectedPortals = ['indeed'];
    }

    this.selectedType = event;
    this.changeDetector.detectChanges();
    this.selectAllCheckbox();
    this.emitEvent();
  }


  OnCityCountryTechnologyChange(eventValue) {
    this.selectedCityCountryTechnology = eventValue;
    this.emitEvent();
  }

  OnPortalChange(eventValue) {
    if(this.selectedType === 'categoryStats'){
      this.selectedPortals.pop();
      this.selectedPortals.push(eventValue);
    } else {
      this.selectedPortals = eventValue;
    }
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

