import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { User } from 'src/app/domain/user';
import { NotificationService } from 'src/app/service/notification.service';
import { UserService } from 'src/app/service/user.service';
import * as $ from 'jquery';
import { MatDialog } from '@angular/material/dialog';
import { UserManagerDetailsComponent } from '../user-manager-details/user-manager-details.component';
import { NotificationType } from 'src/app/enum/notification-type';
import { UserManagerFormComponent } from '../user-manager-form/user-manager-form.component';

@Component({
  selector: 'app-user-manager',
  templateUrl: './user-manager.component.html',
  styleUrls: ['./user-manager.component.css']
})
export class UserManagerComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  users: User[];
  itemsPerPage: number = 5;
  currentPage: number = 1;
  user_id: any;

  constructor(private userService: UserService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.listAll();
  }

  listAll() {
    this.subscriptions.push(
      this.userService.listAll().subscribe(
        (response: User[]) => {
          this.users = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  showForm() {
    const dialogRef = this.dialog.open(UserManagerFormComponent, {
      width: '50%',
      height: '50%',
      data: this.user_id
    });

    // dialogRef.componentInstance.userAdded.subscribe(() => {
    //   this.listAll();
    // });
  }

  viewDetails(id: number) {
    this.dialog.open(UserManagerDetailsComponent, {
      width: '100%',
      height: '100%',
      data: id
    });
  }

  editUser(id: number) {
    this.user_id = id;
    this.showForm();
  }

  showDeleteModal(id: number) {
    this.user_id = id;

    $('.delete-modal').show();
    $('.delete-modal-message').text(`Delete user with id: ${id}`);
  }

  delete() {
    this.subscriptions.push(
      this.userService.delete(this.user_id).subscribe(
        () => {
          $('.delete-modal').hide();
          this.listAll();
          this.notificationService.sendNotification(NotificationType.SUCCESS ,`The user with ID: ${this.user_id} was deleted successfuly!`);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  closeModal() {
    $('.delete-modal').hide();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe);
  }
}
