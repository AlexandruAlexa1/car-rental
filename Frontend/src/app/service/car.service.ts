import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Car } from '../domain/car';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private url = 'http://localhost:8080/api/v1/cars';

  constructor(private httpClient: HttpClient) { }

  listAll(): Observable<Car[]> {
    return this.httpClient.get<Car[]>(this.url);
  }

  save(car: Car): Observable<Car> {
    return this.httpClient.post<Car>(this.url, car);
  }

  get(id: number): Observable<Car> {
    return this.httpClient.get<Car>(`${this.url}/${id}`);
  }

  update(car: Car): Observable<Car> {
    return this.httpClient.put<Car>(this.url, car);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.url}/${id}`);
  }

  findByBrandAndModel(brand: string, model: string): Observable<Car[]> {
    return this.httpClient.get<Car[]>(`${this.url}/brand-model?brand=${brand}&model=${model}`);
  }

  findByFuelType(fuelType: string): Observable<Car[]> {
    return this.httpClient.get<Car[]>(`${this.url}/fuel-type?fuelType=${fuelType}`);
  }

  findAvailableCars(startDate: Date, endDate: Date): Observable<Car[]> {
    return this.httpClient.get<Car[]>(`${this.url}/available?startDate=${startDate}&endDate=${endDate}`);
  }
}
