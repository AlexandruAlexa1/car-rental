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
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { MenuComponent } from './component/menu/menu.component';
import { RentManagerComponent } from './component/admin/rent/rent-manager/rent-manager.component';
import { RentManagerDetailsComponent } from './component/admin/rent/rent-manager-details/rent-manager-details.component';
import { RentManagerFormComponent } from './component/admin/rent/rent-manager-form/rent-manager-form.component';
import { CarManagerComponent } from './component/admin/car/car-manager/car-manager.component';
import { CarManagerDetailsComponent } from './component/admin/car/car-manager-details/car-manager-details.component';
import { CarManagerFormComponent } from './component/admin/car/car-manager-form/car-manager-form.component';
import { UserManagerComponent } from './component/admin/user/user-manager/user-manager.component';
import { UserManagerFormComponent } from './component/admin/user/user-manager-form/user-manager-form.component';
import { UserManagerDetailsComponent } from './component/admin/user/user-manager-details/user-manager-details.component';
import { LocationManagerComponent } from './component/admin/location/location-manager/location-manager.component';
import { LocationDetailsComponent } from './component/admin/location/location-details/location-details.component';
import { LocationFormComponent } from './component/admin/location/location-form/location-form.component';

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
    UserManagerDetailsComponent,
    MenuComponent,
    RentManagerComponent,
    RentManagerDetailsComponent,
    RentManagerFormComponent,
    LocationManagerComponent,
    LocationDetailsComponent,
    LocationFormComponent
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
