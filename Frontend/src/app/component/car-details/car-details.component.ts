import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Car } from 'src/app/domain/car';
import { CarService } from 'src/app/service/car.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-car-details',
  templateUrl: './car-details.component.html',
  styleUrls: ['./car-details.component.css']
})
export class CarDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  car: Car;
 
  constructor(private carService: CarService,
              private activatedRoute: ActivatedRoute,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.getCar();
  }
  
  getCar() {
    const car_id = this.activatedRoute.snapshot.params['id'];

    this.subscriptions.push(
      this.carService.get(car_id).subscribe(
        (response: Car) => {
          this.car = response;
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
