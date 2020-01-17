import {CategoryStatistics} from '../models/categoryStatistics.interfaces';
import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
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
    technologyName = technologyName.split('+').join( '%2B');
    return this.http.get<JobOffer[]>(environment.backendURL + '/itJobOffersInPoland?technology=' + technologyName);
  }

  getItJobsOffersInWorld(technologyName: string): Observable<JobOffer[]> {
    technologyName = technologyName.split('+').join( '%2B');
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

  getItJobsOffersInPolandDiagram(technologyName: string, dateFrom: string, dateTo: string, portals: HttpParams): Observable<Diagram[]> {
    technologyName = technologyName.split('+').join( '%2B');
    return this.http.get<Diagram[]>(environment.backendURL +
      '/ItJobsOfferInPolandDiagram?technology=' + technologyName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo
      , {params: portals});
  }

  getItJobsOffersInWorldDiagram(technologyName: string, dateFrom: string, dateTo: string, portals: HttpParams): Observable<Diagram[]> {
    technologyName = technologyName.split('+').join( '%2B');
    return this.http.get<Diagram[]>(environment.backendURL +
      '/itJobOffersInWorldDiagram?technology=' + technologyName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo
    , {params: portals});
  }

  getTechnologyStatsInPolandDiagram(cityName: string, dateFrom: string, dateTo: string, portals: HttpParams): Observable<Diagram[]> {
    return this.http.get<Diagram[]>(environment.backendURL +
      '/technologyStatisticsInPolandDiagram?location=' + cityName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo
      , {params: portals});
  }

  getTechnologyStatsInWorldDiagram(countryName: string, dateFrom: string, dateTo: string, portals: HttpParams): Observable<Diagram[]> {
    return this.http.get<Diagram[]>(environment.backendURL +
      '/technologyStatisticsInWorldDiagram?location=' + countryName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo
      , {params: portals});
  }

  getCategoryStatsInPolandDiagram(cityName: string, dateFrom: string, dateTo: string, portals: HttpParams): Observable<Diagram[]> {
    return this.http.get<Diagram[]>(environment.backendURL +
      '/categoryStatisticsInPolandDiagram?location=' + cityName + '&dateFrom=' + dateFrom + '&dateTo=' + dateTo
      , {params: portals});
  }

}
