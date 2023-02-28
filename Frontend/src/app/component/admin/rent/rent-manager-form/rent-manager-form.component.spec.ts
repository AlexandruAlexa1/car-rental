import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentManagerFormComponent } from './rent-manager-form.component';

describe('RentManagerFormComponent', () => {
  let component: RentManagerFormComponent;
  let fixture: ComponentFixture<RentManagerFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RentManagerFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentManagerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
