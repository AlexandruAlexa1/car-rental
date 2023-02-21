import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarManagerDetailsComponent } from './car-manager-details.component';

describe('CarManagerDetailsComponent', () => {
  let component: CarManagerDetailsComponent;
  let fixture: ComponentFixture<CarManagerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarManagerDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarManagerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
