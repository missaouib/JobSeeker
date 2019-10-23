import { Category } from '../models/category.interfaces';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city.interfaces';
import { HttpHeaders } from '@angular/common/http';
import { Technology } from '../models/technology.interfaces';
import { Country } from '../models/country.interfaces';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({providedIn: 'root'})

export class HttpService {

  constructor(private http: HttpClient) { }

  getCities(technologyName: string): Observable <City[]> {
    return this.http.post<City[]>('http://localhost:8080/itJobOffersInPoland', {
      technology: technologyName
    }, httpOptions);
  }

  getCountries(technologyName: string): Observable <Country[]> {
    return this.http.post<Country[]>('http://localhost:8080/itJobOffersInWorld', {
      technology: technologyName
    }, httpOptions);
  }

  getTechnologies(cityName: string): Observable <Technology[]>{
    return this.http.post<Technology[]>('http://localhost:8080/technologyStatistics', {
      city: cityName
    }, httpOptions)
  }

  getCategories(cityName: string): Observable <Category[]>{
    return this.http.post<Category[]>('http://localhost:8080/categoryStatistics', {
      city: cityName
    }, httpOptions)
  }
}
