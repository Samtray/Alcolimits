import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDrvComponent } from './add-drv.component';

describe('AddDrvComponent', () => {
  let component: AddDrvComponent;
  let fixture: ComponentFixture<AddDrvComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddDrvComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDrvComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
