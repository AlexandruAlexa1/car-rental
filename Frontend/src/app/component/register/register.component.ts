import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/domain/user';
import { NotificationType } from 'src/app/enum/notification-type';
import { NotificationService } from 'src/app/service/notification.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {

  registerForm: FormGroup;
  subscriptions: Subscription[] = [];

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private router: Router,
              private notificationService: NotificationService) {}

  ngOnInit(): void {
    this.buildForm();
  }

  buildForm() {
    this.registerForm = this.formBuilder.group({
      id: [''],
      email: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(125)]],
      password: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(64)]],
      repeatPassword: ['', Validators.required],
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(45)]],
      address: this.formBuilder.group({
        city: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        state: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        country: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        postalCode: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
        phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(15)]]
      })
    }, { validator: this.checkPasswords });
  }

  checkPasswords(group: FormGroup): any {
    let password = group.get('password')?.value;
    let repeatPassword = group.get('repeatPassword')?.value;

    return password === repeatPassword ? null : { notSame: true };
  }

  register() {
    const formValue = { ...this.registerForm.value };
    delete formValue.repeatPassword;

    console.log(formValue);

    this.subscriptions.push(
      this.userService.register(formValue).subscribe(
        (response: User) => {
          this.router.navigateByUrl('/login');
          this.notificationService.sendNotification(NotificationType.SUCCESS, `Hi, ${response.firstName}! You have been register successfuly!`);
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
