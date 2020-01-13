import {Component, OnInit} from '@angular/core';
import { NgxChartsModule } from "@swimlane/ngx-charts";

@Component({
  selector: 'app-history-diagrams',
  templateUrl: './history-diagrams.component.html',
  styleUrls: ['./history-diagrams.component.css']
})
export class HistoryDiagramsComponent implements OnInit {

  single: any[];
  multi: any[];

  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = "Country";
  showYAxisLabel = true;
  yAxisLabel = "Population";

  colorScheme = {
    domain: ["#5AA454", "#A10A28", "#C7B42C", "#AAAAAA"]
  };

  constructor() {
    // Object.assign(this, { single });
  }

  ngOnInit() {
  }

}
