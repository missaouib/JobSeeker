import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ServerCommunicationService {

  constructor(private http: HttpClient) { }

  getCities(technology: string) {
    this.http.get('http://localhost:8080/getAmountOfAdsForTechnology');
  }

}
