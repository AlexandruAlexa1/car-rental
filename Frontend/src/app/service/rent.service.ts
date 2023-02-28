import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rent } from '../domain/rent';

@Injectable({
  providedIn: 'root'
})
export class RentService {

  url = 'http://localhost:8080/api/v1/rents';

  constructor(private httpClient: HttpClient) { }

  listAll(): Observable<Rent[]> {
    return this.httpClient.get<Rent[]>(this.url);
  }

  get(id: number): Observable<Rent> {
    return this.httpClient.get<Rent>(`${this.url}/${id}`);
  }

  update(rent: Rent): Observable<Rent> {
    return this.httpClient.post<Rent>(this.url, rent);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.url}/${id}`);
  }
}
