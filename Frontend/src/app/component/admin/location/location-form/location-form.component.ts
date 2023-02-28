import { HttpErrorResponse } from '@angular/common/http';
import { Component, EventEmitter, Inject, OnDestroy, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Location } from 'src/app/domain/location';
import { Car } from 'src/app/domain/car';
import { NotificationType } from 'src/app/enum/notification-type';
import { CarService } from 'src/app/service/car.service';
import { LocationService } from 'src/app/service/location.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-location-form',
  templateUrl: './location-form.component.html',
  styleUrls: ['./location-form.component.css']
})
export class LocationFormComponent implements OnInit, OnDestroy {
  @Output() locationAdded = new EventEmitter<Location>();
  subscriptions: Subscription[] = [];
  locationForm: FormGroup;
  isEditMode = this.location_id != null;
  location: Location;
  cars: Car[];

  constructor(@Inject(MAT_DIALOG_DATA) private location_id: number,
              private fb: FormBuilder,
              private locationService: LocationService,
              private carService: CarService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.getCars();

    if (this.isEditMode) {
      this.getLocation();
      this.buildEditForm();
    } else {
      this.buildForm();
    }
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

  buildEditForm() {
    this.locationForm = this.fb.group({
      id: [''],
      address: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  getLocation() {
    this.subscriptions.push(
      this.locationService.get(this.location_id).subscribe(
        (response: Location) => {
          this.location = response;

          this.locationForm.setValue({
            id: response.id,
            address: response.address,
            phoneNumber: response.phoneNumber,
            email: response.email
          })
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }
  
  buildForm() {
    this.locationForm = this.fb.group({
      address: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      email: ['', Validators.required]
    });
  }

  onSubmit() {
    this.dialog.closeAll();

    if (this.isEditMode) {
      this.update();
    } else {
      this.save();
    }
  }
  
  update() {
    this.subscriptions.push(
      this.locationService.update(this.locationForm.value).subscribe(
        (response: Location) => {
          this.notificationService.sendNotification(NotificationType.SUCCESS, 'The location has beed updated successfuly!');
          this.locationAdded.emit(response);
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  save() {
    this.subscriptions.push(
      this.locationService.save(this.locationForm.value).subscribe(
        (response: Location) => {
          this.notificationService.sendNotification(NotificationType.SUCCESS, 'The location has beed saved successfuly!');
          this.locationAdded.emit(response);
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
