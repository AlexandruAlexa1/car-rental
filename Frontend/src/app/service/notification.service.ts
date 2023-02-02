import { Injectable } from '@angular/core';
import { NotificationType } from '../enum/notification-type';
import * as $ from 'jquery';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() { }

  sendNotification(notificationType: NotificationType, message: string) {
    const notification = $('.notification');
    const notificationMessage = $('.notification-message');

    // Set notification type
    switch(notificationType) {
      case 'Info':
        case 'Info':
          notification.addClass('info');
          break;
        case 'Warning':
          notification.addClass('warning');
          break;
        case 'Error':
          notification.addClass('error');
          break;
        case 'Success':
          notification.addClass('success');
          break;
        default:
          notification.addClass('info');
    }

    // Set message
    notificationMessage.text(message);

    // Show notification
    notification.fadeIn();

    // Hide notification
    this.hideNotification(notification);
  }

  private hideNotification(notification: JQuery<HTMLElement>) {
    setTimeout(() => {
      notification.fadeOut();
    }, 5000);
  }
  
}
