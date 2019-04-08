import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { City } from '../models/city.model';

@Injectable({
  providedIn: 'root'
})
export class ServerCommunicationService {

  constructor(private http: HttpClient) { }

  getCities(technology2: string): Observable <City[]> {
    return this.http.post<City[]>('http://localhost:8080/getAmountOfAdsForTechnology', {
      technology: technology2
    });
  }

}
