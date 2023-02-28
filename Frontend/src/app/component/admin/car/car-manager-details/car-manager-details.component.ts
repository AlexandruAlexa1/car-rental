import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/domain/car';
import { CarService } from 'src/app/service/car.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-car-manager-details',
  templateUrl: './car-manager-details.component.html',
  styleUrls: ['./car-manager-details.component.css']
})
export class CarManagerDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  car: Car;

  constructor(@Inject(MAT_DIALOG_DATA) private id: number,
              private carService: CarService,
              private notification: NotificationService) {}
 
  ngOnInit(): void {
    this.getCar();
  }

  getCar() {
    this.subscriptions.push(
      this.carService.get(this.id).subscribe(
        (response: Car) => {
          this.car = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe);
  }
}
