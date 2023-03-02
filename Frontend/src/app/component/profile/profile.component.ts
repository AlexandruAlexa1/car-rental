import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { User } from 'src/app/domain/user';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { NotificationService } from 'src/app/service/notification.service';
import { UserService } from 'src/app/service/user.service';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import { NotificationType } from 'src/app/enum/notification-type';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  profileForm: FormGroup;
  authUser: User;

  constructor(private fb: FormBuilder,
              private authService: AuthenticationService,
              private userService: UserService,
              private notification: NotificationService,
              private router: Router) {}

  ngOnInit(): void {
    this.buildForm();
  }
  
  getAuthUser() {
    console.log(this.authService.loggedUser);

    this.subscriptions.push(
      this.userService.findByEmail(this.authService.loggedUser).subscribe(
        (response: User) => {
          this.authUser = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  buildForm() {
    this.profileForm = this.fb.group({
      id: [''],
      email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(125)]],
      password: ['', [Validators.minLength(5), Validators.maxLength(64)]],
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(125)]],
      address: this.fb.group({
        city: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        state: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        country: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        postalCode: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]]
      })
    });
  }

  showProfile() {
    if (this.authService.isLoggedIn()) {
      // this.getAuthUser();
      $('.user-profile').toggle('slow');
    }
  }

  updateAccount() {
    this.subscriptions.push(
      this.userService.update(this.profileForm.value).subscribe(
        (response: User) => {
          // this.getAuthUser();
          // this.buildForm();
          this.notification.sendNotification(NotificationType.SUCCESS, 'Your account has been updated successfuly!');
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  logOut() {
    this.authService.logoutUser();
    this.router.navigateByUrl('/login');
  }
  
  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
