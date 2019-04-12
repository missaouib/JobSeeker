import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city.model';
import { HttpHeaders } from '@angular/common/http';
import { Technology } from '../models/technology.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type':  'application/json',
  })
};

@Injectable({
  providedIn: 'root'
})

export class HttpService {

  constructor(private http: HttpClient) { }

  getCities(keyword: string): Observable <City[]> {
    return this.http.post<City[]>('http://localhost:8080/getCities', {
      technology: keyword
    } , httpOptions);
  }

  getTechnologies(keyword: string): Observable <Technology[]>{
    return this.http.post<Technology[]>('http://localhost:8080/getTechnologies', {
      city: keyword
    })
  }
}
