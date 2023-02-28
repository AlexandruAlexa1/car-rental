import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentManagerDetailsComponent } from './rent-manager-details.component';

describe('RentManagerDetailsComponent', () => {
  let component: RentManagerDetailsComponent;
  let fixture: ComponentFixture<RentManagerDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RentManagerDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentManagerDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
