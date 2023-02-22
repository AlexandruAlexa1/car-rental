import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './component/login/login.component';
import { NotificationComponent } from './component/notification/notification.component';
import { RegisterComponent } from './component/register/register.component';
import { CarComponent } from './component/car/car.component';
import { AuthInterceptor } from './interceptor/auth.interceptor';
import { CarDetailsComponent } from './component/car-details/car-details.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { CarManagerComponent } from './component/admin/car-manager/car-manager.component';
import { CarManagerDetailsComponent } from './component/admin/car-manager-details/car-manager-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { CarManagerFormComponent } from './component/admin/car-manager-form/car-manager-form.component';
import { UserManagerComponent } from './component/admin/user-manager/user-manager.component';
import { UserManagerFormComponent } from './component/admin/user-manager-form/user-manager-form.component';
import { UserManagerDetailsComponent } from './component/admin/user-manager-details/user-manager-details.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NotificationComponent,
    RegisterComponent,
    CarComponent,
    CarDetailsComponent,
    CarManagerComponent,
    CarManagerDetailsComponent,
    CarManagerFormComponent,
    UserManagerComponent,
    UserManagerFormComponent,
    UserManagerDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    MatDialogModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
