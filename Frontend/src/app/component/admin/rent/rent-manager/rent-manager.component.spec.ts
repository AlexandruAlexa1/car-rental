import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentManagerComponent } from './rent-manager.component';

describe('RentManagerComponent', () => {
  let component: RentManagerComponent;
  let fixture: ComponentFixture<RentManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RentManagerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
