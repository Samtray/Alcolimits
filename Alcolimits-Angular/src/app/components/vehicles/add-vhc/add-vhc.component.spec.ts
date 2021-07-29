import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddVhcComponent } from './add-vhc.component';

describe('AddVhcComponent', () => {
  let component: AddVhcComponent;
  let fixture: ComponentFixture<AddVhcComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddVhcComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddVhcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
