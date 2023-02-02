import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from "@auth0/angular-jwt";
import { Observable } from 'rxjs';
import { AuthRequest } from '../domain/auth-request';
import { Jwt } from '../domain/jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private jwtHelperService = new JwtHelperService();
  private loggedUser: string | null;
  private token: string | null;
  host = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  loginUser(authRequest: AuthRequest): Observable<HttpResponse<Jwt>> {
    return this.httpClient.post<Jwt>(`${this.host}/auth/login`, authRequest, { observe: 'response' });
  }

  isLoggedIn(): boolean {
    this.loadToken();

    // Check if token exist
    if (this.token != null && this.token !== '') {

      // Check if token contains the subject
      if (this.jwtHelperService.decodeToken(this.token).sub != null || '') {

        // Check if token is not expired
        if (!this.jwtHelperService.isTokenExpired(this.token)) {
          
          // Set logged user
          this.loggedUser = this.jwtHelperService.decodeToken(this.token).sub;

          return true;
        }
      }
    }

    this.logoutUser();
    return false;
  }

  logoutUser() {
    this.token = null;
    this.loggedUser = null;

    localStorage.removeItem('token');
  }

  loadToken(): void {
    this.token = localStorage.getItem('token');
  }

  saveAuthToken(token: string): void {
    this.token = token;
    localStorage.setItem('token', token);
  }

  getAuthToken(): any {
    return this.token;
  }
}