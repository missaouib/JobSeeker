import {Component, OnInit} from '@angular/core';
import {HttpService} from "../../services/http.service";

@Component({
  selector: 'app-history-diagrams',
  templateUrl: './history-diagrams.component.html',
  styleUrls: ['./history-diagrams.component.css']
})
export class HistoryDiagramsComponent implements OnInit {

  view: any[] = [1200, 700];
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = "Date";
  showYAxisLabel = true;
  yAxisLabel = "Job Offers";

  colorScheme = {
    domain: ["#238097", "#3eb547", "#f99177", "#1ec4a6", "#fecf92", "#988254", "#0d6440", "#9182a6", "#C7B42C", "#aca990",
      "#538576", "#8ad4c5", "#e3cd94", "#00a6aa", "#fa5c9c", "#6ea5c3", "#a50842", "#6d72a0", "#df6270", "#ab5b46"]
  };

  constructor(private httpService: HttpService) {

    this.httpService.getItJobsOffersInPolandDiagram('java', '2020-01-01', '2020-01-15')
      .subscribe(data => {
        Object.assign(this, { data });
      });
  }

  ngOnInit() {
  }

}
