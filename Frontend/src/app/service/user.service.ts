import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../domain/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
 
  private host = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  save(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.host}/api/v1/users`, user);
  }

  update(user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.host}/api/v1/users`, user);
  }

  listAll(): Observable<User[]> {
    return this.httpClient.get<User[]>(`${this.host}/api/v1/users`);
  }

  get(user_id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.host}/api/v1/users/${user_id}`);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete<any>(`${this.host}/api/v1/users/${id}`)
  }

  register(user: User): Observable<User> {
    return this.httpClient.post<User>(`${this.host}/auth/register`, user);
  }
}
