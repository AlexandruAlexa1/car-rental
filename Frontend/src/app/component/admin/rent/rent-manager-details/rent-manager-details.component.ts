import { HttpErrorResponse } from '@angular/common/http';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Rent } from 'src/app/domain/rent';
import { NotificationService } from 'src/app/service/notification.service';
import { RentService } from 'src/app/service/rent.service';

@Component({
  selector: 'app-rent-manager-details',
  templateUrl: './rent-manager-details.component.html',
  styleUrls: ['./rent-manager-details.component.css']
})
export class RentManagerDetailsComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  rent: Rent;

  constructor(@Inject(MAT_DIALOG_DATA) private rent_id: number,
              private rentService: RentService,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.getRent();
  }

  getRent() {
    this.subscriptions.push(
      this.rentService.get(this.rent_id).subscribe(
        (response: Rent) => {
          this.rent = response;
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
