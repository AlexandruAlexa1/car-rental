import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CarManagerDetailsComponent } from './component/admin/car-manager-details/car-manager-details.component';
import { CarManagerFormComponent } from './component/admin/car-manager-form/car-manager-form.component';
import { CarManagerComponent } from './component/admin/car-manager/car-manager.component';
import { CarDetailsComponent } from './component/car-details/car-details.component';
import { CarComponent } from './component/car/car.component';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'cars', component: CarComponent },
  { path: 'cars/detail/:id', component: CarDetailsComponent },
  { path: 'admin/cars', component: CarManagerComponent },
  { path: 'admin/cars/detail', component: CarManagerDetailsComponent },
  { path: 'admin/cars/form', component: CarManagerFormComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
