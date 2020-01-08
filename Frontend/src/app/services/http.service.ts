import {Category} from '../models/category.interfaces';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {City} from '../models/city.interfaces';
import {Technology} from '../models/technology.interfaces';
import {Country} from '../models/country.interfaces';
import {environment} from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  })
};

@Injectable({providedIn: 'root'})

export class HttpService {

  constructor(private http: HttpClient) {
  }

  getCities(technologyName: string): Observable<City[]> {
    return this.http.get<City[]>(environment.backendURL + '/itJobOffersInPoland?technology=' + technologyName);
  }

  getCountries(technologyName: string): Observable<Country[]> {
    return this.http.get<Country[]>(environment.backendURL + '/itJobOffersInWorld?technology=' + technologyName);
  }

  getTechnologies(cityName: string): Observable<Technology[]> {
    return this.http.get<Technology[]>(environment.backendURL + '/technologyStatisticsInPoland?location=' + cityName);
  }

  getTechnologies2(cityName: string): Observable<Technology[]> {
    return this.http.get<Technology[]>(environment.backendURL + '/technologyStatisticsInWorld?location=' + cityName);
  }

  getCategories(cityName: string): Observable<Category[]> {
    return this.http.get<Category[]>(environment.backendURL + '/categoryStatisticsInPoland?location=' + cityName);
  }

}
