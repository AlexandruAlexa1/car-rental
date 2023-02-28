import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarManagerFormComponent } from './car-manager-form.component';

describe('CarManagerFormComponent', () => {
  let component: CarManagerFormComponent;
  let fixture: ComponentFixture<CarManagerFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CarManagerFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarManagerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
