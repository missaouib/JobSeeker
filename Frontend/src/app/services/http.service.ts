import { Category } from '../models/category.interfaces';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city.interfaces';
import { HttpHeaders } from '@angular/common/http';
import { Technology } from '../models/technology.interfaces';
import { Country } from '../models/country.interfaces';
import {environment} from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({providedIn: 'root'})

export class HttpService {

  constructor(private http: HttpClient) { }

  getCities(technologyName: string): Observable <City[]> {
    return this.http.post<City[]>(environment.backendURL + '/itJobOffersInPoland', {
      technology: technologyName
    }, httpOptions);
  }

  getCountries(technologyName: string): Observable <Country[]> {
    return this.http.post<Country[]>(environment.backendURL + '/itJobOffersInWorld', {
      technology: technologyName
    }, httpOptions);
  }

  getTechnologies(cityName: string): Observable <Technology[]>{
    return this.http.post<Technology[]>(environment.backendURL + '/technologyStatistics', {
      city: cityName
    }, httpOptions)
  }

  getCategories(cityName: string): Observable <Category[]>{
    return this.http.post<Category[]>(environment.backendURL + '/categoryStatistics', {
      city: cityName
    }, httpOptions)
  }
}
