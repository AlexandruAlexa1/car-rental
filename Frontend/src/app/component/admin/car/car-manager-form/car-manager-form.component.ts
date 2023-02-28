import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Inject, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription} from 'rxjs';
import { Car } from 'src/app/domain/car';
import { Location } from 'src/app/domain/location';
import { FuelType } from 'src/app/enum/fuel-type';
import { NotificationType } from 'src/app/enum/notification-type';
import { CarService } from 'src/app/service/car.service';
import { LocationService } from 'src/app/service/location.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-car-manager-form',
  templateUrl: './car-manager-form.component.html',
  styleUrls: ['./car-manager-form.component.css']
})
export class CarManagerFormComponent implements OnInit, OnDestroy {

  @Output() carAdded = new EventEmitter<Car>();

  subscriptions: Subscription[] = [];
  fuelTypes = Object.values(FuelType);
  isEditMode = this.carId != null;
  carForm: FormGroup;
  car = new Car();
  locations: Location[];

  constructor(@Inject(MAT_DIALOG_DATA) private carId: number,
              private fb: FormBuilder,
              private carService: CarService,
              private locationService: LocationService,
              private notification: NotificationService,
              private dialog: MatDialog) {}
 
  ngOnInit(): void {
    this.getLocations();

    if (this.isEditMode) {
      this.getCar();
      this.buildEditForm();
    } else {
      this.buildForm();
    }
  }

  getLocations() {
    this.subscriptions.push(
      this.locationService.listAll().subscribe(
        (response: Location[]) => {
          this.locations = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    )
  }

  buildEditForm() {
    this.carForm = this.fb.group({
      id: [''],
      brand: ['', Validators.required],
      model: ['', Validators.required],
      year: ['', Validators.required],
      color: ['', Validators.required],
      seatCount: ['', Validators.required],
      fuelType: ['', Validators.required],
      pricePerDay: ['', Validators.required],
      location: ['']
    });
  }

  getCar() {
    this.subscriptions.push(
      this.carService.get(this.carId).subscribe(
        (response: Car) => {
          this.car = response;
          console.log(response);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  buildForm() {
      this.carForm = this.fb.group({
        brand: ['', Validators.required],
        model: ['', Validators.required],
        year: ['', Validators.required],
        color: ['', Validators.required],
        seatCount: ['', Validators.required],
        fuelType: ['', Validators.required],
        pricePerDay: ['', Validators.required],
        location: ['']
      });
  }

  save() {
    console.log(this.carForm.getRawValue());

    if (this.isEditMode) {
      this.editCar();
    } else {
      this.addCar();
    }
  }

  addCar() {
    this.subscriptions.push(
      this.carService.save(this.carForm.getRawValue()).subscribe(
        (response: Car) => {
          this.dialog.closeAll();

          this.notification.sendNotification(NotificationType.SUCCESS, 'The car was added successfuly!');

          this.carAdded.emit(response);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  editCar() {
    this.subscriptions.push(
      this.carService.update(this.carForm.getRawValue()).subscribe(
        (response: Car) => {
          this.dialog.closeAll();

          this.notification.sendNotification(NotificationType.SUCCESS, 'The car was updated successfuly!');

          this.carAdded.emit(response);
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
