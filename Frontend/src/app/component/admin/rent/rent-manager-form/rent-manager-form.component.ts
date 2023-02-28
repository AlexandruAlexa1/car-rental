import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Inject, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/domain/car';
import { Rent } from 'src/app/domain/rent';
import { User } from 'src/app/domain/user';
import { NotificationType } from 'src/app/enum/notification-type';
import { CarService } from 'src/app/service/car.service';
import { NotificationService } from 'src/app/service/notification.service';
import { RentService } from 'src/app/service/rent.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-rent-manager-form',
  templateUrl: './rent-manager-form.component.html',
  styleUrls: ['./rent-manager-form.component.css']
})
export class RentManagerFormComponent implements OnInit, OnDestroy {
  @Output() rentAdded = new EventEmitter<Rent>();
  subscriptions: Subscription[] =[];
  rentForm: FormGroup;
  rent: Rent;
  cars: Car[];
  users: User[];

  constructor(@Inject(MAT_DIALOG_DATA) private rent_id: number,
              private fb: FormBuilder,
              private rentServie: RentService,
              private carService: CarService,
              private userService: UserService,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.buildForm();
    this.getCar();
    this.getCars();
    this.getUsers();
  }

  getUsers() {
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

  getCars() {
    this.subscriptions.push(
      this.carService.listAll().subscribe(
        (response: Car[]) => {
          this.cars = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  getCar() {
    this.subscriptions.push(
      this.rentServie.get(this.rent_id).subscribe(
        (response: Rent) => {
          this.rent = response;

          this.rentForm.setValue({
            id: response.id,
            startDate: response.startDate,
            endDate: response.endDate,
            car: response.car,
            user: response.user
          });
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  update() {
    console.log(this.rentForm.value);

    this.subscriptions.push(
      this.rentServie.update(this.rentForm.value).subscribe(
        (response: Rent) => {
          this.notificationService.sendNotification(NotificationType.SUCCESS, 'The rent has beed updated successfuly!');
          this.rentAdded.emit(response);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  buildForm() {
    this.rentForm = this.fb.group({
      id: [''],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      car: this.fb.group({
        id: ['']
      }),
      user: this.fb.group({
        id: ['']
      })
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
