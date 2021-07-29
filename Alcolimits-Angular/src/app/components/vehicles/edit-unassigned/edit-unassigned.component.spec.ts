import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditUnassignedComponent } from './edit-unassigned.component';

describe('EditUnassignedComponent', () => {
  let component: EditUnassignedComponent;
  let fixture: ComponentFixture<EditUnassignedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditUnassignedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditUnassignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
