import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/domain/car';
import { CarService } from 'src/app/service/car.service';
import { NotificationService } from 'src/app/service/notification.service';
import { CarManagerDetailsComponent } from '../car-manager-details/car-manager-details.component';
import { CarManagerFormComponent } from '../car-manager-form/car-manager-form.component';
import * as $ from 'jquery';
import { NotificationType } from 'src/app/enum/notification-type';

@Component({
  selector: 'app-car-manager',
  templateUrl: './car-manager.component.html',
  styleUrls: ['./car-manager.component.css']
})

export class CarManagerComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  cars: Car[];
  carId: number;
  itemsPerPage: number = 5;
  currentPage: number = 1;

  constructor(private carService: CarService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.listAll();
  }

  listAll() {
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

  showForm() {
    const dialogRef = this.dialog.open(CarManagerFormComponent, {
      width: '50%',
      height: '50%',
      data: this.carId
    });

    dialogRef.componentInstance.carAdded.subscribe(() => {
      this.listAll();
    });
  }

  viewDetails(id: number) {
    this.dialog.open(CarManagerDetailsComponent, {
      width: '100%',
      height: '100%',
      data: id
    });
  }

  editCar(id: number) {
    this.carId = id;
    this.showForm();
  }

  showDeleteModal(id: number) {
    this.carId = id;

    $('.delete-modal').show();
    $('.delete-modal-message').text(`Delete car with id: ${id}`);
  }

  delete() {
    this.subscriptions.push(
      this.carService.delete(this.carId).subscribe(
        () => {
          $('.delete-modal').hide();
          this.listAll();
          this.notificationService.sendNotification(NotificationType.SUCCESS ,`The car with ID: ${this.carId} was deleted successfuly!`);
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
