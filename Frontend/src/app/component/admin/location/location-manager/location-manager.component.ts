import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { NotificationType } from 'src/app/enum/notification-type';
import { LocationService } from 'src/app/service/location.service';
import { NotificationService } from 'src/app/service/notification.service';
import { LocationDetailsComponent } from '../location-details/location-details.component';
import { LocationFormComponent } from '../location-form/location-form.component';
import { Location } from 'src/app/domain/location';
import * as $ from 'jquery';

@Component({
  selector: 'app-location-manager',
  templateUrl: './location-manager.component.html',
  styleUrls: ['./location-manager.component.css']
})
export class LocationManagerComponent implements OnInit, OnDestroy {
 
  subscriptions: Subscription[] = [];
  locations: Location[];
  itemsPerPage: number = 5;
  currentPage: number = 1;
  location_id: number;

  constructor(private locationService: LocationService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}

  ngOnInit(): void {
    this.listAll();
    console.log(this.location_id);
  }

  listAll() {
    this.subscriptions.push(
      this.locationService.listAll().subscribe(
        (response: Location[]) => {
          this.locations = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  showForm() {
    const dialogRef = this.dialog.open(LocationFormComponent, {
      width: '50%',
      height: '50%',
      data: this.location_id
    });

    dialogRef.componentInstance.locationAdded.subscribe(() => {
      this.listAll();
    })
  }

  viewDetails(id: number) {
    this.dialog.open(LocationDetailsComponent, {
      width: '50%',
      height: '50%',
      data: id
    });
  }

  editLocation(id: number) {
    this.location_id = id;
    this.showForm();
  }

  showDeleteModal(id: number) {
    this.location_id = id;

    $('.delete-modal').show();
    $('.delete-modal-message').text(`Delete location with id: ${id}`);
  }

  delete() {
    this.subscriptions.push(
      this.locationService.delete(this.location_id).subscribe(
        () => {
          $('.delete-modal').hide();
          this.listAll();
          this.notificationService.sendNotification(NotificationType.SUCCESS ,`The location with ID: ${this.location_id} was deleted successfuly!`);
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
