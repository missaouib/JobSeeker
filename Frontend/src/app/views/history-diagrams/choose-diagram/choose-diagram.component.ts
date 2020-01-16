import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../../services/http.service";
import {FormControl} from "@angular/forms";

export interface Food {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-choose-diagram',
  templateUrl: './choose-diagram.component.html',
  styleUrls: ['./choose-diagram.component.css']
})
export class ChooseDiagramComponent implements OnInit {

  minDate = new Date(2000, 0, 1);
  maxDate = new Date(2020, 0, 1);

  toppings = new FormControl();
  toppingList: string[] = ['Extra cheese', 'Mushroom', 'Onion', 'Pepperoni', 'Sausage', 'Tomato'];

  foods: Food[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];

  constructor(private httpService: HttpService) {

  }

  ngOnInit() {
  }

}
