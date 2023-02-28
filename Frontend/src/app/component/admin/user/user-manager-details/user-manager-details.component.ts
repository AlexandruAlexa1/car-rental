import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { User } from 'src/app/domain/user';
import { NotificationService } from 'src/app/service/notification.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-manager-details',
  templateUrl: './user-manager-details.component.html',
  styleUrls: ['./user-manager-details.component.css']
})
export class UserManagerDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  user: User;

  constructor(@Inject(MAT_DIALOG_DATA) private user_id: number,
              private userService: UserService,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser() {
    this.subscriptions.push(
      this.userService.get(this.user_id).subscribe(
        (response: User) => {
          this.user = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
