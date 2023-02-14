import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/domain/car';
import { FuelType } from 'src/app/enum/fuel-type';
import { NotificationType } from 'src/app/enum/notification-type';
import { CarService } from 'src/app/service/car.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css']
})
export class CarComponent implements OnInit, OnDestroy {
  subscriptions: Subscription[] = [];
  fuelTypes = Object.values(FuelType);
  cars: Car[];
  itemsPerPage: number = 3;
  p: number = 1;
  

  constructor(private carService: CarService,
              private notificationService: NotificationService) {}

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
          console.log(errorResponse);
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  findByBrandAndModel(brand: string, model: string) {
    this.subscriptions.push(
      this.carService.findByBrandAndModel(brand, model).subscribe(
        (response: Car[]) => {
          if (!response) {
            this.cars = [];
            this.notificationService.sendNotification(NotificationType.INFO, `Could not find any cars`);
          } else {
            this.cars = response;
          }
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  findAvailableCars(startDate: any, endDate: any) {
    this.subscriptions.push(
      this.carService.findAvailableCars(startDate, endDate).subscribe(
        (response: Car[]) => {
          if (!response) {
            this.cars = [];
            this.notificationService.sendNotification(NotificationType.INFO, `Could not find any cars`);
          } else {
            this.cars = response;
          }
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  findByFuelType(fuelType: string) {
    this.subscriptions.push(
      this.carService.findByFuelType(fuelType).subscribe(
        (response: Car[]) => {
          if (!response) {
            this.cars = [];
            this.notificationService.sendNotification(NotificationType.INFO, `Could not find any cars`);
          } else {
            this.cars = response;
          }
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
