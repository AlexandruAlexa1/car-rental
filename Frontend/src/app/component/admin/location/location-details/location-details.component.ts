import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { LocationService } from 'src/app/service/location.service';
import { NotificationService } from 'src/app/service/notification.service';
import { Location } from 'src/app/domain/location';

@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.css']
})
export class LocationDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] =[];
  location: Location;

  constructor(@Inject(MAT_DIALOG_DATA) private location_id: number,
              private locationService: LocationService,
              private notification: NotificationService) {}

  ngOnDestroy(): void {
    this.getLocation();
  }

  getLocation() {
    this.subscriptions.push(
      this.locationService.get(this.location_id).subscribe(
        (response: Location) => {
          this.location = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notification.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  ngOnInit(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
