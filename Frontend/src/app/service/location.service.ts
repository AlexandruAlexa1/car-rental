import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Location } from 'src/app/domain/location';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  url = 'http://localhost:8080/api/v1/locations';

  constructor(private http: HttpClient) { }

  listAll(): Observable<Location[]> {
    return this.http.get<Location[]>(this.url);
  }

  get(id: number): Observable<Location> {
    return this.http.get<Location>(`${this.url}/${id}`);
  }

  save(location: Location): Observable<Location> {
    return this.http.post<Location>(this.url, location);
  }

  update(location: Location): Observable<Location> {
    return this.http.put<Location>(this.url, location);
  }

  delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/${id}`);
  }
}
