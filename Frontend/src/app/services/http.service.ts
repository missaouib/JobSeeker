import {CategoryStatistics} from '../models/categoryStatistics.interfaces';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TechnologyStatistics} from '../models/technologyStatistics.interfaces';
import {environment} from "../../environments/environment";
import {JobOffer} from "../models/jobOffer.interfaces";
import {Diagram} from "../models/diagram.interfaces";

@Injectable({providedIn: 'root'})

export class HttpService {

  constructor(private http: HttpClient) {
  }

  getItJobsOffersInPoland(technologyName: string): Observable<JobOffer[]> {
    return this.http.get<JobOffer[]>(environment.backendURL + '/itJobOffersInPoland?technology=' + technologyName);
  }

  getItJobsOffersInWorld(technologyName: string): Observable<JobOffer[]> {
    return this.http.get<JobOffer[]>(environment.backendURL + '/itJobOffersInWorld?technology=' + technologyName);
  }

  getTechnologyStatsInPoland(cityName: string): Observable<TechnologyStatistics[]> {
    return this.http.get<TechnologyStatistics[]>(environment.backendURL + '/technologyStatisticsInPoland?location=' + cityName);
  }

  getTechnologyStatsInWorld(cityName: string): Observable<TechnologyStatistics[]> {
    return this.http.get<TechnologyStatistics[]>(environment.backendURL + '/technologyStatisticsInWorld?location=' + cityName);
  }

  getCategoryStatsInPoland(cityName: string): Observable<CategoryStatistics[]> {
    return this.http.get<CategoryStatistics[]>(environment.backendURL + '/categoryStatisticsInPoland?location=' + cityName);
  }

  getItJobsOffersInPolandDiagram(technologyName: string, dateFrom: string, dateTo: string): Observable<Diagram[]> {
    return this.http.get<Diagram[]>(environment.backendURL +
      '/ItJobsOfferInPolandDiagram?technology=' + technologyName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo);
  }

}
