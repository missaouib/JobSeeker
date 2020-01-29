import {Component} from '@angular/core';
import {HttpService} from "../../services/http.service";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-history-diagrams',
  templateUrl: './history-diagrams.component.html',
  styleUrls: ['./history-diagrams.component.css']
})
export class HistoryDiagramsComponent {

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
  }

  onRequest(event) {

    let portals = new HttpParams();
    portals = portals.set('portals', event.portals.join(', '));

    switch (event.type) {
      case 'itJobPoland': {
        this.httpService.getItJobsOffersInPolandDiagram(event.cityCountryTechnology, event.dateFrom, event.dateTo, portals)
          .subscribe(data => {
            Object.assign(this, {data});
          });
        break;
      }
      case 'itJobWorld': {
        this.httpService.getItJobsOffersInWorldDiagram(event.cityCountryTechnology, event.dateFrom, event.dateTo, portals)
          .subscribe(data => {
            Object.assign(this, {data});
          });
        break;
      }
      case 'techStatsPoland': {
        this.httpService.getTechnologyStatsInPolandDiagram(event.cityCountryTechnology, event.dateFrom, event.dateTo, portals)
          .subscribe(data => {
            Object.assign(this, {data});
          });
        break;
      }
      case 'techStatsWorld': {
        this.httpService.getTechnologyStatsInWorldDiagram(event.cityCountryTechnology, event.dateFrom, event.dateTo, portals)
          .subscribe(data => {
            Object.assign(this, {data});
          });
        break;
      }
      case 'categoryStats': {
        if (portals.get("portals") === 'indeed') {
          this.httpService.getCategoryStatsInPolandDiagramForIndeed(event.cityCountryTechnology, event.dateFrom, event.dateTo)
            .subscribe(data => {
              Object.assign(this, {data});
            });
        } else if (portals.get("portals") === 'pracuj') {
          this.httpService.getCategoryStatsInPolandDiagramForPracuj(event.cityCountryTechnology, event.dateFrom, event.dateTo)
            .subscribe(data => {
              Object.assign(this, {data});
            });
        }
        break;
      }

    }
  }

}
