import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { Rent } from 'src/app/domain/rent';
import { NotificationType } from 'src/app/enum/notification-type';
import { NotificationService } from 'src/app/service/notification.service';
import { RentService } from 'src/app/service/rent.service';
import { RentManagerDetailsComponent } from '../rent-manager-details/rent-manager-details.component';
import { RentManagerFormComponent } from '../rent-manager-form/rent-manager-form.component';
import * as $ from 'jquery';

@Component({
  selector: 'app-rent-manager',
  templateUrl: './rent-manager.component.html',
  styleUrls: ['./rent-manager.component.css']
})
export class RentManagerComponent implements OnInit, OnDestroy {

  subscriptions: Subscription[] = [];
  rents: Rent[];
  itemsPerPage: number = 5;
  currentPage: number = 1;
  rent_id: number;

  constructor(private rentService: RentService,
              private notificationService: NotificationService,
              private dialog: MatDialog) {}
 
  ngOnInit(): void {
    this.listAll();
  }

  listAll() {
    this.subscriptions.push(
      this.rentService.listAll().subscribe(
        (response: Rent[]) => {
          this.rents = response;
        },
        (errorResponse: HttpErrorResponse) => {
          this.notificationService.sendErrorNotification(errorResponse.error.message);
        }
      )
    );
  }

  viewDetails(id: number) {
    this.dialog.open(RentManagerDetailsComponent, {
      width: '50%',
      height: '50%',
      data: id
    });
  }

  editRent(id: number) {
    this.rent_id = id;
    this.showForm();
  }

  showForm() {
    const dialogRef = this.dialog.open(RentManagerFormComponent, {
      width: '50%',
      height: '50%',
      data: this.rent_id
    })

    dialogRef.componentInstance.rentAdded.subscribe(() => {
      this.listAll();
    })
  }

  showDeleteModal(id: number) {
    this.rent_id = id;

    $('.delete-modal').show();
    $('.delete-modal-message').text(`Delete rent with id: ${id}`);
  }

  delete() {
    this.subscriptions.push(
      this.rentService.delete(this.rent_id).subscribe(
        () => {
          $('.delete-modal').hide();
          this.listAll();
          this.notificationService.sendNotification(NotificationType.SUCCESS ,`The rent with ID: ${this.rent_id} was deleted successfuly!`);
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
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
}
