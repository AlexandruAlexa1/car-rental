import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthRequest } from 'src/app/domain/auth-request';
import { Jwt } from 'src/app/domain/jwt';
import { NotificationType } from 'src/app/enum/notification-type';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];

  constructor(private authService: AuthenticationService,
              private router: Router,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.checkIfUserIsLoggedIn();
  }

  checkIfUserIsLoggedIn() {
    if (this.authService.isLoggedIn()) {
      // this.router.navigateByUrl('/cars');
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  onLogin(authRequest: AuthRequest) {
    this.subscriptions.push(
      this.authService.loginUser(authRequest).subscribe(
        (response: HttpResponse<Jwt>) => {
          this.authService.saveAuthToken(String(response.body?.jwt));

          // this.router.navigateByUrl('/cars');
        },
        (errorResponse: HttpErrorResponse) => {
          this.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  sendErrorNotification(message: any) {
    if (message) {
      this.notificationService.sendNotification(NotificationType.INFO, message);
    } else {
      this.notificationService.sendNotification(NotificationType.ERROR, 'An error occured. Please try again.');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}


