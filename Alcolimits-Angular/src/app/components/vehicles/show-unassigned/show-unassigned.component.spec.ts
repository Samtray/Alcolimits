import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowUnassignedComponent } from './show-unassigned.component';

describe('ShowUnassignedComponent', () => {
  let component: ShowUnassignedComponent;
  let fixture: ComponentFixture<ShowUnassignedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowUnassignedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowUnassignedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
