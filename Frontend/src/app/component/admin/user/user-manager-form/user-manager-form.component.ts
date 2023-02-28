import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Inject, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { User } from 'src/app/domain/user';
import { NotificationType } from 'src/app/enum/notification-type';
import { NotificationService } from 'src/app/service/notification.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-manager-form',
  templateUrl: './user-manager-form.component.html',
  styleUrls: ['./user-manager-form.component.css']
})
export class UserManagerFormComponent implements OnInit, OnDestroy {

  @Output() userAdded = new EventEmitter<User>();
  subscriptions: Subscription[] = [];
  userForm: FormGroup;
  isEditMode = this.user_id != null;
  user: User;

  constructor(@Inject(MAT_DIALOG_DATA) private user_id: number,
              private fb: FormBuilder,
              private userService: UserService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    if (this.isEditMode) {
      this.getUser();
      this.buildEditForm();
    } else {
      this.buildForm();
    }
  }

  buildEditForm() {
    this.userForm = this.fb.group({
      id: [''],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      address: this.fb.group({
        city: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        state: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        country: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        postalCode: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]]
      })
    });
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
  
  buildForm() {
    this.userForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      address: this.fb.group({
        city: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        state: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        country: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        postalCode: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]]
      })
    });
  }

  onSubmit() {
    console.log(`FORM DATA: ${this.userForm.value}`)

    this.dialog.closeAll();

    if (this.isEditMode) {
      this.update();
    } else {
      this.save();
    }
  }
  
  update() {
    this.subscriptions.push(
      this.userService.update(this.userForm.value).subscribe(
        (response: User) => {
          this.notificationService.sendNotification(NotificationType.SUCCESS, 'The user has beed updated successfuly!');
          this.userAdded.emit(response);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  save() {
    this.subscriptions.push(
      this.userService.save(this.userForm.value).subscribe(
        (response: User) => {
          this.notificationService.sendNotification(NotificationType.SUCCESS, 'The user has beed saved successfuly!');
          this.userAdded.emit(response);
        }, 
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe);
  }
}
