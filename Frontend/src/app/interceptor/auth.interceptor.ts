import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../service/authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthenticationService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Pass request if request contains /login or /register
    if (request.url.includes(`${this.authService.host}/login`) || 
        request.url.includes(`${this.authService.host}/register`)) {
          return next.handle(request);
    }

    // Load token in local cash
    this.authService.loadToken();

    // Get token from local cash
    const token = this.authService.getAuthToken();

    // Create a clone from request and pass the Authorization Header with Bearer and token
    const httpRequest = request.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });

    // Finally return the custom Request
    // This request will be send every time to the Backend when we call UserService
    return next.handle(httpRequest);
  }

}
